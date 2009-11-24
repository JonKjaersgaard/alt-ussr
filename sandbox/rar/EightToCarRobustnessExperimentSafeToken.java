/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package rar;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import rar.EightToCarRobustnessBatch.Parameters;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.physics.TimedPhysicsObserver;
import ussr.remote.SimulationClient;
import ussr.remote.facade.ParameterHolder;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONController;
import ussr.samples.atron.GenericATRONSimulation;

/**
 * Port of the eight-to-car simulation to Java.  Classical ATRON self-reconfiguration example.
 * 
 * @author ups
 */ 
public class EightToCarRobustnessExperimentSafeToken extends EightToCarRobustnessExperiment {

    private static final int RETRANSMIT_DELAY = 100;
    private static final float TRANSMIT_DELAY_MS = RETRANSMIT_DELAY/1000.0f;
    private static final boolean TRACE = false;
    private int maxNretries;
    
    public EightToCarRobustnessExperimentSafeToken(int maxNretries) {
        this.maxNretries = maxNretries;
    }
    
    public int getMaxNretries() { return maxNretries; }
    
    @Override
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new EightController();
            }
        };
    }

    private static class Packet {
        byte channel; byte[] payload; int id; private int number_of_tries;
        Packet(byte channel, byte[] payload) { this(channel,payload,-1); }
        Packet(byte channel, byte[] payload, int id) { this.channel = channel; this.payload = payload; this.id = id; } 
        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + channel;
            result = prime * result + Arrays.hashCode(payload);
            return result;
        }
        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Packet other = (Packet) obj;
            if (channel != other.channel)
                return false;
            if (!Arrays.equals(payload, other.payload))
                return false;
            return true;
        }
        public synchronized void incTries() { number_of_tries++; }
        public int getTries() { return number_of_tries; }
    }
    
    private static class CacheEntry {
        private byte id; byte counter;
        /**
         * @param id
         * @param counter
         */
        public CacheEntry(byte id, byte counter) {
            this.id = id;
            this.counter = counter;
        }
        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + counter;
            result = prime * result + id;
            return result;
        }
        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            CacheEntry other = (CacheEntry) obj;
            if (counter != other.counter)
                return false;
            if (id != other.id)
                return false;
            return true;
        }
        
    }
    
    protected class EightController extends ATRONController {

        int token[] = new int[1];
        int moduleTranslator[] = new int[7];
        int message[] = new int[3];
        boolean eight2car_active = true;
        byte lastConnectorTry = 0;
        int retries = 0;
        byte packetCounter = 0;
        List<Packet> ackQueue = java.util.Collections.synchronizedList(new LinkedList<Packet>());
        List<Packet> outbound = new LinkedList<Packet>();
        List<CacheEntry> packetsSeen = new LinkedList<CacheEntry>();
        private float lastSendTime = 0;
        private boolean transmitNow = false;
        private float last_reset_check_time = 0, reset_interval, reset_risk;
        private Random resetRandomizer;

        public EightController() {
            Parameters p = (Parameters)ParameterHolder.get();
            reset_risk = p.resetRisk;
            reset_interval = p.resetInterval;
            resetRandomizer = p.seedMaybe==null?new Random():new Random(p.seedMaybe);
        }
        
        private void sendMessage(int[] message, int size, int channel) {
            if(VERIFY_OPERATIONS && !this.isConnected(channel)) { 
                System.out.println("WARNING: module "+this.getMyID()+" unable to deliver on "+channel); System.out.flush(); 
                if(retries++==8) throw new Error("Unable to deliver message");
                byte newChannel = (byte)((channel+lastConnectorTry)%8);
                newChannel++;
            }
            int state = message[1];
            int moduleNumber = message[2];
            System.out.println("Module "+getMyID()+" setting module "+moduleNumber+" to state "+state+" through channel "+channel);
            lowlevelSendMessage(message, size, channel);
        }

        private static final byte HEADER_SIZE = 3;
        private static final byte MAGIC_SEND_HEADER = (byte)123;
        private static final byte MAGIC_ACK_HEADER = (byte)124;

        private void lowlevelSendMessage(int[] message, int size, int channel) {
            byte[] bmsg = new byte[message.length+HEADER_SIZE];
            byte counter = packetCounter++;
            bmsg[0] = MAGIC_SEND_HEADER;
            bmsg[1] = counter;
            bmsg[2] = (byte) getMyID();
            for(int i=HEADER_SIZE;i<message.length+HEADER_SIZE;i++)
                bmsg[i] = (byte)message[i-HEADER_SIZE];
            synchronized(outbound) { outbound.add(new Packet((byte)channel,bmsg,counter)); }
            transmitNow = true;
        }
        
        private void sendAct() {
            float time = module.getSimulation().getTime();
            if(!transmitNow && lastSendTime+TRANSMIT_DELAY_MS>time) return;
            lastSendTime = time;
            if(outbound.size()>0) {
                transmitNow = false;
                Packet p;
                synchronized(outbound) {
                    if(outbound.size()==0) return;
                    p = outbound.remove(0);
                    if(p.getTries()>=getMaxNretries()) {
                        System.out.println("["+getMyID()+"] Timeout");
                        return;
                    }
                    p.incTries();
                    outbound.add(p);
                }
                if(TRACE) System.out.println("["+getMyID()+"] Sending outbound packet id "+p.id);
                super.sendMessage(p.payload, (byte)p.payload.length, p.channel);
            } else if(ackQueue.size()>0) {
                Packet p = ackQueue.remove(0);
                if(TRACE&&!this.isConnected(p.channel)) System.out.println("["+getMyID()+"] Warning: attempting ack with no neighbor");
                super.sendMessage(p.payload, (byte)p.payload.length, p.channel);
            }
            
//            while(counter==packetCounter) {
//                if(!this.isConnected(channel)) {
//                    if(TRACE) System.out.println("["+getMyID()+"] Warning: attempting transmit with no neighbor");
//                    packetCounter++;
//                    break;
//                }
//                if(ackQueue.size()>0) {
//                } else {
//                    super.sendMessage(bmsg,(byte)size, (byte)channel);
//                    break;
//                }
//                if(++retries>=previous*2) {
//                    previous = retries;
//                    if(TRACE) System.out.println("["+getMyID()+"] Waiting for packet confirmation ("+retries+")");
//                    if(retries>=MAX_N_TRANSMIT_RETRIES) {
//                        if(TRACE) System.out.println("["+getMyID()+"] Timeout");
//                        packetCounter++;
//                        break;
//                    }
//                }
//                //this.delay(TRANSMIT_DELAY);
//            }
//            if(TRACE) System.out.println("["+getMyID()+"] State transmission operation complete");
        }
        private byte[] lowlevelReceiveMessage(byte[] message, int channel) {
            if(message.length<HEADER_SIZE || (message[0]!=MAGIC_SEND_HEADER && message[0]!=MAGIC_ACK_HEADER)) {
                System.err.println("Illegal packet");
                return message;
            }
            if(message[0]==MAGIC_SEND_HEADER) {
                // Send ack
                byte[] reply = new byte[HEADER_SIZE];
                reply[0] = MAGIC_ACK_HEADER;
                reply[1] = (byte)(message[1]);
                ackQueue.add(new Packet((byte)channel,reply));
                // Have we seen this packet before?
                CacheEntry entry = new CacheEntry(message[2],message[1]);
                if(packetsSeen.contains(entry)) {
                    if(TRACE) System.out.println("["+getMyID()+"] discarding duplicate packet <"+message[2]+","+message[1]+">");
                    return null;
                }
                packetsSeen.add(0, entry);
                if(TRACE) System.out.println("["+getMyID()+"] cache added <"+message[2]+","+message[1]+">");
                if(packetsSeen.size()>CACHE_SIZE) packetsSeen.remove(packetsSeen.size()-1);
                // Extract payload
                byte[] payload = new byte[message.length-HEADER_SIZE];
                for(int i=HEADER_SIZE; i<message.length; i++)
                    payload[i-HEADER_SIZE] = message[i];
                return payload;
            } else {
                synchronized(outbound) {
                    byte counter = message[1];
                    Iterator<Packet> packets = outbound.iterator();
                    while(packets.hasNext()) {
                        Packet p = packets.next();
                        if(p.id==counter) {
                            packets.remove();
                            if(TRACE) System.out.println("["+getMyID()+"] Packet confirmed");
                            return null;
                        }
                    }
                    if(TRACE) System.out.println("["+getMyID()+"] Ignoring confirm");
                    return null;
                }
            }

        }
        
        private void setNorthIOPort(int value) { }

        @Override
        public void activate() {
            EightToCarRobustnessBatch.Parameters p = (EightToCarRobustnessBatch.Parameters)ParameterHolder.get();
            super.setCommFailureRisk(p.minR, p.maxR, p.completeR, p.completeDegree, p.seedMaybe);
            this.yield();
            if(USE_BLOCKING_ROTATE) this.setBlocking(true);
            this.delay(10);
            setup();
            home();
            this.activate_eight2car();
        }

        public void activate_eight2car() {
            initializeState();
            while(eight2car_active)
            {
                super.yield();
                sendAct();
                resetAct();
                if(token[0]!=255 && token[0]!=-1) {
                    System.out.println("Module "+this.getName()+" in state "+token[0]);
                    //delay(500);
                }
                switch(token[0])
                {
                case 0:
                    disconnect_module(0,2);
                    while (!isDisconnected(0)) super.yield();
                    token[0]=1;
                    break;
                case 1:
                    message[0]=0;
                    message[1]=2;
                    message[2]=moduleTranslator[1];
                    token[0]=-1;
                    sendMessage(message,3,4);
                    setNorthIOPort(0);
                    break;
                case 2:
                    message[0]=0;
                    message[1]=3;
                    message[2]=moduleTranslator[3];
                    token[0]=-1;
                    sendMessage(message,3,7);
                    setNorthIOPort(0);
                    break;
                case 3:
                    disconnect_module(4,4);
                    while (!isDisconnected(4)) super.yield();
                    token[0]=4;
                    break;
                case 4:
                    doRotate(-1);
                    //while (!isRotating());
                    //while (isRotating());
                    token[0]=5;
                    break;
                case 5:
                    message[0]=0;
                    message[1]=6;
                    message[2]=moduleTranslator[5];
                    token[0]=-1;
                    sendMessage(message,3,0);
                    setNorthIOPort(0);
                    break;
                case 6:
                    message[0]=0;
                    message[1]=7;
                    message[2]=moduleTranslator[6];
                    token[0]=-1;
                    sendMessage(message,3,CORRECT_CAR_WHEELS?3:5);
                    setNorthIOPort(0);
                    break;
                case 7:
                    message[0]=0;
                    message[1]=8;
                    message[2]=moduleTranslator[4];
                    token[0]=-1;
                    sendMessage(message,3,6);
                    setNorthIOPort(0);
                    break;
                case 8:
                    doRotate(CORRECT_CAR_WHEELS?-1:1);
                    //while (!isRotating());
                    //while (isRotating());
                    token[0]=9;
                    break;
                case 9:
                    connect_module(CORRECT_CAR_WHEELS?6:0,3);
                    while (!isConnected(CORRECT_CAR_WHEELS?6:0)) super.yield();
                    token[0]=10;
                    break;
                case 10:
                    message[0]=0;
                    message[1]=11;
                    message[2]=moduleTranslator[3];
                    token[0]=-1;
                    sendMessage(message,3,CORRECT_CAR_WHEELS?6:0);
                    setNorthIOPort(0);
                    break;
                case 11:
                    message[0]=0;
                    message[1]=12;
                    message[2]=moduleTranslator[1];
                    token[0]=-1;
                    sendMessage(message,3,6);
                    setNorthIOPort(0);
                    break;
                case 12:
                    doRotate(-1);
                    //while (!isRotating());
                    //while (isRotating());
                    token[0]=13;
                    break;
                case 13:
                    message[0]=0;
                    message[1]=14;
                    message[2]=moduleTranslator[3];
                    token[0]=-1;
                    sendMessage(message,3,7);
                    setNorthIOPort(0);
                    break;
                case 14:
                    message[0]=0;
                    message[1]=15;
                    message[2]=moduleTranslator[5];
                    token[0]=-1;
                    sendMessage(message,3,0);
                    setNorthIOPort(0);
                    break;
                case 15:
                    message[0]=0;
                    message[1]=16;
                    message[2]=moduleTranslator[6];
                    token[0]=-1;
                    sendMessage(message,3,CORRECT_CAR_WHEELS?3:5);
                    setNorthIOPort(0);
                    break;
                case 16:
                    disconnect_module(2,5);
                    while (!isDisconnected(2)) super.yield();
                    token[0]=17;
                    break;
                case 17:
                    message[0]=0;
                    message[1]=18;
                    message[2]=moduleTranslator[4];
                    token[0]=-1;
                    sendMessage(message,3,6);
                    setNorthIOPort(0);
                    break;
                case 18:
                    doRotate(1);
                    //while (!isRotating());
                    //while (isRotating());
                    token[0]=19;
                    break;
                case 19:
                    message[0]=0;
                    message[1]=20;
                    message[2]=moduleTranslator[6];
                    token[0]=-1;
                    sendMessage(message,3,CORRECT_CAR_WHEELS?1:7);
                    setNorthIOPort(0);
                    break;
                case 20:
                    doRotate(1);
                    //while (!isRotating());
                    //while (isRotating());
                    token[0]=21;
                    break;
                case 21:
                    message[0]=0;
                    message[1]=22;
                    message[2]=moduleTranslator[4];
                    token[0]=-1;
                    sendMessage(message,3,6);
                    setNorthIOPort(0);
                    break;
                case 22:
                    message[0]=0;
                    message[1]=23;
                    message[2]=moduleTranslator[3];
                    token[0]=-1;
                    sendMessage(message,3,CORRECT_CAR_WHEELS?6:0);
                    setNorthIOPort(0);
                    break;
                case 23:
                    message[0]=0;
                    message[1]=24;
                    message[2]=moduleTranslator[1];
                    token[0]=-1;
                    sendMessage(message,3,6);
                    setNorthIOPort(0);
                    break;
                case 24:
                    message[0]=0;
                    message[1]=25;
                    message[2]=moduleTranslator[0];
                    token[0]=-1;
                    sendMessage(message,3,3);
                    setNorthIOPort(0);
                    break;
                case 25:
                    connect_module(0,6);
                    while (!isConnected(0)) super.yield();
                    token[0]=26;
                    break;
                case 26:
                    message[0]=0;
                    message[1]=27;
                    message[2]=moduleTranslator[6];
                    token[0]=-1;
                    sendMessage(message,3,0);
                    setNorthIOPort(0);
                    break;
                case 27:
                    disconnect_module(6,4);
                    while (!isDisconnected(6)) super.yield();
                    token[0]=28;
                    break;
                case 28:
                    message[0]=0;
                    message[1]=29;
                    message[2]=moduleTranslator[0];
                    token[0]=-1;
                    sendMessage(message,3,3);
                    setNorthIOPort(0);
                    break;
                case 29:
                    doRotate(-1);
                    //while (!isRotating());
                    //while (isRotating());
                    token[0]=30;
                    break;
                case 30:
                    message[0]=0;
                    message[1]=31;
                    message[2]=moduleTranslator[1];
                    token[0]=-1;
                    sendMessage(message,3,4);
                    setNorthIOPort(0);
                    break;
                case 31:
                    doRotate(1);
                    //while (!isRotating());
                    //while (isRotating());
                    token[0]=32;
                    break;
                case 32:
                    message[0]=0;
                    message[1]=33;
                    message[2]=moduleTranslator[0];
                    token[0]=-1;
                    sendMessage(message,3,3);
                    setNorthIOPort(0);
                    break;
                case 33:
                    doRotate(1);
                    //while (!isRotating());
                    //while (isRotating());
                    token[0]=34;
                    break;
                case 34:
                    message[0]=0;
                    message[1]=35;
                    message[2]=moduleTranslator[1];
                    token[0]=-1;
                    sendMessage(message,3,4);
                    setNorthIOPort(0);
                    break;
                case 35:
                    message[0]=0;
                    message[1]=36;
                    message[2]=moduleTranslator[3];
                    token[0]=-1;
                    sendMessage(message,3,7);
                    setNorthIOPort(0);
                    break;
                case 36:
                    message[0]=0;
                    message[1]=37;
                    message[2]=moduleTranslator[5];
                    token[0]=-1;
                    sendMessage(message,3,0);
                    setNorthIOPort(0);
                    break;
                case 37:
                    connect_module(CORRECT_CAR_WHEELS?4:0,6);
                    while (!isConnected(CORRECT_CAR_WHEELS?4:0)) super.yield();
                    token[0]=38;
                    break;
                case 38:
                    message[0]=0;
                    message[1]=39;
                    message[2]=moduleTranslator[3];
                    token[0]=-1;
                    sendMessage(message,3,CORRECT_CAR_WHEELS?7:1);
                    setNorthIOPort(0);
                    break;
                case 39:
                    message[0]=0;
                    message[1]=40;
                    message[2]=moduleTranslator[2];
                    token[0]=-1;
                    sendMessage(message,3,2);
                    setNorthIOPort(0);
                    break;
                case 40:
                    connect_module(4,6);
                    while (!isConnected(4)) super.yield();
                    token[0]=41;
                    break;
                case 41:
                    message[0]=0;
                    message[1]=42;
                    message[2]=moduleTranslator[3];
                    token[0]=-1;
                    sendMessage(message,3,5);
                    setNorthIOPort(0);
                    break;
                case 42:
                    message[0]=0;
                    message[1]=43;
                    message[2]=moduleTranslator[1];
                    token[0]=-1;
                    sendMessage(message,3,6);
                    setNorthIOPort(0);
                    break;
                case 43:
                    connect_module(4,4);
                    while (!isConnected(4)) super.yield();
                    token[0]=44;
                    break;
                case 44:
                    message[0]=0;
                    message[1]=45;
                    message[2]=moduleTranslator[4];
                    token[0]=-1;
                    sendMessage(message,3,4);
                    setNorthIOPort(0);
                    break;
                case 45:
                    disconnect_module(CORRECT_CAR_WHEELS?6:0,3);
                    while (!isDisconnected(CORRECT_CAR_WHEELS?6:0)) super.yield();
                    token[0]=46;
                    break;
                case 46:
                    message[0]=0;
                    message[1]=47;
                    message[2]=moduleTranslator[1];
                    token[0]=-1;
                    sendMessage(message,3,CORRECT_CAR_WHEELS?5:1);
                    setNorthIOPort(0);
                    break;
                case 47:
                    message[0]=0;
                    message[1]=48;
                    message[2]=moduleTranslator[3];
                    token[0]=-1;
                    sendMessage(message,3,7);
                    setNorthIOPort(0);
                    break;
                case 48:
                    disconnect_module(6,1);
                    while (!isDisconnected(6)) super.yield();
                    token[0]=49;
                    break;
                case 49:
                    message[0]=0;
                    message[1]=50;
                    message[2]=moduleTranslator[5];
                    token[0]=-1;
                    sendMessage(message,3,0);
                    setNorthIOPort(0);
                    break;
                case 50:
                    message[0]=0;
                    message[1]=51;
                    message[2]=moduleTranslator[6];
                    token[0]=-1;
                    sendMessage(message,3,CORRECT_CAR_WHEELS?4:0);
                    setNorthIOPort(0);
                    break;
                case 51:
                    message[0]=0;
                    message[1]=52;
                    message[2]=moduleTranslator[0];
                    token[0]=-1;
                    sendMessage(message,3,3);
                    setNorthIOPort(0);
                    break;
                case 52:
                    message[0]=0;
                    message[1]=53;
                    message[2]=moduleTranslator[1];
                    token[0]=-1;
                    sendMessage(message,3,4);
                    setNorthIOPort(0);
                    break;
                case 53:
                    doRotate(1);
                    //while (!isRotating());
                    //while (isRotating());
                    token[0]=54;
                    break;
                case 54:
                    message[0]=0;
                    message[1]=55;
                    message[2]=moduleTranslator[0];
                    token[0]=-1;
                    sendMessage(message,3,3);
                    setNorthIOPort(0);
                    break;
                case 55:
                    message[0]=0;
                    message[1]=56;
                    message[2]=moduleTranslator[6];
                    token[0]=-1;
                    sendMessage(message,3,0);
                    setNorthIOPort(0);
                    break;
                case 56:
                    message[0]=0;
                    message[1]=57;
                    message[2]=moduleTranslator[5];
                    token[0]=-1;
                    sendMessage(message,3,5);
                    setNorthIOPort(0);
                    break;
                case 57:
                    message[0]=0;
                    message[1]=58;
                    message[2]=moduleTranslator[3];
                    token[0]=-1;
                    sendMessage(message,3,CORRECT_CAR_WHEELS?7:1);
                    setNorthIOPort(0);
                    break;
                case 58:
                    doRotate(1);
                    //while (!isRotating());
                    //while (isRotating());
                    token[0]=59;
                    break;
                case 59:
                    message[0]=0;
                    message[1]=60;
                    message[2]=moduleTranslator[5];
                    token[0]=-1;
                    sendMessage(message,3,0);
                    setNorthIOPort(0);
                    break;
                case 60:
                    message[0]=0;
                    message[1]=61;
                    message[2]=moduleTranslator[6];
                    token[0]=-1;
                    sendMessage(message,3,CORRECT_CAR_WHEELS?4:0);
                    setNorthIOPort(0);
                    break;
                case 61:
                    message[0]=0;
                    message[1]=62;
                    message[2]=moduleTranslator[0];
                    token[0]=-1;
                    sendMessage(message,3,3);
                    setNorthIOPort(0);
                    break;
                case 62:
                    message[0]=0;
                    message[1]=63;
                    message[2]=moduleTranslator[1];
                    token[0]=-1;
                    sendMessage(message,3,4);
                    setNorthIOPort(0);
                    break;
                case 63:
                    connect_module(6,3);
                    while (!isConnected(6)) super.yield();
                    token[0]=64;
                    break;
                case 64:
                    message[0]=0;
                    message[1]=65;
                    message[2]=moduleTranslator[3];
                    token[0]=-1;
                    sendMessage(message,3,6);
                    setNorthIOPort(0);
                    break;
                case 65:
                    disconnect_module(0,5);
                    while (!isDisconnected(0)) super.yield();
                    token[0]=66;
                    break;
                case 66:
                    disconnect_module(2,2);
                    while (!isDisconnected(2)) super.yield();
                    token[0]=67;
                    break;
                case 67:
                    message[0]=0;
                    message[1]=68;
                    message[2]=moduleTranslator[1];
                    token[0]=-1;
                    sendMessage(message,3,5);
                    setNorthIOPort(0);
                    break;
                case 68:
                    doRotate(1);
                    //while (!isRotating());
                    //while (isRotating());
                    token[0]=69;
                    break;
                case 69:
                    token[0]=-1;
                    stopEightToCar(new byte[] { 0, 0, 87 }, 3, -1);
                    reportResult(true);
                    break;
                }
            }
        }

        private void resetAct() {
            float time = getTime();
            if(last_reset_check_time+reset_interval>time) return;
            last_reset_check_time = time;
            if(resetRandomizer .nextFloat()<reset_risk) {
                System.err.println("RESET start "+token[0]);
                this.initializeState();
                System.err.println("RESET done "+token[0]);
                reportEvent("RESET",time);
            }
        }

        private void initializeState() {
            int i;
            for (i=0;i<1;i++)
                token[i]=255;
            for(i=0;i<7;i++)
                moduleTranslator[i] = i;
            if(getMyID()==0) {
                token[0] = 0;
            }
        }

        byte other_id;
        
        private void connect_module(int i, int module_id) {
            this.connect(i);
            if(!VERIFY_OPERATIONS) return;
            get_module_id_at(i);
            if(!(module_id==other_id)) throw new Error("foo");
            System.out.println("Module "+this.getMyID()+" connected to connector "+i+" to module "+other_id);
        }

        private void disconnect_module(int i, int module_id) {
            if(VERIFY_OPERATIONS) {
                get_module_id_at(i);
                if(!(module_id==other_id)) throw new Error("bar");
                System.out.println("Module "+this.getMyID()+" disconnecting connector "+i+" to module "+other_id);
            }
            this.disconnect(i);
        }

        private void get_module_id_at(int i) {
            other_id = -1;
            byte[] message = new byte[] { 0, 0, 88 };
            while(other_id==-1) {
                super.sendMessage(message, (byte)3, (byte)i);
                super.delay(1000);
            }
        }

        private void doRotate(int i) {
            this.rotate(i);
            if(!USE_BLOCKING_ROTATE) {
                while (!isRotating()) super.yield();
                while (isRotating()) super.yield();
            }
        }

        protected int getMyID() {
            String id = this.getName().substring(1);
            return Integer.parseInt(id);
        }

        public void handleMessage(byte[] incoming, int messageSize, int channel) {
            incoming = lowlevelReceiveMessage(incoming,channel);
            if(incoming==null) return;
            messageSize = incoming.length;
            if (incoming[2]==getMyID())
            {
                token[incoming[0]]=incoming[1];
                setNorthIOPort(incoming[0]+0xF0);
            } else if(incoming[2]==87) {
                stopEightToCar(incoming,messageSize,channel);
            } else if(incoming[2]==88) {
                if(!VERIFY_OPERATIONS) throw new Error("Illegal packet 88");
                byte[] response = new byte[] { 0, (byte)this.getMyID(), 89 };
                super.sendMessage(response, (byte)3, (byte)channel);
            } else if(incoming[2]==89) {
                if(!VERIFY_OPERATIONS) throw new Error("Illegal packet 89");
                this.other_id = incoming[1];
            } else if(!canHandleMessage(incoming,messageSize,channel)) {
            	System.out.println("Warning: ignorign unknown packet"); System.out.flush();
                //super.sendMessage(incoming,(byte)messageSize,(byte)channel);
            } 

        }

        private void stopEightToCar(byte[] incoming, int messageSize, int channel) {
            this.eight2car_active = false;
            for(int c=0; c<8; c++)
                if(c!=channel && this.isConnected(c)) this.sendMessage(incoming, (byte)messageSize, (byte)c);
        }

        protected boolean canHandleMessage(byte[] incoming, int messageSize, int channel) {
            return false;
        }
    }

}

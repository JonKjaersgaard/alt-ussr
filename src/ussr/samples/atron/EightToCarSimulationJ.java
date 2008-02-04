/**
 * 
 */
package ussr.samples.atron;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class EightToCarSimulationJ extends GenericATRONSimulation {

    public static final boolean USE_BLOCKING_ROTATE = true;
    public static final boolean CORRECT_CAR_WHEELS = true;
    public static final boolean VERIFY_OPERATIONS = false;

    public static void main(String argv[]) {
        new EightToCarSimulationJ().main();
    }

    @Override
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new EightController();
            }
        };
    }

    @Override
    protected void setupPhysicsHook() {
        PhysicsLogger.setDisplayInfo(true);
        PhysicsParameters.get().setMaintainRotationalJointPositions(true);
        PhysicsParameters.get().setRealisticCollision(true);
        PhysicsParameters.get().setPhysicsSimulationStepSize(0.01f); // before: 0.0005f
        PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
    }
    
    protected void changeWorldHook(WorldDescription world) {
        world.setPlaneTexture(WorldDescription.GRID_TEXTURE);
        world.setHasBackgroundScenery(false);
    }

    protected ArrayList<ModulePosition> buildRobot() {
        float Yoffset = 0.25f+2*unit;
        ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
        mPos.add(new ModulePosition("#0", new VectorDescription(0*unit,0*unit-Yoffset,0*unit), rotation_NS_1));
        mPos.add(new ModulePosition("#1", new VectorDescription(1*unit,0*unit-Yoffset,1*unit), rotation_EW));
        mPos.add(new ModulePosition("#2", new VectorDescription(1*unit,0*unit-Yoffset,-1*unit), rotation_EW));
        mPos.add(new ModulePosition("#3", new VectorDescription(2*unit,0*unit-Yoffset,0*unit), rotation_NS_1));
        if(CORRECT_CAR_WHEELS) {
            mPos.add(new ModulePosition("#4", new VectorDescription(3*unit,0*unit-Yoffset,1*unit), rotation_WE));
            mPos.add(new ModulePosition("#5", new VectorDescription(3*unit,0*unit-Yoffset,-1*unit), rotation_WE));
        } else {
            mPos.add(new ModulePosition("#4", new VectorDescription(3*unit,0*unit-Yoffset,1*unit), rotation_EW));
            mPos.add(new ModulePosition("#5", new VectorDescription(3*unit,0*unit-Yoffset,-1*unit), rotation_EW));
        }
        mPos.add(new ModulePosition("#6", new VectorDescription(4*unit,0*unit-Yoffset,0*unit), rotation_NS_1));
        return mPos;
    }

    protected class EightController extends ATRONController {

        int token[] = new int[1];
        int moduleTranslator[] = new int[7];
        int message[] = new int[3];
        boolean eight2car_active = true;
        byte lastConnectorTry = 0;
        int retries = 0;

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
            byte[] bmsg = new byte[message.length];
            for(int i=0;i<message.length;i++)
                bmsg[i] = (byte)message[i];
            super.sendMessage(bmsg,(byte)size, (byte)channel);
        }

        private void setNorthIOPort(int value) { }

        @Override
        public void activate() {
            this.ussrYield();
            if(USE_BLOCKING_ROTATE) this.setBlocking(true);
            this.delay(10);
            setup();
            home();
            this.activate_before_eight2car();
            this.activate_eight2car();
            this.activate_after_eight2car();
        }
        
        public void activate_before_eight2car() { ; }
        public void activate_after_eight2car() { ; }

        public void activate_eight2car() {
            int i;
            for (i=0;i<1;i++)
                token[i]=255;
            for(i=0;i<7;i++)
                moduleTranslator[i] = i;
            if(getMyID()==0) token[0] = 0;
            while(eight2car_active)
            {
                super.ussrYield();
                if(token[0]!=255 && token[0]!=-1)
                    System.out.println("Module "+this.getName()+" in state "+token[0]);
                switch(token[0])
                {
                case 0:
                    disconnect_module(0,2);
                    while (!isDisconnected(0)) super.ussrYield();
                    token[0]=1;
                    break;
                case 1:
                    message[0]=0;
                    message[1]=2;
                    message[2]=moduleTranslator[1];
                    sendMessage(message,3,4);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 2:
                    message[0]=0;
                    message[1]=3;
                    message[2]=moduleTranslator[3];
                    sendMessage(message,3,7);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 3:
                    disconnect_module(4,4);
                    while (!isDisconnected(4)) super.ussrYield();
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
                    sendMessage(message,3,0);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 6:
                    message[0]=0;
                    message[1]=7;
                    message[2]=moduleTranslator[6];
                    sendMessage(message,3,CORRECT_CAR_WHEELS?3:5);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 7:
                    message[0]=0;
                    message[1]=8;
                    message[2]=moduleTranslator[4];
                    sendMessage(message,3,6);
                    token[0]=-1;
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
                    while (!isConnected(CORRECT_CAR_WHEELS?6:0)) super.ussrYield();
                    token[0]=10;
                    break;
                case 10:
                    message[0]=0;
                    message[1]=11;
                    message[2]=moduleTranslator[3];
                    sendMessage(message,3,CORRECT_CAR_WHEELS?6:0);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 11:
                    message[0]=0;
                    message[1]=12;
                    message[2]=moduleTranslator[1];
                    sendMessage(message,3,6);
                    token[0]=-1;
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
                    sendMessage(message,3,7);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 14:
                    message[0]=0;
                    message[1]=15;
                    message[2]=moduleTranslator[5];
                    sendMessage(message,3,0);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 15:
                    message[0]=0;
                    message[1]=16;
                    message[2]=moduleTranslator[6];
                    sendMessage(message,3,CORRECT_CAR_WHEELS?3:5);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 16:
                    disconnect_module(2,5);
                    while (!isDisconnected(2)) super.ussrYield();
                    token[0]=17;
                    break;
                case 17:
                    message[0]=0;
                    message[1]=18;
                    message[2]=moduleTranslator[4];
                    sendMessage(message,3,6);
                    token[0]=-1;
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
                    sendMessage(message,3,CORRECT_CAR_WHEELS?1:7);
                    token[0]=-1;
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
                    sendMessage(message,3,6);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 22:
                    message[0]=0;
                    message[1]=23;
                    message[2]=moduleTranslator[3];
                    sendMessage(message,3,CORRECT_CAR_WHEELS?6:0);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 23:
                    message[0]=0;
                    message[1]=24;
                    message[2]=moduleTranslator[1];
                    sendMessage(message,3,6);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 24:
                    message[0]=0;
                    message[1]=25;
                    message[2]=moduleTranslator[0];
                    sendMessage(message,3,3);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 25:
                    connect_module(0,6);
                    while (!isConnected(0)) super.ussrYield();
                    token[0]=26;
                    break;
                case 26:
                    message[0]=0;
                    message[1]=27;
                    message[2]=moduleTranslator[6];
                    sendMessage(message,3,0);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 27:
                    disconnect_module(6,4);
                    while (!isDisconnected(6)) super.ussrYield();
                    token[0]=28;
                    break;
                case 28:
                    message[0]=0;
                    message[1]=29;
                    message[2]=moduleTranslator[0];
                    sendMessage(message,3,3);
                    token[0]=-1;
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
                    sendMessage(message,3,4);
                    token[0]=-1;
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
                    sendMessage(message,3,3);
                    token[0]=-1;
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
                    sendMessage(message,3,4);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 35:
                    message[0]=0;
                    message[1]=36;
                    message[2]=moduleTranslator[3];
                    sendMessage(message,3,7);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 36:
                    message[0]=0;
                    message[1]=37;
                    message[2]=moduleTranslator[5];
                    sendMessage(message,3,0);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 37:
                    connect_module(CORRECT_CAR_WHEELS?4:0,6);
                    while (!isConnected(CORRECT_CAR_WHEELS?4:0)) super.ussrYield();
                    token[0]=38;
                    break;
                case 38:
                    message[0]=0;
                    message[1]=39;
                    message[2]=moduleTranslator[3];
                    sendMessage(message,3,CORRECT_CAR_WHEELS?7:1);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 39:
                    message[0]=0;
                    message[1]=40;
                    message[2]=moduleTranslator[2];
                    sendMessage(message,3,2);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 40:
                    connect_module(4,6);
                    while (!isConnected(4)) super.ussrYield();
                    token[0]=41;
                    break;
                case 41:
                    message[0]=0;
                    message[1]=42;
                    message[2]=moduleTranslator[3];
                    sendMessage(message,3,5);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 42:
                    message[0]=0;
                    message[1]=43;
                    message[2]=moduleTranslator[1];
                    sendMessage(message,3,6);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 43:
                    connect_module(4,4);
                    while (!isConnected(4)) super.ussrYield();
                    token[0]=44;
                    break;
                case 44:
                    message[0]=0;
                    message[1]=45;
                    message[2]=moduleTranslator[4];
                    sendMessage(message,3,4);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 45:
                    disconnect_module(CORRECT_CAR_WHEELS?6:0,3);
                    while (!isDisconnected(CORRECT_CAR_WHEELS?6:0)) super.ussrYield();
                    token[0]=46;
                    break;
                case 46:
                    message[0]=0;
                    message[1]=47;
                    message[2]=moduleTranslator[1];
                    sendMessage(message,3,CORRECT_CAR_WHEELS?5:1);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 47:
                    message[0]=0;
                    message[1]=48;
                    message[2]=moduleTranslator[3];
                    sendMessage(message,3,7);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 48:
                    disconnect_module(6,1);
                    while (!isDisconnected(6)) super.ussrYield();
                    token[0]=49;
                    break;
                case 49:
                    message[0]=0;
                    message[1]=50;
                    message[2]=moduleTranslator[5];
                    sendMessage(message,3,0);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 50:
                    message[0]=0;
                    message[1]=51;
                    message[2]=moduleTranslator[6];
                    sendMessage(message,3,CORRECT_CAR_WHEELS?4:0);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 51:
                    message[0]=0;
                    message[1]=52;
                    message[2]=moduleTranslator[0];
                    sendMessage(message,3,3);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 52:
                    message[0]=0;
                    message[1]=53;
                    message[2]=moduleTranslator[1];
                    sendMessage(message,3,4);
                    token[0]=-1;
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
                    sendMessage(message,3,3);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 55:
                    message[0]=0;
                    message[1]=56;
                    message[2]=moduleTranslator[6];
                    sendMessage(message,3,0);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 56:
                    message[0]=0;
                    message[1]=57;
                    message[2]=moduleTranslator[5];
                    sendMessage(message,3,5);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 57:
                    message[0]=0;
                    message[1]=58;
                    message[2]=moduleTranslator[3];
                    sendMessage(message,3,CORRECT_CAR_WHEELS?7:1);
                    token[0]=-1;
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
                    sendMessage(message,3,0);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 60:
                    message[0]=0;
                    message[1]=61;
                    message[2]=moduleTranslator[6];
                    sendMessage(message,3,CORRECT_CAR_WHEELS?4:0);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 61:
                    message[0]=0;
                    message[1]=62;
                    message[2]=moduleTranslator[0];
                    sendMessage(message,3,3);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 62:
                    message[0]=0;
                    message[1]=63;
                    message[2]=moduleTranslator[1];
                    sendMessage(message,3,4);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 63:
                    connect_module(6,3);
                    while (!isConnected(6)) super.ussrYield();
                    token[0]=64;
                    break;
                case 64:
                    message[0]=0;
                    message[1]=65;
                    message[2]=moduleTranslator[3];
                    sendMessage(message,3,6);
                    token[0]=-1;
                    setNorthIOPort(0);
                    break;
                case 65:
                    disconnect_module(0,5);
                    while (!isDisconnected(0)) super.ussrYield();
                    token[0]=66;
                    break;
                case 66:
                    disconnect_module(2,2);
                    while (!isDisconnected(2)) super.ussrYield();
                    token[0]=67;
                    break;
                case 67:
                    message[0]=0;
                    message[1]=68;
                    message[2]=moduleTranslator[1];
                    sendMessage(message,3,5);
                    token[0]=-1;
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
                    break;
                }
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
                while (!isRotating()) super.ussrYield();
                while (isRotating()) super.ussrYield();
            }
        }

        protected int getMyID() {
            String id = this.getName().substring(1);
            return Integer.parseInt(id);
        }

        public void handleMessage(byte[] incoming, int messageSize, int channel) {
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
            	System.out.println("ERROR?"); System.out.flush();
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

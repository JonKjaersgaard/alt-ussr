/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package robustReversible;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import robustReversible.gen.snakeGen_seq;

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
public class SnakeRobustnessExperimentSafeToken extends EightToCarRobustnessExperimentSafeToken {

    public SnakeRobustnessExperimentSafeToken(int maxValue) {
        super(maxValue);
    }

    @Override
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new SnakeController(3);
            }
        };
    }
    
    protected class SnakeController extends EightController {
        public SnakeController(int initialModuleID) {
            super(initialModuleID);
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
                }
                switch(token[0])
                {
                case 0: // M3
                    disconnect_module(0,-1);
                    while (!isDisconnected(0)) super.yield();
                    disconnect_module(6,-1);
                    while (!isDisconnected(6)) super.yield();
                    message[0]=0;
                    message[1]=1;
                    message[2]=moduleTranslator[2];
                    token[0]=-1;
                    sendMessage(message,3,2);
                    break;
                case 1: // M2
                    message[0]=0;
                    message[1]=2;
                    message[2]=moduleTranslator[0];
                    token[0]=-1;
                    sendMessage(message,3,1);
                    break;
                case 2: // M0
                    doRotate(1);
                    doRotate(1);
                    message[0]=0;
                    message[1]=3;
                    message[2]=moduleTranslator[2];
                    token[0]=-1;
                    sendMessage(message,3,0);
                    break;
                case 3: // M2
                    message[0]=0;
                    message[1]=4;
                    message[2]=moduleTranslator[3];
                    token[0]=-1;
                    sendMessage(message,3,5);
                    break;
                case 4: // M3
                    message[0]=0;
                    message[1]=5;
                    message[2]=moduleTranslator[4];
                    token[0]=-1;
                    sendMessage(message,3,4);
                    break;
                case 5: // M4
                    message[0]=0;
                    message[1]=6;
                    message[2]=moduleTranslator[6];
                    token[0]=-1;
                    sendMessage(message,3,7);
                    break;
                case 6: // M6
                    doRotate(1);
                    doRotate(1);
                    doRotate(-1);
                    doRotate(-1);
                    message[0]=0;
                    message[1]=7;
                    message[2]=moduleTranslator[4];
                    token[0]=-1;
                    sendMessage(message,3,6);
                case 7: // M4
                    message[0]=0;
                    message[1]=8;
                    message[2]=moduleTranslator[3];
                    token[0]=-1;
                    sendMessage(message,3,3);
                    break;
                case 8: // M3
                    message[0]=0;
                    message[1]=9;
                    message[2]=moduleTranslator[2];
                    token[0]=-1;
                    sendMessage(message,3,2);
                    break;
                case 9: // M2
                    message[0]=0;
                    message[1]=10;
                    message[2]=moduleTranslator[0];
                    token[0]=-1;
                    sendMessage(message,3,1);
                    break;
                case 10: // M0
                    doRotate(-1);
                    doRotate(-1);
                    message[0]=0;
                    message[1]=11;
                    message[2]=moduleTranslator[2];
                    token[0]=-1;
                    sendMessage(message,3,0);
                    break;
                case 11: // M2
                    message[0]=0;
                    message[1]=12;
                    message[2]=moduleTranslator[3];
                    token[0]=-1;
                    sendMessage(message,3,5);
                    break;
                case 12: // M3
                    connect_module(0,-1);
                    while (!isConnected(0)) super.yield();
                    connect_module(6,-1);
                    while (!isConnected(6)) super.yield();
                case 13: // M3
                    disconnect_module(0,-1);
                    while (!isDisconnected(0)) super.yield();
                    disconnect_module(6,-1);
                    while (!isDisconnected(6)) super.yield();
                    message[0]=0;
                    message[1]=14;
                    message[2]=moduleTranslator[2];
                    token[0]=-1;
                    sendMessage(message,3,2);
                    break;
                case 14: // M2
                    message[0]=0;
                    message[1]=15;
                    message[2]=moduleTranslator[0];
                    token[0]=-1;
                    sendMessage(message,3,1);
                    break;
                case 15: // M0
                    doRotate(1);
                    doRotate(1);
                    message[0]=0;
                    message[1]=16;
                    message[2]=moduleTranslator[2];
                    token[0]=-1;
                    sendMessage(message,3,0);
                    break;
                case 16: // M2
                    message[0]=0;
                    message[1]=17;
                    message[2]=moduleTranslator[3];
                    token[0]=-1;
                    sendMessage(message,3,5);
                    break;
                case 17: // M3
                    message[0]=0;
                    message[1]=18;
                    message[2]=moduleTranslator[4];
                    token[0]=-1;
                    sendMessage(message,3,4);
                    break;
                case 18: // M4
                    message[0]=0;
                    message[1]=19;
                    message[2]=moduleTranslator[6];
                    token[0]=-1;
                    sendMessage(message,3,7);
                    break;
                case 19: // M6
                    doRotate(1);
                    doRotate(1);
                    doRotate(-1);
                    doRotate(-1);
                    message[0]=0;
                    message[1]=20;
                    message[2]=moduleTranslator[4];
                    token[0]=-1;
                    sendMessage(message,3,6);
                case 20: // M4
                    message[0]=0;
                    message[1]=21;
                    message[2]=moduleTranslator[3];
                    token[0]=-1;
                    sendMessage(message,3,3);
                    break;
                case 21: // M3
                    message[0]=0;
                    message[1]=22;
                    message[2]=moduleTranslator[2];
                    token[0]=-1;
                    sendMessage(message,3,2);
                    break;
                case 22: // M2
                    message[0]=0;
                    message[1]=23;
                    message[2]=moduleTranslator[0];
                    token[0]=-1;
                    sendMessage(message,3,1);
                    break;
                case 23: // M0
                    doRotate(-1);
                    doRotate(-1);
                    message[0]=0;
                    message[1]=24;
                    message[2]=moduleTranslator[2];
                    token[0]=-1;
                    sendMessage(message,3,0);
                    break;
                case 24: // M2
                    message[0]=0;
                    message[1]=25;
                    message[2]=moduleTranslator[3];
                    token[0]=-1;
                    sendMessage(message,3,5);
                    break;
                case 25: // M3
                    connect_module(0,-1);
                    while (!isConnected(0)) super.yield();
                    connect_module(6,-1);
                    while (!isConnected(6)) super.yield();
                case 26: // M3
                    disconnect_module(0,-1);
                    while (!isDisconnected(0)) super.yield();
                    disconnect_module(6,-1);
                    while (!isDisconnected(6)) super.yield();
                    message[0]=0;
                    message[1]=27;
                    message[2]=moduleTranslator[2];
                    token[0]=-1;
                    sendMessage(message,3,2);
                    break;
                case 27: // M2
                    message[0]=0;
                    message[1]=28;
                    message[2]=moduleTranslator[0];
                    token[0]=-1;
                    sendMessage(message,3,1);
                    break;
                case 28: // M0
                    doRotate(1);
                    doRotate(1);
                    message[0]=0;
                    message[1]=29;
                    message[2]=moduleTranslator[2];
                    token[0]=-1;
                    sendMessage(message,3,0);
                    break;
                case 29: // M2
                    message[0]=0;
                    message[1]=30;
                    message[2]=moduleTranslator[3];
                    token[0]=-1;
                    sendMessage(message,3,5);
                    break;
                case 30: // M3
                    message[0]=0;
                    message[1]=31;
                    message[2]=moduleTranslator[4];
                    token[0]=-1;
                    sendMessage(message,3,4);
                    break;
                case 31: // M4
                    message[0]=0;
                    message[1]=32;
                    message[2]=moduleTranslator[6];
                    token[0]=-1;
                    sendMessage(message,3,7);
                    break;
                case 32: // M6
                    doRotate(1);
                    doRotate(1);
                    doRotate(-1);
                    doRotate(-1);
                    message[0]=0;
                    message[1]=33;
                    message[2]=moduleTranslator[4];
                    token[0]=-1;
                    sendMessage(message,3,6);
                case 33: // M4
                    message[0]=0;
                    message[1]=34;
                    message[2]=moduleTranslator[3];
                    token[0]=-1;
                    sendMessage(message,3,3);
                    break;
                case 34: // M3
                    message[0]=0;
                    message[1]=35;
                    message[2]=moduleTranslator[2];
                    token[0]=-1;
                    sendMessage(message,3,2);
                    break;
                case 35: // M2
                    message[0]=0;
                    message[1]=36;
                    message[2]=moduleTranslator[0];
                    token[0]=-1;
                    sendMessage(message,3,1);
                    break;
                case 36: // M0
                    doRotate(-1);
                    doRotate(-1);
                    message[0]=0;
                    message[1]=37;
                    message[2]=moduleTranslator[2];
                    token[0]=-1;
                    sendMessage(message,3,0);
                    break;
                case 37: // M2
                    message[0]=0;
                    message[1]=38;
                    message[2]=moduleTranslator[3];
                    token[0]=-1;
                    sendMessage(message,3,5);
                    break;
                case 38: // M3
                    connect_module(0,-1);
                    while (!isConnected(0)) super.yield();
                    connect_module(6,-1);
                    while (!isConnected(6)) super.yield();
                case 69:
                    token[0]=-1;
                    stopEightToCar(new byte[] { 0, 0, 87 }, 3, -1);
                    reportResult(true);
                    break;
                }
            }
        }

    }

}

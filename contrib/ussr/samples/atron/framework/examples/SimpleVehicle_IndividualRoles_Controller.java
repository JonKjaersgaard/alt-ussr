/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.framework.examples;

import ussr.samples.atron.ATRONController;
import ussr.samples.atron.framework.ATRONFramework;
import ussr.samples.atron.framework.Connection;
import ussr.samples.atron.framework.annotations.Behavior;
import ussr.samples.atron.framework.annotations.Handler;
import ussr.samples.atron.framework.annotations.RemoteRole;
import ussr.samples.atron.framework.annotations.Require;
import ussr.samples.atron.framework.annotations.Startup;
import ussr.samples.atron.framework.util.Action;

/**
 * A controller for a two-wheeler ATRON robot
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class SimpleVehicle_IndividualRoles_Controller extends ATRONFramework {
    
    static final int REVERSE_TIME = 2;
    
    abstract class CarModule extends Role {
        @Startup void initialize() { self.setup(); }
    }
    
    class Driver extends CarModule {
        private Connection<IWheel> wheels = connectedTo(IWheel.class);
        private boolean reversing = false;
        @Require public boolean nConnections() { return self.getNumberOfConnections()==2; }
        @Startup public void initialize() { 
            super.initialize(); 
            self.getSensors().get(1).setSensitivity(0.5f); 
            self.getSensors().get(3).setSensitivity(0.5f);
        }
        @Handler(proximity={1,3}) public void isObstacle() {
            if(!reversing) {
                System.out.println("Head start reverse");
                wheels.getAll().reverse(REVERSE_TIME);
                reversing = true;
                schedule(REVERSE_TIME,new Action() { public void action() { reversing = false; } });
            }
        }
    }

    interface IWheel extends RemoteRole {
        public void reverse(int time);
    }
    
    abstract class Wheel extends CarModule implements IWheel {
        private String identity;
        private float forwardSpeed, reverseSpeed, currentSpeed;
        @Require public boolean name() { return self.getName().contains(identity); }
        protected Wheel(String _identity, float _forwardSpeed, float _reverseSpeed) { 
            this.identity = _identity; this.forwardSpeed = _forwardSpeed; this.reverseSpeed = _reverseSpeed;
            currentSpeed = forwardSpeed;
        }
        @Behavior public void drive() { self.rotateContinuous(currentSpeed); }
        public void reverse(int time) { 
            System.out.println("Reverse start "+this.getName());
            currentSpeed = -reverseSpeed;
            schedule(time,new Action() { public void action() { System.out.println("Reverse stop "+getName()); currentSpeed = forwardSpeed; } });
        }
    }
    
    class LeftWheel extends Wheel {
        LeftWheel() { super("Left",1,0.5f); }
    }
    
    class RightWheel extends Wheel {
        RightWheel() { super("Right",-1,0.7f); }
    }
    
    public Role[] getRoles() {
        return new Role[] { new Driver(), new LeftWheel(), new RightWheel() };
    }
}
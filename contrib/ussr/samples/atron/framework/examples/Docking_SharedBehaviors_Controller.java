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
import ussr.samples.atron.framework.distributed.Collaboration;
import ussr.samples.atron.framework.distributed.Element;
import ussr.samples.atron.framework.distributed.Script;
import ussr.samples.atron.framework.util.Action;

/**
 * A controller for a two-wheeler ATRON robot
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class Docking_SharedBehaviors_Controller extends ATRONFramework {
    
    static final int REVERSE_TIME = 2;
    
    public class EvasionBehavior extends Collaboration {
        private ModuleSelector<CarModule> car;
        public final Script searchingBehavior = makeAtomicScript(
                    car.all(IWheel.class).forwards(),
                    car.some(Driver.class).IF(self(Driver.class).inDockingPosition(),
                            seq(
                                    car.all(IWheel.class).dockingMotion(),
                                    par(
                                            car.all(Driver.class).connectorClose(1),
                                            car.all(Driver.class).connectorClose(3))))
                                    
                                    
                    );
    }

    public abstract class CarModule extends Role {
        @Startup void initialize() { self.setup(); }
    }
    
    public class Driver extends CarModule {
        EvasionBehavior evasion = getBehavior(EvasionBehavior.class);
        @Require public boolean nConnections() { return self.getNumberOfConnections()==2; }
        public Element<?> connectorClose(int i) {
            // TODO Auto-generated method stub
            // return null;
            throw new Error("Method not implemented");
        }
        public Element<Boolean> inDockingPosition() {
            // TODO Auto-generated method stub
            // return null;
            throw new Error("Method not implemented");
        }
        @Startup public void initialize() { 
            super.initialize(); 
            self.getSensors().get(1).setSensitivity(0.5f); 
            self.getSensors().get(3).setSensitivity(0.5f);
        }
        @Handler(proximity={1,3}) public void isObstacle() { evasion.searchingBehavior.run(); }
    }

    public interface IWheel extends RemoteRole {
        public Element<Void> reverse();
        public Element<?> dockingMotion();
        public Element<Void> forwards();
    }
    
    public abstract class Wheel extends CarModule implements IWheel {
        private String identity;
        private float forwardSpeed, reverseSpeed, currentSpeed;
        @Require public boolean name() { return self.getName().contains(identity); }
        protected Wheel(String _identity, float _forwardSpeed, float _reverseSpeed) { 
            this.identity = _identity; this.forwardSpeed = _forwardSpeed; this.reverseSpeed = _reverseSpeed;
            forwards();
        }
        @Behavior public void drive() { self.rotateContinuous(currentSpeed); }
        public Element<Void> reverse() { 
            currentSpeed = -reverseSpeed;
            return Element.CONTINUED;
        }
        public Element<Void> forwards() {
            currentSpeed = forwardSpeed;
            return Element.CONTINUED;
        }
        public Element<Void> dockingMotion() {
            throw new Error();
        }
    }
    
    public class LeftWheel extends Wheel {
        LeftWheel() { super("Left",1,0.5f); }
    }
    
    public class RightWheel extends Wheel {
        RightWheel() { super("Right",-1,0.7f); }
    }
    
    public Role[] getRoles() {
        return new Role[] { new Driver(), new LeftWheel(), new RightWheel() };
    }
}
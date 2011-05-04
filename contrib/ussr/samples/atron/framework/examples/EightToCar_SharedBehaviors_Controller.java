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
public class EightToCar_SharedBehaviors_Controller extends ATRONFramework {
    
    public class EightToCar extends Collaboration {
        private ModuleSelector<Role> m;
        public final Script script = makeAtomicScript(
                par(m.get(0).connectorOpen(0),
                    m.get(3).connectorOpen(4)),
                m.get(3).rotateFromToBy(0, 324, false, 150),
                m.get(4).rotateFromToBy(0,108,true,150),
                sleep(4),
                m.getAny(2,5).IF(m.isAlive(4),
                    m.get(4).rotateFromToBy(0, 0, false, 0)),
                m.getAll(2,5).WHILE(m.get(2).noProximity(3),
                    m.get(3).rotateFromToBy(1, 1, true, 1)));
    }
    
    public abstract class CarModule extends Role {
        @Startup void initialize() { self.setup(); }
    }

    @Override
    protected Role[] getRoles() {
        throw new Error("what?");
    }
    
}
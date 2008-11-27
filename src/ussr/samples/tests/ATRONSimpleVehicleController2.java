/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.tests;

import java.awt.Color;
import java.util.List;

import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.model.Module;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMESimulation;
import ussr.samples.atron.ATRONController;

/**
 * A controller for a two-wheeler ATRON robot
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONSimpleVehicleController2 extends ATRONController {
	private int i;
    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        yield();
        byte dir = 1;
        while(true) {
            String name = module.getProperty("name");
            if(name=="RearRightWheel") rotateContinuous(dir);
            else if(name=="RearLeftWheel") rotateContinuous(-dir);
            else if(!name.startsWith("Dyn")){
                try {
                    Thread.sleep(1000);
                } catch(InterruptedException exn) {
                    ;
                }
                PhysicsSimulation sim = this.module.getSimulation();
                JMESimulation js = (JMESimulation) sim;
                VectorDescription pos = this.module.getPhysics().get(0).getPosition();
                pos.add(0, 2, 0);
                RotationDescription rot = this.module.getPhysics().get(0).getRotation();
                ModulePosition p = new ModulePosition("Dyn#"+i,pos,rot);
                Module m = js.createModule(p,true);
                m.setColorList(this.module.getColorList());
            }
            yield();
        }
    }
}

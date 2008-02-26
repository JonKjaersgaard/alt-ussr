package ussr.samples.atron.natives;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.physics.PhysicsSimulation;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;
import ussr.samples.atron.ATRON;

public class DcdTwoCarSimulation extends ATRONNativeCarSimulation {
    
    public static void main(String argv[]) {
        new DcdTwoCarSimulation().main();
    }

    protected void changeWorldHook(WorldDescription world) {
        super.changeWorldHook(world);
        //world.setFlatWorld(false);
        //world.setPlaneTexture(WorldDescription.MARS_TEXTURE);
    }

/*    @Override
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new ATRONNativeController("dcdController");
            }
        };
    }*/
    
    protected void simulationHook(PhysicsSimulation simulation) {
        ATRON zuper = new ATRON() {
            public Controller createController() {
                return new ATRONNativeController("dcdController");
            }
        };
        zuper.setSuper();
        simulation.setRobot(zuper, "ATRON super");
    }
    
    static VectorDescription pos(float x, float y, float z) {
        final float Yoffset = 0.25f;
        return new VectorDescription(x*ATRON.UNIT, y*ATRON.UNIT-Yoffset, z*ATRON.UNIT);
    }
    
    protected ArrayList<ModulePosition> buildCar() {
        ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
        mPos.add(new ModulePosition("driver0", pos(0,0,0), ATRON.ROTATION_EW));
        mPos.add(new ModulePosition("driver91", pos(-2,0,0), ATRON.ROTATION_EW));
        mPos.add(new ModulePosition("driverExtra0", pos(0,0,4), ATRON.ROTATION_EW));
        //mPos.add(new WorldDescription.ModulePosition("driverExtra93", pos(-2,0,4), rotation_EW));
        mPos.add(new ModulePosition("axleOne11", pos(1,-1,0), ATRON.ROTATION_UD));
        mPos.add(new ModulePosition("axleTwo12", pos(-1,-1,0), ATRON.ROTATION_UD));
        mPos.add(new ModulePosition("axleThree13", pos(-3,-1,0), ATRON.ROTATION_UD));
        mPos.add(new ModulePosition("axleFour14", pos(1,-1,4), ATRON.ROTATION_UD));
        mPos.add(new ModulePosition("axleFive15", pos(-1,-1,4), ATRON.ROTATION_UD));
        //mPos.add(new WorldDescription.ModulePosition("axleSix16", pos(-3,-1,4), rotation_UD));
        mPos.add(new ModulePosition("wheel1", pos(-1,-2,1), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("wheel2", pos(-1,-2,-1), ATRON.ROTATION_NS));
        mPos.add(new ModulePosition("wheel3", pos(1,-2,1), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("wheel4", pos(1,-2,-1), ATRON.ROTATION_NS));
        mPos.add(new ModulePosition("wheel5", pos(-3,-2,1), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("wheel6", pos(-3,-2,-1), ATRON.ROTATION_NS));
        mPos.add(new ModulePosition("wheel31", pos(-1,-2,3), ATRON.ROTATION_NS));
        mPos.add(new ModulePosition("wheel32", pos(-1,-2,5), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("wheel33", pos(1,-2,3), ATRON.ROTATION_NS));
        mPos.add(new ModulePosition("wheel34", pos(1,-2,5), ATRON.ROTATION_SN));
        //mPos.add(new WorldDescription.ModulePosition("wheel35", pos(-3,-2,3), rotation_NS));
        //mPos.add(new WorldDescription.ModulePosition("wheel36", pos(-3,-2,5), rotation_SN));
        //mPos.add(new WorldDescription.ModulePosition("connectOne21", "ATRON super", pos(-1,0,1), rotation_SN));
        //mPos.add(new WorldDescription.ModulePosition("connectTwo22", "ATRON super", pos(-1,0,3), rotation_NS));
        //mPos.add(new WorldDescription.ModulePosition("connectThree23", "ATRON super", pos(0,0,2), rotation_EW));
        return mPos;
    }

}

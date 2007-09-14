package ussr.samples.atron.natives;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;
import ussr.robotbuildingblocks.WorldDescription.ModulePosition;
import ussr.samples.atron.ATRON;

public class ATRONDcdCyclicCarSimulation extends ATRONNativeCarSimulation {
    
    public static void main(String argv[]) {
        new ATRONDcdCyclicCarSimulation().main();
    }

    @Override
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new ATRONNativeController("dcdController");
            }
        };
    }
    
    static VectorDescription pos(float x, float y, float z) {
        final float Yoffset = 0.25f;
        return new VectorDescription(x*unit, y*unit-Yoffset, z*unit);
    }
    
    protected ArrayList<ModulePosition> buildCar() {
        ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
        mPos.add(new WorldDescription.ModulePosition("driver0", pos(0,0,0), rotation_EW));
        mPos.add(new WorldDescription.ModulePosition("axleOne", pos(1,-1,0), rotation_UD));
        mPos.add(new WorldDescription.ModulePosition("axleTwo", pos(-1,-1,0), rotation_UD));
        mPos.add(new WorldDescription.ModulePosition("wheel1", pos(-1,-2,1), rotation_SN));
        mPos.add(new WorldDescription.ModulePosition("wheel2", pos(-1,-2,-1), rotation_NS));
        mPos.add(new WorldDescription.ModulePosition("wheel3", pos(1,-2,1), rotation_SN));
        mPos.add(new WorldDescription.ModulePosition("wheel4", pos(1,-2,-1), rotation_NS));
        mPos.add(new WorldDescription.ModulePosition("spareTyre", pos(-1,0,1), rotation_SN));
        return mPos;
    }

}

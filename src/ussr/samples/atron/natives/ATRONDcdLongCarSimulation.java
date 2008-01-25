package ussr.samples.atron.natives;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.samples.atron.ATRON;

public class ATRONDcdLongCarSimulation extends ATRONNativeCarSimulation {
    
    public static void main(String argv[]) {
        ATRONDcdLongCarSimulation simulation = new ATRONDcdLongCarSimulation();
        simulation.setObstableType(ObstacleType.CIRCLE);
        simulation.main();
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
        mPos.add(new ModulePosition("driver0", pos(0,0,0), rotation_EW));
        mPos.add(new ModulePosition("driverExtra10", pos(-2,0,0), rotation_EW));
        mPos.add(new ModulePosition("axleOne11", pos(1,-1,0), rotation_UD));
        mPos.add(new ModulePosition("axleTwo12", pos(-1,-1,0), rotation_UD));
        mPos.add(new ModulePosition("axleThree13", pos(-3,-1,0), rotation_UD));
        mPos.add(new ModulePosition("--wheel1", pos(-1,-2,1), rotation_SN));
        mPos.add(new ModulePosition("--wheel2", pos(-1,-2,-1), rotation_NS));
        mPos.add(new ModulePosition("--wheel3", pos(1,-2,1), rotation_SN));
        mPos.add(new ModulePosition("--wheel4", pos(1,-2,-1), rotation_NS));
        mPos.add(new ModulePosition("--wheel5", pos(-3,-2,1), rotation_SN));
        mPos.add(new ModulePosition("--wheel6", pos(-3,-2,-1), rotation_NS));
        return mPos;
    }

}

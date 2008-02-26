package ussr.samples.atron;

import java.util.ArrayList;

import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.VectorDescription;

public class ATRONBuilder {

    public ArrayList<ModulePosition> buildCar(int numberOfWheels, VectorDescription position) {
        float Yoffset = position.getY();
        ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
        mPos.add(new ModulePosition("driver0", new VectorDescription(2*0*ATRON.UNIT,0*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
        mPos.add(new ModulePosition("axleOne5", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_UD));
        mPos.add(new ModulePosition("axleTwo6", new VectorDescription(-1*ATRON.UNIT,-1*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_UD));
        mPos.add(new ModulePosition("wheel1", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("wheel2", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
        mPos.add(new ModulePosition("wheel3", new VectorDescription(1*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("wheel4", new VectorDescription(1*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
        return mPos;
    }

}

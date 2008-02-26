package ussr.samples.atron;

import java.util.ArrayList;
import java.util.Random;

import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;

public class ATRONBuilder {

    public interface Namer {
        public String name(int number, VectorDescription pos, RotationDescription rot);
    }

    public interface ModuleSelector {
        String select(String name, int index, VectorDescription pos, RotationDescription rot);
    }

    public ArrayList<ModulePosition> buildCar(int numberOfWheels, VectorDescription position) {
        float Yoffset = position.getY();
        ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
        if(numberOfWheels==4) {
            mPos.add(new ModulePosition("driver0", new VectorDescription(2*0*ATRON.UNIT,0*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
            mPos.add(new ModulePosition("axleOne5", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_UD));
            mPos.add(new ModulePosition("axleTwo6", new VectorDescription(-1*ATRON.UNIT,-1*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_UD));
            mPos.add(new ModulePosition("wheel1", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
            mPos.add(new ModulePosition("wheel2", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
            mPos.add(new ModulePosition("wheel3", new VectorDescription(1*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
            mPos.add(new ModulePosition("wheel4", new VectorDescription(1*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
        } else
            throw new Error("Not implemented yet");
        return mPos;
    }

    public ArrayList<ModulePosition> buildAsLattice(int nModules, int xMax, int yMax, int zMax) {
        return this.buildAsNamedLattice(nModules, xMax, yMax, zMax, new Namer() {
            public String name(int number, VectorDescription pos, RotationDescription rot) {
                return "module"+Integer.toString(number);
            }
                
        }, new ModuleSelector() {
            public String select(String name, int index, VectorDescription pos, RotationDescription rot) {
                return null;
            }
        }, ATRON.UNIT);
    }
    
    public ArrayList<ModulePosition> buildAsNamedLattice(int nModules, int xMax, int yMax, int zMax, Namer namer, ModuleSelector selector, float placement_unit) {
        ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
        int index=0;
        for(int x=0;x<xMax;x++) {
            for(int y=0;y<yMax;y++) {
                for(int z=0;z<zMax;z++) {
                    VectorDescription pos = null;
                    RotationDescription rot = ATRON.ROTATION_NS;
                    if(y%2==0&&z%2==0) {
                        pos = new VectorDescription(2*x*placement_unit,y*placement_unit,z*placement_unit);
                        rot = ATRON.ROTATION_EW;
                    }
                    else if(y%2==0&&z%2==1)  {
                        pos = new VectorDescription(2*x*placement_unit+placement_unit,y*placement_unit,z*placement_unit);
                        rot = ATRON.ROTATION_NS;
                    }
                    else if(y%2==1&&z%2==0) {
                        pos = new VectorDescription(2*x*placement_unit+placement_unit,y*placement_unit,z*placement_unit);
                        rot = ATRON.ROTATION_UD;
                    }
                    else if(y%2==1&&z%2==1) {
                        pos = new VectorDescription(2*x*placement_unit,y*placement_unit,z*placement_unit);
                        rot = ATRON.ROTATION_NS;
                    }
                    if(index<nModules) {
                        String name = namer.name(index,pos,rot);
                        String robotNameMaybe = selector.select(name,index,pos,rot);
                        ModulePosition mpos;
                        if(robotNameMaybe==null)
                            mpos = new ModulePosition(name, pos, rot);
                        else
                            mpos = new ModulePosition(name, robotNameMaybe, pos, rot);
                        mPos.add(mpos);
                    }
                    index++;
                }
            }
        }
        return mPos;
    }
    public ArrayList<ModulePosition> randomStructure(int nModules) {
        if(true) throw new Error("Borken method");
        ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
        int index=0;
        Random rand = new Random(1234); 
        while(index<nModules) {
            int x = (index==0)?0:rand.nextInt()%10;
            int y = (index==0)?0:rand.nextInt()%10;
            int z = (index==0)?0:rand.nextInt()%10;
            VectorDescription pos = null;
            RotationDescription rot = ATRON.ROTATION_NS;
            if(y%2==0&&z%2==0) {
                pos = new VectorDescription(2*x*ATRON.UNIT,y*ATRON.UNIT,z*ATRON.UNIT);
                rot = ATRON.ROTATION_EW;
            }
            else if(y%2==0&&z%2==1)  {
                pos = new VectorDescription(2*x*ATRON.UNIT+ATRON.UNIT,y*ATRON.UNIT,z*ATRON.UNIT);
                rot = ATRON.ROTATION_NS;
            }
            else if(y%2==1&&z%2==0) {
                pos = new VectorDescription(2*x*ATRON.UNIT+ATRON.UNIT,y*ATRON.UNIT,z*ATRON.UNIT);
                rot = ATRON.ROTATION_UD;
            }
            else if(y%2==1&&z%2==1) {
                pos = new VectorDescription(2*x*ATRON.UNIT,y*ATRON.UNIT,z*ATRON.UNIT);
                rot = ATRON.ROTATION_NS;
            }
            if(pos!=null&&(index==0||isConnectable(new ModulePosition("",pos,rot),mPos)&&empty(pos,mPos))) {
                mPos.add(new ModulePosition(Integer.toString(index), pos, rot));
                index++;
            }
        }
        return mPos;
    }

    private static boolean empty(VectorDescription pos, ArrayList<ModulePosition> allPos) {
        for(ModulePosition p: allPos) {
            if(pos.distance(p.getPosition())<0.001)
                return false;
        }
        return true;
    }

    private static boolean isConnectable(ModulePosition pos, ArrayList<ModulePosition> allPos) {
        /*for(ModulePosition p: allPos) {
            if(isConnectable(pos, p))
                return true;
        }*/
        return false;
    }

}

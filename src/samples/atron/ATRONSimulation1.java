/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package samples.atron;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;
import ussr.robotbuildingblocks.WorldDescription.Connection;
import ussr.robotbuildingblocks.WorldDescription.ModulePosition;
import ussr.samples.GenericSimulation;

/**
 * A simple simulation with the sticky bot, using the key "Z" to globally toggle stickiness
 * of the connectors.
 * 
 * @author ups
 *
 */
public class ATRONSimulation1 extends GenericSimulation {
	
	private static float half = (float)(Math.PI);
	private static float quart = (float)(0.5*Math.PI);
	private static float eigth = (float)(0.25*Math.PI);
	
	private static float unit = 0.08f;//8 cm between two lattice positions on physical atrons
	private static RotationDescription rotation_NS = new RotationDescription(0,0,eigth);
	private static RotationDescription rotation_SN = new RotationDescription(0,half,eigth);
	private static RotationDescription rotation_UD = new RotationDescription(quart,eigth,0);
	private static RotationDescription rotation_EW = new RotationDescription(new VectorDescription(eigth,0,0),new VectorDescription(0,quart,0));
	
    public static void main( String[] args ) {
        new ATRONSimulation1().main();
    }

    public void main() {
        setConnectorsAreActive(true);
        WorldDescription world = new WorldDescription();
        world.setPlaneSize(5);
        ArrayList<WorldDescription.ModulePosition> modulePos;
        //modulePos = buildAsLattice(5,2,4,1);
        modulePos = buildCar();
       // modulePos = buildSnake(2);
        
        world.setModulePositions(modulePos);
        
        ArrayList<Connection> connections = allConnections(modulePos);
        world.setModuleConnections(connections);
        
        
        /*world.setModulePositions(new WorldDescription.ModulePosition[] {
        new WorldDescription.ModulePosition("leftleg",new VectorDescription(0,0,0), rotation_EW),
        new WorldDescription.ModulePosition("middle",new VectorDescription(unit,unit,0), rotation_UD),
        new WorldDescription.ModulePosition("rightleg",new VectorDescription(2*unit,2*unit,0), rotation_EW),
        new WorldDescription.ModulePosition("rightleg",new VectorDescription(4*unit,2*unit,0), rotation_EW),
 		});*/
        /*world.setModuleConnections(new WorldDescription.Connection[] {
              //  new WorldDescription.Connection("leftleg",4,"middle",6)
                //,new WorldDescription.Connection("rightleg",2,"middle",4)
        });*/
        this.runSimulation(world,true);
    }

    private static ArrayList<ModulePosition> buildSnake(int length) {
    	//float Yoffset = 0.4f;
    	float Yoffset = 0.0f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	int x=0,y=0,z=0;
    	for(int i=0;i<length;i++) {
    		if(i%2==0) {
    			mPos.add(new WorldDescription.ModulePosition("snake "+i, new VectorDescription(x*unit,y*unit-Yoffset,z*unit), rotation_EW));
    		}
    		else {
    			mPos.add(new WorldDescription.ModulePosition("snake "+i, new VectorDescription(x*unit,y*unit-Yoffset,z*unit), rotation_NS));
    		}
    		x++;z++;
    	}
		return mPos;
	}
	private static ArrayList<ModulePosition> buildCar() {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new WorldDescription.ModulePosition("driver", new VectorDescription(2*0*unit,0*unit-Yoffset,0*unit), rotation_EW));
    	mPos.add(new WorldDescription.ModulePosition("axes1", new VectorDescription(1*unit,-1*unit-Yoffset,0*unit), rotation_UD));
    	mPos.add(new WorldDescription.ModulePosition("axes2", new VectorDescription(-1*unit,-1*unit-Yoffset,0*unit), rotation_UD));
    	mPos.add(new WorldDescription.ModulePosition("wheel1", new VectorDescription(-1*unit,-2*unit-Yoffset,1*unit), rotation_SN));
    	mPos.add(new WorldDescription.ModulePosition("wheel2", new VectorDescription(-1*unit,-2*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new WorldDescription.ModulePosition("wheel3", new VectorDescription(1*unit,-2*unit-Yoffset,1*unit), rotation_SN));
    	mPos.add(new WorldDescription.ModulePosition("wheel4", new VectorDescription(1*unit,-2*unit-Yoffset,-1*unit), rotation_NS));
        return mPos;
	}
    private static ArrayList<ModulePosition> buildAsLattice(int nModules, int xMax, int yMax, int zMax) {
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
        int index=0;
        for(int x=0;x<xMax;x++) {
        	for(int y=0;y<yMax;y++) {
        		for(int z=0;z<zMax;z++) {
        			VectorDescription pos = null;
        			RotationDescription rot = rotation_NS;
        			if(y%2==0&&z%2==0) {
        				pos = new VectorDescription(2*x*unit,y*unit,z*unit);
        				rot = rotation_EW;
        			}
	        		else if(y%2==0&&z%2==1)  {
	        			pos = new VectorDescription(2*x*unit+unit,y*unit,z*unit);
	        			rot = rotation_NS;
	        		}
	        		else if(y%2==1&&z%2==0) {
	        			pos = new VectorDescription(2*x*unit+unit,y*unit,z*unit);
	        			rot = rotation_UD;
	        		}
	        		else if(y%2==1&&z%2==1) {
	        			pos = new VectorDescription(2*x*unit,y*unit,z*unit);
	        			rot = rotation_NS;
	        		}
	        		if(index<nModules) mPos.add(new WorldDescription.ModulePosition(Integer.toString(index), pos, rot));
	        		index++;
        		}
        	}
        }
        return mPos;
	}
	private static ArrayList<Connection> allConnections(ArrayList<ModulePosition> modulePos) {
    	ArrayList<Connection> connections = new ArrayList<Connection>();
    	System.out.println("modulePos.size()"+modulePos.size());
    	for(int i=0;i<modulePos.size();i++) {
    		for(int j=i+1;j<modulePos.size();j++) {
    			if(isConnectable(modulePos.get(i), modulePos.get(j))) {
    				System.out.println("Found connection from module "+modulePos.get(i).getName()+" to "+modulePos.get(j).getName());
    				connections.add(new Connection(modulePos.get(i).getName(),modulePos.get(j).getName()));
    			}
    		}
    	}
		return connections;
	}
	public static boolean isConnectable(ModulePosition m1, ModulePosition m2) {
    	float dist = m1.getPosition().distance(m2.getPosition());
    	return Math.abs(dist-0.11313708f)<0.001;
    }
    @Override
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new ATRONSampleController1();
            }
        };
    }

 

}

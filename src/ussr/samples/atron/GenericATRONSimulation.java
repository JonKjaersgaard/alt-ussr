/**
 * Unified Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.atron;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.robotbuildingblocks.ModuleConnection;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.physics.PhysicsParameters;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;
import ussr.samples.GenericSimulation;

/**
 * An abstract implementation of an ATRON simulation, defining a number of useful
 * helper functionalities.
 * 
 * @author Modular Robots @ MMMI
 *
 */
public abstract class GenericATRONSimulation extends GenericSimulation {
	
	protected static float half = (float)(Math.PI);
	protected static float quart = (float)(0.5*Math.PI);
	protected static float eigth = (float)(0.25*Math.PI);
	public static boolean startPaused = true;
	protected static float unit = 0.08f;//8 cm between two lattice positions on physical atrons
	protected static RotationDescription rotation_NS = new RotationDescription(0,0,eigth+quart);//(0,0,eigth);
    protected static RotationDescription rotation_NS_1 = new RotationDescription(0,0,eigth+3*quart);//(0,0,eigth);
	protected static RotationDescription rotation_SN = new RotationDescription(0,half,eigth);
	protected static RotationDescription rotation_UD = new RotationDescription(quart,eigth+quart,0);
	protected static RotationDescription rotation_DU = new RotationDescription(-quart,eigth,0);
	protected static RotationDescription rotation_EW = new RotationDescription(new VectorDescription(eigth,0,0),new VectorDescription(0,quart,0));
	protected static RotationDescription rotation_WE = new RotationDescription(new VectorDescription(-eigth,0,0),new VectorDescription(0,-quart,0));

    protected float connection_acceptance_range = 0.001f;
    
    public void main() {
        setConnectorsAreActive(true);
        WorldDescription world = new WorldDescription();
        world.setPlaneSize(100);
        
        ArrayList<ModulePosition> modulePos;
        modulePos = buildRobot();
        world.setModulePositions(modulePos);
        
        ArrayList<ModuleConnection> connections = allConnections(modulePos);
        world.setModuleConnections(connections);

        this.changeWorldHook(world);
        
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
        this.runSimulation(world,startPaused);
    }
    
	protected void changeWorldHook(WorldDescription world) {
    }

    private ArrayList<ModuleConnection> allConnections(ArrayList<ModulePosition> modulePos) {
    	ArrayList<ModuleConnection> connections = new ArrayList<ModuleConnection>();
    	System.out.println("modulePos.size()"+modulePos.size());
    	for(int i=0;i<modulePos.size();i++) {
    		for(int j=i+1;j<modulePos.size();j++) {
    			if(isConnectable(modulePos.get(i), modulePos.get(j))) {
    				System.out.println("Found connection from module "+modulePos.get(i).getName()+" to "+modulePos.get(j).getName());
    				connections.add(new ModuleConnection(modulePos.get(i).getName(),modulePos.get(j).getName()));
    			}
    		}
    	}
		return connections;
	}
	public boolean isConnectable(ModulePosition m1, ModulePosition m2) {
    	float dist = m1.getPosition().distance(m2.getPosition());
    	return Math.abs(dist-0.11313708f)<connection_acceptance_range;
    }
    @Override
    protected abstract Robot getRobot(); 
    protected abstract ArrayList<ModulePosition> buildRobot();
 

}

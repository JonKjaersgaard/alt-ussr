/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package communication.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ussr.description.Robot;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModuleConnection;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsParameters;
import ussr.samples.GenericSimulation;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.GenericATRONSimulation;
import ussr.samples.atron.ATRONBuilder.ModuleSelector;
import ussr.samples.atron.ATRONBuilder.Namer;

/**
 * A sample ATRON simulation
 *
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONSimulation extends GenericSimulation {
	
	//After USSR update, this acceptance range created too many collisions and made USSR (or ODE) to stop the simulation.
	//private float connection_acceptance_range = 0.0000001f;
	private float connection_acceptance_range = 0.001f;
	private static int nModules = 50;
	private static int xMax = 2;
	private static int yMax = 1;
	private static int zMax = 1;
	public static float pe = 0.1f;//0 to 1, probability of modules sending information out.
	public static float pne = 1.0f;//0 to 1, proportion of modules the information is transmitted to.
	public static float pp = 0.1f;//0 to 1, probability of Imods modules sending information out.
	
    public static void main( String[] args ) {
    	
        for(int i=0;i<args.length;i++) {
			if(args[i].contains("nModules=")) {
				ATRONSimulation.nModules = Integer.parseInt(args[i].subSequence(args[i].indexOf('=')+1, args[i].length()).toString());
				//System.out.println(ATRONSimulation.nModules);
			}
			else if(args[i].contains("xMax=")) {
				ATRONSimulation.xMax = Integer.parseInt(args[i].subSequence(args[i].indexOf('=')+1, args[i].length()).toString());
			}
			else if(args[i].contains("yMax=")) {
				ATRONSimulation.yMax = Integer.parseInt(args[i].subSequence(args[i].indexOf('=')+1, args[i].length()).toString());
			}
			else if(args[i].contains("zMax=")) {
				ATRONSimulation.zMax = Integer.parseInt(args[i].subSequence(args[i].indexOf('=')+1, args[i].length()).toString());
			}
			else if(args[i].contains("pe=")) {
				ATRONSimulation.pe = Float.parseFloat(args[i].subSequence(args[i].indexOf('=')+1, args[i].length()).toString());
			}
			else if(args[i].contains("pne=")) {
				ATRONSimulation.pne = Float.parseFloat(args[i].subSequence(args[i].indexOf('=')+1, args[i].length()).toString());
			}
			else if(args[i].contains("pp=")) {
				ATRONSimulation.pp = Float.parseFloat(args[i].subSequence(args[i].indexOf('=')+1, args[i].length()).toString());
			}
			else {
				System.out.println("Unrecognized option "+args[i]);
			}
		}
        new ATRONSimulation().main();
        //System.out.println("\nSimulation Stopped");
    	System.exit(0);
    	
    }

    public void main() {
    	PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
        PhysicsParameters.get().setPhysicsSimulationStepSize(0.01f);
        PhysicsParameters.get().setResolutionFactor(3);
        PhysicsParameters.get().setRealisticCollision(false);
        setConnectorsAreActive(false);
        WorldDescription world = new WorldDescription();
        world.setPlaneSize(5);
        //
        world.setPlaneTexture(WorldDescription.WHITE_GRID_TEXTURE);
        //
        
        //modulePos = builder.buildCar(4, new VectorDescription(0,-0.25f,0));
        // modulePos = buildSnake(2);
        // modulePos = Arrays.asList(new WorldDescription.ModulePosition[] { new WorldDescription.ModulePosition("hermit", new VectorDescription(2*0*unit,0*unit,0*unit), rotation_EW) });
        
        //ArrayList<ModulePosition> modulePos = new ATRONBuilder().buildAsLattice(50,4,4,3); //it looks like the computer is not powerfull enough.
        
        
        
        
        ArrayList<ModulePosition> modulePos = new ArrayList<ModulePosition>();
        Namer namer = new Namer() {
            public String name(int number, VectorDescription pos, RotationDescription rot) {
                return "module"+Integer.toString(number);
            }
                
        };
        
        ModuleSelector selector = new ModuleSelector() {
            public String select(String name, int index, VectorDescription pos, RotationDescription rot) {
                return null;
            }
        };
        
        //int nModules=50, xMax=5, yMax=1, zMax=5;//Plane
        //int nModules=100, xMax=3, yMax=3, zMax=3;//Cube
        
        int index=0;
        boolean skip = false;
        for(int x=0;x<ATRONSimulation.xMax;x++) {
            for(int y=0;y<(2*ATRONSimulation.yMax-1);y++) {
                for(int z=0;z<2*ATRONSimulation.zMax;z++) {
                    VectorDescription pos = null;
                    RotationDescription rot = ATRON.ROTATION_NS;
                    if(y%2==0&&z%2==0) {//even y, even z
                        pos = new VectorDescription(2*x*ATRON.UNIT,y*ATRON.UNIT-0.43f,z*ATRON.UNIT);
                        rot = ATRON.ROTATION_EW;
                        skip = false;
                    }
                    else if(y%2==0&&z%2==1)  {//even y, odd z
                        pos = new VectorDescription(2*x*ATRON.UNIT+ATRON.UNIT,y*ATRON.UNIT-0.43f,z*ATRON.UNIT);
                        rot = ATRON.ROTATION_NS;
                        skip = false;
                    }
                    else if(y%2==1&&z%2==0) {//odd y, even z
                        pos = new VectorDescription(2*x*ATRON.UNIT+ATRON.UNIT,y*ATRON.UNIT-0.43f,z*ATRON.UNIT);
                        rot = ATRON.ROTATION_UD;
                        skip = false;
                    }
                    else if(y%2==1&&z%2==1) {//odd y, odd z
                        //pos = new VectorDescription(2*x*ATRON.UNIT,y*ATRON.UNIT-0.43f,z*ATRON.UNIT);
                    	//rot = ATRON.ROTATION_NS;
                        //pos = new VectorDescription(2*x*ATRON.UNIT+ATRON.UNIT,y*ATRON.UNIT+ATRON.UNIT-0.43f,z*ATRON.UNIT);
                        //rot = ATRON.ROTATION_NS;
                    	//pos = new VectorDescription(2*x*ATRON.UNIT+ATRON.UNIT,y*ATRON.UNIT-0.43f,2*z*ATRON.UNIT);
                        //rot = ATRON.ROTATION_UD;
                    	skip = true;
                    }
                    if(index<ATRONSimulation.nModules && !skip) {
                        String name = namer.name(index,pos,rot);
                        String robotNameMaybe = selector.select(name,index,pos,rot);
                        ModulePosition mpos;
                        if(robotNameMaybe==null)
                            mpos = new ModulePosition(name, pos, rot);
                        else
                            mpos = new ModulePosition(name, robotNameMaybe, pos, rot);
                        modulePos.add(mpos);
                        index++;
                    }
                    //index++;
                }
            }
        }
        
        world.setModulePositions(modulePos);
                
        ArrayList<ModuleConnection> connections = allConnections(modulePos);
        System.out.println("nModules = "+ATRONSimulation.nModules+" xMax = "+ATRONSimulation.xMax+" yMax = "+
        		ATRONSimulation.yMax+" zMax = "+ATRONSimulation.zMax+" pe = "+ATRONSimulation.pe+" pne = "+
        		ATRONSimulation.pne+" pp = "+ATRONSimulation.pp);
        System.out.println("#connection found = "+connections.size());
        world.setModuleConnections(connections);
        System.out.println("#Modules Placed = "+modulePos.size());
        //world.setModulePositions(modulePos);
        //Here is the cheese...
        //System.out.println("connections.size() = "+connections.size()+"; modulePos.size() = "+modulePos.size());
        //System.out.println("#Modules per Interface (avg) = " + (1+(((float)(2*connections.size()))/((float)(8*modulePos.size())))) );
        System.out.println("#Modules per Interface (avg) = " + (1+(((float)(connections.size()))/((float)(8*modulePos.size()-connections.size())))) );
        
        this.runSimulation(world,false);
    }
    
    public ArrayList<ModuleConnection> allConnections(ArrayList<ModulePosition> modulePos) {
        ArrayList<ModuleConnection> connections = new ArrayList<ModuleConnection>();
        for(int i=0;i<modulePos.size();i++) {
            for(int j=i+1;j<modulePos.size();j++) {
                if(isConnectable(modulePos.get(i), modulePos.get(j))) {
                    //System.out.println("Found connection from module "+modulePos.get(i).getName()+" to "+modulePos.get(j).getName());
                    connections.add(new ModuleConnection(modulePos.get(i).getName(),modulePos.get(j).getName()));
                }
            }
        }
        return connections;
    }
    
    public boolean isConnectable(ModulePosition m1, ModulePosition m2) {
    	/*float dist = m1.getPosition().distance(m2.getPosition());
    	if(dist-Math.abs(Math.sqrt(2*(ATRON.UNIT+Math.sqrt(ATRON.UNIT))))/2<connection_acceptance_range) return true;
    	return dist==(float)Math.sqrt(2*(ATRON.UNIT+Math.sqrt(ATRON.UNIT)));*/
        float dist = m1.getPosition().distance(m2.getPosition());
        return Math.abs(dist-0.11313708f)<connection_acceptance_range;  
    }

    @Override
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new ATRONController();
            }
        };
    }

 

}

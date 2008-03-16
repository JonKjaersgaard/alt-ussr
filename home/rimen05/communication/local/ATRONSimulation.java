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
import ussr.samples.atron.ATRONBuilder.ModuleSelector;
import ussr.samples.atron.ATRONBuilder.Namer;

/**
 * A sample ATRON simulation
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONSimulation extends GenericSimulation {
	
	private float connection_acceptance_range = 0.001f;
	
    public static void main( String[] args ) {
        new ATRONSimulation().main();
    }

    public void main() {
        PhysicsParameters.get().setPhysicsSimulationStepSize(0.001f);
        setConnectorsAreActive(true);
        WorldDescription world = new WorldDescription();
        world.setPlaneSize(5);
        //
        world.setPlaneTexture(WorldDescription.GRID_TEXTURE);
        //
        
        //List<ModulePosition> modulePos;
        //ATRONBuilder builder = new ATRONBuilder(); 
        //modulePos = builder.buildAsLattice(10,5,5,5);
        //modulePos = builder.buildCar(4, new VectorDescription(0,-0.25f,0));
        // modulePos = buildSnake(2);
        // modulePos = Arrays.asList(new WorldDescription.ModulePosition[] { new WorldDescription.ModulePosition("hermit", new VectorDescription(2*0*unit,0*unit,0*unit), rotation_EW) });
        
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
        
        int nModules=30, xMax=10, yMax=1,zMax=5;//Plane
        
        int index=0;
        for(int x=0;x<xMax;x++) {
            for(int y=0;y<yMax;y++) {
                for(int z=0;z<zMax;z++) {
                    VectorDescription pos = null;
                    RotationDescription rot = ATRON.ROTATION_NS;
                    if(y%2==0&&z%2==0) {
                        pos = new VectorDescription(2*x*ATRON.UNIT,y*ATRON.UNIT-0.43f,z*ATRON.UNIT);
                        rot = ATRON.ROTATION_EW;
                    }
                    else if(y%2==0&&z%2==1)  {
                        pos = new VectorDescription(2*x*ATRON.UNIT+ATRON.UNIT,y*ATRON.UNIT-0.43f,z*ATRON.UNIT);
                        rot = ATRON.ROTATION_NS;
                    }
                    else if(y%2==1&&z%2==0) {
                        pos = new VectorDescription(2*x*ATRON.UNIT+ATRON.UNIT,y*ATRON.UNIT-0.43f,z*ATRON.UNIT);
                        rot = ATRON.ROTATION_UD;
                    }
                    else if(y%2==1&&z%2==1) {
                        pos = new VectorDescription(2*x*ATRON.UNIT,y*ATRON.UNIT-0.43f,z*ATRON.UNIT);
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
                        modulePos.add(mpos);
                    }
                    index++;
                }
            }
        }
                
        ArrayList<ModuleConnection> connections = allConnections(modulePos);
        System.out.println("#connection found = "+connections.size());
        world.setModuleConnections(connections);
        System.out.println("#Modules Placed = "+modulePos.size());
        world.setModulePositions(modulePos);
        //System.out.println("#Total         = "+modulePos.size());
        System.out.println("#Modules per Interface (avg)= "+(1+(((float)(2*connections.size()))/((float)(8*modulePos.size())))));
        
        this.runSimulation(world,true);
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

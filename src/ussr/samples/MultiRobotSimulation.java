/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ussr.description.Robot;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModuleConnection;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.simulations.ATRONCarController1;
import ussr.samples.mtran.MTRAN;
import ussr.samples.mtran.MTRANSampleController1;
import ussr.samples.odin.modules.OdinBall;
import ussr.samples.odin.modules.OdinBattery;
import ussr.samples.odin.modules.OdinHinge;
import ussr.samples.odin.modules.OdinMuscle;
import ussr.samples.odin.modules.OdinWheel;
import ussr.samples.odin.simulations.OdinSampleController1;

import com.jme.math.Quaternion;

/**
 * Sample simulation showing multiple robot types in the same simulation
 * 
 * @author ups
 */
public class MultiRobotSimulation extends GenericSimulation {
    // Odin constants
    private static float odinUnit = (float)Math.sqrt((0.18f*0.18f)/2);
    private static float pi = (float)Math.PI;
    
    // ATRON constants
    protected static float half = (float)(Math.PI);
    protected static float quart = (float)(0.5*Math.PI);
    protected static float eigth = (float)(0.25*Math.PI);
    protected static float atronUnit = 0.08f;//8 cm between two lattice positions on physical atrons
    protected static RotationDescription rotation_NS = new RotationDescription(0,0,eigth+quart);//(0,0,eigth);
    protected static RotationDescription rotation_NS_1 = new RotationDescription(0,0,eigth+3*quart);//(0,0,eigth);
    protected static RotationDescription rotation_SN = new RotationDescription(0,half,eigth);
    protected static RotationDescription rotation_UD = new RotationDescription(quart,eigth+quart,0);
    protected static RotationDescription rotation_DU = new RotationDescription(-quart,eigth,0);
    protected static RotationDescription rotation_EW = new RotationDescription(new VectorDescription(eigth,0,0),new VectorDescription(0,quart,0));
    protected static RotationDescription rotation_WE = new RotationDescription(new VectorDescription(-eigth,0,0),new VectorDescription(0,-quart,0));
    protected float atron_connection_acceptance_range = 0.001f;
    
    // M-TRAN constants
    private static float unit =  0.065f*2+0.01f;//(float)Math.sqrt((0.18f*0.18f)/2);
    
    public static void main( String[] args ) {
        GenericSimulation.setConnectorsAreActive(true);
        MultiRobotSimulation simulation = new MultiRobotSimulation();
        //PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
        PhysicsParameters.get().setWorldDampingLinearVelocity(0.9f);
        PhysicsParameters.get().setRealisticCollision(true);
        //PhysicsParameters.get().setPhysicsSimulationStepSize(0.0035f);
        PhysicsParameters.get().setPhysicsSimulationStepSize(0.01f);
        simulation.runSimulation(null,true);
    }

    public void runSimulation(WorldDescription world, boolean startPaused) {
        PhysicsLogger.setDefaultLoggingLevel();
        final PhysicsSimulation simulation = PhysicsFactory.createSimulator();
        
        simulation.setRobot(new OdinMuscle(){
            public Controller createController() {
                return new OdinSampleController1("OdinMuscle") {
                    public void activate() {
                        delay(10000);
                        super.activate();
                    }
                };
            }},"OdinMuscle");
        simulation.setRobot(new OdinWheel(){
            public Controller createController() {
                return new OdinSampleController1("OdinWheel");
            }},"OdinWheel");
        simulation.setRobot(new OdinHinge(){
            public Controller createController() {
                return new OdinSampleController1("OdinHinge");
            }},"OdinHinge");
        
        simulation.setRobot(new OdinBattery(){
            public Controller createController() {
                return new OdinSampleController1("OdinBattery");
            }},"OdinBattery");
        
        simulation.setRobot(new OdinBall(){
            public Controller createController() {
                return new OdinSampleController1("OdinBall");
            }},"OdinBall");
        
        ATRON atron = new ATRON(){
            public Controller createController() {
                return new ATRONCarController1() {
                    public void activate() {
                        delay(10000);
                        super.activate();
                    }
                };
            }
        };
        atron.setGentle();
        simulation.setRobot(atron,"ATRON");

        simulation.setRobot(new MTRAN(){
            public Controller createController() {
                return new MTRANSampleController1("MTRAN") {
                  public void activate() {
                      delay(10000);
                      super.activate();
                  }
                };
            }},"MTRAN");
        
        if(world==null) world = createWorld();
        simulation.setWorld(world);
        simulation.setPause(startPaused);

        // Start
        simulation.start();
    }
    /**
     * Create a world description for our simulation
     * @return the world description
     */
    private WorldDescription createWorld() {
        WorldDescription world = new WorldDescription();
        world.setHasBackgroundScenery(false);
        world.setPlaneTexture(WorldDescription.WHITE_GRID_TEXTURE);
        world.setPlaneSize(5);
        // Odin
        ArrayList<ModulePosition> odinBallPos = new ArrayList<ModulePosition>();
        ArrayList<ModulePosition> odinModulePos = new ArrayList<ModulePosition>();
        ArrayList<ModuleConnection> odinConnections = createOdinRobot(0,0.1f,0.7f,odinBallPos,odinModulePos);
        // ATRON
        ArrayList<ModulePosition> atronPos = buildAtronCar();
        ArrayList<ModuleConnection> atronConnections = atronConnections(atronPos);
        // M-TRAN
        ArrayList<ModulePosition> mtranPos = constructMTRAN(0,0,4);
        ArrayList<ModuleConnection> mtranConnections = allMTRANConnections(mtranPos);
        // Build world
        ArrayList<ModulePosition> positions = new ArrayList<ModulePosition>();
        positions.addAll(odinBallPos);
        positions.addAll(odinModulePos);
        positions.addAll(atronPos);
        positions.addAll(mtranPos);
        ArrayList<ModuleConnection> connections = new ArrayList<ModuleConnection>();
        connections.addAll(odinConnections);
        connections.addAll(atronConnections);
        connections.addAll(mtranConnections);
        world.setModulePositions(positions);
        world.setModuleConnections(connections);
        return world;
    }

    private int MTRANconstructIndex = 0;
    public static final RotationDescription MTRAN_ORI1 = new RotationDescription(pi,0,0);
    public static final RotationDescription MTRAN_ORI2 = new RotationDescription(-pi/2,0,0);
    public static final RotationDescription MTRAN_ORI3 = new RotationDescription(pi,0,pi/2);
    private ArrayList<ModulePosition> constructMTRAN(float xOffset, float yOffset, float zOffset) {
        ArrayList<ModulePosition> pos = new ArrayList<ModulePosition>();
        for(int i=0; i<=14; i+=2)
            addMTRANModule(pos,i+xOffset,yOffset,zOffset,MTRAN_ORI2);
        /*addMTRANModule(pos,2+xOffset,yOffset,zOffset,MTRAN_ORI2);
        addMTRANModule(pos,4,0,0,MTRAN_ORI2);
        addMTRANModule(pos,6,0,0,MTRAN_ORI2);
        addMTRANModule(pos,8,0,0,MTRAN_ORI2);
        addMTRANModule(pos,10,0,0,MTRAN_ORI2);
        addMTRANModule(pos,12,0,0,MTRAN_ORI2);
        addMTRANModule(pos,14,0,0,MTRAN_ORI2);*/
        return pos;
    }
    private void addMTRANModule(List<ModulePosition> modulePos, float x, float y, float z, RotationDescription ori) {
        VectorDescription pos = new VectorDescription(x*unit/2,y*unit/2-0.43f,z*unit/2);
        //RotationDescription rot = rotFromBalls(ballPos.get(i),ballPos.get(j));
        modulePos.add(new ModulePosition("MTRAN"+Integer.toString(MTRANconstructIndex),"MTRAN", pos, ori));
        MTRANconstructIndex++;
    }
    private static ArrayList<ModuleConnection> allMTRANConnections(ArrayList<ModulePosition> modulePos) {
        ArrayList<ModuleConnection> connections = new ArrayList<ModuleConnection>();
        //System.out.println("modulePos.size()"+modulePos.size());
        for(int i=0;i<modulePos.size();i++) {
            for(int j=i+1;j<modulePos.size();j++) {
                if(isMTRANConnectable(modulePos.get(i), modulePos.get(j))) {
                    System.out.println("Found connection from module "+modulePos.get(i).getName()+" to "+modulePos.get(j).getName());
                    connections.add(new ModuleConnection(modulePos.get(i).getName(),modulePos.get(j).getName()));
                }
            }
        }
        return connections;
    }
    public static boolean isMTRANConnectable(ModulePosition m1, ModulePosition m2) {
        float dist = m1.getPosition().distance(m2.getPosition());
        return Math.abs(dist-unit)<0.01f;
    }

    protected ArrayList<ModulePosition> buildAtronCar() {
        float Yoffset = 0.25f;
        ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
        mPos.add(new ModulePosition("driver0", "ATRON", new VectorDescription(2*0*atronUnit,0*atronUnit-Yoffset,0*atronUnit), rotation_EW));
        mPos.add(new ModulePosition("axleOne5", "ATRON", new VectorDescription(1*atronUnit,-1*atronUnit-Yoffset,0*atronUnit), rotation_UD));
        mPos.add(new ModulePosition("axleTwo6", "ATRON", new VectorDescription(-1*atronUnit,-1*atronUnit-Yoffset,0*atronUnit), rotation_UD));
        mPos.add(new ModulePosition("wheel1", "ATRON", new VectorDescription(-1*atronUnit,-2*atronUnit-Yoffset,1*atronUnit), rotation_SN));
        mPos.add(new ModulePosition("wheel2", "ATRON", new VectorDescription(-1*atronUnit,-2*atronUnit-Yoffset,-1*atronUnit), rotation_NS));
        mPos.add(new ModulePosition("wheel3", "ATRON", new VectorDescription(1*atronUnit,-2*atronUnit-Yoffset,1*atronUnit), rotation_SN));
        mPos.add(new ModulePosition("wheel4", "ATRON", new VectorDescription(1*atronUnit,-2*atronUnit-Yoffset,-1*atronUnit), rotation_NS));
        System.out.println("ATRON #modules: "+mPos.size());
        return mPos;
    }

    private ArrayList<ModuleConnection> atronConnections(ArrayList<ModulePosition> modulePos) {
        ArrayList<ModuleConnection> connections = new ArrayList<ModuleConnection>();
        //System.out.println("modulePos.size()"+modulePos.size());
        for(int i=0;i<modulePos.size();i++) {
            for(int j=i+1;j<modulePos.size();j++) {
                if(isAtronConnectable(modulePos.get(i), modulePos.get(j))) {
                    System.out.println("ATRON connection from module "+modulePos.get(i).getName()+" to "+modulePos.get(j).getName());
                    connections.add(new ModuleConnection(modulePos.get(i).getName(),modulePos.get(j).getName()));
                }
            }
        }
        return connections;
    }

    public boolean isAtronConnectable(ModulePosition m1, ModulePosition m2) {
        float dist = m1.getPosition().distance(m2.getPosition());
        return Math.abs(dist-0.11313708f)<atron_connection_acceptance_range;
    }

    private static ArrayList<ModuleConnection> createOdinRobot(float xOffset, float yOffset, float zOffset, ArrayList<ModulePosition> odinBallPos, ArrayList<ModulePosition> odinModulePos) {
        int index=0;
        int nBalls=4, xMax=3, yMax=2,zMax=2;
        for(int x=0;x<xMax;x++) {
            for(int y=0;y<yMax;y++) {
                for(int z=0;z<zMax;z++) {
                    if((x+y+z)%2==0) {
                        VectorDescription pos = new VectorDescription(xOffset+x*odinUnit,yOffset+y*odinUnit-0.48f,zOffset+z*odinUnit);
                        if(index<nBalls) {
                            odinBallPos.add(new ModulePosition(Integer.toString(index),"OdinBall", pos, new RotationDescription(0,0,0)));
                        }
                        index++;
                    }
                }
            }
        }
        for(int i=0;i<odinBallPos.size();i++) {
            for(int j=i+1;j<odinBallPos.size();j++) {
                if(isNeighorBalls(odinBallPos.get(i),odinBallPos.get(j))) {
                    VectorDescription pos = posFromBalls(odinBallPos.get(i),odinBallPos.get(j));
                    RotationDescription rot = rotFromBalls(odinBallPos.get(i),odinBallPos.get(j));
                    if(index%2==0) {
                        Quaternion q = new Quaternion();
                        q.fromAngles(pi/2, 0, 0);
                        rot.setRotation(rot.getRotation().mult(q));
                        
                    }
                    odinModulePos.add(new ModulePosition(Integer.toString(index),"OdinMuscle", pos, rot));
                    //if(index%2==0) modulePos.add(new WorldDescription.ModulePosition(Integer.toString(index),"OdinMuscle", pos, rot));
                    //if(index%2==0) modulePos.add(new WorldDescription.ModulePosition(Integer.toString(index),"OdinBattery", pos, rot));
                    //else modulePos.add(new WorldDescription.ModulePosition(Integer.toString(index),"OdinWheel", pos, rot));
                    index++;
                    //System.out.println("Ball "+i+" and ball "+j+" are neighbors");
                }
            }
        }
       ArrayList<ModuleConnection> connections = allOdinConnections(odinBallPos,odinModulePos);
       System.out.println("Odin: #connection found = "+connections.size());
        System.out.println("Odin: #Balls Placed  = "+odinBallPos.size());
        System.out.println("Odin: #Module Placed = "+odinModulePos.size());
        //odinModulePos.addAll(odinBallPos);
        Iterator<ModulePosition> positions = odinModulePos.iterator();
        positionLoop: while(positions.hasNext()) {
            ModulePosition pos = positions.next();
            String name = pos.getName();
            for(ModuleConnection connection: connections) {
                if(name.equals(connection.getModule1())) continue positionLoop;
                if(name.equals(connection.getModule2())) continue positionLoop;
            }
            System.out.println("Removing unconnected Odin module "+name);
            positions.remove();
        }
        System.out.println("Odin: #Total         = "+odinModulePos.size());
        return connections;
    }
    private static ArrayList<ModuleConnection> allOdinConnections(ArrayList<ModulePosition> ballPos, ArrayList<ModulePosition> modulePos) {
        ArrayList<ModuleConnection> connections = new ArrayList<ModuleConnection>();
        for(int i=0;i<ballPos.size();i++) {
            for(int j=0;j<modulePos.size();j++) {
                if(isOdinConnectable(ballPos.get(i), modulePos.get(j))) {
                    connections.add(new ModuleConnection(ballPos.get(i).getName(),modulePos.get(j).getName()));
                }
            }
        }
        return connections;
    }
    private static VectorDescription posFromBalls(ModulePosition p1, ModulePosition p2) {
        VectorDescription pos = new VectorDescription((p1.getPosition().getX()+p2.getPosition().getX())/2,(p1.getPosition().getY()+p2.getPosition().getY())/2,(p1.getPosition().getZ()+p2.getPosition().getZ())/2);
        return pos;
    }
    private static RotationDescription rotFromBalls(ModulePosition p1, ModulePosition p2) {
        float x1 = p1.getPosition().getX();
        float y1 = p1.getPosition().getY();
        float z1 = p1.getPosition().getZ();
        float x2 = p2.getPosition().getX();
        float y2 = p2.getPosition().getY();
        float z2 = p2.getPosition().getZ();
        if(x1-x2<0&&z1-z2<0) return new RotationDescription(0,-pi/4,0);
        else if(x1-x2<0&&z1-z2>0) return new RotationDescription(0,pi/4,0);
        else if(x1-x2<0&&y1-y2<0) return new RotationDescription(0,0,pi/4);
        else if(x1-x2<0&&y1-y2>0) return new RotationDescription(0,0,-pi/4);
        else if(y1-y2<0&&z1-z2<0) return new RotationDescription(0,pi/4,-pi/2);
        else if(y1-y2<0&&z1-z2>0) return new RotationDescription(0,-pi/4,-pi/2);
        System.out.println("("+(x1-x2)+","+(y1-y2)+","+(z1-z2)+")");
        return new RotationDescription(0,0,0);
    }
    public static boolean isOdinConnectable(ModulePosition ball, ModulePosition module) {
        float dist = ball.getPosition().distance(module.getPosition());
        if(dist-Math.abs(Math.sqrt(2*odinUnit*odinUnit))/2<0.0000001) return true;
        return dist==(float)Math.sqrt(2*odinUnit*odinUnit)/2;
    }
    public static boolean isNeighorBalls(ModulePosition ball1, ModulePosition ball2) {
        float dist = ball1.getPosition().distance(ball2.getPosition());
        if(dist-Math.abs(Math.sqrt(2*odinUnit*odinUnit))<0.0000001) return true;
        return dist==(float)Math.sqrt(2*odinUnit*odinUnit);
    }
    public static void printConnectorPos() {
        for(int x=-2;x<2;x++) {
            for(int y=-2;y<2;y++) {
                for(int z=-2;z<2;z++) {
                    if((x+y+z)%2==0&&(x*x+y*y+z*z)<3&&!(x==0&&y==0&&z==0)) {
                        System.out.println("new VectorDescription("+x+"*unit, "+y+"*unit, "+z+"*unit),");
                    }
                }
            }
        }
    }

    @Override
    protected Robot getRobot() {
        return Robot.NO_DEFAULT;
    }
}

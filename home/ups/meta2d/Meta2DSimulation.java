/**
 * 
 */
package meta2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import meta2d.statements.ConnectForeigners;
import meta2d.statements.Disconnect;
import meta2d.statements.DisconnectForeigners;
import meta2d.statements.Reconnect;
import meta2d.statements.RemapRoles;
import meta2d.statements.Rotate;
import meta2d.statements.Terminate;

import ussr.description.Robot;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.BoxDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.description.setup.WorldDescription.TextureDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.GenericATRONSimulation;

/**
 * A simulation of an ATRON 2D meta modules
 * 
 * @author Modular Robots @ MMMI
 */
public class Meta2DSimulation extends GenericATRONSimulation {

    public static final int MetaPosition_UNKNOWN = 0;
    public static final int MetaPosition_NORTH = 1;
    public static final int MetaPosition_SOUTH = 2;
    public static final int MetaPosition_EAST = 4;
    public static final int MetaPosition_WEST = 8;
    public static final int MetaPosition_NONE = 16;

    public static final Program program_north_east = new Program(new Statement[] {
        new DisconnectForeigners(MetaPosition_SOUTH),
        new DisconnectForeigners(MetaPosition_EAST),
        new Disconnect(MetaPosition_SOUTH,MetaPosition_WEST),
        new Rotate(MetaPosition_NORTH,MetaPosition_EAST,90),
        new Rotate(MetaPosition_EAST,MetaPosition_EAST,180), //new Rotate180(MetaPosition_EAST),
        new Rotate(MetaPosition_NORTH,MetaPosition_EAST,90),
        new ConnectForeigners(MetaPosition_SOUTH),
        new ConnectForeigners(MetaPosition_EAST),
        new DisconnectForeigners(MetaPosition_NORTH),
        new DisconnectForeigners(MetaPosition_WEST),
        new Rotate(MetaPosition_EAST,MetaPosition_UNKNOWN,-180),
        new Rotate(MetaPosition_NORTH,MetaPosition_UNKNOWN,-180),
        new Reconnect(MetaPosition_SOUTH,MetaPosition_WEST),
        new RemapRoles(MetaPosition_SOUTH,MetaPosition_NORTH,MetaPosition_WEST,MetaPosition_EAST),
        new Terminate()
    });

    public static final Program program_south_west = program_north_east.reverse();
    
    public static final Program[] program_store = new Program[] {
        program_north_east,
        program_south_west
    };
    
    public static void main(String argv[]) {
        PhysicsParameters.get().setMaintainRotationalJointPositions(true);
        PhysicsParameters.get().setRealisticCollision(true);
        Meta2DSimulation main = new Meta2DSimulation();
        main.main();
    }

    protected Robot getRobot() {
        return new MetaATRON();
    }

    public static class MetaATRON extends ATRON {
        public MetaATRON() {
            this.setSuper();
        }
        public Controller createController() {
            return new MetaController();
        }
    }

    protected void simulationHook(PhysicsSimulation simulation) {
        simulation.addGadget(new MetaGadget());
    }


    protected ArrayList<ModulePosition> buildRobot() {
        ArrayList<ModulePosition> positions = new ArrayList<ModulePosition>();
        buildMeta(positions, "meta", 1, 0, -0.25f, 0);
        buildMeta(positions, "foo", 2, 2*ATRON.UNIT, -0.25f, 2*ATRON.UNIT);
        buildMeta(positions, "bar", 3, 4*ATRON.UNIT, -0.25f, 4*ATRON.UNIT);
        buildMeta(positions, "baz", 4, 4*ATRON.UNIT, -0.25f, 0*ATRON.UNIT);
        buildMeta(positions, "fisk", 5, 2*ATRON.UNIT, -0.25f, -2*ATRON.UNIT);
        buildMeta(positions, "hest", 6, 0*ATRON.UNIT, -0.25f, 4*ATRON.UNIT);
        return positions;
    }

    private void buildMeta(ArrayList<ModulePosition> positions, String prefix, int group, float Xoffset, float Yoffset, float Zoffset) {
        positions.add(new ModulePosition(prefix+"/E:"+group, new VectorDescription(-1*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,1*ATRON.UNIT+Zoffset), ATRON.ROTATION_SN));
        positions.add(new ModulePosition(prefix+"/S:"+group, new VectorDescription(0+Xoffset,-2*ATRON.UNIT+Yoffset,0+Zoffset), ATRON.ROTATION_EW));
        positions.add(new ModulePosition(prefix+"/W:"+group, new VectorDescription(1*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,1*ATRON.UNIT+Zoffset), ATRON.ROTATION_NS));
        positions.add(new ModulePosition(prefix+"/N:"+group, new VectorDescription(0*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,2*ATRON.UNIT+Zoffset), ATRON.ROTATION_WE));
    }

    protected void changeWorldHook(WorldDescription world) {
        world.setHasBackgroundScenery(false);
        world.setPlaneTexture(WorldDescription.GREY_GRID_TEXTURE);
    }

    public static final byte MSG_MetaPositionSet = 1;
    public static final byte MSG_NorthEastMetaStep = 2;
    public static final byte MSG_DisconnectThisConnector = 3;
    public static final byte MSG_ConnectThisConnector = 4;
    public static final byte MSG_SetActiveProgram = 5;

}

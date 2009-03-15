package ussr.builder.construction;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

import ussr.builder.BuilderHelper;
import ussr.description.geometry.VectorDescription;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.samples.mtran.MTRANSimulation;

/**
 * The main responsibility of this class is to take care of construction functions for
 * construction of MTRAN modular robot's morphology. Here module consists of components. To be more precise
 * MTRAN module consists of three components: two half boxes-cylinders(blue and red)and one link(black).
 * Connectors are considered to be as parts of components. MTRAN module has 6 connectors, numbered as: 
 * 0,1,2,3,4 and 5. All methods are specific to MTRAN module design.
 * @author Konstantinas
 */
//FIXME 1) UPDATE COMMENTS
//FIXME 2) FIX EXISTING IMPROVEMENTS
public class MTRANConstructionTemplate extends ModularRobotConstructionTemplate {

	/**
	 * The numbers of connectors on the MTRAN module
	 * Connectors Nr0 and 3 are special(simpler)cases than others, because they always are aligned with one of the coordinate axes 
	 */
	private final static int connectorNr0 = 0, connectorNr1 = 1,  connectorNr2 = 2, connectorNr3  = 3, connectorNr4  = 4, connectorNr5  = 5;

	/**
	 * Supported rotations of MTRAN module.
	 */
	private final static String ori1 ="ORI1", ori1y = "ORI1Y", ori1x ="ORI1X", ori1xy ="ORI1XY", ori2 ="ORI2", ori2y ="ORI2Y", 
	ori2x ="ORI2X",ori2xy ="ORI2XY", ori3 ="ORI3", ori3y ="ORI3Y", ori3x ="ORI3X", ori3xy ="ORI3XY"; 
	
	/**
	 * The indexes of MTRAN module components 
	 */
	private final static int redBoxIndex =0, linkIndex = 1, blueBoxIndex =2;

	/**
	 * The length of the module. Used to move each component of new movable module along the selected module.
	 * Particularly useful when moving the new movable module components along the axis which is alligned along the selected module.
	 */
	private final static  float moduleLengthOffset =0.14f;

	/**
	 * The offset for moving link in between(middle) two half boxes-cylinders along the new movable module respectively to selected module.
	 */
	private final static float linkOffsetNr1 = 0.1028f;

	/**
	 * The offset for moving link in between(middle) two half boxes-cylinders across the new movable module respectively to selected module.
	 */
	private final static float linkOffsetNr2 = 0.0375f;

	/**
	 * The offset for aligning two half boxes-cylinders together (connected)
	 */
	private final static float boxOffsetNr1 = 0.075f;

	/**
	 * The offset for aligning two half boxes-cylinders together (connected)
	 */
	private final static float boxOffsetNr2 = 0.065f;	

	/**
	 * The array of objects containing information about MTRAN specific rotations.
	 * The logic is: if rotation is "0RI1", then the rotation value is MTRANSimulation.ORI1 and opposite
	 * to this rotation is MTRANSimulation.ORI1Y and MTRANSimulation.ORI1X is specific to opposite.
	 * (Look the first entry in array beneath).
	 */
	private final static ModuleRotationMapEntryHelper[] moduleRotationMap =  {
		new ModuleRotationMapEntryHelper(ori1,MTRANSimulation.ORI1,MTRANSimulation.ORI1Y,MTRANSimulation.ORI1X),
		new ModuleRotationMapEntryHelper(ori1y,MTRANSimulation.ORI1Y,MTRANSimulation.ORI1,MTRANSimulation.ORI1XY),
		new ModuleRotationMapEntryHelper(ori1x,MTRANSimulation.ORI1X,MTRANSimulation.ORI1XY,MTRANSimulation.ORI1),
		new ModuleRotationMapEntryHelper(ori1xy,MTRANSimulation.ORI1XY,MTRANSimulation.ORI1X,MTRANSimulation.ORI1Y),
		new ModuleRotationMapEntryHelper(ori2,MTRANSimulation.ORI2,MTRANSimulation.ORI2Y,MTRANSimulation.ORI2X),
		new ModuleRotationMapEntryHelper(ori2y,MTRANSimulation.ORI2Y,MTRANSimulation.ORI2,MTRANSimulation.ORI2XY),
		new ModuleRotationMapEntryHelper(ori2x,MTRANSimulation.ORI2X,MTRANSimulation.ORI2XY,MTRANSimulation.ORI2),
		new ModuleRotationMapEntryHelper(ori2xy,MTRANSimulation.ORI2XY,MTRANSimulation.ORI2X,MTRANSimulation.ORI2Y),
		new ModuleRotationMapEntryHelper(ori3,MTRANSimulation.ORI3,MTRANSimulation.ORI3Y,MTRANSimulation.ORI3X),
		new ModuleRotationMapEntryHelper(ori3y,MTRANSimulation.ORI3Y,MTRANSimulation.ORI3,MTRANSimulation.ORI3XY),
		new ModuleRotationMapEntryHelper(ori3x,MTRANSimulation.ORI3X,MTRANSimulation.ORI3XY,MTRANSimulation.ORI3),
		new ModuleRotationMapEntryHelper(ori3xy,MTRANSimulation.ORI3XY,MTRANSimulation.ORI3X,MTRANSimulation.ORI3Y)
	};

	/**
	 * Internal component counter used to keep track which component of the module is considered.
	 */
	int componentCounter =-1;
	
	/**
	 * Tolerance used to identify if component (module) already exists in interval of space.
	 */
	private final static float searchTolerance = 0.06498f;
	
	
	/**
	 * COMMENT
	 * @param simulation
	 */
	public MTRANConstructionTemplate(JMESimulation simulation) {
		super(simulation);		
	}

	/**
	 * Updates the array of objects containing the information about all available initial rotations 
	 * of MTRAN modular robot module and rotations together with positions of newly added MTRAN module with respect to specific 
	 * connector number on selected module and selected module itself.
	 * This method is so-called "Primitive operation" for TEMPLATE method, called "moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule)".	 
	 * @param x, the amount of x coordinate of current position of MTRAN component.
	 * @param y, the amount of y coordinate of current position of MTRAN component.
	 * @param z, the amount of z coordinate of current position of MTRAN component. 
	 */
	public void updateModuleMap(float x, float y, float z){		

		/*Different offsets along each of coordinate axes. This is done to get the position 
		 * of newly added component of the module (movable module) with respect to selected one*/
		float xMinusOffset = x - moduleLengthOffset;
		float xPlusOffset = x + moduleLengthOffset;
		float yMinusOffset = y - moduleLengthOffset;
		float yPlusOffset = y + moduleLengthOffset;
		float zPlusOffset = z + moduleLengthOffset;
		float zMinusOffset = z - moduleLengthOffset;		

		float xMinusLinkOffsetNr1 = x - linkOffsetNr1;
		float xPlusLinkOffsetNr1 = x + linkOffsetNr1;
		float yMinusLinkOffsetNr1 = y - linkOffsetNr1;
		float yPlusLinkOffsetNr1 = y + linkOffsetNr1;
		float zPlusLinkOffsetNr1 = z + linkOffsetNr1;
		float zMinusLinkOffsetNr1  = z - linkOffsetNr1;		

		float xMinusSecondLinkOffset = x - linkOffsetNr2;
		float xPlusSecondLinkOffset = x + linkOffsetNr2;
		float yMinusSecondLinkOffset = y - linkOffsetNr2;
		float yPlusSecondLinkOffset = y + linkOffsetNr2;
		float zPlusSecondLinkOffset = z + linkOffsetNr2;
		float zMinusSecondLinkOffset  = z - linkOffsetNr2;		

		float xMinusBoxOffsetNr1 = x - boxOffsetNr1;
		float xPlusBoxOffsetNr1 = x + boxOffsetNr1;
		float yMinusBoxOffsetNr1 = y - boxOffsetNr1;
		float yPlusBoxOffsetNr1 = y + boxOffsetNr1;
		float zPlusBoxOffsetNr1 = z + boxOffsetNr1;
		float zMinusBoxOffsetNr1 = z - boxOffsetNr1;		

		float xMinusBoxOffsetNr2 = x - boxOffsetNr2;
		float xPlusBoxOffsetNr2 = x + boxOffsetNr2;
		float yMinusBoxOffsetNr2 = y - boxOffsetNr2;
		float yPlusBoxOffsetNr2 = y + boxOffsetNr2;
		float zPlusBoxOffsetNr2 = z + boxOffsetNr2;
		float zMinusBoxOffsetNr2 = z - boxOffsetNr2;

		/*Array containing the data for adding the new movable component(module) with respect to selected module.
		 * The format of the object is: (connector number on selected module, the rotation of selected module, 
		 * the rotation of new movable module, the position of new movable module.  So the logic is, if connector
		 * number is for example 0 and selected module rotation is MTRANSimulation.ORI2, then new module should
		 * have rotation MTRANSimulation.ORI2 and the position of it will be new new Vector3f(xMinusOffset,y,z).
		 * (Look the first entry in array)*/ 
		ModuleMapEntryHelper[] moduleMap = {
				/*ConnectorNr0*/
				new ModuleMapEntryHelper(connectorNr0,MTRANSimulation.ORI2,MTRANSimulation.ORI2,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntryHelper(connectorNr0,MTRANSimulation.ORI2X,MTRANSimulation.ORI2X,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntryHelper(connectorNr0,MTRANSimulation.ORI2Y,MTRANSimulation.ORI2Y,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntryHelper(connectorNr0,MTRANSimulation.ORI2XY,MTRANSimulation.ORI2XY,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntryHelper(connectorNr0,MTRANSimulation.ORI1,MTRANSimulation.ORI1,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(connectorNr0,MTRANSimulation.ORI1X,MTRANSimulation.ORI1X,new Vector3f(x,yMinusOffset,z)),				
				new ModuleMapEntryHelper(connectorNr0,MTRANSimulation.ORI1Y,MTRANSimulation.ORI1Y,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(connectorNr0,MTRANSimulation.ORI1XY,MTRANSimulation.ORI1XY,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(connectorNr0,MTRANSimulation.ORI3,MTRANSimulation.ORI3,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(connectorNr0,MTRANSimulation.ORI3X,MTRANSimulation.ORI3X,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(connectorNr0,MTRANSimulation.ORI3Y,MTRANSimulation.ORI3Y,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(connectorNr0,MTRANSimulation.ORI3XY,MTRANSimulation.ORI3XY,new Vector3f(x,y,zMinusOffset)),

				/* Here the logic is changing and it is, if connector number is for example 1 and  selected module rotation is MTRANSimulation.ORI2, then the
				 * blueBox component of MTRAN module should have rotation MTRANSimulation.ORI3 and the position of it will be new Vector3f(xMinusBoxOffsetNr1,y,zPlusBoxOffsetNr2).
				 * (Look the first entry in array).This is quite specific to MTRAN*/
				/* ConnectorNr1. Components: 2 - blueBoxIndex, 1-linkIndex, 0 - redBoxIndex.*/
				new ModuleMapEntryHelper(connectorNr1,blueBoxIndex,MTRANSimulation.ORI2,MTRANSimulation.ORI3,new Vector3f(xMinusBoxOffsetNr1,y,zPlusBoxOffsetNr2)),
				new ModuleMapEntryHelper(connectorNr1,linkIndex,MTRANSimulation.ORI2,MTRANSimulation.ORI3,new Vector3f(xMinusSecondLinkOffset,y,zPlusLinkOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr1,redBoxIndex,MTRANSimulation.ORI2,MTRANSimulation.ORI3,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(connectorNr1,blueBoxIndex,MTRANSimulation.ORI2X,MTRANSimulation.ORI1XY,new Vector3f(xMinusBoxOffsetNr1,yPlusBoxOffsetNr2,z)),
				new ModuleMapEntryHelper(connectorNr1,linkIndex,MTRANSimulation.ORI2X,MTRANSimulation.ORI1XY,new Vector3f(xMinusSecondLinkOffset,yPlusLinkOffsetNr1,z)),
				new ModuleMapEntryHelper(connectorNr1,redBoxIndex,MTRANSimulation.ORI2X,MTRANSimulation.ORI1XY,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(connectorNr1,blueBoxIndex,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3Y,new Vector3f(xPlusBoxOffsetNr1,y,zMinusBoxOffsetNr2)),
				new ModuleMapEntryHelper(connectorNr1,linkIndex,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3Y,new Vector3f(xPlusSecondLinkOffset,y,zMinusLinkOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr1,redBoxIndex,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3Y,new Vector3f(x,y,zMinusOffset)),				
				new ModuleMapEntryHelper(connectorNr1,blueBoxIndex,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1XY,new Vector3f(xPlusBoxOffsetNr1,yPlusBoxOffsetNr2,z)),
				new ModuleMapEntryHelper(connectorNr1,linkIndex,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1XY,new Vector3f(xPlusSecondLinkOffset,yPlusLinkOffsetNr1,z)),
				new ModuleMapEntryHelper(connectorNr1,redBoxIndex,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1XY,new Vector3f(x,yPlusOffset,z)),				
				new ModuleMapEntryHelper(connectorNr1,blueBoxIndex,MTRANSimulation.ORI1,MTRANSimulation.ORI3X,new Vector3f(x,yMinusBoxOffsetNr1,zPlusBoxOffsetNr2)),
				new ModuleMapEntryHelper(connectorNr1,linkIndex,MTRANSimulation.ORI1,MTRANSimulation.ORI3X,new Vector3f(x,yMinusSecondLinkOffset,zPlusLinkOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr1,redBoxIndex,MTRANSimulation.ORI1,MTRANSimulation.ORI3X,new Vector3f(x,y,zPlusOffset)),				
				new ModuleMapEntryHelper(connectorNr1,blueBoxIndex,MTRANSimulation.ORI1X,MTRANSimulation.ORI2X,new Vector3f(xMinusBoxOffsetNr2,yMinusBoxOffsetNr1,z)),
				new ModuleMapEntryHelper(connectorNr1,linkIndex,MTRANSimulation.ORI1X,MTRANSimulation.ORI2X,new Vector3f(xMinusLinkOffsetNr1,yMinusSecondLinkOffset,z)),				
				new ModuleMapEntryHelper(connectorNr1,redBoxIndex,MTRANSimulation.ORI1X,MTRANSimulation.ORI2X,new Vector3f(xMinusOffset,y,z)),				
				new ModuleMapEntryHelper(connectorNr1,blueBoxIndex,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3XY,new Vector3f(x,yPlusBoxOffsetNr1,zMinusBoxOffsetNr2)),
				new ModuleMapEntryHelper(connectorNr1,linkIndex,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3XY,new Vector3f(x,yPlusSecondLinkOffset,zMinusLinkOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr1,redBoxIndex,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3XY,new Vector3f(x,y,zMinusOffset)),				
				new ModuleMapEntryHelper(connectorNr1,blueBoxIndex,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2X,new Vector3f(xMinusBoxOffsetNr2,yPlusBoxOffsetNr1,z)),
				new ModuleMapEntryHelper(connectorNr1,linkIndex,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2X,new Vector3f(xMinusLinkOffsetNr1,yPlusSecondLinkOffset,z)),
				new ModuleMapEntryHelper(connectorNr1,redBoxIndex,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2X,new Vector3f(xMinusOffset,y,z)),				
				new ModuleMapEntryHelper(connectorNr1,blueBoxIndex,MTRANSimulation.ORI3,MTRANSimulation.ORI2Y,new Vector3f(xPlusBoxOffsetNr2,y,zPlusBoxOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr1,linkIndex,MTRANSimulation.ORI3,MTRANSimulation.ORI2Y,new Vector3f(xPlusLinkOffsetNr1,y,zPlusSecondLinkOffset)),
				new ModuleMapEntryHelper(connectorNr1,redBoxIndex,MTRANSimulation.ORI3,MTRANSimulation.ORI2Y,new Vector3f(xPlusOffset,y,z)),				
				new ModuleMapEntryHelper(connectorNr1,blueBoxIndex,MTRANSimulation.ORI3X,MTRANSimulation.ORI1Y,new Vector3f(x,yPlusBoxOffsetNr2,zPlusBoxOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr1,linkIndex,MTRANSimulation.ORI3X,MTRANSimulation.ORI1Y,new Vector3f(x,yPlusLinkOffsetNr1,zPlusSecondLinkOffset)),
				new ModuleMapEntryHelper(connectorNr1,redBoxIndex,MTRANSimulation.ORI3X,MTRANSimulation.ORI1Y,new Vector3f(x,yPlusOffset,z)),				
				new ModuleMapEntryHelper(connectorNr1,blueBoxIndex,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2,new Vector3f(xMinusBoxOffsetNr2,y,zMinusBoxOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr1,linkIndex,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2,new Vector3f(xMinusLinkOffsetNr1,y,zMinusSecondLinkOffset)),
				new ModuleMapEntryHelper(connectorNr1,redBoxIndex,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2,new Vector3f(xMinusOffset,y,z)),				
				new ModuleMapEntryHelper(connectorNr1,blueBoxIndex,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1Y,new Vector3f(x,yPlusBoxOffsetNr2,zMinusBoxOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr1,linkIndex,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1Y,new Vector3f(x,yPlusLinkOffsetNr1,zMinusSecondLinkOffset)),
				new ModuleMapEntryHelper(connectorNr1,redBoxIndex,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1Y,new Vector3f(x,yPlusOffset,z)),
				/*ConnectorNr2*/				
				new ModuleMapEntryHelper(connectorNr2,blueBoxIndex,MTRANSimulation.ORI2,MTRANSimulation.ORI3Y,new Vector3f(xMinusBoxOffsetNr1,y,zMinusBoxOffsetNr2)),
				new ModuleMapEntryHelper(connectorNr2,linkIndex,MTRANSimulation.ORI2,MTRANSimulation.ORI3Y,new Vector3f(xMinusSecondLinkOffset,y,zMinusLinkOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr2,redBoxIndex,MTRANSimulation.ORI2,MTRANSimulation.ORI3Y,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(connectorNr2,blueBoxIndex,MTRANSimulation.ORI2X,MTRANSimulation.ORI1X,new Vector3f(xMinusBoxOffsetNr1,yMinusBoxOffsetNr2,z)),
				new ModuleMapEntryHelper(connectorNr2,linkIndex,MTRANSimulation.ORI2X,MTRANSimulation.ORI1X,new Vector3f(xMinusSecondLinkOffset,yMinusLinkOffsetNr1,z)),
				new ModuleMapEntryHelper(connectorNr2,redBoxIndex,MTRANSimulation.ORI2X,MTRANSimulation.ORI1X,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(connectorNr2,blueBoxIndex,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3,new Vector3f(xPlusBoxOffsetNr1,y,zPlusBoxOffsetNr2)),
				new ModuleMapEntryHelper(connectorNr2,linkIndex,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3,new Vector3f(xPlusSecondLinkOffset,y,zPlusLinkOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr2,redBoxIndex,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3,new Vector3f(x,y,zPlusOffset)),				
				new ModuleMapEntryHelper(connectorNr2,blueBoxIndex,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1X,new Vector3f(xPlusBoxOffsetNr1,yMinusBoxOffsetNr2,z)),
				new ModuleMapEntryHelper(connectorNr2,linkIndex,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1X,new Vector3f(xPlusSecondLinkOffset,yMinusLinkOffsetNr1,z)),
				new ModuleMapEntryHelper(connectorNr2,redBoxIndex,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1X,new Vector3f(x,yMinusOffset,z)),				
				new ModuleMapEntryHelper(connectorNr2,blueBoxIndex,MTRANSimulation.ORI1,MTRANSimulation.ORI3XY,new Vector3f(x,yMinusBoxOffsetNr1,zMinusBoxOffsetNr2)),
				new ModuleMapEntryHelper(connectorNr2,linkIndex,MTRANSimulation.ORI1,MTRANSimulation.ORI3XY,new Vector3f(x,yMinusSecondLinkOffset,zMinusLinkOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr2,redBoxIndex,MTRANSimulation.ORI1,MTRANSimulation.ORI3XY,new Vector3f(x,y,zMinusOffset)),				
				new ModuleMapEntryHelper(connectorNr2,blueBoxIndex,MTRANSimulation.ORI1X,MTRANSimulation.ORI2XY,new Vector3f(xPlusBoxOffsetNr2,yMinusBoxOffsetNr1,z)),
				new ModuleMapEntryHelper(connectorNr2,linkIndex,MTRANSimulation.ORI1X,MTRANSimulation.ORI2XY,new Vector3f(xPlusLinkOffsetNr1,yMinusSecondLinkOffset,z)),				
				new ModuleMapEntryHelper(connectorNr2,redBoxIndex,MTRANSimulation.ORI1X,MTRANSimulation.ORI2XY,new Vector3f(xPlusOffset,y,z)),				
				new ModuleMapEntryHelper(connectorNr2,blueBoxIndex,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3X,new Vector3f(x,yPlusBoxOffsetNr1,zPlusBoxOffsetNr2)),
				new ModuleMapEntryHelper(connectorNr2,linkIndex,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3X,new Vector3f(x,yPlusSecondLinkOffset,zPlusLinkOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr2,redBoxIndex,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3X,new Vector3f(x,y,zPlusOffset)),				
				new ModuleMapEntryHelper(connectorNr2,blueBoxIndex,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2XY,new Vector3f(xPlusBoxOffsetNr2,yPlusBoxOffsetNr1,z)),
				new ModuleMapEntryHelper(connectorNr2,linkIndex,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2XY,new Vector3f(xPlusLinkOffsetNr1,yPlusSecondLinkOffset,z)),
				new ModuleMapEntryHelper(connectorNr2,redBoxIndex,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2XY,new Vector3f(xPlusOffset,y,z)),				
				new ModuleMapEntryHelper(connectorNr2,blueBoxIndex,MTRANSimulation.ORI3,MTRANSimulation.ORI2,new Vector3f(xMinusBoxOffsetNr2,y,zPlusBoxOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr2,linkIndex,MTRANSimulation.ORI3,MTRANSimulation.ORI2,new Vector3f(xMinusLinkOffsetNr1,y,zPlusSecondLinkOffset)),
				new ModuleMapEntryHelper(connectorNr2,redBoxIndex,MTRANSimulation.ORI3,MTRANSimulation.ORI2,new Vector3f(xMinusOffset,y,z)),				
				new ModuleMapEntryHelper(connectorNr2,blueBoxIndex,MTRANSimulation.ORI3X,MTRANSimulation.ORI1,new Vector3f(x,yMinusBoxOffsetNr2,zPlusBoxOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr2,linkIndex,MTRANSimulation.ORI3X,MTRANSimulation.ORI1,new Vector3f(x,yMinusLinkOffsetNr1,zPlusSecondLinkOffset)),
				new ModuleMapEntryHelper(connectorNr2,redBoxIndex,MTRANSimulation.ORI3X,MTRANSimulation.ORI1,new Vector3f(x,yMinusOffset,z)),				
				new ModuleMapEntryHelper(connectorNr2,blueBoxIndex,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2Y,new Vector3f(xPlusBoxOffsetNr2,y,zMinusBoxOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr2,linkIndex,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2Y,new Vector3f(xPlusLinkOffsetNr1,y,zMinusSecondLinkOffset)),
				new ModuleMapEntryHelper(connectorNr2,redBoxIndex,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2Y,new Vector3f(xPlusOffset,y,z)),				
				new ModuleMapEntryHelper(connectorNr2,blueBoxIndex,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1,new Vector3f(x,yMinusBoxOffsetNr2,zMinusBoxOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr2,linkIndex,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1,new Vector3f(x,yMinusLinkOffsetNr1,zMinusSecondLinkOffset)),
				new ModuleMapEntryHelper(connectorNr2,redBoxIndex,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1,new Vector3f(x,yMinusOffset,z)),				
				/*ConnectorNr3*/				
				new ModuleMapEntryHelper(connectorNr3,MTRANSimulation.ORI2,MTRANSimulation.ORI2,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntryHelper(connectorNr3,MTRANSimulation.ORI2X,MTRANSimulation.ORI2X,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntryHelper(connectorNr3,MTRANSimulation.ORI2Y,MTRANSimulation.ORI2Y,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntryHelper(connectorNr3,MTRANSimulation.ORI2XY,MTRANSimulation.ORI2XY,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntryHelper(connectorNr3,MTRANSimulation.ORI1,MTRANSimulation.ORI1,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(connectorNr3,MTRANSimulation.ORI1X,MTRANSimulation.ORI1X,new Vector3f(x,yPlusOffset,z)),				
				new ModuleMapEntryHelper(connectorNr3,MTRANSimulation.ORI1Y,MTRANSimulation.ORI1Y,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(connectorNr3,MTRANSimulation.ORI1XY,MTRANSimulation.ORI1XY,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(connectorNr3,MTRANSimulation.ORI3,MTRANSimulation.ORI3,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(connectorNr3,MTRANSimulation.ORI3X,MTRANSimulation.ORI3X,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(connectorNr3,MTRANSimulation.ORI3Y,MTRANSimulation.ORI3Y,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(connectorNr3,MTRANSimulation.ORI3XY,MTRANSimulation.ORI3XY,new Vector3f(x,y,zPlusOffset)),
				/*Connector Nr4*/
				new ModuleMapEntryHelper(connectorNr4,blueBoxIndex,MTRANSimulation.ORI2,MTRANSimulation.ORI3Y,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(connectorNr4,linkIndex,MTRANSimulation.ORI2,MTRANSimulation.ORI3Y,new Vector3f(xPlusSecondLinkOffset,y,zPlusLinkOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr4,redBoxIndex,MTRANSimulation.ORI2,MTRANSimulation.ORI3Y,new Vector3f(xPlusBoxOffsetNr1,y,zPlusBoxOffsetNr2)),
				new ModuleMapEntryHelper(connectorNr4,blueBoxIndex,MTRANSimulation.ORI2X,MTRANSimulation.ORI1X,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(connectorNr4,linkIndex,MTRANSimulation.ORI2X,MTRANSimulation.ORI1X,new Vector3f(xPlusSecondLinkOffset,yPlusLinkOffsetNr1,z)),
				new ModuleMapEntryHelper(connectorNr4,redBoxIndex,MTRANSimulation.ORI2X,MTRANSimulation.ORI1X,new Vector3f(xPlusBoxOffsetNr1,yPlusBoxOffsetNr2,z)),
				new ModuleMapEntryHelper(connectorNr4,blueBoxIndex,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(connectorNr4,linkIndex,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3,new Vector3f(xMinusSecondLinkOffset,y,zMinusLinkOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr4,redBoxIndex,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3,new Vector3f(xMinusBoxOffsetNr1,y,zMinusBoxOffsetNr2)),				
				new ModuleMapEntryHelper(connectorNr4,blueBoxIndex,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1X,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(connectorNr4,linkIndex,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1X,new Vector3f(xMinusSecondLinkOffset,yPlusLinkOffsetNr1,z)),
				new ModuleMapEntryHelper(connectorNr4,redBoxIndex,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1X,new Vector3f(xMinusBoxOffsetNr1,yPlusBoxOffsetNr2,z)),				
				new ModuleMapEntryHelper(connectorNr4,blueBoxIndex,MTRANSimulation.ORI1,MTRANSimulation.ORI3XY,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(connectorNr4,linkIndex,MTRANSimulation.ORI1,MTRANSimulation.ORI3XY,new Vector3f(x,yPlusSecondLinkOffset,zPlusLinkOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr4,redBoxIndex,MTRANSimulation.ORI1,MTRANSimulation.ORI3XY,new Vector3f(x,yPlusBoxOffsetNr1,zPlusBoxOffsetNr2)),				
				new ModuleMapEntryHelper(connectorNr4,blueBoxIndex,MTRANSimulation.ORI1X,MTRANSimulation.ORI2XY,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntryHelper(connectorNr4,linkIndex,MTRANSimulation.ORI1X,MTRANSimulation.ORI2XY,new Vector3f(xMinusLinkOffsetNr1,yPlusSecondLinkOffset,z)),				
				new ModuleMapEntryHelper(connectorNr4,redBoxIndex,MTRANSimulation.ORI1X,MTRANSimulation.ORI2XY,new Vector3f(xMinusBoxOffsetNr2,yPlusBoxOffsetNr1,z)),				
				new ModuleMapEntryHelper(connectorNr4,blueBoxIndex,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3X,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(connectorNr4,linkIndex,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3X,new Vector3f(x,yMinusSecondLinkOffset,zMinusLinkOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr4,redBoxIndex,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3X,new Vector3f(x,yMinusBoxOffsetNr1,zMinusBoxOffsetNr2)),				
				new ModuleMapEntryHelper(connectorNr4,blueBoxIndex,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2XY,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntryHelper(connectorNr4,linkIndex,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2XY,new Vector3f(xMinusLinkOffsetNr1,yMinusSecondLinkOffset,z)),
				new ModuleMapEntryHelper(connectorNr4,redBoxIndex,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2XY,new Vector3f(xMinusBoxOffsetNr2,yMinusBoxOffsetNr1,z)),				
				new ModuleMapEntryHelper(connectorNr4,blueBoxIndex,MTRANSimulation.ORI3,MTRANSimulation.ORI2,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntryHelper(connectorNr4,linkIndex,MTRANSimulation.ORI3,MTRANSimulation.ORI2,new Vector3f(xPlusLinkOffsetNr1,y,zMinusSecondLinkOffset)),
				new ModuleMapEntryHelper(connectorNr4,redBoxIndex,MTRANSimulation.ORI3,MTRANSimulation.ORI2,new Vector3f(xPlusBoxOffsetNr2,y,zMinusBoxOffsetNr1)),				
				new ModuleMapEntryHelper(connectorNr4,blueBoxIndex,MTRANSimulation.ORI3X,MTRANSimulation.ORI1,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(connectorNr4,linkIndex,MTRANSimulation.ORI3X,MTRANSimulation.ORI1,new Vector3f(x,yPlusLinkOffsetNr1,zMinusSecondLinkOffset)),
				new ModuleMapEntryHelper(connectorNr4,redBoxIndex,MTRANSimulation.ORI3X,MTRANSimulation.ORI1,new Vector3f(x,yPlusBoxOffsetNr2,zMinusBoxOffsetNr1)),				
				new ModuleMapEntryHelper(connectorNr4,blueBoxIndex,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2Y,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntryHelper(connectorNr4,linkIndex,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2Y,new Vector3f(xMinusLinkOffsetNr1,y,zPlusSecondLinkOffset)),
				new ModuleMapEntryHelper(connectorNr4,redBoxIndex,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2Y,new Vector3f(xMinusBoxOffsetNr2,y,zPlusBoxOffsetNr1)),				
				new ModuleMapEntryHelper(connectorNr4,blueBoxIndex,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntryHelper(connectorNr4,linkIndex,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1,new Vector3f(x,yPlusLinkOffsetNr1,zPlusSecondLinkOffset)),
				new ModuleMapEntryHelper(connectorNr4,redBoxIndex,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1,new Vector3f(x,yPlusBoxOffsetNr2,zPlusBoxOffsetNr1)),				
				/*Connector Nr5*/
				new ModuleMapEntryHelper(connectorNr5,blueBoxIndex,MTRANSimulation.ORI2,MTRANSimulation.ORI3,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(connectorNr5,linkIndex,MTRANSimulation.ORI2,MTRANSimulation.ORI3,new Vector3f(xPlusSecondLinkOffset,y,zMinusLinkOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr5,redBoxIndex,MTRANSimulation.ORI2,MTRANSimulation.ORI3,new Vector3f(xPlusBoxOffsetNr1,y,zMinusBoxOffsetNr2)),
				new ModuleMapEntryHelper(connectorNr5,blueBoxIndex,MTRANSimulation.ORI2X,MTRANSimulation.ORI1XY,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(connectorNr5,linkIndex,MTRANSimulation.ORI2X,MTRANSimulation.ORI1XY,new Vector3f(xPlusSecondLinkOffset,yMinusLinkOffsetNr1,z)),
				new ModuleMapEntryHelper(connectorNr5,redBoxIndex,MTRANSimulation.ORI2X,MTRANSimulation.ORI1XY,new Vector3f(xPlusBoxOffsetNr1,yMinusBoxOffsetNr2,z)),
				new ModuleMapEntryHelper(connectorNr5,blueBoxIndex,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3Y,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(connectorNr5,linkIndex,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3Y,new Vector3f(xMinusSecondLinkOffset,y,zPlusLinkOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr5,redBoxIndex,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3Y,new Vector3f(xMinusBoxOffsetNr1,y,zPlusBoxOffsetNr2)),				
				new ModuleMapEntryHelper(connectorNr5,blueBoxIndex,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1XY,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(connectorNr5,linkIndex,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1XY,new Vector3f(xMinusSecondLinkOffset,yMinusLinkOffsetNr1,z)),
				new ModuleMapEntryHelper(connectorNr5,redBoxIndex,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1XY,new Vector3f(xMinusBoxOffsetNr1,yMinusBoxOffsetNr2,z)),				
				new ModuleMapEntryHelper(connectorNr5,blueBoxIndex,MTRANSimulation.ORI1,MTRANSimulation.ORI3X,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntryHelper(connectorNr5,linkIndex,MTRANSimulation.ORI1,MTRANSimulation.ORI3X,new Vector3f(x,yPlusSecondLinkOffset,zMinusLinkOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr5,redBoxIndex,MTRANSimulation.ORI1,MTRANSimulation.ORI3X,new Vector3f(x,yPlusBoxOffsetNr1,zMinusBoxOffsetNr2)),				
				new ModuleMapEntryHelper(connectorNr5,blueBoxIndex,MTRANSimulation.ORI1X,MTRANSimulation.ORI2X,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntryHelper(connectorNr5,linkIndex,MTRANSimulation.ORI1X,MTRANSimulation.ORI2X,new Vector3f(xPlusLinkOffsetNr1,yPlusSecondLinkOffset,z)),				
				new ModuleMapEntryHelper(connectorNr5,redBoxIndex,MTRANSimulation.ORI1X,MTRANSimulation.ORI2X,new Vector3f(xPlusBoxOffsetNr2,yPlusBoxOffsetNr1,z)),				
				new ModuleMapEntryHelper(connectorNr5,blueBoxIndex,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3XY,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntryHelper(connectorNr5,linkIndex,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3XY,new Vector3f(x,yMinusSecondLinkOffset,zPlusLinkOffsetNr1)),
				new ModuleMapEntryHelper(connectorNr5,redBoxIndex,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3XY,new Vector3f(x,yMinusBoxOffsetNr1,zPlusBoxOffsetNr2)),				
				new ModuleMapEntryHelper(connectorNr5,blueBoxIndex,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2X,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntryHelper(connectorNr5,linkIndex,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2X,new Vector3f(xPlusLinkOffsetNr1,yMinusSecondLinkOffset,z)),
				new ModuleMapEntryHelper(connectorNr5,redBoxIndex,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2X,new Vector3f(xPlusBoxOffsetNr2,yMinusBoxOffsetNr1,z)),				
				new ModuleMapEntryHelper(connectorNr5,blueBoxIndex,MTRANSimulation.ORI3,MTRANSimulation.ORI2Y,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntryHelper(connectorNr5,linkIndex,MTRANSimulation.ORI3,MTRANSimulation.ORI2Y,new Vector3f(xMinusLinkOffsetNr1,y,zMinusSecondLinkOffset)),
				new ModuleMapEntryHelper(connectorNr5,redBoxIndex,MTRANSimulation.ORI3,MTRANSimulation.ORI2Y,new Vector3f(xMinusBoxOffsetNr2,y,zMinusBoxOffsetNr1)),				
				new ModuleMapEntryHelper(connectorNr5,blueBoxIndex,MTRANSimulation.ORI3X,MTRANSimulation.ORI1Y,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(connectorNr5,linkIndex,MTRANSimulation.ORI3X,MTRANSimulation.ORI1Y,new Vector3f(x,yMinusLinkOffsetNr1,zMinusSecondLinkOffset)),
				new ModuleMapEntryHelper(connectorNr5,redBoxIndex,MTRANSimulation.ORI3X,MTRANSimulation.ORI1Y,new Vector3f(x,yMinusBoxOffsetNr2,zMinusBoxOffsetNr1)),				
				new ModuleMapEntryHelper(connectorNr5,blueBoxIndex,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntryHelper(connectorNr5,linkIndex,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2,new Vector3f(xPlusLinkOffsetNr1,y,zPlusSecondLinkOffset)),
				new ModuleMapEntryHelper(connectorNr5,redBoxIndex,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2,new Vector3f(xPlusBoxOffsetNr2,y,zPlusBoxOffsetNr1)),				
				new ModuleMapEntryHelper(connectorNr5,blueBoxIndex,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1Y,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntryHelper(connectorNr5,linkIndex,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1Y,new Vector3f(x,yMinusLinkOffsetNr1,zPlusSecondLinkOffset)),
				new ModuleMapEntryHelper(connectorNr5,redBoxIndex,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1Y,new Vector3f(x,yMinusBoxOffsetNr2,zPlusBoxOffsetNr1))				
		};		
		this.moduleMap = moduleMap;		
	}		

	/**
	 * Moves newMovableModuleComponent of MTRAN according(respectively) to selected module preconditions,
	 * like connector number, initial rotation of selected module component, and so on.
	 * This method is so-called "Primitive operation" for TEMPLATE method,called "moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule)". 
	 * @param connectorNr, the connector number on selected MTRAN module.
	 * @param selectedModule,  the MTRAN module selected in simulation environment.
	 * @param movableModuleComponent, the new MTRAN module component to move respectively to selected one.
	 * @param rotationQuatComponent, the rotation of current MTRAN component of selected module.	 
	 */		
	public void moveModuleComponentAccording(int connectorNr,Module selectedModule, JMEModuleComponent movableModuleComponent,Quaternion rotationQuatComponent) {
		componentCounter++;	
		/*Loop through and locate the object matching the description of current component(also selected module).*/
		for (int i=0; i<moduleMap.length;i++){			
			if (connectorNr ==connectorNr0||connectorNr ==connectorNr3){// connectors Nr 0 and 3 are special(simpler)cases than others, because they always are aligned with one of the coordinate axes 
				if (moduleMap[i].getConnectorNr()==connectorNr && moduleMap[i].getInitialRotation().getRotation().equals(rotationQuatComponent)){
					/*If component(module) already exists at current position, delete movableModuleComponent and newly added module.*/
					if (componentExitst(moduleMap[i].getNewPosition(), searchTolerance)){
						BuilderHelper.deleteModule(movableModuleComponent.getModel());						
					}else{/*move the component to new position with new rotation*/
						moveModuleComponent(movableModuleComponent,moduleMap[i].getNewRotation(),moduleMap[i].getNewPosition());
					}
				}
			}else if (moduleMap[i].getConnectorNr()==connectorNr &&moduleMap[i].getComponentNr()==componentCounter && moduleMap[i].getInitialRotation().getRotation().equals(rotationQuatComponent)){
				/*If component(module) already exists at current position, delete movableModuleComponent and newly added module.*/
				if (componentExitst(moduleMap[i].getNewPosition(), searchTolerance)){					
					BuilderHelper.deleteModule(movableModuleComponent.getModel());											
				}else{/*move the component to new position with new rotation*/
				moveModuleComponent(movableModuleComponent,moduleMap[i].getNewRotation(),moduleMap[i].getNewPosition());
				}
			}
		} 
		// reset the counter to -1 so this method can be run in a loop.
		resetComponentCounter(componentCounter);		
	}	

	/**
	 * Resets the counter to -1, so methods using it can be run in a loop and handle three MTRAN components (from 0 to 2).
	 * @param componentCounter, the counter counting the number of components from 0 to 2.
	 */
	private void resetComponentCounter(int componentCounter){
		if (componentCounter ==blueBoxIndex){
			this.componentCounter =-1;
		}
	}

	/**
	 * Rotates selected MTRAN module component according to its initial rotation with opposite rotation.
	 * This method is so-called "Primitive operation" for TEMPLATE method, called "rotateModuleOpposite(Module selectedModule)". 	
	 * @param selectedModuleComponent,the module component selected in simulation environment.
	 * @param rotationQComponent,the rotation of selected component.	 
	 */
//	TODO MAYBE IS NOT USEFULL IN MTRAN CASE, BECAUSE THERE IS OFTEN THE CASE THAT THE SAME ACTIVE OR PASSIVE
//	BOXES CONNECT WITH THE SAME TYPE OF THE BOX (CONFLICT). TOO MUCH FLEXIBILITY TO USER.	
	public void rotateComponentOpposite(JMEModuleComponent selectedModuleComponent,Quaternion  rotationQComponent) {
		/*Locate matching rotation Quaternion in moduleRotationMap and rotate with opposite rotation Quaternion
		 * from the same entry in  moduleRotationMap*/
		for (int entry=0;entry<moduleRotationMap.length;entry++){
			if (rotationQComponent.equals(moduleRotationMap[entry].getRotation().getRotation())){
				rotateModuleComponent(selectedModuleComponent,moduleRotationMap[entry].getRotationOppositeValue().getRotation());
			}
		}
		/*Swap the positions of red  and blue boxes composing MTRAN module. This is because
		 * it is not enough to just rotate them */
		swapComponentsPositions(selectedModuleComponent.getModel(),redBoxIndex,  blueBoxIndex);		
	}	

	/**
	 * Rotates selected MTRAN module component with standard rotations, passed as a string.
	 * This method is so-called "Primitive Operation" for TEMPLATE method, called "rotateModuleSpecifically(Module selectedModule,String rotationName)". 
	 * @param selectedModuleComponent,the module component selected in simulation environment.	 
	 * @param rotationName,the name of standard(specific) rotation of the module.	 
	 */	
	public void rotateComponentSpecifically(JMEModuleComponent selectedModuleComponent, String rotationName) {
		componentCounter++;	
		/*link is never moved because it is always in the center, so we need to move all components respectively to it */
		VectorDescription positionLink = selectedModuleComponent.getModel().getComponent(linkIndex).getPosition();//red half-cylinder box		

		/*Get x,y,z of link position.*/
		float xLink = positionLink.getX();
		float yLink = positionLink.getY();
		float zLink = positionLink.getZ();

		/*Update the array of objects storing information about all available specific rotations of MTRAN modules
		 * and  and position of its components relative to the position of the component called link. */
		ModuleRotationMapEntryHelper[] currentRotationAdditionalMap = updateModuleRotationAdditionalMap(xLink, yLink, zLink);

		/*Find match and in case of link rotate it and in case of red and blue boxes move respectively to link*/
		for (int entry=0;entry<currentRotationAdditionalMap.length;entry++){
			if (rotationName.equals(currentRotationAdditionalMap[entry].getRotationName())){
				if (currentRotationAdditionalMap[entry].getComponentNr()==componentCounter){//Red and Blue box
					moveModuleComponent(selectedModuleComponent,currentRotationAdditionalMap[entry].getRotation(),currentRotationAdditionalMap[entry].getComponentNewPosition());
				}else {//Link
					rotateModuleComponent(selectedModuleComponent,currentRotationAdditionalMap[entry].getRotation().getRotation());
				}
			}
		}
		// reset the counter to -1 so this method can be run in a loop.
		resetComponentCounter(componentCounter);
	}


	/**
	 * Updates and returns the array of objects containing the information about all available specific
	 * rotations of MTRAN module and position of its components relative to the position of the component
	 * called link. 
	 * @param x, the amount of x coordinate of current position of the link
	 * @param y, the amount of y coordinate of current position of the link
	 * @param z, the amount of z coordinate of current position of the link
	 * @return moduleRotationAdditonalMap, updated array of objects.
	 */
	private  ModuleRotationMapEntryHelper[] updateModuleRotationAdditionalMap(float x, float y, float z){

		/*Different offsets along each of coordinate axes. This is done to get the position 
		 * of component of the module  with respect to the position of the link*/
		float xMinusSecondLinkOffset = x - linkOffsetNr2;
		float xPlusSecondLinkOffset = x + linkOffsetNr2;
		float yMinusSecondLinkOffset = y - linkOffsetNr2;
		float yPlusSecondLinkOffset = y + linkOffsetNr2;
		float zPlusSecondLinkOffset = z + linkOffsetNr2;
		float zMinusSecondLinkOffset  = z - linkOffsetNr2;	

		/*Array containing the data for rearranging the components of MTRAN module to specific rotation
		 * with respect to the position of the link*/ 
		ModuleRotationMapEntryHelper[] moduleRotationAdditonalMap = {
				new ModuleRotationMapEntryHelper(ori1,redBoxIndex,MTRANSimulation.ORI1,new Vector3f(x,yMinusSecondLinkOffset,z)),
				new ModuleRotationMapEntryHelper(ori1,blueBoxIndex,MTRANSimulation.ORI1,new Vector3f(x,yPlusSecondLinkOffset,z)),				
				new ModuleRotationMapEntryHelper(ori1x,redBoxIndex,MTRANSimulation.ORI1X,new Vector3f(x,yMinusSecondLinkOffset,z)),
				new ModuleRotationMapEntryHelper(ori1x,blueBoxIndex,MTRANSimulation.ORI1X,new Vector3f(x,yPlusSecondLinkOffset,z)),				
				new ModuleRotationMapEntryHelper(ori1y,redBoxIndex,MTRANSimulation.ORI1Y,new Vector3f(x,yPlusSecondLinkOffset,z)),
				new ModuleRotationMapEntryHelper(ori1y,blueBoxIndex,MTRANSimulation.ORI1Y,new Vector3f(x,yMinusSecondLinkOffset,z)),				
				new ModuleRotationMapEntryHelper(ori1xy,redBoxIndex,MTRANSimulation.ORI1XY,new Vector3f(x,yPlusSecondLinkOffset,z)),
				new ModuleRotationMapEntryHelper(ori1xy,blueBoxIndex,MTRANSimulation.ORI1XY,new Vector3f(x,yMinusSecondLinkOffset,z)),				
				new ModuleRotationMapEntryHelper(ori2,redBoxIndex,MTRANSimulation.ORI2,new Vector3f(xMinusSecondLinkOffset,y,z)),
				new ModuleRotationMapEntryHelper(ori2,blueBoxIndex,MTRANSimulation.ORI2,new Vector3f(xPlusSecondLinkOffset,y,z)),				
				new ModuleRotationMapEntryHelper(ori2x,redBoxIndex,MTRANSimulation.ORI2X,new Vector3f(xMinusSecondLinkOffset,y,z)),
				new ModuleRotationMapEntryHelper(ori2x,blueBoxIndex,MTRANSimulation.ORI2X,new Vector3f(xPlusSecondLinkOffset,y,z)),				
				new ModuleRotationMapEntryHelper(ori2y,redBoxIndex,MTRANSimulation.ORI2Y,new Vector3f(xPlusSecondLinkOffset,y,z)),
				new ModuleRotationMapEntryHelper(ori2y,blueBoxIndex,MTRANSimulation.ORI2Y,new Vector3f(xMinusSecondLinkOffset,y,z)),				
				new ModuleRotationMapEntryHelper(ori2xy,redBoxIndex,MTRANSimulation.ORI2XY,new Vector3f(xPlusSecondLinkOffset,y,z)),
				new ModuleRotationMapEntryHelper(ori2xy,blueBoxIndex,MTRANSimulation.ORI2XY,new Vector3f(xMinusSecondLinkOffset,y,z)),				
				new ModuleRotationMapEntryHelper(ori3,redBoxIndex,MTRANSimulation.ORI3,new Vector3f(x,y,zPlusSecondLinkOffset)),
				new ModuleRotationMapEntryHelper(ori3,blueBoxIndex,MTRANSimulation.ORI3,new Vector3f(x,y,zMinusSecondLinkOffset)),				
				new ModuleRotationMapEntryHelper(ori3x,redBoxIndex,MTRANSimulation.ORI3X,new Vector3f(x,y,zPlusSecondLinkOffset)),
				new ModuleRotationMapEntryHelper(ori3x,blueBoxIndex,MTRANSimulation.ORI3X,new Vector3f(x,y,zMinusSecondLinkOffset)),				
				new ModuleRotationMapEntryHelper(ori3y,redBoxIndex,MTRANSimulation.ORI3Y,new Vector3f(x,y,zMinusSecondLinkOffset)),
				new ModuleRotationMapEntryHelper(ori3y,blueBoxIndex,MTRANSimulation.ORI3Y,new Vector3f(x,y,zPlusSecondLinkOffset)),				
				new ModuleRotationMapEntryHelper(ori3xy,redBoxIndex,MTRANSimulation.ORI3XY,new Vector3f(x,y,zMinusSecondLinkOffset)),
				new ModuleRotationMapEntryHelper(ori3xy,blueBoxIndex,MTRANSimulation.ORI3XY,new Vector3f(x,y,zPlusSecondLinkOffset))
		};		
		return moduleRotationAdditonalMap;
	}

	/**
	 * Additional method for implementing unique properties of MTRAN modular robot components. Like for example 
	 * MTRAN has several more specific rotations, which are implemented to make the construction
	 * more flexible.
	 * This method is so-called "Primitive operation" for TEMPLATE method, called "variateModuleProperties(Module selectedModule)". 	
	 * @param selectedModuleComponent,the module component selected in simulation environment.
	 * @param rotationQComponent,the rotation of selected component.	 
	 */	
	public void variateComponentProperties(JMEModuleComponent selectedModuleComponent,Quaternion  rotationQComponent){
		for (int entry=0;entry<moduleRotationMap.length;entry++){
			if (rotationQComponent.equals(moduleRotationMap[entry].getRotation().getRotation())){
				rotateModuleComponent(selectedModuleComponent,moduleRotationMap[entry].getRotationAroundAxisValue().getRotation());
			}
		}		
	}	
}

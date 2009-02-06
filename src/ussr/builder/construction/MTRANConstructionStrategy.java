package ussr.builder.construction;

import java.awt.Color;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import ussr.description.geometry.VectorDescription;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.samples.mtran.MTRANSimulation;

/**
 *  The main responsibility of this class is to take care of construction functions for
 * construction of MTRAN modular robot's morphology. Here module consists of components. To be more precise
 * MTRAN module consists of three components: two half boxes-cylinders(blue and red)and one link(black).
 * Connectors are considered to be as parts of components. MTRAN module has 6 connectors, numbered as: 
 * 0,1,2,3,4 and 5. All methods are specific to MTRAN module design.
 * @author Konstantinas
 *
 */
public class MTRANConstructionStrategy extends ModularRobotConstructionStrategy {
	
	/**
	 * The length of the module. Used to move each component of new movable module along the selected module.
	 * Particularly useful when moving the new movable module components along the axis which is the same as selected module.
	 */
	private final static  float moduleLengthOffset =0.14f;
	
	/**
	 * The offset for moving link in between(middle) two half boxes-cylinders along the new movable module respectively to selected module.
	 */
	private final static float linkOffsetNr1 = 0.1f;

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
	

	@Override
	public void moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule) {
		
		/*Amount of components constituting selectedModule*/
		int amountComponents =selectedModule.getNumberOfComponents();
        
		/*Loop through each component of selected module and move the component of new movable module 
		 * with respect to selected module component. MTRAN module has three components(two half boxes-cylinders
		 * (blue and red)and one link(black).*/
		for (int component=0; component<amountComponents;component++){
			
			/* The current component of selected module*/
			JMEModuleComponent currentComponent = (JMEModuleComponent)selectedModule.getComponent(component);
			VectorDescription positionComponent = currentComponent.getPosition();
			Quaternion  rotationQuatComponent = selectedModule.getComponent(component).getRotation().getRotation();
			
			/*Get x,y,z of selected module component position. In USSR x axis is horizontal,
			 * y axis is perpendicular to horizontal axis, z is facing the user*/
			float x = positionComponent.getX();
			float y = positionComponent.getY();
			float z = positionComponent.getZ();			
			
			/*Update the array of objects storing information about all available rotations of MTRAN modules
			 * and  rotations together with positions of newly added modules(also components) with respect to specific connector*/
			ModuleMapEntry[] currentModuleMap = updateModuleMap(x,y,z);			
			
			/*The current component of new movable module*/ 
			JMEModuleComponent movableModuleComponent = (JMEModuleComponent)newMovableModule.getComponent(component);

			/*Loop through and locate the object matching the description of current component(also module) .
			 *After that move movable component, with respect to selected component of the module using
			 *extracted information form the object describing it*/
			for (int i=0; i<currentModuleMap.length;i++){
				if (connectorNr ==0||connectorNr ==3){// connectors Nr 0 and 3 are special(simpler)cases than others, because they always are aligned with one of the coordinate axes 
				if (currentModuleMap[i].getConnectorNr()==connectorNr && currentModuleMap[i].getInitialRotation().getRotation().equals(rotationQuatComponent)){
					moveModuleComponent(movableModuleComponent,currentModuleMap[i].getNewRotation(),currentModuleMap[i].getNewPosition());
				}
				}else if (currentModuleMap[i].getConnectorNr()==connectorNr &&currentModuleMap[i].getComponentNr()==component && currentModuleMap[i].getInitialRotation().getRotation().equals(rotationQuatComponent)){
					moveModuleComponent(movableModuleComponent,currentModuleMap[i].getNewRotation(),currentModuleMap[i].getNewPosition());
				}			
			}			
		}		
	}
	
	
	/**
	 * Updates and returns the array containing the information about all available rotations of MTRAN module
     * components and  rotations together with positions of newly added modules(components) with respect to specific connector
	 * @param x, the value of x coordinate of current position of component
	 * @param y, the value of y coordinate of current position of component
	 * @param z,the value of z coordinate of current position of component
	 * @return moduleMap, updated array of objects.
	 */
	private  ModuleMapEntry[] updateModuleMap(float x, float y, float z){		
		
		/*Different offsets along each of coordinate axes.
		 * This is done to get the position of newly added component of the module (movable module) with respect to selected one*/
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
		 * The format of the object is: (connector number on selected module, the rotation of selected module, the rotation of new movable module, the position of new movable module*/ 
		ModuleMapEntry[] moduleMap = {
				// Connector Nr:0
				new ModuleMapEntry(0,MTRANSimulation.ORI2,MTRANSimulation.ORI2,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntry(0,MTRANSimulation.ORI2X,MTRANSimulation.ORI2X,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntry(0,MTRANSimulation.ORI2Y,MTRANSimulation.ORI2Y,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntry(0,MTRANSimulation.ORI2XY,MTRANSimulation.ORI2XY,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntry(0,MTRANSimulation.ORI1,MTRANSimulation.ORI1,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntry(0,MTRANSimulation.ORI1X,MTRANSimulation.ORI1X,new Vector3f(x,yPlusOffset,z)),				
				new ModuleMapEntry(0,MTRANSimulation.ORI1Y,MTRANSimulation.ORI1Y,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntry(0,MTRANSimulation.ORI1XY,MTRANSimulation.ORI1XY,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntry(0,MTRANSimulation.ORI3,MTRANSimulation.ORI3,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntry(0,MTRANSimulation.ORI3X,MTRANSimulation.ORI3X,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntry(0,MTRANSimulation.ORI3Y,MTRANSimulation.ORI3Y,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntry(0,MTRANSimulation.ORI3XY,MTRANSimulation.ORI3XY,new Vector3f(x,y,zMinusOffset)),
				// Connector Nr:1. Components: 2 - blue half-cylinder box, 1-link, 0 - red half-cylinder box.
				new ModuleMapEntry(1,2,MTRANSimulation.ORI2,MTRANSimulation.ORI3,new Vector3f(xMinusBoxOffsetNr1,y,zPlusBoxOffsetNr2)),
				new ModuleMapEntry(1,1,MTRANSimulation.ORI2,MTRANSimulation.ORI3,new Vector3f(xMinusSecondLinkOffset,y,zPlusLinkOffsetNr1)),
				new ModuleMapEntry(1,0,MTRANSimulation.ORI2,MTRANSimulation.ORI3,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntry(1,2,MTRANSimulation.ORI2X,MTRANSimulation.ORI1XY,new Vector3f(xMinusBoxOffsetNr1,yPlusBoxOffsetNr2,z)),
				new ModuleMapEntry(1,1,MTRANSimulation.ORI2X,MTRANSimulation.ORI1XY,new Vector3f(xMinusSecondLinkOffset,yPlusLinkOffsetNr1,z)),
				new ModuleMapEntry(1,0,MTRANSimulation.ORI2X,MTRANSimulation.ORI1XY,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntry(1,2,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3Y,new Vector3f(xPlusBoxOffsetNr1,y,zMinusBoxOffsetNr2)),
				new ModuleMapEntry(1,1,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3Y,new Vector3f(xPlusSecondLinkOffset,y,zMinusLinkOffsetNr1)),
				new ModuleMapEntry(1,0,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3Y,new Vector3f(x,y,zMinusOffset)),				
				new ModuleMapEntry(1,2,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1XY,new Vector3f(xPlusBoxOffsetNr1,yPlusBoxOffsetNr2,z)),
				new ModuleMapEntry(1,1,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1XY,new Vector3f(xPlusSecondLinkOffset,yPlusLinkOffsetNr1,z)),
				new ModuleMapEntry(1,0,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1XY,new Vector3f(x,yPlusOffset,z)),				
				new ModuleMapEntry(1,2,MTRANSimulation.ORI1,MTRANSimulation.ORI3X,new Vector3f(x,yMinusBoxOffsetNr1,zPlusBoxOffsetNr2)),
				new ModuleMapEntry(1,1,MTRANSimulation.ORI1,MTRANSimulation.ORI3X,new Vector3f(x,yMinusSecondLinkOffset,zPlusLinkOffsetNr1)),
				new ModuleMapEntry(1,0,MTRANSimulation.ORI1,MTRANSimulation.ORI3X,new Vector3f(x,y,zPlusOffset)),				
				new ModuleMapEntry(1,2,MTRANSimulation.ORI1X,MTRANSimulation.ORI2X,new Vector3f(xMinusBoxOffsetNr2,yMinusBoxOffsetNr1,z)),
				new ModuleMapEntry(1,1,MTRANSimulation.ORI1X,MTRANSimulation.ORI2X,new Vector3f(xMinusLinkOffsetNr1,yMinusSecondLinkOffset,z)),				
				new ModuleMapEntry(1,0,MTRANSimulation.ORI1X,MTRANSimulation.ORI2X,new Vector3f(xMinusOffset,y,z)),				
				new ModuleMapEntry(1,2,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3XY,new Vector3f(x,yPlusBoxOffsetNr1,zMinusBoxOffsetNr2)),
				new ModuleMapEntry(1,1,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3XY,new Vector3f(x,yPlusSecondLinkOffset,zMinusLinkOffsetNr1)),
				new ModuleMapEntry(1,0,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3XY,new Vector3f(x,y,zMinusOffset)),				
				new ModuleMapEntry(1,2,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2X,new Vector3f(xMinusBoxOffsetNr2,yPlusBoxOffsetNr1,z)),
				new ModuleMapEntry(1,1,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2X,new Vector3f(xMinusLinkOffsetNr1,yPlusSecondLinkOffset,z)),
				new ModuleMapEntry(1,0,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2X,new Vector3f(xMinusOffset,y,z)),				
				new ModuleMapEntry(1,2,MTRANSimulation.ORI3,MTRANSimulation.ORI2Y,new Vector3f(xPlusBoxOffsetNr2,y,zPlusBoxOffsetNr1)),
				new ModuleMapEntry(1,1,MTRANSimulation.ORI3,MTRANSimulation.ORI2Y,new Vector3f(xPlusLinkOffsetNr1,y,zPlusSecondLinkOffset)),
				new ModuleMapEntry(1,0,MTRANSimulation.ORI3,MTRANSimulation.ORI2Y,new Vector3f(xPlusOffset,y,z)),				
				new ModuleMapEntry(1,2,MTRANSimulation.ORI3X,MTRANSimulation.ORI1Y,new Vector3f(x,yPlusBoxOffsetNr2,zPlusBoxOffsetNr1)),
				new ModuleMapEntry(1,1,MTRANSimulation.ORI3X,MTRANSimulation.ORI1Y,new Vector3f(x,yPlusLinkOffsetNr1,zPlusSecondLinkOffset)),
				new ModuleMapEntry(1,0,MTRANSimulation.ORI3X,MTRANSimulation.ORI1Y,new Vector3f(x,yPlusOffset,z)),				
				new ModuleMapEntry(1,2,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2,new Vector3f(xMinusBoxOffsetNr2,y,zMinusBoxOffsetNr1)),
				new ModuleMapEntry(1,1,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2,new Vector3f(xMinusLinkOffsetNr1,y,zMinusSecondLinkOffset)),
				new ModuleMapEntry(1,0,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2,new Vector3f(xMinusOffset,y,z)),				
				new ModuleMapEntry(1,2,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1Y,new Vector3f(x,yPlusBoxOffsetNr2,zMinusBoxOffsetNr1)),
				new ModuleMapEntry(1,1,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1Y,new Vector3f(x,yPlusLinkOffsetNr1,zMinusSecondLinkOffset)),
				new ModuleMapEntry(1,0,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1Y,new Vector3f(x,yPlusOffset,z)),
				// Connector Nr:2				
				new ModuleMapEntry(2,2,MTRANSimulation.ORI2,MTRANSimulation.ORI3Y,new Vector3f(xMinusBoxOffsetNr1,y,zMinusBoxOffsetNr2)),
				new ModuleMapEntry(2,1,MTRANSimulation.ORI2,MTRANSimulation.ORI3Y,new Vector3f(xMinusSecondLinkOffset,y,zMinusLinkOffsetNr1)),
				new ModuleMapEntry(2,0,MTRANSimulation.ORI2,MTRANSimulation.ORI3Y,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntry(2,2,MTRANSimulation.ORI2X,MTRANSimulation.ORI1X,new Vector3f(xMinusBoxOffsetNr1,yMinusBoxOffsetNr2,z)),
				new ModuleMapEntry(2,1,MTRANSimulation.ORI2X,MTRANSimulation.ORI1X,new Vector3f(xMinusSecondLinkOffset,yMinusLinkOffsetNr1,z)),
				new ModuleMapEntry(2,0,MTRANSimulation.ORI2X,MTRANSimulation.ORI1X,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntry(2,2,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3,new Vector3f(xPlusBoxOffsetNr1,y,zPlusBoxOffsetNr2)),
				new ModuleMapEntry(2,1,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3,new Vector3f(xPlusSecondLinkOffset,y,zPlusLinkOffsetNr1)),
				new ModuleMapEntry(2,0,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3,new Vector3f(x,y,zPlusOffset)),				
				new ModuleMapEntry(2,2,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1X,new Vector3f(xPlusBoxOffsetNr1,yMinusBoxOffsetNr2,z)),
				new ModuleMapEntry(2,1,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1X,new Vector3f(xPlusSecondLinkOffset,yMinusLinkOffsetNr1,z)),
				new ModuleMapEntry(2,0,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1X,new Vector3f(x,yMinusOffset,z)),				
				new ModuleMapEntry(2,2,MTRANSimulation.ORI1,MTRANSimulation.ORI3XY,new Vector3f(x,yMinusBoxOffsetNr1,zMinusBoxOffsetNr2)),
				new ModuleMapEntry(2,1,MTRANSimulation.ORI1,MTRANSimulation.ORI3XY,new Vector3f(x,yMinusSecondLinkOffset,zMinusLinkOffsetNr1)),
				new ModuleMapEntry(2,0,MTRANSimulation.ORI1,MTRANSimulation.ORI3XY,new Vector3f(x,y,zMinusOffset)),				
				new ModuleMapEntry(2,2,MTRANSimulation.ORI1X,MTRANSimulation.ORI2XY,new Vector3f(xPlusBoxOffsetNr2,yMinusBoxOffsetNr1,z)),
				new ModuleMapEntry(2,1,MTRANSimulation.ORI1X,MTRANSimulation.ORI2XY,new Vector3f(xPlusLinkOffsetNr1,yMinusSecondLinkOffset,z)),				
				new ModuleMapEntry(2,0,MTRANSimulation.ORI1X,MTRANSimulation.ORI2XY,new Vector3f(xPlusOffset,y,z)),				
				new ModuleMapEntry(2,2,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3X,new Vector3f(x,yPlusBoxOffsetNr1,zPlusBoxOffsetNr2)),
				new ModuleMapEntry(2,1,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3X,new Vector3f(x,yPlusSecondLinkOffset,zPlusLinkOffsetNr1)),
				new ModuleMapEntry(2,0,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3X,new Vector3f(x,y,zPlusOffset)),				
				new ModuleMapEntry(2,2,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2XY,new Vector3f(xPlusBoxOffsetNr2,yPlusBoxOffsetNr1,z)),
				new ModuleMapEntry(2,1,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2XY,new Vector3f(xPlusLinkOffsetNr1,yPlusSecondLinkOffset,z)),
				new ModuleMapEntry(2,0,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2XY,new Vector3f(xPlusOffset,y,z)),				
				new ModuleMapEntry(2,2,MTRANSimulation.ORI3,MTRANSimulation.ORI2,new Vector3f(xMinusBoxOffsetNr2,y,zPlusBoxOffsetNr1)),
				new ModuleMapEntry(2,1,MTRANSimulation.ORI3,MTRANSimulation.ORI2,new Vector3f(xMinusLinkOffsetNr1,y,zPlusSecondLinkOffset)),
				new ModuleMapEntry(2,0,MTRANSimulation.ORI3,MTRANSimulation.ORI2,new Vector3f(xMinusOffset,y,z)),				
				new ModuleMapEntry(2,2,MTRANSimulation.ORI3X,MTRANSimulation.ORI1,new Vector3f(x,yMinusBoxOffsetNr2,zPlusBoxOffsetNr1)),
				new ModuleMapEntry(2,1,MTRANSimulation.ORI3X,MTRANSimulation.ORI1,new Vector3f(x,yMinusLinkOffsetNr1,zPlusSecondLinkOffset)),
				new ModuleMapEntry(2,0,MTRANSimulation.ORI3X,MTRANSimulation.ORI1,new Vector3f(x,yMinusOffset,z)),				
				new ModuleMapEntry(2,2,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2Y,new Vector3f(xPlusBoxOffsetNr2,y,zMinusBoxOffsetNr1)),
				new ModuleMapEntry(2,1,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2Y,new Vector3f(xPlusLinkOffsetNr1,y,zMinusSecondLinkOffset)),
				new ModuleMapEntry(2,0,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2Y,new Vector3f(xPlusOffset,y,z)),				
				new ModuleMapEntry(2,2,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1,new Vector3f(x,yMinusBoxOffsetNr2,zMinusBoxOffsetNr1)),
				new ModuleMapEntry(2,1,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1,new Vector3f(x,yMinusLinkOffsetNr1,zMinusSecondLinkOffset)),
				new ModuleMapEntry(2,0,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1,new Vector3f(x,yMinusOffset,z)),				
				// Connector Nr:3				
				new ModuleMapEntry(3,MTRANSimulation.ORI2,MTRANSimulation.ORI2,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntry(3,MTRANSimulation.ORI2X,MTRANSimulation.ORI2X,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntry(3,MTRANSimulation.ORI2Y,MTRANSimulation.ORI2Y,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntry(3,MTRANSimulation.ORI2XY,MTRANSimulation.ORI2XY,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntry(3,MTRANSimulation.ORI1,MTRANSimulation.ORI1,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntry(3,MTRANSimulation.ORI1X,MTRANSimulation.ORI1X,new Vector3f(x,yPlusOffset,z)),				
				new ModuleMapEntry(3,MTRANSimulation.ORI1Y,MTRANSimulation.ORI1Y,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntry(3,MTRANSimulation.ORI1XY,MTRANSimulation.ORI1XY,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntry(3,MTRANSimulation.ORI3,MTRANSimulation.ORI3,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntry(3,MTRANSimulation.ORI3X,MTRANSimulation.ORI3X,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntry(3,MTRANSimulation.ORI3Y,MTRANSimulation.ORI3Y,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntry(3,MTRANSimulation.ORI3XY,MTRANSimulation.ORI3XY,new Vector3f(x,y,zPlusOffset)),
				// Connector Nr:4
				new ModuleMapEntry(4,2,MTRANSimulation.ORI2,MTRANSimulation.ORI3Y,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntry(4,1,MTRANSimulation.ORI2,MTRANSimulation.ORI3Y,new Vector3f(xPlusSecondLinkOffset,y,zPlusLinkOffsetNr1)),
				new ModuleMapEntry(4,0,MTRANSimulation.ORI2,MTRANSimulation.ORI3Y,new Vector3f(xPlusBoxOffsetNr1,y,zPlusBoxOffsetNr2)),
				new ModuleMapEntry(4,2,MTRANSimulation.ORI2X,MTRANSimulation.ORI1X,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntry(4,1,MTRANSimulation.ORI2X,MTRANSimulation.ORI1X,new Vector3f(xPlusSecondLinkOffset,yPlusLinkOffsetNr1,z)),
				new ModuleMapEntry(4,0,MTRANSimulation.ORI2X,MTRANSimulation.ORI1X,new Vector3f(xPlusBoxOffsetNr1,yPlusBoxOffsetNr2,z)),
				new ModuleMapEntry(4,2,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntry(4,1,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3,new Vector3f(xMinusSecondLinkOffset,y,zMinusLinkOffsetNr1)),
				new ModuleMapEntry(4,0,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3,new Vector3f(xMinusBoxOffsetNr1,y,zMinusBoxOffsetNr2)),				
				new ModuleMapEntry(4,2,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1X,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntry(4,1,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1X,new Vector3f(xMinusSecondLinkOffset,yPlusLinkOffsetNr1,z)),
				new ModuleMapEntry(4,0,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1X,new Vector3f(xMinusBoxOffsetNr1,yPlusBoxOffsetNr2,z)),				
				new ModuleMapEntry(4,2,MTRANSimulation.ORI1,MTRANSimulation.ORI3XY,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntry(4,1,MTRANSimulation.ORI1,MTRANSimulation.ORI3XY,new Vector3f(x,yPlusSecondLinkOffset,zPlusLinkOffsetNr1)),
				new ModuleMapEntry(4,0,MTRANSimulation.ORI1,MTRANSimulation.ORI3XY,new Vector3f(x,yPlusBoxOffsetNr1,zPlusBoxOffsetNr2)),				
				new ModuleMapEntry(4,2,MTRANSimulation.ORI1X,MTRANSimulation.ORI2XY,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntry(4,1,MTRANSimulation.ORI1X,MTRANSimulation.ORI2XY,new Vector3f(xMinusLinkOffsetNr1,yPlusSecondLinkOffset,z)),				
				new ModuleMapEntry(4,0,MTRANSimulation.ORI1X,MTRANSimulation.ORI2XY,new Vector3f(xMinusBoxOffsetNr2,yPlusBoxOffsetNr1,z)),				
				new ModuleMapEntry(4,2,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3X,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntry(4,1,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3X,new Vector3f(x,yMinusSecondLinkOffset,zMinusLinkOffsetNr1)),
				new ModuleMapEntry(4,0,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3X,new Vector3f(x,yMinusBoxOffsetNr1,zMinusBoxOffsetNr2)),				
				new ModuleMapEntry(4,2,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2XY,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntry(4,1,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2XY,new Vector3f(xMinusLinkOffsetNr1,yMinusSecondLinkOffset,z)),
				new ModuleMapEntry(4,0,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2XY,new Vector3f(xMinusBoxOffsetNr2,yMinusBoxOffsetNr1,z)),				
				new ModuleMapEntry(4,2,MTRANSimulation.ORI3,MTRANSimulation.ORI2,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntry(4,1,MTRANSimulation.ORI3,MTRANSimulation.ORI2,new Vector3f(xPlusLinkOffsetNr1,y,zMinusSecondLinkOffset)),
				new ModuleMapEntry(4,0,MTRANSimulation.ORI3,MTRANSimulation.ORI2,new Vector3f(xPlusBoxOffsetNr2,y,zMinusBoxOffsetNr1)),				
				new ModuleMapEntry(4,2,MTRANSimulation.ORI3X,MTRANSimulation.ORI1,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntry(4,1,MTRANSimulation.ORI3X,MTRANSimulation.ORI1,new Vector3f(x,yPlusLinkOffsetNr1,zMinusSecondLinkOffset)),
				new ModuleMapEntry(4,0,MTRANSimulation.ORI3X,MTRANSimulation.ORI1,new Vector3f(x,yPlusBoxOffsetNr2,zMinusBoxOffsetNr1)),				
				new ModuleMapEntry(4,2,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2Y,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntry(4,1,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2Y,new Vector3f(xMinusLinkOffsetNr1,y,zPlusSecondLinkOffset)),
				new ModuleMapEntry(4,0,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2Y,new Vector3f(xMinusBoxOffsetNr2,y,zPlusBoxOffsetNr1)),				
				new ModuleMapEntry(4,2,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1,new Vector3f(x,yPlusOffset,z)),
				new ModuleMapEntry(4,1,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1,new Vector3f(x,yPlusLinkOffsetNr1,zPlusSecondLinkOffset)),
				new ModuleMapEntry(4,0,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1,new Vector3f(x,yPlusBoxOffsetNr2,zPlusBoxOffsetNr1)),				
				// Connector Nr:5
				new ModuleMapEntry(5,2,MTRANSimulation.ORI2,MTRANSimulation.ORI3,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntry(5,1,MTRANSimulation.ORI2,MTRANSimulation.ORI3,new Vector3f(xPlusSecondLinkOffset,y,zMinusLinkOffsetNr1)),
				new ModuleMapEntry(5,0,MTRANSimulation.ORI2,MTRANSimulation.ORI3,new Vector3f(xPlusBoxOffsetNr1,y,zMinusBoxOffsetNr2)),
				new ModuleMapEntry(5,2,MTRANSimulation.ORI2X,MTRANSimulation.ORI1XY,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntry(5,1,MTRANSimulation.ORI2X,MTRANSimulation.ORI1XY,new Vector3f(xPlusSecondLinkOffset,yMinusLinkOffsetNr1,z)),
				new ModuleMapEntry(5,0,MTRANSimulation.ORI2X,MTRANSimulation.ORI1XY,new Vector3f(xPlusBoxOffsetNr1,yMinusBoxOffsetNr2,z)),
				new ModuleMapEntry(5,2,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3Y,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntry(5,1,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3Y,new Vector3f(xMinusSecondLinkOffset,y,zPlusLinkOffsetNr1)),
				new ModuleMapEntry(5,0,MTRANSimulation.ORI2Y,MTRANSimulation.ORI3Y,new Vector3f(xMinusBoxOffsetNr1,y,zPlusBoxOffsetNr2)),				
				new ModuleMapEntry(5,2,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1XY,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntry(5,1,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1XY,new Vector3f(xMinusSecondLinkOffset,yMinusLinkOffsetNr1,z)),
				new ModuleMapEntry(5,0,MTRANSimulation.ORI2XY,MTRANSimulation.ORI1XY,new Vector3f(xMinusBoxOffsetNr1,yMinusBoxOffsetNr2,z)),				
				new ModuleMapEntry(5,2,MTRANSimulation.ORI1,MTRANSimulation.ORI3X,new Vector3f(x,y,zMinusOffset)),
				new ModuleMapEntry(5,1,MTRANSimulation.ORI1,MTRANSimulation.ORI3X,new Vector3f(x,yPlusSecondLinkOffset,zMinusLinkOffsetNr1)),
				new ModuleMapEntry(5,0,MTRANSimulation.ORI1,MTRANSimulation.ORI3X,new Vector3f(x,yPlusBoxOffsetNr1,zMinusBoxOffsetNr2)),				
				new ModuleMapEntry(5,2,MTRANSimulation.ORI1X,MTRANSimulation.ORI2X,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntry(5,1,MTRANSimulation.ORI1X,MTRANSimulation.ORI2X,new Vector3f(xPlusLinkOffsetNr1,yPlusSecondLinkOffset,z)),				
				new ModuleMapEntry(5,0,MTRANSimulation.ORI1X,MTRANSimulation.ORI2X,new Vector3f(xPlusBoxOffsetNr2,yPlusBoxOffsetNr1,z)),				
				new ModuleMapEntry(5,2,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3XY,new Vector3f(x,y,zPlusOffset)),
				new ModuleMapEntry(5,1,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3XY,new Vector3f(x,yMinusSecondLinkOffset,zPlusLinkOffsetNr1)),
				new ModuleMapEntry(5,0,MTRANSimulation.ORI1Y,MTRANSimulation.ORI3XY,new Vector3f(x,yMinusBoxOffsetNr1,zPlusBoxOffsetNr2)),				
				new ModuleMapEntry(5,2,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2X,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntry(5,1,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2X,new Vector3f(xPlusLinkOffsetNr1,yMinusSecondLinkOffset,z)),
				new ModuleMapEntry(5,0,MTRANSimulation.ORI1XY,MTRANSimulation.ORI2X,new Vector3f(xPlusBoxOffsetNr2,yMinusBoxOffsetNr1,z)),				
				new ModuleMapEntry(5,2,MTRANSimulation.ORI3,MTRANSimulation.ORI2Y,new Vector3f(xMinusOffset,y,z)),
				new ModuleMapEntry(5,1,MTRANSimulation.ORI3,MTRANSimulation.ORI2Y,new Vector3f(xMinusLinkOffsetNr1,y,zMinusSecondLinkOffset)),
				new ModuleMapEntry(5,0,MTRANSimulation.ORI3,MTRANSimulation.ORI2Y,new Vector3f(xMinusBoxOffsetNr2,y,zMinusBoxOffsetNr1)),				
				new ModuleMapEntry(5,2,MTRANSimulation.ORI3X,MTRANSimulation.ORI1Y,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntry(5,1,MTRANSimulation.ORI3X,MTRANSimulation.ORI1Y,new Vector3f(x,yMinusLinkOffsetNr1,zMinusSecondLinkOffset)),
				new ModuleMapEntry(5,0,MTRANSimulation.ORI3X,MTRANSimulation.ORI1Y,new Vector3f(x,yMinusBoxOffsetNr2,zMinusBoxOffsetNr1)),				
				new ModuleMapEntry(5,2,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2,new Vector3f(xPlusOffset,y,z)),
				new ModuleMapEntry(5,1,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2,new Vector3f(xPlusLinkOffsetNr1,y,zPlusSecondLinkOffset)),
				new ModuleMapEntry(5,0,MTRANSimulation.ORI3Y,MTRANSimulation.ORI2,new Vector3f(xPlusBoxOffsetNr2,y,zPlusBoxOffsetNr1)),				
				new ModuleMapEntry(5,2,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1Y,new Vector3f(x,yMinusOffset,z)),
				new ModuleMapEntry(5,1,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1Y,new Vector3f(x,yMinusLinkOffsetNr1,zPlusSecondLinkOffset)),
				new ModuleMapEntry(5,0,MTRANSimulation.ORI3XY,MTRANSimulation.ORI1Y,new Vector3f(x,yMinusBoxOffsetNr2,zPlusBoxOffsetNr1))				
			};		
		return moduleMap;		
	}		

	@Override
	public void rotateOpposite(Module selectedModule) {
//FIXME MAYBE IS NOT USEFULL IN MTRAN CASE, BECAUSE THERE IS OFTEN THE CASE THAT THE SAME ACTIVE OR PASSIVE
// BOXES CONNECT WITH THE SAME TYPE OF THE BOX
		/*Amount of components constituting selectedModule*/
		int amountComponents =selectedModule.getNumberOfComponents();		

		/*Loop through each component of selected module and rotate it with selected standard rotation. 
		 TRAN module has three components(two half boxes-cylinders (blue and red)and one link(black).*/
		for (int component=0; component<amountComponents;component++){

			/* The current component of selected module*/
			JMEModuleComponent selectedModuleComponent= (JMEModuleComponent)selectedModule.getComponent(component);			
			Quaternion  rotationQselectedModuleComponent = selectedModule.getComponent(component).getRotation().getRotation();

// FIXME MAYBE CAN BE MADE NICER   
			/* Check rotation Quaternion and rotate with opposite*/			
			if (rotationQselectedModuleComponent.equals(MTRANSimulation.ORI1.getRotation())){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI1Y.getRotation());				
			}else if (rotationQselectedModuleComponent.equals(MTRANSimulation.ORI1Y.getRotation())){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI1.getRotation());				
			}else if (rotationQselectedModuleComponent.equals(MTRANSimulation.ORI1X.getRotation())){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI1XY.getRotation());	
			}else if (rotationQselectedModuleComponent.equals(MTRANSimulation.ORI1XY.getRotation())){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI1X.getRotation());	
			}else if (rotationQselectedModuleComponent.equals(MTRANSimulation.ORI2.getRotation())){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI2Y.getRotation());	
			}else if (rotationQselectedModuleComponent.equals(MTRANSimulation.ORI2Y.getRotation())){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI2.getRotation());	
			}else if (rotationQselectedModuleComponent.equals(MTRANSimulation.ORI2X.getRotation())){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI2XY.getRotation());	
			}else if (rotationQselectedModuleComponent.equals(MTRANSimulation.ORI2XY.getRotation())){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI2X.getRotation());	
			}else if (rotationQselectedModuleComponent.equals(MTRANSimulation.ORI3.getRotation())){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI3Y.getRotation());				
			}else if (rotationQselectedModuleComponent.equals(MTRANSimulation.ORI3Y.getRotation())){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI3.getRotation());				
			}else if (rotationQselectedModuleComponent.equals(MTRANSimulation.ORI3X.getRotation())){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI3XY.getRotation());	
			}else if (rotationQselectedModuleComponent.equals(MTRANSimulation.ORI3XY.getRotation())){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI3X.getRotation());	
			}	
		}
		int redBoxIndex =0, blueBoxIndex =2;
		
		/*Get positions of red and blue half-cylinder boxes*/
		VectorDescription positionRedBox = selectedModule.getComponent(redBoxIndex).getPosition();//red half-cylinder box		
		VectorDescription positionBlueBox = selectedModule.getComponent(blueBoxIndex).getPosition();// blue half-cylinder box
		
		/*Swap the positioning of boxes*/
		translateModuleComponent((JMEModuleComponent)selectedModule.getComponent(redBoxIndex), new Vector3f(positionBlueBox.getX(),positionBlueBox.getY(),positionBlueBox.getZ()));
		translateModuleComponent((JMEModuleComponent)selectedModule.getComponent(blueBoxIndex), new Vector3f(positionRedBox.getX(),positionRedBox.getY(),positionRedBox.getZ()));		
	}

	/**
	 * Rotates selected MTRAN module with standard rotations,like ORI1. 
	 * @param selectedModule,the MTRAN module selected in simulation environment	 
	 * @param rotationName,the name of standard rotation of MTRAN module 
	 */
	@Override
	public void rotateSpecifically(Module selectedModule, String rotationName) {
// FIXME NEED TO BE REIMPLEMENTED	
		/*Amount of components constituting selectedModule*/
		int amountComponents =selectedModule.getNumberOfComponents();
		
		int redBoxIndex =0, blueBoxIndex =2;
		VectorDescription positionBlueBox = selectedModule.getComponent(blueBoxIndex).getPosition();// blue half-cylinder box
		VectorDescription positionRedBox = selectedModule.getComponent(redBoxIndex).getPosition();//red half-cylinder box		
		
		float xBlueBox = positionBlueBox.getX();

		/*Loop through each component of selected module and rotate it with selected standard rotation. 
		 M-Tran module has three components(active and passive half cylinders-boxes and link between them ).*/
		for (int component=0; component<amountComponents;component++){

			/* The current component of selected module*/
			JMEModuleComponent selectedModuleComponent= (JMEModuleComponent)selectedModule.getComponent(component);

			if (rotationName.equalsIgnoreCase("ORI1")){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI1.getRotation());			
			}else if (rotationName.equalsIgnoreCase("ORI1X")){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI1X.getRotation());
			/*	if (component==redBoxIndex){
					translateModuleComponent(selectedModuleComponent, new Vector3f());
				}*/
			}else if (rotationName.equalsIgnoreCase("ORI1Y")){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI1Y.getRotation());
			}else if (rotationName.equalsIgnoreCase("ORI1XY")){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI1XY.getRotation());
			}else if (rotationName.equalsIgnoreCase("ORI2")){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI2.getRotation());
			}else if (rotationName.equalsIgnoreCase("ORI2X")){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI2X.getRotation());
			}else if (rotationName.equalsIgnoreCase("ORI2Y")){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI2Y.getRotation());
			}else if (rotationName.equalsIgnoreCase("ORI2XY")){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI2XY.getRotation());
			}else if (rotationName.equalsIgnoreCase("ORI3")){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI3.getRotation());
			}else if (rotationName.equalsIgnoreCase("ORI3X")){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI3X.getRotation());
			}else if (rotationName.equalsIgnoreCase("ORI3Y")){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI3Y.getRotation());
			}else if (rotationName.equalsIgnoreCase("ORI3XY")){
				rotateModuleComponent(selectedModuleComponent,MTRANSimulation.ORI3XY.getRotation());
			}				
		}
	}
}

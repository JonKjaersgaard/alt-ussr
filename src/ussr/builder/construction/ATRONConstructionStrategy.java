package ussr.builder.construction;

import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.samples.atron.ATRON;

import com.jme.math.Matrix3f;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

/**
 * The main responsibility of this class is to take care of construction functions for
 * construction of ATRON modular robot's morphology. Here module consists of components. To be more precise
 * ATRON module consists of two components (two hemispheres). Connectors are considered to be as parts
 * of components. ATRON module has 8 connectors, numbered as: 0,1,2,3,4,5,6 and 7. All methods are 
 * specific to ATRON module design.
 * @author Konstantinas
 */
//FIXME 1) UPDATE COMMENTS
//FIXME 2) FIX EXISTING IMPROVEMENTS
public class ATRONConstructionStrategy extends ModularRobotConstructionStrategy  {	

	/**
	 * The physical lattice distance between two ATRON modules
	 */
	private final static float offset = ATRON.UNIT;

	/**
	 * The array of objects containing information about ATRON specific rotations.
	 */
	private final static ModuleRotationMapEntry[] moduleRotationMap =  {
		new ModuleRotationMapEntry("EW",ATRON.ROTATION_EW,ATRON.ROTATION_WE),
		new ModuleRotationMapEntry("WE",ATRON.ROTATION_WE,ATRON.ROTATION_EW),
		new ModuleRotationMapEntry("SN",ATRON.ROTATION_SN,ATRON.ROTATION_NS),
		new ModuleRotationMapEntry("NS",ATRON.ROTATION_NS,ATRON.ROTATION_SN),
		new ModuleRotationMapEntry("UD",ATRON.ROTATION_UD,ATRON.ROTATION_DU),
		new ModuleRotationMapEntry("DU",ATRON.ROTATION_DU,ATRON.ROTATION_UD),
	};

	/**
	 * Moves newMovableModule of ATRON according(respectively) to selected ATRON module preconditions,
	 * like connector number, initial rotation of selected module, and so on.
	 * @param connectorNr, the connector number on selected ATRON module
	 * @param selectedModule,  the ATRON module selected in simulation environment
	 * @param newMovableModule, the new ATRON module to move respectively to selected one
	 */	
	@Override
	public void moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule){	

		/*Amount of components constituting selectedModule*/
		int amountComponents =selectedModule.getNumberOfComponents();		

		/*Loop through each component of selected module and move the component of new movable module 
		 * with respect to selected component of the module. ATRON module has two components(two hemispheres).*/
		for (int component=0; component<amountComponents;component++){

			/* The current component of selected module*/
			JMEModuleComponent currentComponent = (JMEModuleComponent)selectedModule.getComponent(component);
			VectorDescription positionComponent = currentComponent.getPosition();
			Quaternion  rotationQuatComponent = currentComponent.getRotation().getRotation();

			/*Get x,y,z of selected module component position. In USSR x axis is horizontal,
			 * y axis is perpendicular to horizontal axis, z is facing the user*/
			float x = positionComponent.getX();
			float y = positionComponent.getY();
			float z = positionComponent.getZ();                            				

			/*Update the array of objects storing information about all available initial rotations of ATRON modules
			 * and  rotations together with positions of newly added modules with respect to specific connector*/
			ModuleMapEntry[] currentModuleMap = updateModuleMap(x,y,z);			

			/*The current component of new movable module */
			JMEModuleComponent movableModuleComponent = (JMEModuleComponent)newMovableModule.getComponent(component);

			/*Loop through and locate the object matching the description of current component(also selected module).
			 *After that move new movable component, with respect to selected component of the module using
			 *extracted information form the object describing it*/
			for (int i=0; i<currentModuleMap.length;i++){
				if (currentModuleMap[i].getConnectorNr()==connectorNr && currentModuleMap[i].getInitialRotation().getRotation().equals(rotationQuatComponent)){
					moveModuleComponent(movableModuleComponent,currentModuleMap[i].getNewRotation(),currentModuleMap[i].getNewPosition());
				}				
			}			
		}  		
	}
// FIXME FOR EXPERIMENTATION
	/*public void moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule){
		experiment(connectorNr,  selectedModule,  newMovableModule);	
	}*/

	/**
	 * Updates and returns the array of objects containing the information about all available initial rotations 
	 * of ATRON module and  rotations together with positions of newly added module with respect to specific 
	 * connector number.	 
	 * @param x, the amount of x coordinate of current position of component
	 * @param y, the amount of y coordinate of current position of component
	 * @param z, the amount of z coordinate of current position of component
	 * @return moduleMap, updated array of objects.
	 */
	private ModuleMapEntry[] updateModuleMap(float x, float y, float z){		

		/*Apply offset of 0.08(UNIT) for each coordinate, which is a lattice distance between two ATRON modules.
		 * This is done to get the position of newly added component of the module (movable module) with respect 
		 * to selected one*/ 
		float xPlusOffset = x + offset;
		float xMinusOffset = x - offset;
		float yPlusOffset = y + offset;
		float yMinusOffset = y - offset;
		float zPlusOffset = z + offset;
		float zMinusOffset = z - offset;

		/*Array containing the data for adding the new movable component(module) with respect to selected module.
		 * The format of the object is: (connector number on selected module, the rotation of selected module, 
		 * the rotation of new movable module, the position of new movable module*/ 
		ModuleMapEntry[] moduleMap = {
				//Connector Nr:0
				new ModuleMapEntry(0,ATRON.ROTATION_EW,ATRON.ROTATION_DU,new Vector3f(xMinusOffset,yPlusOffset,z)),
				new ModuleMapEntry(0,ATRON.ROTATION_WE,ATRON.ROTATION_DU,new Vector3f(xPlusOffset,yPlusOffset,z)),
				new ModuleMapEntry(0,ATRON.ROTATION_DU,ATRON.ROTATION_SN,new Vector3f(x,yMinusOffset,zMinusOffset)),
				new ModuleMapEntry(0,ATRON.ROTATION_UD,ATRON.ROTATION_SN,new Vector3f(x,yPlusOffset,zMinusOffset)),
				new ModuleMapEntry(0,ATRON.ROTATION_SN,ATRON.ROTATION_WE,new Vector3f(xMinusOffset,y,zPlusOffset)),
				new ModuleMapEntry(0,ATRON.ROTATION_NS,ATRON.ROTATION_WE,new Vector3f(xMinusOffset,y,zMinusOffset)),
				//Connector Nr:1
				new ModuleMapEntry(1,ATRON.ROTATION_EW,ATRON.ROTATION_NS,new Vector3f(xMinusOffset,y,zPlusOffset)),
				new ModuleMapEntry(1,ATRON.ROTATION_WE,ATRON.ROTATION_SN,new Vector3f(xPlusOffset,y,zMinusOffset)),
				new ModuleMapEntry(1,ATRON.ROTATION_DU,ATRON.ROTATION_WE,new Vector3f(xMinusOffset,yMinusOffset,z)),
				new ModuleMapEntry(1,ATRON.ROTATION_UD,ATRON.ROTATION_EW,new Vector3f(xPlusOffset,yPlusOffset,z)),
				new ModuleMapEntry(1,ATRON.ROTATION_SN,ATRON.ROTATION_DU,new Vector3f(x,yPlusOffset,zPlusOffset)),
				new ModuleMapEntry(1,ATRON.ROTATION_NS,ATRON.ROTATION_UD,new Vector3f(x,yMinusOffset,zMinusOffset)),
				//Connector Nr:2
				new ModuleMapEntry(2,ATRON.ROTATION_EW,ATRON.ROTATION_UD,new Vector3f(xMinusOffset,yMinusOffset,z)),
				new ModuleMapEntry(2,ATRON.ROTATION_WE,ATRON.ROTATION_UD,new Vector3f(xPlusOffset,yMinusOffset,z)),
				new ModuleMapEntry(2,ATRON.ROTATION_DU,ATRON.ROTATION_NS,new Vector3f(x,yMinusOffset,zPlusOffset)),
				new ModuleMapEntry(2,ATRON.ROTATION_UD,ATRON.ROTATION_NS,new Vector3f(x,yPlusOffset,zPlusOffset)),
				new ModuleMapEntry(2,ATRON.ROTATION_SN,ATRON.ROTATION_EW,new Vector3f(xPlusOffset,y,zPlusOffset)),
				new ModuleMapEntry(2,ATRON.ROTATION_NS,ATRON.ROTATION_EW,new Vector3f(xPlusOffset,y,zMinusOffset)),		
				//Connector Nr:3
				new ModuleMapEntry(3,ATRON.ROTATION_EW,ATRON.ROTATION_SN,new Vector3f(xMinusOffset,y,zMinusOffset)),
				new ModuleMapEntry(3,ATRON.ROTATION_WE,ATRON.ROTATION_NS,new Vector3f(xPlusOffset,y,zPlusOffset)),
				new ModuleMapEntry(3,ATRON.ROTATION_DU,ATRON.ROTATION_EW,new Vector3f(xPlusOffset,yMinusOffset,z)),
				new ModuleMapEntry(3,ATRON.ROTATION_UD,ATRON.ROTATION_WE,new Vector3f(xMinusOffset,yPlusOffset,z)),
				new ModuleMapEntry(3,ATRON.ROTATION_SN,ATRON.ROTATION_UD,new Vector3f(x,yMinusOffset,zPlusOffset)),
				new ModuleMapEntry(3,ATRON.ROTATION_NS,ATRON.ROTATION_DU,new Vector3f(x,yPlusOffset,zMinusOffset)),
				//Connector Nr:4
				new ModuleMapEntry(4,ATRON.ROTATION_EW,ATRON.ROTATION_UD,new Vector3f(xPlusOffset,yPlusOffset,z)),
				new ModuleMapEntry(4,ATRON.ROTATION_WE,ATRON.ROTATION_UD,new Vector3f(xMinusOffset,yPlusOffset,z)),
				new ModuleMapEntry(4,ATRON.ROTATION_DU,ATRON.ROTATION_NS,new Vector3f(x,yPlusOffset,zMinusOffset)),
				new ModuleMapEntry(4,ATRON.ROTATION_UD,ATRON.ROTATION_NS,new Vector3f(x,yMinusOffset,zMinusOffset)),
				new ModuleMapEntry(4,ATRON.ROTATION_SN,ATRON.ROTATION_EW,new Vector3f(xMinusOffset,y,zMinusOffset)),
				new ModuleMapEntry(4,ATRON.ROTATION_NS,ATRON.ROTATION_EW,new Vector3f(xMinusOffset,y,zPlusOffset)),
				//Connector Nr:5
				new ModuleMapEntry(5,ATRON.ROTATION_EW,ATRON.ROTATION_SN,new Vector3f(xPlusOffset,y,zPlusOffset)),
				new ModuleMapEntry(5,ATRON.ROTATION_WE,ATRON.ROTATION_NS,new Vector3f(xMinusOffset,y,zMinusOffset)),
				new ModuleMapEntry(5,ATRON.ROTATION_DU,ATRON.ROTATION_EW,new Vector3f(xMinusOffset,yPlusOffset,z)),
				new ModuleMapEntry(5,ATRON.ROTATION_UD,ATRON.ROTATION_WE,new Vector3f(xPlusOffset,yMinusOffset,z)),
				new ModuleMapEntry(5,ATRON.ROTATION_SN,ATRON.ROTATION_UD,new Vector3f(x,yPlusOffset,zMinusOffset)),
				new ModuleMapEntry(5,ATRON.ROTATION_NS,ATRON.ROTATION_DU,new Vector3f(x,yMinusOffset,zPlusOffset)),
				//Connector Nr:6
				new ModuleMapEntry(6,ATRON.ROTATION_EW,ATRON.ROTATION_DU,new Vector3f(xPlusOffset,yMinusOffset,z)),
				new ModuleMapEntry(6,ATRON.ROTATION_WE,ATRON.ROTATION_DU,new Vector3f(xMinusOffset,yMinusOffset,z)),
				new ModuleMapEntry(6,ATRON.ROTATION_DU,ATRON.ROTATION_SN,new Vector3f(x,yPlusOffset,zPlusOffset)),
				new ModuleMapEntry(6,ATRON.ROTATION_UD,ATRON.ROTATION_SN,new Vector3f(x,yMinusOffset,zPlusOffset)),
				new ModuleMapEntry(6,ATRON.ROTATION_SN,ATRON.ROTATION_WE,new Vector3f(xPlusOffset,y,zMinusOffset)),
				new ModuleMapEntry(6,ATRON.ROTATION_NS,ATRON.ROTATION_WE,new Vector3f(xPlusOffset,y,zPlusOffset)),
				//Connector Nr:7
				new ModuleMapEntry(7,ATRON.ROTATION_EW,ATRON.ROTATION_NS,new Vector3f(xPlusOffset,y,zMinusOffset)),
				new ModuleMapEntry(7,ATRON.ROTATION_WE,ATRON.ROTATION_SN,new Vector3f(xMinusOffset,y,zPlusOffset)),
				new ModuleMapEntry(7,ATRON.ROTATION_DU,ATRON.ROTATION_WE,new Vector3f(xPlusOffset,yPlusOffset,z)),
				new ModuleMapEntry(7,ATRON.ROTATION_UD,ATRON.ROTATION_EW,new Vector3f(xMinusOffset,yMinusOffset,z)),
				new ModuleMapEntry(7,ATRON.ROTATION_SN,ATRON.ROTATION_DU,new Vector3f(x,yMinusOffset,zMinusOffset)),
				new ModuleMapEntry(7,ATRON.ROTATION_NS,ATRON.ROTATION_UD,new Vector3f(x,yPlusOffset,zPlusOffset))
		};		
		return moduleMap;		
	}	

	/**
	 * Rotates selected ATRON module according to its initial rotation with opposite rotation.	
	 * @param selectedModule,the ATRON module selected in simulation environment	
	 */
	@Override
	public void rotateOpposite(Module selectedModule){

		/*Amount of components constituting selectedModule*/
		int amountComponents =selectedModule.getNumberOfComponents();		

		/*Loop through each component of selected module and rotate it with opposite rotation. 
		 ATRON module has two components(two hemispheres).*/
		for (int component=0; component<amountComponents;component++){

			/* The current component of selected module*/
			JMEModuleComponent selectedModuleComponent= (JMEModuleComponent)selectedModule.getComponent(component);			
			Quaternion  rotationQselectedModuleComponent = selectedModule.getComponent(component).getRotation().getRotation();

			/*Locate matching rotation Quaternion and rotate with opposite Quaternion*/			
			for (int entry=0;entry<moduleRotationMap.length;entry++){
				if (rotationQselectedModuleComponent.equals(moduleRotationMap[entry].getRotation().getRotation())){
					rotateModuleComponent(selectedModuleComponent,moduleRotationMap[entry].getRotationOppositeValue().getRotation());
				}
			}		
		}		
	}

	/**
	 * Rotates selected ATRON module with standard rotations, like EW = east-west,WE = west-east,DU= down-up,SN= south-north and so on.
	 * @param selectedModule,the ATRON module selected in simulation environment	 
	 * @param rotationName,the name of standard rotation of ATRON module, like for example: "EW" = east-west, "WE" = west-east and so on. 
	 */
	@Override
	public void rotateSpecifically(Module selectedModule, String rotationName){

		/*Amount of components constituting selectedModule*/
		int amountComponents =selectedModule.getNumberOfComponents();		

		/*Loop through each component of selected module and rotate it with selected standard rotation. 
		 ATRON module has two components(two hemispheres).*/
		for (int component=0; component<amountComponents;component++){

			/* The current component of selected module*/
			JMEModuleComponent selectedModuleComponent= (JMEModuleComponent)selectedModule.getComponent(component);

			/* Locate mathing rotation name and rotate with value of it*/
			for (int entry=0;entry<moduleRotationMap.length;entry++){
				if (rotationName.equals(moduleRotationMap[entry].getRotationName())){
					rotateModuleComponent(selectedModuleComponent,moduleRotationMap[entry].getRotation().getRotation());
				}
			}			
		}
	}

	/**
	 * Rotates selected ATRON module according to its initial rotation with opposite rotation.
	 * Does the same as rotateOpposite(Module selectedModule) method.
	 * @param selectedModule,the ATRON module selected in simulation environment	 
	 */
//	FIXME CONSIDER MERGING OPPOSITE ROTATION AND VARIATION INTO ONE SELECTION TOOL(method)
//	FIXME MAYBE CAN BE FOUND MORE	
	public void variate(Module selectedModule){
		rotateOpposite(selectedModule);//Repeats, because there are no special variations for ATRON		
	}

//	FIXME UNDER DEVELOPMENT	
	public void experiment(int connectorNr, Module selectedModule, Module newMovableModule){
		/*				
		Matrix3f globalRotation = new Matrix3f(1,0,0,0,1,0,0,0,1);
		//USSR coordinate(Theory)				
		System.out.println("GAroundX(Y):"+ (Math.asin(globalRotation.m20)*(180/pi)));
		System.out.println("GAroundY(Z):"+ (Math.atan(globalRotation.m10/globalRotation.m00)*(180/pi)));
		System.out.println("GAroundZ(X):"+ (Math.atan(globalRotation.m21/globalRotation.m22)*(180/pi)));		
		 */		
		Quaternion newQ = selectedModule.getPhysics().get(0).getRotation().getRotation();

		VectorDescription vd = selectedModule.getConnectors().get(connectorNr).getPhysics().get(0).getPosition();
		System.out.println("ConnectorPostion"+ vd.toString() );

		float  X = selectedModule.getPhysics().get(0).getPosition().getX();
		float  Y = selectedModule.getPhysics().get(0).getPosition().getY();
		float  Z = selectedModule.getPhysics().get(0).getPosition().getZ();

		Matrix3f selectedMatrix = new  Matrix3f( );
		selectedMatrix.set(newQ);		

		float aroundX =(float) Math.asin(selectedMatrix.m20)*(180/pi);
		float aroundY =(float) Math.atan(selectedMatrix.m10/selectedMatrix.m00)*(180/pi);
		float aroundZ = (float)Math.atan(selectedMatrix.m21/selectedMatrix.m22)*(180/pi);

		System.out.println("AroundX(Y):"+ (Math.asin(selectedMatrix.m20)*(180/pi)));
		System.out.println("AroundY(Z):"+ (Math.atan(selectedMatrix.m10/selectedMatrix.m00)*(180/pi)));
		System.out.println("AroundZ(X):"+ (Math.atan(selectedMatrix.m21/selectedMatrix.m22)*(180/pi)));

		/*		Matrix3f movableMatrix = new Matrix3f(1,0,0,0,1,0,0,0,1);

		movableMatrix = rotateAround(movableMatrix,"X",aroundX);
		movableMatrix = rotateAround(movableMatrix,"Y",aroundY);
		movableMatrix = rotateAround(movableMatrix,"Z",aroundZ);
		 */

		//float tolerance =1.5f;
		Matrix3f mf; 
		mf = rotateAround(selectedMatrix,"X",aroundX-90);
		mf = rotateAround(mf,"Y",aroundY+90f+0.6f); //SN 1.5 //EW0.6		
		mf = rotateAround(mf,"Z",aroundZ+90+0.6f);//EW 0.6

		Quaternion newQaut = new Quaternion();
		newQaut.fromRotationMatrix(mf);		

		newMovableModule.setRotation(new RotationDescription(newQaut));

		newMovableModule.setPosition(new VectorDescription(X+ATRON.UNIT,Y,Z-ATRON.UNIT));
	}

	private Matrix3f rotateAround(Matrix3f matrix,String coordinate, float degrees){

		float cos =(float)Math.cos(degrees);
		float sin =(float)Math.sin(degrees);

		Matrix3f rotationMatrix = null;
		if (coordinate.equalsIgnoreCase("X")){
			System.out.println("Degrees:"+degrees);
			rotationMatrix = new Matrix3f(1,0,0,0,cos,-sin,0,sin,cos);
		}else if(coordinate.equalsIgnoreCase("Y")){
			System.out.println("Degrees:"+degrees);
			rotationMatrix = new Matrix3f(cos,0,sin,0,1,0,-sin,0,cos);

		}else if(coordinate.equalsIgnoreCase("Z")){
			System.out.println("Degrees:"+degrees);
			rotationMatrix = new Matrix3f(cos,-sin,0,sin,cos,0,0,0,1);					
		}	

		return rotationMatrix.mult(matrix) ;
	}  
}

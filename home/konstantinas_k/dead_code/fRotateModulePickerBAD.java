package dead_code;

import com.jme.math.Quaternion;
import com.jme.scene.Spatial;
import com.jmex.physics.DynamicPhysicsNode;

import ussr.description.geometry.RotationDescription;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

/**
 *DO NOT WORKS WEEL CONSIDER SOTMETHING ELSE
 * @author Konstantinas
 *
 */
public class fRotateModulePickerBAD  extends CustomizedPicker{
	
	/**
	 * The physical simulation
	 */
	private JMESimulation simulation;
	
	/**
	 * Mathematical constant pi
	 */
	public final float pi = (float)Math.PI;
	
	/**
	 * Constructor called in QPSS main window 
	 * @param simulation,the physical simulation
	 */
	public fRotateModulePickerBAD(JMESimulation simulation) {
		 this.simulation = simulation;		
	 }	
	
	/* Not used
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickTarget(com.jme.scene.Spatial)
	 */
	
	@Override
	protected void pickTarget(Spatial target) {	}
	
	/* Handles identification of selected module and removal of its components 
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {
		
		int moduleID = component.getModel().getID();		
		RotationDescription rotation = simulation.getModules().get(moduleID).getPhysics().get(0).getRotation();
		
		//System.out.println("rotation: ATRON"+ );//For debugging
		String rotationString = rotation.toString();
		String []temp= null;
		temp = rotationString.split("\\(");
		//System.out.println("rotation:"+ temp[0]+"_"+temp[1]+"_" );//For debugging
		String end = temp[1];
		temp = null;//reset
		temp = end.split("\\)");
		//System.out.println("rotation:"+ temp[0]+"_");//For debugging
		end = "";//reset
		end = temp[0]; 
		temp = null;//reset
		temp = end.split("\\, ");
		System.out.println("rotation:"+ temp[0]+"_"+temp[1]+"_"+temp[2]+"_" );//For debugging
		
		float rotationX = Float.parseFloat(temp[0].toString());
		float rotationY = Float.parseFloat(temp[1].toString());
		float rotationZ = Float.parseFloat(temp[2].toString());
		System.out.println("rotationXYZ:"+ rotationX+"_"+rotationY+"_"+rotationZ+"_" );//For debugging
        
		if (rotationX>0f){
			rotationX = pi+rotationX;
		}else if (rotationX<0f){
			rotationX = -pi+rotationX;
		}
		
		if (rotationY>0f){
			rotationY = pi+rotationY;
		}else if (rotationY<0f){
			rotationY = -pi+rotationY;
		}
/*		if (rotationZ>0f){
			rotationZ = pi+rotationZ;
		}else if (rotationZ<0f){
			rotationZ = -pi+rotationZ;
		}*/
		System.out.println("rotationXYZAfter:"+ rotationX+"_"+rotationY+"_"+rotationZ+"_" );//For debugging
		
		RotationDescription rotationOpposite = new RotationDescription(rotationX,rotationY,rotationZ); 
		
		int nrComponents= simulation.getModules().get(moduleID).getColorList().size();//HACK number of colors of components equals the number of components
		/*
		 * WOULD BE NICE TO CHECK HOW MANY COMPONENTS ARE IN THE MODULE (THIS WILL BE FOR SURE NECESSARY FOR SOLUTION TO BE GENERIC)
		 *Something like that
		 *findModuleToMove(2).getComponents.size()
		 */
		for (int c=0; c<nrComponents;c++){			
			JMEModuleComponent moduleComponent= (JMEModuleComponent)simulation.getModules().get(moduleID).getComponent(c);			
			for(DynamicPhysicsNode part: moduleComponent.getNodes()){
	            part.setLocalRotation(rotationOpposite.getRotation());
			}        
		}	
	}	
}
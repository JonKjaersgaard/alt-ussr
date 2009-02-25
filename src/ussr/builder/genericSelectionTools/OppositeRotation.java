package ussr.builder.genericSelectionTools;

import com.jme.math.Matrix3f;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Spatial;
import com.jmex.physics.DynamicPhysicsNode;

import ussr.description.geometry.RotationDescription;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;

/**
 *DO NOT WORKS WEEL CONSIDER SOTMETHING ELSE
 * @author Konstantinas
 *
 */
public class OppositeRotation  extends CustomizedPicker{
	
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
	public OppositeRotation(JMESimulation simulation) {
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
		//System.out.println("rotation:"+ temp[0]+"_"+temp[1]+"_"+temp[2]+"_" );//For debugging
		
		float rotationX = Float.parseFloat(temp[0].toString());
		float rotationY = Float.parseFloat(temp[1].toString());
		float rotationZ = Float.parseFloat(temp[2].toString());
		//System.out.println("rotationXYZ:"+ rotationX+"_"+rotationY+"_"+rotationZ+"_" );//For debugging
        
/*		if (rotationX>0f){
			rotationX = pi+rotationX;
		}else if (rotationX<0f){
			rotationX = -pi-rotationX;
		}
		
		if (rotationY>0f){
			rotationY = pi+rotationY;
		}else if (rotationY<0f){
			rotationY = -pi-rotationY;
		}
	    if (rotationZ>0f){
			rotationZ = pi+rotationZ;
			
		}else if (rotationZ<0f){
			rotationZ = -pi-rotationZ;
		}*/
		//System.out.println("rotationXYZAfter:"+ rotationX+"_"+rotationY+"_"+rotationZ+"_" );//For debugging
		
		RotationDescription rotationOpposite = new RotationDescription(rotationX,rotationY,rotationZ); 
		
		
		
		Quaternion g = rotation.getRotation();
		float det =g.toRotationMatrix().determinant();
		
		//Quaternion fv = new Quaternion(0);
		
		System.out.println("Quat:"+ g.toString());//For debugging	
		
		
		//Quaternion v = new Quaternion();
		
		//Matrix3f mat = new Matrix3f(1.1920929E-7f,0.0f,1.0f,0.7071068f,0.7071067f,-5.9604645E-8f,-0.7071067f,0.7071068f,5.9604645E-8f); 
		
		//Matrix3f newMat =g.toRotationMatrix().mult(mat); 
		
		//v.set(g.toRotationMatrix().invert());
		
		//System.out.println("Determinant:"+ det);//For debugging
		
		//System.out.println("Column0:"+ g.toRotationMatrix().toString());//For debugging			
		
		//System.out.println("Column0:"+ newMat.toString());//For debugging	
		
		
		/*System.out.println("Column0:"+ g.getRotationColumn(0).toString());//For debugging
		System.out.println("Column1:"+ g.getRotationColumn(1).toString());//For debugging
		System.out.println("Column2:"+ g.getRotationColumn(2).toString());//For debugging
*/		
	/*	Quaternion gf = new Quaternion(1f,1f,1f,1f);
		
		System.out.println("Column0:"+ gf.getRotationColumn(0).toString());//For debugging
		System.out.println("Column1:"+ gf.getRotationColumn(1).toString());//For debugging
		System.out.println("Column2:"+ gf.getRotationColumn(2).toString());//For debugging
*/		
		Vector3f newss = new Vector3f(rotationX,rotationY,rotationZ);
		int nrComponents= simulation.getModules().get(moduleID).getColorList().size();//HACK number of colors of components equals the number of components
		/*
		 * WOULD BE NICE TO CHECK HOW MANY COMPONENTS ARE IN THE MODULE (THIS WILL BE FOR SURE NECESSARY FOR SOLUTION TO BE GENERIC)
		 *Something like that
		 *findModuleToMove(2).getComponents.size()
		 */
		for (int c=0; c<nrComponents;c++){			
			JMEModuleComponent moduleComponent= (JMEModuleComponent)simulation.getModules().get(moduleID).getComponent(c);			
			for(DynamicPhysicsNode part: moduleComponent.getNodes()){
	            //part.setLocalRotation(g);

	           // part.setLocalRotation(g.toRotationMatrix());
	            
			}        
		}	
	}	
}
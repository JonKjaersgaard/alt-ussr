package ussr.builder.genericSelectionTools;

import com.jme.math.Matrix3f;
import com.jme.math.Quaternion;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;

import ussr.builder.QuickPrototyping;
import ussr.builder.construction.MTRANOperationsTemplate;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;

public class MtranExperiment extends CustomizedPicker {

	
	 private JMESimulation simulation;
		private Module selectedModule;
		private int selectedConnectorNr = 1000;

		
		/**
		 * Mathematical constant pi
		 */
		private static final float pi = (float)Math.PI;
		private int counter;
		
		private QuickPrototyping guickPrototyping;
		public MtranExperiment (JMESimulation simulation, QuickPrototyping guickPrototyping, int counter){
			this.simulation = simulation;
			this.counter = counter;
			this.guickPrototyping = guickPrototyping;
			
		}	
	
	
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {
		
		selectedModule = component.getModel();
		VectorDescription positionModule  = selectedModule.getComponent(1).getPosition();
		
		VectorDescription pos0  = selectedModule.getComponent(0).getPosition();
		VectorDescription pos1  = selectedModule.getComponent(1).getPosition();
		VectorDescription pos2  = selectedModule.getComponent(2).getPosition();
		RotationDescription rd0 =  selectedModule.getComponent(0).getRotation();
		RotationDescription rd1 =  selectedModule.getComponent(1).getRotation();
		RotationDescription rd2 =  selectedModule.getComponent(2).getRotation();
		float xModule = positionModule.getX();
		float yModule  = positionModule.getY();
		float zModule  = positionModule.getZ();		
		
		if (counter==0){
		MTRANOperationsTemplate m =  new MTRANOperationsTemplate(simulation);
		Module m1= m.createNewModule(selectedModule);
		//m1.setPosition(positionModule.add(new VectorDescription(0.1f,0.1f,0.1f)));
		m1.getComponent(0).setRotation(rd0);
		m1.getComponent(1).setRotation(rd1);
		m1.getComponent(2).setRotation(rd2);
		m1.setPosition(new VectorDescription(0.1f,0.1f,0.1f));
		//m1.getComponent(0).setPosition(pos0.add(new VectorDescription(0.1f,0.1f,0.1f)));
		//m1.getComponent(1).setPosition(pos1.add(new VectorDescription(0.1f,0.1f,0.1f)));
		//m1.getComponent(2).setPosition(pos2.add(new VectorDescription(0.1f,0.1f,0.1f)));
		}else if(counter ==1){ 
			guickPrototyping.setCounter(-1);
		VectorDescription positionConnector = selectedModule.getConnectors().get(selectedConnectorNr).getPhysics().get(0).getPosition();
		
		float xConnector = positionConnector.getX();
		float yConnector = positionConnector.getY();
		float zConnector  = positionConnector.getZ();
		int lastModule = simulation.getModules().size();
		
		simulation.getModules().get(lastModule-1).setPosition(new VectorDescription(xConnector+(xConnector-xModule),yConnector+(yConnector-yModule),zConnector+(zConnector-zModule)));}
		//simulation.getModules().get(0).getComponent(0).setRotation(selectedModule.getComponent(0).getRotation());
		//simulation.getModules().get(0).getComponent(1).setRotation(selectedModule.getComponent(1).getRotation());
		
		/*Matrix3f m = new Matrix3f();
		m.set(selectedModule.getComponent(0).getRotation().getRotation());
		
		Matrix3f m11 = new Matrix3f();
		m11.set(selectedModule.getComponent(1).getRotation().getRotation());
		
		Quaternion slectQ = selectedModule.getComponent(0).getRotation().getRotation();
	
		Matrix3f m1= null,m2= null, m111= null, m222 = null;
		if (selectedConnectorNr ==0||selectedConnectorNr ==6){			
			if (slectQ.equals(ATRON.ROTATION_DU.getRotation())||slectQ.equals(ATRON.ROTATION_NS.getRotation())){
				m1 = rotateAround(m,"X",  270);				
				 m111 = rotateAround(m11,"X",  270);
			 
			}else{
				m1 = rotateAround(m,"X",  90);				
				 m111 = rotateAround(m11,"X",  90);
			}			 
			 
			 if (slectQ.equals(ATRON.ROTATION_WE.getRotation())){
				 m2 = rotateAround(m1,"Z", -90);
				 m222 = rotateAround(m111,"Z",  -90);			
			 }else{				 
				 m2 = rotateAround(m1,"Z", 90);
				 m222 = rotateAround(m111,"Z",  90); 
			 }			 
			
		}else if(selectedConnectorNr ==1||selectedConnectorNr ==7) {
		   
		   m1 = rotateAround(m,"X",  90);
			 m2 = rotateAround(m1,"Y", - 90);
			 
			 m111 = rotateAround(m11,"X",  90);
			 m222 = rotateAround(m111,"Y", - 90);
		   }else if(selectedConnectorNr ==3||selectedConnectorNr ==5) {
			   m1 = rotateAround(m,"X",  -90);
				 m2 = rotateAround(m1,"Y",  90);
				 
				 m111 = rotateAround(m11,"X",  -90);
				 m222 = rotateAround(m111,"Y",  90);
		   }else if (selectedConnectorNr ==2||selectedConnectorNr ==4) {
			   
			   if (slectQ.equals(ATRON.ROTATION_UD.getRotation())||slectQ.equals(ATRON.ROTATION_NS.getRotation())){
					m1 = rotateAround(m,"X",  270);				
					 m111 = rotateAround(m11,"X",  270);
				 
				}else{
					   m1 = rotateAround(m,"X",  90);
					   m111 = rotateAround(m11,"X",  90);	
				}	
			   
			     if (slectQ.equals(ATRON.ROTATION_WE.getRotation())){
					 m2 = rotateAround(m1,"Z", -270);
					 m222 = rotateAround(m111,"Z",  -270);			
				 }else{				 
					 m2 = rotateAround(m1,"Z",  -90);
					 m222 = rotateAround(m111,"Z",  -90);
				 }							   
		   }
		
		
		Quaternion newQaut = new Quaternion();
		newQaut.fromRotationMatrix(m2);
		
		Quaternion newQaut1 = new Quaternion();
		newQaut1.fromRotationMatrix(m222);	
		
		simulation.getModules().get(0).getComponent(0).setRotation(new RotationDescription(newQaut));
		simulation.getModules().get(0).getComponent(1).setRotation(new RotationDescription(newQaut1));*/
		
	}

	@Override
	protected void pickTarget(Spatial target) {
		if(target instanceof TriMesh) {			
			String name = simulation.getGeometryName((TriMesh)target);
			if(name!=null && name.contains("Connector")){ 
				//System.out.println("Connector: "+name);//For debugging				
				String [] temp = null;	         
				temp = name.split("#");// Split by #, into two parts, line describing the connector. For example "Connector 1 #1"
				this.selectedConnectorNr= Integer.parseInt(temp[1].toString());// Take only the connector number, in above example "1" (at the end)
				System.out.println("CON:" + selectedConnectorNr );
			}
		}	
		
	}
	
	public static Matrix3f rotateAround(Matrix3f matrix,String coordinate, float angle){

		float cos =(float)Math.cos(angle/(180/pi));// division on 180/pi means transformation into radians 
		float sin =(float) Math.sin(angle/(180/pi));

		Matrix3f rotationMatrix = null;
		if (coordinate.equalsIgnoreCase("X")){			
			rotationMatrix = new Matrix3f(1,0,0,0,cos,-sin,0,sin,cos);
		}else if(coordinate.equalsIgnoreCase("Y")){			
			rotationMatrix = new Matrix3f(cos,0,sin,0,1,0,-sin,0,cos);
		}else if(coordinate.equalsIgnoreCase("Z")){			
			rotationMatrix = new Matrix3f(cos,-sin,0,sin,cos,0,0,0,1);					
		}	
		return rotationMatrix.mult(matrix) ;
	}	

}

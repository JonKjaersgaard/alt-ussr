package ussr.builder.controllersLabels;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.jme.scene.Spatial;

import ussr.builder.BuilderHelper;
import ussr.model.Controller;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.CustomizedPicker;


public class AssignController extends CustomizedPicker  {

	
	

	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {
		Module selectedModule = component.getModel();
		String labels = selectedModule.getProperty(BuilderHelper.getLabelsKey());
		Controller moduleController = selectedModule.getController();
		//String[] temp = labels.split(",");
		
		
	//	for (int index=0;index<temp.length;index++){
			try {
				//Class clas = Class.forName("ussr.builder.controllersLabels."+temp[index]+"Controller");
				Class clas = Class.forName("ussr.builder.controllersLabels.WheelController");
				Object controller = clas.newInstance();
				Class[] typs = new Class[]{Module.class}; 
				Method method =clas.getMethod("activate", typs);
				method.invoke(controller, selectedModule);
				
			} catch (ClassNotFoundException e) {				
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
		//}
		
		
	}

	@Override
	protected void pickTarget(Spatial target) {
		// TODO Auto-generated method stub
		
	}
}

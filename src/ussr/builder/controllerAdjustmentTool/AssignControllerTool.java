package ussr.builder.controllerAdjustmentTool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.jme.scene.Geometry;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.pickers.CustomizedPicker;

/**
 * The main responsibility of this class is to assign specific controller
 * passed as a string in format:"package + class name" to  module selected in simulation
 * environment. The precondition is that controller has the method called "activate()" implemented.
 * This method is going to be invoked. 
 * @author Konstantinas
 */
public class AssignControllerTool extends CustomizedPicker  {

	
	/**
	 * The path (directory) where the controller can be located. In format:package + class name.
	 */
	private String packageClassName;
	
	/**
	 * The method name to execute in the controller
	 */
	private final static String methodToAccess = "activate";
	
	public AssignControllerTool(String packageClassName){
		this.packageClassName = packageClassName;
	}	
	
	/*  Method executed when the module is selected with the left side of the mouse in simulation environment. 
	 *  In this case locates specific class in specific package and invokes method called "activate" in it.
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickModuleComponent(ussr.physics.jme.JMEModuleComponent)
	 */
	protected void pickModuleComponent(JMEModuleComponent component) {
		Module selectedModule = component.getModel();	
						
				Class clas;
				try {
					clas = Class.forName(packageClassName);
					Object controller = clas.newInstance();
					Class[] typs = new Class[]{Module.class}; 
					Method method =clas.getMethod(methodToAccess, typs);
					method.invoke(controller, selectedModule);
				} catch (ClassNotFoundException e) {
					throw new Error ("Class not found in in the directory:"+packageClassName);
				} catch (InstantiationException e) {
					throw new Error ("Class can not be instantiated. Class name:"+packageClassName );
				} catch (IllegalAccessException e) {
					throw new Error ("Illegal Acess for class:"+ packageClassName );
				} catch (SecurityException e) {
					throw new Error ("Security Exception for class:"+ packageClassName );
				} catch (NoSuchMethodException e) {
					throw new Error ("Method activate() was not found in class:"+ packageClassName );
				} catch (IllegalArgumentException e) {
					throw new Error ("Illegal argument for method in class:"+ packageClassName );
				} catch (InvocationTargetException e) {
					throw new Error ("Invocation exceptio in class:"+ packageClassName );
				}
	}

	/* Method executed when the module is selected with the left side of the mouse in simulation environment.
	 * Here not used, because it is enough of pickModuleComponent(JMEModuleComponent component) method (look above).
	 * @see ussr.physics.jme.pickers.CustomizedPicker#pickTarget(com.jme.scene.Spatial)
	 */
	@Override
	protected void pickTarget(Geometry target) {		
	}	
}

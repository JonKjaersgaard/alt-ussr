package dead_code;

import ussr.physics.jme.JMEModuleComponent;

public class KKpicker extends CustomizedPicker {

	
	//Disconnect all connectors on the module at runtime
	@Override
	protected void pickModuleComponent(JMEModuleComponent component) {
		
		int k = component.getModel().getConnectors().size();		
		for (int i= 0; i<k;i++ ){			
			component.getModel().getConnectors().get(i).disconnect();
			System.out.println("connector"+i);
		}
		
	}

}

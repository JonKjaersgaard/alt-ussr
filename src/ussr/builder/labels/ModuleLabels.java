package ussr.builder.labels;

import ussr.comm.Packet;
import ussr.model.Connector;
import ussr.model.Module;

/**
 * The main responsibility of this class is to provide support for handling labels
 * previously assigned to the module.
 * @author Konstantinas
 *
 */
public class ModuleLabels extends Label {
	
	/**
	 * The module as an entity in simulation.
	 */
	private Module module;
	
	/**
	 * Handles the labels assigned to the module.
	 * @param module, the module.
	 */
	public ModuleLabels(Module module){
	this.module = module;	
	}	

	/**
	 * Checks if the module was assigned the label passed as a string.
	 * NOTE: IT IS SIMPLE CONTAINS CHECK FOR NOW.
	 * It is the method following STRATEGY pattern.
	 * @param label, the label name to check;
	 * @return true, if passed label was assigned to the module, false - if not. 
	 */
	@Override
	public boolean has(String label) {
		
		if (getLabels(module).contains(label)){
			return true;
		}
		return false;		
	}
	
	public  void sendMessage(/*Connector connector, Packet packet*/){
		int amountTransmitters = module.getTransmitters().size();
		System.out.println("AmountTransmitters: " + amountTransmitters );
	/*	for (int i=0;i<amountTransmitters; i++){
			module.getTransmitters().get(i).send(packet);

		}*/
	}

}

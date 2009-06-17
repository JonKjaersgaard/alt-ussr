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
public class ModuleLabels extends EntityLabels {
	
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
		if (getEntityLabels(module).contains(label)){
			return true;
		}
		return false;		
	}	
	
	
	public void sendMessage(int connectorNr, Packet packet){
		//if(module.getConnectors().get(connectorNr).isConnected()){
		int amountTransmitters = module.getTransmitters().size();
		if (connectorNr>=0 && connectorNr<=amountTransmitters-1){
			module.getTransmitters().get(connectorNr).send(packet);			
			System.out.println("Send it");
		}else throw new Error ("Some");
		
			
			
		//} else throw new Error("Message can not be send because connector Nr:"+ connectorNr+" is not connected");			
	}
	
	public String receiveMessage(int connectorNr){
		//module.getReceivers().get(connectorNr).getData().toString();
		//System.out.println("RECEIVED DATA:"+ module.getReceivers().get(connectorNr).getData().toString());
		
		//if(module.getConnectors().get(connectorNr).isConnected()){
			//System.out.println("Connected");
		//if (module.getReceivers().get(connectorNr).canReceiveFrom(module.getTransmitters().get(5))){
			//System.out.println("CAN Receive:");
		//}
		
		//}
		
		// else throw new Error("Message can not be send because connector Nr:"+ connectorNr+" is not connected");
		
		return module.getReceivers().get(connectorNr).getData().toString();
	}

	@Override
	public String getLabels() {		
		return getEntityLabels(module);
	}

}

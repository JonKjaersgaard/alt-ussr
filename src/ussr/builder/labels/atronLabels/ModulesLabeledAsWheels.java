package ussr.builder.labels.atronLabels;

import java.util.ArrayList;

import ussr.builder.labels.Labels;
import ussr.model.Module;
import ussr.samples.atron.ATRONController;

public abstract class ModulesLabeledAsWheels implements Wheel {
	
	protected ArrayList<String> moduleLabels = new ArrayList<String>();
	
	protected Module module;
	
	protected Labels currentModuleLabels;
	
	protected final String wheelLabel = "wheel";
	
	public abstract void rotateContinuously(float speed);
	
	//public abstract void addLabel(String label);
	
	
	protected void rotateWheelContinuously(float speed){
		ATRONController atronController   = (ATRONController)module.getController();
		atronController.rotateContinuous(speed);			
	}
	
	protected void stopWheel(){
		ATRONController atronController   = (ATRONController)module.getController();
		atronController.rotateDegrees(1);
		atronController.rotateDegrees(-1);		
	}
	
    protected boolean checkExistanceLabels(Labels currentModuleLabels, ArrayList<String> moduleLabels){
		
    	
    	ArrayList<String> temp = new ArrayList<String>();    	
    	int amountLabels = moduleLabels.size(); 
    	
    	/*Populate array of strings with string "true" if all labels assigned to the module
    	 * during construction of morphology are matching the labels assigned through the code*/
    	for(int index=0; index<amountLabels; index++){
    		if (currentModuleLabels.has(moduleLabels.get(index))){    		
    			temp.add("true");
    		}else {temp.add("false");}    		
    	}
    	
    	/*If array contains all "true" then new counter (j) should match the amount of labels
    	 * assigned through the code */
    	int j =0;
    	for (int i =0; i<amountLabels; i++){
    		if (temp.get(i).contains("true")){
    			j++;
    		}
    	}
    	
    	if (j==amountLabels){
    		return true;
    	}
   	
		return false;		
	}
    

	public void addLabel(String label) {
		moduleLabels.add(label);
	}
	
	
}

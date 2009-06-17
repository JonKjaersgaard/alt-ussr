package ussr.builder.labels.atronLabels;

import java.util.ArrayList;

import ussr.builder.labels.Labels;
import ussr.builder.labels.ModuleLabels;
import ussr.model.Module;
import ussr.samples.atron.ATRONController;

public class ATRONWheelModule implements Wheel {

   public ArrayList<String> moduleLabels = new ArrayList<String>();
	
   public Module module;
	
   public Labels currentModuleLabels;
	
	public ATRONWheelModule(Module module){
		this.module = module;
	}
   
   
	@Override
	public void addLabel(String label) {
		moduleLabels.add(label);		
	}

	@Override
	public void rotateContinuously(float speed) {
		/*Get labels of the module*/
		Labels currentModuleLabels = new ModuleLabels(module);		
		if (checkExistanceLabels(currentModuleLabels,moduleLabels)){
			ATRONController atronController   = (ATRONController)module.getController();
			atronController.rotateContinuous(speed);
		}
		
	}

	@Override
	public void stop() {
		/*Get labels of the module*/
		Labels currentModuleLabels = new ModuleLabels(module);
		if(checkExistanceLabels(currentModuleLabels,moduleLabels)){
			ATRONController atronController   = (ATRONController)module.getController();
			atronController.rotateDegrees(1);
			atronController.rotateDegrees(-1);
		}
		
	}
	
    private boolean checkExistanceLabels(Labels currentModuleLabels, ArrayList<String> moduleLabels){
		
    	
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
	

}

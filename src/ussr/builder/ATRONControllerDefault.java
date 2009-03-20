package ussr.builder;

import ussr.model.Sensor;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMESimulation;
import ussr.samples.GenericSimulation;
import ussr.samples.atron.ATRONController;

public class ATRONControllerDefault extends ATRONController  {

	private PhysicsSimulation simulation;
	
	public ATRONControllerDefault(PhysicsSimulation simulation){
		this.simulation = simulation;		
	}


	@Override
	public void activate() {
		yield();
    	this.delay(1000); /* rotateContinuous seem to fail sometimes if we do not wait at first */
        byte dir = 1;
        float lastProx = Float.NEGATIVE_INFINITY; /* for printing out proximity data */
        boolean firstTime = true;
        while(true) {
        	
            // Enable stopping the car interactively:
            if(!GenericSimulation.getActuatorsAreActive()) { yield(); firstTime = true; continue; }
            
            // Basic control: first time we enter the loop start rotating and turn the axle
            String labels = module.getProperty(BuilderHelper.getModuleLabelsKey());
            if(firstTime) {
                firstTime = false;
                /*Rotate around to the left*/
                //rotateAround(labels,dir,  true);                
                /*Rotate around to the right*/
               // rotateAround(labels,dir,  false);          
            //driveForward (labels, dir); //for snake turns it in circules to the left
           //driveBack (labels, dir); // //for snake turns it in circules to the right        
                
            }

            // Print out proximity information
            float max_prox = Float.NEGATIVE_INFINITY;
            for(Sensor s: module.getSensors()) {
                if(s.getName().startsWith("Proximity")) {
                    float v = s.readValue();
                    max_prox = Math.max(max_prox, v);
                }
            }
           /* if(name.startsWith("wheel")&&Math.abs(lastProx-max_prox)>0.01) {
                System.out.println("Proximity "+name+" max = "+max_prox);
                lastProx = max_prox; 
            }*/

            // Always call yield sometimes
        	yield();
        }
		
	}
	
	private void driveForward (String labels, byte dir){
	    if(labels.contains("wheel1")) rotateContinuous(dir);
        if(labels.contains("wheel2")) rotateContinuous(dir);
        if(labels.contains("wheel3")) rotateContinuous(dir);
        if(labels.contains("wheel4")) rotateContinuous(dir);		
	}
	private void driveBack (String labels, byte dir){
		if(labels.contains("wheel1")) rotateContinuous(-dir);
        if(labels.contains("wheel2")) rotateContinuous(-dir);
        if(labels.contains("wheel3")) rotateContinuous(-dir);
        if(labels.contains("wheel4")) rotateContinuous(-dir);		
	}
	
	private void rotateAround(String labels,byte dir, boolean toLeft){
		if (toLeft){
			 if(labels.contains("wheel1")) rotateContinuous(dir);
             if(labels.contains("wheel2")) rotateContinuous(-dir);
             if(labels.contains("wheel3")) rotateContinuous(dir);
             if(labels.contains("wheel4")) rotateContinuous(-dir);
		}else {
			  if(labels.contains("wheel1")) rotateContinuous(-dir);
              if(labels.contains("wheel2")) rotateContinuous(dir);
              if(labels.contains("wheel3")) rotateContinuous(-dir);
              if(labels.contains("wheel4")) rotateContinuous(dir);
		}
		
	}

}

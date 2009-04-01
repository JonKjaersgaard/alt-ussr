package ussr.builder;


import ussr.physics.PhysicsSimulation;

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
    	this.delay(1000); // rotateContinuous seem to fail sometimes if we do not wait at first 
        byte dir = 1;      
        boolean firstTime = true;
        while(true) {
        	
            // Enable stopping the car interactively:
            if(!GenericSimulation.getActuatorsAreActive()) { yield(); firstTime = true; continue; }
            
            // Basic control: first time we enter the loop start rotating and turn the axle
            String labels = module.getProperty(BuilderHelper.getLabelsKey());
            if(firstTime) {
                firstTime = false;
                if (labels ==null){}else{
                //Rotate around to the left
                //rotateAround(labels,dir,  true);                
                //Rotate around to the right
                //rotateAround(labels,dir,  false); 
                
            //driveForward (labels, dir); //for snake turns it in circles to the left
           //driveForward (labels, -dir); // for snake turns it in circles to the right        
                }  
            }            
            // Always call yield sometimes
        	yield();
        }
		
	}
	
	private void driveForward (String labels, byte dir){
	    if(labels.contains("wheel1")) rotateContinuous(dir);
        if(labels.contains("wheel2")) rotateContinuous(dir);
        if(labels.contains("wheel3")) rotateContinuous(dir);
        if(labels.contains("wheel4")) rotateContinuous(dir);
        if(labels.contains("wheel5")) rotateContinuous(dir);
        if(labels.contains("wheel6")) rotateContinuous(dir);
        
       // if(labels.contains("axleFront")&& simulation.getTime()>20&&simulation.getTime()<30)rotate(45) ;
        
	}
	
	private void rotateAround(String labels,byte dir, boolean toLeft){
		if (toLeft){
			 if(labels.contains("wheel1")) rotateContinuous(dir);
             if(labels.contains("wheel2")) rotateContinuous(-dir);
             if(labels.contains("wheel3")) rotateContinuous(dir);
             if(labels.contains("wheel4")) rotateContinuous(-dir);
             if(labels.contains("wheel5")) rotateContinuous(dir);
             if(labels.contains("wheel6")) rotateContinuous(-dir);            
		}else {
			  if(labels.contains("wheel1")) rotateContinuous(-dir);
              if(labels.contains("wheel2")) rotateContinuous(dir);
              if(labels.contains("wheel3")) rotateContinuous(-dir);
              if(labels.contains("wheel4")) rotateContinuous(dir);
              if(labels.contains("wheel5")) rotateContinuous(-dir);
              if(labels.contains("wheel6")) rotateContinuous(dir);             
		}
		
	}

}

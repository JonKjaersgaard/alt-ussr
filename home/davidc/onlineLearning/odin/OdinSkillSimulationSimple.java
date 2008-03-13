package onlineLearning.odin;
import onlineLearning.SkillFileManager;
import onlineLearning.locomoton.MotionRewardSystem;
import ussr.description.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsSimulation;

public class OdinSkillSimulationSimple extends OdinSkillSimulation {
	float oldDist = 0; 
    float oldPrintTime=0;
    float oldSkillTime=0;
    public static void main( String[] args ) {
    	for(int i=0;i<1;i++) { //how to do this?
	    	OdinSkillSimulation simulation = new OdinSkillSimulationSimple();
	    	simulation.runSimulation(null,true);
    	}
    }
	public void physicsTimeStepHook(PhysicsSimulation simulation) {
    	//simulation.setGravity(0.0f);
		//Vector3f cm = MotionRewardSystem.getRobotCM();
		if(simulation.getTime()==0.0) {
			//System.out.println();
			//System.out.println("********** FIRST SKILL: "+MotionSkills.skillToString(OdinSkillController.robotSkill)+" ************");
		}
		if(oldPrintTime+1<simulation.getTime()) {
       		//System.out.print(OdinRewardSystem.getSumOfReward()+", ");
       		MotionRewardSystem.resetReward();
       		oldPrintTime = simulation.getTime();
       	}
       	if(oldSkillTime+3*60<simulation.getTime()) {
       		MotionRewardSystem.resetReward();
       		//if(OdinSkillController.robotSkill==OdinSkillController.nSkills-1) System.exit(0);
       		//OdinSkillController.robotSkill = OdinSkillController.robotSkill+1;
       		//System.out.println();
       		//System.out.println("********** NEXT SKILL: "+OdinMotionSkills.skillToString(OdinSkillController.robotSkill)+" ************");
       		oldSkillTime = simulation.getTime();
       	}
       	if(simulation.getTime()>60*60) { //one hour
   			simulation.stop();
   			//System.out.println("Fitness = "+((cm.x-0)*(cm.x-0)+(cm.z-0)*(cm.z-0)));
   			//System.out.println("Fitness = "+(-cm.distance(new Vector3f(10,0,0))));
       	}
    }
	
	protected void changeWorldHook(WorldDescription world) {
		SkillFileManager.initLogFiles("Odin",robotType.name(),System.currentTimeMillis()%1000+"");
		if(loadSkillsFromFile) SkillFileManager.loadSkills();
		
        /*WorldDescription.BoxDescription[] boxes = new WorldDescription.BoxDescription[5];
        VectorDescription size = new VectorDescription(10f,0.1f,0.05f);
        RotationDescription rotEastWest = new RotationDescription(new Quaternion(new float[]{0,(float)Math.PI/2,0}));
        RotationDescription rotNorthSouth= new RotationDescription(new Quaternion());
        boxes[0] = new WorldDescription.BoxDescription(new VectorDescription(10,-0.4f,0),size,rotEastWest,true);
        boxes[1] = new WorldDescription.BoxDescription(new VectorDescription(-10,-0.4f,0),size,rotEastWest,true);
        boxes[2] = new WorldDescription.BoxDescription(new VectorDescription(0,-0.4f,10),size,rotNorthSouth,true);
        boxes[3] = new WorldDescription.BoxDescription(new VectorDescription(0,-0.4f,-10),size,rotNorthSouth,true);
        
        boxes[4] = new WorldDescription.BoxDescription(new VectorDescription(0,0,0),new VectorDescription(0.05f,0.05f,0.05f),false);
        
        world.setBigObstacles(boxes);*/
    }
	public Controller getController(String type) {
		return new OdinSkillController(type);
	}
}

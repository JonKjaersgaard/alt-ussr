package ussr.home.davidc.onlineLearning.tools;

import java.util.Random;

public class CollectiveLearningTest {
	static Random rand = new Random();
	static int nParticles=10;
	static boolean globalBest=false;
	static boolean printOut = false;
	static RNNPSOTrainer[] trainers;
	
	public static void main(String[] args) {
		trainers = new RNNPSOTrainer[nParticles];
		for(int i=0;i<nParticles;i++) {
			trainers[i] = new RNNPSOTrainer(3,2,1,10);
		}
		iterate(5000,0);
		RNN best = RNNPSOTrainer.getGlobalBest(trainers);
		best.clean();
		System.out.println("POS done - one network found: ");
		best.printNetwork();
		printOut=true;
		System.out.println("Behavior: ");
		evaluate(best, 0);
	}
	
	private static float evaluatePopulation(int evalType) {
		float fitnessSum=0;
		for(int j=0;j<nParticles;j++) {
			float fitness = evaluate(trainers[j].getRNN(),j%4);
			trainers[j].setFitness(fitness);
			fitnessSum+=fitness;
		}
		return fitnessSum;
	}
	
	private static void trainPopulation(int evalType) {
		RNN gBest = RNNPSOTrainer.getGlobalBest(trainers);
		for(int j=0;j<nParticles;j++) {
			if(globalBest) {
				trainers[j].train(gBest,j);
			}
			else {
				RNN lBest = RNNPSOTrainer.getLocalBest(trainers,j);
				//float fitness = evaluate(lBest,evalType);
				//lBest.setFitness(fitness);
				trainers[j].train(lBest,j);
			}
		}
	}
	
	public static float evaluate(RNN nn, int evalType) {
		float error =0;
		for(int i=0;i<1;i++) {
			nn.resetNodes();
			if(printOut) {
				System.out.println("[CL("+evalType+")] \t");
			}
			
			float[] out=null;
			float[] input = new float[3];
			input[0]=1;
			if(evalType==0) {input[1]=0; input[2]=0;}
			if(evalType==1) {input[1]=0; input[2]=1;}
			if(evalType==2) {input[1]=1; input[2]=0;}
			if(evalType==3) {input[1]=1; input[2]=1;}
			for(int k=0;k<10;k++) {
				out = nn.iterate(input);
				if(printOut) {
					float val = (Math.abs(out[0])<0.001)?0:out[0];
					System.out.print(val+", ");
				}
			}
			if(evalType==0) error = out[0];
			if(evalType==1) error = 1-out[0];
			if(evalType==2) error = (float) Math.sqrt(out[0]);
			if(evalType==3) error = out[0]*out[0];
			
			if(printOut) System.out.println();
		}
		if(Float.isNaN(error)) error = 2;
		return -error;
	}
	private static void iterate(int nIterations, int evalType) {
		for(int i=0;i<nIterations;i++) {
			/*Evaluation*/
			float populationfitness=evaluatePopulation(evalType);
			
			/*Train*/
			trainPopulation(evalType);
			
			/*Print*/
			if(i%50==0) {
				RNN gBest = RNNPSOTrainer.getGlobalBest(trainers);
				System.out.print(i+":\t");
				System.out.print("["+populationfitness/nParticles+", "+gBest.getFitness()+"]\t");
				for(int j=0;j<nParticles;j++) {
					System.out.print(trainers[j].getFitness()+"("+RNNPSOTrainer.similarity(trainers[j].getRNN(),gBest)+"%)\t");
				}
				System.out.println();
				//trainers[1].getRNN().printNetwork();
			}
		}
	}
}

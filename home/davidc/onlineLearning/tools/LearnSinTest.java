package onlineLearning.tools;

import java.util.Random;

public class LearnSinTest {
	static Random rand = new Random();
	static int nParticles=10;
	static boolean globalBest=false;
	static RNNPSOTrainer[] trainers;
	
	public static void main(String[] args) {
		trainers = new RNNPSOTrainer[nParticles];
		for(int i=0;i<nParticles;i++) {
			trainers[i] = new RNNPSOTrainer(1,5,1,9);
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
	
	private static void iterate(int nIterations, int evalType) {
		for(int i=0;i<nIterations;i++) {
			/*Evaluation*/
			float fitnessSum=0;
			for(int j=0;j<nParticles;j++) {
				float fitness = evaluate(trainers[j].getRNN(),evalType);
				trainers[j].setFitness(fitness);
				fitnessSum+=fitness;
			}
			RNN gBest = RNNPSOTrainer.getGlobalBest(trainers);
			/*Train*/
			for(int j=0;j<nParticles;j++) {
				if(globalBest) {
					trainers[j].train(gBest,j);
				}
				else {
					RNN lBest = RNNPSOTrainer.getLocalBest(trainers,j);
					float fitness = evaluate(lBest,evalType);
					lBest.setFitness(fitness);
					trainers[j].train(lBest,j);
				}
			}
			
			/*Print*/
			if(i%50==0) {
				/*System.out.print(i+":\t");
				System.out.print("["+fitnessSum/nParticles+", "+gBest.getFitness()+"]\t");
				for(int j=0;j<nParticles;j++) {
					System.out.print(trainers[j].getFitness()+"("+RNNPSOTrainer.similarity(trainers[j].getRNN(),gBest)+"%)\t");
				}
				System.out.println();*/
				
				System.out.println("{"+i+", "+fitnessSum/nParticles+", "+gBest.getFitness()+"},");
				/*System.out.print("["+fitnessSum/nParticles+", "+gBest.getFitness()+"]\t");
				for(int j=0;j<nParticles;j++) {
					System.out.print(trainers[j].getFitness()+"("+similarity(trainers[j].getRNN(),gBest)+"%)\t");
				}*/
				//System.out.println();
				//trainers[1].getRNN().printNetwork();
			}
		}
	}
	
	static boolean printOut = false;
	public static float evaluate(RNN nn, int evalType) {
		float error =0;
		for(int i=0;i<1;i++) {
			nn.resetNodes();
			if(printOut) {
				System.out.println("[Sin(t)] \t");
			}
			
			float[] out=null;
			float[] input = new float[1];
			input[0]=1;
			for(int k=0;k<200;k++) {
				//input[1] = 1;//(float)((k/10.0)%6.28);
				out = nn.iterate(input);
				if(printOut) {
					float val = (Math.abs(out[0])<0.001)?0:out[0];
					System.out.print(val+", ");
				}
				error += (Math.sin(k/10.0)-out[0])*(Math.sin(k/10.0)-out[0]);
			}
			if(printOut) System.out.println();
		}
		return -error;
	}
}

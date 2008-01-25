package onlineLearning.tools;

import java.util.Random;

public class RNNPSOTrainer {
	static Random rand = new Random();
	
	RNN X;
	RNN P;
	float[][] V;
	int nNode;
	float c1 = 2;
	float c2 = 2;
	float w  = 0.5f;
	float vMax = 10f;
	float xMax = 10f;

	public RNNPSOTrainer(int nInput, int nHidden, int nOutput, int nConnection) {
		nNode = nInput + nHidden + nOutput;
		X = new RNN(nInput, nHidden, nOutput, nConnection);
		P = new RNN(nInput, nHidden, nOutput, nConnection);
		V = new float[nNode][nNode];
	}
	
	public void train(RNN G, int debugIndex) {
		float r1, r2, r3;
		for(int i=0;i<nNode;i++) {
			for(int j=0;j<nNode;j++) {
				int xIndex = X.getConnection(i,j);
				int gIndex = G.getConnection(i,j);
				int pIndex = P.getConnection(i,j);
				r1 = rand.nextFloat();
				r2 = rand.nextFloat();
				r3 = rand.nextFloat();
				float x=0,g=0,p=0,v=0; 
				if(xIndex!=-1) x = X.weight[xIndex];
				if(gIndex!=-1) g = G.weight[gIndex];
				if(pIndex!=-1) p = P.weight[pIndex];
				v = V[i][j];
				if(xIndex==-1&&gIndex==-1&&pIndex==-1) {
					//v += rand.nextGaussian();
				}
				v = clamp(w*v+c1*r1*(p-x)+c2*r2*(g-x), -vMax, vMax); //standard
				//v = clamp(w*v+c1*r1*(p-x)+c2*r2*(g-x)+(float)(c2*r3*rand.nextGaussian()), -vMax, vMax); //repulsive
				x = clamp(x + v, -xMax, xMax);
				V[i][j] = v;
				float noise = 0;//(float)(rand.nextFloat()>0.90?1f*rand.nextGaussian():0);
				int index = X.insertConnectionExclusive(i, j, x + noise , G, P);
				
				if(xIndex==-1&&gIndex==-1&&pIndex==-1) {
					if(index==-1) {
						//System.out.println("NOOO");
					}
					else {
						//System.out.println("new Connection added: "+X.connectionToString(index));
					}
				}
				
				//if(debugIndex==1) System.out.println("index = "+index);
			}
		}
		if(rand.nextFloat()>0.99&&!G.equals(P)) {
			P.resetNetwork();
			X.resetNetwork();
		}
	}
	public void setFitness(float fitness) {
		X.setFitness(fitness);
		if(fitness>P.getFitness()) {
			P.cloneFrom(X);
			int removedConnection = P.clean();
//			System.out.println("New P found cleaned = "+removedConnection);
		}
	}
	public float getFitness() {
		return X.getFitness();
	}
	public float getPFitness() {
		if(P==null) return Float.MIN_VALUE;
		return P.getFitness();
	}
	public RNN getPRNN() {
		return P;
	}
	public RNN getRNN() {
		return X;
	}
	private float clamp(float val, float min, float max) {
		if(val>max) return max;
		if(val<min) return min;
		return val;
	}
	
	static int nParticles=10;
	static boolean globalBest=false;
	static RNNPSOTrainer[] trainers;
	public static void main(String[] args) {
		trainers = new RNNPSOTrainer[nParticles];
		for(int i=0;i<nParticles;i++) {
			trainers[i] = new RNNPSOTrainer(3,2,1,8);
		}
		iterate(1000,0);
		//how to deal with changing tasks?
		//how to deal with noise in fitness?
		iterate(1000,1);
		
		RNN best = getGlobalBest(trainers);
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
			RNN gBest = getGlobalBest(trainers);
			/*Train*/
			for(int j=0;j<nParticles;j++) {
				if(globalBest) {
					trainers[j].train(gBest,j);
				}
				else {
					RNN lBest = getLocalBest(trainers,j);
					float fitness = evaluate(lBest,evalType);
					lBest.setFitness(fitness);
					trainers[j].train(lBest,j);
				}
			}
			
			/*Print*/
			if(i%50==0) {
				if(false) {
					System.out.print(i+":\t");
					System.out.print("["+fitnessSum/nParticles+", "+gBest.getFitness()+"]\t");
					for(int j=0;j<nParticles;j++) {
						System.out.print(trainers[j].getFitness()+"("+similarity(trainers[j].getRNN(),gBest)+"%)\t");
					}
					System.out.println();
				}
				else {
					System.out.println("{"+i+", "+fitnessSum/nParticles+", "+gBest.getFitness()+"},");
				}
				//trainers[1].getRNN().printNetwork();
			}
		}
	}

	public static float similarity(RNN rnn, RNN best) {
		float sameConnection =0;
		for(int i=0;i<rnn.nNode;i++) {
			for(int j=0;j<rnn.nNode;j++) {
				if(rnn.getConnection(i, j)!=-1&&best.getConnection(i, j)!=-1) 
					sameConnection++;
			}
		}
		return 100*sameConnection/rnn.nConnection;
	}

	public static RNN getLocalBest(RNNPSOTrainer[] trainers, int index) {
		int neighborhoot=2;
		
		float best = trainers[index].getPFitness();
		int bestIndex = index;
		
		for(int i=index-neighborhoot;i<=index+neighborhoot;i++) {
			int k = i%trainers.length;
			if(k<0) k=trainers.length+k;
			if(trainers[k].getPFitness()>best) {
				best = trainers[k].getPFitness();
				bestIndex = k;
			}
		}
		//System.out.println("Best "+bestIndex+ "Fitness = "+best);
		return trainers[bestIndex].getPRNN();
	}

	public static RNN getGlobalBest(RNNPSOTrainer[] trainers) {
		float best = trainers[0].getPFitness();
		int bestIndex = 0;
		for(int i=0;i<trainers.length;i++) {
			if(trainers[i].getPFitness()>best) {
				best = trainers[i].getPFitness();
				bestIndex = i;
			}
		}
		//System.out.println("Best "+bestIndex+ "Fitness = "+best);
		return trainers[bestIndex].getPRNN();
	}
	static float[][] trainSet = {{0,0,1},{0,1,1},{1,0,1},{1,1,1}};
	static float[][] goalSet1 = {{0},{1},{1},{0}}; //Xor problem
	static float[][] goalSet2 = {{0},{0},{0},{1}}; //and problem
	static boolean printOut = false;
	public static float evaluate(RNN nn, int evalType) {
		float error =0;
		for(int i=0;i<trainSet.length;i++) {
			nn.resetNodes();
			if(printOut) {
				System.out.print("[");
				for(int j=0;j<trainSet[i].length-1;j++) {
					System.out.print(trainSet[i][j]+", ");
				}
				System.out.print(trainSet[i][trainSet[i].length-1]+"]\t");
			}
			
			float[] out=null;
			for(int k=0;k<10;k++) {
				out = nn.iterate(trainSet[i]);
				if(printOut) {
					System.out.print("{");
					for(int j=0;j<out.length-1;j++) {
						System.out.print(out[j]+", ");
					}
					System.out.print(out[out.length-1]+"}, ");
				}
			}
			if(printOut) System.out.println();
			float[][] goalSet=null;
			if(evalType==0) goalSet = goalSet1;
			if(evalType==1) goalSet = goalSet2;
			for(int j=0;j<goalSet[i].length;j++) {
				error += (goalSet[i][j]-out[j])*(goalSet[i][j]-out[j]);
			}
		}
		return -error;
	}
}

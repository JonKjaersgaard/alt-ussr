package onlineLearning.tools;

import java.util.Random;

public class PSO {
	int nParticles;
	int objType;
	int nDimensions;
	Random rand;
	float[][] V; //current velocity of a particle
	float[][] X; //current position of a particle
	float[][] P; //current best position of a particle
	float[][] F; //current and best fitness of a particle
	
	float w = 0.90f; //momentum term (usually slightly less than 1)
	float c1 = 2.0f; //cognitive component (usually app. 2)
	float c2 = 2.0f; //social component (usually app. 2)
	
	float xMax = 20f; //maximum position
	float vMax = 2*xMax; //maximum velocity of a dimension
	
	FitnessEvaluator evaluator;
	
	public PSO(int nParticles, int nDimensions, int objType) {
		this.nParticles = nParticles;
		this.objType = objType;
		this.nDimensions = nDimensions;
		this.rand = new Random();
		 V = new float[nParticles][nDimensions];
		 X = new float[nParticles][nDimensions];
		 P = new float[nParticles][nDimensions];
		 F = new float[nParticles][2];
		 for(int i = 0; i<nParticles;i++) {
			for(int j=0;j<nDimensions;j++) {
				X[i][j] = xMax*(rand.nextFloat()-0.5f);
				P[i][j] = xMax*(rand.nextFloat()-0.5f);
			}
			F[i][0] = Float.NEGATIVE_INFINITY;
			F[i][1] = Float.NEGATIVE_INFINITY;
		 }
	}
	public void iterate() {
		updateAll();
		evaluateAll();
	}
	public void evaluateAll() {
		for(int i = 0; i<nParticles;i++) {
			F[i][0] = fitnessOf(X[i]);
			if(F[i][0]>F[i][1]) { //new particle best
				F[i][1] = F[i][0]; //Store new current best
				for(int j=0;j<nDimensions;j++) {
					P[i][j] = X[i][j];
				}
			}
		}
	}
	public float[] getParticle(int particle) {
		return X[particle];
	}
	public float getMaxValue() {
		return xMax;
	}
	public void update(int particle) {
		float[] G = findGlobalBest();
		float r1, r2;
		for(int j=0;j<nDimensions;j++) {
			r1 = rand.nextFloat();
			r2 = rand.nextFloat();
			V[particle][j] = w*V[particle][j]+c1*r1*(P[particle][j]-X[particle][j])+c2*r2*(G[j]-X[particle][j]); //update velocity of i,j
			V[particle][j] = clamp(V[particle][j],-vMax,vMax);
			X[particle][j] = X[particle][j]+V[particle][j]; //update position of dimension j in particle i
			X[particle][j] = clamp(V[particle][j],-xMax,xMax);
		}
	}
	public void setFitness(int particle, float fitness) {
		F[particle][0] = fitness;
		if(F[particle][0]>F[particle][1]) { //new particle best
			F[particle][1] = F[particle][0]; //Store new current best
			for(int j=0;j<nDimensions;j++) {
				P[particle][j] = X[particle][j];
			}
		}
	}
	public float getPFitness(int particle) {
		return F[particle][1];
	}
	private float fitnessOf(float[] p) {
		float fitness=0;
		if(objType==0) {
			evaluator.fitnessOf(p);
		}
		if(objType==1) {
			float sum=0;
			for(int i=0;i<nDimensions;i++) {
				sum+=p[i];
			}
			fitness = 1-Math.abs(1-sum);
		}
		if(objType==2) { //rosenbrock = sum_{i=1:D-1} 100*(x(i+1) - x(i)^2)^2 + (1-x(i))^2
			float sum=0;
			for(int i=0;i<nDimensions-1;i++) {
				sum += 100*(p[i+1]-p[i]*p[i])*(p[i+1]-p[i]*p[i])+(1-p[i])*(1-p[i]);
			}
			fitness = -sum; //minimize Rotenbrock function 
		}
		return fitness;
	}
	public void setEvaluator(FitnessEvaluator eval) {
		evaluator=eval;
	}
	private void updateAll() {
		float[] G = findGlobalBest();
		float r1, r2;
		for(int i = 0; i<nParticles;i++) {
			for(int j=0;j<nDimensions;j++) {
				r1 = rand.nextFloat();
				r2 = rand.nextFloat();
				V[i][j] = w*V[i][j]+c1*r1*(P[i][j]-X[i][j])+c2*r2*(G[j]-X[i][j]); //update velocity of i,j
				//if(i==0&&j==0) System.out.println("G="+G[j]+" X="+X[i][j]+" V="+V[i][j]+" 1="+c1*r1*(P[i][j]-X[i][j])+" 2="+c2*r2*(G[j]-X[i][j]));
				V[i][j] = clamp(V[i][j],-vMax,vMax);
				X[i][j] = X[i][j]+V[i][j]; //update position of dimension j in particle i
				X[i][j] = clamp(V[i][j],-xMax,xMax);
				//if(i==0) System.out.println(X[i][j]);; 
			}
		}
	}
	private float clamp(float val, float min, float max) {
		if(val>max) return max;
		if(val<min) return min;
		return val;
	}
	private float maxFitness() {
		float max = F[0][0];
		for(int i = 0; i<nParticles;i++) {
			if(F[i][0]>max) {
				max = F[i][0];
			}
		}
		return max;
	}
	private float averageFitness() {
		float average = 0;
		for(int i = 0; i<nParticles;i++) {
			average += F[i][0];
		}
		return average/nParticles;
	}
	private float minFitness() {
		float min = F[0][0];
		for(int i = 0; i<nParticles;i++) {
			if(F[i][0]<min) {
				min = F[i][0];
			}
		}
		return min;
	}
	public float gBestFitness() {
		float best = F[0][1];
		for(int i = 0; i<nParticles;i++) {
			if(F[i][1]>best) {
				best = F[i][1];
			}
		}
		return best;
	}
	public int gBestIndex() {
		float best = F[0][1];
		int bestIndex = 0;
		for(int i = 0; i<nParticles;i++) {
			if(F[i][1]>best) {
				best = F[i][1];
				bestIndex = i; 
			}
		}
		return bestIndex;
	}
	public float[] findGlobalBest() {
		int bestIndex = gBestIndex();
		return P[bestIndex];
	}
	public static void main(String[] args) {
		PSO pso = new PSO(20,100,1);
		for(int i=0;i<1000;i++) {
			pso.iterate();
			if(i%50==0) System.out.println(i+"\t"+pso.gBestFitness()+": \t"+pso.averageFitness()+"\t"+pso.maxFitness()+"\t"+pso.minFitness());
		}
		float[] best = pso.findGlobalBest();
		System.out.print("Best particle = ");
		for(int i=0;i<best.length;i++) {
			System.out.print(best[i]+" \t");
		}
		System.out.println("");
	}
}


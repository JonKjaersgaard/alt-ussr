package onlineLearning.tools;

import java.util.Random;

public class RNN {
	int nInput,nOutput,nHidden,nNode,nConnection;
	public int index=-1;
	int[] from;
	int[] to;
	float[] weight;
	float[] node;
	float fitness = Float.NEGATIVE_INFINITY;
	static Random rand = new Random(10);
	
	public RNN(int nInput, int nHidden, int nOutput, int nConnection) {
		this.nConnection= nConnection;
		this.nInput	= nInput;
		this.nHidden	= nHidden;
		this.nOutput	= nOutput;
		this.nNode	= nInput+nHidden+nOutput;
		
		from = new int[nConnection];
		to = new int[nConnection];
		weight = new float[nConnection];
		node = new float[nNode];
		
		resetNetwork();
		resetNodes();
	}
	public void resetNetwork() {
		for(int i=0;i<nConnection;i++) {
			removeConnection(i);
		}
		while(getFreeConnection()!=-1) {
			insertConnection(myRand(0,nNode),myRand(0,nNode),2.0f*(rand.nextFloat()-0.5f));
		}
		setFitness(Float.NEGATIVE_INFINITY);
	}
	public float getFitness() {	
		return fitness; 
	}
	public void setFitness(float fitness) {	
		this.fitness=fitness; 
	}
	public int[] getFrom() {
		return from;
	}
	public int[] getTo() {
		return to;
	}
	public float[] getWeight() {
		return weight;
	}
	public float[] getNode() {
		return node;
	}
	public int myRand(int min, int max) {
		return rand.nextInt(nNode);
	}
	public float[] iterate(float[] input)
	{
		float[] newNode = new float[nNode];
		for(int i=0;i<nInput;i++) {
			newNode[i]=input[i]; //connection from input til neuron with weight 1
			//node[i]=input[i]; //connection from input til neuron with weight 1
		}
		
		for(int i=0;i<nConnection;i++) {
			//newNode[to[i]] += weight[i]*node[from[i]];
			newNode[to[i]] += floatScale(byteScale(weight[i], -10, 10), -10, 10)*node[from[i]];
		}

		for(int i=0;i<nNode;i++) {
			//node[i]=(float)(1.0/(1+Math.exp(-newNode[i]))); //sigmoid activation funtion
			node[i]=(float)(Math.tanh(newNode[i])); //much better (sigmoid never minus!)
			//node[i]=newNode[i];
		}
		
		float[] output = new float[nOutput];
		for(int i=0;i<nOutput;i++) {
			output[i]=node[nNode-i-1];
		}
		return output;
	}
	public int insertConnectionExclusive(int fromNode, int toNode, float weightVal, RNN g, RNN p) {
		int index = insertConnection(fromNode, toNode, weightVal);
		if(index==-1) index = getWeakestConnectionNotIn(g,p);
		if(index==-1) index = getWeakestConnectionNotIn(g,g);
		if(index==-1) index = getWeakestConnectionNotIn(p,p);
		if(index==-1) index = getWeakestConnection();
		if(index==-1) return -1;
		//if(rand.nextFloat()*Math.abs(weight[index])>rand.nextFloat()*Math.abs(weightVal)) return -1;
		if(Math.abs(weight[index])>Math.abs(weightVal)) return -1;
		insertConnectionAt(fromNode, toNode, weightVal, index);
		return index;
	}
	public int insertConnection(int fromNode, int toNode, float weightVal) {
		//if(fromNode>=toNode) return -1;
		int index = getConnection(fromNode,toNode); //if already there it is a update
		if(index==-1) index = getFreeConnection(); //otherwise find a free connection
		if(index==-1) return -1;
		insertConnectionAt(fromNode, toNode, weightVal, index);
		return index;
	}
	private void insertConnectionAt(int fromNode, int toNode, float weightVal, int index) {
		from[index] = fromNode;
		to[index] = toNode;
		weight[index] = weightVal;	
	}
	public void removeConnection(int index) {
		insertConnectionAt(0, 0, 0, index);
	}
	public int clean() {
		int cleaned = clean(true);
		//if(cleaned!=0) System.out.println("Freed1 "+cleaned+" connection");
		cleaned += clean(false);
		//if(cleaned!=0) System.out.println("Freed2 "+cleaned+" connection");
		return cleaned;
	}
	private int clean(boolean fromOut) {
		boolean[] activeNodes = new boolean[nNode];
		boolean[] activeConnection = new boolean[nConnection];
		if(fromOut) {
			for(int i=0;i<nOutput;i++) {
				activeNodes[nNode-i-1] = true;
			}
		}
		else {
			for(int i=0;i<nInput;i++) {
				activeNodes[i] = true;
			}
		}
		
		boolean change = true;
		while(change) {
			change = false;
			for(int i=0;i<nConnection;i++) {
				if(!activeConnection[i]&&((activeNodes[to[i]]&&fromOut)||activeNodes[from[i]]&&!fromOut)) {
					activeConnection[i] = true;
					if(fromOut) activeNodes[from[i]] = true;
					else activeNodes[to[i]] = true;
					change=true;
				}
			}
		}
		int cleanCount=0;
		for(int i=0;i<nConnection;i++) {
			if(!activeConnection[i]) {
				removeConnection(i);
				cleanCount++;
			}
		}
		return cleanCount;
	}
	private int getWeakestConnectionNotIn(RNN g, RNN p) {
		float weakestVal=Float.MAX_VALUE;
		int weakestIndex=-1;
		for(int i=0;i<nConnection;i++) {
			if(g.getConnection(from[i], to[i])==-1&&p.getConnection(from[i], to[i])==-1) { 
				if(weight[i]<weakestVal) {
					weakestVal=weight[i];
					weakestIndex=i;
				}
			}
		}
		return weakestIndex;
	}
	private int getWeakestConnection() {
		float weakestVal=weight[0];
		int weakestIndex=0;
		for(int i=0;i<nConnection;i++) {
			if(weight[i]<weakestVal) {
				weakestVal=weight[i];
				weakestIndex=i;
			}
		}
		return weakestIndex;
	}
	public int getConnection(int fromNode, int toNode) {
		for(int i=0;i<nConnection;i++) {
			if(from[i]==fromNode&&to[i]==toNode) {
				return i;
			}
		}
		return -1;
	}
	private int getFreeConnection() {
		for(int i =0;i<nConnection;i++) {
			if(weight[i]==0.0) {
				return i;
			}
		}
		return -1;
	}
	public void printNetwork() {
		for(int i =0;i<nConnection;i++) {
			System.out.println(connectionToString(i)+"("+weight[i]+")");
		}
	}
	public String connectionToString(int index) {
		if(weight[index]==0) return "null";
		return from[index] +"->"+to[index];
	}
	public void resetNodes() {
		for(int i=0;i<nNode;i++) {
			node[i] = 0; //dont work = (float)rand.nextGaussian();
		}
	}
	public void cloneFrom(RNN nn) {
		for(int i=0;i<nConnection;i++) {
			from[i] = nn.getFrom()[i];
			to[i] = nn.getTo()[i];
			weight[i] = nn.getWeight()[i];;
		}
		for(int i=0;i<nNode;i++) {
			node[i] = nn.getNode()[i];
		}
		setFitness(nn.getFitness());
	}

	public static void main(String[] args) {
		RNN rnn = new RNN(2,2,2,8);
		
		float[] input1 = {1,1};
		for(int i=0;i<10;i++) {
			float[] output = rnn.iterate(input1);
			System.out.println(i+":\t"+output[0]+"\t"+output[1]);
		}
		
		float[] input2 = {0,0};
		for(int i=0;i<10;i++) {
			float[] output = rnn.iterate(input2);
			System.out.println(i+":\t"+output[0]+"\t"+output[1]);
		}
		
		System.out.print("Connections = ");
		for(int i=0;i<8;i++){
			System.out.print(rnn.connectionToString(i)+"  ");
		}
		System.out.println();
	}
	private byte byteScale(float data, float min, float max) {
		if(data>max) data = max;
		if(data<min) data = min;
		float alpha = 127/(max-min);
		byte result = (byte)(alpha*(data - min) + min);
		return result;
	}
	private float floatScale(byte data, float min, float max) {
		float alpha = 127f/(max-min);
		float result = (float)(1.0/alpha*(data - min) + min);
		//if(result>max) result = max;
		//if(result<min) result = min;
		return result;
		
	}
	public byte[] toByteArray() {
		byte[] data = new byte[1+3*nConnection]; //fitness
		data[0] = byteScale(getFitness(), -5f, 5f);
		//System.out.println("This fitness "+getFitness()+" "+data[0]);
		for(int i=0;i<nConnection;i++) {
			data[3*i+1] = (byte)from[i];
			data[3*i+2] = (byte)to[i];
			data[3*i+3] = byteScale(weight[i], -10, 10);
		}
		return data;
	}
	public void fromByteArray(byte[] data) {
		setFitness(floatScale(data[0], -5, 5));
		//System.out.println("Just set fitnes "+getFitness()+" "+data[0]);
		for(int i=0;i<nConnection;i++) {
			from[i] 	= data[3*i+1];
			to[i] 		= data[3*i+2];
			weight[i] 	= floatScale(data[3*i+3], -10, 10);
		}
	}
}

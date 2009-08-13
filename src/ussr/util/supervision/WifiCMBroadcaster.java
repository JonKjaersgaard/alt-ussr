package ussr.util.supervision;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ussr.comm.Packet;
import ussr.comm.RadioTransmitter;
import ussr.comm.TransmissionType;
import ussr.model.Module;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsSimulation;

import com.jme.math.Vector3f;
import com.sun.corba.se.impl.ior.ByteBuffer;

public class WifiCMBroadcaster implements PhysicsObserver {
	final byte LEARNING_MESSAGE = 4;
	final byte label = 1;
	PhysicsSimulation simulation;
	double deltaT;
	double nextT;
	CMTracker tracker;
	RadioTransmitter radio;
	Vector3f oldPos;
	byte stateCount;
	public WifiCMBroadcaster(PhysicsSimulation simulation, double deltaT, CMTracker tracker) {
		this.simulation = simulation;
		this.deltaT = deltaT;
		this.tracker = tracker;
		Module dummyModule = new Module(simulation,-1);
		radio = new RadioTransmitter(dummyModule, dummyModule,TransmissionType.RADIO, Float.MAX_VALUE);
		nextT = simulation.getTime()+deltaT;
		oldPos = tracker.getRobotCM();
		stateCount = 0;
		initXYscatterPlot();
	}
	
	/**
	 * Transmit robots movement over wifi 
	 */
	public void physicsTimeStepHook(PhysicsSimulation simulation) {
		if(nextT<simulation.getTime()) {
			//Long before = System.currentTimeMillis();
			float dist = tracker.getRobotCM().distance(oldPos);
			addToXYscatterPlot(simulation.getTime(), dist/deltaT*100);
			byte reward = (byte)(250*dist);
			if(!Float.isNaN(dist)) {				
	 			ByteBuffer bb = new ByteBuffer(1);
				bb.append(LEARNING_MESSAGE);
				bb.append(label);
				bb.append(stateCount);
				bb.append(reward);
				bb.trimToSize();
				System.out.println(stateCount+": Reward send = "+reward+" msg length "+bb.toArray().length);
				Packet packet = new Packet(bb.toArray());
				radio.send(packet);
			}
			nextT += deltaT;
			stateCount++;
			oldPos = tracker.getRobotCM();
			//Long after = System.currentTimeMillis();
			//System.out.println("Time to compute: "+(after-before));
		}
	}
	static XYSeries series1;
	public static void addToXYscatterPlot(double x, double y) {
		series1.add(x, y);
	}
	
	public static void initXYscatterPlot() {
		 // create a dataset...
        series1 = new XYSeries("Series 1");
        XYDataset dataset = new XYSeriesCollection(series1);
        
        JFreeChart chart = ChartFactory.createXYLineChart(
        	    "Locomotion Velocity",  // chart title
                "Time (sec)",
                "Reward (cm/sec)",
                dataset,         // data
                PlotOrientation.VERTICAL,
                false,            // include legend
                false,            // tooltips
                false            // urls
            );
		ChartFrame frame = new ChartFrame("Reward Chart", chart);
		frame.pack();
		
		frame.setSize(2*690/3, 2*450/3);
		//frame.setLocation(x, y);
		frame.setVisible(true);
	}
}

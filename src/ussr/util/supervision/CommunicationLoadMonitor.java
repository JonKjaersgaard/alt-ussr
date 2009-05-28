package ussr.util.supervision;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ussr.comm.Packet;
import ussr.comm.RadioTransmitter;
import ussr.comm.TransmissionType;
import ussr.comm.monitors.StatisticalMonitor;
import ussr.model.Module;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsSimulation;

import com.jme.math.Vector3f;
import com.sun.corba.se.impl.ior.ByteBuffer;

public class CommunicationLoadMonitor implements PhysicsObserver {
	PhysicsSimulation simulation;
	XYSeries series1;
	double nextT;
	double deltaT;
	StatisticalMonitor commMonitor;
	public CommunicationLoadMonitor(PhysicsSimulation simulation, double deltaT) {
		this.simulation = simulation;
		this.deltaT = deltaT;
		commMonitor = new StatisticalMonitor(1.0f);
		PhysicsFactory.getOptions().addCommunicationMonitor(commMonitor);
		nextT = simulation.getTime()+deltaT;
		initXYscatterPlot();
	}
	
	public void physicsTimeStepHook(PhysicsSimulation simulation) {
		if(nextT<simulation.getTime()) {
			for(int id=0;id<simulation.getModules().size();id++) {
				for(int channel=0;channel<=8;channel++) {
					int bitpersec = commMonitor.getBitOutWindow(id, channel);
					if(bitpersec==Integer.MIN_VALUE) {
						bitpersec=0;
					}
					addToXYscatterPlot(simulation.getTime(), bitpersec);
					//System.out.println("Module "+id+" sends "+bitpersec+" bits/sec on channel "+channel);
				}
			}
			nextT += deltaT;
		}
	}
	
	public void addToXYscatterPlot(float x, float y) {
		series1.add(x, y);
	}
	
	public void initXYscatterPlot() {
		 // create a dataset...
        series1 = new XYSeries("Series 1");
        XYDataset dataset = new XYSeriesCollection(series1);
        
        JFreeChart chart = ChartFactory.createXYLineChart(
        	    "Communication Load",  // chart title
                "Time (sec)",
                "Communication (bits/sec)",
                dataset,         // data
                PlotOrientation.VERTICAL,
                false,            // include legend
                true,            // tooltips
                false            // urls
            );
        
        // 	create and display a frame...
        //ChartPanel panel = new ChartPanel(chart);
		ChartFrame frame = new ChartFrame("Communication Chart", chart);
		frame.pack();
		//System.out.println(frame.getSize());
		frame.setSize(2*690/3, 2*450/3);
		frame.setVisible(true);
	}
}

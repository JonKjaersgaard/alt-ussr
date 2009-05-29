package ussr.util.supervision;

import java.awt.AWTEvent;
import java.awt.Toolkit;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ussr.comm.monitors.StatisticalMonitor;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsSimulation;
import ussr.util.WindowSaver;

public class CommunicationLoadMonitor implements PhysicsObserver {
	PhysicsSimulation simulation;
	XYSeries series1;  
	double nextT;
	double deltaT;
	StatisticalMonitor commMonitor;
	public CommunicationLoadMonitor(PhysicsSimulation simulation, double deltaT) {
		this.simulation = simulation;
		this.deltaT = deltaT;
		commMonitor = new StatisticalMonitor(deltaT);
		PhysicsFactory.getOptions().addCommunicationMonitor(commMonitor);
		nextT = simulation.getTime()+deltaT;
		initXYscatterPlot();
	}
	
	public void physicsTimeStepHook(PhysicsSimulation simulation) {
		if(nextT<simulation.getTime()) {
			int maxbps = 0;
			int bpsSum = 0;
			int channelCount=0;
			for(int id=0;id<simulation.getModules().size();id++) {
				for(int channel=0;channel<8;channel++) {
					int bitpersec = commMonitor.getBitOutWindow(id, channel);
					if(bitpersec==Integer.MIN_VALUE) {
						bitpersec=0;
					}
					if(bitpersec>maxbps) {
						maxbps = bitpersec;
					}
					if(simulation.getModules().get(id).getConnectors().get(channel).hasProximateConnector()) {
						channelCount++;
						bpsSum +=bitpersec;
					}
					//System.out.println("Module "+id+" sends "+bitpersec+" bits/sec on channel "+channel);
				}
			}
			addMaxToXYscatterPlot(simulation.getTime(), maxbps);
			addMeanToXYscatterPlot(simulation.getTime(), ((float)bpsSum)/channelCount);
			nextT += deltaT;
		}
	}
	
	public void addMaxToXYscatterPlot(float x, float y) {
		series1.add(x, y);
	}
	public void addMeanToXYscatterPlot(float x, float y) {
		series2.add(x, y);
	}
	XYSeries series2;
	public void initXYscatterPlot() {
		 // create a dataset...
        series1 = new XYSeries("Max");
        series2 = new XYSeries("Mean");
        
        XYSeriesCollection dataset = new XYSeriesCollection(); 
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        
        JFreeChart chart = ChartFactory.createXYLineChart(
        	    "Communication Load",  // chart title
                "Time (sec)",
                "Communication (bits/sec)",
                dataset,         // data
                PlotOrientation.VERTICAL,
                true,            // include legend
                false,            // tooltips
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

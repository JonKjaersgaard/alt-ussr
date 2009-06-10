package ussr.util.supervision;

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

public class CommunicationLostMonitor implements PhysicsObserver {
	PhysicsSimulation simulation;
	XYSeries max, mean;  
	double nextT;
	double deltaT;
	StatisticalMonitor commMonitor;
	public CommunicationLostMonitor(PhysicsSimulation simulation, double deltaT) {
		this.simulation = simulation;
		this.deltaT = deltaT;
		commMonitor = new StatisticalMonitor(deltaT);
		PhysicsFactory.getOptions().addCommunicationMonitor(commMonitor);
		nextT = simulation.getTime()+deltaT;
		initXYscatterPlot();
	}
	
	public void physicsTimeStepHook(PhysicsSimulation simulation) {
		if(nextT<simulation.getTime()) {
			float max_lost_percent = 0;
			float mean_lost_percent = 0;
			
			int channelCount=0;
			for(int id=0;id<simulation.getModules().size();id++) {
				for(int channel=0;channel<8;channel++) {
					float sendt_bps= (float) commMonitor.getBitOutWindow(id, channel);
					float lost_bps = (float) commMonitor.getBitLostWindow(id, channel);
					if(sendt_bps==Integer.MIN_VALUE) sendt_bps=0;
					if(lost_bps==Integer.MIN_VALUE) lost_bps=0;
					
					float lost_percent;
					if(sendt_bps!=0) {
						lost_percent = lost_bps/sendt_bps*100.0f;
					}
					else {
						lost_percent=0;
					}
					
					if(lost_percent>max_lost_percent) {
						max_lost_percent = lost_percent;
					}
					if(simulation.getModules().get(id).getConnectors().get(channel).hasProximateConnector()) {
						channelCount++;
						mean_lost_percent +=lost_percent;
					}
					//System.out.println("Module "+id+" sends "+bitpersec+" bits/sec on channel "+channel);
				}
			}
			addMaxToXYscatterPlot(simulation.getTime(), max_lost_percent);
			addMeanToXYscatterPlot(simulation.getTime(), mean_lost_percent/channelCount);
			nextT += deltaT;
		}
	}
	
	public void addMaxToXYscatterPlot(float x, float y) {
		max.add(x, y);
	}
	public void addMeanToXYscatterPlot(float x, float y) {
		mean.add(x, y);
	}
	
	public void initXYscatterPlot() {
		 // create a dataset...
        max = new XYSeries("Max");
        mean = new XYSeries("Mean");
        
        XYSeriesCollection dataset = new XYSeriesCollection(); 
        dataset.addSeries(max);
        dataset.addSeries(mean);
        
        JFreeChart chart = ChartFactory.createXYLineChart(
        	    "Communication Lost",  // chart title
                "Time (sec)",
                "Communication Lost (percent)",
                dataset,         // data
                PlotOrientation.VERTICAL,
                true,            // include legend
                false,            // tooltips
                false            // urls
            );
        // 	create and display a frame...
        //ChartPanel panel = new ChartPanel(chart);
		ChartFrame frame = new ChartFrame("Communication Lost Chart", chart);
		frame.pack();
		//System.out.println(frame.getSize());
		frame.setSize(2*690/3, 2*450/3);
		frame.setVisible(true);
	}
}

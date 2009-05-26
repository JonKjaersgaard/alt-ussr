package ussr.builder.labels;

import ussr.builder.BuilderHelper;
import ussr.model.Connector;
import ussr.model.Module;
import ussr.model.Sensor;

public class SensorLabel extends Label {
	
	private Sensor sensor;
	
	public SensorLabel(Sensor sensor){
	this.sensor = sensor;	
	}

	
	
	
	public String getLabels(){
		String labels = sensor.getProperty(BuilderHelper.getLabelsKey());
		if (labels ==null){
			return "none";
		}
		return labels;
	}




	@Override
	public boolean has(String label) {		
		if (getLabels().contains(label)){
			return true;
		}
		return false;		
	}	
	
}

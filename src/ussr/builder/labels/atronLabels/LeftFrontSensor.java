package ussr.builder.labels.atronLabels;

import ussr.builder.labels.Labels;
import ussr.builder.labels.ModuleLabels;
import ussr.builder.labels.SensorLabels;
import ussr.builder.labels.abstractLabels.ProximitySensor;
import ussr.model.Module;
import ussr.model.Sensor;

public class LeftFrontSensor implements ProximitySensor {

	private Module module;
	
	public LeftFrontSensor(Module module){
		this.module = module;
	}
	
	@Override
	public float getValue() {
		Labels moduleLabels = new ModuleLabels(module);
		if(moduleLabels.has("wheel")&& moduleLabels.has("left")){
			for(Sensor sensor: module.getSensors()) {
				/*Get labels of the sensor*/
				Labels sesorLabels = new SensorLabels(sensor);
				if(sesorLabels.has("front")) {
					if (sensor.readValue() ==0){						
					}else{
					return sensor.readValue();
					}
				}
			}

		}
		return 1000;//means there is no front sensor
	}

}

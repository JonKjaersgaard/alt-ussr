package ussr.builder.labels.atronLabels;

import ussr.builder.labels.Labels;
import ussr.builder.labels.ModuleLabels;
import ussr.builder.labels.SensorLabels;
import ussr.builder.labels.abstractLabels.ProximitySensor;
import ussr.model.Module;
import ussr.model.Sensor;

public abstract class ProximitySensors implements ProximitySensor {

 private Module module;
 
	public ProximitySensors(Module module){
		this.module = module;
	}


	public abstract float getValue();

}

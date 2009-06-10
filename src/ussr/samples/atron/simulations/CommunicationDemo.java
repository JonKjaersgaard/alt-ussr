/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.simulations;

import java.util.HashMap;
import java.util.Map;

import ussr.comm.CommunicationMonitor;
import ussr.comm.GenericReceiver;
import ussr.comm.GenericTransmitter;
import ussr.comm.Packet;
import ussr.description.Robot;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.BoxDescription;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.model.Module;
import ussr.physics.PhysicsFactory;
import ussr.samples.ObstacleGenerator;
import ussr.samples.atron.ATRON;

/**
 * Extension of the {@link EightToCarSimulationJ} example to use snake and car modes of locomotion.
 * 
 * @author ups
 */
public class CommunicationDemo extends EightToCarSimulationJ {

    public static class TestMonitor implements CommunicationMonitor {
        Map<Packet,Module> registry = new HashMap<Packet,Module>();

        public void packetReceived(Module module, GenericReceiver receiver, Packet data) {
            Module from = registry.get(data);
            if(from==null)
                System.out.println("Unknown source for packet "+data);
            else {
                String fromName = formatName(from,from.getProperty("name"));
                String toName = formatName(module,module.getProperty("name"));
                System.out.println("["+fromName+"->"+toName+"] "+data);
                registry.remove(data);
            }
        }

        private String formatName(Module module, String name) {
            return name==null ? ("<Unnamed module "+module+">") : name;
        }
        
        public void packetSent(Module module, GenericTransmitter transmitter, Packet packet) {
            registry.put(packet,module);
        }

		public void packetLost(Module module, GenericTransmitter transmitter, Packet packet) {
			// TODO Auto-generated method stub
			
		}

    }

    public static void main(String argv[]) {
        PhysicsFactory.getOptions().addCommunicationMonitor(new TestMonitor());
        new CommunicationDemo().main();
    }

}

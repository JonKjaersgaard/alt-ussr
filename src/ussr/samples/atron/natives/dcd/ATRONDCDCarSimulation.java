/**
 * 
 */
package ussr.samples.atron.natives.dcd;

import java.util.HashSet;
import java.util.Set;

import ussr.description.Robot;
import ussr.model.Controller;
import ussr.physics.PhysicsParameters;
import ussr.samples.ObstacleGenerator;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.natives.ATRONNativeController;
import ussr.samples.atron.simulations.ATRONCarSimulation;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class ATRONDCDCarSimulation extends ATRONCarSimulation {
    public static void main(String argv[]) {
        new ATRONDCDCarSimulation().main();
    }
    
    @Override public void main() {
        PhysicsParameters.get().setMaintainRotationalJointPositions(true);
        PhysicsParameters.get().setRealisticCollision(true);
        PhysicsParameters.get().setPhysicsSimulationStepSize(0.01f); // before: 0.0005f
        PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
        this.obstacleType = ObstacleGenerator.ObstacleType.LINE;
        super.main();
    }
    
    @Override
    protected Robot getRobot() {
        ATRON robot = new ATRON() {
            public Controller createController() {
                return new DCDController();
            }
        };
        robot.setGentle();
        return robot;
    }

    private static class DCDController extends ATRONNativeController {
        private class DCDLinkCacheTempHack extends Link {

            private Set<Entry> cache = new HashSet<Entry>();
            
            int dcd_perfect_cache(int packet_id, int packet_x, int packet_y, int packet_z, int packet_r) {
                Entry entry = new Entry(packet_id, packet_x, packet_y, packet_z, packet_r);
                if(cache.contains(entry)) return 0;
                cache.add(entry);
                return 1;
            }
            
        }
        
        DCDController() {
            super("dcdController");
        }
        @Override
        protected void setInternalControllerHook() {
            this.setInternalController(new DCDLinkCacheTempHack());
        }
    }

    private static class Entry {
        int packet_id, packet_x, packet_y, packet_z, packet_r;

        public Entry(int packet_id, int packet_x, int packet_y, int packet_z, int packet_r) {
            this.packet_id = packet_id;
            this.packet_x = packet_x;
            this.packet_y = packet_y;
            this.packet_z = packet_z;
            this.packet_r = packet_r;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + packet_id;
            result = prime * result + packet_r;
            result = prime * result + packet_x;
            result = prime * result + packet_y;
            result = prime * result + packet_z;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final Entry other = (Entry) obj;
            if (packet_id != other.packet_id)
                return false;
            if (packet_r != other.packet_r)
                return false;
            if (packet_x != other.packet_x)
                return false;
            if (packet_y != other.packet_y)
                return false;
            if (packet_z != other.packet_z)
                return false;
            return true;
        }
        
        
    }   
}

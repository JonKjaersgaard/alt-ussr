/**
 * 
 */
package ussr.samples.atron.natives;

import java.util.HashSet;
import java.util.Set;

import ussr.model.NativeController;
import ussr.model.NativeControllerProvider;
import ussr.samples.atron.ATRONController;


/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class ATRONNativeController extends NativeController {
	
	private class Link extends ATRONController implements NativeControllerProvider {
		@Override
		public void activate() {
			ATRONNativeController.this.activate();
		}
		public int getRole() { return ATRONNativeController.this.getRole(); }

		public synchronized void handleMessage(byte[] message, int messageSize, int channel) {
	        if(eventLock!=null) {
	            synchronized(eventLock) {
	                eventLock.notify();
	                eventLock = null;
	            }
	        }
	        nativeHandleMessage(getInitializationContext(), message, messageSize, channel);
	    }

		private Set<Entry> cache = new HashSet<Entry>();
		
		int dcd_perfect_cache(int packet_id, int packet_x, int packet_y, int packet_z, int packet_r) {
		    Entry entry = new Entry(packet_id, packet_x, packet_y, packet_z, packet_r);
		    if(cache.contains(entry)) return 0;
		    cache.add(entry);
		    return 1;
		}
		
		void delay_internal(int wait) {
		    super.delay(wait);
		}
	}

	public ATRONNativeController(String nativeLibraryName) {
		super(nativeLibraryName);
		this.setInternalController(new Link());
	}

    private native void nativeHandleMessage(int context, byte[] message, int messageSize, int channel);

    static class Entry {
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

package sunTron.samples.simpleVechicle;
import sunTron.api.SunTronAPIUSSR;
public class SunTronSimpleVehicleController extends SunTronAPIUSSR {
    public void activate() {
        yield();
        byte dir = 1;
        byte[]test = {12};
    	for (int i = 0;i<8;i++){
        sendMessage(test, (byte) 1 ,(byte) i);
    	}
        while(true) {
            String name = getName();
            if(name=="RearRightWheel") rotateContinuous(dir);
            if(name=="RearLeftWheel") rotateContinuous(-dir);
            yield();
        }
    }
    @Override
    public void handleMessage(byte[] message, int messageSize, int channel) {
    	// TODO Auto-generated method stub
    	System.out.println(getName() + " Message: " + message[0]);
    }
}

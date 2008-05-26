package sunTron.samples.simpleVechicle;
import sunTron.API.SunTronAPI;
public class SunTronSimpleVehicleController extends SunTronAPI {
    public void activate() {
        yield();
        byte dir = 1;
        while(true) {
            String name = getName();
            if(name=="RearRightWheel") rotateContinuous(dir);
            if(name=="RearLeftWheel") rotateContinuous(-dir);
            yield();
        }
    }
}

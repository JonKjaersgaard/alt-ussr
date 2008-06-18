package sunTron.samples.simpleVechicle;
import sunTron.api.SunTronAPIUSSR;
public class SunTronSimpleVehicleController extends SunTronAPIUSSR {
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

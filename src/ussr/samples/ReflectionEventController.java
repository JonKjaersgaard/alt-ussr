package ussr.samples;

import ussr.model.Module;
import ussr.network.EventConnection;
import ussr.network.ReflectionConnection;

public interface ReflectionEventController {

    Module getModule();

    void setEventConnection(EventConnection eventConnection);

    void setRcConnection(ReflectionConnection rcConnection);

    void setActive();

}

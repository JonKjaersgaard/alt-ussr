package morphingProductionLine.simpleExample;

import ussr.description.setup.BoxDescription;
import ussr.description.setup.WorldDescription;
import morphingProductionLine.abstractConveyor.AbstractEventGenerator;

public class SimpleEventGenerator extends AbstractEventGenerator {

    @Override
    public void prepareWorld(WorldDescription world) {
        BoxDescription[] boxes = new BoxDescription[] { 
                new BoxDescription(Configuration.boxInitialPosition(), Configuration.BOX_SIZE, Configuration.boxInitialRotation(), Configuration.BOX_MASS)
        };
        world.setBigObstacles(boxes);
    }

}

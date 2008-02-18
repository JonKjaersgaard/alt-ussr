package ussr.samples.atron;

import ussr.model.Controller;
import ussr.robotbuildingblocks.BoxDescription;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;
import ussr.samples.atron.EightToCarSimulationJ.EightController;

public class SnakeCarDemo extends EightToCarSimulationJ {

    public static void main(String argv[]) {
        new SnakeCarDemo().main();
    }

    private static final int N_CIRCLE_OBSTACLES = 64;
    private static final int N_CIRCLE_LAYERS = 1;
    private static final float CIRCLE_OBSTACLE_RADIUS = 0.5f;
    private static final float OBSTACLE_Y = -0.4f;
    private static final float OBSTACLE_Y_INC = 0.15f;
    private static final float OBSTACLE_SIZE = 0.02f;
    protected void changeWorldHook(WorldDescription world) {
        world.setPlaneTexture(WorldDescription.GRID_TEXTURE);
        world.setHasBackgroundScenery(false);
        // Override settings from superclass
        System.out.println("Generating circle obstacles");
        BoxDescription[] obstacles = new BoxDescription[N_CIRCLE_OBSTACLES*N_CIRCLE_LAYERS];
        int index = 0;
        float y = OBSTACLE_Y;
        VectorDescription size = new VectorDescription(OBSTACLE_SIZE,OBSTACLE_SIZE,OBSTACLE_SIZE);
        for(int layers=0; layers<N_CIRCLE_LAYERS; layers++) {
            for(int i=0; i<N_CIRCLE_OBSTACLES; i++) {
                VectorDescription position = new VectorDescription(
                        ((float)(CIRCLE_OBSTACLE_RADIUS*Math.cos(((double)i)/N_CIRCLE_OBSTACLES*Math.PI*2+y))),
                        y,
                        ((float)(CIRCLE_OBSTACLE_RADIUS*Math.sin(((double)i)/N_CIRCLE_OBSTACLES*Math.PI*2+y)))
                );
                obstacles[index++] = new BoxDescription(position,size,new RotationDescription(),100f); 
            }
            y+=OBSTACLE_Y_INC;
        }
        world.setBigObstacles(obstacles);
    }
    @Override
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new CarStuffController();
            }
        };
    }

    public static final byte NO_CONNECTOR = 8;
    public static final byte ALL_MODULES = 8;

    protected class CarStuffController extends EightController {

        volatile private int state = 0;
        int snake_counter = 4;

        @Override
        public void activate_before_eight2car() {
            int id = this.getMyID();
            if(id==3) this.state = 1;
            while(true) {
                super.ussrYield();
                if(state!=0) System.out.println("@ Pre-controller "+id+" in state "+state);
                switch(state) {
                case 1: /* module 3 */
                    this.disconnect(0);
                    this.disconnect(6);
                    nextState(2,0);
                    break;
                case 2: /* module 0 */
                    this.rotate(1);
                    this.rotate(1);
                    nextState(3,6);
                    break; 
                case 3: /* module 6 */
                    this.rotate(1);
                    this.rotate(1);
                    nextState(9,2);
                    //nextState(4,3);
                    break;
                case 4: /* module 3 */ /* NOT USED */
                    for(int i=0; i<10; i++) {
                        this.rotateDegrees(30);
                        this.rotateDegrees(-30);
                        delay(1000);
                    }
                    nextState(5,6);
                    break;
                case 5: /* module 6 */
                    this.rotate(-1);
                    this.rotate(-1);
                    nextState(6,0);
                    break;
                case 6: /* module 0 */ 
                    this.rotate(-1);
                    this.rotate(-1);
                    nextState(7,3);
                    break; 
                case 7: /* module 3 */
                    this.connect(0);
                    this.connect(6);
                    nextState(8,ALL_MODULES);
                    break;
                case 8: /* all modules */
                    super.delay(5000);
                    return;
                case 9: /* module 2 */
                    //this.rotate(1);
                    this.rotateContinuous(1);
                    this.delay(6000);
                    //this.centerStop();
                    if(this.snake_counter-->0) {
                        nextState(10,4);
                    } else {
                        this.centerStop();
                        super.home();
                        nextState(11,4);
                    }
                    break;
                case 10: /* module 4 */
                    //this.rotate(-1);
                    this.rotateContinuous(-1);
                    this.delay(6000);
                    //this.centerStop();
                    nextState(9,2);
                    break;
                case 11: /* module 4 */
                    this.centerStop();
                    super.home();
                    nextState(5,6);
                    break;
                default:
                    state = 0;
                }
            }
        }

        @Override
        public void activate_after_eight2car() {
            // First execute "getup" operation
            int id = this.getMyID();
            if(id==0) reportTilt();
            if(id==0) 
                this.state = 1;
            else
                this.state = 0;
            control: while(true) {
                delay(10000);
                super.ussrYield();
                if(state!=0) System.out.println("@ Post-controller "+id+" in state "+state);
                switch(state) {
                case 1: /* module 0 */
                    this.rotate(1);
                    this.rotate(1);
                    delay(20000);
                    reportTilt();
                    nextState(2,6);
                    break;
                case 2: /* module 6 */
                    this.rotateDegrees(45);
                    nextState(3,0);
                    break;
                case 3: /* module 0 */
                    for(int i=0; i<4; i++) {
                        this.rotateDegrees(20);
                        delay(1000);
                    }
                    nextState(4,1);
                    break;
                case 4: /* module 1 */
                    this.rotateDegrees(20);
                    nextState(5,0);
                    break;
                case 5: /* module 0 */
                    for(int i=0; i<5; i++) {
                        this.rotateDegrees(20);
                        delay(1000);
                    }
                    nextState(6,6);
                    break;
                case 6: /* module 6 */
                    this.rotateDegrees(-45);
                    nextState(10,ALL_MODULES);
                    break;
                case 10:
                    delay(5000);
                    break control;
                default:
                    state = 0;
                }
            }
            // Then start driving 
            switch(id) {
            case 5: this.rotateContinuous(1f); break;
            case 2: this.rotateContinuous(-1f); break;
            case 3: this.rotateContinuous(1f); break;
            case 4: this.rotateContinuous(-1f); break;
            case 0:
                int i;
                while(true) {
                    this.delay(1000);
                    reportTilt();
                }
            }
            delay(500000);
            this.centerStop();
            while(true) this.ussrYield();
        }

        private void reportTilt() {
            System.out.println("Tilt sensor in module 0: ["+this.getTiltX()+","+this.getTiltY()+","+this.getTiltZ()+"]");
        }
        
        private void nextState(int nextState, int module_id) {
            this.state = 0;
            this.sendNextState(nextState, NO_CONNECTOR, (byte)module_id, (byte)0);
            if(module_id==ALL_MODULES) this.state = nextState;
        }

        private void sendNextState(int nextState, byte omit, byte destination, byte seen) {
            seen |= 1<<this.getMyID();
            byte[] message = new byte[] { 0, (byte)(nextState), 42, destination, seen };
            for(byte c=0; c<8; c++)
                if(c!=omit && super.isConnected(c)) super.sendMessage(message, (byte)4, c);
        }

        @Override
        protected boolean canHandleMessage(byte[] incoming, int messageSize, int channel) {
            if(incoming[2]==42) {
                byte destination = incoming[3];
                byte seen = incoming[4];
                byte flag = (byte) (seen>>this.getMyID());
                byte nextState = incoming[1];
                if((flag&1)==1) return true;
                if(destination==this.getMyID() || destination==ALL_MODULES) state = nextState;
                if(destination!=this.getMyID() || destination==ALL_MODULES)
                    sendNextState(nextState,(byte)channel,destination,seen);
                return true;
            }
            return false;
        }
    }
}

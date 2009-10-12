package ussr.aGui;

import org.lwjgl.input.Mouse;

import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.input.InputHandler;
import com.jme.input.MouseLookHandler;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.CameraNode;
import com.jme.scene.shape.Box;
import com.jme.scene.state.TextureState;
import com.jme.system.canvas.SimpleCanvasImpl;
import com.jme.util.TextureManager;

class MyImplementor extends SimpleCanvasImpl {

    private Quaternion rotQuat;
    private float angle = 0;
    private Vector3f axis;
    private Box box;
    long startTime = 0;
    long fps = 0;
    private InputHandler input;
    CameraNode cam = new CameraNode();
    MouseLookHandler mouse;
    public MyImplementor(int width, int height) {
        super(width, height);
    }

    public void simpleSetup() {

        // Normal Scene setup stuff...
        rotQuat = new Quaternion();
        axis = new Vector3f(1, 1, 0.5f);
        axis.normalizeLocal();

        Vector3f max = new Vector3f(5, 5, 5);
        Vector3f min = new Vector3f(-5, -5, -5);

        box = new Box("Box", min, max);
        box.setModelBound(new BoundingBox());
        box.updateModelBound();
        box.setLocalTranslation(new Vector3f(0, 0, -10));
        box.setRenderQueueMode(Renderer.QUEUE_SKIP);
        rootNode.attachChild(box);

        box.setRandomColors();

        TextureState ts = renderer.createTextureState();
        ts.setEnabled(true);
        ts.setTexture(TextureManager.loadTexture(MainFrame.class
                .getClassLoader().getResource(
                        "jmetest/data/images/Monkey.jpg"),
                Texture.MinificationFilter.BilinearNearestMipMap,
                Texture.MagnificationFilter.Bilinear));

        rootNode.setRenderState(ts);
        startTime = System.currentTimeMillis() + 5000;

        input = new InputHandler();
        input.addAction(new InputAction() {
            public void performAction(InputActionEvent evt) {
               // logger.info(evt.getTriggerName());
                //frame.PopupMenuExample(Mouse.getX(), Mouse.getY());
            }
        }, InputHandler.DEVICE_MOUSE, InputHandler.BUTTON_ALL,
                InputHandler.AXIS_NONE, false);

        input.addAction(new InputAction() {
            public void performAction(InputActionEvent evt) {
                //logger.info(evt.getTriggerName());
            }
        }, InputHandler.DEVICE_KEYBOARD, InputHandler.BUTTON_ALL,
                InputHandler.AXIS_NONE, false);
    }

    public void simpleUpdate() {
        input.update(tpf);

        // Code for rotating the box... no surprises here.
        if (tpf < 1) {
            angle = angle + (tpf * 25);
            if (angle > 360) {
                angle = 0;
            }
        }
        rotQuat.fromAngleNormalAxis(angle * FastMath.DEG_TO_RAD, axis);
        box.setLocalRotation(rotQuat);

        if (startTime > System.currentTimeMillis()) {
            fps++;
        } else {
            long timeUsed = 5000 + (startTime - System.currentTimeMillis());
            startTime = System.currentTimeMillis() + 5000;
          /*  logger.info(fps + " frames in " + (timeUsed / 1000f)
                    + " seconds = " + (fps / (timeUsed / 1000f))
                    + " FPS (average)");
            fps = 0;*/
        }
    }
    
   
    
}

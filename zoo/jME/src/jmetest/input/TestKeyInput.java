/*
 * Copyright (c) 2003-2006 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software 
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package jmetest.input;

import com.jme.app.BaseGame;
import com.jme.image.Texture;
import com.jme.input.KeyInput;
import com.jme.input.KeyInputListener;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.Text;
import com.jme.scene.state.AlphaState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.system.JmeException;
import com.jme.util.TextureManager;

/**
 * <code>TestKeyInput</code>
 * @author Mark Powell
 * @version $Id: TestKeyInput.java,v 1.12 2006/01/13 19:37:27 renanse Exp $
 */
public class TestKeyInput extends BaseGame {
    private Text text;
    private Camera cam;
    private Node scene;
    private KeyInput key;

    public static void main(String[] args) {
        TestKeyInput app = new TestKeyInput();
        app.setDialogBehaviour(ALWAYS_SHOW_PROPS_DIALOG);
        app.start();
    }

    protected void update(float interpolation) {
        if(key.isKeyDown(KeyInput.KEY_0)) {
            text.print("You pressed 0.");
        }

        if(key.isKeyDown(KeyInput.KEY_1)) {
            text.print("You pressed 1.");
        }
    }

    /**
     * draws the scene graph
     * @see com.jme.app.SimpleGame#render(float)
     */
    protected void render(float interpolation) {
        display.getRenderer().clearBuffers();

        display.getRenderer().draw(scene);

    }

    /**
     * initializes the display and camera.
     * @see com.jme.app.SimpleGame#initSystem()
     */
    protected void initSystem() {
        try {
            display = DisplaySystem.getDisplaySystem(properties.getRenderer());
            display.createWindow(
                    properties.getWidth(),
                    properties.getHeight(),
                    properties.getDepth(),
                    properties.getFreq(),
                    properties.getFullscreen());

            cam = display.getRenderer().createCamera(properties.getWidth(), properties.getHeight());
        } catch (JmeException e) {
            e.printStackTrace();
            System.exit(1);
        }
        ColorRGBA blueColor = new ColorRGBA();
        blueColor.r = 0;
        blueColor.g = 0;
        display.getRenderer().setBackgroundColor(blueColor);
        cam.setFrustum(1.0f, 1000.0f, -0.55f, 0.55f, 0.4125f, -0.4125f);
        Vector3f loc = new Vector3f(4.0f, 0.0f, 0.0f);
        Vector3f left = new Vector3f(0.0f, -1.0f, 0.0f);
        Vector3f up = new Vector3f(0.0f, 0.0f, 1.0f);
        Vector3f dir = new Vector3f(-1.0f, 0f, 0.0f);
        cam.setFrame(loc, left, up, dir);

        key = KeyInput.get();
        display.getRenderer().setCamera(cam);
    }

    /**
     * initializes the scene
     * @see com.jme.app.SimpleGame#initGame()
     */
    protected void initGame() {
        text = new Text("Text Label", "Press 0 and/or 1");
        text.setLocalTranslation(new Vector3f(1, 60, 0));
        TextureState ts = display.getRenderer().createTextureState();
        ts.setEnabled(true);
        ts.setTexture(
            TextureManager.loadTexture(
                   TestKeyInput.class.getClassLoader().getResource("jmetest/data/font/font.png"),
                Texture.MM_LINEAR,
                Texture.FM_LINEAR));
        text.setRenderState(ts);
        AlphaState as1 = display.getRenderer().createAlphaState();
        as1.setBlendEnabled(true);
        as1.setSrcFunction(AlphaState.SB_SRC_ALPHA);
        as1.setDstFunction(AlphaState.DB_ONE);
        as1.setTestEnabled(true);
        as1.setTestFunction(AlphaState.TF_GREATER);
        text.setRenderState(as1);
        scene = new Node("Scene graph node");
        scene.attachChild(text);
        cam.update();

        scene.updateGeometricState(0.0f, true);
        scene.updateRenderState();

        KeyInput.get().addListener( new KeyInputListener() {
            public void onKey( char character, int keyCode, boolean pressed ) {
                text.print( "key: '" + (character != 0 ? ""+character : "\\0") + "' (code "+keyCode+") " + (pressed?"pressed":"released") );
            }
        } );
    }

    /**
     * not used.
     * @see com.jme.app.SimpleGame#reinit()
     */
    protected void reinit() {

    }

    /**
     * not used.
     * @see com.jme.app.SimpleGame#cleanup()
     */
    protected void cleanup() {

    }
}

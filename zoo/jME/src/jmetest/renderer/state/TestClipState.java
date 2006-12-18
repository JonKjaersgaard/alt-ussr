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

package jmetest.renderer.state;

import com.jme.app.SimpleGame;
import com.jme.bounding.BoundingSphere;
import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Box;
import com.jme.scene.state.ClipState;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;

/**
 * <code>TestClipState</code>
 * @author Mark Powell
 * @version $Id: TestClipState.java,v 1.3 2006/01/13 19:37:15 renanse Exp $
 */
public class TestClipState extends SimpleGame {
    private TriMesh t;

    /**
     * Entry point for the test,
     * @param args
     */
    public static void main(String[] args) {
        TestClipState app = new TestClipState();
        app.setDialogBehaviour(ALWAYS_SHOW_PROPS_DIALOG);
        app.start();
    }

    /**
     * builds the trimesh.
     * @see com.jme.app.SimpleGame#initGame()
     */
    protected void simpleInitGame() {
        display.setTitle("Clip State Test");
        cam.setLocation(new Vector3f(-10,0,40));
        cam.update();

        Vector3f max = new Vector3f(100,100,100);
        Vector3f min = new Vector3f(0,0,0);

        t = new Box("Box", min,max);
        t.setModelBound(new BoundingSphere());
        t.updateModelBound();

        t.setLocalTranslation(new Vector3f(-50,0,-75));

        rootNode.attachChild(t);

        TextureState ts = display.getRenderer().createTextureState();
                ts.setEnabled(true);
                ts.setTexture(
                    TextureManager.loadTexture(
                        TestClipState.class.getClassLoader().getResource("jmetest/data/images/Monkey.jpg"),
                        Texture.MM_LINEAR_LINEAR,
                        Texture.FM_LINEAR));

        rootNode.setRenderState(ts);
        
        ClipState clipState = display.getRenderer().createClipState();
        clipState.setEnableClipPlane(ClipState.CLIP_PLANE0, true);
        clipState.setClipPlaneEquation(0, 0.5f, 0.5f, 0,0);
        rootNode.setRenderState(clipState);
    }
}

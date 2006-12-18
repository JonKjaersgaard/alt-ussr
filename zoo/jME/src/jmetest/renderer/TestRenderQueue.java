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

package jmetest.renderer;

import com.jme.app.SimpleGame;
import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.light.DirectionalLight;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.SceneElement;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Quad;
import com.jme.scene.shape.Torus;
import com.jme.scene.state.AlphaState;
import com.jme.scene.state.LightState;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.TextureState;
import com.jme.scene.state.ZBufferState;
import com.jme.util.TextureManager;

/**
 * <code>TestRenderQueue</code>
 * @author Joshua Slack
 * @version $Id: TestRenderQueue.java,v 1.21 2006/11/16 19:59:26 nca Exp $
 */
public class TestRenderQueue extends SimpleGame {
  private boolean useQueue = false;
  protected Node opaques, transps, orthos;

  /**
   * Entry point for the test,
   * @param args
   */
  public static void main(String[] args) {
    TestRenderQueue app = new TestRenderQueue();
    app.setDialogBehaviour(ALWAYS_SHOW_PROPS_DIALOG);
    app.start();
  }

  protected void simpleUpdate() {
      boolean updateTitle = false;

      if (KeyBindingManager.getKeyBindingManager().isValidCommand("queue", false)) {
          if (useQueue) {
              transps.setRenderQueueMode(Renderer.QUEUE_SKIP);
              opaques.setRenderQueueMode(Renderer.QUEUE_SKIP);
              orthos.setRenderQueueMode(Renderer.QUEUE_SKIP);
            } else {
              transps.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
              opaques.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
              orthos.setRenderQueueMode(Renderer.QUEUE_ORTHO);
            }
          useQueue = !useQueue;
          updateTitle = true;
      }

      if (KeyBindingManager.getKeyBindingManager().isValidCommand("trans", false)) {
          display.getRenderer().getQueue().setTwoPassTransparency(!display.getRenderer().getQueue().isTwoPassTransparency());
          updateTitle = true;
      }

      if (updateTitle)
            display.setTitle("Test Render Queue - " + useQueue
                    + " - hit 'Q' to toggle Queue mode - '2' Two Pass: - "
                    + display.getRenderer().getQueue().isTwoPassTransparency());
  }

  protected void simpleRender() {
    if (!useQueue) {
      display.getRenderer().setOrtho();
      display.getRenderer().draw(orthos);
      display.getRenderer().unsetOrtho();
    } else {
      display.getRenderer().draw(orthos);
    }

    display.getRenderer().draw(transps);
    display.getRenderer().draw(opaques);
  }

  protected void simpleInitGame() {
    display.setTitle("Test Render Queue - false - hit 'Q' to toggle Queue mode - '2' Two Pass: - true");
    KeyBindingManager.getKeyBindingManager().set("queue", KeyInput.KEY_Q);
    KeyBindingManager.getKeyBindingManager().set("trans", KeyInput.KEY_2);
    cam.setLocation(new Vector3f(10, 0, 50));
    cam.update();

    Vector3f max = new Vector3f(5, 5, 5);
    Vector3f min = new Vector3f( -5, -5, -5);

    opaques = new Node("Opaques");
    transps = new Node("Transps");
    orthos = new Node("Orthos");
    transps.setRenderQueueMode(Renderer.QUEUE_SKIP);
    opaques.setRenderQueueMode(Renderer.QUEUE_SKIP);
    orthos.setRenderQueueMode(Renderer.QUEUE_SKIP);
    rootNode.attachChild(orthos);
    rootNode.attachChild(transps);
    rootNode.attachChild(opaques);

    Box b1 = new Box("Box", min, max);
    b1.setModelBound(new BoundingBox());
    b1.updateModelBound();
    b1.setLocalTranslation(new Vector3f(0, 0, -15));
    opaques.attachChild(b1);

    Box b2 = new Box("Box", min, max);
    b2.setModelBound(new BoundingBox());
    b2.updateModelBound();
    b2.setLocalTranslation(new Vector3f(0, 0, -30));
    opaques.attachChild(b2);

    Box b3 = new Box("Box", min, max);
    b3.setModelBound(new BoundingBox());
    b3.updateModelBound();
    b3.setLocalTranslation(new Vector3f(0, -15, -15));
    opaques.attachChild(b3);

    TextureState ts = display.getRenderer().createTextureState();
    ts.setEnabled(true);
    ts.setTexture(
        TextureManager.loadTexture(
        TestRenderQueue.class.getClassLoader().getResource(
        "jmetest/data/images/Monkey.tga"),
        Texture.MM_LINEAR_LINEAR,
        Texture.FM_LINEAR));
    opaques.setRenderState(ts);

    LightState ls = display.getRenderer().createLightState();
    ls.setEnabled(true);
    DirectionalLight dLight = new DirectionalLight();
    dLight.setEnabled(true);
    dLight.setDiffuse(new ColorRGBA(1,1,1,1));
    dLight.setDirection(new Vector3f(-1,-1,-1));
    ls.attach(dLight);
    DirectionalLight dLight2 = new DirectionalLight();
    dLight2.setEnabled(true);
    dLight2.setDiffuse(new ColorRGBA(1,1,1,1));
    dLight2.setDirection(new Vector3f(1,1,1));
    ls.attach(dLight2);
    ls.setTwoSidedLighting(false);
    transps.setRenderState(ls);
    transps.setLightCombineMode(LightState.REPLACE);

    Box tb1 = new Box("TBox Blue", min, max);
    tb1.setModelBound(new BoundingBox());
    tb1.updateModelBound();
    tb1.setLocalTranslation(new Vector3f(0, 15, 15));
    transps.attachChild(tb1);
    MaterialState ms1 = display.getRenderer().createMaterialState();
    ms1.setEnabled(true);
    ms1.setDiffuse(new ColorRGBA(0,0,1,.75f));
    ms1.setShininess(128);
    tb1.setRenderState(ms1);

    Torus tb2 = new Torus("TBox Green", 20, 20, 3, 6);
    tb2.setModelBound(new BoundingBox());
    tb2.updateModelBound();
    tb2.setLocalTranslation(new Vector3f(0, 0, 30));
    transps.attachChild(tb2);
    MaterialState ms2 = display.getRenderer().createMaterialState();
    ms2.setEnabled(true);
    ms2.setDiffuse(new ColorRGBA(0,1,0,.75f));
    ms2.setShininess(128);
    tb2.setRenderState(ms2);

    Box tb3 = new Box("TBox Red", min, max);
    tb3.setModelBound(new BoundingBox());
    tb3.updateModelBound();
    tb3.setLocalTranslation(new Vector3f(0, 0, 15));
    transps.attachChild(tb3);
    MaterialState ms3 = display.getRenderer().createMaterialState();
    ms3.setEnabled(true);
    ms3.setDiffuse(new ColorRGBA(1,0,0,.5f));
    ms3.setShininess(128);
    tb3.setRenderState(ms3);
    
    Box tb4 = new Box("TBox Blue2", new Vector3f(-4.5f,-4.5f,-4.5f), new Vector3f(4.5f,4.5f,4.5f));
    tb4.setModelBound(new BoundingBox());
    tb4.updateModelBound();
    tb4.setLocalTranslation(new Vector3f(0, 4, 17));
    transps.attachChild(tb4);
    MaterialState ms4 = display.getRenderer().createMaterialState();
    ms4.setEnabled(true);
    ms4.setDiffuse(new ColorRGBA(0,0,1,.75f));
    ms4.setShininess(128);
    tb4.setRenderState(ms4);

    AlphaState as = display.getRenderer().createAlphaState();
    as.setEnabled(true);
    as.setBlendEnabled(true);
    as.setSrcFunction(AlphaState.SB_SRC_ALPHA);
    as.setDstFunction(AlphaState.DB_ONE_MINUS_SRC_ALPHA);
    transps.setRenderState(as);

    Vector2f center = new Vector2f(display.getWidth()>>1, display.getWidth()>>1);

    Quad q1 = new Quad("Ortho Q1", 40, 40);
    q1.setLocalTranslation(new Vector3f(100+center.x,100+center.y,0));
    q1.setZOrder(1);
    q1.setDefaultColor(ColorRGBA.white);
    q1.setLightCombineMode(LightState.OFF);
    orthos.attachChild(q1);

    Quad q2 = new Quad("Ortho Q2", 100, 100);
    q2.setLocalTranslation(new Vector3f(60+center.x,60+center.y,0));
    q2.setZOrder(5);
    q2.setDefaultColor(ColorRGBA.red);
    q2.setLightCombineMode(LightState.OFF);
    orthos.attachChild(q2);

    Quad q3 = new Quad("Ortho Q3", 120, 60);
    q3.setLocalTranslation(new Vector3f(-20+center.x,-150+center.y,0));
    q3.setZOrder(2);
    q3.setDefaultColor(ColorRGBA.blue);
    q3.setLightCombineMode(LightState.OFF);
    orthos.attachChild(q3);

    ZBufferState zstate = display.getRenderer().createZBufferState();
    zstate.setWritable(false);
    zstate.setEnabled(false);
    orthos.setRenderState(zstate);

    orthos.setRenderState(Renderer.defaultStateList[RenderState.RS_LIGHT]);
    
    rootNode.setCullMode(SceneElement.CULL_ALWAYS);
    opaques.setCullMode(SceneElement.CULL_DYNAMIC);
    transps.setCullMode(SceneElement.CULL_DYNAMIC);
    orthos.setCullMode(SceneElement.CULL_NEVER);
  }
}

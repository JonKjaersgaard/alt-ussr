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
import com.jme.math.Vector3f;
import com.jme.scene.shape.Box;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;

/**
 * <code>TestBox</code>
 * @author Mark Powell
 * @version $Id: TestBox.java,v 1.6 2006/11/16 19:59:27 nca Exp $
 */
public class TestBox extends SimpleGame {

  /**
   * Entry point for the test,
   * @param args
   */
  public static void main(String[] args) {
    TestBox app = new TestBox();
    app.setDialogBehaviour(NEVER_SHOW_PROPS_DIALOG);
    app.start();

  }
  
  protected void simpleInitGame() {
    display.setTitle("Repeating Texture");
    lightState.setEnabled(false);
    
    Box floor = new Box("Floor", new Vector3f(), 100, 1, 100); 
    floor.setModelBound(new BoundingBox()); 
    floor.updateModelBound(); 
    floor.getLocalTranslation().y = -20; 
    TextureState ts = display.getRenderer().createTextureState();
    //Base texture, not environmental map.
    Texture t0 = TextureManager.loadTexture(
            TestEnvMap.class.getClassLoader().getResource(
            "jmetest/data/images/Monkey.jpg"),
        Texture.MM_LINEAR_LINEAR,
        Texture.FM_LINEAR);
    t0.setWrap(Texture.WM_WRAP_S_WRAP_T);
    ts.setTexture(t0);
    floor.setRenderState(ts); 
    floor.getBatch(0).scaleTextureCoordinates(0, 5);
      
    rootNode.attachChild(floor); 

  }
}

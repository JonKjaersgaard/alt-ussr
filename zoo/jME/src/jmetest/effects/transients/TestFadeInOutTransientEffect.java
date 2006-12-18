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

/*
 * Created on Apr 6, 2004
 */
package jmetest.effects.transients;

import com.jme.app.AbstractGame;
import com.jme.app.VariableTimestepGame;
import com.jme.image.Texture;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Pyramid;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.TextureState;
import com.jme.scene.state.ZBufferState;
import com.jme.system.DisplaySystem;
import com.jme.system.JmeException;
import com.jme.util.TextureManager;
import com.jmex.effects.transients.FadeInOut;
import com.jmex.effects.transients.FadeInOutController;
/**
 * @author Ahmed
 */
public class TestFadeInOutTransientEffect extends VariableTimestepGame {
	private Camera cam;
	private FadeInOut fio;
	private FadeInOutController fioC;
	private Node rootNode, fadeOutNode, fadeInNode;
	
	protected void update(float deltaT) {		
		rootNode.updateWorldData(deltaT * 10);
	}
	protected void render(float interpolation) {
		display.getRenderer().clearBuffers();
		display.getRenderer().draw(rootNode);
		display.getRenderer().clearStatistics();
	}
	protected void initSystem() {
		try {
			display = DisplaySystem.getDisplaySystem(properties.getRenderer());
			display.createWindow(properties.getWidth(), properties.getHeight(),
					properties.getDepth(), properties.getFreq(), properties
							.getFullscreen());
			display.setTitle("FadeInOut Test");
			cam = display.getRenderer().createCamera(properties.getWidth(),
					properties.getHeight());
		} catch (JmeException e) {
			e.printStackTrace();
			System.exit(1);
		}
		display.getRenderer().setBackgroundColor(
				new ColorRGBA(0.1f, 0.1f, 0, 1));
		cam.setFrustum(1f, 1000f, -0.55f, 0.55f, 0.4125f, -0.4125f);
		Vector3f loc = new Vector3f(0, 0, 5);
		Vector3f left = new Vector3f(-1, 0, 0);
		Vector3f up = new Vector3f(0, 1, 0);
		Vector3f dir = new Vector3f(0, 0, -1);
		cam.setFrame(loc, left, up, dir);
		display.getRenderer().setCamera(cam);
		display.getRenderer().enableStatistics(true);
	}
	protected void initGame() {
		rootNode = new Node("Scene Graph Root Node");
		fadeOutNode = new Node("Fade Out Node");
		
		ZBufferState zEnabled = display.getRenderer().createZBufferState();
		zEnabled.setEnabled(true);
		
		setFadeOutNode();
		setFadeInNode();
		
		Quad fadeQ = new Quad("Fade Quad");
        fadeQ.initialize(5, 5);
        fadeQ.setColorBuffer(0, null );
		fadeQ.getLocalTranslation().z = 1;
		
		fio = new FadeInOut("FadeInOut", fadeQ, fadeOutNode, fadeInNode,
				new ColorRGBA(1, 0, 0, 1), 0.01f);
		fio.setLocalTranslation(new Vector3f(0, 0, 1));
		fioC = new FadeInOutController(fio);
		fio.addController(fioC);
		
		rootNode.setRenderState(zEnabled);
		rootNode.attachChild(fio);
		rootNode.attachChild(fadeQ);
		rootNode.updateRenderState();
	}
	// sets up the fadeOut Node
	private void setFadeOutNode() {
		fadeOutNode = new Node("FadeOut Node");
		Vector3f min = new Vector3f(-0.1f, -0.1f, -0.1f);
		Vector3f max = new Vector3f(0.1f, 0.1f, 0.1f);
		TextureState boxTS = display.getRenderer().createTextureState();
		boxTS.setTexture(TextureManager.loadTexture(
				TestFadeInOutTransientEffect.class.getClassLoader()
						.getResource("jmetest/data/images/Monkey.png"),
				Texture.MM_LINEAR, Texture.FM_LINEAR));
		boxTS.setEnabled(true);
		
		Quaternion quat = new Quaternion();
		quat.fromAngleAxis(45, new Vector3f(1, 1, 0));
		
		TriMesh box = new Box("FadeOutBox", min.mult(5f), max.mult(5f));
		box.setLocalRotation(quat);
		box.setRenderState(boxTS);
		
		fadeOutNode.attachChild(box);
	}
	private void setFadeInNode() {
		fadeInNode = new Node("FadeIn Node");
		
		TextureState pyramidTS = display.getRenderer().createTextureState();
		pyramidTS.setTexture(TextureManager.loadTexture(
				TestFadeInOutTransientEffect.class.getClassLoader()
						.getResource("jmetest/data/images/Monkey.png"),
				Texture.MM_LINEAR, Texture.FM_LINEAR));
		pyramidTS.setEnabled(true);
		
		Quaternion quat = new Quaternion();
		quat.fromAngleAxis(45, new Vector3f(1, 1, 0));
		
		TriMesh pyramid = new Pyramid("FadeInPyramid", 2, 3);
		pyramid.setLocalRotation(quat);
		pyramid.setRenderState(pyramidTS);
		
		fadeInNode.attachChild(pyramid);
	}
	protected void reinit() {
	}
	protected void cleanup() {
	}
	public static void main(String[] args) {
		TestFadeInOutTransientEffect app = new TestFadeInOutTransientEffect();
		app
				.setDialogBehaviour(AbstractGame.FIRSTRUN_OR_NOCONFIGFILE_SHOW_PROPS_DIALOG);
		app.start();
	}
}
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

package jmetest.intersection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import com.jme.app.AbstractGame;
import com.jme.app.SimpleGame;
import com.jme.bounding.BoundingSphere;
import com.jme.image.Texture;
import com.jme.input.AbsoluteMouse;
import com.jme.input.MouseInput;
import com.jme.intersection.PickData;
import com.jme.intersection.PickResults;
import com.jme.intersection.TrianglePickResults;
import com.jme.math.Ray;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Line;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.scene.batch.TriangleBatch;
import com.jme.scene.state.AlphaState;
import com.jme.scene.state.ShadeState;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;
import com.jme.util.export.binary.BinaryImporter;
import com.jme.util.geom.BufferUtils;
import com.jmex.model.XMLparser.Converters.FormatConverter;
import com.jmex.model.XMLparser.Converters.ObjToJme;

/**
 * Started Date: Jul 22, 2004 <br>
 * <br>
 * 
 * Demonstrates picking with the mouse.
 * 
 * @author Jack Lindamood
 */
public class TestOBBPick extends SimpleGame {

	// This will be my mouse
	AbsoluteMouse am;

	Spatial maggie;

	private Line l;

	public static void main(String[] args) {
		TestOBBPick app = new TestOBBPick();
		app.setDialogBehaviour(AbstractGame.ALWAYS_SHOW_PROPS_DIALOG);
		app.start();
	}

	protected void simpleInitGame() {
		ShadeState ss = display.getRenderer().createShadeState();
		ss.setShade(ShadeState.SM_FLAT);
		rootNode.setRenderState(ss);
		// Create a new mouse. Restrict its movements to the display screen.
		am = new AbsoluteMouse("The Mouse", display.getWidth(), display
				.getHeight());

		// Get a picture for my mouse.
		TextureState ts = display.getRenderer().createTextureState();
		URL cursorLoc;
		cursorLoc = TestOBBPick.class.getClassLoader().getResource(
				"jmetest/data/cursor/cursor1.png");
		Texture t = TextureManager.loadTexture(cursorLoc, Texture.MM_LINEAR,
				Texture.FM_LINEAR);
		ts.setTexture(t);
		am.setRenderState(ts);

		// Make the mouse's background blend with what's already there
		AlphaState as = display.getRenderer().createAlphaState();
		as.setBlendEnabled(true);
		as.setSrcFunction(AlphaState.SB_SRC_ALPHA);
		as.setDstFunction(AlphaState.DB_ONE_MINUS_SRC_ALPHA);
		as.setTestEnabled(true);
		as.setTestFunction(AlphaState.TF_GREATER);
		am.setRenderState(as);

		// Move the mouse to the middle of the screen to start with
		am.setLocalTranslation(new Vector3f(display.getWidth() / 2, display
				.getHeight() / 2, 0));
		// Assign the mouse to an input handler
        am.registerWithInputHandler( input );
		// Create the box in the middle. Give it a bounds
		URL model = TestOBBPick.class.getClassLoader().getResource(
				"jmetest/data/model/maggie.obj");
		try {
			FormatConverter converter = new ObjToJme();
			converter.setProperty("mtllib", model);
			ByteArrayOutputStream BO = new ByteArrayOutputStream();
			converter.convert(model.openStream(), BO);
			maggie = (Spatial)BinaryImporter.getInstance().load(new ByteArrayInputStream(BO
					.toByteArray()));
			maggie.setLocalScale(.1f);
		} catch (IOException e) { // Just in case anything happens
			System.out.println("Damn exceptions!" + e);
			e.printStackTrace();
			System.exit(0);
		}
		
		maggie.setModelBound(new BoundingSphere());
        maggie.updateModelBound();
        maggie.updateCollisionTree();
		randomizeColors(maggie);
		// Attach Children
		rootNode.attachChild(maggie);
		rootNode.attachChild(am);
		l = new Line("me", new Vector3f[] { new Vector3f(110, 110, 110),
				new Vector3f(-110, -110, -110) }, null, new ColorRGBA[] {
				ColorRGBA.white, ColorRGBA.white }, null);
		rootNode.attachChild(l);
		
		// Deactivate the lightstate so we can see the per-vertex colors
		lightState.setEnabled(false);

		maggie.lockBounds();
        System.err.println(maggie.getWorldBound().getCenter());
        System.err.println(((BoundingSphere)maggie.getWorldBound()).getRadius());
		maggie.lockTransforms();
        results.setCheckDistance(true);
	}

	private void randomizeColors(Spatial s) {
		if (s instanceof TriMesh) {
			((TriMesh) s).setRandomColors();
		} else if (s instanceof Node) {
			Node sPar = (Node) s;
			for (int i = sPar.getQuantity() - 1; i >= 0; i--)
				randomizeColors(sPar.getChild(i));
		}
	}

	PickResults results = new TrianglePickResults() {

		public void processPick() {
			if (getNumber() > 0) {
				for (int j = 0; j < getNumber(); j++) {
					PickData pData = getPickData(j);
					ArrayList tris = pData.getTargetTris();
                    TriangleBatch mesh = (TriangleBatch) pData.getTargetMesh();
					int[] indices = new int[3];
					ColorRGBA toPaint = ColorRGBA.randomColor();

                    if (tris == null) {
                        mesh.setRandomColors();
                        continue;
                    }
                    
					System.out.println(pData.getDistance());
					for (int i = 0; i < tris.size(); i++) {
						int triIndex = ((Integer) tris.get(i)).intValue();
						mesh.getTriangle(triIndex, indices);
						FloatBuffer buff = mesh.getColorBuffer();
						BufferUtils.setInBuffer(toPaint, buff, indices[0]);
						BufferUtils.setInBuffer(toPaint, buff, indices[1]);
						BufferUtils.setInBuffer(toPaint, buff, indices[2]);
					}
				}
			}
		}
	};

	// This is called every frame. Do changing of values here.
	protected void simpleUpdate() {

		// Is button 0 down? Button 0 is left click
		if (MouseInput.get().isButtonDown(0)) {
			Vector2f screenPos = new Vector2f();
			// Get the position that the mouse is pointing to
			screenPos.set(am.getHotSpotPosition().x, am.getHotSpotPosition().y);
			// Get the world location of that X,Y value
			Vector3f worldCoords = display.getWorldCoordinates(screenPos, 1.0f);
			// Create a ray starting from the camera, and going in the direction
			// of the mouse's location
			final Ray mouseRay = new Ray(cam.getLocation(), worldCoords
					.subtractLocal(cam.getLocation()));
            mouseRay.getDirection().normalizeLocal();
			results.clear();

			BufferUtils.setInBuffer(cam.getLocation(), l.getVertexBuffer(0), 0);
			BufferUtils.setInBuffer(worldCoords, l.getVertexBuffer(0), 1);
			maggie.calculatePick(mouseRay, results);

		}
	}
}
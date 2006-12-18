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

package jmetest.effects.water;

import com.jme.app.SimpleGame;
import com.jme.image.Texture;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Spatial;
import com.jme.scene.state.FogState;
import com.jme.scene.state.GLSLShaderObjectsState;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;
import com.jmex.effects.water.HeightGenerator;
import com.jmex.effects.water.ProjectedGrid;
import com.jmex.terrain.util.AbstractHeightMap;

/**
 * <code>TestProjectedGrid</code>
 * Test for the projected grid mesh.
 *
 * @author Rikard Herlitz (MrCoder)
 */
public class TestProjectedGrid extends SimpleGame {
	ProjectedGrid projectedGrid;

	public static void main( String[] args ) {
		TestProjectedGrid app = new TestProjectedGrid();
		app.setDialogBehaviour( ALWAYS_SHOW_PROPS_DIALOG );
		app.start();
	}

	protected void simpleUpdate() {
		if( KeyBindingManager.getKeyBindingManager().isValidCommand( "f", false ) ) {
			projectedGrid.switchFreeze();
		}
	}

	protected void simpleInitGame() {
		display.setTitle( "Projected grid test" );
		cam.setFrustumPerspective( 45.0f, (float) display.getWidth() / (float) display.getHeight(), 1f, 1000 );
		cam.setLocation( new Vector3f( 0, 50, 0 ) );
		cam.update();

		setupFog();

		setupProjectedGrid();

		KeyBindingManager.getKeyBindingManager().set( "f", KeyInput.KEY_F );
		KeyBindingManager.getKeyBindingManager().set( "1", KeyInput.KEY_1 );

		rootNode.setCullMode( Spatial.CULL_NEVER );
		rootNode.setRenderQueueMode( Renderer.QUEUE_OPAQUE );
		fpsNode.setRenderQueueMode( Renderer.QUEUE_OPAQUE );
	}

	private void setupProjectedGrid() {
		projectedGrid = new ProjectedGrid( "Terrain", cam, 100, 80, 0.01f, new HeightGenerator() {
			public float getHeight( float x, float z, float time ) {
				return FastMath.abs( FastMath.sin( x * 0.01f ) * FastMath.cos( z * 0.01f ) * 30.0f +
									 FastMath.sin( x * 0.1f ) * FastMath.cos( z * 0.1f ) * 5.0f );
			}
		} );

		TextureState ts = display.getRenderer().createTextureState();
		Texture t =
				TextureManager.loadTexture(
						TestProjectedGrid.class.getClassLoader().getResource(
								"jmetest/data/texture/dirt.jpg" ),
						Texture.MM_LINEAR_LINEAR,
						Texture.FM_LINEAR );
		t.setWrap( Texture.WM_WRAP_S_WRAP_T );
		ts.setTexture( t );
		ts.setEnabled( true );
		projectedGrid.setRenderState( ts );
		projectedGrid.updateRenderState();
		rootNode.attachChild( projectedGrid );
	}

	private void setupFog() {
		FogState fogState = display.getRenderer().createFogState();
		fogState.setDensity( 1.0f );
		fogState.setEnabled( true );
		fogState.setColor( new ColorRGBA( 0.0f, 0.0f, 0.0f, 1.0f ) );
		fogState.setEnd( 1000 );
		fogState.setStart( 0 );
		fogState.setDensityFunction( FogState.DF_LINEAR );
		fogState.setApplyFunction( FogState.AF_PER_VERTEX );
		rootNode.setRenderState( fogState );
	}
}
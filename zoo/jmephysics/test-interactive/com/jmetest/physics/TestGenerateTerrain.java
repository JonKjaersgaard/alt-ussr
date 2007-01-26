/*
 * Copyright (c) 2005-2006 jME Physics 2
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of 'jME Physics 2' nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
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
package com.jmetest.physics;

import java.util.logging.Level;
import javax.swing.ImageIcon;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.image.Texture;
import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.intersection.BoundingPickResults;
import com.jme.light.DirectionalLight;
import com.jme.math.FastMath;
import com.jme.math.Ray;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.CullState;
import com.jme.scene.state.FogState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.LoggingSystem;
import com.jme.util.TextureManager;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.StaticPhysicsNode;
import com.jmex.physics.util.PhysicsPicker;
import com.jmex.physics.util.SimplePhysicsGame;
import com.jmex.terrain.TerrainPage;
import com.jmex.terrain.util.FaultFractalHeightMap;
import com.jmex.terrain.util.ProceduralTextureGenerator;

/**
 * @author Irrisor
 */
public class TestGenerateTerrain extends SimplePhysicsGame {

    protected void simpleInitGame() {

        final StaticPhysicsNode staticNode = getPhysicsSpace().createStaticNode();

        Spatial terrain = createTerrain();
        staticNode.attachChild( terrain );

        staticNode.getLocalTranslation().set( 0, -150, 0 );

        rootNode.attachChild( staticNode );
        staticNode.generatePhysicsGeometry();

        final DynamicPhysicsNode dynamicNode = getPhysicsSpace().createDynamicNode();

        Sphere meshSphere = new Sphere( "meshsphere", 9, 9, 2 );
        meshSphere.getLocalTranslation().set( -1, 0, 0 );
        meshSphere.setModelBound( new BoundingSphere() );
        meshSphere.updateModelBound();
        dynamicNode.attachChild( meshSphere );

        Node sphere2Node = new Node( "2" );
        sphere2Node.getLocalTranslation().set( 0.25f, 0, 0 );
        sphere2Node.getLocalRotation().fromAngleNormalAxis( -FastMath.PI / 2, new Vector3f( 0, 1, 0 ) );
        Sphere meshSphere2 = new Sphere( "meshsphere2", 9, 9, 1 );
        meshSphere2.getLocalTranslation().set( 0.5f, 0, 0 );
        meshSphere2.setModelBound( new BoundingSphere() );
        meshSphere2.updateModelBound();
        sphere2Node.attachChild( meshSphere2 );
        dynamicNode.attachChild( sphere2Node );

        dynamicNode.generatePhysicsGeometry();

        rootNode.attachChild( dynamicNode );
        dynamicNode.computeMass();

        final DynamicPhysicsNode dynamicNode3 = getPhysicsSpace().createDynamicNode();

        Box meshBox3 = new Box( "meshbox3", new Vector3f(), 2, 2, 2 );
        meshBox3.setModelBound( new BoundingBox() );
        meshBox3.updateModelBound();
        dynamicNode3.attachChild( meshBox3 );
        dynamicNode3.generatePhysicsGeometry();

        rootNode.attachChild( dynamicNode3 );
        dynamicNode3.computeMass();

        showPhysics = true;

        final InputAction resetAction = new InputAction() {
            public void performAction( InputActionEvent evt ) {
                dynamicNode.getLocalTranslation().set( 0, 3, 0 );
                dynamicNode.getLocalRotation().set( 0, 0, 0, 1 );
                dynamicNode.clearDynamics();

                dynamicNode3.getLocalTranslation().set( 0, -2.5f, 0 );
                dynamicNode3.getLocalRotation().set( 0, 0, 0, 1 );
                dynamicNode3.clearDynamics();
            }
        };
        input.addAction( resetAction, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_R, InputHandler.AXIS_NONE, false );
        resetAction.performAction( null );

        InputAction removeAction = new InputAction() {
            public void performAction( InputActionEvent evt ) {
                staticNode.setActive( !staticNode.isActive() );
            }
        };
        input.addAction( removeAction, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_DELETE, InputHandler.AXIS_NONE, false );

        MouseInput.get().setCursorVisible( true );
        new PhysicsPicker( input, rootNode, getPhysicsSpace() );

        //initialize OBBTree of terrain
        rootNode.findPick( new Ray( new Vector3f(), new Vector3f( 1, 0, 0 ) ), new BoundingPickResults() );

        timer.reset();
    }

    private Spatial createTerrain() {

        fpsNode.setRenderQueueMode( Renderer.QUEUE_ORTHO );

        DirectionalLight dl = new DirectionalLight();
        dl.setDiffuse( new ColorRGBA( 1.0f, 1.0f, 1.0f, 1.0f ) );
        dl.setDirection( new Vector3f( 1, -0.5f, 1 ) );
        dl.setEnabled( true );
        lightState.attach( dl );

        DirectionalLight dr = new DirectionalLight();
        dr.setEnabled( true );
        dr.setDiffuse( new ColorRGBA( 1.0f, 1.0f, 1.0f, 1.0f ) );
        dr.setAmbient( new ColorRGBA( 0.5f, 0.5f, 0.5f, 1.0f ) );
        dr.setDirection( new Vector3f( 0.5f, -0.5f, 0 ) );

        lightState.attach( dr );

        display.getRenderer().setBackgroundColor( new ColorRGBA( 0.5f, 0.5f, 0.5f, 1 ) );

        FaultFractalHeightMap heightMap = new FaultFractalHeightMap( 257, 32, 0, 255,
                0.75f, 3 );
        Vector3f terrainScale = new Vector3f( 10, 1, 10 );
        heightMap.setHeightScale( 0.001f );
        TerrainPage page = new TerrainPage( "Terrain", 33, heightMap.getSize(), terrainScale,
                heightMap.getHeightMap(), false );
        page.setDetailTexture( 1, 16 );

        CullState cs = DisplaySystem.getDisplaySystem().getRenderer().createCullState();
        cs.setCullMode( CullState.CS_BACK );
        cs.setEnabled( true );
        page.setRenderState( cs );


        ProceduralTextureGenerator pt = new ProceduralTextureGenerator( heightMap );
        pt.addTexture( new ImageIcon( TestGenerateTerrain.class.getClassLoader().getResource(
                "jmetest/data/texture/grassb.png" ) ), -128, 0, 128 );
        pt.addTexture( new ImageIcon( TestGenerateTerrain.class.getClassLoader().getResource(
                "jmetest/data/texture/dirt.jpg" ) ), 0, 128, 255 );
        pt.addTexture( new ImageIcon( TestGenerateTerrain.class.getClassLoader().getResource(
                "jmetest/data/texture/highest.jpg" ) ), 128, 255, 384 );

        pt.createTexture( 512 );

        TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
        ts.setEnabled( true );
        Texture t1 = TextureManager.loadTexture(
                pt.getImageIcon().getImage(),
                Texture.MM_LINEAR_LINEAR,
                Texture.FM_LINEAR,
                true );
        ts.setTexture( t1, 0 );

        Texture t2 = TextureManager.loadTexture( TestGenerateTerrain.class.getClassLoader().
                getResource(
                "jmetest/data/texture/Detail.jpg" ),
                Texture.MM_LINEAR_LINEAR,
                Texture.FM_LINEAR );
        ts.setTexture( t2, 1 );
        t2.setWrap( Texture.WM_WRAP_S_WRAP_T );

        t1.setApply( Texture.AM_COMBINE );
        t1.setCombineFuncRGB( Texture.ACF_MODULATE );
        t1.setCombineSrc0RGB( Texture.ACS_TEXTURE );
        t1.setCombineOp0RGB( Texture.ACO_SRC_COLOR );
        t1.setCombineSrc1RGB( Texture.ACS_PRIMARY_COLOR );
        t1.setCombineOp1RGB( Texture.ACO_SRC_COLOR );
        t1.setCombineScaleRGB( 1.0f );

        t2.setApply( Texture.AM_COMBINE );
        t2.setCombineFuncRGB( Texture.ACF_ADD_SIGNED );
        t2.setCombineSrc0RGB( Texture.ACS_TEXTURE );
        t2.setCombineOp0RGB( Texture.ACO_SRC_COLOR );
        t2.setCombineSrc1RGB( Texture.ACS_PREVIOUS );
        t2.setCombineOp1RGB( Texture.ACO_SRC_COLOR );
        t2.setCombineScaleRGB( 1.0f );
        page.setRenderState( ts );

        FogState fs = DisplaySystem.getDisplaySystem().getRenderer().createFogState();
        fs.setDensity( 0.5f );
        fs.setEnabled( true );
        fs.setColor( new ColorRGBA( 0.5f, 0.5f, 0.5f, 0.5f ) );
        fs.setEnd( 1000 );
        fs.setStart( 500 );
        fs.setDensityFunction( FogState.DF_LINEAR );
        fs.setApplyFunction( FogState.AF_PER_VERTEX );
        page.setRenderState( fs );

        return page;
    }


    @Override
    protected void simpleUpdate() {
        cameraInputHandler.setEnabled( MouseInput.get().isButtonDown( 1 ) );
    }

    public static void main( String[] args ) {
        LoggingSystem.getLogger().setLevel( Level.WARNING );
        new TestGenerateTerrain().start();
    }
}

/*
 * $Log: TestGenerateTerrain.java,v $
 * Revision 1.3  2006/12/23 22:07:00  irrisor
 * Ray added, Picking interface (natives pending), JOODE implementation added, license header added
 *
 * Revision 1.2  2006/07/04 09:45:32  irrisor
 * added maven 2 pom
 *
 * Revision 1.1  2006/07/01 14:38:26  irrisor
 * Terrain test, TerrainPage is generated correctly as TriMeshGeometry
 *
 */


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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.jme.bounding.BoundingSphere;
import com.jme.image.Texture;
import com.jme.input.MouseInput;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Line;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Extrusion;
import com.jme.scene.shape.Quad;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.AlphaState;
import com.jme.scene.state.CullState;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.TextureState;
import com.jme.scene.state.ZBufferState;
import com.jme.util.LoggingSystem;
import com.jme.util.TextureManager;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.StaticPhysicsNode;
import com.jmex.physics.material.Material;
import com.jmex.physics.util.PhysicsPicker;
import com.jmex.physics.util.SimplePhysicsGame;

/**
 * @author Irrisor
 */
public class TestParcours extends SimplePhysicsGame {
    private List<Vector3f> points = new ArrayList<Vector3f>();
    private Extrusion mesh;
    private DynamicPhysicsNode lastSphere;
    private static final URL RESOURCE_WOOD = TestChooser.class.getResource( "resources/crate.png" );
    private static final URL RESOURCE_BACKGROUND = jmetest.TestChooser.class.getResource( "data/texture/clouds.png" );
    private Quad background;

    protected void simpleInitGame() {
//        Random random = new Random( 23454 );
//        newSphere().getLocalTranslation().set( -5, 9, 1 );
//        newSphere().getLocalTranslation().set( -8, 6, 1 );
//        newSphere().getLocalTranslation().set( -7, 3, 1 );
//        newSphere().getLocalTranslation().set( 0, 1, 1 );
//        lastSphere = newSphere();
//        lastSphere.getLocalTranslation().set( 5, -5, 1 );

//        Vector3f pos = new Vector3f( 5, -5, 1 );
//        for ( int i=0; i < 30; i++ ) {
//            pos = new Vector3f( pos.x + random.nextFloat()*8-4,
//                    pos.y - random.nextFloat()*3 - 3,
//                    pos.z - random.nextFloat()*2 - 1 );
//
////            newSphere().getLocalTranslation().set( pos );
//            points.add( pos );
//        }
        int segments = 10;
        float radius = 8;
        float angle = 0;
        float step = FastMath.PI * 2 / segments;
        for ( int i = 0; i < segments * 10; i++ ) {
            float dx = FastMath.cos( angle ) * radius;
            float dy = FastMath.sin( angle ) * radius;
            Vector3f point = new Vector3f( dx, -i, dy );
            if ( i < segments ) {
                Material material;
                switch ( i % 4 ) {
                    case 1:
                        material = Material.IRON;
                        break;
                    case 2:
                        material = Material.WOOD;
                        break;
                    case 3:
                        material = Material.RUBBER;
                        break;
                    default:
                        material = Material.ICE;
                }
                lastSphere = newSphere( material );
                lastSphere.getLocalTranslation().set( point );
            } else {
                points.add( point );
            }
            angle += step;
        }

        mesh = new Extrusion();
        mesh.updateGeometry( createFourCirclesShape( 10 ), points, 10, false, new Vector3f( 0, 0.001f, 0 ).normalizeLocal() );
        mesh.setModelBound( new BoundingSphere() );
        mesh.updateModelBound();

        StaticPhysicsNode staticNode = getPhysicsSpace().createStaticNode();
        staticNode.attachChild( mesh );
        staticNode.generatePhysicsGeometry( true );
        staticNode.setMaterial( Material.IRON );

        CullState cs = display.getRenderer().createCullState();
        cs.setCullMode( CullState.CS_BACK );
        mesh.setRenderState( cs );
        rootNode.attachChild( staticNode );

        new PhysicsPicker( input, rootNode, getPhysicsSpace() );
        MouseInput.get().setCursorVisible( true );

        int width = display.getWidth();
        int height = display.getHeight();
        background = new Quad( "background", width, height );
        TextureState backTex = display.getRenderer().createTextureState();
        backTex.setTexture( TextureManager.loadTexture( RESOURCE_BACKGROUND,
                Texture.MM_LINEAR, Texture.FM_LINEAR ) );
        background.setRenderState( backTex );
        background.setRenderQueueMode( Renderer.QUEUE_ORTHO );
        background.getLocalTranslation().set( width / 2, height / 2, 0 );
        ZBufferState zState = display.getRenderer().createZBufferState();
        zState.setWritable( false );
        background.setRenderState( zState );
        background.updateRenderState();
        background.updateGeometricState( 0, true );
    }

    @Override
    protected void preRender() {
        display.getRenderer().draw( background );
        display.getRenderer().renderQueue();
    }

    @Override
    protected void simpleUpdate() {
//        cameraInputHandler.setEnabled( true );
        cam.getLocation().y = lastSphere.getLocalTranslation().y;
        cam.update();
    }

    private DynamicPhysicsNode newSphere( Material material ) {
        TriMesh sphere = new Sphere( null, 20, 15, 1 );
        sphere.setModelBound( new BoundingSphere() );
        sphere.updateModelBound();
        DynamicPhysicsNode node = getPhysicsSpace().createDynamicNode();
        node.attachChild( sphere );
        node.generatePhysicsGeometry( false );
        node.setMaterial( material );
        node.computeMass();

        Texture texture = null;
        if ( material == Material.IRON ) {
            texture = TextureManager.loadTexture( RESOURCE_BACKGROUND,
                    Texture.MM_LINEAR, Texture.FM_LINEAR );
            texture.setScale( new Vector3f( 0.2f, 0.2f, 1 ) );
            texture.setEnvironmentalMapMode( Texture.EM_SPHERE );
        } else if ( material == Material.ICE ) {
            color( sphere, new ColorRGBA( 0.5f, 0.5f, 0.9f, 0.6f ) );
        } else if ( material == Material.WOOD ) {
            texture = TextureManager.loadTexture( RESOURCE_WOOD,
                    Texture.MM_LINEAR, Texture.FM_LINEAR );
        } else if ( material == Material.RUBBER ) {
            color( sphere, new ColorRGBA( 0.2f, 0, 0, 1 ) );
        }
        if ( texture != null ) {
            TextureState ts = display.getRenderer().createTextureState();
            ts.setTexture( texture );
            node.setRenderState( ts );
        }

        node.updateRenderState();
        sphere.lockMeshes();
        rootNode.attachChild( node );
        points.add( node.getLocalTranslation() );
        return node;
    }

    /**
     * Little helper method to color a spatial.
     *
     * @param spatial the spatial to be colored
     * @param color   desired color
     */
    private void color( Spatial spatial, ColorRGBA color ) {
        final MaterialState materialState = display.getRenderer().createMaterialState();
        materialState.setDiffuse( color );
        if ( color.a < 1 ) {
            final AlphaState alphaState = display.getRenderer().createAlphaState();
            alphaState.setEnabled( true );
            alphaState.setBlendEnabled( true );
            alphaState.setSrcFunction( AlphaState.SB_SRC_ALPHA );
            alphaState.setDstFunction( AlphaState.DB_ONE_MINUS_SRC_ALPHA );
            spatial.setRenderState( alphaState );
            spatial.setRenderQueueMode( Renderer.QUEUE_TRANSPARENT );
        }
//        materialState.setSpecular( new ColorRGBA( color.r*1.3f, color.g*1.3f, color.b*1.3f, color.a*1.3f ) );
        spatial.setRenderState( materialState );

        CullState cullState = display.getRenderer().createCullState();
        cullState.setCullMode( CullState.CS_BACK );
        spatial.setRenderState( cullState );
    }

    private Line createFourCirclesShape( int numberOfPointsPerCircle ) {
        Line line = new Line( "four circles shape" );
        line.appendCircle( 0.2f, -0.9f, -0.9f, numberOfPointsPerCircle, false );
        line.appendCircle( 0.2f, -0.9f, 0.9f, numberOfPointsPerCircle, false );
        line.appendCircle( 0.2f, 0.9f, -0.9f, numberOfPointsPerCircle, false );
        line.appendCircle( 0.2f, 0.9f, 0.9f, numberOfPointsPerCircle, false );
        return line;
    }

    public static void main( String[] args ) {
        LoggingSystem.getLogger().setLevel( Level.WARNING ); // to see the important stuff
        new TestParcours().start();
    }
}

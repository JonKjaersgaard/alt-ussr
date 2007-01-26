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

package com.jmex.physics;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Geometry;
import com.jme.scene.Line;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.scene.state.LightState;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.WireframeState;
import com.jme.scene.state.ZBufferState;
import com.jme.util.geom.BufferUtils;

/**
 * Analoguous to {@link com.jme.util.geom.Debugger} this class can be used to view physics data such as physics
 * collision bounds, velocity and forces.
 *
 * @author Irrisor
 */
public class PhysicsDebugger {
    private static final float CROSS_SIZE_PERCENTAGE = 0.1f;

    private PhysicsDebugger() {
    }

    /**
     * Draw all info available for the physics nodes in the specified space.
     *
     * @param space    what to draw info for
     * @param renderer where to draw
     */
    public static void drawPhysics( PhysicsSpace space, Renderer renderer ) {
        for ( PhysicsNode physicsNode : space.getNodes() ) {
            drawPhysics( physicsNode, renderer );
        }
        for ( Joint joint : space.getJoints() ) {
            drawJoint( joint, renderer );
        }
    }

    /**
     * Draw all info available for the physics node.
     *
     * @param physicsNode    what to draw info for
     * @param renderer          where to draw
     */
    public static void drawPhysics( PhysicsNode physicsNode, Renderer renderer ) {
        physicsNode.drawDebugInfo( renderer );
    }

    /**
     * Draw all info available for the physics geometry.
     *
     * @param physicsCollisionGeometry    what to draw info for
     * @param renderer          where to draw
     */
    public static void drawPhysics( PhysicsCollisionGeometry physicsCollisionGeometry, Renderer renderer ) {
        physicsCollisionGeometry.drawDebugShape( physicsCollisionGeometry.getPhysicsNode(), renderer );
    }

    private static void drawJoint( Joint joint, Renderer renderer ) {
        Vector3f center = tmp2;
        joint.getAnchor( center );
        DynamicPhysicsNode node1;
        DynamicPhysicsNode node2;
        switch ( joint.getNodes().size() ) {
            case 1:
                node1 = joint.getNodes().get( 0 );
                node2 = null;
                break;
            case 2:
                node1 = joint.getNodes().get( 0 );
                node2 = joint.getNodes().get( 1 );
                node1.getWorldRotation().multLocal( center );
                center.addLocal( node1.getWorldTranslation() );
                break;
            default:
                // not connected (or more than 2 - not allowed!)
                return;
        }

        line.setDefaultColor( COLOR_JOINT );
        line.setLineWidth( 3 );
        drawCross( center, 1, renderer );
        line.setLineWidth( 1 );
        for ( DynamicPhysicsNode node : joint.getNodes() ) {
            lineFrom( center );
            lineTo( node.getWorldTranslation() );
            line.draw( renderer );
        }

        for ( JointAxis axis : joint.getAxes() ) {
            Vector3f direction = tmp3;
            axis.getDirection( direction );
            if ( node2 != null ) {
                if ( !axis.isRelativeToSecondObject() ) {
                    node1.getWorldRotation().multLocal( direction );
                }
                else {
                    node2.getWorldRotation().multLocal( direction );
                }
            }

            Vector3f target = tmp4;

            line.setLineWidth( 2 );
            if ( axis.isTranslationalAxis() ) {
                lineFrom( center );
                lineTo( target.set( center ).addLocal( direction ) );
                line.draw( renderer );
            }
            line.setLineWidth( 3 );
            if ( axis.isRotationalAxis() ) {
                lineFrom( center );
                lineTo( target.set( center ).subtractLocal( direction ) );
                line.draw( renderer );
            }
        }
    }

    /**
     * Draw all info available for the specified PhysicsNode.
     *
     * @param physicsNode what to draw
     * @param renderer where to draw
     */
    static void drawCollisionGeometry( PhysicsNode physicsNode, Renderer renderer ) {
        for ( int i = physicsNode.getQuantity() - 1; i >= 0; i-- ) {
            Spatial child = physicsNode.getChild( i );
            if ( child instanceof PhysicsCollisionGeometry ) {
                PhysicsCollisionGeometry collisionGeometry = (PhysicsCollisionGeometry) child;
                collisionGeometry.drawDebugShape( physicsNode, renderer );
            }
        }
    }

    private static WireframeState boundsWireState;
    private static ZBufferState boundsZState;

    private static void applyStates( Renderer r ) {

        for ( int x = 0; x < Renderer.defaultStateList.length; x++ ) {
            if ( x != RenderState.RS_ZBUFFER && x != RenderState.RS_WIREFRAME ) {
                Renderer.defaultStateList[x].apply();
            }
        }

        if ( boundsWireState == null ) {
            boundsWireState = r.createWireframeState();
            boundsZState = r.createZBufferState();
        }

        boundsWireState.apply();
        boundsZState.apply();
    }

    private static Line line;

    static {
        Vector3f[] points = {new Vector3f(), new Vector3f()};
        line = new Line( "line", points, null, null, null );
        line.setRenderQueueMode( Renderer.QUEUE_SKIP );
        line.setColorBuffer( 0, null );
        line.setLightCombineMode( LightState.OFF );
    }

    public static final ColorRGBA COLOR_ACTIVE = new ColorRGBA( 0.0f, 0.0f, 1.0f, 1 );
    public static final ColorRGBA COLOR_INACTIVE = new ColorRGBA( 0.2f, 0.0f, 0.0f, 1 );
    public static final ColorRGBA COLOR_CENTER = new ColorRGBA( 0, 0.6f, 0, 1 );
    public static final ColorRGBA COLOR_JOINT = new ColorRGBA( 0.6f, 0.6f, 0, 1 );

    private static final Vector3f tmp = new Vector3f();
    private static final Vector3f tmp2 = new Vector3f();
    private static final Vector3f tmp3 = new Vector3f();
    private static final Vector3f tmp4 = new Vector3f();

    private static final ColorRGBA color = new ColorRGBA();

    public static void drawDebugShape( Spatial debugShape, Vector3f geometryPivot, PhysicsCollisionGeometry geometry,
                                       Renderer renderer, float approximateVisualSize ) {
        PhysicsNode physicsNode = geometry.getPhysicsNode();
        applyStates( renderer );
        if ( physicsNode == null || physicsNode.isActive() ) {
            if ( debugShape != null ) {
                setColor( debugShape, COLOR_ACTIVE );
            }
            geometry.getMaterial().getDebugColor( color );
            line.setDefaultColor( color );
        }
        else {
            if ( debugShape != null ) {
                setColor( debugShape, COLOR_INACTIVE );
            }
            line.setDefaultColor( COLOR_INACTIVE );
        }

        if ( physicsNode != null ) {
            // draw line from geometry to center of node
            Vector3f center = physicsNode.getWorldTranslation();
            lineFrom( center );
            lineTo( geometryPivot );
            line.draw( renderer );

            // cross at center
            line.setDefaultColor( COLOR_CENTER );
            line.setLineWidth( 3 );
            float length = Math.max( tmp.set( center ).subtractLocal( geometryPivot ).length(), approximateVisualSize );
            drawCross( center, length, renderer );
        }

        boundsWireState.apply(); // to clear line width
        if ( debugShape != null ) {
            debugShape.draw( renderer );
        }
    }

    private static void drawCross( Vector3f center, float length, Renderer renderer ) {
        float crossSize = CROSS_SIZE_PERCENTAGE * length;
        lineFrom( tmp.set( center ).addLocal( crossSize, 0, 0 ) );
        lineTo( tmp.set( center ).addLocal( -crossSize, 0, 0 ) );
        line.draw( renderer );
        lineFrom( tmp.set( center ).addLocal( 0, crossSize, 0 ) );
        lineTo( tmp.set( center ).addLocal( 0, -crossSize, 0 ) );
        line.draw( renderer );
        lineFrom( tmp.set( center ).addLocal( 0, 0, crossSize ) );
        lineTo( tmp.set( center ).addLocal( 0, 0, -crossSize ) );
        line.draw( renderer );
    }

    private static void setColor( Spatial debugShape, ColorRGBA color ) {
        if ( debugShape instanceof Geometry ) {
            Geometry triMesh = (Geometry) debugShape;
            triMesh.setDefaultColor( color );
            triMesh.setColorBuffer( 0, null );
        } else if ( debugShape instanceof Node ) {
            Node node = (Node) debugShape;
            for ( int i = node.getQuantity() - 1; i >= 0; i-- ) {
                Spatial child = node.getChild( i );
                setColor( child, color );
            }
        }
    }

    private static final Vector3f ARROWHEAD_AXIS = new Vector3f( 1, 0, 0.0001f );

    /**
     * Draws an arrow in specified color. Should be called directly _after_ {@link #drawDebugShape} only!
     *
     * @param from     starting point of the arrow
     * @param to       target point of the arrow
     * @param color    of the arrow
     * @param renderer where to draw to
     */
    public static void drawArrow( Vector3f from, Vector3f to, ColorRGBA color, Renderer renderer ) {
        Vector3f direction = tmp.set( to ).subtractLocal( from );
        float length = direction.length();
        if ( length > 0 ) {
            direction.divide( length );

            line.setDefaultColor( color );
            line.setLineWidth( 1 );
            lineFrom( from );
            lineTo( to );
            line.draw( renderer );

            // draw arrow head
            float size = Math.min( 0.3f, length / 2 );
            Vector3f dir1 = tmp2.set( direction ).cross( ARROWHEAD_AXIS ).normalizeLocal().multLocal( size );
            Vector3f dir2 = tmp3.set( direction ).cross( dir1 ).normalizeLocal().multLocal( size );
            direction.multLocal( 0.2f );
            lineFrom( tmp4.set( to ).subtractLocal( direction ).addLocal( dir1 ) );
            line.draw( renderer );
            lineFrom( tmp4.set( to ).subtractLocal( direction ).subtractLocal( dir1 ) );
            line.draw( renderer );
            lineFrom( tmp4.set( to ).subtractLocal( direction ).addLocal( dir2 ) );
            line.draw( renderer );
            lineFrom( tmp4.set( to ).subtractLocal( direction ).subtractLocal( dir2 ) );
            line.draw( renderer );
        }
    }

    private static void lineTo( Vector3f geometryPivot ) {
        BufferUtils.setInBuffer( geometryPivot, line.getVertexBuffer( 0 ), 1 );
    }

    private static void lineFrom( Vector3f from ) {
        BufferUtils.setInBuffer( from, line.getVertexBuffer( 0 ), 0 );
    }

    public static void setupDebugGeom( Geometry debugShape ) {
        debugShape.setRenderQueueMode( Renderer.QUEUE_SKIP );
        debugShape.setColorBuffer( 0, null );
        debugShape.setLightCombineMode( LightState.OFF );
    }
}

/*
* $log$
*/

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

import java.util.logging.Level;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.bounding.BoundingVolume;
import com.jme.bounding.OrientedBoundingBox;
import com.jme.input.util.SyntheticButton;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.Geometry;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.GeoSphere;
import com.jme.scene.shape.Sphere;
import com.jme.util.LoggingSystem;
import com.jmex.physics.geometry.PhysicsBox;
import com.jmex.physics.geometry.PhysicsCapsule;
import com.jmex.physics.geometry.PhysicsCylinder;
import com.jmex.physics.geometry.PhysicsMesh;
import com.jmex.physics.geometry.PhysicsRay;
import com.jmex.physics.geometry.PhysicsSphere;
import com.jmex.physics.material.Material;
import com.jmex.terrain.TerrainBlock;

/**
 * A PhysicsNode defines a physical entity. Its children may be graphical and physical scenegraph Spatial. The graphical
 * Spatials are used for visual representation and may be used for automatic computation of physical representation
 * (see {@link #generatePhysicsGeometry}, {@link PhysicsCollisionGeometry}). The physical Spatials define the collision
 * bounds for the physics simulation. Advanced implementation may even use them for mass distribution of dynamic nodes.
 * <p/>
 * <p/>
 * PhysicsNodes are created solely by the PhysicsSpace (methods {@link PhysicsSpace#createDynamicNode()} and
 * {@link PhysicsSpace#createStaticNode()}).
 *
 * @author Irrisor
 * @see #generatePhysicsGeometry()
 * @see DynamicPhysicsNode
 * @see StaticPhysicsNode
 * @see PhysicsCollisionGeometry
 */
public abstract class PhysicsNode extends Node {
    /**
     * Constructor.
     *
     * @param name name of the node
     */
    protected PhysicsNode( String name ) {
        super( name );
    }

    protected PhysicsNode() {
        this( null );
    }

    /**
     * @return space this node belongs to, must not be null
     */
    public abstract PhysicsSpace getSpace();

    /**
     * @return true if this is a static (passive, immovable) node.
     */
    public abstract boolean isStatic();

    /**
     * This method generates physics geometry bounds for detecting collision from the graphical representation in this
     * PhysicsNode.
     *
     * @throws IllegalStateException if no graphical representation is present (no Geometries within this Node)
     * @see PhysicsCollisionGeometry
     */
    public void generatePhysicsGeometry() {
        generatePhysicsGeometry( false );
    }

    /**
     * This method generates physics geometry bounds for detecting collision from the graphical representation in this
     * PhysicsNode.
     *
     * @param useTriangleAccurateGeometries true to use triangle accuracy for collision detection with arbitrary
     * geometries - use with care! (makes it expensive to compute collisions)
     * @throws IllegalStateException if no graphical representation is present (no Geometries within this Node)
     * @see PhysicsCollisionGeometry
     */
    public void generatePhysicsGeometry( boolean useTriangleAccurateGeometries ) {
        updateGeometricState( 0, true );
        Vector3f translation = new Vector3f();
        Quaternion rotation = new Quaternion();
        Vector3f scale = new Vector3f( 1, 1, 1 );
        for ( int i = this.getQuantity() - 1; i >= 0; i-- ) {
            Spatial child = this.getChild( i );
            if ( ! ( child instanceof PhysicsCollisionGeometry ) ) {
                addPhysicsGeometries( child, translation, rotation, scale, useTriangleAccurateGeometries );
            }
        }
    }

    private void addPhysicsGeometries( Spatial spatial, Vector3f translation, Quaternion rotation, Vector3f scale,
                                       boolean useTriangleAccurateGeometries ) {
        {
            // incorporate spatials transforms into translation/rotation/scale
            // we have to create new objects here as the method is invoked recursively
            Vector3f nextTranslation = rotation.mult( spatial.getLocalTranslation(),
                    new Vector3f() ).multLocal( scale )
                    .addLocal( translation );
            Quaternion nextRotation = rotation.mult( spatial.getLocalRotation(), new Quaternion() );
            Vector3f localScale = spatial.getLocalScale();
            Vector3f nextScale = new Vector3f( scale ).multLocal( localScale );
            if ( nextScale.x <= 0 || nextScale.y <= 0 || nextScale.z <= 0 ) {
                LoggingSystem.getLogger().log( Level.WARNING, "Skipped node:" ,
                        new IllegalArgumentException( "Scale cannot have a component that is 0 (or smaller) to " +
                                "generate collision geometries!" ) );
                return;
            }
            translation = nextTranslation;
            rotation = nextRotation;
            scale = nextScale;
        }

        geometryBlock:
        if ( spatial instanceof Geometry ) {
            Geometry geometry = (Geometry) spatial;
            
            try {
	            BoundingVolume bound = geometry.getBatch( 0 ).getModelBound(); //TODO: support multiple batches
	
	            if ( bound == null || useTriangleAccurateGeometries ) {
	                // try to create default bounding for unbounded jME primitives
	                if ( geometry instanceof Box ) {
	                    Box box = (Box) geometry;
	                    bound = new BoundingBox( box.getCenter(), box.xExtent, box.yExtent, box.zExtent );
	                }
	                else if ( geometry instanceof Sphere ) {
	                    Sphere sphere = (Sphere) geometry;
	                    bound = new BoundingSphere( sphere.radius, sphere.getCenter() );
	                } else if ( geometry instanceof GeoSphere ) {
                        GeoSphere sphere = (GeoSphere) geometry;
                        bound = new BoundingSphere( sphere.getRadius(), new Vector3f() );
                    }
	                // todo: more defaults?
	                else {
	                    if ( !useTriangleAccurateGeometries ) {
	                        LoggingSystem.getLogger().warning( "no model bound: " + spatial );
	                        break geometryBlock;
	                    }
	                }
	                if ( !useTriangleAccurateGeometries ) {
	                    LoggingSystem.getLogger().info( "using default model bound for: " + spatial );
	                }
	            }
	
	            PhysicsCollisionGeometry collisionGeometry;
	            if ( useTriangleAccurateGeometries ) {
	                if ( geometry instanceof Box ) {
	                    collisionGeometry = createPhysicsGeometry( (BoundingBox) bound );
	                }
	                else if ( geometry instanceof Sphere ) {
	                    collisionGeometry = createPhysicsGeometry( (BoundingSphere) bound );
	                }
	                else if ( geometry instanceof TriMesh ) {
	            			collisionGeometry = createPhysicsGeometry( (TriMesh) geometry );
	                } else {
	                    LoggingSystem.getLogger().warning( "unknown type: " + spatial );
	                    break geometryBlock;
	                }
	            } else {
	                if ( geometry instanceof TerrainBlock ) {
	                    collisionGeometry = createPhysicsGeometry( (TriMesh) geometry );
	                }
	                else if ( bound instanceof BoundingSphere ) {
	                    collisionGeometry = createPhysicsGeometry( (BoundingSphere) bound );
	                }
	                else if ( bound instanceof BoundingBox ) {
	                    collisionGeometry = createPhysicsGeometry( (BoundingBox) bound );
	                }
	                else if ( bound instanceof OrientedBoundingBox ) {
	                    collisionGeometry = createPhysicsGeometry( (OrientedBoundingBox) bound );
	                }
	                else {
	                    throw new RuntimeException( "Unknown bounding volume type: " + bound.getClass() );
	                }
	            }
	            if ( collisionGeometry != null ) {
    		            collisionGeometry.setName( spatial.getName() );
        		        rotation.mult( collisionGeometry.getLocalTranslation(),
            		            collisionGeometry.getLocalTranslation() ).multLocal( scale )
                		        .addLocal( translation );
		            rotation.mult( collisionGeometry.getLocalRotation(), collisionGeometry.getLocalRotation() );
        		        collisionGeometry.getLocalScale().multLocal( scale );
            		}
	        }
            catch (IllegalArgumentException e) {
                // HACK: this identifies TriMeshes which are created by the 3DS loader
                // for sub-materials (multiple materials inside the same mesh);
            		// see TDSFile.putChildMeshes(). These meshes can have bounds of zero volume.
                if (geometry.getName().contains("##"))
                		LoggingSystem.getLogger().warning(geometry.getName() + ": " + e.getMessage());
                else
                		throw e;
            }
        }

        if ( spatial instanceof Node ) {
            Node node = (Node) spatial;
            for ( int i = node.getQuantity() - 1; i >= 0; i-- ) {
                Spatial child = node.getChild( i );
                addPhysicsGeometries( child, translation, rotation, scale, useTriangleAccurateGeometries );
            }
        }
    }

    private PhysicsCollisionGeometry createPhysicsGeometry( TriMesh geometry ) {
        PhysicsMesh mesh = createMesh( null );
        mesh.copyFrom( geometry );
        mesh.getLocalTranslation().set( 0, 0, 0 );
        mesh.setLocalScale( 1 );
        mesh.getLocalRotation().set( 0, 0, 0, 1 );
        return mesh;
    }

    private PhysicsCollisionGeometry createPhysicsGeometry( OrientedBoundingBox box ) {
        PhysicsBox geom = createBox( null );
        if ( box.getExtent().x <= 0 || box.getExtent().y <= 0 || box.getExtent().z <= 0 ) {
            LoggingSystem.getLogger().log( Level.WARNING, "Skipped geometry:" , new IllegalArgumentException( "Extent cannot have a component that is 0 to generate " +
                    "collision geometries!" ) );
            return null;
        }
        geom.getLocalScale().set( box.getExtent().x * 2, box.getExtent().y * 2, box.getExtent().z * 2 );
        geom.getLocalTranslation().set( box.getCenter() );
        geom.getLocalRotation().fromAxes( box.getXAxis(), box.getYAxis(), box.getZAxis() );
        return geom;
    }

    private PhysicsCollisionGeometry createPhysicsGeometry( BoundingBox box ) {
        PhysicsBox geom = createBox( null );
        if ( box.xExtent <= 0 || box.yExtent <= 0 || box.zExtent <= 0 ) {
            LoggingSystem.getLogger().log( Level.WARNING, "Skipped geometry:" , new IllegalArgumentException( "Extent cannot have a component that is 0 to generate " +
                    "collision geometries!" ) );
        }
        geom.getLocalScale().set( box.xExtent * 2, box.yExtent * 2, box.zExtent * 2 );
        geom.getLocalTranslation().set( box.getCenter() );
        return geom;
    }

    private PhysicsCollisionGeometry createPhysicsGeometry( BoundingSphere sphere ) {
        PhysicsSphere geom = createSphere( null );
        float radius = sphere.getRadius();
        if ( radius <= 0 ) {
            throw new IllegalArgumentException( "Found bounding sphere with radius 0 - this is not allowed!" );
        }
        geom.setLocalScale( radius );
        geom.getLocalTranslation().set( sphere.getCenter() );
        return geom;
    }

    /**
     * overridden to check we don't get another PhysicsNode as parent.
     *
     * @param parent new Parent
     * @see com.jme.scene.Spatial#setParent(com.jme.scene.Node)
     */
    @Override
    protected void setParent( Node parent ) {
        if ( parent != null ) {
            Node ancestor = parent;
            while ( ancestor != null ) {
                if ( ancestor instanceof DynamicPhysicsNode ) {
                    throw new IllegalArgumentException( "DynamicPhysicsNodes cannot contain other PhysicsNodes!" );
                }
                ancestor = ancestor.getParent();
            }
        }
        super.setParent( parent );
    }

//    /**
//     * Activate physical behaviour. PhysicsNodes are in deactivated state after creation. PhysicsNode does not interfer
//     * with other PhysicsNodes nor react in any other physical way while deactivated.
//     * Thus all PhysicsNodes have to be activated.
//     * <p/>
//     * If physics Spatials are present within this Node they are used for the physical collision bounds of this Node.
//     * If no such Spatials exist they are generated from the graphical representation by calling
//     * {@link #generatePhysicsGeometry()}.
//     *
//     * @see #deactivate
//     */
//    public abstract void activate();
//
//    /**
//     * Deactivate physical behaviour. PhysicsNode does not interfer
//     * with other PhysicsNodes nor react in any other physical way while deactivated.
//     *
//     * @see #activate
//     */
//    public abstract void deactivate();

    /**
     * Draw information about the physical properties of the Node.
     *
     * @param renderer where to draw to
     */
    protected void drawDebugInfo( Renderer renderer ) {
        PhysicsDebugger.drawCollisionGeometry( this, renderer );
    }

//    /**
//     * @return true if node was activated
//     * @see #activate()
//     */
//    public abstract boolean isActivated();

    /**
     * Create a physics sphere.
     *
     * @param name name of the Spatial
     * @return a new physics sphere
     * @see PhysicsCollisionGeometry
     * @see PhysicsSphere
     */
    public PhysicsSphere createSphere( String name ) {
        return getSpace().createSphere( name, this );
    }

    /**
     * Create a physics box.
     *
     * @param name name of the Spatial
     * @return a new physics box
     * @see PhysicsCollisionGeometry
     * @see PhysicsBox
     */
    public PhysicsBox createBox( String name ) {
        return getSpace().createBox( name, this );
    }

    /**
     * Create a physics cylinder.
     *
     * @param name name of the Spatial
     * @return a new physics cylinder
     * @see PhysicsCollisionGeometry
     * @see PhysicsCylinder
     */
    public PhysicsCylinder createCylinder( String name ) {
        return getSpace().createCylinder( name, this );
    }

    /**
     * Create a physics capsule.
     *
     * @param name name of the Spatial
     * @return a new physics capsule
     * @see PhysicsCollisionGeometry
     * @see PhysicsCapsule
     */
    public PhysicsCapsule createCapsule( String name ) {
        return getSpace().createCapsule( name, this );
    }

    /**
     * Create a physics mesh.
     *
     * @param name name of the Spatial
     * @return a new physics mesh
     * @see PhysicsCollisionGeometry
     * @see PhysicsMesh
     */
    public PhysicsMesh createMesh( String name ) {
        return getSpace().createMesh( name, this );
    }

    /**
     * Create a physics ray.
     *
     * @param name name of the Spatial
     * @return a new physics ray
     * @see PhysicsCollisionGeometry
     * @see PhysicsRay
     */
    public PhysicsRay createRay( String name ) {
        return getSpace().createRay( name, this );
    }

    private boolean active;

    /**
     * @return true if node is currently active
     */
    public final boolean isActive() {
        return active;
    }

    /**
     * Activate the node when added to a space. Deactivate when removed.
     *
     * @param value true when activated
     * @return true if node was (de)activated, false if state was already set to value
     */
    public boolean setActive( boolean value ) {
        if ( active != value ) {
            active = value;
            if ( value ) {
                getSpace().addNode( this );
            }
            else {
                getSpace().removeNode( this );
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Query default material of this node. If no material was set for a geometry the material of this
     * physics node is returned.
     *
     * @return material used for inheriting geometries, not null
     * @see PhysicsCollisionGeometry#getMaterial
     */
    public Material getMaterial() {
        Material material = this.material;
        if ( material != null ) {
            return material;
        }
        else {
            return getSpace().getDefaultMaterial();
        }
    }

    /**
     * store the value for field material
     */
    private Material material;

    /**
     * Change material of this node. All geometries that did not have a matrial set as well as those with the same
     * material like the node inherit this nodes material.
     *
     * @param value new material
     * @see PhysicsCollisionGeometry#setMaterial
     */
    public void setMaterial( final Material value ) {
        this.material = value;
    }

    SyntheticButton collisionEventHandler;

    /**
     * Creates a synthetic button that is triggered when this node collides with another node.
     *
     * @return a synthetic button that is triggered on a collision event that involves this node
     * @see PhysicsSpace#getCollisionEventHandler()
     */
    public SyntheticButton getCollisionEventHandler() {
        if ( collisionEventHandler == null ) {
            collisionEventHandler = new SyntheticButton( "collision" );
        }
        return collisionEventHandler;
    }
    
    /**
     * Merges the set of children of the given node into this one's.
     * The given node will have its children removed.
     * 
     * @param other PhysicsNode whose children are moved to this one.
     */
    public abstract void mergeWith( PhysicsNode other );
}

/*
* $log$
*/

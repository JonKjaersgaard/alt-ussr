package com.jmex.physics;

import java.io.IOException;

import com.jme.bounding.BoundingVolume;
import com.jme.input.util.SyntheticButton;
import com.jme.intersection.CollisionResults;
import com.jme.intersection.PickResults;
import com.jme.math.Ray;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.util.export.InputCapsule;
import com.jme.util.export.JMEExporter;
import com.jme.util.export.JMEImporter;
import com.jme.util.export.OutputCapsule;
import com.jmex.physics.material.Material;

/**
 * Subclasses of this class are used to specify the physical geometry of a {@link PhysicsNode}. The physical geometry
 * defines the collision bounds for the physics simulation. Advanced implementation may even use them for mass
 * distribution of dynamic nodes.
 * <p/>
 * PhysicsCollisionGeometries are created via the create* methods in {@link PhysicsNode}.
 * <p/>
 * TODO: Cylinder, Cone, Capsule, TriMesh, Plane?, Ray?
 *
 * @author Irrisor
 */
public abstract class PhysicsCollisionGeometry extends Spatial {
    public static final String PHYSICS_NODE_PROPERTY = "physicsNode";

	@Override
    public void draw( Renderer r ) {
        // not drawn by default
    }

    public int getTriangleCount() {
        return 0;
    }

    public int getVertexCount() {
        return 0;
    }

    private PhysicsNode node;

    /**
     * Allow to change the associated node by the implementation
     * @param node new phyiscs node of this geometry
     */
    protected void setNode( PhysicsNode node ) {
        if ( node != null ) {
            this.node = node;
            node.attachChild( this );
        } else {
            throw new NullPointerException();
        }
    }

    protected PhysicsCollisionGeometry( PhysicsNode node, String name ) {
        super( name );
        if ( node == null ) {
            throw new NullPointerException();
        }
        this.node = node;
    }

    protected PhysicsCollisionGeometry( PhysicsNode node ) {
        this( node, null );
    }

    @Override
	public void read(JMEImporter im) throws IOException {
		super.read(im);
		
		// physicsNode property is not read because it is set during collision geometry creation
	}

    
    
	@Override
	public void write(JMEExporter ex) throws IOException {
		super.write(ex);
        OutputCapsule capsule = ex.getCapsule(this);

        capsule.write(getParent(), PHYSICS_NODE_PROPERTY, null);
	}

	public static PhysicsNode readPhysicsNodeFromInputCapsule(InputCapsule inputCapsule) throws IOException {
		return (PhysicsNode) inputCapsule.readSavable( PHYSICS_NODE_PROPERTY, null );
	}

	/**
     * Allow only PhysicsNodes as parent.
     *
     * @param parent new parent
     * @see Spatial#setParent(com.jme.scene.Node)
     */
    @Override
    protected void setParent( Node parent ) {
        if ( node == parent || parent == null ) {
            super.setParent( parent );
        }
        else {
            throw new IllegalArgumentException( "parent of a PhysicsCollisionGeometry must be the creating PhysicsNode" );
        }
    }

    /**
     * Draw a visual representation of this physical collision bound.
     *
     * @param physicsNode node this geometry belongs to
     * @param renderer    where to draw to
     */
    protected abstract void drawDebugShape( PhysicsNode physicsNode, Renderer renderer );

    @Override
    public void findCollisions( Spatial scene, CollisionResults results ) {
        // TODO: should this collide with other scenegraph objects?
    }

    @Override
    public void findPick( Ray toTest, PickResults results ) {
        // TODO: should this be pickable
    }

    @Override
    public int getType() {
        return 0; // nothing
    }

    @Override
    public boolean hasCollision( Spatial scene, boolean checkTriangles ) {
        // TODO: should this collide with other scenegraph objects?
        return false;
    }

    @Override
    public void updateWorldBound() {
        // TODO: need bounds?
//        if (bound != null) {
//            worldBound = bound.transform(worldRotation, worldTranslation,
//                    worldScale, worldBound);
//        }
    }

    /**
     * @return the PhysicsNode this collision geometry belongs to
     */
    public PhysicsNode getPhysicsNode() {
        return node;
    }


    /**
     * Query material of this geometry. If no material was set for this geometry the material of the
     * physics node is returned.
     *
     * @return material used for this geometry, not null
     * @see PhysicsNode#getMaterial
     */
    public Material getMaterial() {
        Material material = this.material;
        if ( material != null ) {
            return material;
        }
        else {
            return getPhysicsNode().getMaterial();
        }
    }

    /**
     * store the value for field material
     */
    private Material material;

    /**
     * Change material of this geometry. If the same material is specified for a geometry as the physics node currently
     * has the material of this geometry is inherited. The same applies if no material is set for a geometry.
     *
     * @param value new material
     * @see PhysicsNode#setMaterial
     */
    public void setMaterial( final Material value ) {
        if ( value == null || value == getPhysicsNode().getMaterial() ) {
            this.material = null;
        }
        else {
            this.material = value;
        }
    }

    /**
     * Query the volume of this geometry. Commonly used for mass calculation.
     *
     * @return the current value of this geometry
     */
    public abstract float getVolume();

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

    public void updateModelBound() {
        // TODO: need bounds?
    }

    public void setModelBound( BoundingVolume modelBound ) {
        // TODO: need bounds?
    }
}

/*
* $log$
*/

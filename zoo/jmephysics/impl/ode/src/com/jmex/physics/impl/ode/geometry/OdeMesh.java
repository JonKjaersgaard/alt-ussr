/*Copyright*/
package com.jmex.physics.impl.ode.geometry;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.TriMesh;
import com.jmex.physics.PhysicsDebugger;
import com.jmex.physics.PhysicsNode;
import com.jmex.physics.geometry.PhysicsMesh;
import com.jmex.physics.impl.ode.OdePhysicsNode;
import org.odejava.GeomTriMesh;
import org.odejava.PlaceableGeom;

/**
 * @author Irrisor
 */
public class OdeMesh extends PhysicsMesh implements OdeGeometry {
    private final GeomTriMesh geom;

    public PlaceableGeom getOdeGeom() {
        return geom;
    }

    public OdeMesh( PhysicsNode node ) {
        super( node );
        geom = new GeomTriMesh( getName(), null );
        geom.setGeometry( this );
        geom.setTCEnabled( false );
        geom.setEnabled( false );
    }

    @Override
    public void copyFrom( TriMesh triMesh ) {
        geom.updateData( triMesh );
        geom.setEnabled( true );
        getLocalTranslation().set( triMesh.getLocalTranslation() );
        getLocalScale().set( triMesh.getLocalScale() );
        getLocalRotation().set( triMesh.getLocalRotation() );
    }

    @Override
    public void updateWorldVectors() {
        super.updateWorldVectors();
        //TODO: only if necessary!
        if ( geom != null ) {
            ( (OdePhysicsNode) getPhysicsNode() ).updateTransforms( geom );
            //TODO: scale
//            final Vector3f worldScale = this.worldScale;
//            worldScale.y = worldScale.x;
//            geom.setRadius( worldScale.x );
//            geom.setLength( worldScale.z );
        }
    }

    @Override
    protected void drawDebugShape( PhysicsNode physicsNode, Renderer r ) {
        PhysicsDebugger.drawDebugShape( null, getWorldTranslation(), this, r, 1 );
    }

    @Override
    public float getVolume() {
        return 1; //TODO: algorithm for computing volume of a trimesh?
    }

    private final Vector3f lastTranslation = new Vector3f();
    private final Quaternion lastRotation = new Quaternion();

    public void updateOdeLastTransformation() {
        geom.setLastTransformation( lastTranslation, lastRotation );
        lastTranslation.set( getWorldTranslation() );
        lastRotation.set( getWorldRotation() );
    }

	@Override
	public void setNode(PhysicsNode node) {
		super.setNode(node);
	}
}

/*
 * $log$
 */


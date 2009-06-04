package com.jmex.physics.impl.jbullet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.SimpleBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.PhysicsNode;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.PhysicsSpatial;
import com.jmex.physics.StaticPhysicsNode;
import com.jmex.physics.CollisionGroup;
import com.jmex.physics.geometry.PhysicsBox;
import com.jmex.physics.geometry.PhysicsCapsule;
import com.jmex.physics.geometry.PhysicsCylinder;
import com.jmex.physics.geometry.PhysicsMesh;
import com.jmex.physics.geometry.PhysicsSphere;
import com.jmex.physics.impl.jbullet.geometry.JBulletBox;
import com.jmex.physics.impl.jbullet.geometry.JBulletCapsule;
import com.jmex.physics.impl.jbullet.geometry.JBulletCylinder;
import com.jmex.physics.impl.jbullet.geometry.JBulletMesh;
import com.jmex.physics.impl.jbullet.geometry.JBulletSphere;
import com.jmex.physics.impl.jbullet.joints.JBulletJoint;
import com.jmex.physics.impl.jbullet.util.VecmathConverter;

public class JBulletPhysicsSpace extends PhysicsSpace {

    private List<PhysicsNode> physicsNodes = new ArrayList<PhysicsNode>();
    private List<Joint> joints = new ArrayList<Joint>();

    public DynamicsWorld dynamicsWorld = null;
    private BroadphaseInterface overlappingPairCache;
    private CollisionDispatcher dispatcher;
    private ConstraintSolver solver;
    private DefaultCollisionConfiguration collisionConfiguration;

    JBulletCollisionGroup staticCollisionGroup;
    JBulletCollisionGroup defaultCollisionGroup;

    com.jme.math.Vector3f gravity = new com.jme.math.Vector3f();

    public JBulletPhysicsSpace() {
        //TODO swap out a collision configuration that will proxy the CollisionAlgorithms in the end, to be able to capture collision events.
        collisionConfiguration = new DefaultCollisionConfiguration();

        //Gotta create the collisionGroups required for the JMEPhys engine.
        staticCollisionGroup = createCollisionGroup( "static" );
        defaultCollisionGroup = createCollisionGroup( "default" );

        //Not used for multi-threading, but as JMEPhys doesn't multi-thread yet ...
        //TODO: Config option for multithreading.
        dispatcher = new CollisionDispatcher( collisionConfiguration );

        //Needs to use AxisSweep3, but the class is not in the JBullet
        //source
        //TODO: Convert to a more efficient Broadphase sweeping algorithm
        overlappingPairCache = new SimpleBroadphase();

        //Default solver.  Other multi-threaded options are available.
        //TODO: Config option for multithreading.
        solver = new SequentialImpulseConstraintSolver();

        //Initialization of the core JBullet physics engine.
        dynamicsWorld = new DiscreteDynamicsWorld( dispatcher, overlappingPairCache, solver, collisionConfiguration );

        dynamicsWorld.setGravity( new javax.vecmath.Vector3f( 0, -9.81f, 0 ) );
//		lwjgl = new LwjglGL();
//		lwjgl.init();
//		dynamicsWorld.setDebugDrawer(new GLDebugDrawer(lwjgl));
//		dynamicsWorld.getDebugDrawer().setDebugMode(DebugDrawModes.DRAW_AABB | DebugDrawModes.DRAW_WIREFRAME | DebugDrawModes.DRAW_FEATURES_TEXT | DebugDrawModes.DRAW_CONTACT_POINTS | DebugDrawModes.DRAW_TEXT);
        gravity = new com.jme.math.Vector3f( 0, -9.81f, 0 );
    }

    static class JBulletFactory implements Factory {
        public JBulletFactory() {
            setFactory( this );
        }

        public PhysicsSpace create() {
            JBulletPhysicsSpace space = new JBulletPhysicsSpace();
            return space;
        }

        public String getImplementationName() {
            return "JBullet";
        }

        public String getImplementationVersion() {
            return "2.66";
        }
    }

    @Override
    public void drawImplementationSpecificPhysics( Renderer renderer ) {
//    	dynamicsWorld.debugDrawWorld();
//    	for(PhysicsNode node : physicsNodes)
//    	{
//    		if(node instanceof JBulletPhysicsNode)
//    			drawCollisionObject(((JBulletPhysicsNode)node).getBody(),(node instanceof JBulletStaticPhysicsNode));
//    	}
    }

//    private void drawCollisionObject(CollisionObject colObj, boolean isStatic)
//    {
//    	if(colObj==null)
//    		return;
//    	Transform m = new Transform();
//    	javax.vecmath.Vector3f wireColor = new javax.vecmath.Vector3f();
//		m.set(colObj.getWorldTransform());
//		wireColor.set(1f, 1f, 0.5f); // wants deactivation
//
//		if (colObj.getActivationState() == 1) // active
//		{
//				wireColor.x -= (isStatic) ? 0.5f : 0.25f;
//		}
//		if (colObj.getActivationState() == 2) // ISLAND_SLEEPING
//		{
//				wireColor.y -= (isStatic) ? 0.5f : 0.25f;
//		}
//
//		GLShapeDrawer.drawOpenGL(lwjgl, m, colObj.getCollisionShape(), wireColor, dynamicsWorld.getDebugDrawer().getDebugMode());
//    }

    @Override
    public void addNode( PhysicsNode node ) {
        super.addNode( node );

        // log some stuff:
        Logger logger = Logger.getLogger( PhysicsSpace.LOGGER_NAME );
        if ( logger.isLoggable( Level.INFO ) ) {
            logger.log( Level.INFO,
                    "PhysicsNode (" + node.getName() + ") has been added" );
        }

        // add it to the arraylist
        physicsNodes.add( node );
    }

    protected void removeNode( PhysicsNode obj ) {

        if ( !physicsNodes.remove( obj ) ) {
            return;
        }
        super.removeNode( obj );

        // print out a statement
        Logger logger = Logger.getLogger( PhysicsSpace.LOGGER_NAME );
        if ( logger.isLoggable( Level.INFO ) ) {
            logger.log( Level.INFO,
                    "PhysicsObject ("
                            + obj.getName()
                            + ") has been removed from PhysicsWorld and will no longer take place in the simulation" );
        }
    }

    @Override
    public void addJoint( Joint joint ) {
        super.addJoint( joint );

        // log some stuff:
        Logger logger = Logger.getLogger( PhysicsSpace.LOGGER_NAME );
        if ( logger.isLoggable( Level.INFO ) ) {
            logger.log( Level.INFO,
                    "Joint (" + joint.getName() + ") has been added" );
        }

        // add it to the arraylist
        joints.add( joint );
    }

    protected void removeJoint( Joint joint ) {

        if ( !joints.remove( joint ) ) {
            return;
        }
        super.removeJoint( joint );

        // print out a statement
        Logger logger = Logger.getLogger( PhysicsSpace.LOGGER_NAME );
        if ( logger.isLoggable( Level.INFO ) ) {
            logger.log( Level.INFO,
                    "Joint ("
                            + joint.getName()
                            + ") has been removed from PhysicsWorld and will no longer take place in the simulation" );
        }
    }

    public JBulletCollisionGroup createCollisionGroup( String name ) {
        return new JBulletCollisionGroup( this, name );
    }

    public CollisionGroup getDefaultCollisionGroup() {
        return defaultCollisionGroup;
    }

    public CollisionGroup getStaticCollisionGroup() {
        return staticCollisionGroup;
    }

    @Override
    public boolean collide( PhysicsSpatial spatial1, PhysicsSpatial spatial2 ) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public DynamicPhysicsNode createDynamicNode() {
        JBulletDynamicPhysicsNode node = new JBulletDynamicPhysicsNode( this );
        node.setCollisionGroup( defaultCollisionGroup );
        node.setActive( true );
        return node;
    }

    @Override
    public Joint createJoint() {
        Joint ret = new JBulletJoint( this );
        addJoint( ret );
        return ret;
    }

    @Override
    public StaticPhysicsNode createStaticNode() {
        JBulletStaticPhysicsNode node = new JBulletStaticPhysicsNode( this );
        node.setCollisionGroup( staticCollisionGroup );
        node.setActive( true );
        return node;
    }

    @Override
    public void delete() {
        // TODO Auto-generated method stub

    }

    @Override
    public Vector3f getDirectionalGravity( Vector3f store ) {
        return store.set( gravity );
    }

    @Override
    public List<? extends Joint> getJoints() {
        return Collections.unmodifiableList( joints );
    }

    @Override
    public List<? extends PhysicsNode> getNodes() {
        return Collections.unmodifiableList( physicsNodes );
    }

    @Override
    public void pick( PhysicsSpatial spatial ) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAccuracy( float value ) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAutoRestThreshold( float threshold ) {
        //TODO Auto-generated method stub
    }

    @Override
    protected PhysicsBox createBox( String name, PhysicsNode node ) {
        JBulletBox ret = new JBulletBox( node );
        if ( node != null ) {
            node.attachChild( ret );
        }
        return ret;
    }

    @Override
    protected PhysicsMesh createMesh( String name, PhysicsNode node ) {
        JBulletMesh ret = new JBulletMesh( node );
        if ( node != null ) {
            node.attachChild( ret );
        }
        return ret;
    }

    @Override
    protected PhysicsSphere createSphere( String name, PhysicsNode node ) {
        JBulletSphere ret = new JBulletSphere( node );
        if ( node != null ) {
            node.attachChild( ret );
        }
        return ret;
    }

    @Override
    protected PhysicsCylinder createCylinder( String name, PhysicsNode node ) {
        JBulletCylinder ret = new JBulletCylinder( node );
        if ( node != null ) {
            node.attachChild( ret );
        }
        return ret;
    }

    @Override
    protected PhysicsCapsule createCapsule( String name, PhysicsNode node ) {
        JBulletCapsule ret = new JBulletCapsule( node );
        if ( node != null ) {
            node.attachChild( ret );
        }
        return ret;
    }

    @Override
    public void setDirectionalGravity( Vector3f gravity ) {
        this.gravity = gravity;
        dynamicsWorld.setGravity( VecmathConverter.convert( gravity ) );
    }

    @Override
    public void update( float time ) {
        for ( PhysicsNode node : physicsNodes ) {
            if ( !( node instanceof JBulletPhysicsNode ) ) {
                continue;
            }
            node.updateWorldVectors();
            if ( ( (JBulletPhysicsNode) node ).isDirty() ) {
                ( (JBulletPhysicsNode) node ).rebuildRigidBody();
            }
        }

		for(Joint joint : joints)
		{
			if(((JBulletJoint)joint).isDirty() && joint.isActive())
				((JBulletJoint)joint).buildConstraint();
		}

        dynamicsWorld.stepSimulation( time );

        for ( PhysicsNode node : physicsNodes ) {
            if ( !( node instanceof JBulletDynamicPhysicsNode ) ) {
                continue;
            }
            ( (JBulletDynamicPhysicsNode) node ).applyPhysicsMovement();
        }
    }
}

package com.jmex.physics;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.jme.input.util.SyntheticButton;
import com.jme.math.Vector3f;
import com.jme.util.LoggingSystem;
import com.jme.util.export.binary.BinaryClassLoader;
import com.jme.util.export.binary.BinaryImporter;
import com.jmex.physics.contact.ContactCallback;
import com.jmex.physics.contact.ContactInfo;
import com.jmex.physics.contact.PendingContact;
import com.jmex.physics.geometry.PhysicsBox;
import com.jmex.physics.geometry.PhysicsCapsule;
import com.jmex.physics.geometry.PhysicsCylinder;
import com.jmex.physics.geometry.PhysicsMesh;
import com.jmex.physics.geometry.PhysicsSphere;
import com.jmex.physics.material.Material;
import com.jmex.physics.material.MaterialContactCallback;
import com.jmex.physics.util.binarymodules.*;

/**
 * PhysicsSpace is the central class of the jME Physics API. {@link PhysicsNode}s from the same space can interact
 * with each other. Multiple spaces can be used to model independent physics simulation parts. <br>
 * PhysicsSpace is used to create PhysicNodes via the {@link #createDynamicNode} and {@link #createStaticNode} methods.
 * <p/>
 * The application must frequently call {@link #update(float)} to let the simulation advance. While update is not called
 * the simulation is paused.
 *
 * @author Irrisor
 */
public abstract class PhysicsSpace {

    /**
     * This method is called by the application to process a simulation timestep. This method also calls the methods
     * from {@link PhysicsUpdateCallback} according to their contract.
     *
     * @param time time the simulation should advance
     */
    public abstract void update( float time );

    /**
     * Default constructor.
     */
    protected PhysicsSpace() {
        getContactCallbacks().add( MaterialContactCallback.get() );
    }

    /**
     * list of ContactCallbacks.
     */
    private final List<ContactCallback> contactCallbacks = new ArrayList<ContactCallback>();

    /**
     * Called by {@link #update(float)} to determine contact details. The default implementation iterates the
     * contactCallbacks (descending indexes) to determine the details.
     *
     * @param contact contact that needs details
     * @see PendingContact
     * @see #getContactCallbacks()
     * @see ContactCallback
     */
    protected void adjustContact( PendingContact contact ) {
        // set defaults
        contact.setMu( 1 );
        contact.setBounce( 0.4f );
        contact.setMinimumBounceVelocity( 1f );

        // iterate callbacks if registered
        for ( int i = contactCallbacks.size() - 1; i >= 0; i-- ) {
            ContactCallback callback = contactCallbacks.get( i );
            if ( callback.adjustContact( contact ) ) {
                return;
            }
        }
    }

    /**
     * Add/Remove/Query callbacks for specifying contact details.
     *
     * @see #adjustContact
     */
    public final List<ContactCallback> getContactCallbacks() {
        return contactCallbacks;
    }

    /**
     * Adds a <code>PhysicsNode</code> to this space. A newly created PhysicsNode is automatically added.
     *
     * @param node the PhysicsNode to add
     */
    protected void addNode( PhysicsNode node ) {
        if ( node.getSpace() != this ) {
            throw new IllegalArgumentException( "Nodes can only be added to the space they were created by." );
        }
        node.setActive( true );
    }

    /**
     * Adds a <code>Joint</code> to this space. A newly created Joint is automatically added.
     *
     * @param joint the Joint to add
     */
    protected void addJoint( Joint joint ) {
        if ( joint.getSpace() != this ) {
            throw new IllegalArgumentException( "Joints can only be added to the space they were created by." );
        }
        joint.setActive( true );
    }

    /**
     * Removes a <code>PhysicsNode</code> from this space. Use {@link #addNode(PhysicsNode)} to add it again.
     *
     * @param node the PhysicsNode to remove
     */
    protected void removeNode( PhysicsNode node ) {
        node.setActive( false );
    }

    /**
     * Removes a <code>Joint</code> from this space. Use {@link #addJoint(Joint)} to add it again.
     *
     * @param joint the PhysicsJoint to remove
     */
    protected void removeJoint( Joint joint ) {
        joint.setActive( false );
    }


    /**
     * The default material used for nodes without material set.
     *
     * @return current default material
     */
    public Material getDefaultMaterial() {
        if ( this.defaultMaterial == null ) {
            this.defaultMaterial = Material.DEFAULT;
        }
        return this.defaultMaterial;
    }

    /**
     * store the value for field defaultMaterial
     */
    private Material defaultMaterial;

    /**
     * Change default material for nodes without a material set.
     *
     * @param value new default material
     * @throws IllegalArgumentException if parameter is null
     */
    public void setDefaultMaterial( final Material value ) {
        if ( value != null ) {
            this.defaultMaterial = value;
        }
        else {
            throw new IllegalArgumentException( "null default material not allowed" );
        }
    }

    /**
     * @return the same list like the last call to {@link #update(float)}
     */
//    public abstract List<ContactInfo> getLastContactInfos();

    /**
     * @return an immutable list of physics nodes in this space
     */
    public abstract List<? extends PhysicsNode> getNodes();

    public abstract List<? extends Joint> getJoints();

    /**
     * Delete all physics for this space.
     */
    public abstract void delete();

    /**
     * Interface for subclasses to register a factory for them.
     */
    protected interface Factory {
        /**
         * Called by {@link PhysicsSpace#create()} to create an implementation specific instance.
         *
         * @return new instance of PhysicsSpace
         */
        public PhysicsSpace create();
    }


    /**
     * @return factory for creating PhysicsSpaces
     * @see Factory
     */
    protected static Factory getFactory() {
        return PhysicsSpace.factory;
    }

    /**
     * store the value for field factory.
     */
    private static Factory factory;

    /**
     * @param value factory for creating PhysicsSpaces
     * @see Factory
     */
    protected static void setFactory( final Factory value ) {
        final Factory oldValue = PhysicsSpace.factory;
        if ( oldValue != value ) {
            PhysicsSpace.factory = value;
        }
    }

    /**
     * List of known implementation factory class names. Used by {@link #create} to find a factory if no factory was set
     * yet. The specified class should have a parameterless constructor and implement {@link Factory}.
     */
    private static String[] knownImplementations = {"com.jmex.physics.impl.ode.OdePhysicsSpace$OdeFactory"};

    /**
     * Create an implementation specific instance of PhysicsSpace.
     *
     * @return new instance of this class
     */
    @SuppressWarnings({"unchecked"})
    public static PhysicsSpace create() {
        Factory factory = getFactory();

        if ( factory == null ) {
            // no factory set yet - search for known implementation
            for ( String classname : knownImplementations ) {
                try {
                    Class<?> cls = Class.forName( classname );
                    if ( Factory.class.isAssignableFrom( cls ) ) {
                        Class<? extends Factory> factoryCls = (Class<? extends Factory>) cls;
                        Constructor<? extends Factory> constructor = factoryCls.getConstructor();
                        constructor.setAccessible( true );
                        factory = constructor.newInstance();
                        setFactory( factory );
                        LoggingSystem.getLogger().info( "Using physics implementation '" + classname + "'." );
                    }
                    else {
                        LoggingSystem.getLogger().warning( "Failed to use physics implementation '" + classname + "' specified class is no Factory!" );
                    }
                } catch ( ClassNotFoundException e ) {
                    LoggingSystem.getLogger().info( "Physics implementation '" + classname + "' not present." );
                    // ok implementation not present - continue searching
                } catch ( IllegalAccessException e ) {
                    LoggingSystem.getLogger().warning( "Failed to use physics implementation '" + classname + "' due to IllegalAccessException: " + e.getMessage() );
                    e.printStackTrace();
                } catch ( InstantiationException e ) {
                    LoggingSystem.getLogger().warning( "Failed to use physics implementation '" + classname + "' due to InstantiationException: " + e.getMessage() );
                    e.printStackTrace();
                } catch ( NoSuchMethodException e ) {
                    LoggingSystem.getLogger().warning( "Failed to use physics implementation '" + classname + "': No parameterless contructor was found." );
                } catch ( InvocationTargetException e ) {
                    Throwable cause = e.getCause();
                    LoggingSystem.getLogger().warning( "Failed to use physics implementation '" + classname + "' due to Exception while creating factory: " + cause.getMessage() );
                    cause.printStackTrace();
                }
            }
        }

        if ( factory != null ) {
            return factory.create();
        }
        else {
            throw new IllegalStateException( "No physics implementation was registered nor found!" );
        }
    }

    /**
     * @return a new dynamic physics node
     */
    public abstract DynamicPhysicsNode createDynamicNode();

    /**
     * @return a new static physics node
     */
    public abstract StaticPhysicsNode createStaticNode();

    /**
     * @return a new Joint
     */
    public abstract Joint createJoint();

    /**
     * Specify the overall directional gravity in this space.
     *
     * @param gravity new gravity in this space
     */
    public abstract void setDirectionalGravity( Vector3f gravity );

    /**
     * Query the gravity in this space. The
     * passed Vector3f will be populated with the values, and then returned.
     *
     * @param store where to store the gravity (null to create a new vector)
     * @return store
     */
    public abstract Vector3f getDirectionalGravity( Vector3f store );


    /**
     * Create a physics sphere. This is a convenience method for physics implementations which don't need different
     * geometry creation for different PhysicsNodes - is overridden in this case.
     *
     * @param name name of the Spatial
     * @param node
     * @return a new physics sphere
     * @see PhysicsCollisionGeometry
     * @see com.jmex.physics.geometry.PhysicsSphere
     */
    protected PhysicsSphere createSphere( String name, PhysicsNode node ) {
        throw new UnsupportedOperationException( "Neither PhysicsSpace not PhysicsNode implementation does handle this geometry!" );
    }

    /**
     * Create a physics box. This is a convenience method for physics implementations which don't need different
     * geometry creation for different PhysicsNodes.
     *
     * @param name name of the Spatial
     * @param node
     * @return a new physics box
     * @see PhysicsCollisionGeometry
     * @see com.jmex.physics.geometry.PhysicsBox
     */
    protected PhysicsBox createBox( String name, PhysicsNode node ) {
        throw new UnsupportedOperationException( "Neither PhysicsSpace not PhysicsNode implementation does handle this geometry!" );
    }

    /**
     * Create a physics cylinder. This is a convenience method for physics implementations which don't need different
     * geometry creation for different PhysicsNodes.
     *
     * @param name name of the Spatial
     * @param node
     * @return a new physics cylinder
     * @see PhysicsCollisionGeometry
     * @see com.jmex.physics.geometry.PhysicsBox
     */
    public PhysicsCylinder createCylinder( String name, PhysicsNode node ) {
        throw new UnsupportedOperationException( "Neither PhysicsSpace not PhysicsNode implementation does handle this geometry!" );
    }

    /**
     * Create a physics capsule. This is a convenience method for physics implementations which don't need different
     * geometry creation for different PhysicsNodes.
     *
     * @param name name of the Spatial
     * @param node
     * @return a new physics capsule
     * @see PhysicsCollisionGeometry
     * @see com.jmex.physics.geometry.PhysicsBox
     */
    public PhysicsCapsule createCapsule( String name, PhysicsNode node ) {
        throw new UnsupportedOperationException( "Neither PhysicsSpace not PhysicsNode implementation does handle this geometry!" );
    }

    /**
     * Create a physics mesh. This is a convenience method for physics implementations which don't need different
     * geometry creation for different PhysicsNodes.
     *
     * @param name name of the Spatial
     * @param node
     * @return a new physics mesh
     * @see PhysicsCollisionGeometry
     * @see com.jmex.physics.geometry.PhysicsBox
     */
    public PhysicsMesh createMesh( String name, PhysicsNode node ) {
        throw new UnsupportedOperationException( "Neither PhysicsSpace not PhysicsNode implementation does handle this geometry!" );
    }

    /**
     * list of PhysicsUpdateCallbacks.
     */
    private ArrayList<PhysicsUpdateCallback> updateCallbacks;

    /**
     * @see PhysicsUpdateCallback
     */
    public boolean addToUpdateCallbacks( PhysicsUpdateCallback value ) {
        boolean changed = false;
        if ( value != null ) {
            if ( this.updateCallbacks == null ) {
                this.updateCallbacks = new ArrayList<PhysicsUpdateCallback>();
            }
            else if ( this.updateCallbacks.contains( value ) ) {
                return false;
            }
            changed = this.updateCallbacks.add( value );
        }
        return changed;
    }

    /**
     * Get an element from the updateCallbacks association.
     *
     * @param index index of element to be retrieved
     * @return the element, null if index out of range
     * @see PhysicsUpdateCallback
     */
    protected PhysicsUpdateCallback getFromUpdateCallbacks( int index ) {
        if ( updateCallbacks != null && index >= 0 && index < updateCallbacks.size() ) {
            return updateCallbacks.get( index );
        }
        else {
            return null;
        }
    }

    /**
     * @see PhysicsUpdateCallback
     */
    public void removeAllFromUpdateCallbacks() {
        for ( int i = this.sizeOfUpdateCallbacks() - 1; i >= 0; i-- ) {
            this.removeFromUpdateCallbacks( i );
        }
    }

    /**
     * @see PhysicsUpdateCallback
     */
    public boolean removeFromUpdateCallbacks( PhysicsUpdateCallback value ) {
        boolean changed = false;
        if ( ( this.updateCallbacks != null ) && ( value != null ) ) {
            int index = this.updateCallbacks.indexOf( value );
            if ( index >= 0 ) {
                removeFromUpdateCallbacks( index );
            }
        }
        return changed;
    }

    private void removeFromUpdateCallbacks( int index ) {
        if ( this.updateCallbacks != null ) {
            this.updateCallbacks.remove( index );
        }
    }

    /**
     * @see PhysicsUpdateCallback
     */
    protected int sizeOfUpdateCallbacks() {
        return ( ( this.updateCallbacks == null )
                ? 0
                : this.updateCallbacks.size() );
    }

    private SyntheticButton generalCollisionEventHandler;

    /**
     * All phyics collisions are reported to this synthetic button to allow the application to react on collisions.
     * The trigger receives the ContactInfo as data element. Thus an action can cast getTriggerData() to ContactInfo
     * to obtain additional information on the collision.
     *
     * @return a synthetic button that is triggered on each collision event
     * @see PhysicsNode#getCollisionEventHandler()
     * @see PhysicsCollisionGeometry#getCollisionEventHandler()
     */
    public SyntheticButton getCollisionEventHandler() {
        if ( generalCollisionEventHandler == null ) {
            generalCollisionEventHandler = new SyntheticButton( "collision" );
        }
        return generalCollisionEventHandler;
    }

    /**
     * Called by subclasses upon each collision event that could be processed by the application.
     *
     * @param info info about the collision
     */
    protected void collisionEvent( ContactInfo info ) {
        if ( generalCollisionEventHandler != null ) {
            generalCollisionEventHandler.trigger( 0, '\0', 0, true, info );
        }
        PhysicsNode node1 = info.getNode1();
        if ( node1 != null && node1.collisionEventHandler != null ) {
            node1.collisionEventHandler.trigger( 0, '\0', 0, true, info );
        }
        PhysicsNode node2 = info.getNode2();
        if ( node2 != node1 &&
                node2 != null && node2.collisionEventHandler != null ) {
            node2.collisionEventHandler.trigger( 0, '\0', 0, true, info );
        }
        PhysicsCollisionGeometry geometry1 = info.getGeometry1();
        if ( geometry1 != null && geometry1.collisionEventHandler != null ) {
            geometry1.collisionEventHandler.trigger( 0, '\0', 0, true, info );
        }
        PhysicsCollisionGeometry geometry2 = info.getGeometry2();
        if ( geometry2 != geometry1 &&
                geometry2 != null && geometry2.collisionEventHandler != null ) {
            geometry2.collisionEventHandler.trigger( 0, '\0', 0, true, info );
        }
    }

    public void setupBinaryClassLoader( BinaryImporter importer ) {
    		BinaryClassLoader.registerModule( new BinaryDynamicPhysicsNodeModule( this ) );
    		BinaryClassLoader.registerModule( new BinaryStaticPhysicsNodeModule( this ) );
    		BinaryClassLoader.registerModule( new BinaryJointModule( this ) );
    		BinaryClassLoader.registerModule( new BinaryRotationalJointAxisModule() );
    		BinaryClassLoader.registerModule( new BinaryTranslationalJointAxisModule() );
    		BinaryClassLoader.registerModule( new BinaryPhysicsSphereModule() );
    		BinaryClassLoader.registerModule( new BinaryPhysicsBoxModule() );
    		BinaryClassLoader.registerModule( new BinaryPhysicsCylinderModule() );
    		BinaryClassLoader.registerModule( new BinaryPhysicsCapsuleModule() );
    		BinaryClassLoader.registerModule( new BinaryPhysicsMeshModule() );
    }
}

/*
* $log$
*/

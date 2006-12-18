package com.jmex.physics;

/**
 * This {@link PhysicsNode} represents a physical entity that cannot be moved or rotated by forces.
 * <p/>
 * PhysicsNodes are created solely by the PhysicsSpace (methods {@link PhysicsSpace#createDynamicNode()} and
 * {@link PhysicsSpace#createStaticNode()}).
 *
 * @author Irrisor
 */
public abstract class StaticPhysicsNode extends PhysicsNode {
    protected StaticPhysicsNode( String name ) {
        super( name );
    }

    protected StaticPhysicsNode() {
    }

    @Override
    public final boolean isStatic() {
        return true;
    }
    
    @Override
    public Class getClassTag() {
    		return StaticPhysicsNode.class;
    }
}

/*
* $log$
*/

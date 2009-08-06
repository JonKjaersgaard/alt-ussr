package ussr.sandbox;

import java.util.logging.Level;

import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.JointAxis;
import com.jmex.physics.StaticPhysicsNode;
import com.jmex.physics.geometry.PhysicsBox;
import com.jmex.physics.util.SimplePhysicsGame;

/**
 * @author ilredeldomani
 */
public class RotateTest extends SimplePhysicsGame {
    protected void simpleInitGame() {
        pause = true;
        showPhysics = true;

        StaticPhysicsNode staticNode = getPhysicsSpace().createStaticNode();
        rootNode.attachChild( staticNode );
        PhysicsBox floorBox = staticNode.createBox( "floor" );
        floorBox.getLocalScale().set( 5, 0.5f, 5 );

        
        DynamicPhysicsNode armBase2 = getPhysicsSpace().createDynamicNode();
        rootNode.attachChild( armBase2 );
        
        armBase2.createBox( "armbase2" );

        armBase2.getLocalTranslation().set( 1, 2.25f, 0 );
        armBase2.getLocalScale().set( 2, 2, 2 );
        armBase2.computeMass();
        
        armBase2.setAffectedByGravity(false);


        DynamicPhysicsNode armLimb1 = getPhysicsSpace().createDynamicNode();
        rootNode.attachChild( armLimb1 );
        armLimb1.createBox( "armJoint1" );
        armLimb1.getLocalScale().set( 3, 1, 1 );

        armLimb1.getLocalTranslation().set( 3.5f, 3.75f, 0 );
        armLimb1.computeMass();
        final Joint jointForLimb1 = getPhysicsSpace().createJoint();
        jointForLimb1.attach( armLimb1, armBase2 );
        jointForLimb1.setAnchor( new Vector3f( -1.5f, -0.5f, 0 ) );
        JointAxis axis = jointForLimb1.createRotationalAxis();

        axis.setDirection( new Vector3f( 0, 0, 1 ) );
        axis.setAvailableAcceleration(1);
        axis.setDesiredVelocity(1);
        armLimb1.setAffectedByGravity(false);
    }


    public static void main( String[] args ) {
        new RotateTest().start();
    }
}

/*
 * $Log: TestHingeJoint.java,v $
 * Revision 1.1  2006/12/23 22:07:01  irrisor
 * Ray added, Picking interface (natives pending), JOODE implementation added, license header added
 *
 */


/*public class RotateTest extends SimpleGame {
	private static PhysicsSpace physicsSpace;
	
	public static void main(String[] args) {
		RotateTest app = new RotateTest();
		physicsSpace = PhysicsSpace.create();
		app.start();
	}
	protected void simpleInitGame() {
		DynamicPhysicsNode dpn1 = physicsSpace.createDynamicNode();
		DynamicPhysicsNode dpn2 = physicsSpace.createDynamicNode();
		
		Sphere s1 = new Sphere("s1", 9, 9, 1);
		Sphere s2 = new Sphere("s2", 9, 9, 1);
		
		s1.setModelBound( new BoundingSphere() );
		s2.setModelBound( new BoundingSphere() );
		
		dpn1.attachChild(s1);
		dpn2.attachChild(s2);
		s1.setLocalTranslation(new Vector3f(2f,0,0));
		dpn1.setLocalTranslation(new Vector3f(1f,0,0));
		
		rootNode.attachChild(s1);
		rootNode.attachChild(s2);
	}
}*/

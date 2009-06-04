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
package ussr.physics.jme.pickers;

import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.InputActionInterface;
import com.jme.intersection.PickData;
import com.jme.intersection.TrianglePickResults;
import com.jme.math.Ray;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.system.DisplaySystem;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.RotationalJointAxis;

/**
 * A small tool to be able to pick the visual of physics nodes and move them around with the mouse.
 *
 * @author Irrisor (original JME version)
 * @author Ulrik (modified for USSR)
 * @author Konstantinas (modified for builder). In particular added functionality to pick and move modules 
 * (also their components)in static (paused) state of simulation. To be more precise added methods called moveStaticModules() and
 * moveStaticComponents()  and constructor with flags for staticState and modules.  
 */
public class PhysicsPicker implements Picker {
    private boolean shiftIsPressed = false;
    private boolean isFlexible;
    
    /**
     * The state of simulation, used as a flag to indicate that the functionality is for static state.
     */
    private boolean staticState = false; 
    
    /**
     * The flag to indicate what if going to be moved modules or their components.
     */
    private boolean modules = true;
    
    /**
     * Initiates picking of modules and components in simulation environment and moving them in desired place. 
     * @param staticState,true for the static state of simulation.
     * @param modules, true if modules will be moved in static state of simulation environment, otherwise the components will be moved.
     */
    public PhysicsPicker(boolean staticState, boolean modules){
    	this.staticState = staticState;
    	this.modules = modules;
    }

    public class ModeAction implements InputActionInterface {

        public void performAction(InputActionEvent evt) {
            int c = evt.getTriggerIndex();
            if(c==KeyInput.KEY_LSHIFT || c==KeyInput.KEY_RSHIFT) {
                shiftIsPressed = evt.getTriggerPressed();
            }
        }

    }

    /**
     * root node of the scene - used for picking.
     */
    private Node rootNode;
    /**
     * helper no the picked node is joined to.
     */
    private DynamicPhysicsNode myNode;
    /**
     * joints to link myNode and picked node.
     */
    private Joint flexibleJoint;
    private Joint fixedJoint;
    /**
     * joint to fix myNode in the world.
     */
    private Joint worldJoint;
    private PickAction pickAction;
    private MoveAction moveAction;
    private ModeAction modeAction;
    
    private JMESimulation simulation;
    /**
     * axes to make the flexible joint flexible
     */
    RotationalJointAxis xAxis, yAxis;
    
    /**
     * Constructor of the class.
     *
     * @param input        where {@link #getInputHandler()} is attached to
     * @param rootNode     root node of the scene - used for picking
     * @param physicsSpace physics space to create joints in (picked nodes must reside in there)
     */
    public PhysicsPicker() { }
    
    public void attach(JMESimulation simulation, InputHandler input, Node rootNode, PhysicsSpace physicsSpace ) {
        this.simulation = simulation;
        this.inputHandler = new InputHandler();
        input.addToAttachedHandlers( this.inputHandler );
        this.rootNode = rootNode;
        fixedJoint = physicsSpace.createJoint();
        fixedJoint.setSpring(2000, 200);
        flexibleJoint = physicsSpace.createJoint();
        flexibleJoint.setSpring( 2000, 200 );
        xAxis = flexibleJoint.createRotationalAxis(); xAxis.setDirection(new Vector3f(1,0,0));
        yAxis = flexibleJoint.createRotationalAxis(); yAxis.setDirection(new Vector3f(0,0,1));
        yAxis.setRelativeToSecondObject(true);
        myNode = physicsSpace.createDynamicNode();
        myNode.setName( "Physics Picker Helper Node");
        myNode.setAffectedByGravity( false );
        worldJoint = physicsSpace.createJoint();
        activatePhysicsPicker();
    }

    /**
     * @return the input handler for this picker
     */
    public InputHandler getInputHandler() {
        return inputHandler;
    }

    private InputHandler inputHandler;

    private DynamicPhysicsNode picked;

    private final Vector2f mousePosition = new Vector2f();

    private void activatePhysicsPicker() {
        pickAction = new PickAction();
        inputHandler.addAction( pickAction, InputHandler.DEVICE_MOUSE, 0, InputHandler.AXIS_NONE, false );
        
        modeAction = new ModeAction();
        inputHandler.addAction( modeAction, InputHandler.DEVICE_KEYBOARD, InputHandler.BUTTON_ALL, InputHandler.AXIS_NONE, false);

        moveAction = new MoveAction();
        inputHandler.addAction( moveAction, InputHandler.DEVICE_MOUSE, InputHandler.BUTTON_NONE, InputHandler.AXIS_ALL, false );
    }
    public DynamicPhysicsNode getPicked() {
    	return picked;
    }
    private void release() {
        picked = null;
        if(isFlexible)
            flexibleJoint.detach();
        else
            fixedJoint.detach();
        worldJoint.detach();
        myNode.setActive( false );
    }

    private final Vector3f pickedScreenPos = new Vector3f();
    private final Vector3f pickedWorldOffset = new Vector3f();

    private void attach( DynamicPhysicsNode node ) {
    	System.out.println("Clicked on "+node.getName()+" ["+node.hashCode()+"]");
        DisplaySystem.getDisplaySystem().getScreenCoordinates( node.getWorldTranslation(), pickedScreenPos );
        DisplaySystem.getDisplaySystem().getWorldCoordinates( mousePosition, pickedScreenPos.z, pickedWorldOffset );
        pickedWorldOffset.subtractLocal( node.getWorldTranslation() );
        picked = node;
        myNode.getLocalTranslation().set( node.getWorldTranslation() );
        System.out.println("T="+node.getWorldTranslation());
        myNode.setActive( true );
        worldJoint.setAnchor( myNode.getLocalTranslation() );
        worldJoint.attach( myNode );
        if(shiftIsPressed) {
            flexibleJoint.attach( myNode, node );
            flexibleJoint.setAnchor( new Vector3f() );
            isFlexible = true;
        } else {
            fixedJoint.attach( myNode, node );
            fixedJoint.setAnchor( new Vector3f() );
            isFlexible = false;
        }
    }

    public void delete() {
        inputHandler.removeAction( pickAction );
        inputHandler.removeAction( moveAction );
        inputHandler.removeAction( modeAction );
        myNode.setActive( false );
        myNode.removeFromParent();
        flexibleJoint.detach();
        flexibleJoint.setActive( false );
        fixedJoint.detach();
        fixedJoint.setActive(false);
        worldJoint.detach();
        worldJoint.setActive( false );
        picked = null;
    }

    private class PickAction extends InputAction {
        private final Ray pickRay = new Ray();
        private final TrianglePickResults pickResults = new TrianglePickResults();

        public void performAction( InputActionEvent evt ) {
            if ( evt.getTriggerPressed() ) {
                
                DisplaySystem.getDisplaySystem().getWorldCoordinates( mousePosition, 0, pickRay.origin );
                DisplaySystem.getDisplaySystem().getWorldCoordinates( mousePosition, 0.3f, pickRay.direction );
                pickRay.direction.subtractLocal( pickRay.origin ).normalizeLocal();

                pickResults.clear();
                pickResults.setCheckDistance( true );
                rootNode.findPick( pickRay, pickResults );
                /* To avoid using overly large amount of memory on interactive clicking, clear the collision tree */
                //rootNode.clearCollisionTree(); //TODO JME2 uncommented
                loopResults:
                for ( int i = 0; i < pickResults.getNumber(); i++ ) {
                    PickData data = pickResults.getPickData( i );
                    if ( data.getTargetTris() != null && data.getTargetTris().size() > 0 ) {
                        Spatial target = data.getTargetMesh().getParent();  //TODO JME2 changed from getParentGeom();
                        if(target instanceof TriMesh) {
                            String name = simulation.getGeometryName((TriMesh)target);
                            if(name!=null) System.out.println("Initial click on: "+name);
                            System.out.println("Position: "+((TriMesh)target).getWorldTranslation());
                        } else {
                            System.out.println("Hit non-trimesh: "+target);
                        }
                        while ( target != null ) {
                            if ( target instanceof DynamicPhysicsNode ) {
                                DynamicPhysicsNode picked = (DynamicPhysicsNode) target;
                                attach( picked );
                                break loopResults;
                            }
                            target = target.getParent();
                        }
                    } else {
                        System.out.println("Alt target with target mesh "+data.getTargetMesh());
                    }
                }
            }
            else {
                release();
            }
        }
    }

    private class MoveAction extends InputAction {
        private final Vector3f anchor = new Vector3f();

        public void performAction( InputActionEvent evt ) {

            switch ( evt.getTriggerIndex() ) {
                case 0:
                    mousePosition.x = evt.getTriggerPosition() * DisplaySystem.getDisplaySystem().getWidth();
                    break;
                case 1:
                    mousePosition.y = evt.getTriggerPosition() * DisplaySystem.getDisplaySystem().getHeight();
                    break;
                case 2:
                    // move into z direction with the wheel
                    if ( evt.getTriggerDelta() > 0 ) {
                        pickedScreenPos.z += ( 1 - pickedScreenPos.z ) / 10;
                    }
                    else {
                        pickedScreenPos.z = ( 10 * pickedScreenPos.z - 1 ) / 9;
                    }
                    break;
            }

            if ( picked != null ) {            	
                DisplaySystem.getDisplaySystem().getWorldCoordinates( mousePosition, pickedScreenPos.z, anchor );                
                if (staticState && modules){
                	moveStaticModules();                	
                }else if (staticState && modules==false){
                	moveStaticComponents();
                }                
                myNode.getLocalTranslation().set( anchor.subtractLocal( pickedWorldOffset ) );
                worldJoint.setAnchor( myNode.getLocalTranslation() );
                worldJoint.attach( myNode );
            }
        }
        
        /**
         * Moves static modules during static state of simulation.
         */
        private void moveStaticModules(){        	
        	 Module pickedModule = null;
        	/* Find  the module picked in simulation environment */
			 for(JMEModuleComponent component: simulation.getModuleComponents()) {
		            if(component.getNodes().contains(picked)) {			                
		            	pickedModule = component.getModel(); 
		            }
		        }
			 /*Find out how many components the module consists of and move them accordingly*/			 
			 int amountComponents = pickedModule.getNumberOfComponents();			 
			 	
			 Vector3f vector = anchor.subtractLocal( pickedWorldOffset);
			 for (int component=0;component<amountComponents;component++){				 
				 JMEModuleComponent currentComponent = (JMEModuleComponent)pickedModule.getComponent(component);
				 currentComponent.getModuleNode().getLocalTranslation().set(vector);
//FIXME SOMETHING STRANGE HANPPENING WITH MTRAN (in vector)					 
			 }      
			 /*For moving single component*/
        	 //Vector3f vector = anchor.subtractLocal( pickedWorldOffset);
        	 //picked.getLocalTranslation().set(vector);
        }
        
        /**
         * Moves static components of the modules, during static state of simulation.
         */
        private void moveStaticComponents(){			 	
			 Vector3f vector = anchor.subtractLocal( pickedWorldOffset);			
       	     picked.getLocalTranslation().set(vector);
       }
    }
}

/*
 * $log$
 */


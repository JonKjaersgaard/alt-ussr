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
package com.jmex.physics.util;

import com.jme.input.InputHandler;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.intersection.PickData;
import com.jme.intersection.TrianglePickResults;
import com.jme.math.Ray;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.system.DisplaySystem;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.PhysicsSpace;

/**
 * A small tool to be able to pick the visual of physics nodes and move them around with the mouse.
 *
 * @author Irrisor
 */
public class PhysicsPicker {
    /**
     * root node of the scene - used for picking.
     */
    private final Node rootNode;
    /**
     * helper no the picked node is joined to.
     */
    private final DynamicPhysicsNode myNode;
    /**
     * joint to link myNode and picked node.
     */
    private final Joint joint;
    /**
     * joint to fix myNode in the world.
     */
    private final Joint worldJoint;
    private PhysicsPicker.PickAction pickAction;
    private PhysicsPicker.MoveAction moveAction;

    /**
     * Constructor of the class.
     *
     * @param input        where {@link #getInputHandler()} is attached to
     * @param rootNode     root node of the scene - used for picking
     * @param physicsSpace physics space to create joints in (picked nodes must reside in there)
     */
    public PhysicsPicker( InputHandler input, Node rootNode, PhysicsSpace physicsSpace ) {
        this.inputHandler = new InputHandler();
        input.addToAttachedHandlers( this.inputHandler );
        this.rootNode = rootNode;
        joint = physicsSpace.createJoint();
        joint.setSpring( 2000, 200 );
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

        moveAction = new MoveAction();
        inputHandler.addAction( moveAction, InputHandler.DEVICE_MOUSE, InputHandler.BUTTON_NONE,
                InputHandler.AXIS_ALL, false );
    }

    private void release() {
        picked = null;
        joint.detach();
        worldJoint.detach();
        myNode.setActive( false );
    }

    private final Vector3f pickedScreenPos = new Vector3f();
    private final Vector3f pickedWorldOffset = new Vector3f();

    private void attach( DynamicPhysicsNode node ) {
        DisplaySystem.getDisplaySystem().getScreenCoordinates( node.getWorldTranslation(), pickedScreenPos );
        DisplaySystem.getDisplaySystem().getWorldCoordinates( mousePosition, pickedScreenPos.z, pickedWorldOffset );
        pickedWorldOffset.subtractLocal( node.getWorldTranslation() );

        picked = node;
        myNode.getLocalTranslation().set( node.getWorldTranslation() );
        myNode.setActive( true );
        worldJoint.setAnchor( myNode.getLocalTranslation() );
        worldJoint.attach( myNode );
        joint.attach( myNode, node );
        joint.setAnchor( new Vector3f() );
    }

    public void delete() {
        inputHandler.removeAction( pickAction );
        inputHandler.removeAction( moveAction );
        myNode.setActive( false );
        myNode.removeFromParent();
        joint.detach();
        joint.setActive( false );
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
                loopResults:
                for ( int i = 0; i < pickResults.getNumber(); i++ ) {
                    PickData data = pickResults.getPickData( i );
                    if ( data.getTargetTris() != null && data.getTargetTris().size() > 0 ) {
                        Spatial target = data.getTargetMesh().getParentGeom();
                        while ( target != null ) {
                            if ( target instanceof DynamicPhysicsNode ) {
                                DynamicPhysicsNode picked = (DynamicPhysicsNode) target;
                                attach( picked );
                                break loopResults;
                            }
                            target = target.getParent();
                        }
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
                myNode.getLocalTranslation().set( anchor.subtractLocal( pickedWorldOffset ) );
                worldJoint.setAnchor( myNode.getLocalTranslation() );
                worldJoint.attach( myNode );
            }
        }
    }
}

/*
 * $log$
 */


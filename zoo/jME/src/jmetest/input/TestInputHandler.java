/*
 * Copyright (c) 2003-2006 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software 
 *   without specific prior written permission.
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

package jmetest.input;

import java.awt.event.ActionEvent;

import com.jme.app.SimpleGame;
import com.jme.image.Texture;
import com.jme.input.AbsoluteMouse;
import com.jme.input.InputHandler;
import com.jme.input.InputSystem;
import com.jme.input.KeyInput;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.input.joystick.JoystickInput;
import com.jme.input.util.TwoButtonAxis;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Text;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;
import com.jmex.awt.swingui.JMEAction;

/**
 * Test some new features of the input system.
 *
 * @author Irrisor
 */
public class TestInputHandler extends SimpleGame {

    private Text text1;
    private Text text2;
    private AbsoluteMouse cursor;

    public static void main( String[] args ) {
        JoystickInput.setProvider( InputSystem.INPUT_SYSTEM_LWJGL );
        TestInputHandler app = new TestInputHandler();
        app.setDialogBehaviour( ALWAYS_SHOW_PROPS_DIALOG );
        app.start();
    }

    protected void simpleInitGame() {
        lightState.setEnabled( false );

        text1 = Text.createDefaultTextLabel( "Text Label", "Testing InputHandler" );
        text1.setLocalTranslation( new Vector3f( 1, 60, 0 ) );
        rootNode.attachChild( text1 );
        text2 = Text.createDefaultTextLabel( "Text Label", "Testing InputHandler" );
        text2.setLocalTranslation( new Vector3f( 1, 100, 0 ) );
        rootNode.attachChild( text2 );

        display.getRenderer().setBackgroundColor( ColorRGBA.blue );
        cursor = new AbsoluteMouse( "Mouse Cursor", display.getWidth(), display.getHeight() );
        TextureState cursorTextureState = display.getRenderer().createTextureState();
        cursorTextureState.setTexture(
                TextureManager.loadTexture(
                        TestInputHandler.class.getClassLoader().getResource( "jmetest/data/cursor/cursor1.PNG" ),
                        Texture.MM_LINEAR, Texture.FM_LINEAR )
        );
        cursor.setRenderState( cursorTextureState );
        cursor.setRenderState( text1.getRenderState( RenderState.RS_ALPHA ) );
        cursor.registerWithInputHandler( input );
        rootNode.attachChild( cursor );

        //create an action to shown button activity
        InputAction buttonAction = new InputAction() {
            public void performAction( InputActionEvent evt ) {
                String actionString;
                if ( !evt.getTriggerAllowsRepeats() ) {
                    actionString = evt.getTriggerPressed() ? "pressed" : "released";
                } else {
                    actionString = "down";
                }
                text1.print( evt.getTriggerDevice() + " " + evt.getTriggerName() + " (" + evt.getTriggerIndex() + ":" + evt.getTriggerCharacter() + ") " +
                        actionString + " on " + timer.getTime() );
            }
        };
        //register the action with all devices (mouse, keyboard, joysticks, etc) for all buttons
        input.addAction( buttonAction, InputHandler.DEVICE_ALL, InputHandler.BUTTON_ALL, InputHandler.AXIS_NONE, false );
        //register the action for all devices and button 1 to be repeatedly called while button is down
        input.addAction( buttonAction, InputHandler.DEVICE_ALL, 1, InputHandler.AXIS_NONE, true );

        //create an action to show axis activity
        InputAction axisAction = new InputAction() {
            public void performAction( InputActionEvent evt ) {
                text2.print( evt.getTriggerDevice() + " " + evt.getTriggerName() + " " +
                        "moved to " + evt.getTriggerPosition() + " by " + evt.getTriggerDelta() );
            }
        };

        //define a new axis from two keys
        TwoButtonAxis twoButtonAxis = new TwoButtonAxis( "left_right" );
        input.addAction( twoButtonAxis.getDecreaseAction(), InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_A,
                InputHandler.AXIS_NONE,  true );
        input.addAction( twoButtonAxis.getIncreaseAction(), InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_D,
                InputHandler.AXIS_NONE,  true );
        //register some action with the new axis
        input.addAction( axisAction, twoButtonAxis.getDeviceName(), InputHandler.BUTTON_NONE,
                twoButtonAxis.getIndex(), false );

        //register it with all devices and all axes of these
        input.addAction( axisAction, InputHandler.DEVICE_ALL, InputHandler.BUTTON_NONE, InputHandler.AXIS_ALL, false );

        JMEAction jmeAction = new JMEAction( "test", input ) {
            public void performAction( InputActionEvent evt ) {
                // this gets invoked in the jME update method
                System.out.println( "invoked: " + evt.getTriggerData() );
            }
        };
        jmeAction.actionPerformed( null );
        jmeAction.actionPerformed( new ActionEvent( this, 1, "" ) );
    }

    protected void cleanup() {
        super.cleanup();
        input.clearActions(); //not needed as application exits anyway - just to test if it does not throw exceptions
    }
}

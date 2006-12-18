package com.jmex.physics.util;

import com.jme.app.BaseSimpleGame;
import com.jme.image.Texture;
import com.jme.input.FirstPersonHandler;
import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.renderer.Renderer;
import com.jme.util.LoggingSystem;
import com.jme.util.geom.Debugger;
import com.jmex.physics.PhysicsDebugger;
import com.jmex.physics.PhysicsSpace;


/**
 * <code>SimplePhysicsGame</code> provides the simplest possible implementation of a
 * main game loop including physics. It's the equivalent to {@link com.jme.app.SimpleGame}, only with a PhysicsSpace
 * added.
 *
 * @author Irrisor
 * @version $Id: SimplePhysicsGame.java,v 1.13 2006/11/25 20:32:22 irrisor Exp $
 * @see com.jme.app.SimpleGame
 */
public abstract class SimplePhysicsGame extends BaseSimpleGame {

    private PhysicsSpace physicsSpace;
    protected InputHandler cameraInputHandler;

    @Override
    protected void initSystem() {
        super.initSystem();

        /** Create a basic input controller. */
        cameraInputHandler = new FirstPersonHandler( cam, 50, 1 );
        input = new InputHandler();
        input.addToAttachedHandlers( cameraInputHandler );

        setPhysicsSpace( PhysicsSpace.create() );

        input.addAction( new InputAction() {
            public void performAction( InputActionEvent evt ) {
                if ( evt.getTriggerPressed() ) {
                    showPhysics = !showPhysics;
                }
            }
        }, InputHandler.DEVICE_KEYBOARD, KeyInput.KEY_V, InputHandler.AXIS_NONE, false );
    }

    /**
     * @return the physics space for this simple game
     */
    public PhysicsSpace getPhysicsSpace() {
        return physicsSpace;
    }

    /**
     * @param physicsSpace The physics space for this simple game
     */
	protected void setPhysicsSpace(PhysicsSpace physicsSpace) {
		if ( physicsSpace != this.physicsSpace ) {
			if ( this.physicsSpace != null )
	       		this.physicsSpace.delete();
			this.physicsSpace = physicsSpace;
		}
	}

	/**
     * Called every frame to update scene information.
     *
     * @param interpolation unused in this implementation
     * @see BaseSimpleGame#update(float interpolation)
     */
    @Override
    protected final void update( float interpolation ) {
        // disable input as we want it to be updated _after_ physics
        // in your application derived from BaseGame you can simply make the call to InputHandler.update later
        // in your game loop instead of this disabling and reenabling

        super.update( interpolation );

        if ( !pause ) {
            float tpf = this.tpf;
            if ( tpf > 1 || Float.isNaN( tpf ) ) {
                LoggingSystem.getLogger().warning( "Maximum physics update interval is 1 second - capped." );
                tpf = 1;
            }
            getPhysicsSpace().update( tpf );
        }

        input.update( tpf );

        if ( !pause ) {
            /** Call simpleUpdate in any derived classes of SimpleGame. */
            simpleUpdate();

            /** Update controllers/render states/transforms/bounds for rootNode. */
            rootNode.updateGeometricState( tpf, true );
        }
    }

    @Override
    protected void updateInput() {
        // don't input here but after physics update
    }

    protected boolean showPhysics;

    /**
     * This is called every frame in BaseGame.start(), after update()
     *
     * @param interpolation unused in this implementation
     * @see com.jme.app.AbstractGame#render(float interpolation)
     */
    @Override
    protected final void render( float interpolation ) {
        super.render( interpolation );

        preRender();

        Renderer r = display.getRenderer();

        /** Draw the rootNode and all its children. */
        r.draw( rootNode );

        /** Call simpleRender() in any derived classes. */
        simpleRender();

        /** Draw the fps node to show the fancy information at the bottom. */
        r.draw( fpsNode );

        if ( showDepth ) {
            r.renderQueue();
            Debugger.drawBuffer( Texture.RTT_SOURCE_DEPTH, Debugger.NORTHEAST, r );
        }

        doDebug(r);
    }

    protected void preRender() {

    }

    @Override
    protected void doDebug(Renderer r) {
        super.doDebug(r);

        if ( showPhysics ) {
            PhysicsDebugger.drawPhysics( getPhysicsSpace(), r );
        }

        if (showDepth) {
            r.renderQueue();
            Debugger.drawBuffer(Texture.RTT_SOURCE_DEPTH, Debugger.NORTHEAST, r);
        }
    }
}

/*
 * $log$
 */
/*
 * Copyright (c) 2003-2006 jMonkeyEngine All rights reserved. Redistribution and
 * use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met: * Redistributions of source
 * code must retain the above copyright notice, this list of conditions and the
 * following disclaimer. * Redistributions in binary form must reproduce the
 * above copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the distribution. *
 * Neither the name of 'jMonkeyEngine' nor the names of its contributors may be
 * used to endorse or promote products derived from this software without
 * specific prior written permission. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT
 * HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.jme.app;

import java.util.logging.Level;

import com.jme.input.InputSystem;
import com.jme.util.LoggingSystem;

/**
 * <code>BaseGame</code> provides the simplest possible implementation of a
 * main game loop. Both logic and graphics are updated as quickly as possible,
 * with no interpolation to account for shifting frame rates. It is suggested
 * that a more complex variant of AbstractGame be used in almost all cases.
 * 
 * @author Mark Powell, Eric Woroshow
 * @version $Id: BaseGame.java,v 1.13 2006/05/12 21:16:04 nca Exp $
 */
public abstract class BaseGame extends AbstractGame {

    /**
     * The simplest main game loop possible: render and update as fast as
     * possible.
     */
    public final void start() {
        LoggingSystem.getLogger().log(Level.INFO, "Application started.");
        try {
            getAttributes();

            if (!finished) {
                initSystem();

                assertDisplayCreated();

                initGame();

                // main loop
                while (!finished && !display.isClosing()) {
                    // handle input events prior to updating the scene
                    // - some applications may want to put this into update of
                    // the game state
                    InputSystem.update();

                    // update game state, do not use interpolation parameter
                    update(-1.0f);

                    // render, do not use interpolation parameter
                    render(-1.0f);

                    // swap buffers
                    display.getRenderer().displayBackBuffer();

                    Thread.yield();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        cleanup();
        LoggingSystem.getLogger().log(Level.INFO, "Application ending.");

        if (display != null)
            display.reset();
        quit();
    }

    /**
     * Closes the display
     * 
     * @see AbstractGame#quit()
     */
    protected void quit() {
        if (display != null)
            display.close();
    }

    /**
     * @param interpolation
     *            unused in this implementation
     * @see AbstractGame#update(float interpolation)
     */
    protected abstract void update(float interpolation);

    /**
     * @param interpolation
     *            unused in this implementation
     * @see AbstractGame#render(float interpolation)
     */
    protected abstract void render(float interpolation);

    /**
     * @see AbstractGame#initSystem()
     */
    protected abstract void initSystem();

    /**
     * @see AbstractGame#initGame()
     */
    protected abstract void initGame();

    /**
     * @see AbstractGame#reinit()
     */
    protected abstract void reinit();

    /**
     * @see AbstractGame#cleanup()
     */
    protected abstract void cleanup();
}

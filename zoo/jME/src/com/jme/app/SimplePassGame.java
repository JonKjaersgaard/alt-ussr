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

package com.jme.app;

import com.jme.renderer.pass.BasicPassManager;

/**
 * <code>SimpleGame</code> provides the simplest possible implementation of a
 * main game loop. Interpolation is used between frames for varying framerates.
 * 
 * @author Joshua Slack, (javadoc by cep21)
 * @version $Id: SimplePassGame.java,v 1.3 2006/05/11 19:40:45 nca Exp $
 */
public abstract class SimplePassGame extends BaseSimpleGame {

    protected BasicPassManager pManager;

    /**
     * This is called every frame in BaseGame.start()
     * 
     * @param interpolation
     *            unused in this implementation
     * @see AbstractGame#update(float interpolation)
     */
    protected final void update(float interpolation) {
        super.update(interpolation);

        /** Call simpleUpdate in any derived classes of SimpleGame. */
        simpleUpdate();

        /** Update controllers/render states/transforms/bounds for rootNode. */
        rootNode.updateGeometricState(tpf, true);
        fpsNode.updateGeometricState(tpf, true);

        pManager.updatePasses(tpf);
    }

    /**
     * This is called every frame in BaseGame.start(), after update()
     * 
     * @param interpolation
     *            unused in this implementation
     * @see AbstractGame#render(float interpolation)
     */
    protected final void render(float interpolation) {
        super.render(interpolation);

        /** Have the PassManager render. */
        pManager.renderPasses(display.getRenderer());

        /** Call simpleRender() in any derived classes. */
        simpleRender();
        
        doDebug(display.getRenderer());
    }

    /**
     * Creates pass manager then calls super.initGame();
     * 
     * @see BaseSimpleGame#initGame()
     */
    protected final void initGame() {
        pManager = new BasicPassManager();
        
        super.initGame();
    }
}

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
package jmetest.awt.swingui;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;

import javax.swing.*;

import com.jme.app.*;
import com.jme.bounding.*;
import com.jme.input.*;
import com.jme.scene.shape.*;
import com.jme.util.*;
import com.jmex.awt.swingui.*;
import com.jmex.game.*;
import com.jmex.game.state.*;

/**
 * @author Matthew D. Hicks
 */
public class TestJMEDesktopState {
	public static void main(String[] args) throws Exception {
		// Create our StandardGame with default settings
		final StandardGame game = new StandardGame("TestJMEDesktopState");
		game.start();
		
		// Lets add a game state behind with some content
		BasicGameState debugState = new BasicGameState("BasicGameState");
		GameStateManager.getInstance().attachChild(debugState);
		debugState.setActive(true);
		Sphere sphere = new Sphere("ExampleSphere", 50, 50, 5.0f);
		sphere.setRandomColors();
		sphere.updateRenderState();
		sphere.setModelBound(new BoundingSphere());
		sphere.updateModelBound();
		debugState.getRootNode().attachChild(sphere);
		
		// Instantiate and add our JMEDesktopState
		final JMEDesktopState desktopState = new JMEDesktopState();
		GameStateManager.getInstance().attachChild(desktopState);
		desktopState.setActive(true);
		
		GameTaskQueueManager.getManager().update(new Callable<Object>() {
			public Object call() throws Exception {
				// Add a Quit button
				JButton button = new JButton("Quit" );
		        desktopState.getDesktop().getJDesktop().add(button);
		        button.setLocation(0, 0);
		        button.setSize(button.getPreferredSize());
		        button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						game.finish();
					}
		        });
		        
		        // Add JInternalFrame
		        JInternalFrame frame = new JInternalFrame("Test Internal Frame", true, true, true);
		        frame.setSize(200, 200);
		        frame.setLocation(100, 100);
		        frame.setVisible(true);
		        desktopState.getDesktop().getJDesktop().add(frame);
		        
		        // Set the cursor to visible
		        MouseInput.get().setCursorVisible(true);
				return null;
			}
        });
	}
}

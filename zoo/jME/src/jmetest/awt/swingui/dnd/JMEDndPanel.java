/*
 * Copyright (c) 2006 World of Mystery Project Team
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

package jmetest.awt.swingui.dnd;

import java.awt.BorderLayout;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import com.jme.util.LoggingSystem;
import com.jmex.awt.swingui.dnd.JMEDndException;
import com.jmex.awt.swingui.dnd.JMEDragAndDrop;
import com.jmex.awt.swingui.dnd.JMEDragGestureEvent;
import com.jmex.awt.swingui.dnd.JMEDragGestureListener;
import com.jmex.awt.swingui.dnd.JMEDragSourceEvent;
import com.jmex.awt.swingui.dnd.JMEDragSourceListener;
import com.jmex.awt.swingui.dnd.JMEDropTargetEvent;
import com.jmex.awt.swingui.dnd.JMEDropTargetListener;
import com.jmex.awt.swingui.dnd.JMEMouseDragGestureRecognizer;


/**
 * a test panel with drag and drop
 *
 * @author galun
 * @version $Id: JMEDndPanel.java,v 1.2 2006/09/17 17:38:22 irrisor Exp $
 */
public class JMEDndPanel extends JInternalFrame implements JMEDragSourceListener, JMEDropTargetListener, JMEDragGestureListener {

    private static final long serialVersionUID = -6299259970887193861L;
    private JLabel label;
    private JLabel info;
    private Logger log = LoggingSystem.getLogger();
    private JMEDragAndDrop dnd;

    public JMEDndPanel( JMEDragAndDrop dragAndDropSupport ) {
        setLayout( new BorderLayout() );
        label = new JLabel( "x" + new Random().nextInt() );
        label.setName( "label" );
        add( label );
        info = new JLabel( " " );
        add( info, BorderLayout.SOUTH );
        dnd = dragAndDropSupport;
        new JMEMouseDragGestureRecognizer( dragAndDropSupport, this, DnDConstants.ACTION_COPY_OR_MOVE, this );
    }

    public void dragEnter( JMEDragSourceEvent dsde ) {
        info.setText( "drag:dragEnter" );
    }

    public void dragExit( JMEDragSourceEvent dse ) {
        info.setText( "drag:dragExit" );
    }

    public void dragDropEnd( JMEDragSourceEvent dsde ) {
        info.setText( "dragDropEnd: " + dsde.getDropSuccess() );
    }

    public void dragEnter( JMEDropTargetEvent dtde ) {
        info.setText( "drag:dragEnter" );
    }

    public void dragExit( JMEDropTargetEvent dte ) {
        info.setText( "drag:dragExit" );
    }

    public void dragOver( JMEDropTargetEvent e ) {
        info.setText( "drap:dragOver" );
    }

    public void drop( JMEDropTargetEvent dtde ) {
        info.setText( "drop" );
        try {
            dtde.acceptDrop( dtde.getAction() );
            Transferable transferable = dtde.getTransferable();
            String text = (String) transferable.getTransferData( TransferableText.TEXT_FLAVOR );
            repaint();
            label.setText( "dropped text: " + text );
            dtde.dropComplete( true );
        } catch ( Exception e ) {
            System.err.println( "drop: " + e.toString() );
            e.printStackTrace();
        }
    }

    public void dragGestureRecognized( JMEDragGestureEvent dge ) {
        String text = label.getText();
        TransferableText transferable = new TransferableText( text );
        ImageIcon icon = JMEDragAndDrop.createTextIcon( this, text );
        try {
            dnd.startDrag( dge, icon, transferable, this );
            label.setText( "drag: " + text );
        } catch ( JMEDndException e ) {
            label.setText( e.getMessage() );
            log.log( Level.INFO, "invalid dnd action", e );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}

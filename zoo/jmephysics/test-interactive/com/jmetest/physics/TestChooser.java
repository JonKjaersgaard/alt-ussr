/*Copyright*/
package com.jmetest.physics;

import java.util.Vector;
import javax.swing.UIManager;

/**
 * @author
 */
public class TestChooser extends jmetest.TestChooser {
    public static void main( String[] args ) {
        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        } catch ( Exception e ) {
            //ok, keep the ugly one then :\
        }
        new TestChooser().start( args );
    }

    @Override
    protected void addDisplayedClasses( Vector classes ) {
        find( "com.jmetest.physics", true, classes );
    }
}

/*
 * $log$
 */


/*
 * Open Dynamics Engine for Java (odejava) Copyright (c) 2004, Jani Laakso, All
 * rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials
 * provided with the distribution. Neither the name of the odejava nor the
 * names of its contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.odejava;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.logging.Level;

import com.jme.math.Vector3f;
import com.jme.util.LoggingSystem;
import org.odejava.ode.Ode;
import org.odejava.ode.SWIGTYPE_p_float;
import org.odejava.ode.SWIGTYPE_p_int;

/**
 * <p/>
 * Odejava physics engine core class. This class contains helper methods and
 * general simulation parameters. API is likely to change as common parameter
 * issues and callback routine handling is changed on Odejava. Also swig type
 * helpers become obsolete when Odejava.i can return proper java objects
 * directly.
 * </p>
 * <p/>
 * Odejava API is divided in two different interfaces.
 * <p/>
 * <strong>Low level API: </strong> This Java API is one to one with ODE's main
 * C API. All ODE's functions are supported on low level API. ODE's own
 * documentation at q12.org/ode can be used directly as the Java API is almost
 * identical. This API is works very well and has best performance. This API is
 * generated using Swig tool and it is easy to regenerate in the future if
 * ODE's own API changes. See class org.odejava.ode.
 * </p>
 * <p/>
 * <strong>High level API: </strong> Proper Java API which is easy to use. This
 * depends to low level API. This is initial version and bound to change.
 * However, I encourage developers to use this this API as much as possible.
 * When needed, high level API can be extended with low level API. See package
 * org.odejava.
 * </p>
 * <p/>
 * Created 16.12.2003 (dd.mm.yyyy)
 *
 * @author Jani Laakso E-mail: jani.laakso@itmill.com
 *         see http://odejava.dev.java.net
 */
public class Odejava {

    private static Odejava singleton;

    //FIX ME: long!
    public static native long getNativeAddr( long swigCPtr );

    private static boolean loadFailed;

    // Load odejava native library
    static {
        loadFailed = false;

        try {
            System.loadLibrary( "odejava" );

        } catch ( UnsatisfiedLinkError e ) {
            try {
                System.loadLibrary( "odejava64" );

            } catch ( UnsatisfiedLinkError e2 ) {
                LoggingSystem.getLogger().log( Level.SEVERE, "Native code library (32 and 64 bit library) failed to load: " + e, e );
                loadFailed = true;
            }
            loadFailed = true;
        }
//            LoggingSystem.getLogger().log( Level.INFO, "Ode native version " + Ode.ODEJAVA_VERSION );
    }


    // Not instantiable
    private Odejava() {
    }

    /**
     * Initializes Odejava if needed.
     */
    public static boolean init() {
        if ( loadFailed ) {
            return false;
        }
        else {
            Odejava.getInstance();
            return true;
        }
    }

    /**
     * Get instance of Odejava, initializes Odejava if needed.
     */
    public static Odejava getInstance() {
        // Loads the native library
        if ( singleton == null ) {
            singleton = new Odejava();
        }
        return singleton;
    }

    /**
     * Returns odejava library version
     *
     * @return version
     */
    public static String getLibraryVersion() {
        return Ode.ODEJAVA_VERSION;
    }

    /**
     * Create swig float array based on given java array.
     *
     * @param javaArray
     * @return swig float array
     */
    public static SWIGTYPE_p_float createSwigArray( float[] javaArray ) {
        SWIGTYPE_p_float result = Ode.new_floatArray( javaArray.length );
        for ( int i = 0; i < javaArray.length; i++ ) {
            Ode.floatArray_setitem( result, i, javaArray[i] );
        }
        return result;
    }

    /**
     * Create swig float array with given length.
     *
     * @param length length of the array
     * @return swig float array
     */
    public static SWIGTYPE_p_float createSwigFloatArray( int length ) {
        SWIGTYPE_p_float result = Ode.new_floatArray( length );
        return result;
    }

    /**
     * Create swig int array with given length.
     *
     * @param length length of the array
     * @return swig int array
     */
    public static SWIGTYPE_p_int createSwigIntArray( int length ) {
        SWIGTYPE_p_int result = Ode.new_intArray( length );
        return result;
    }

    /**
     * Create java float array based on given swig array.
     *
     * @param swigArray
     * @param length
     * @return java float array
     */
    public static float[] createJavaArray( SWIGTYPE_p_float swigArray, int length ) {
        float[] result = new float[length];
        for ( int i = 0; i < length; i++ ) {
            result[i] = Ode.floatArray_getitem( swigArray, i );
        }
        return result;
    }

    /**
     * Create java Vector3f based on given swig array. Swig array must be at
     * least of size 3.
     *
     * @param swigArray
     */
    public static void createVector3f( SWIGTYPE_p_float swigArray, Vector3f v ) {
        v.x = Ode.floatArray_getitem( swigArray, 0 );
        v.y = Ode.floatArray_getitem( swigArray, 1 );
        v.z = Ode.floatArray_getitem( swigArray, 2 );
    }

    /**
     * Create swig int array based on given java array.
     *
     * @param javaArray
     * @return swig int array
     */
    public static SWIGTYPE_p_int createSwigArray( int[] javaArray ) {
        SWIGTYPE_p_int result = Ode.new_intArray( javaArray.length );
        for ( int i = 0; i < javaArray.length; i++ ) {
            Ode.intArray_setitem( result, i, javaArray[i] );
        }
        return result;
    }

    /**
     * Create swig int array based on given java buffer. The buffer must already be positioned appropriately.
     *
     * @param buffer nio buffer to be copied
     * @param length size of the new swig array
     * @return swig int array
     */
    public static SWIGTYPE_p_int createSwigArray( IntBuffer buffer, int length ) {
        SWIGTYPE_p_int result = Ode.new_intArray( length );
        for ( int i = 0; i < length; i++ ) {
            Ode.intArray_setitem( result, i, buffer.get() );
        }
        return result;
    }

    /**
     * Create swig float array based on given java buffer. The buffer must already be positioned appropriately.
     *
     * @param buffer nio buffer to be copied
     * @param length size of the new swig array
     * @return swig float array
     */
    public static SWIGTYPE_p_float createSwigArray( FloatBuffer buffer, int length ) {
        SWIGTYPE_p_float result = Ode.new_floatArray( length );
        for ( int i = 0; i < length; i++ ) {
            Ode.floatArray_setitem( result, i, buffer.get() );
        }
        return result;
    }

    /**
     * Create java int array based on given swig array.
     *
     * @param swigArray
     * @param length
     * @return java int array
     */
    public static int[] createJavaArray( SWIGTYPE_p_int swigArray, int length ) {
        int[] result = new int[length];
        for ( int i = 0; i < length; i++ ) {
            result[i] = Ode.intArray_getitem( swigArray, i );
        }
        return result;
    }

}

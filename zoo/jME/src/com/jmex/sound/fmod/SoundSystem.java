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

/*
 * Created on 9 avr. 2005
 */
package com.jmex.sound.fmod;

import java.util.logging.Level;

import org.lwjgl.fmod3.FMOD;
import org.lwjgl.fmod3.FMODException;
import org.lwjgl.fmod3.FSound;

import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.util.LoggingSystem;
import com.jmex.sound.fmod.objects.Listener;
import com.jmex.sound.fmod.objects.MusicStream;
import com.jmex.sound.fmod.objects.Sample3D;
import com.jmex.sound.fmod.scene.Configuration;
import com.jmex.sound.fmod.scene.SoundNode;

/**
 * @author Arman
 */
public class SoundSystem {

    public static final int FREE_NODE_INDEX = -1;

    public static final int RENDER_MEHOD_PAUSE = 1;// TODO

    public static final int RENDER_MEHOD_STOP = 2;// TODO

    public static final int RENDER_MEHOD_MUTE = 3;// TODO

    public static final int OUTPUT_DEFAULT = 0;

    public static int DEFAULT_RENDER_METOD = 2;

    // WINDOWS
    public static final int OUTPUT_DSOUND = 1;

    public static final int OUTPUT_WINMM = 2;

    public static final int OUTPUT_ASIO = 3;

    // LINUX
    public static final int OUTPUT_OSS = 5;

    public static final int OUTPUT_ESD = 6;

    public static final int OUTPUT_ALSA = 7;

    // MAC
    public static final int OUTPUT_MAC = 8;

    private static Listener listener;

    private static Camera camera;

    private static SoundNode[] nodes;

    private static Sample3D[] sample3D;

    private static MusicStream[] stream;

    private static int OS_DETECTED;

    private static final int OS_LINUX = 1;

    private static final int OS_WINDOWS = 2;

    private static final int OS_MAC = 3;

    static {
        try {
            LoggingSystem.getLogger()
                    .log(Level.INFO, "DETECT OPERATING SYSTEM");
            detectOS();
            LoggingSystem.getLogger().log(Level.INFO, "CREATE FMOD");
            FMOD.create();
            LoggingSystem.getLogger().log(Level.INFO, "CREATE LISTENER");
            listener = new Listener();

        } catch (FMODException e) {
            e.printStackTrace();
        }
    }

    private static void detectOS() {
        String osName = System.getProperty("os.name");
        osName = osName.toUpperCase();
        if (osName.startsWith("LINUX"))
            OS_DETECTED = OS_LINUX;
        if (osName.startsWith("WINDOWS"))
            OS_DETECTED = OS_WINDOWS;
        if (osName.startsWith("MAC"))
            OS_DETECTED = OS_MAC;
    }

    /**
     * init the sound system by setting it's listener's position to the cameras
     * position
     * 
     * @param cam
     * @param outputMethod
     */
    public static void init(Camera cam, int outputMethod) {
        camera = cam;
        if (outputMethod == OUTPUT_DEFAULT) {
            outputMethod = OS_DETECTED;
        }
        switch (outputMethod) {
        case OS_LINUX:
            FSound.FSOUND_SetOutput(FSound.FSOUND_OUTPUT_ALSA);
            break;
        case OS_WINDOWS:
            FSound.FSOUND_SetOutput(FSound.FSOUND_OUTPUT_DSOUND);
            break;
        case OS_MAC:
            FSound.FSOUND_SetOutput(FSound.FSOUND_OUTPUT_MAC);
            break;

        }
        // FSound.FSOUND_SetDriver(0);
        FSound.FSOUND_SetMixer(FSound.FSOUND_MIXER_AUTODETECT);
        LoggingSystem.getLogger().log(Level.INFO, "INIT FSOUND 44100 1024 0");
        FSound.FSOUND_Init(44100, 1024, 0);
        FSound.FSOUND_3D_SetDistanceFactor(1.0f);
    }

    /**
     * Updates the geometric states of all nodes in the scene
     * 
     * @param time
     *            currently not used
     */
    public static void update(float time) {
        if (nodes == null)
            return;

        for (int a = 0; a < nodes.length; a++) {
            nodes[a].updateWorldData(time);
        }
        updateListener();

    }

    /**
     * Updates the geometric states of the given node in the scene
     * 
     * @param nodeName
     *            the node to update
     * @param time
     *            currently not used
     */
    public static void update(int nodeName, float time) {
        if (nodes == null)
            return;
        if (nodeName < 0 || nodeName >= nodes.length)
            return;
        nodes[nodeName].updateWorldData(time);
        updateListener();

    }

    /**
     * Draws all nodes in the scene
     * 
     * @param time
     *            currently not used
     */
    public static void draw() {
        if (nodes == null)
            return;
        for (int a = 0; a < nodes.length; a++) {
            nodes[a].draw();
        }
        FSound.FSOUND_Update();
    }

    /**
     * Draws the given node in the scene
     * 
     * @param nodeName
     *            the node to update
     * @param time
     *            currently not used
     */
    public static void draw(int nodeName) {
        if (nodes == null)
            return;
        if (nodeName < 0 || nodeName >= nodes.length)
            return;
        nodes[nodeName].draw();
        FSound.FSOUND_Update();
    }

    private static void updateListener() {
        if (camera != null) {
            listener.setPosition(camera.getLocation());
        }
        float[] orientation = listener.getOrientation();
        Vector3f dir = null;
        Vector3f up = null;
        if (camera != null) {
            dir = camera.getDirection();
            up = camera.getUp();
        } else if (dir == null) {
            dir = new Vector3f(0, 0, -1);
            up = new Vector3f(0, 1, 0);
        }
        orientation[0] = -dir.x;
        orientation[1] = dir.y;
        orientation[2] = dir.z;
        orientation[3] = up.x;
        orientation[4] = up.y;
        orientation[5] = up.z;
        listener.update();
        // FSound.FSOUND_Update();
    }

    /**
     * Get the "ears" of the sound system
     * 
     * @return
     */
    public static Camera getCamera() {
        return camera;
    }

    /**
     * Set the "ears" of the sound system
     * 
     * @param c
     */
    public static void setCamera(Camera c) {
        camera = c;
        updateListener();
    }

    /**
     * Creates a node ans return an integer as it's identifier.
     * 
     * @return the node identifier
     */
    public static int createSoundNode() {
        if (nodes == null) {
            nodes = new SoundNode[1];
            nodes[0] = new SoundNode();
            return 0;
        } 
            
        SoundNode[] tmp = new SoundNode[nodes.length];
        System.arraycopy(nodes, 0, tmp, 0, tmp.length);
        nodes = new SoundNode[tmp.length + 1];
        System.arraycopy(tmp, 0, nodes, 0, tmp.length);
        nodes[tmp.length] = new SoundNode();
        return tmp.length;
    }

    /**
     * Creates a 3D sample and returns an identifier for it
     * 
     * @param file
     *            the sample file name
     * @return the 3D sample identifier
     */
    public static int create3DSample(String file) {
        if (sample3D == null) {
            sample3D = new Sample3D[1];
            sample3D[0] = new Sample3D(listener, file, DEFAULT_RENDER_METOD);
            return 0;
        }
            
        Sample3D[] tmp = new Sample3D[sample3D.length];
        System.arraycopy(sample3D, 0, tmp, 0, tmp.length);
        sample3D = new Sample3D[tmp.length + 1];
        System.arraycopy(tmp, 0, sample3D, 0, tmp.length);
        sample3D[tmp.length] = new Sample3D(listener, file,
                DEFAULT_RENDER_METOD);
        return tmp.length;        
    }
    
    
    /**
     * <pre>
     * Get a new handle for the Sample in order to 
     * add the same sample in several nodes or
     * attach the same sound to several 3D objects
     * </pre>
     * @param sampleIdent the already created sample Ident
     * @return -1 if the sample ident does not exist
     */
    public static int cloneSample(int sampleIdent){
    	if(sample3D==null){
    		return -1;
    	}
    	if(sample3D !=null && sample3D.length<sampleIdent){
    		return -1;
    	}
    	
        Sample3D[] tmp=new Sample3D[sample3D.length];
    	System.arraycopy(sample3D, 0, tmp, 0, tmp.length);
    	sample3D=new Sample3D[tmp.length+1];
        System.arraycopy(tmp, 0, sample3D, 0, tmp.length);
        sample3D[tmp.length]=(Sample3D)sample3D[sampleIdent].clone();
        return tmp.length;
    }
    

    /**
     * Creates a Music stream and returns an identifier for it
     * 
     * @param file
     *            streaming file name
     * @param loadIntoMemory
     * @return the stream identifier
     */
    public static int createStream(String file, boolean loadIntoMemory) {
        if (stream == null) {
            stream = new MusicStream[1];
            stream[0] = new MusicStream(file, loadIntoMemory);
            return 0;
        } 
        
        MusicStream[] tmp = new MusicStream[stream.length];
        System.arraycopy(stream, 0, tmp, 0, tmp.length);
        stream = new MusicStream[tmp.length + 1];
        System.arraycopy(tmp, 0, stream, 0, tmp.length);
        stream[tmp.length] = new MusicStream(file, loadIntoMemory);
        return tmp.length;
    }

    /**
     * Checks if a stream is opened. If it is, this can be used to know that the
     * file is really a audio file
     * 
     * @param streamName
     * @return true if the stream is opened
     */
    public static boolean isStreamOpened(int streamName) {
        if (stream == null) {
            return false;
        } else if (streamName < 0 || streamName >= stream.length) {
            return false;
        } else {
            return stream[streamName].isOpened();
        }
    }

    /**
     * Get the length of the given stream in milliseconds
     * 
     * @param streamName
     * @return the stream length in millis
     */
    public static int getStreamLength(int streamName) {
        if (stream == null) {
            return -1;
        } else if (streamName < 0 || streamName >= stream.length) {
            return -1;
        } else {
            return stream[streamName].length();
        }
    }

    public static boolean playStream(int streamName) {
        if (stream == null) {
            return false;
        } else if (streamName < 0 || streamName >= stream.length) {
            return false;
        } else {
            return stream[streamName].play();
        }
    }

    public static boolean pauseStream(int streamName) {
        if (stream == null) {
            return false;
        } else if (streamName < 0 || streamName >= stream.length) {
            return false;
        } else {
            return stream[streamName].pause();
        }
    }

    public static void stopStream(int streamName) {
        if (stream == null) {
            return;
        } else if (streamName < 0 || streamName >= stream.length) {
            return;
        } else {
            stream[streamName].stop();
        }
    }

    /**
     * Sets the spatial position of a given sample
     * 
     * @param sample
     *            the sample identifier
     * @param x
     *            the x position of the sample
     * @param y
     *            the y position of the sample
     * @param z
     *            the z position of the sample
     */
    public static void setSamplePosition(int sample, float x, float y, float z) {
        if (sample3D == null) {
            return;
        } else if (sample < 0 || sample >= sample3D.length) {
            return;
        } else {
            sample3D[sample].setPosition(x, y, z);
        }
    }

    /**
     * Sets the velocity of a given sample
     * 
     * @param sample
     *            the sample identifier
     * @param x
     *            the x velocity of the sample
     * @param y
     *            the y velocity of the sample
     * @param z
     *            the z velocity of the sample
     */
    public static void setSampleVelocity(int sample, float x, float y, float z) {
        if (sample3D == null) {
            return;
        } else if (sample < 0 || sample >= sample3D.length) {
            return;
        } else {
            sample3D[sample].setVelocity(x, y, z);
        }
    }

    /**
     * Set the FX configuration of the given sample
     * 
     * @param sample
     *            sample the sample identifier
     * @param conf
     *            the config
     */
    public static void setSampleConfig(int sample, Configuration conf) {
        if (sample3D == null) {
            return;
        } else if (sample < 0 || sample >= sample3D.length) {
            return;
        } else {
            sample3D[sample].setConfiguration(conf);
        }
    }

    /**
     * Set the FX configuration of the given stream
     * 
     * @param sample
     *            stream the sample identifier
     * @param conf
     *            the config
     */
    public static void setStreamConfig(int streamName, Configuration conf) {
        if (stream == null) {
            return;
        } else if (streamName < 0 || streamName >= stream.length) {
            return;
        } else {
            stream[streamName].setConfiguration(conf);
        }
    }
    
    /**
     * Set the volume of the given stream: range from 0 to 255
     * 
     * @param sample
     *            stream the sample identifier
     * @param conf
     *            the config
     */
    public static void setStreamVolume(int streamName, int volume) {
        if (stream == null) {
            return;
        } else if (streamName < 0 || streamName >= stream.length) {
            return;
        } else {
            stream[streamName].setVolume(volume);
        }
    }

    /**
     * Sets the units from which the sample will stop playing
     * 
     * @param sample
     *            the sample identifier
     * @param dist
     *            the distance unit from which the sample will stop playing
     */
    public static void setSampleMaxAudibleDistance(int sample, int dist) {
        if (sample3D == null) {
            return;
        } else if (sample < 0 || sample >= sample3D.length) {
            return;
        } else {
            sample3D[sample].setMaxAudibleDistance(dist);
        }
    }

    public static void setSampleMinAudibleDistance(int sample, int dist) {
        if (sample3D == null) {
            return;
        } else if (sample < 0 || sample >= sample3D.length) {
            return;
        } else {
            sample3D[sample].setMinDistance(dist);
        }
    }

    /**
     * Adds a sample to the given node identifier
     * 
     * @param destNode
     * @param sample
     */
    public static void addSampleToNode(int sample, int destNode) {
        if (nodes == null) {
            return;
        } else if (sample3D == null) {
            return;
        } else if (destNode < 0 || destNode >= nodes.length) {
            return;
        } else if (sample < 0 || sample >= sample3D.length) {
            return;
        } else {
            nodes[destNode].attachChild(sample3D[sample]);
        }
    }

    /**
     * Binds an event to the given sample. The event number sould be a unique
     * id. Binding an event to sample will make it play only if the event is
     * fired on the container node
     * 
     * @param sample
     *            the sample to which the event will be bound
     * @param event
     *            the unique event number
     */
    public static void bindEventToSample(int sample, int event) {
        if (sample3D == null) {
            return;
        } else if (sample < 0 || sample >= sample3D.length) {
            return;
        } else {
            sample3D[sample].bindEvent(event);
        }
    }

    /**
     * Fires an event on all nodes
     * 
     * @param eventName
     *            the event to fire
     */
    public static void onEvent(int eventName) {
        if (nodes == null)
            return;
        for (int a = 0; a < nodes.length; a++) {
            nodes[a].onEvent(eventName);
        }

    }

    /**
     * 
     * @param nodeName
     * @param eventName
     */
    public static void onEvent(int nodeName, int eventName) {
        if (nodes == null)
            return;
        if (nodeName < 0 || nodeName >= nodes.length)
            return;
        nodes[nodeName].onEvent(eventName);

    }

    public static void setRolloffFactor(float rolloff) {
        FSound.FSOUND_3D_SetRolloffFactor(rolloff);
    }

    /**
     * Set the volume of the given sample
     * 
     * @param sample
     * @param volume
     */
    public static void setSampleVolume(int sample, int volume) {
        if (sample3D == null) {
            return;
        } else if (sample < 0 || sample >= sample3D.length) {
            return;
        } else {
            sample3D[sample].setVolume(volume);
        }
    }

    /**
     * Sets the volume of all samples attached to a given node.
     * 
     * @param node
     *            We change the volume of this nodes children.
     * @param volume
     *            The volume to set. Should be a value between 0 - 255.
     */
    public static void setNodeVolume(int node, float volume) {
        if (nodes == null)
            return;
        if (node < 0 || node >= nodes.length)
            return;
        for (int i = 0; i < nodes[node].getQuantity(); i++) {
            ((Sample3D) nodes[node].getChild(i)).setVolume((int) volume);
        }
    }

    /**
     * Sets the default 3D sample rendering method. While the 3d sample's max
     * audible distance is set the sound will respect the rendering method
     * specified here. If the pause method is selected the sample will pause if
     * the listener goes out of the max audible distance and continue to play
     * from where it was paused if the user comes into the audible sphere
     * defined with max audible distance. If the stop method is selected the
     * sound will stop and restart If the mute method is selected the sample
     * will continue to play
     * 
     * @param method
     *            the method to set, by default the method is
     *            SoundSystem.RENDER_METHOD_PAUSE possible values are
     *            SoundSystem.RENDER_MEHOD_MUTE, SoundSystem.RENDER_MEHOD_STOP
     *            and SoundSystem.RENDER_MEHOD_PAUSE
     */
    public static void setRenderMethod(final int method) {
        if (method != RENDER_MEHOD_MUTE || method != RENDER_MEHOD_PAUSE
                || method != RENDER_MEHOD_STOP)
            return;
        DEFAULT_RENDER_METOD = method;
    }

}

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
 * Created on 10 avr. 2005
 */
package com.jmex.sound.openAL.objects;

import java.net.URL;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.logging.Level;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

import com.jme.util.LoggingSystem;
import com.jmex.sound.openAL.objects.util.Buffer;
import com.jmex.sound.openAL.objects.util.SampleLoader;
import com.jmex.sound.openAL.scene.Configuration;
import com.jmex.sound.openAL.scene.SoundSpatial;

/**
 * @author Arman
 */
public class Sample3D extends SoundSpatial implements Cloneable{

    
    
    private int ray;
    private int min=1;
    private FloatBuffer position=BufferUtils.createFloatBuffer(3);
    private FloatBuffer velocity=BufferUtils.createFloatBuffer(3);
    private Buffer buffer=null;
    private boolean handlesEvent;
    
    float posx=0;
    
    public Sample3D(String file){     
        LoggingSystem.getLogger().log(Level.INFO,"Load file:"+file);
        buffer=SampleLoader.loadBuffer(file);
        generateSource();
    }
    
    public Sample3D(URL url){
        LoggingSystem.getLogger().log(Level.INFO,"Load file:"+url);
        buffer=SampleLoader.loadBuffer(url);
        generateSource();
    }
    
    private Sample3D(){
    	
    }
    
    public Sample3D(Listener listener, String file){        
        this(file);
        this.listener=listener;
    }
    
    public Sample3D(Listener listener, URL url){        
        this(url);
        this.listener=listener;
    }
    
    public void setConfiguration(Configuration conf){
        configuration=conf;
    }
    
    public void draw() {  
       if(handlesEvent) return;
       if (distance(listener.getPosition().x,
                listener.getPosition().y, 
                listener.getPosition().z,
                position.get(0),position.get(1), position.get(2)) > ray) {
            if(sourceNumber>=0){
                stop();
            }
        } else {
            if (!isPlaying()) {
                AL10.alSourcef(sourceNumber, AL10.AL_MAX_DISTANCE, ray);
                play();
            }
            
        }
    }
    
    public boolean play(){
        AL10.alSourcei(sourceNumber, AL10.AL_BUFFER, buffer.getBufferNumber());
        AL10.alSourcePlay(sourceNumber);
        return true;
    }
    
    public boolean pause(){
        AL10.alSourcePause(sourceNumber);
        return true;
    }
    
    public boolean stop(){
        AL10.alSourceStop(sourceNumber);
        return true;
    }
    
    public boolean isPlaying(){
        return sourceNumber >=0 && (AL10.alGetSourcei(sourceNumber, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING);
    }
    
    public void delete() {
        IntBuffer alSource = BufferUtils.createIntBuffer(1);
        alSource.put(sourceNumber);
        AL10.alDeleteSources(alSource);

    }
    
    
    public void setPosition(float x, float y, float z){
        position.clear();
        position.put(x);
        position.put(y);
        position.put(z);
        if(sourceNumber>=0){
            AL10.alSource3f(sourceNumber, AL10.AL_POSITION, position.get(0), position.get(1), position.get(2));
        } 
    }
    
    public void setVelocity(float x, float y, float z){
        AL10.alSource3f(sourceNumber, AL10.AL_VELOCITY, x, y, z);
        
    }
    
    public void setMinDistance(int min){
        this.min=min;
        
    }
    
    public void setVolume(float volume){
        AL10.alSourcef(sourceNumber, AL10.AL_GAIN, volume);
    }
    
    public void setMaxAudibleDistance(int max){
        ray=max;
        
    }
    
    private float distance(float ax, float ay, float az, float bx, float by, float bz){
        return (float)Math.sqrt(distanceSquared(ax, ay, az, bx,by, bz));
    }
    

    /**
     * <code>distanceSquared</code> returns the distance between two points,
     * with the distance squared. This allows for faster comparisons if relation
     * is important but actual distance is not.
     * @return the distance squared between two points.
     */
    private static float distanceSquared(float ax, float ay, float az, float bx, float by, float bz) {
        return ((ax - bx) * (ax - bx))
                + ((ay - by) * (ay - by))
                + ((az - bz) * (az - bz));
    }
    
    private void configure(){
        
    }
    
    
    private void generateSource() {
        IntBuffer alSources = BufferUtils.createIntBuffer(1);
        AL10.alGenSources(alSources);
        sourceNumber=alSources.get(0);
    }
    

    private int[] event;
    private int[] program;
    
    public void bindEvent(int eventNumber) {
        if (event == null) {
            event = new int[1];
            event[0] = eventNumber;
            handlesEvent=true;
            return;
        }
        int[] tmp = new int[event.length + 1];
        System.arraycopy(event, 0, tmp, 0, event.length);
        tmp[event.length] = eventNumber;
        event = tmp;        
    }
    
    /**
     * Used internally for firing an event on this sound playing object.
     * @return true if the event has been fired
     * and false if this sound does not "know" the event
     */
    public boolean fireEvent(int eventNumber) {
        if (event != null && (allowInterrupt || !isPlaying())) {
            for (int i = 0; i < event.length; i++) {
                if (event[i] == eventNumber) {
                    stop();
                    play();
                    return true;
                }
            }
        }
        return false;
    }


    public void setRolloffFactor( float rolloff ) {
        AL10.alSourcef(sourceNumber, AL10.AL_ROLLOFF_FACTOR, rolloff);
    }
    /**
     * Clones this sample without reloading the sample buffer
     */
    public Object clone(){
    	IntBuffer alSources = BufferUtils.createIntBuffer(1);
        AL10.alGenSources(alSources);
        Sample3D clone=new Sample3D();
        clone.buffer=this.buffer;
        clone.sourceNumber=alSources.get(0);
        clone.listener=this.listener;
    	return clone;
    }
    
}

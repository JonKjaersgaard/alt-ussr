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

/**
 * Created on Apr 19, 2005
 */
package com.jmex.sound.openAL.objects;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.jmex.sound.openAL.objects.util.StreamPlayer;
import com.jmex.sound.openAL.scene.Configuration;
import com.jmex.sound.openAL.scene.Playable;

public class MusicStream extends Playable{
    
    private ByteBuffer memoryData;
    private boolean opened=true;
    private boolean memory;
    private String streamFile;
    private Configuration configuration;
    
    
    public MusicStream(String file, boolean memoryLoad){
        this.streamFile=file;
        if(memoryLoad){
           
        }else{
            sourceNumber=StreamPlayer.getInstance().openStream(file);
        }
            
    }
    
    public MusicStream(URL file){
        this.streamFile=file.getFile();
        sourceNumber=StreamPlayer.getInstance().openStream(file);
    }
    
    public MusicStream(URL file, boolean memoryLoad){
        this.streamFile=file.getFile();
        if(memoryLoad){
            
        }else{
            sourceNumber=StreamPlayer.getInstance().openStream(file);
        }
    }


    public void setConfiguration(Configuration conf){
        configuration=conf;
    }
    
    public boolean play(){
       StreamPlayer.getInstance().play(sourceNumber);
       return true;
    }
    
    /**
     * Pause the stream
     * @return true if the stream is paused
     */
    public boolean pause(){
        return StreamPlayer.getInstance().pauseStream(sourceNumber);
    }
    
    /**
     * Stops the stream handled by this Music stream
     */
    public void stop(){
        StreamPlayer.getInstance().stopStream(sourceNumber);
    }
    
    public void close(){
    }
    
    /**
     * Get the playing status of this stream
     * @return true is the stream is playing
     */
    public boolean isPlaying(){
        return StreamPlayer.getInstance().isPlaying(sourceNumber);
    }
    
    
    public int length(){
        return (int)StreamPlayer.getInstance().length(sourceNumber);
    }
    
    /**
     * Reads the file into a ByteBuffer
     * @param filename Name of file to load
     * @return ByteBuffer containing file data
     */
    static protected ByteBuffer getData(String filename) {
        ByteBuffer buffer = null;
        try {
            BufferedInputStream bis = new BufferedInputStream(MusicStream.class.getClassLoader().getResourceAsStream(filename));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferLength = 4096;
            byte[] readBuffer = new byte[bufferLength];
            int read = -1;
            while ((read = bis.read(readBuffer, 0, bufferLength)) != -1) {
                baos.write(readBuffer, 0, read);
            }
            //done reading, close
            bis.close();
            // place it in a buffer
            buffer = ByteBuffer.allocateDirect(baos.size());
            buffer.order(ByteOrder.nativeOrder());
            buffer.put(baos.toByteArray());
            buffer.flip();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return buffer;
    }


    

    public boolean isOpened() {
        return sourceNumber !=-1;
    }

    /**
     * @return
     */
    public void loop(boolean flag) {
        StreamPlayer.getInstance().loopStream(sourceNumber, flag);
    }
    
    public void setVolume(float volume){
    	StreamPlayer.getInstance().setVolume(sourceNumber, volume);
    }

}

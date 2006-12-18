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
 * Created on 24 janv. 2004
 *
 */
package com.jmex.sound.openAL.objects.util;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;


/**
 * @author Arman Ozcelik
 * 
 */
public class Buffer{

	protected int bufferNumber;
	private ByteBuffer data;
	protected float playTime;

	protected Buffer(int bufferNumber) {
		this.bufferNumber = bufferNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jmex.sound.IBuffer#configure(java.nio.ByteBuffer, int, int)
	 */
	public void configure(ByteBuffer data, int format, int freq, float durationInSeconds) {
		this.data = data;
		AL10.alBufferData(bufferNumber, format, data, freq);
		playTime=durationInSeconds;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jmex.sound.IBuffer#delete()
	 */
	public void delete() {
		IntBuffer alBuffer = BufferUtils.createIntBuffer(1);//ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
		alBuffer.put(bufferNumber);
		alBuffer.rewind();
		AL10.alDeleteBuffers(alBuffer);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jmex.sound.IBuffer#getBitDepth()
	 */
	public int getBitDepth() {
		return AL10.alGetBufferi(bufferNumber, AL10.AL_BITS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jmex.sound.IBuffer#getNumChannels()
	 */
	public int getNumChannels() {
		return AL10.alGetBufferi(bufferNumber, AL10.AL_CHANNELS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jmex.sound.IBuffer#getData()
	 */
	public ByteBuffer getData() {
		return data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jmex.sound.IBuffer#getFrequency()
	 */
	public int getFrequency() {
		return AL10.alGetBufferi(bufferNumber, AL10.AL_FREQUENCY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jmex.sound.IBuffer#getSize()
	 */
	public int getSize() {
		return AL10.alGetBufferi(bufferNumber, AL10.AL_SIZE);
	}

	public int getBufferNumber() {
		return bufferNumber;
	}
	
	public float getDuration(){
		return playTime;
	}

    public static Buffer[] generateBuffers(int numOfBuffers) {
        Buffer[] result = new Buffer[numOfBuffers];
        IntBuffer alBuffers = BufferUtils.createIntBuffer(numOfBuffers);//ByteBuffer.allocateDirect(4
        // *
        // numOfBuffers).order(ByteOrder.nativeOrder()).asIntBuffer();
        AL10.alGenBuffers(alBuffers);
        for (int i = 0; i < numOfBuffers; i++) {
            result[i] = new Buffer(alBuffers.get(i));
        }
        return result;
    }
}
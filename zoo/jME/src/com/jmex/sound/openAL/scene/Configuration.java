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
 * Created on Apr 20, 2005
 */
package com.jmex.sound.openAL.scene;

public class Configuration {
    
    private boolean fxEnabled;
    private boolean echoEnabled;
    private boolean chorusEnabled;
    private boolean compressorEnabled;
    private boolean distorsionEnabled;
    private boolean flangerEnabled;
    private boolean gargleEnabled;
    private boolean I3DL2ReverbEnabled;
    private boolean eqEnabled;
    private boolean reverbEnabled;
    private boolean maxEnabled;
    private static final int MAX_FX=16;
    //FX Config    
    public static final int FX_CHORUS=0;
    public static final int FX_COMPRESSOR=1;
    public static final int FX_DISTORTION=2;
    public static final int FX_ECHO=3;
    public static final int FX_FLANGER=4;
    public static final int FX_GARGLE=5;
    public static final int FX_I3DL2REVERB=6;
    public static final int FX_PARAMEQ=7;
    public static final int FX_WAVES_REVERB=8;
    public static final int FX_MAX=9;
    


   public Configuration(){
        
    }
    
    public boolean isFxEnabled() {
        return fxEnabled;
    }

    public boolean isChorusEnabled() {
        return chorusEnabled;
    }
    
    public boolean isCompressorEnabled() {
        return compressorEnabled;
    }

    public boolean isDistorsionEnabled() {
        return distorsionEnabled;
    }
    
    public boolean isEchoEnabled() {
        return echoEnabled;
    }
    
    public boolean isEqEnabled() {
        return eqEnabled;
    }

    public boolean isFlangerEnabled() {
        return flangerEnabled;
    }

    public boolean isGargleEnabled() {
        return gargleEnabled;
    }

    public boolean isI3DL2ReverbEnabled() {
        return I3DL2ReverbEnabled;
    }

    public boolean isMaxEnabled() {
        return maxEnabled;
    }

    public boolean isReverbEnabled() {
        return reverbEnabled;
    }


    private float[] chorusParam=new float[7];
    /**
     * Fx chorus configuration params.
     * Setting those values will automatically enable fx on the sample or stream
     * @param WetDryMix Ratio of wet (processed) signal to dry (unprocessed) signal. Must be in the range from 0 through 100 (all wet).
     * @param Depth Percentage by which the delay time is modulated by the low-frequency oscillator, in hundredths of a percentage point. Must be in the range from 0 through 100. The default value is 25.
     * @param Feedback  Percentage of output signal to feed back into the effects input, in the range from -99 to 99. The default value is 0.
     * @param Frequency Frequency of the LFO, in the range from 0 to 10. The default value is 0.     * 
     * @param Waveform Waveform - Waveform of the LFO. Defined values are 0 triangle. 1 sine. By default, the waveform is a sine.
     * @param Delay Number of milliseconds the input is delayed before it is played back, in the range from 0 to 20. The default value is 0 ms
     * @param Phase Phase differential between left and right LFOs, in the range from 0 through 4. Possible values are defined as follows: 0 -180 degrees 1 - 90 degrees 2 0 degrees 3 90 degrees 4 180 degrees
     */
    public void setChorus(float WetDryMix, float Depth, float Feedback, float Frequency, int Waveform, float Delay, int Phase){ 
        fxEnabled=true;
        chorusEnabled=true;
        chorusParam[0]=WetDryMix;
        chorusParam[1]=Depth;
        chorusParam[2]=Feedback;
        chorusParam[3]=Frequency;
        chorusParam[4]=Waveform;
        chorusParam[5]=Delay;
        chorusParam[6]=Phase;
        
    }
    
    private float[] echoParam=new float[5];
    
    /**
     * 
     * @param WetDryMix Ratio of wet (processed) signal to dry (unprocessed) signal. Must be in the range from 0 through 100 (all wet).
     * @param Feedback  Percentage of output fed back into input, in the range from 0 through 100. The default value is 0.
     * @param LeftDelay  Delay for left channel, in milliseconds, in the range from 1 through 2000. The default value is 333 ms
     * @param RightDelay  Delay for right channel, in milliseconds, in the range from 1 through 2000. The default value is 333 ms.
     * @param PanDelay Value that specifies whether to swap left and right delays with each successive echo. The default value is FALSE, meaning no swap. Possible values are defined as TRUE or FALSE.
     */
    public void setEcho(float WetDryMix, float Feedback, float LeftDelay, float RightDelay, int PanDelay){
        echoParam[0]=WetDryMix;
        echoParam[1]=Feedback;
        echoParam[2]=LeftDelay;
        echoParam[3]=RightDelay;
        echoParam[4]=PanDelay;
    }
    
    private float[] compressorParam=new float[6];  
    /**
     * 
     * @param Gain  Output gain of signal after compression, in the range from -60 to 60. The default value is 0 dB.
     * @param Attack Time before compression reaches its full value, in the range from 0.01 to 500. The default value is 0.01 ms.
     * @param Release Speed at which compression is stopped after input drops below fThreshold, in the range from 50 to 3000. The default value is 50 ms.
     * @param Threshold  Point at which compression begins, in decibels, in the range from -60 to 0. The default value is -10 dB
     * @param Ratio Compression ratio, in the range from 1 to 100. The default value is 10, which means 10:1 compression.
     * @param Predelay Time after lThreshold is reached before attack phase is started, in milliseconds, in the range from 0 to 4. The default value is 0 ms.
     */
    public void setCompressor(float Gain, float Attack, float Release, float Threshold, float Ratio, float Predelay){
        fxEnabled=true;
        compressorEnabled=true;
        compressorParam[0]=Gain;
        compressorParam[1]=Attack;
        compressorParam[2]=Release;
        compressorParam[3]=Threshold;
        compressorParam[4]=Ratio;
        compressorParam[5]=Predelay;                
                
    }

    private float[] distorsionParam=new float[5];
    /**
     * 
     * @param Gain Amount of signal change after distortion, in the range from -60 through 0. The default value is 0 dB.
     * @param Edge Percentage of distortion intensity, in the range in the range from 0 through 100. The default value is 50 percent
     * @param PostEQCenterFrequency Center frequency of harmonic content addition, in the range from 100 through 8000. The default value is 4000 Hz
     * @param PostEQBandwidth Width of frequency band that determines range of harmonic content addition, in the range from 100 through 8000. The default value is 4000 Hz.
     * @param PreLowpassCutoff Filter cutoff for high-frequency harmonics attenuation, in the range from 100 through 8000. The default value is 4000 Hz
     */
    public void setDistortion(float Gain, float Edge, float PostEQCenterFrequency, float PostEQBandwidth, float PreLowpassCutoff){
        distorsionEnabled=true;
        fxEnabled=true;
        distorsionParam[0]=Gain;
        distorsionParam[1]=Edge;
        distorsionParam[2]=PostEQCenterFrequency;
        distorsionParam[3]=PostEQBandwidth;
        distorsionParam[4]=PreLowpassCutoff;
    }
    
    private float[] flangerParam=new float[7];
    /**
     * 
     * @param WetDryMix Ratio of wet (processed) signal to dry (unprocessed) signal. Must be in the range from 0 through 100 (all wet).
     * @param Depth Percentage by which the delay time is modulated by the low-frequency oscillator (LFO), in hundredths of a percentage point. Must be in the range from 0 through 100. The default value is 25.
     * @param Feedback Feedback - Percentage of output signal to feed back into the effects input, in the range from -99 to 99. The default value is 0.
     * @param Frequency Frequency of the LFO, in the range from 0 to 10. The default value is 0.
     * @param Waveform Waveform of the LFO. By default, the waveform is a sine. Possible values are defined as follows: 0 - Triangle. 1 - Sine.
     * @param Delay Number of milliseconds the input is delayed before it is played back, in the range from 0 to 4. The default value is 0 ms
     * @param Phase Phase differential between left and right LFOs, in the range from 0 through 4. Possible values are defined as follows: 0 -180 degrees 1 - 90 degrees 2 0 degrees 3 90 degrees 4 180 degrees 
     */
    public void setFlanger(float WetDryMix, float Depth, float Feedback, float Frequency, int Waveform, float Delay, int Phase){
        fxEnabled=true;
        flangerEnabled=true;
        flangerParam[0]=WetDryMix;
        flangerParam[1]=Depth;
        flangerParam[2]=Feedback;
        flangerParam[3]=Frequency;
        flangerParam[4]=Waveform;
        flangerParam[5]=Delay;
        flangerParam[6]=Phase;
    }
    
    private float[] gargleParam=new float[2];
    /**
     * 
     * @param RateHz Rate of modulation, in Hertz. Must be in the range from 1 through 1000
     * @param WaveShape Shape of the modulation wave. The following values are defined. 0 - Triangular wave. 1 - Square wave. 
     */
    public void setGargle(int RateHz, int WaveShape){
        fxEnabled=true;
        gargleEnabled=true;
        gargleParam[0]=RateHz;
        gargleParam[1]=WaveShape;
    }
    
    private float[] i3DLParam=new float[12];
    /**
     * 
     * @param Room Attenuation of the room effect, in millibels (mB), in the range from -10000 to 0. The default value is -1000 mB.
     * @param RoomHF Attenuation of the room high-frequency effect, in mB, in the range from -10000 to 0. The default value is 0 mB
     * @param RoomRolloffFactor  Rolloff factor for the reflected signals, in the range from 0 to 10. The default value is 0.0. The rolloff factor for the direct path is controlled by the listener
     * @param DecayTime Decay time, in seconds, in the range from .1 to 20. The default value is 1.49 seconds.
     * @param DecayHFRatio Ratio of the decay time at high frequencies to the decay time at low frequencies, in the range from 0.1 to 2. The default value is 0.83
     * @param Reflections Attenuation of early reflections relative to lRoom, in mB, in the range from -10000 to 1000. The default value is -2602 mB
     * @param ReflectionsDelay Delay time of the first reflection relative to the direct path, in seconds, in the range from 0 to 0.3. The default value is 0.007 seconds
     * @param Reverb Attenuation of late reverberation relative to lRoom, in mB, in the range from -10000 to 2000. The default value is 200 mB
     * @param ReverbDelay  Time limit between the early reflections and the late reverberation relative to the time of the first reflection, in seconds, in the range from 0 to 0.1. The default value is 0.011 seconds
     * @param Diffusion Echo density in the late reverberation decay, in percent, in the range from 0 to 100. The default value is 100.0 percent
     * @param Density Modal density in the late reverberation decay, in percent, in the range from 0 to 100. The default value is 100.0 percent
     * @param HFReference Reference high frequency, in hertz, in the range from 20 to 20000. The default value is 5000.0 Hz
     */
    public void setI3DL2Reverb(int Room, int RoomHF, float RoomRolloffFactor, float DecayTime, float DecayHFRatio, int Reflections, float ReflectionsDelay, int Reverb, float ReverbDelay, float Diffusion, float Density, float HFReference){
        fxEnabled=true;
        I3DL2ReverbEnabled=true;    
        i3DLParam[0]=Room;
        i3DLParam[1]=RoomHF;
        i3DLParam[2]=RoomRolloffFactor;
        i3DLParam[3]=DecayTime;
        i3DLParam[4]=DecayHFRatio;
        i3DLParam[5]=Reflections;
        i3DLParam[6]=ReflectionsDelay;
        i3DLParam[7]=Reverb;
        i3DLParam[8]=ReverbDelay;
        i3DLParam[9]=Diffusion;
        i3DLParam[10]=Density;
        i3DLParam[11]=HFReference;
        
    }
    
    private float[] eqParam=new float[3];
    /**
     * 
     * @param Center Center frequency, in hertz, in the range from 80 to 16000. This value cannot exceed one-third of the frequency of the buffer. Default is 8000
     * @param Bandwidth Bandwidth, in semitones, in the range from 1 to 36. Default is 12
     * @param Gain Gain, in the range from -15 to 15. Default is 0. 
     */
    public void setEqParam(float Center, float Bandwidth, float Gain){
        fxEnabled=true;
        eqEnabled=true;
        eqParam[0]=Center;
        eqParam[1]=Bandwidth;
        eqParam[2]=Bandwidth;
    }
    
    
    private float[] reverbParam=new float[4];
    /**
     * 
     * @param InGain Input gain of signal, in decibels (dB), in the range from -96 through 0. The default value is 0 dB.
     * @param ReverbMix Reverb mix, in dB, in the range from -96 through 0. The default value is 0 dB.
     * @param ReverbTime Reverb time, in milliseconds, in the range from .001 through 3000. The default value is 1000.
     * @param HighFreqRTRatio In the range from .001 through .999. The default value is 0.001
     */
    public void setReverb(float InGain, float ReverbMix, float ReverbTime, float HighFreqRTRatio){
        fxEnabled=true;
        reverbEnabled=true;
        reverbParam[0]=InGain;
        reverbParam[0]=ReverbMix;
        reverbParam[0]=ReverbTime;
        reverbParam[0]=HighFreqRTRatio;
        
    }
    
    public float[] getChorusParams(){
        return chorusParam;
    }
    
    public float[] getCompressorParams(){
        return compressorParam;
    }
    
    public float[] getDistorsionParams(){
        return distorsionParam;
    }
    
    public float[] getEchoParams(){
        return echoParam;
    }
    
    public float[] getFlangerParams(){
        return flangerParam;
    }
    
    public float[] getGargleParams(){
        return gargleParam;
    }
    
    public float[] getI3DLParams(){
        return i3DLParam;
    }
    
    public float[] getEqParams(){
        return eqParam;
    }

    public float[] getReverbParams(){
        return reverbParam;
    }
    
    
}

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

package com.jmex.effects.particles;

import java.io.IOException;
import java.util.ArrayList;

import com.jme.math.FastMath;
import com.jme.scene.Controller;
import com.jme.scene.Spatial;
import com.jme.util.export.InputCapsule;
import com.jme.util.export.JMEExporter;
import com.jme.util.export.JMEImporter;
import com.jme.util.export.OutputCapsule;

/**
 * <code>ParticleController</code> controls and maintains the parameters of a
 * ParticleGeometry particle system over time.
 * 
 * @author Joshua Slack
 * @version $Id: ParticleController.java,v 1.12 2007/01/03 15:51:26 nca Exp $
 */
public class ParticleController extends Controller {

    private static final long serialVersionUID = 1L;

    private ParticleGeometry particles;
    private int particlesToCreate = 0;
    private float releaseVariance;
    private float currentTime;
    private float prevTime;
    private float releaseParticles;
    private float timePassed;
    private float precision;
    private boolean controlFlow;

    private int iterations;
    private ArrayList<ParticleInfluence> influences;
    protected ArrayList<ParticleControllerListener> listeners;

    public ParticleController() {}
    
    /**
     * ParticleManager constructor
     * 
     * @param particleMesh
     *            Target ParticleGeometry to act upon.
     */
    public ParticleController(ParticleGeometry particleMesh) {
        this.particles = particleMesh;

        setMinTime(0);
        setMaxTime(Float.MAX_VALUE);
        setRepeatType(Controller.RT_WRAP);
        setSpeed(1.0f);

        releaseVariance = 0;
        controlFlow = false;
        precision = .01f; // 10ms

        particleMesh.updateRotationMatrix();
        warmUp(60);
    }

    /**
     * Update the particles managed by this manager. If any particles are "dead"
     * recreate them at the origin position (which may be a point, line or
     * rectangle.) See com.jme.scene.Controller.update(float)
     * 
     * @param secondsPassed
     *            float
     */
    public void update(float secondsPassed) {
        // Only update if active
        if (isActive()) {

            // Add time and unless we have more than precision time passed
            // since last real update, do nothing
            currentTime += secondsPassed * getSpeed();
            timePassed = currentTime - prevTime;
            if (timePassed < precision * getSpeed()) {
                return;
            }

            // We are actually going to do a real update,
            // so this is our new previous time
            prevTime = currentTime;

            // Update the current rotation matrix if needed.
            particles.updateRotationMatrix();

            // If we are in the time window where this controller is active
            // (defaults to 0 to Float.MAX_VALUE for ParticleController)
            if (currentTime >= getMinTime() && currentTime <= getMaxTime()) {

                // If we are controlling the flow (ie the rate of particle spawning.)
                if (controlFlow) {
                    // Release a number of particles based on release rate,
                    // timePassed (already scaled for speed) and variance. This
                    // is added to any current value Note this is a float value,
                    // so we will keep adding up partial particles
                    
                    releaseParticles += (particles.getReleaseRate() *
                        timePassed * (1.0f + releaseVariance *
                            (FastMath.nextRandomFloat() - 0.5f)));

                    // Try to create all "whole" particles we have added up
                    particlesToCreate = (int) releaseParticles;
                    
                    // If we have any whole particles, then subtract them from
                    // releaseParticles
                    if (particlesToCreate > 0)
                        releaseParticles -= particlesToCreate;
                    
                    // Make sure particlesToCreate is not negative
                    else
                        particlesToCreate = 0;
                }

                particles.updateInvScale();

                // If we have any influences, prepare them all
                if (influences != null) {
                    for (ParticleInfluence influence : influences) {
                        influence.prepare(particles);
                    }
                }
                
                // Track particle index
                int i = 0;
                
                // Track whether the whole set of particles is "dead" - if any
                // particles are still alive, this will be set to false
                boolean dead = true;
                
                // opposite of above boolean, but tracked seperately
                boolean anyAlive = false;
                
                // i is index through all particles
                while (i < particles.getNumParticles()) {
                    // Current particle
                    Particle p = particles.getParticle(i);
                    
                    // If we have influences and particle is alive
                    if (influences != null && p.getStatus() == Particle.ALIVE) {
                        // Apply each enabled influence to the current particle
                        for (int x = 0; x < influences.size(); x++) {
                            ParticleInfluence inf = influences.get(x);
                            if (inf.isEnabled())
                                inf.apply(timePassed, p, i);
                        }
                    }
                        

                    // Update and check the particle.
                    // If this returns true, indicating particle is ready to be
                    // reused, we may reuse it. Do so if we are not using
                    // control flow, OR we intend to create particles based on
                    // control flow count calculated above
                    boolean reuse = p.updateAndCheck(timePassed);
                    if (reuse && (!controlFlow || particlesToCreate > 0)) {
                        
                        // Don't recreate the particle if it is dead, and we are clamped
                        if (p.getStatus() == Particle.DEAD
                                && getRepeatType() == RT_CLAMP) {
                            ;

                        // We plan to reuse the particle
                        } else {
                            // Not all particles are dead (this one will be reused)
                            dead = false;
                            
                            // If we are doing flow control, decrement
                            // particlesToCreate, since we are about to create
                            // one
                            if (controlFlow) {
                                particlesToCreate--;
                            }

                            // Recreate the particle
                            p.recreateParticle(particles.getRandomLifeSpan());
                            p.setStatus(Particle.ALIVE);
                            particles.initParticleLocation(i);
                            particles.resetParticleVelocity(i);
                            p.updateVerts(null);
                        }

                    } else if (!reuse || (controlFlow && particles.getReleaseRate() > 0)) {
                        // The particle wasn't dead, or we expect more particles
                        // later, so we're not dead!
                        dead = false;
                    }

                    // Check for living particles so we know when to update our boundings.
                    if (p.getStatus() == Particle.ALIVE) {
                        anyAlive = true;
                    }
                    
                    // Next particle
                    i++;
                }

                // If we are dead, deactivate and tell our listeners
                if (dead) {
                    setActive(false);
                    if (listeners != null && listeners.size() > 0) {
                        for (ParticleControllerListener listener : listeners) {
                            listener.onDead(particles);
                        }
                    }
                }

                // If we have a bound and any live particles, update it
                if (particles.getBatch(0).getModelBound() != null && anyAlive) {
                    particles.updateModelBound();
                    particles.updateWorldBoundManually();
                }
            }
        }
    }

    /**
     * Get how soon after the last update the manager will send updates to the
     * particles.
     * 
     * @return The precision.
     */
    public float getPrecision() {
        return precision;
    }

    /**
     * Set how soon after the last update the manager will send updates to the
     * particles. Defaults to .01f (10ms)<br>
     * <br>
     * This means that if an update is called every 2ms (e.g. running at 500
     * FPS) the particles position and stats will be updated every fifth frame
     * with the elapsed time (in this case, 10ms) since previous update.
     * 
     * @param precision
     *            in seconds
     */
    public void setPrecision(float precision) {
        this.precision = precision;
    }

    /**
     * Get the variance possible on the release rate. 0.0f = no variance 0.5f =
     * between releaseRate / 2f and 1.5f * releaseRate
     * 
     * @return release variance as a percent.
     */
    public float getReleaseVariance() {
        return releaseVariance;
    }

    /**
     * Set the variance possible on the release rate.
     * 
     * @param variance
     *            release rate +/- variance as a percent (eg. .5 = 50%)
     */
    public void setReleaseVariance(float variance) {
        this.releaseVariance = variance;
    }

    /**
     * Does this manager regulate the particle flow?
     * 
     * @return true if this manager regulates how many particles per sec are
     *         emitted.
     */
    public boolean isControlFlow() {
        return controlFlow;
    }

    /**
     * Set the regulate flow property on the manager.
     * 
     * @param regulate
     *            regulate particle flow.
     */
    public void setControlFlow(boolean regulate) {
        this.controlFlow = regulate;
    }

    /**
     * Get the Spatial that holds all of the particle information for display.
     * 
     * @return Spatial holding particle information.
     */
    public Spatial getParticles() {
        return particles;
    }

    /**
     * Return the number this manager has warmed up
     * 
     * @return int
     */
    public int getIterations() {
        return iterations;
    }

    /**
     * Sets the iterations for the warmup and calls warmUp with the number of
     * iterations as the argument
     * 
     * @param iterations
     */
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    /**
     * Add an external influence to this particle controller.
     * 
     * @param influence
     *            ParticleInfluence
     */
    public void addInfluence(ParticleInfluence influence) {
        if (influences == null) influences = new ArrayList<ParticleInfluence>(1);
        influences.add(influence);
    }

    /**
     * Remove an influence from this particle controller.
     * 
     * @param influence
     *            ParticleInfluence
     * @return true if found and removed.
     */
    public boolean removeInfluence(ParticleInfluence influence) {
        if (influences == null) return false;
        return influences.remove(influence);
    }
    
    /**
     * Returns the list of influences acting on this particle controller.
     * 
     * @return ArrayList
     */
    public ArrayList<ParticleInfluence> getInfluences() {
        return influences;
    }
    
    public void clearInfluences() {
        if (influences != null)
            influences.clear();
    }

    /**
     * Subscribe a listener to receive mouse events. Enable event generation.
     *
     * @param listener to be subscribed
     */
    public void addListener( ParticleControllerListener listener ) {
        if ( listeners == null ) {
            listeners = new ArrayList<ParticleControllerListener>();
        }

        listeners.add( listener );
    }

    /**
     * Unsubscribe a listener. Disable event generation if no more listeners.
     *
     * @param listener to be unsuscribed
     * @see #addListener(ParticleControllerListener)
     */
    public void removeListener( ParticleControllerListener listener ) {
        if ( listeners != null ) {
            listeners.remove( listener );
        }
    }

    /**
     * Remove all listeners and disable event generation.
     */
    public void removeListeners() {
        if ( listeners != null ) {
            listeners.clear();
        }
    }

    /**
     * Check if a listener is allready added to this ParticleController
     * @param listener listener to check for
     * @return true if listener is contained in the listenerlist
     */
    public boolean containsListener( ParticleControllerListener listener ) {
        if ( listeners != null ) {
            return listeners.contains( listener );
        }
        return false;
    }

    /**
     * Get all added ParticleController listeners
     * @return ArrayList of listeners added to this ParticleController
     */
    public ArrayList<ParticleControllerListener> getListeners() {
        return listeners;
    }

    /**
     * Runs the update method of this particle manager for iteration seconds
     * with an update every .1 seconds (IE <code>iterations</code> * 10
     * update(.1f) calls). This is used to "warm up" and get the particle
     * manager going.
     * 
     * @param iterations
     *            The number of iterations to warm up.
     */
    public void warmUp(int iterations) {
        iterations *= 10;
        for (int i = iterations; --i >= 0;)
            update(.1f);
    }
    
    public void write(JMEExporter e) throws IOException {
        super.write(e);
        OutputCapsule capsule = e.getCapsule(this);
        capsule.write(particles, "particleMesh", null);
        capsule.write(releaseVariance, "releaseVariance", 0);
        capsule.write(precision, "precision", 0);
        capsule.write(controlFlow, "controlFlow", false);
        capsule.write(iterations, "iterations", 0);
        capsule.writeSavableArrayList(influences, "influences", null);
    }

    @SuppressWarnings("unchecked")
    public void read(JMEImporter e) throws IOException {
        super.read(e);
        InputCapsule capsule = e.getCapsule(this);
        particles = (ParticleGeometry)capsule.readSavable("particleMesh", null);
        releaseVariance = capsule.readFloat("releaseVariance", 0);
        precision = capsule.readFloat("precision", 0);
        controlFlow = capsule.readBoolean("controlFlow", false);
        iterations = capsule.readInt("iterations", 0);
        influences = capsule.readSavableArrayList("influences", null);
    }
}

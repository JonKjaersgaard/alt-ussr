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
package org.odejava.collision;

import java.nio.FloatBuffer;
import java.nio.LongBuffer;

import com.jme.math.Vector3f;

/**
 * <p/>
 * This is an extended version of standard Contact class. This solution is
 * faster on all occasions, but real performance gains occur when iterating
 * over a large set of contacts (several hundreds per step). Transfers contact
 * data from ByteBuffers to local arrays in a single ByteBuffer method call.
 * Commit writes local arrays back to ByteBuffers in a single ByteBuffer method
 * call.
 * </p>
 * <p/>
 * Note: when iterating collision data in a tight loop, avoid creating any
 * objects (e.g. Vector3f, Quaternion4f), this can seriously affect your
 * simulation performance.
 * </p>
 * <p/>
 * Created 13.03.2004 (dd.mm.yyyy)
 *
 * @author Jani Laakso E-mail: jani.laakso@itmill.com
 *         <p/>
 *         see http://odejava.dev.java.net
 */
public class BulkContact extends Contact {
    // Contact count varies on each step
    int contactCount;
    // Maximum amout of contacts in any time of simulation
    public static final int ARRAY_SIZE = 1500;
    // Preallocated local arrays
    long[] longArray = new long[INTBUF_CHUNK_SIZE * ARRAY_SIZE];
    float[] floatArray = new float[FLOATBUF_CHUNK_SIZE * ARRAY_SIZE];

    /**
     * @param longBuf
     * @param floatBuf
     */
    public BulkContact( LongBuffer longBuf, FloatBuffer floatBuf ) {
        super( longBuf, floatBuf );
    }

    /**
     * Bulk load ByteBuffer data to local arrays on a single call. This is
     * required before you start iterating your contact data. Call e.g.
     * bulkContact.load(collision.getContactCount());
     */
    public void load( int contactCount ) {
        this.contactCount = contactCount;
        // Copy ByteBuffers to local arrays
        longBuf.rewind();
        floatBuf.rewind();

        longBuf.get( longArray, 0, INTBUF_CHUNK_SIZE * contactCount );
        floatBuf.get( floatArray, 0, FLOATBUF_CHUNK_SIZE * contactCount );
    }

    /**
     * Commit local arrays back to ByteBuffers in a single call. This is
     * required before calling Collision.applyContacts(), if local arrays are
     * changed.
     */
    public void commit() {
        longBuf.rewind();
        floatBuf.rewind();

        // Copy local arrays back to ByteBuffers
        longBuf.put( longArray, 0, INTBUF_CHUNK_SIZE * contactCount );
        floatBuf.put( floatArray, 0, FLOATBUF_CHUNK_SIZE * contactCount );
    }

    /**
     * Ignore contact so it does not affect to simulation. Note: if you wish to
     * ignore certain geom <->geom collisions then use categoryBits and
     * collideBits instead, that is a lot faster.
     *
     * @param idx The index of the contact to be ignored
     */
    public void ignoreContact( int idx ) {
        setGeomID1( 0, idx );
        setGeomID2( 0, idx );
    }

    @Override
    public long getGeomID1() {
        return longArray[index * INTBUF_CHUNK_SIZE + GEOM_ID1];
    }

    @Override
    public long getGeomID1( int idx ) {
        return longArray[idx * INTBUF_CHUNK_SIZE + GEOM_ID1];
    }

    @Override
    public void setGeomID1( long id ) {
        longArray[index * INTBUF_CHUNK_SIZE + GEOM_ID1] = id;
    }

    @Override
    public void setGeomID1( long id, int idx ) {
        longArray[idx * INTBUF_CHUNK_SIZE + GEOM_ID1] = id;
    }

    @Override
    public long getGeomID2() {
        return longArray[index * INTBUF_CHUNK_SIZE + GEOM_ID2];
    }

    @Override
    public long getGeomID2( int idx ) {
        return longArray[idx * INTBUF_CHUNK_SIZE + GEOM_ID2];
    }

    @Override
    public void setGeomID2( long id ) {
        longArray[index * INTBUF_CHUNK_SIZE + GEOM_ID2] = id;
    }

    @Override
    public void setGeomID2( long id, int idx ) {
        longArray[idx * INTBUF_CHUNK_SIZE + GEOM_ID2] = id;
    }

    @Override
    public long getBodyID1() {
        return longArray[index * INTBUF_CHUNK_SIZE + BODY_ID1];
    }

    @Override
    public long getBodyID1( int idx ) {
        return longArray[idx * INTBUF_CHUNK_SIZE + BODY_ID1];
    }

    @Override
    public void setBodyID1( long id ) {
        longArray[index * INTBUF_CHUNK_SIZE + BODY_ID1] = id;
    }

    @Override
    public void setBodyID1( long id, int idx ) {
        longArray[idx * INTBUF_CHUNK_SIZE + BODY_ID1] = id;
    }

    @Override
    public long getBodyID2() {
        return longArray[index * INTBUF_CHUNK_SIZE + BODY_ID2];
    }

    @Override
    public long getBodyID2( int idx ) {
        return longArray[idx * INTBUF_CHUNK_SIZE + BODY_ID2];
    }

    @Override
    public void setBodyID2( long id ) {
        longArray[index * INTBUF_CHUNK_SIZE + BODY_ID2] = id;
    }

    @Override
    public void setBodyID2( long id, int idx ) {
        longArray[idx * INTBUF_CHUNK_SIZE + BODY_ID2] = id;
    }

    /**
     * Note, if mode = -1 then default surface parameter values are used. You
     * can set default surface parameters through Collision class.
     *
     * @return mode of surface contact
     */
    @Override
    public long getMode() {
        return longArray[index * INTBUF_CHUNK_SIZE + MODE];
    }

    /**
     * Note, if mode = -1 then default surface parameter values are used. You
     * can set default surface parameters through Collision class.
     *
     * @return mode of surface contact
     */
    @Override
    public long getMode( int idx ) {
        return longArray[idx * INTBUF_CHUNK_SIZE + MODE];
    }

    /**
     * Note, if mode = -1 then default surface parameter values are used. You
     * can set default surface parameters through Collision class.
     *
     * @param mode of surface contact
     */
    @Override
    public void setMode( int mode ) {
        longArray[index * INTBUF_CHUNK_SIZE + MODE] = mode;
    }

    /**
     * Note, if mode = -1 then default surface parameter values are used. You
     * can set default surface parameters through Collision class.
     *
     * @param mode of surface contact
     */
    @Override
    public void setMode( int mode, int idx ) {
        longArray[idx * INTBUF_CHUNK_SIZE + MODE] = mode;
    }

    //
    // floatBuffer methods
    //

    @Override
    public Vector3f getPosition( Vector3f position ) {
        position.x = floatArray[index * FLOATBUF_CHUNK_SIZE + POSITION];
        position.y = floatArray[index * FLOATBUF_CHUNK_SIZE + POSITION + 1];
        position.z = floatArray[index * FLOATBUF_CHUNK_SIZE + POSITION + 2];
        return position;
    }

    @Override
    public void getPosition( Vector3f position, int idx ) {
        position.x = floatArray[idx * FLOATBUF_CHUNK_SIZE + POSITION];
        position.y = floatArray[idx * FLOATBUF_CHUNK_SIZE + POSITION + 1];
        position.z = floatArray[idx * FLOATBUF_CHUNK_SIZE + POSITION + 2];
    }

    @Override
    public void getPosition( float[] position ) {
        position[0] = floatArray[index * FLOATBUF_CHUNK_SIZE + POSITION];
        position[1] = floatArray[index * FLOATBUF_CHUNK_SIZE + POSITION + 1];
        position[2] = floatArray[index * FLOATBUF_CHUNK_SIZE + POSITION + 2];
    }

    @Override
    public void getPosition( float[] position, int idx ) {
        position[0] = floatArray[idx * FLOATBUF_CHUNK_SIZE + POSITION];
        position[1] = floatArray[idx * FLOATBUF_CHUNK_SIZE + POSITION + 1];
        position[2] = floatArray[idx * FLOATBUF_CHUNK_SIZE + POSITION + 2];
    }

    @Override
    public float[] getPosition() {
        float[] position = new float[3];
        position[0] = floatArray[index * FLOATBUF_CHUNK_SIZE + POSITION];
        position[1] = floatArray[index * FLOATBUF_CHUNK_SIZE + POSITION + 1];
        position[2] = floatArray[index * FLOATBUF_CHUNK_SIZE + POSITION + 2];
        return position;
    }

    @Override
    public float[] getPosition( int idx ) {
        float[] position = new float[3];
        position[0] = floatArray[idx * FLOATBUF_CHUNK_SIZE + POSITION];
        position[1] = floatArray[idx * FLOATBUF_CHUNK_SIZE + POSITION + 1];
        position[2] = floatArray[idx * FLOATBUF_CHUNK_SIZE + POSITION + 2];
        return position;
    }

    @Override
    public void setPosition( float[] position ) {
        floatArray[index * FLOATBUF_CHUNK_SIZE + POSITION] = position[0];
        floatArray[index * FLOATBUF_CHUNK_SIZE + POSITION + 1] = position[1];
        floatArray[index * FLOATBUF_CHUNK_SIZE + POSITION + 2] = position[2];
    }

    @Override
    public void setPosition( float[] position, int idx ) {
        floatArray[idx * FLOATBUF_CHUNK_SIZE + POSITION] = position[0];
        floatArray[idx * FLOATBUF_CHUNK_SIZE + POSITION + 1] = position[1];
        floatArray[idx * FLOATBUF_CHUNK_SIZE + POSITION + 2] = position[2];
    }

    @Override
    public Vector3f getNormal( Vector3f normal ) {
        normal.x = floatArray[index * FLOATBUF_CHUNK_SIZE + NORMAL];
        normal.y = floatArray[index * FLOATBUF_CHUNK_SIZE + NORMAL + 1];
        normal.z = floatArray[index * FLOATBUF_CHUNK_SIZE + NORMAL + 2];
        return normal;
    }

    @Override
    public void getNormal( Vector3f normal, int idx ) {
        normal.x = floatArray[idx * FLOATBUF_CHUNK_SIZE + NORMAL];
        normal.y = floatArray[idx * FLOATBUF_CHUNK_SIZE + NORMAL + 1];
        normal.z = floatArray[idx * FLOATBUF_CHUNK_SIZE + NORMAL + 2];
    }

    @Override
    public void getNormal( float[] normal ) {
        normal[0] = floatArray[index * FLOATBUF_CHUNK_SIZE + NORMAL];
        normal[1] = floatArray[index * FLOATBUF_CHUNK_SIZE + NORMAL + 1];
        normal[2] = floatArray[index * FLOATBUF_CHUNK_SIZE + NORMAL + 2];
    }

    @Override
    public void getNormal( float[] normal, int idx ) {
        normal[0] = floatArray[idx * FLOATBUF_CHUNK_SIZE + NORMAL];
        normal[1] = floatArray[idx * FLOATBUF_CHUNK_SIZE + NORMAL + 1];
        normal[2] = floatArray[idx * FLOATBUF_CHUNK_SIZE + NORMAL + 2];
    }

    @Override
    public float[] getNormal() {
        float[] normal = new float[3];
        normal[0] = floatArray[index * FLOATBUF_CHUNK_SIZE + NORMAL];
        normal[1] = floatArray[index * FLOATBUF_CHUNK_SIZE + NORMAL + 1];
        normal[2] = floatArray[index * FLOATBUF_CHUNK_SIZE + NORMAL + 2];
        return normal;
    }

    @Override
    public float[] getNormal( int idx ) {
        float[] normal = new float[3];
        normal[0] = floatArray[idx * FLOATBUF_CHUNK_SIZE + NORMAL];
        normal[1] = floatArray[idx * FLOATBUF_CHUNK_SIZE + NORMAL + 1];
        normal[2] = floatArray[idx * FLOATBUF_CHUNK_SIZE + NORMAL + 2];
        return normal;
    }

    @Override
    public void setNormal( float[] normal ) {
        floatArray[index * FLOATBUF_CHUNK_SIZE + NORMAL] = normal[0];
        floatArray[index * FLOATBUF_CHUNK_SIZE + NORMAL + 1] = normal[1];
        floatArray[index * FLOATBUF_CHUNK_SIZE + NORMAL + 2] = normal[2];
    }

    @Override
    public void setNormal( float[] normal, int idx ) {
        floatArray[idx * FLOATBUF_CHUNK_SIZE + NORMAL] = normal[0];
        floatArray[idx * FLOATBUF_CHUNK_SIZE + NORMAL + 1] = normal[1];
        floatArray[idx * FLOATBUF_CHUNK_SIZE + NORMAL + 2] = normal[2];
    }

    @Override
    public float getDepth() {
        return floatArray[index * FLOATBUF_CHUNK_SIZE + DEPTH];
    }

    @Override
    public float getDepth( int idx ) {
        return floatArray[idx * FLOATBUF_CHUNK_SIZE + DEPTH];
    }

    @Override
    public void setDepth( float depth ) {
        floatArray[index * FLOATBUF_CHUNK_SIZE + DEPTH] = depth;
    }

    @Override
    public void setDepth( float depth, int idx ) {
        floatArray[idx * FLOATBUF_CHUNK_SIZE + DEPTH] = depth;
    }

    @Override
    public void getFdir1( float[] fdir1 ) {
        fdir1[0] = floatArray[index * FLOATBUF_CHUNK_SIZE + FDIR1];
        fdir1[1] = floatArray[index * FLOATBUF_CHUNK_SIZE + FDIR1 + 1];
        fdir1[2] = floatArray[index * FLOATBUF_CHUNK_SIZE + FDIR1 + 2];
    }

    @Override
    public void getFdir1( float[] fdir1, int idx ) {
        fdir1[0] = floatArray[idx * FLOATBUF_CHUNK_SIZE + FDIR1];
        fdir1[1] = floatArray[idx * FLOATBUF_CHUNK_SIZE + FDIR1 + 1];
        fdir1[2] = floatArray[idx * FLOATBUF_CHUNK_SIZE + FDIR1 + 2];
    }

    @Override
    public void setFdir1( float[] fdir1 ) {
        floatArray[index * FLOATBUF_CHUNK_SIZE + FDIR1] = fdir1[0];
        floatArray[index * FLOATBUF_CHUNK_SIZE + FDIR1 + 1] = fdir1[1];
        floatArray[index * FLOATBUF_CHUNK_SIZE + FDIR1 + 2] = fdir1[2];
    }

    @Override
    public void setFdir1( Vector3f fdir1 ) {
        floatArray[index * FLOATBUF_CHUNK_SIZE + FDIR1] = fdir1.x;
        floatArray[index * FLOATBUF_CHUNK_SIZE + FDIR1 + 1] = fdir1.y;
        floatArray[index * FLOATBUF_CHUNK_SIZE + FDIR1 + 2] = fdir1.z;
    }

    @Override
    public void setFdir1( float[] fdir1, int idx ) {
        floatArray[idx * FLOATBUF_CHUNK_SIZE + FDIR1] = fdir1[0];
        floatArray[idx * FLOATBUF_CHUNK_SIZE + FDIR1 + 1] = fdir1[1];
        floatArray[idx * FLOATBUF_CHUNK_SIZE + FDIR1 + 2] = fdir1[2];
    }

    @Override
    public float getMu() {
        return floatArray[index * FLOATBUF_CHUNK_SIZE + MU];
    }

    @Override
    public float getMu( int idx ) {
        return floatArray[idx * FLOATBUF_CHUNK_SIZE + MU];
    }

    @Override
    public void setMu( float mu ) {
        floatArray[index * FLOATBUF_CHUNK_SIZE + MU] = mu;
    }

    @Override
    public void setMu( float mu, int idx ) {
        floatArray[idx * FLOATBUF_CHUNK_SIZE + MU] = mu;
    }

    @Override
    public float getMu2() {
        return floatArray[index * FLOATBUF_CHUNK_SIZE + MU2];
    }

    @Override
    public float getMu2( int idx ) {
        return floatArray[idx * FLOATBUF_CHUNK_SIZE + MU2];
    }

    @Override
    public void setMu2( float mu2 ) {
        floatArray[index * FLOATBUF_CHUNK_SIZE + MU2] = mu2;
    }

    @Override
    public void setMu2( float mu2, int idx ) {
        floatArray[idx * FLOATBUF_CHUNK_SIZE + MU2] = mu2;
    }

    @Override
    public float getBounce() {
        return floatArray[index * FLOATBUF_CHUNK_SIZE + BOUNCE];
    }

    @Override
    public float getBounce( int idx ) {
        return floatArray[idx * FLOATBUF_CHUNK_SIZE + BOUNCE];
    }

    @Override
    public void setBounce( float bounce ) {
        floatArray[index * FLOATBUF_CHUNK_SIZE + BOUNCE] = bounce;
    }

    @Override
    public void setBounce( float bounce, int idx ) {
        floatArray[idx * FLOATBUF_CHUNK_SIZE + BOUNCE] = bounce;
    }

    @Override
    public float getBounceVel() {
        return floatArray[index * FLOATBUF_CHUNK_SIZE + BOUNCE_VEL];
    }

    @Override
    public float getBounceVel( int idx ) {
        return floatArray[idx * FLOATBUF_CHUNK_SIZE + BOUNCE_VEL];
    }

    @Override
    public void setBounceVel( float bounceVel ) {
        floatArray[index * FLOATBUF_CHUNK_SIZE + BOUNCE_VEL] = bounceVel;
    }

    @Override
    public void setBounceVel( float bounceVel, int idx ) {
        floatArray[idx * FLOATBUF_CHUNK_SIZE + BOUNCE_VEL] = bounceVel;
    }

    @Override
    public float getSoftErp() {
        return floatArray[index * FLOATBUF_CHUNK_SIZE + SOFT_ERP];
    }

    @Override
    public float getSoftErp( int idx ) {
        return floatArray[idx * FLOATBUF_CHUNK_SIZE + SOFT_ERP];
    }

    @Override
    public void setSoftErp( float softErp ) {
        floatArray[index * FLOATBUF_CHUNK_SIZE + SOFT_ERP] = softErp;
    }

    @Override
    public void setSoftErp( float softErp, int idx ) {
        floatArray[idx * FLOATBUF_CHUNK_SIZE + SOFT_ERP] = softErp;
    }

    @Override
    public float getSoftCfm() {
        return floatArray[index * FLOATBUF_CHUNK_SIZE + SOFT_CFM];
    }

    @Override
    public float getSoftCfm( int idx ) {
        return floatArray[idx * FLOATBUF_CHUNK_SIZE + SOFT_CFM];
    }

    @Override
    public void setSoftCfm( float softCfm ) {
        floatArray[index * FLOATBUF_CHUNK_SIZE + SOFT_CFM] = softCfm;
    }

    @Override
    public void setSoftCfm( float softCfm, int idx ) {
        floatArray[idx * FLOATBUF_CHUNK_SIZE + SOFT_CFM] = softCfm;
    }

    @Override
    public float getMotion1() {
        return floatArray[index * FLOATBUF_CHUNK_SIZE + MOTION1];
    }

    @Override
    public float getMotion1( int idx ) {
        return floatArray[idx * FLOATBUF_CHUNK_SIZE + MOTION1];
    }

    @Override
    public void setMotion1( float motion1 ) {
        floatArray[index * FLOATBUF_CHUNK_SIZE + MOTION1] = motion1;
    }

    @Override
    public void setMotion1( float motion1, int idx ) {
        floatArray[idx * FLOATBUF_CHUNK_SIZE + MOTION1] = motion1;
    }

    @Override
    public float getMotion2() {
        return floatArray[index * FLOATBUF_CHUNK_SIZE + MOTION2];
    }

    @Override
    public float getMotion2( int idx ) {
        return floatArray[idx * FLOATBUF_CHUNK_SIZE + MOTION2];
    }

    @Override
    public void setMotion2( float motion2 ) {
        floatArray[index * FLOATBUF_CHUNK_SIZE + MOTION2] = motion2;
    }

    @Override
    public void setMotion2( float motion2, int idx ) {
        floatArray[idx * FLOATBUF_CHUNK_SIZE + MOTION2] = motion2;
    }

    @Override
    public float getSlip1() {
        return floatArray[index * FLOATBUF_CHUNK_SIZE + SLIP1];
    }

    @Override
    public float getSlip1( int idx ) {
        return floatArray[idx * FLOATBUF_CHUNK_SIZE + SLIP1];
    }

    @Override
    public void setSlip1( float slip1 ) {
        floatArray[index * FLOATBUF_CHUNK_SIZE + SLIP1] = slip1;
    }

    @Override
    public void setSlip1( float slip1, int idx ) {
        floatArray[idx * FLOATBUF_CHUNK_SIZE + SLIP1] = slip1;
    }

    @Override
    public float getSlip2() {
        return floatArray[index * FLOATBUF_CHUNK_SIZE + SLIP2];
    }

    @Override
    public float getSlip2( int idx ) {
        return floatArray[idx * FLOATBUF_CHUNK_SIZE + SLIP2];
    }

    @Override
    public void setSlip2( float slip2 ) {
        floatArray[index * FLOATBUF_CHUNK_SIZE + SLIP2] = slip2;
    }

    @Override
    public void setSlip2( float slip2, int idx ) {
        floatArray[idx * FLOATBUF_CHUNK_SIZE + SLIP2] = slip2;
    }

}

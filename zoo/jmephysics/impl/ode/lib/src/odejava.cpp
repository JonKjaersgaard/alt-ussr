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
 *
 * --
 * Modified by Irrisor.
 */

#include <jni.h>
#include "odejava.hpp"

//
// General callback definitions and functions
//

// Contact group where all dContacts will be stored
static dJointGroupID contactGroupID;
static dWorldID worldID;

// Set contact jointGroupID used in step
void setContactGroupID(dJointGroupID id) {
  contactGroupID = id;
}

// Set worldID used in step
void setWorldID(dWorldID id) {
  worldID = id;
}

//
// Default surface values
//
void setSurfaceMode(int mode) {
  surfaceMode=mode;
}
void setSurfaceMu(float mu) {
  surfaceMu=mu;
}
void setSurfaceMu2(float mu2) {
  surfaceMu2=mu2;
}
void setSurfaceBounce(float bounce) {
  surfaceBounce=bounce;
}
void setSurfaceBounceVel(float bounceVel) {
  surfaceBounceVel=bounceVel;
}
void setSurfaceMotion1(float motion1) {
  surfaceMotion1=motion1;
}
void setSurfaceMotion2(float motion2) {
  surfaceMotion2=motion2;
}
void setSurfaceSlip1(float slip1) {
  surfaceSlip1=slip1;
}
void setSurfaceSlip2(float slip2) {
  surfaceSlip2=slip2;
}
void setSurfaceSoftErp(float erp) {
  surfaceSoftErp=erp;
}
void setSurfaceSoftCfm(float cfm) {
  surfaceSoftCfm=cfm;
}


#ifdef __cplusplus
extern "C" {
#endif


// Maximum number of contacts per callback
#define MAX_CALLBACK_CONTACTS 12
static int maxCallbackContactGeoms = MAX_CALLBACK_CONTACTS;

// Create dContactGeom array used in each callback
static dContactGeom * callbackContactGeoms = 
  (dContactGeom *)malloc(MAX_CALLBACK_CONTACTS * sizeof(dContactGeom));

// Set new MAX_CALLBACK_CONTACTS
// Defines how many contact geoms will be generated per dCollide call.
// Smaller values make simulation faster, larger values better
JNIEXPORT void JNICALL Java_org_odejava_collision_Collision_setMaxCallbackContactGeoms
(JNIEnv *env, jobject obj, jint size) {
  maxCallbackContactGeoms = (int) size;
  free(callbackContactGeoms);
  callbackContactGeoms = (dContactGeom *)malloc((int) size * sizeof(dContactGeom));
}


//
// Intermediate Java version of nearCallback
// Does most work on C side, but gives contact information related data
// back to Java where it can be edited before stepping the world.
// nearCallback is done in C but it collects the dContact data to 
// Java DirectBuffers.
//

// Maximum number of contacts per single step (Java DirectBuffers)
#define MAX_STEP_CONTACTS 4096
#define FLOAT_CONTACT_CHUNKSIZE 20
#define LONG_CONTACT_CHUNKSIZE 5
#define FLOAT_CONTACTBUFFER_SIZE sizeof(float)*MAX_STEP_CONTACTS*FLOAT_CONTACT_CHUNKSIZE
#define LONG_CONTACTBUFFER_SIZE sizeof(jlong)*MAX_STEP_CONTACTS*LONG_CONTACT_CHUNKSIZE
static int maxStepContacts = MAX_STEP_CONTACTS;

// Create buffers where contact information is stored (Java DirectBuffers)
// contactBufCursor is cursor for buffers, count of contacts per each step
int floatContactBufChunkSize = FLOAT_CONTACT_CHUNKSIZE;
int longContactBufChunkSize = LONG_CONTACT_CHUNKSIZE;
static float *floatContactBuf = (float *) malloc(FLOAT_CONTACTBUFFER_SIZE);
static jlong *longContactBuf = (jlong *) malloc(LONG_CONTACTBUFFER_SIZE);
int contactBufCursor = 0;

// Set new MAX_STEP_CONTACTS
// Buffers need to be big enough to contain contact data generated per single step
JNIEXPORT void JNICALL Java_org_odejava_collision_JavaCollision_setMaxStepContacts
(JNIEnv *env, jobject obj, jint size) {
  maxStepContacts = (int) size;
  free(floatContactBuf);
  free(longContactBuf);
  floatContactBuf = (float *) malloc(sizeof(float)*size*FLOAT_CONTACT_CHUNKSIZE);
  longContactBuf = (jlong *) malloc(sizeof(jlong)*size*LONG_CONTACT_CHUNKSIZE);
}

// debug
//static int greatestContactGeomsSize;

// This callback collects all contacts data (dContactGeom) to buffers which
// can be later accessed on the Java side.
// Used with spaceCollide
static void nearCallback (void *data, dGeomID o1, dGeomID o2) {   
  // This function is called by ODE if two Geoms are near each other.
  // Geoms o1 and o2 are near each other, if they actually collide then
  // generate contact information (dContact structure) and add them
  // to contact jointgroup.

  dBodyID b1 = dGeomGetBody(o1);
  dBodyID b2 = dGeomGetBody(o2);

  // ignore if both o1 and o2 are plain Geoms (not bodies)
  if (!b1 && !b2) return;

  // ignore if the two bodies are connected by a joint
  if (b1 && b2 && dAreConnectedExcluding (b1,b2,dJointTypeContact)) return;
    
  // Get number of possible contact points into callbackContactGeoms array
  int ncp = dCollide(
    o1,o2,maxCallbackContactGeoms,&callbackContactGeoms[0],sizeof(dContactGeom)
  );
  
  if (ncp > 0) {
    // Geoms actually do collide    

    // debug
    //if (ncp > greatestContactGeomsSize) greatestContactGeomsSize=ncp;

    for (int i = 0; i < ncp; i++) {     
      // Add collision related data to buffers
	// geomID1, geomID2, bodyID1, bodyID2
      longContactBuf[contactBufCursor*longContactBufChunkSize] = (jlong) o1;
	longContactBuf[contactBufCursor*longContactBufChunkSize+1] = (jlong) o2;
	longContactBuf[contactBufCursor*longContactBufChunkSize+2] = (jlong) b1;
	longContactBuf[contactBufCursor*longContactBufChunkSize+3] = (jlong) b2;
	// pos, normal, depth
	floatContactBuf[contactBufCursor*floatContactBufChunkSize] =
	  callbackContactGeoms[i].pos[0];
	floatContactBuf[contactBufCursor*floatContactBufChunkSize+1] = 
	  callbackContactGeoms[i].pos[1];
	floatContactBuf[contactBufCursor*floatContactBufChunkSize+2] = 
	  callbackContactGeoms[i].pos[2];
	floatContactBuf[contactBufCursor*floatContactBufChunkSize+3] =
	  callbackContactGeoms[i].normal[0];
	floatContactBuf[contactBufCursor*floatContactBufChunkSize+4] =
	  callbackContactGeoms[i].normal[1];
	floatContactBuf[contactBufCursor*floatContactBufChunkSize+5] =
	  callbackContactGeoms[i].normal[2];
      floatContactBuf[contactBufCursor*floatContactBufChunkSize+6] =
	  callbackContactGeoms[i].depth;
	// Set contactbuffer surface mode to -1
	// Set surface mode to -1, use global surface parameters
  	longContactBuf[contactBufCursor*longContactBufChunkSize+4] = -1;
	contactBufCursor++;
    }
  }
}

// This callback collects all contacts data (dContactGeom) to buffers which
// can be later accessed on the Java side.
// Used with spaceCollide2
static void nearCallback2 (void *data, dGeomID o1, dGeomID o2) {   
  // This function is called by ODE if two Geoms are near each other.
  // Geoms o1 and o2 are near each other, if they actually collide then
  // generate contact information (dContact structure) and add them
  // to contact jointgroup.
  if (dGeomIsSpace (o1) || dGeomIsSpace (o2)) {
      // colliding a space with something
      dSpaceCollide2 (o1,o2,data,&nearCallback);
      // collide all geoms internal to the space(s)
      if (dGeomIsSpace (o1)) dSpaceCollide ((dSpaceID) o1,data,&nearCallback);
      if (dGeomIsSpace (o2)) dSpaceCollide ((dSpaceID) o2,data,&nearCallback);
  } else {

    dBodyID b1 = dGeomGetBody(o1);
    dBodyID b2 = dGeomGetBody(o2);

    // Get number of possible contact points into callbackContactGeoms array
    int ncp = dCollide(
      o1,o2,maxCallbackContactGeoms,&callbackContactGeoms[0],sizeof(dContactGeom)
    );
  
    if (ncp > 0) {
      // Geoms actually do collide    

      // debug
      //if (ncp > greatestContactGeomsSize) greatestContactGeomsSize=ncp;

      for (int i = 0; i < ncp; i++) {     
        // Add collision related data to buffers
  	  // geomID1, geomID2, bodyID1, bodyID2
        longContactBuf[contactBufCursor*longContactBufChunkSize] = (jlong) o1;
  	  longContactBuf[contactBufCursor*longContactBufChunkSize+1] = (jlong) o2;
        longContactBuf[contactBufCursor*longContactBufChunkSize+2] = (jlong) b1;
	  longContactBuf[contactBufCursor*longContactBufChunkSize+3] = (jlong) b2;
	  // pos, normal, depth
	  floatContactBuf[contactBufCursor*floatContactBufChunkSize] =
	    callbackContactGeoms[i].pos[0];
	  floatContactBuf[contactBufCursor*floatContactBufChunkSize+1] = 
	    callbackContactGeoms[i].pos[1];
	  floatContactBuf[contactBufCursor*floatContactBufChunkSize+2] = 
	    callbackContactGeoms[i].pos[2];
	  floatContactBuf[contactBufCursor*floatContactBufChunkSize+3] =
	    callbackContactGeoms[i].normal[0];
	  floatContactBuf[contactBufCursor*floatContactBufChunkSize+4] =
	    callbackContactGeoms[i].normal[1];
	  floatContactBuf[contactBufCursor*floatContactBufChunkSize+5] =
	    callbackContactGeoms[i].normal[2];
        floatContactBuf[contactBufCursor*floatContactBufChunkSize+6] =
	    callbackContactGeoms[i].depth;
	  // Set surface mode to -1, use global surface parameters
  	  longContactBuf[contactBufCursor*longContactBufChunkSize+4] = -1;
	  contactBufCursor++;
      }
    }
  }
}

// Collide space's objects with nearCallback function.
// returns number of contacts
JNIEXPORT jint JNICALL Java_org_odejava_collision_JavaCollision_spaceCollide
(JNIEnv *env, jobject obj, jlong jarg1) {    
  dSpaceID arg1;
  dSpaceID *argp1;
  argp1 = *(dSpaceID **)&jarg1;
  arg1 = *argp1;  

  contactBufCursor = 0;

  // debug
  //greatestContactGeomsSize = 0;
  // Collide, updates stepContactsSize and stepContacts

  dSpaceCollide (arg1,0,&nearCallback);

  // debug
  //fprintf(
  //  stderr,"[C] greatestContactGeomsSize %d\n",greatestContactGeomsSize
  //);
  return contactBufCursor;
}

// Collide space/geom into space/geom with nearCallback function.
// returns number of contacts
JNIEXPORT jint JNICALL Java_org_odejava_collision_JavaCollision_spaceCollide2
(JNIEnv *env, jobject obj, jlong jarg1, jlong jarg2) {

  contactBufCursor = 0;

  // debug
  //greatestContactGeomsSize = 0;
  // Collide, updates stepContactsSize and stepContacts

  dSpaceCollide2 (*(dGeomID *)jarg1,*(dGeomID *)jarg2,0,&nearCallback2);

  // debug
  //fprintf(
  //  stderr,"[C] greatestContactGeomsSize %d\n",greatestContactGeomsSize
  //);
  return contactBufCursor;
}

// Create Java DirectBuffer based on native longContactBuf
JNIEXPORT jobject JNICALL Java_org_odejava_collision_JavaCollision_getContactIntBuf
(JNIEnv *env, jobject obj) {
  return env->NewDirectByteBuffer(longContactBuf, LONG_CONTACTBUFFER_SIZE); 
}

// Create Java DirectBuffer based on native floatContactBuf
JNIEXPORT jobject JNICALL Java_org_odejava_collision_JavaCollision_getContactFloatBuf
(JNIEnv *env, jobject obj) {
  return env->NewDirectByteBuffer(floatContactBuf, FLOAT_CONTACTBUFFER_SIZE); 
}


// Adds all contact joints to contact jointgroup.
// Constructs dContact structure based on buffers values.
// Call this after spaceCollide(2)
JNIEXPORT void JNICALL Java_org_odejava_collision_JavaCollision_createContactJoints
(JNIEnv *env, jobject obj) {    
  // Create contact joints using contact information
  for (int i = 0; i < contactBufCursor;i ++) {
    // ignore contact if dContactGeom.g1=0 && dContactGeom.g2=0 (hack)
    if (
      longContactBuf[i*longContactBufChunkSize] || 
      longContactBuf[i*longContactBufChunkSize+1]
    ) {
      dContact c;
	dContactGeom cg;
	dSurfaceParameters sp;

      // dContact
	c.fdir1[0] = floatContactBuf[i*floatContactBufChunkSize+7];
	c.fdir1[1] = floatContactBuf[i*floatContactBufChunkSize+8];
	c.fdir1[2] = floatContactBuf[i*floatContactBufChunkSize+9];
	c.fdir1[3] = 0;

	// dContact.geom
	cg.pos[0] = floatContactBuf[i*floatContactBufChunkSize+0];
	cg.pos[1] = floatContactBuf[i*floatContactBufChunkSize+1];
	cg.pos[2] = floatContactBuf[i*floatContactBufChunkSize+2];
	cg.pos[3] = 0;
	cg.normal[0] = floatContactBuf[i*floatContactBufChunkSize+3];
	cg.normal[1] = floatContactBuf[i*floatContactBufChunkSize+4];
	cg.normal[2] = floatContactBuf[i*floatContactBufChunkSize+5];
	cg.normal[3] = 0;
	cg.depth = floatContactBuf[i*floatContactBufChunkSize+6];
	cg.g1 = (dxGeom *) longContactBuf[i*longContactBufChunkSize];
	cg.g2 = (dxGeom *) longContactBuf[i*longContactBufChunkSize+1];

	// dContact.surface
	// If contactbuffer surface mode is -1, use global surface parameters
	if (longContactBuf[i*longContactBufChunkSize+4] == -1) {
	  // Use default values
	  sp.mode = surfaceMode;
        sp.mu = surfaceMu;
        sp.mu2 = surfaceMu2;
        sp.bounce = surfaceBounce;
        sp.bounce_vel = surfaceBounceVel;
        sp.soft_erp = surfaceSoftErp;
        sp.soft_cfm = surfaceSoftCfm;
        sp.motion1 = surfaceMotion1;
        sp.motion2 = surfaceMotion2;
        sp.slip1 = surfaceSlip1;
        sp.slip2 = surfaceSlip2;
	} else {
	  // Use values from buffers
  	  sp.mode = longContactBuf[i*longContactBufChunkSize+4];
	  sp.mu = floatContactBuf[i*floatContactBufChunkSize+10];
	  sp.mu2 = floatContactBuf[i*floatContactBufChunkSize+11];
	  sp.bounce = floatContactBuf[i*floatContactBufChunkSize+12];
	  sp.bounce_vel = floatContactBuf[i*floatContactBufChunkSize+13];
	  sp.soft_erp = floatContactBuf[i*floatContactBufChunkSize+14];
	  sp.soft_cfm = floatContactBuf[i*floatContactBufChunkSize+15];
	  sp.motion1 = floatContactBuf[i*floatContactBufChunkSize+16];
	  sp.motion2 = floatContactBuf[i*floatContactBufChunkSize+17];
	  sp.slip1 = floatContactBuf[i*floatContactBufChunkSize+18];
	  sp.slip2 = floatContactBuf[i*floatContactBufChunkSize+19];
	}

	// Set dContact.geom and dContact.surface
	c.geom = cg;
	c.surface = sp;
	
      dJointID joint = dJointCreateContact (
        worldID,contactGroupID,&c
      );
      dJointAttach (
        joint,
        (dxBody *) longContactBuf[i*longContactBufChunkSize+2],
        (dxBody *) longContactBuf[i*longContactBufChunkSize+3]
      );
    }
  }
}


//
// Pure Java version of nearCallback
//

// JVM variables
// If user wishes to send every nearCallback calls to Java
// (using pureJavaNearCallback), then JVM variables need to be
// set by init call setJavaNearCallback(), and afterwards
// collision is handled by calling pureJavaSpaceCollide().
// TODO use struct later for triMesh/Ray/etc callbacks
static JNIEnv *jenv;
static jobject jobj;
static jmethodID jmid;

// Set pure Java callback method that handles all nearCallback
// tasks on the Java side.
// Store Java's JVM and Java's nearCallback method to C side so
// pureJavaCallback function can call the Java method.
JNIEXPORT void JNICALL Java_org_odejava_collision_PureJavaCollision_setJavaNearCallback
(JNIEnv *env, jobject obj, jobject obj2, jstring methodName) {
  jenv = env;
  jclass clazz = env->GetObjectClass(obj2);  
  if (clazz == 0) {
    fprintf(stderr,"setJavaNearCallback could not find class, check param1\n");
  }
  const char *name = env->GetStringUTFChars(methodName, 0);
  jmethodID mid = env->GetMethodID(
    clazz, name, "(II)V"
  );
  if (mid == 0) {
    fprintf(stderr,"setJavaNearCallback could not find method, check param2\n");
  }
  env->ReleaseStringUTFChars(methodName, name);
  jobj = obj;
  jmid = mid;
}

static void javaNearCallback (void *data, dGeomID o1, dGeomID o2) {   
  jenv->CallVoidMethod(jobj, jmid, (jint *) o1, (jint *) o2);
}

// Collide space's objects with javaNearCallback function
// using dSpaceCollide
JNIEXPORT void JNICALL Java_org_odejava_collision_PureJavaCollision_javaSpaceCollide
(JNIEnv *env, jobject obj, jlong jarg1) {    
  dSpaceID arg1;
  dSpaceID *argp1;
  argp1 = *(dSpaceID **)&jarg1;
  arg1 = *argp1;  

  // Collide
  dSpaceCollide (arg1,0,&javaNearCallback);
}

// Collide space's objects with javaNearCallback function
// using dSpaceCollide2
JNIEXPORT void JNICALL Java_org_odejava_collision_PureJavaCollision_javaSpaceCollide2
(JNIEnv *env, jobject obj, jlong jarg1, jlong jarg2) {
  // Collide
  dSpaceCollide2 ((dGeomID) jarg1,(dGeomID) jarg2,0,&javaNearCallback);
}

// Adds single contact joint into contact jointgroup
// jointId = swigCptr, bodyId1 = native addr, bodyId2 = native addr
JNIEXPORT void JNICALL Java_org_odejava_collision_PureJavaCollision_jointAttach
(JNIEnv *env, jobject obj, jlong jointId, jint bodyId1, jint bodyId2) {
  dJointAttach(
    **(dJointID **) &jointId,
    (dBodyID) bodyId1,
    (dBodyID) bodyId2
  );
}


//
// Helper functions
//

// Get native C address for structs / objects where given swigCPtr points to.
// This function is used e.g. to compare contact information's geomIDs
// against Odejava's Geoms.
// NOTE: nearCallback creates dGeomID's that are not Odejava style dGeomID's.
// Swig wraps native objects so these cannot be directly compared.
JNIEXPORT jlong JNICALL Java_org_odejava_Odejava_getNativeAddr
(JNIEnv *jenv, jclass jcls, jlong swigCPtr) {
#ifdef __ppc__
        // power pc seems to issues casting pointers to long and back again (maybe due to byte order?)
        return (jlong)(**(jint **)&swigCPtr);
#else
	void* id = *((void* *)swigCPtr);
	return (jlong) id;
#endif
}


//
// Plain native version of nearCallback
//

// The code below can be used if user does not need collision
// related information handled in the Java side.
// This callback handles everything on the C side
static void plainNativeNearCallback (void *data, dGeomID o1, dGeomID o2)
{
  // This function is called by ODE if two Geoms are near each other.
  // Geoms o1 and o2 are near each other, if they actually collide then
  // generate contact information (dContact structure) and add them
  // to contact jointgroup.
  dBodyID b1 = dGeomGetBody(o1);
  dBodyID b2 = dGeomGetBody(o2);

  // ignore if both o1 and o2 are plain Geoms (not bodies)
  if (!b1 && !b2) return;

  // ignore if the two bodies are connected by a joint
  if (b1 && b2 && dAreConnectedExcluding (b1,b2,dJointTypeContact)) return;
    
  // Get number of possible contact points into callbackContactGeoms array
  int ncp = dCollide(
    o1,o2,maxCallbackContactGeoms,&callbackContactGeoms[0],sizeof(dContactGeom)
  );
  
  if (ncp > 0) {
    // Geoms actually do collide    

    // debug
    //if (ncp > greatestContactGeomsSize) greatestContactGeomsSize=ncp;

    for (int i = 0; i < ncp; i++) {     
      // Create dContact structure for each contact
      dContact contact;
      // Set contact geom
      contact.geom = callbackContactGeoms[i];
      // Set surface parameters
      contact.surface.mode = surfaceMode;
      contact.surface.mu = surfaceMu;
      contact.surface.mu2 = surfaceMu2;
      contact.surface.bounce = surfaceBounce;
      contact.surface.bounce_vel = surfaceBounceVel;
      contact.surface.soft_cfm = surfaceSoftCfm;
      contact.surface.motion1 = surfaceMotion1;
      contact.surface.motion2 = surfaceMotion2;
      contact.surface.slip1 = surfaceSlip1;
      contact.surface.slip2 = surfaceSlip2;
      // Create contact joint
      dJointID c = dJointCreateContact (worldID,contactGroupID,&contact);
      dJointAttach (c,b1,b2);
    }
  }
}

// Plain native spaceCollide
JNIEXPORT void JNICALL Java_org_odejava_collision_NativeCollision_plainNativeSpaceCollide
(JNIEnv *env, jobject obj, jlong jarg1) {    
  dSpaceID arg1;
  dSpaceID *argp1;
  argp1 = *(dSpaceID **)&jarg1;
  arg1 = *argp1;  
  dSpaceCollide (arg1,0,&plainNativeNearCallback);
}


#ifdef __cplusplus
}
#endif



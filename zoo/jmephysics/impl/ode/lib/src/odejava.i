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

//
// Swig interface definition for ODE interface
// Version 0.2.3
// Updated: 14.2.2004 (dd.mm.yyyy)
//
// Odejava's API conforms to ODE's C API as this is the main API on ODE.
// This library is to be considered as alpha because proper testing has not
// been done. Preliminary testing has been done using swig 1.3.20 and
// ODE release version 0.39.
//
// This API contains also following contrib modules:
// -dCylinder
//
// If you are builind your own odejava library and want to use ode with
// double precision, replace all "float" strings with "double". ODE must be
// compiled with single precision if you want to use TriMesh (OPCODE).
//
// See http://odejava.dev.java.net for more information.
//

%module Ode

///////////////////////////////////////////////////////////////////////////
// Odejava
///////////////////////////////////////////////////////////////////////////
%{
 #include <ode/ode.h>
// #include <ode/GeomTransformGroup.h>
// #include <../ode/src/dCylinder.h>
 #include <../ode/src/joint.h>
 #include "odejava.hpp"
%}

// Set protected to public for constructors and getting swigCPtr
// Open also swigCPtr as public method.
%typemap(javaptrconstructormodifiers) SWIGTYPE, SWIGTYPE *, SWIGTYPE &, SWIGTYPE [], SWIGTYPE (CLASS::*) "public"
%typemap(javagetcptr) SWIGTYPE, SWIGTYPE *, SWIGTYPE &, SWIGTYPE [], SWIGTYPE (CLASS::*)
%{
  public static long getCPtr($javaclassname obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  public long getSwigCPtr() {
    return swigCPtr;
  }

  public void setSwigCPtr(long swigCPtr) {
    this.swigCPtr = swigCPtr;
  }
%}

// Use single precision
typedef float dReal;

// Add swig helper functions for handling arrays
%include "carrays.i"
%array_functions(float, floatArray);
%array_functions(int, intArray);
%array_functions(dGeomID, geomIDArray);


//
// Odejava C additions
//

%inline %{
// Odejava version identifier
#define ODEJAVA_VERSION "0.2.4"

// Zero body id
const dBodyID BODYID_ZERO = 0;

// Zero joint group
const dJointGroupID JOINTGROUPID_ZERO = 0;

// Zero parent space id
const dSpaceID PARENTSPACEID_ZERO = 0;
%}

// Set world and contact groupId used in simulation step
void setWorldID(dWorldID id);
void setContactGroupID(dJointGroupID id);

// Set general surface parameters, these are the default values
// for every contact point. These can be overridden in Java.
void setSurfaceMode(int mode);
void setSurfaceMu(float mu);
void setSurfaceMu2(float mu2);
void setSurfaceBounce(float bounce);
void setSurfaceBounceVel(float bounceVel);
void setSurfaceMotion1(float motion1);
void setSurfaceMotion2(float motion2);
void setSurfaceSlip1(float slip1);
void setSurfaceSlip2(float slip2);
void setSurfaceSoftErp(float softErp);
void setSurfaceSoftCfm(float softCfm);


//
// ODE functions begin
//

///////////////////////////////////////////////////////////////////////////
// collision.h
///////////////////////////////////////////////////////////////////////////
/* general functions */

void dGeomDestroy (dGeomID);
void dGeomSetData (dGeomID, void *);
void *dGeomGetData (dGeomID);
void dGeomSetBody (dGeomID, dBodyID);
dBodyID dGeomGetBody (dGeomID);
void dGeomSetPosition (dGeomID, dReal x, dReal y, dReal z);
void dGeomSetRotation (dGeomID, const dMatrix3 R);
void dGeomSetQuaternion (dGeomID, const dQuaternion);
const dReal * dGeomGetPosition (dGeomID);
const dReal * dGeomGetRotation (dGeomID);
void dGeomGetQuaternion (dGeomID, dQuaternion result);
void dGeomGetAABB (dGeomID, dReal aabb[6]);
int dGeomIsSpace (dGeomID);
dSpaceID dGeomGetSpace (dGeomID);
int dGeomGetClass (dGeomID);
void dGeomSetCategoryBits (dGeomID, unsigned long bits);
void dGeomSetCollideBits (dGeomID, unsigned long bits);
unsigned long dGeomGetCategoryBits (dGeomID);
unsigned long dGeomGetCollideBits (dGeomID);
void dGeomEnable (dGeomID);
void dGeomDisable (dGeomID);
int dGeomIsEnabled (dGeomID);

/* collision detection */

int dCollide (dGeomID o1, dGeomID o2, int flags, dContactGeom *contact,
	      int skip);
void dSpaceCollide (dSpaceID space, void *data, dNearCallback *callback);


/* standard classes */

/* the maximum number of user classes that are supported */
enum {
  dMaxUserClasses = 4
};

/* class numbers - each geometry object needs a unique number */
enum {
  dSphereClass = 0,
  dBoxClass,
  dCapsuleClass,
  dCylinderClass,
  dPlaneClass,
  dRayClass,
  dGeomTransformClass,
  dTriMeshClass,
//  dTerrainClass,	// Contrib (TerrainAndCone)
//  dConeClass,	// Contrib (TerrainAndCone)
  dFirstSpaceClass,
  dSimpleSpaceClass = dFirstSpaceClass,
  dHashSpaceClass,
  dQuadTreeSpaceClass,
  dLastSpaceClass = dQuadTreeSpaceClass,
  dFirstUserClass,
  dLastUserClass = dFirstUserClass + dMaxUserClasses - 1,
  dGeomNumClasses
};

dGeomID dCreateSphere (dSpaceID space, dReal radius);
void dGeomSphereSetRadius (dGeomID sphere, dReal radius);
dReal dGeomSphereGetRadius (dGeomID sphere);
dReal dGeomSpherePointDepth (dGeomID sphere, dReal x, dReal y, dReal z);

dGeomID dCreateBox (dSpaceID space, dReal lx, dReal ly, dReal lz);
void dGeomBoxSetLengths (dGeomID box, dReal lx, dReal ly, dReal lz);
void dGeomBoxGetLengths (dGeomID box, dVector3 result);
dReal dGeomBoxPointDepth (dGeomID box, dReal x, dReal y, dReal z);

dGeomID dCreatePlane (dSpaceID space, dReal a, dReal b, dReal c, dReal d);
void dGeomPlaneSetParams (dGeomID plane, dReal a, dReal b, dReal c, dReal d);
void dGeomPlaneGetParams (dGeomID plane, dVector4 result);
dReal dGeomPlanePointDepth (dGeomID plane, dReal x, dReal y, dReal z);

dGeomID dCreateCapsule (dSpaceID space, dReal radius, dReal length);
void dGeomCapsuleSetParams (dGeomID Capsule, dReal radius, dReal length);
void dGeomCapsuleGetParams (dGeomID Capsule, dReal *radius, dReal *length);
dReal dGeomCapsulePointDepth (dGeomID Capsule, dReal x, dReal y, dReal z);

dGeomID dCreateRay (dSpaceID space, dReal length);
void dGeomRaySetLength (dGeomID ray, dReal length);
dReal dGeomRayGetLength (dGeomID ray);
void dGeomRaySet (dGeomID ray, dReal px, dReal py, dReal pz,
		  dReal dx, dReal dy, dReal dz);
void dGeomRayGet (dGeomID ray, dVector3 start, dVector3 dir);

/*
 * Set/get ray flags that influence ray collision detection.
 * These flags are currently only noticed by the trimesh collider, because
 * they can make a major differences there.
 */
void dGeomRaySetParams (dGeomID g, int FirstContact, int BackfaceCull);
void dGeomRayGetParams (dGeomID g, int *FirstContact, int *BackfaceCull);
//void dGeomRaySetClosestHit (dGeomID g, int closestHit);
//int dGeomRayGetClosestHit (dGeomID g);

/* for backward compatibility */
/*
dGeomID dCreateGeomGroup (dSpaceID space);
void dGeomGroupAdd (dGeomID group, dGeomID x);
void dGeomGroupRemove (dGeomID group, dGeomID x);
int dGeomGroupGetNumGeoms (dGeomID group);
dGeomID dGeomGroupGetGeom (dGeomID group, int i);
int dGeomGroupQuery (dGeomID group, dGeomID x);
*/

dGeomID dCreateGeomTransform (dSpaceID space);
void dGeomTransformSetGeom (dGeomID g, dGeomID obj);
dGeomID dGeomTransformGetGeom (dGeomID g);
void dGeomTransformSetCleanup (dGeomID g, int mode);
int dGeomTransformGetCleanup (dGeomID g);
void dGeomTransformSetInfo (dGeomID g, int mode);
int dGeomTransformGetInfo (dGeomID g);


/* utility functions */

void dClosestLineSegmentPoints (const dVector3 a1, const dVector3 a2,
				const dVector3 b1, const dVector3 b2,
				dVector3 cp1, dVector3 cp2);

int dBoxTouchesBox (const dVector3 _p1, const dMatrix3 R1,
		    const dVector3 side1, const dVector3 _p2,
		    const dMatrix3 R2, const dVector3 side2);

void dInfiniteAABB (dGeomID geom, dReal aabb[6]);
void dCloseODE();

/* custom classes */

typedef void dGetAABBFn (dGeomID, dReal aabb[6]);
typedef int dColliderFn (dGeomID o1, dGeomID o2,
			 int flags, dContactGeom *contact, int skip);
typedef dColliderFn * dGetColliderFnFn (int num);
typedef void dGeomDtorFn (dGeomID o);
typedef int dAABBTestFn (dGeomID o1, dGeomID o2, dReal aabb[6]);

typedef struct dGeomClass {
  int bytes;
  dGetColliderFnFn *collider;
  dGetAABBFn *aabb;
  dAABBTestFn *aabb_test;
  dGeomDtorFn *dtor;
};

int dCreateGeomClass (const dGeomClass *classptr);
void * dGeomGetClassData (dGeomID);
dGeomID dCreateGeom (int classnum);

// Contrib (TerrainAndCone)
/*
dGeomID dCreateTerrain (dSpaceID space, dReal *pHeights,dReal vLength,int nNumNodesPerSide);
dReal dGeomTerrainPointDepth (dGeomID Capsule, dReal x, dReal y, dReal z);
dGeomID dCreateCone(dSpaceID space, dReal radius, dReal length);
void dGeomConeSetParams (dGeomID cone, dReal radius, dReal length);
void dGeomConeGetParams (dGeomID cone, dReal *radius, dReal *length);
dReal dGeomConePointDepth(dGeomID g, dReal x, dReal y, dReal z);
*/

///////////////////////////////////////////////////////////////////////////
// collision_space.h
///////////////////////////////////////////////////////////////////////////
struct dContactGeom;

typedef void dNearCallback (void *data, dGeomID o1, dGeomID o2);


dSpaceID dSimpleSpaceCreate (dSpaceID space);
dSpaceID dHashSpaceCreate (dSpaceID space);
dSpaceID dQuadTreeSpaceCreate (dSpaceID space, dVector3 Center, dVector3 Extents, int Depth);

void dSpaceDestroy (dSpaceID);

void dHashSpaceSetLevels (dSpaceID space, int minlevel, int maxlevel);
//void dHashSpaceGetLevels (dSpaceID space, int *minlevel, int *maxlevel);

void dSpaceSetCleanup (dSpaceID space, int mode);
int dSpaceGetCleanup (dSpaceID space);

void dSpaceAdd (dSpaceID, dGeomID);
void dSpaceRemove (dSpaceID, dGeomID);
int dSpaceQuery (dSpaceID, dGeomID);
//void dSpaceClean (dSpaceID);
int dSpaceGetNumGeoms (dSpaceID);
dGeomID dSpaceGetGeom (dSpaceID, int i);

///////////////////////////////////////////////////////////////////////////
// collision_trimesh.h
///////////////////////////////////////////////////////////////////////////
/*
 * Data storage for triangle meshes.
 */
struct dxTriMeshData;
typedef struct dxTriMeshData* dTriMeshDataID;

/*
 * These dont make much sense now, but they will later when we add more
 * features.
 */
dTriMeshDataID dGeomTriMeshDataCreate();
void dGeomTriMeshDataDestroy(dTriMeshDataID g);

enum { TRIMESH_FACE_NORMALS, TRIMESH_LAST_TRANSFORMATION };
void dGeomTriMeshDataSet(dTriMeshDataID g, int data_id, void* in_data);
void* dGeomTriMeshDataGet(dTriMeshDataID g, int data_id);

/*
 * Build function.
 */
void dGeomTriMeshDataBuildSingle(dTriMeshDataID g, const dReal* Vertices, int VertexStride, int VertexCount, const int* Indices, int IndexCount, int TriStride);
void dGeomTriMeshDataBuildSingle1(dTriMeshDataID g, const dReal* Vertices, int VertexStride, int VertexCount, const int* Indices, int IndexCount, int TriStride, const dReal* Normals);

/*
 * Simple build. Works only for single precision!
 */
void dGeomTriMeshDataBuildSimple(dTriMeshDataID g, const dReal* Vertices, int VertexCount, const int* Indices, int IndexCount);
void dGeomTriMeshDataBuildSimple1(dTriMeshDataID g, const dReal* Vertices, int VertexCount, const int* Indices, int IndexCount, const int* Normals);

/*
 * Per triangle callback. Allows the user to say if he wants a collision with
 * a particular triangle.
 */
typedef int dTriCallback(dGeomID TriMesh, dGeomID RefObject, int TriangleIndex);
//void dGeomTriMeshSetCallback(dGeomID g, dTriCallback* Callback);
//dTriCallback* dGeomTriMeshGetCallback(dGeomID g);

/*
 * Per object callback. Allows the user to get the list of triangles in 1
 * shot. Maybe we should remove this one.
 */
//typedef void dTriArrayCallback(dGeomID TriMesh, dGeomID RefObject, const int* TriIndices, int TriCount);
//void dGeomTriMeshSetArrayCallback(dGeomID g, dTriArrayCallback* ArrayCallback);
//dTriArrayCallback* dGeomTriMeshGetArrayCallback(dGeomID g);

/*
 * Ray callback.
 * Allows the user to say if a ray collides with a triangle on barycentric
 * coords. The user can for example sample a texture with alpha transparency
 * to determine if a collision should occur.
 */
//typedef int dTriRayCallback(dGeomID TriMesh, dGeomID Ray, int TriangleIndex, dReal u, dReal v);
//void dGeomTriMeshSetRayCallback(dGeomID g, dTriRayCallback* Callback);
//dTriRayCallback* dGeomTriMeshGetRayCallback(dGeomID g);

/*
 * Trimesh class
 * Construction. Callbacks are optional.
 */
dGeomID dCreateTriMesh(dSpaceID space, dTriMeshDataID Data, dTriCallback* Callback, dTriArrayCallback* ArrayCallback, dTriRayCallback* RayCallback);

void dGeomTriMeshSetData(dGeomID g, dTriMeshDataID Data);

// enable/disable/check temporal coherence
void dGeomTriMeshEnableTC(dGeomID g, int geomClass, int enable);
int dGeomTriMeshIsTCEnabled(dGeomID g, int geomClass);

/*
 * Clears the internal temporal coherence caches. When a geom has its
 * collision checked with a trimesh once, data is stored inside the trimesh.
 * With large worlds with lots of seperate objects this list could get huge.
 * We should be able to do this automagically.
 */
void dGeomTriMeshClearTCCache(dGeomID g);

/*
 * Gets a triangle.
 */
void dGeomTriMeshGetTriangle(dGeomID g, int Index, dVector3* v0, dVector3* v1, dVector3* v2);

/*
 * Gets the point on the requested triangle and the given barycentric
 * coordinates.
 */
void dGeomTriMeshGetPoint(dGeomID g, int Index, dReal u, dReal v, dVector3 Out);

///////////////////////////////////////////////////////////////////////////
// common.h
///////////////////////////////////////////////////////////////////////////
/* these types are mainly just used in headers */
typedef dReal dVector3[4];
typedef dReal dVector4[4];
typedef dReal dMatrix3[4*3];
typedef dReal dMatrix4[4*4];
typedef dReal dMatrix6[8*6];
typedef dReal dQuaternion[4];

/* joint type numbers */
enum {
  dJointTypeNone = 0,		/* or "unknown" */
  dJointTypeBall,
  dJointTypeHinge,
  dJointTypeSlider,
  dJointTypeContact,
  dJointTypeUniversal,
  dJointTypeHinge2,
  dJointTypeFixed,
  dJointTypeNull,
  dJointTypeAMotor
};

/* standard joint parameter names */
enum jointParamNames {
  dParamLoStop = 0,
  dParamHiStop,
  dParamVel,
  dParamFMax, 
  dParamFudgeFactor, 
  dParamBounce, 
  dParamCFM, 
  dParamStopERP, 
  dParamStopCFM, 
  dParamSuspensionERP, 
  dParamSuspensionCFM,
  dParamERP, 
  dParamLoStop2 = 0x100,
  dParamHiStop2,
  dParamVel2,
  dParamFMax2, 
  dParamFudgeFactor2, 
  dParamBounce2, 
  dParamCFM2, 
  dParamStopERP2, 
  dParamStopCFM2, 
  dParamSuspensionERP2, 
  dParamSuspensionCFM2,
  dParamERP2, 
  dParamLoStop3 = 0x200,
  dParamHiStop3,
  dParamVel3,
  dParamFMax3, 
  dParamFudgeFactor3, 
  dParamBounce3, 
  dParamCFM3, 
  dParamStopERP3, 
  dParamStopCFM3, 
  dParamSuspensionERP3, 
  dParamSuspensionCFM3,
  dParamERP3 
};

/* angular motor mode numbers */
enum{
  dAMotorUser = 0,
  dAMotorEuler = 1
};

/* joint force feedback information */
// TODO: test (dVector3 changed to dReal [4])
typedef struct dJointFeedback {
  // dVector3 f1;		/* force applied to body 1 */
  dReal f1[4];	
  // dVector3 t1;		/* torque applied to body 1 */
  dReal t1[4];
  // dVector3 f2;		/* force applied to body 2 */
  dReal f2[4];
  // dVector3 t2;		/* torque applied to body 2 */
  dReal t2[4];
};

//void dGeomMoved (dGeomID);
//dGeomID dGeomGetBodyNext (dGeomID);


///////////////////////////////////////////////////////////////////////////
// contact.h
///////////////////////////////////////////////////////////////////////////
enum {
  dContactMu2		= 0x001,
  dContactFDir1		= 0x002,
  dContactBounce		= 0x004,
  dContactSoftERP		= 0x008,
  dContactSoftCFM		= 0x010,
  dContactMotion1		= 0x020,
  dContactMotion2		= 0x040,
  dContactSlip1		= 0x080,
  dContactSlip2		= 0x100,
  dContactApprox0		= 0x0000,
  dContactApprox1_1	= 0x1000,
  dContactApprox1_2	= 0x2000,
  dContactApprox1		= 0x3000
};

typedef struct dSurfaceParameters {
  /* must always be defined */
  int mode;
  dReal mu;

  /* only defined if the corresponding flag is set in mode */
  dReal mu2;
  dReal bounce;
  dReal bounce_vel;
  dReal soft_erp;
  dReal soft_cfm;
  dReal motion1,motion2;
  dReal slip1,slip2;
};

/* contact info set by collision functions */
// TODO: test (dVector3 changed to dReal [4])
typedef struct dContactGeom {
  //dVector3 pos;
  dReal pos[4];
  //dVector3 normal;
  dReal normal[4];
  dReal depth;
  dGeomID g1,g2;
};

/* contact info used by contact joint */
// TODO: test (dVector3 changed to dReal [4])
typedef struct dContact {
  dSurfaceParameters surface;
  dContactGeom geom;
  //dVector3 fdir1;
  dReal fdir1[4];
};


///////////////////////////////////////////////////////////////////////////
// mass.h
///////////////////////////////////////////////////////////////////////////
struct dMass;
typedef struct dMass dMass;

void dMassSetZero (dMass *);

void dMassSetParameters (dMass *, dReal themass,
			 dReal cgx, dReal cgy, dReal cgz,
			 dReal I11, dReal I22, dReal I33,
			 dReal I12, dReal I13, dReal I23);

void dMassSetSphere (dMass *, dReal density, dReal radius);
void dMassSetSphereTotal (dMass *, dReal total_mass, dReal radius);

void dMassSetCapsule (dMass *, dReal density, int direction,
			     dReal radius, dReal length);
void dMassSetCapsuleTotal (dMass *, dReal total_mass, int direction,
				  dReal radius, dReal length);

void dMassSetCylinder (dMass *, dReal density, int direction,
		       dReal radius, dReal length);
void dMassSetCylinderTotal (dMass *, dReal total_mass, int direction,
			    dReal radius, dReal length);

void dMassSetBox (dMass *, dReal density,
		  dReal lx, dReal ly, dReal lz);
void dMassSetBoxTotal (dMass *, dReal total_mass,
		       dReal lx, dReal ly, dReal lz);

void dMassAdjust (dMass *, dReal newmass);

void dMassTranslate (dMass *, dReal x, dReal y, dReal z);

void dMassRotate (dMass *, const dMatrix3 R);

void dMassAdd (dMass *a, const dMass *b);

// TODO: test 
// -dVector4 changed to dReal [4]
// -dMatrix3 changed to dReal [4*3]
struct dMass {
  dReal mass;
  // dVector4 c;
  dReal c[4];
  // dMatrix3 I;
  dReal I[4*3];
};
%extend dMass {
  dMass * getCPtr() {
    return self;
  }
}


///////////////////////////////////////////////////////////////////////////
// matrix.h 
///////////////////////////////////////////////////////////////////////////
// TODO, add if needed


///////////////////////////////////////////////////////////////////////////
// objects.h
///////////////////////////////////////////////////////////////////////////
/* world */

dWorldID dWorldCreate();
void dWorldDestroy (dWorldID);

void dWorldSetGravity (dWorldID, dReal x, dReal y, dReal z);
void dWorldGetGravity (dWorldID, dVector3 gravity);
void dWorldSetERP (dWorldID, dReal erp);
dReal dWorldGetERP (dWorldID);
void dWorldSetCFM (dWorldID, dReal cfm);
dReal dWorldGetCFM (dWorldID);
void dWorldStep (dWorldID, dReal stepsize);
void dWorldImpulseToForce (dWorldID, dReal stepsize,
			   dReal ix, dReal iy, dReal iz, dVector3 force);

void dWorldSetAutoDisableFlag(dWorldID, int do_auto_disable);
int dWorldGetAutoDisableFlag(dWorldID);
void dWorldSetAutoDisableLinearThreshold(dWorldID, dReal linear_threshold);
dReal dWorldGetAutoDisableLinearThreshold(dWorldID);
void dWorldSetAutoDisableAngularThreshold(dWorldID, dReal angular_threshold);
dReal dWorldGetAutoDisableAngularThreshold(dWorldID);
void dWorldSetAutoDisableSteps(dWorldID, int steps);
int dWorldGetAutoDisableSteps(dWorldID);
void dWorldSetAutoDisableTime(dWorldID, dReal time);
dReal dWorldGetAutoDisableTime(dWorldID);
 
void dWorldSetContactMaxCorrectingVel(dWorldID, dReal vel);
dReal dWorldGetContactMaxCorrectingVel(dWorldID);
void dWorldSetContactSurfaceLayer(dWorldID, dReal depth);
dReal dWorldGetContactSurfaceLayer(dWorldID);

/* StepFast1 functions */
void dWorldStepFast1(dWorldID, dReal stepsize, int maxiterations);
void dWorldSetAutoEnableDepthSF1(dWorldID, int autoEnableDepth);

int dWorldGetAutoEnableDepthSF1(dWorldID);

/* StepFast functions */
void dWorldQuickStep (dWorldID, dReal stepsize);
void dWorldSetQuickStepNumIterations (dWorldID, int num);
int dWorldGetQuickStepNumIterations (dWorldID);

// Not yet implemented by ODE
//void dBodySetAutoDisableThresholdSF1(dBodyID, dReal autoDisableThreshold);

/* These functions are not yet implemented by ODE. */
/*
dReal dBodyGetAutoDisableThresholdSF1(dBodyID);

void dBodySetAutoDisableStepsSF1(dBodyID, int AutoDisableSteps);

int dBodyGetAutoDisableStepsSF1(dBodyID);

void dBodySetAutoDisableSF1(dBodyID, int doAutoDisable);

int dBodyGetAutoDisableSF1(dBodyID);
*/

/* bodies */
dBodyID dBodyCreate (dWorldID);
void dBodyDestroy (dBodyID);

void  dBodySetData (dBodyID, void *data);
void *dBodyGetData (dBodyID);

void dBodySetPosition   (dBodyID, dReal x, dReal y, dReal z);
void dBodySetRotation   (dBodyID, const dMatrix3 R);
void dBodySetQuaternion (dBodyID, const dQuaternion q);
void dBodySetLinearVel  (dBodyID, dReal x, dReal y, dReal z);
void dBodySetAngularVel (dBodyID, dReal x, dReal y, dReal z);
const dReal * dBodyGetPosition   (dBodyID);
const dReal * dBodyGetRotation   (dBodyID);	/* ptr to 4x3 rot matrix */
const dReal * dBodyGetQuaternion (dBodyID);
const dReal * dBodyGetLinearVel  (dBodyID);
const dReal * dBodyGetAngularVel (dBodyID);

void dBodySetMass (dBodyID, const dMass *mass);
void dBodyGetMass (dBodyID, dMass *mass);

void dBodyAddForce            (dBodyID, dReal fx, dReal fy, dReal fz);
void dBodyAddTorque           (dBodyID, dReal fx, dReal fy, dReal fz);
void dBodyAddRelForce         (dBodyID, dReal fx, dReal fy, dReal fz);
void dBodyAddRelTorque        (dBodyID, dReal fx, dReal fy, dReal fz);
void dBodyAddForceAtPos       (dBodyID, dReal fx, dReal fy, dReal fz,
			                dReal px, dReal py, dReal pz);
void dBodyAddForceAtRelPos    (dBodyID, dReal fx, dReal fy, dReal fz,
			                dReal px, dReal py, dReal pz);
void dBodyAddRelForceAtPos    (dBodyID, dReal fx, dReal fy, dReal fz,
			                dReal px, dReal py, dReal pz);
void dBodyAddRelForceAtRelPos (dBodyID, dReal fx, dReal fy, dReal fz,
			                dReal px, dReal py, dReal pz);

const dReal * dBodyGetForce   (dBodyID);
const dReal * dBodyGetTorque  (dBodyID);
void dBodySetForce  (dBodyID b, dReal x, dReal y, dReal z);
void dBodySetTorque (dBodyID b, dReal x, dReal y, dReal z);

void dBodyGetRelPointPos    (dBodyID, dReal px, dReal py, dReal pz,
			     dVector3 result);
void dBodyGetRelPointVel    (dBodyID, dReal px, dReal py, dReal pz,
			     dVector3 result);
void dBodyGetPointVel       (dBodyID, dReal px, dReal py, dReal pz,
			     dVector3 result);
void dBodyGetPosRelPoint    (dBodyID, dReal px, dReal py, dReal pz,
			     dVector3 result);
void dBodyVectorToWorld     (dBodyID, dReal px, dReal py, dReal pz,
			     dVector3 result);
void dBodyVectorFromWorld   (dBodyID, dReal px, dReal py, dReal pz,
			     dVector3 result);

void dBodySetFiniteRotationMode (dBodyID, int mode);
void dBodySetFiniteRotationAxis (dBodyID, dReal x, dReal y, dReal z);

int dBodyGetFiniteRotationMode (dBodyID);
void dBodyGetFiniteRotationAxis (dBodyID, dVector3 result);

int dBodyGetNumJoints (dBodyID b);
dJointID dBodyGetJoint (dBodyID, int index);

void dBodyEnable (dBodyID);
void dBodyDisable (dBodyID);
int dBodyIsEnabled (dBodyID);

void dBodySetGravityMode (dBodyID b, int mode);
int dBodyGetGravityMode (dBodyID b);

void dBodySetAutoDisableDefaults(dBodyID);
void dBodySetAutoDisableFlag(dBodyID, int do_auto_disable);
int dBodyGetAutoDisableFlag(dBodyID);
void dBodySetAutoDisableLinearThreshold(dBodyID, dReal linear_threshold);
dReal dBodyGetAutoDisableLinearThreshold(dBodyID);
void dBodySetAutoDisableAngularThreshold(dBodyID, dReal angular_threshold);
dReal dBodyGetAutoDisableAngularThreshold(dBodyID);
void dBodySetAutoDisableSteps(dBodyID, int steps);
int dBodyGetAutoDisableSteps(dBodyID);
void dBodySetAutoDisableTime(dBodyID, dReal time);
dReal dBodyGetAutoDisableTime(dBodyID);

/* joints */
dJointID dJointCreateBall (dWorldID, dJointGroupID);
dJointID dJointCreateHinge (dWorldID, dJointGroupID);
dJointID dJointCreateSlider (dWorldID, dJointGroupID);
dJointID dJointCreateContact (dWorldID, dJointGroupID, const dContact *);
dJointID dJointCreateHinge2 (dWorldID, dJointGroupID);
dJointID dJointCreateUniversal (dWorldID, dJointGroupID);
dJointID dJointCreateFixed (dWorldID, dJointGroupID);
dJointID dJointCreateNull (dWorldID, dJointGroupID);
dJointID dJointCreateAMotor (dWorldID, dJointGroupID);

void dJointDestroy (dJointID);

dJointGroupID dJointGroupCreate (int max_size);
void dJointGroupDestroy (dJointGroupID);
void dJointGroupEmpty (dJointGroupID);

void dJointAttach (dJointID, dBodyID body1, dBodyID body2);
void dJointSetData (dJointID, void *data);
void *dJointGetData (dJointID);
int dJointGetType (dJointID);
dBodyID dJointGetBody (dJointID, int index);

void dJointSetFeedback (dJointID, dJointFeedback *);
dJointFeedback *dJointGetFeedback (dJointID);

void dJointSetBallAnchor (dJointID, dReal x, dReal y, dReal z);
void dJointSetBallParam (dJointID, int parameter, dReal value);
dReal dJointGetBallParam (dJointID, int parameter);
void dJointSetHingeAnchor (dJointID, dReal x, dReal y, dReal z);
void dJointSetHingeAxis (dJointID, dReal x, dReal y, dReal z);
void dJointSetHingeParam (dJointID, int parameter, dReal value);
//void dJointAddHingeTorque(dJointID joint, dReal torque);
void dJointSetSliderAxis (dJointID, dReal x, dReal y, dReal z);
void dJointSetSliderParam (dJointID, int parameter, dReal value);
//void dJointAddSliderForce(dJointID joint, dReal force);
void dJointSetHinge2Anchor (dJointID, dReal x, dReal y, dReal z);
void dJointSetHinge2Axis1 (dJointID, dReal x, dReal y, dReal z);
void dJointSetHinge2Axis2 (dJointID, dReal x, dReal y, dReal z);
void dJointSetHinge2Param (dJointID, int parameter, dReal value);
//void dJointAddHinge2Torques(dJointID joint, dReal torque1, dReal torque2);
void dJointSetUniversalAnchor (dJointID, dReal x, dReal y, dReal z);
void dJointSetUniversalAxis1 (dJointID, dReal x, dReal y, dReal z);
void dJointSetUniversalAxis2 (dJointID, dReal x, dReal y, dReal z);
void dJointSetUniversalParam (dJointID, int parameter, dReal value);
//void dJointAddUniversalTorques(dJointID joint, dReal torque1, dReal torque2);
void dJointSetFixed (dJointID);
void dJointSetFixedParam (dJointID, int parameter, dReal value);
dReal dJointGetFixedParam (dJointID, int parameter);
void dJointSetAMotorNumAxes (dJointID, int num);
void dJointSetAMotorAxis (dJointID, int anum, int rel,
			  dReal x, dReal y, dReal z);
void dJointSetAMotorAngle (dJointID, int anum, dReal angle);
void dJointSetAMotorParam (dJointID, int parameter, dReal value);
void dJointSetAMotorMode (dJointID, int mode);
//void dJointAddAMotorTorques (dJointID, dReal torque1, dReal torque2, dReal torque3);

void dJointGetBallAnchor (dJointID, dVector3 result);
void dJointGetBallAnchor2 (dJointID, dVector3 result);
void dJointGetHingeAnchor (dJointID, dVector3 result);
void dJointGetHingeAnchor2 (dJointID, dVector3 result);
void dJointGetHingeAxis (dJointID, dVector3 result);
dReal dJointGetHingeParam (dJointID, int parameter);
dReal dJointGetHingeAngle (dJointID);
dReal dJointGetHingeAngleRate (dJointID);
dReal dJointGetSliderPosition (dJointID);
dReal dJointGetSliderPositionRate (dJointID);
void dJointGetSliderAxis (dJointID, dVector3 result);
dReal dJointGetSliderParam (dJointID, int parameter);
void dJointGetHinge2Anchor (dJointID, dVector3 result);
void dJointGetHinge2Anchor2 (dJointID, dVector3 result);
void dJointGetHinge2Axis1 (dJointID, dVector3 result);
void dJointGetHinge2Axis2 (dJointID, dVector3 result);
dReal dJointGetHinge2Param (dJointID, int parameter);
dReal dJointGetHinge2Angle1 (dJointID);
dReal dJointGetHinge2Angle1Rate (dJointID);
dReal dJointGetHinge2Angle2Rate (dJointID);
void dJointGetUniversalAnchor (dJointID, dVector3 result);
void dJointGetUniversalAnchor2 (dJointID, dVector3 result);
void dJointGetUniversalAxis1 (dJointID, dVector3 result);
void dJointGetUniversalAxis2 (dJointID, dVector3 result);
dReal dJointGetUniversalParam (dJointID, int parameter);
dReal dJointGetUniversalAngle1 (dJointID);
dReal dJointGetUniversalAngle2 (dJointID);
dReal dJointGetUniversalAngle1Rate (dJointID);
dReal dJointGetUniversalAngle2Rate (dJointID);
int dJointGetAMotorNumAxes (dJointID);
void dJointGetAMotorAxis (dJointID, int anum, dVector3 result);
int dJointGetAMotorAxisRel (dJointID, int anum);
dReal dJointGetAMotorAngle (dJointID, int anum);
dReal dJointGetAMotorAngleRate (dJointID, int anum);
dReal dJointGetAMotorParam (dJointID, int parameter);
int dJointGetAMotorMode (dJointID);

int dAreConnected (dBodyID, dBodyID);
int dAreConnectedExcluding (dBodyID, dBodyID, int joint_type);


void dRandSetSeed (long seed);

///////////////////////////////////////////////////////////////////////////
// odemath.h
///////////////////////////////////////////////////////////////////////////
// TODO, add if needed


///////////////////////////////////////////////////////////////////////////
// rotation.h
///////////////////////////////////////////////////////////////////////////

void dRSetIdentity (dMatrix3 R);

void dRFromAxisAndAngle (dMatrix3 R, dReal ax, dReal ay, dReal az,
			 dReal angle);

void dRFromEulerAngles (dMatrix3 R, dReal phi, dReal theta, dReal psi);

void dRFrom2Axes (dMatrix3 R, dReal ax, dReal ay, dReal az,
		  dReal bx, dReal by, dReal bz);

void dRFromZAxis (dMatrix3 R, dReal ax, dReal ay, dReal az);

void dQSetIdentity (dQuaternion q);

void dQFromAxisAndAngle (dQuaternion q, dReal ax, dReal ay, dReal az,
			 dReal angle);

/* Quaternion multiplication, analogous to the matrix multiplication routines. */
/* qa = rotate by qc, then qb */
void dQMultiply0 (dQuaternion qa, const dQuaternion qb, const dQuaternion qc);
/* qa = rotate by qc, then by inverse of qb */
void dQMultiply1 (dQuaternion qa, const dQuaternion qb, const dQuaternion qc);
/* qa = rotate by inverse of qc, then by qb */
void dQMultiply2 (dQuaternion qa, const dQuaternion qb, const dQuaternion qc);
/* qa = rotate by inverse of qc, then by inverse of qb */
void dQMultiply3 (dQuaternion qa, const dQuaternion qb, const dQuaternion qc);

void dQtoR (const dQuaternion q, dMatrix3 R);

void dRtoQ (const dMatrix3 R, dQuaternion q);

void dWtoDQ (const dVector3 w, const dQuaternion q, dVector4 dq);


///////////////////////////////////////////////////////////////////////////
// GeomTransformGroup.h (Contrib)
///////////////////////////////////////////////////////////////////////////
/*
dGeomID dCreateGeomTransformGroup (dSpaceID space);
// - create a GeomTransformGroup 
    
void dGeomTransformGroupAddGeom    (dGeomID tg, dGeomID obj);
//  - Comparable to dGeomTransformSetGeom or dGeomGroupAdd
//  - add objects to this group
   
void dGeomTransformGroupRemoveGeom (dGeomID tg, dGeomID obj);
//  - remove objects from this group

void dGeomTransformGroupSetRelativePosition
            (dGeomID g, dReal x, dReal y, dReal z);
void dGeomTransformGroupSetRelativeRotation
            (dGeomID g, const dMatrix3 R);
*/
/*  - Comparable to setting the position and rotation of all the
    dGeomTransform encapsulated geometry. The difference 
    is that it is global with respect to this group and therefore
    affects all geoms in this group.
  - The relative position and rotation are attributes of the 
    transformgroup, so the position and rotation of the individual
    geoms are not changed */
/*
const dReal * dGeomTransformGroupGetRelativePosition (dGeomID g);
const dReal * dGeomTransformGroupGetRelativeRotation (dGeomID g);
//  - get the relative position and rotation
  
dGeomID dGeomTransformGroupGetGeom (dGeomID tg, int i);
//  - Comparable to dGeomGroupGetGeom
//  - get a specific geom of the group
  
int dGeomTransformGroupGetNumGeoms (dGeomID tg);
//  - Comparable to dGeomGroupGetNumGeoms
//  - get the number of geoms in the group
*/

///////////////////////////////////////////////////////////////////////////
// dCylinder.h (Contrib)
///////////////////////////////////////////////////////////////////////////
dGeomID dCreateCylinder (dSpaceID space, dReal r, dReal lz);
void dGeomCylinderSetParams (dGeomID g, dReal radius, dReal length);
void dGeomCylinderGetParams (dGeomID g, dReal *radius, dReal *length);


///////////////////////////////////////////////////////////////////////////
// Terrain and Cone (Contrib)
///////////////////////////////////////////////////////////////////////////
// Added, see collision.h (above)




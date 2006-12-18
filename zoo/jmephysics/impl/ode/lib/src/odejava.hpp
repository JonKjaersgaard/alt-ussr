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

#include <ode/ode.h>

//#ifdef __cplusplus
//extern "C" {
//#endif

/*
const dSpaceID PARENTSPACEID_ZERO = 0;
const dBodyID BODYID_ZERO = 0;
const dJointGroupID JOINTGROUPID_ZERO = 0;
*/

static int surfaceMode = 0;
static float surfaceMu = 0;
static float surfaceMu2 = 0;
static float surfaceBounce = 0;
static float surfaceBounceVel = 0;
static float surfaceMotion1 = 0;
static float surfaceMotion2 = 0;
static float surfaceSlip1 = 0;
static float surfaceSlip2 = 0;
static float surfaceSoftErp = 0;
static float surfaceSoftCfm = 0;


void setWorldID(dWorldID id);
void setContactGroupID(dJointGroupID id);
// Set general surface parameters, these are set for every collision
// Can be overridden in Java
// Set surface mode, see section contact.h below
void setSurfaceMode(int mode);
// Set surface parameters
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

void plainNativeSpaceCollide(dSpaceID spaceID);

//#ifdef __cplusplus
//}
//#endif

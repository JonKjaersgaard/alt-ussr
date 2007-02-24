#include <stdio.h>
#include "nativeController.h"
#include "ussr.h"

JNIEXPORT void JNICALL Java_ussr_samples_ATRONSampleController1_nativeActivate(JNIEnv *jniENV, jobject self) {
  USSREnv env;
  env.jnienv = jniENV;
  env.controller = self;
  activate(&env);
}

/* Helper methods */

static void reportError(JNIEnv *jniEnv, char *error) {
  fprintf(stderr, "Error: %s\n", error);
}

static jobject getModule(JNIEnv *jniEnv, jobject self) {
  jfieldID module_fid; /* store the field ID */
  /* Get a reference to the controllers class */
  jclass controllerClass = (*jniEnv)->GetObjectClass(jniEnv, self);
  /* Look for the instance field module in controllerClass */
  module_fid = (*jniEnv)->GetFieldID(jniEnv, controllerClass, "module", "Lussr/module/Controller;");
  if (module_fid == NULL)
    reportError(jniEnv,"Failed to locate module field in controller class");
  /* Read the instance field module */
  return (*jniEnv)->GetObjectField(jniEnv, self, module_fid);	
}

static jobject getSimulator(JNIEnv *jniEnv, jobject self) {
  jobject module = getModule(jniEnv, self);
  jclass moduleClass = (*jniEnv)->GetObjectClass(jniEnv,module);
  jmethodID getSimulation_mid = (*jniEnv)->GetMethodID(jniEnv, moduleClass, "getSimulation", "()Lussr/physics/PhysicsSimulation");
  jobject simulator;
  if (getSimulation_mid == NULL) {
    reportError(jniEnv,"Failed to locate getSimulation method in module class");
  }
  simulator = (*jniEnv)->CallObjectMethod(jniEnv, module, getSimulation_mid);
  return simulator;
}

/* Simulator API */

#define READ_USSR_ENV(jni_var,self_var) \
  JNIEnv *jni_var = (JNIEnv*)env->jnienv; \
  jobject self_var = (jobject)env->controller;

char simulatorIsPaused(USSREnv *env) {
  READ_USSR_ENV(jniEnv,self);
  jobject simulator = getSimulator(jniEnv,self);
  
}

int getProperty(USSREnv *env, char *name, char *buffer, int count) {
  return 0;
}

void threadYield(USSREnv *env) {
  ;
}

/* ATRON API, should be moved somewhere else */

char rotate(USSREnv *env, char direction) {
  printf("ussr.samples.ATRONController.rotateContinuous not implemented(%d)\n",direction);
  return 0;
}

char isRotating(USSREnv *env) {
  printf("ussr.samples.ATRONController.isRotating not implemented\n");
  return 0;
}

/* Activate method, should be moved somewhere else */

#define NAME_MAX 128
void activate(USSREnv *env) { 
  char name[NAME_MAX];
  while(1) {
    if(!simulatorIsPaused(env)) {
      getProperty(env,"name",name,NAME_MAX);
      if(!strcmp(name,"wheel1")) rotate(env,1);
      if(!strcmp(name,"wheel2")) rotate(env,1);
      if(!strcmp(name,"wheel3")) rotate(env,1);
      if(!strcmp(name,"wheel4")) rotate(env,1);
      if(strstr(name,"snake")) {
	if(!isRotating(env)) {
	  rotate(env,1);
	  rotate(env,-1);
	}
      }
      /* missing: if(!GenericSimulation.getConnectorsAreActive()) disconnectAll(); */
    }
    threadYield(env);
  }
}

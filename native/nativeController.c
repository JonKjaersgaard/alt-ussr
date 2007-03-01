#include "nativeController.h"
#include "ussr.h"

JNIEXPORT void JNICALL Java_ussr_samples_ATRONNativeController_activate(JNIEnv *jniENV, jobject self) {
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

void controllerIterationSimulatorHook(USSREnv *env) {
  READ_USSR_ENV(jniEnv,self);
  jobject simulator = getSimulator(jniEnv,self);
  
}

int getRole(USSREnv *env) {
  return 0;
}

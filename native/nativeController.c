#include "nativeController.h"
#include "ussr.h"

#include <stdarg.h>
#include <stdio.h>

JNIEXPORT void JNICALL Java_ussr_samples_ATRONNativeController_activate(JNIEnv *jniENV, jobject self) {
  USSREnv env;
  env.jnienv = jniENV;
  env.controller = self;
  activate(&env);
}

#define READ_USSR_ENV(jni_var,self_var) \
  JNIEnv *jni_var = (JNIEnv*)env->jnienv; \
  jobject self_var = (jobject)env->controller;

/* Internal helper methods */

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

/* API helper methods */

void ussr_call_void_controller_method(USSREnv *env, char *name, char *signature, ...) {
  READ_USSR_ENV(jniEnv,self);
  va_list parameters;
  /* Find method ID */
  jclass controllerClass = (*jniEnv)->GetObjectClass(jniEnv, self);
  jmethodID mid = (*jniEnv)->GetMethodID(jniEnv, controllerClass, name, signature);
  if (mid == NULL) {
    reportError(jniEnv,"Failed to locate method in controller class");
  }
  /* Find parameters */
  va_start(parameters, signature);
  /* Call Java method */
  (*jniEnv)->CallVoidMethodV(jniEnv, self, mid, parameters);
}

int ussr_call_int_controller_method(USSREnv *env, char *name, char *signature, ...) {
  READ_USSR_ENV(jniEnv,self);
  va_list parameters;
  /* Find method ID */
  jclass controllerClass = (*jniEnv)->GetObjectClass(jniEnv, self);
  jmethodID mid = (*jniEnv)->GetMethodID(jniEnv, controllerClass, name, signature);
  if (mid == NULL) {
    reportError(jniEnv,"Failed to locate method in controller class");
  }
  /* Find parameters */
  va_start(parameters, signature);
  /* Call Java method */
  int result = (*jniEnv)->CallIntMethodV(jniEnv, self, mid, parameters);
  return result;
}

/* Simulator API */

void controllerIterationSimulatorHook(USSREnv *env) {
  ussr_call_void_controller_method(env, "iterationSimulatorHook", "()V");
}

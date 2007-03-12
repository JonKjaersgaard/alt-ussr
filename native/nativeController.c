#include "nativeController.h"
#include "ussr.h"

#include <stdarg.h>
#include <stdio.h>

JNIEXPORT void JNICALL Java_ussr_samples_atron_ATRONNativeController_activate(JNIEnv *jniENV, jobject self) {
  USSREnv env;
  env.jnienv = jniENV;
  env.controller = self;
  activate(&env);
}

#define READ_USSR_ENV(jni_var,self_var) \
  JNIEnv *jni_var = (JNIEnv*)env->jnienv; \
  jobject self_var = (jobject)env->controller;

/* Internal helper methods */

static void reportError(JNIEnv *jniEnv, char *error, char *name, char *type) {
  fprintf(stderr, "Error: %s (%s of type %s)\n", error, name, type);
}

#define MODULE_FIELD_NAME "module"
#define MODULE_FIELD_TYPE "Lussr/module/Controller;"
static jobject getModule(JNIEnv *jniEnv, jobject self) {
  jfieldID module_fid; /* store the field ID */
  /* Get a reference to the controllers class */
  jclass controllerClass = (*jniEnv)->GetObjectClass(jniEnv, self);
  /* Look for the instance field module in controllerClass */
  module_fid = (*jniEnv)->GetFieldID(jniEnv, controllerClass, MODULE_FIELD_NAME, MODULE_FIELD_TYPE);
  if (module_fid == NULL)
    reportError(jniEnv,"Failed to locate module field in controller class",MODULE_FIELD_NAME, MODULE_FIELD_TYPE);
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
    reportError(jniEnv,"Failed to locate method in controller class", name, signature);
  }
  /* Find parameters */
  va_start(parameters, signature);
  /* Call Java method */
  (*jniEnv)->CallVoidMethodV(jniEnv, self, mid, parameters);
}

#define MAKE_USSR_CALL_ANY_CONTROLLER_METHOD(RETURNTYPE,METHODNAME) RETURNTYPE METHODNAME(USSREnv *env, char *name, char *signature, ...) {\
  READ_USSR_ENV(jniEnv,self);\
  va_list parameters;\
  jclass controllerClass = (*jniEnv)->GetObjectClass(jniEnv, self);\
  jmethodID mid = (*jniEnv)->GetMethodID(jniEnv, controllerClass, name, signature);\
  if (mid == NULL) {\
    reportError(jniEnv,"Failed to locate method in controller class", name, signature);\
  }\
  va_start(parameters, signature);\
  RETURNTYPE result = (*jniEnv)->CallIntMethodV(jniEnv, self, mid, parameters);\
  return result;\
}

MAKE_USSR_CALL_ANY_CONTROLLER_METHOD(int,ussr_call_int_controller_method)
MAKE_USSR_CALL_ANY_CONTROLLER_METHOD(char,ussr_call_byte_controller_method)

jbyteArray ussr_charArray2byteArray(USSREnv *env, unsigned char *message, unsigned char messageSize) {
  READ_USSR_ENV(jniEnv,self);
  jbyte *buffer;
  int i;
  jbyteArray array = (*jniEnv)->NewByteArray(jniEnv, messageSize);
  if(array==NULL) {
    reportError(jniEnv,"Failed to allocate memory for byte array", "ussr_charArray2byteArray", "<>");
    return NULL;
  }
  buffer = (*jniEnv)->GetPrimitiveArrayCritical(jniEnv, array, 0);
  if(buffer==NULL) {
    reportError(jniEnv,"Failed to allocate memory for buffer", "ussr_charArray2byteArray", "<>");
    return NULL;
  }  
  for(i=0; i<messageSize; i++)
    buffer[i] = message[i];
  (*jniEnv)->ReleasePrimitiveArrayCritical(jniEnv, array, buffer, 0);
  return array;
}

void ussr_releaseByteArray(USSREnv *env, jbyteArray array) {
  READ_USSR_ENV(jniEnv,self);
  (*jniEnv)->DeleteLocalRef(jniEnv, array);
}

void ussr_byteArray2charArray(USSREnv *env, jbyteArray message, unsigned char messageSize, unsigned char *buffer) {
  READ_USSR_ENV(jniEnv,self);
  int i;
  jbyte *tmp_buffer;
  /* Sanity check: array length */
  if((*jniEnv)->GetArrayLength(jniEnv, message) > messageSize) {
    reportError(jniEnv,"Failed to allocate memory for buffer", "ussr_byteArray2charArray", "<>");
    return;
  }
  /* OK, proceed */
  tmp_buffer = (*jniEnv)->GetPrimitiveArrayCritical(jniEnv, message, 0);
  if(tmp_buffer==NULL) {
    reportError(jniEnv,"Failed to allocate memory for buffer", "ussr_byteArray2charArray", "<>");
    return;
  }
  for(i=0; i<messageSize; i++)
    buffer[i] = tmp_buffer[i];
  (*jniEnv)->ReleasePrimitiveArrayCritical(jniEnv, message, tmp_buffer, 0);
}

/* Simulator API */

void controllerIterationSimulatorHook(USSREnv *env) {
  ussr_call_void_controller_method(env, "iterationSimulatorHook", "()V");
}

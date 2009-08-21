#include <nativeController.h>
#include <ussr.h>

#include <stdarg.h>
#include <stdio.h>

#define CHECK_EXN if((*jniEnv)->ExceptionCheck(jniEnv)) { (*jniEnv)->ExceptionDescribe(jniEnv); (*jniEnv)->ExceptionClear(jniEnv); exit(1); }

JNIEXPORT jint JNICALL Java_ussr_model_NativeController_nativeInitialize(JNIEnv *jniEnv, jobject self) {
  USSREnv env;
  env.jnienv = jniEnv;
  env.controller = self;
  env.context = 0;
  return initialize(&env);
}

JNIEXPORT void JNICALL Java_ussr_model_NativeController_nativeActivate(JNIEnv *jniEnv, jobject self, jint initializationContext) {
  USSREnv env;
  env.jnienv = jniEnv;
  env.controller = self;
  env.context = initializationContext;
  /* Wait for simulator to be unpaused, otherwise system becomes unstable (e.g., JNI method lookup fails incorrectly). Maybe problem with unchecked exceptions bug? */
  controllerIterationSimulatorHook(&env,0);
  activate(&env);
}

#define READ_USSR_ENV(jni_var,self_var) \
  JNIEnv *jni_var = (JNIEnv*)env->jnienv; \
  jobject self_var = (jobject)env->controller;

/* Internal helper methods */

static void reportError(JNIEnv *jniEnv, char *error, char *name, char *type) {
  fprintf(stderr, "Error: %s (%s of type %s)\n", error, name, type);
  exit(1);
}

/* API helper methods */

void ussr_call_void_controller_method(USSREnv *env, char *name, char *signature, ...) {
  READ_USSR_ENV(jniEnv,self);
  va_list parameters;
  jmethodID get_internal_controller_mid; /* store the field ID */
  /* Find controller object */
  jobject controller;
  jclass holderClass = (*jniEnv)->GetObjectClass(jniEnv, self);
  /* Look for the instance field controller in controllerClass */
  get_internal_controller_mid = (*jniEnv)->GetMethodID(jniEnv,holderClass,"getInternalController","()Lussr/model/Controller;");
  if(get_internal_controller_mid==NULL) {
  	reportError(jniEnv,"Could not find method for getting internal controller","getInternalController","()Lussr/model/Controller;");
  }
  controller = (*jniEnv)->CallObjectMethod(jniEnv, self, get_internal_controller_mid);
  CHECK_EXN;
  if(controller == NULL) {
  	reportError(jniEnv,"Lookup of controller gave NULL", "getInternalController","()Lussr/model/Controller;");
  }
  /* Find method ID */
  jclass controllerClass = (*jniEnv)->GetObjectClass(jniEnv, controller);
  jmethodID mid = (*jniEnv)->GetMethodID(jniEnv, controllerClass, name, signature);
  if (mid == NULL) {
    reportError(jniEnv,"Failed to locate method in controller class", name, signature);
  }
  /* Find parameters */
  va_start(parameters, signature);
  /* Call Java method */
  (*jniEnv)->CallVoidMethodV(jniEnv, controller, mid, parameters);
  /* Check for exceptions */
  if((*jniEnv)->ExceptionCheck(jniEnv)) {
    (*jniEnv)->ExceptionDescribe(jniEnv);
    (*jniEnv)->ExceptionClear(jniEnv);
    reportError(jniEnv,"Exception occurred during method evaluation", name, signature);
  }
}

#define MAKE_USSR_CALL_ANY_CONTROLLER_METHOD(RETURNTYPE,METHODNAME) RETURNTYPE METHODNAME(USSREnv *env, char *name, char *signature, ...) {\
  READ_USSR_ENV(jniEnv,self);\
  va_list parameters;\
  jmethodID get_internal_controller_mid;\
  jobject controller;\
  jclass holderClass = (*jniEnv)->GetObjectClass(jniEnv,self);\
  get_internal_controller_mid = (*jniEnv)->GetMethodID(jniEnv,holderClass,"getInternalController","()Lussr/model/Controller;");\
  if(get_internal_controller_mid==NULL) {\
  	reportError(jniEnv,"Could not find method for getting internal controller","getInternalController","()Lussr/model/Controller;");\
  }\
  controller = (*jniEnv)->CallObjectMethod(jniEnv, self, get_internal_controller_mid);\
  CHECK_EXN;\
  if(controller == NULL) {\
  	reportError(jniEnv,"Lookup of controller gave NULL", "getInternalController","()Lussr/model/Controller;");\
  }\
  jclass controllerClass = (*jniEnv)->GetObjectClass(jniEnv, controller);\
  jmethodID mid = (*jniEnv)->GetMethodID(jniEnv, controllerClass, name, signature);\
  if (mid == NULL) {\
    reportError(jniEnv,"Failed to locate method in controller class", name, signature);\
  }\
  va_start(parameters, signature);\
  RETURNTYPE result = (*jniEnv)->CallIntMethodV(jniEnv, controller, mid, parameters);\
  if((*jniEnv)->ExceptionCheck(jniEnv)) {\
    (*jniEnv)->ExceptionDescribe(jniEnv);\
    (*jniEnv)->ExceptionClear(jniEnv);\
    reportError(jniEnv,"Exception occurred during method evaluation", name, signature);\
  }\
  return result;\
}

MAKE_USSR_CALL_ANY_CONTROLLER_METHOD(int,ussr_call_int_controller_method)
MAKE_USSR_CALL_ANY_CONTROLLER_METHOD(char,ussr_call_byte_controller_method)
MAKE_USSR_CALL_ANY_CONTROLLER_METHOD(int64_t,ussr_call_long_controller_method)

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

void controllerIterationSimulatorHook(USSREnv *env, unsigned char waitForEvent) {
  READ_USSR_ENV(jniEnv,self);
  jclass nativeClass = (*jniEnv)->GetObjectClass(jniEnv, self);
  jmethodID mid = (*jniEnv)->GetMethodID(jniEnv, nativeClass, "iterationSimulatorHook", "(Z)V");
  if (mid == NULL) {
    reportError(jniEnv,"Failed to locate method in native controller class", "iterationSimulatorHook", "(Z)V");
  }
  (*jniEnv)->CallVoidMethod(jniEnv, self, mid, (int)waitForEvent);
  /* Check for exceptions */
  if((*jniEnv)->ExceptionCheck(jniEnv)) {
    (*jniEnv)->ExceptionDescribe(jniEnv);
    (*jniEnv)->ExceptionClear(jniEnv);
    reportError(jniEnv,"Exception occurred during method simulator hook", "iterationSimulatorHook", "(Z)V");
  }
}

void disable_interrupts() {
  //printf("Warning: interrupts not disabled!\n");
}

void enable_interrupts() { ; }

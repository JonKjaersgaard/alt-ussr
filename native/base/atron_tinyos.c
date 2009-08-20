#include <atron_tinyos.h>
#include <ussr_internal.h>

#include <nativeController.h>

#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>


void activate(USSRONLY(USSREnv *env)) {
  int moduleId = ussr_call_int_controller_method(env, "getRole", "()I");/* the old "role" */

  /* dispatcher, to be possibly auto-generated */
  if (moduleId == 0)
    main_0(env);
  else if (moduleId == 1)
    main_1(env);
  /* --- */

}

int32_t initialize(USSRONLY(USSREnv *env)) { }

/* API downcalls/commands */
uint32_t sendMessage(USSREnv *env, uint8_t *message, int32_t messageSize, int32_t connector) {
  jbyteArray array = ussr_charArray2byteArray(env, message, messageSize);
  //  char result = ussr_call_byte_controller_method(env, "sendMessage", "([BBB)B", array, messageSize, connector);
  uint32_t result = ussr_call_int_controller_method(env, "sendMessage", "([BII)I", array, messageSize, connector);
  ussr_releaseByteArray(env, array);
  return result;
}
/* API upcalls/event */
void JNICALL Java_ussr_samples_atron_natives_ATRONNativeTinyOSController_nativeSendDone(JNIEnv *jniENV, jobject self, jint initializationContext, /**/jint error, jint connector) {
  int moduleId;
  USSREnv env;
  env.jnienv = jniENV;
  env.controller = self;
  env.context = initializationContext;

  moduleId = ussr_call_int_controller_method(&env, "getRole", "()I");/* the old "role" */

  /* dispatcher, to be possibly auto-generated */
  if (moduleId == 0)
    sendDone_0(  &env,   error, connector);
  else if (moduleId == 1)
    sendDone_1(  &env,   error, connector);
  /* --- */

}
void JNICALL Java_ussr_samples_atron_natives_ATRONNativeTinyOSController_nativeHandleMessage(JNIEnv *jniENV, jobject self, jint initializationContext, jbyteArray message, jint messageSize, jint channel) {
  int moduleId;
  unsigned char buffer[ATRON_MAX_MESSAGE_SIZE];
  USSREnv env;
  env.jnienv = jniENV;
  env.controller = self;
  env.context = initializationContext;

  moduleId = ussr_call_int_controller_method(&env, "getRole", "()I");/* the old "role" */

  ussr_byteArray2charArray(&env, message, messageSize, buffer);

  /* dispatcher, to be possibly auto-generated */
  if (moduleId == 0)
    handleMessage_0(&env, buffer, messageSize, channel);
  else if (moduleId == 1)
    handleMessage_1(&env, buffer, messageSize, channel);
  /* --- */

}


#include "atron.h"
#include "ussr_internal.h"

#include "nativeController.h"

void rotate(USSREnv *env, int direction) {
  ussr_call_void_controller_method(env, "rotate", "(I)V", direction);
}

void rotateContinuous(USSREnv *env, int direction) {
  ussr_call_void_controller_method(env, "rotateContinuous", "(F)V", (float)direction);
}

int getRole(USSREnv *env) {
  return ussr_call_int_controller_method(env, "getRole", "()I");
}

char sendMessage(USSREnv *env, unsigned char *message, unsigned char messageSize, unsigned char connector) {
  jbyteArray array = ussr_charArray2byteArray(env, message, messageSize);
  char result = ussr_call_byte_controller_method(env, "sendMessage", "([BBB)B", array, messageSize, connector);
  ussr_releaseByteArray(env, array);
  return result;
}

void JNICALL Java_ussr_samples_atron_ATRONNativeController_nativeHandleMessage(JNIEnv *jniENV, jobject self, jbyteArray message, jint messageSize, jint channel) {
  unsigned char buffer[MAX_MESSAGE_SIZE];
  USSREnv env;
  env.jnienv = jniENV;
  env.controller = self;
  ussr_byteArray2charArray(&env, message, messageSize, buffer);
  handleMessage(&env, buffer, messageSize, channel);
}



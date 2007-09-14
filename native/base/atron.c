#include <atron.h>
#include <ussr_internal.h>

#include <nativeController.h>

#include <stdlib.h>

void rotate(USSREnv *env, int direction) {
  ussr_call_void_controller_method(env, "rotate", "(I)V", direction);
}

void rotateDegrees(USSREnv *env, int degrees) {
  ussr_call_void_controller_method(env, "rotateDegrees", "(I)V", degrees);
}

void rotateToDegree(USSREnv *env, int degree) {
  ussr_call_void_controller_method(env, "rotateToDegreeInDegrees", "(I)V", degree);
}

int get_angular_position(USSREnv *env, int degree) {
  return ussr_call_int_controller_method(env, "getAngularPositionDegrees", "()I");
}

void rotateContinuously(USSREnv *env, int direction) {
  ussr_call_void_controller_method(env, "rotateContinuous", "(F)V", (float)direction);
}

void centerBrake(USSREnv *env) {
  ussr_call_void_controller_method(env, "centerBrake", "()V");
}

void centerStop(USSREnv *env) {
  ussr_call_void_controller_method(env, "centerStop", "()V");
}

int getRole(USSREnv *env) {
  return ussr_call_int_controller_method(env, "getRole", "()I");
}

unsigned char isOtherConnectorNearby(USSREnv *env, unsigned char connector) {
  return ussr_call_int_controller_method(env, "isOtherConnectorNearby", "(I)Z", (int)connector);
}

unsigned char getJointPosition(USSREnv *env) {
  return ussr_call_int_controller_method(env, "getJointPosition", "()I");
}

unsigned char isObjectNearby(USSREnv *env, unsigned char connector) {
  return ussr_call_int_controller_method(env, "isObjectNearby", "(I)Z", (int)connector);
}

char sendMessage(USSREnv *env, unsigned char *message, unsigned char messageSize, unsigned char connector) {
  jbyteArray array = ussr_charArray2byteArray(env, message, messageSize);
  char result = ussr_call_byte_controller_method(env, "sendMessage", "([BBB)B", array, messageSize, connector);
  ussr_releaseByteArray(env, array);
  return result;
}

void JNICALL Java_ussr_samples_atron_natives_ATRONNativeController_nativeHandleMessage(JNIEnv *jniENV, jobject self, jint initializationContext, jbyteArray message, jint messageSize, jint channel) {
  unsigned char buffer[ATRON_MAX_MESSAGE_SIZE];
  USSREnv env;
  env.jnienv = jniENV;
  env.controller = self;
  env.context = initializationContext;
  ussr_byteArray2charArray(&env, message, messageSize, buffer);
  handleMessage(&env, buffer, messageSize, channel);
}

void delay(int amount) {
#ifdef WIN32
  fprintf(stderr,"Warning: cannot sleep\n");
#else
  sleep(amount/100);
#endif
}

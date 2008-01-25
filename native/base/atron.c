#include <atron.h>
#include <ussr_internal.h>

#include <nativeController.h>

#include <stdlib.h>
#include <stdio.h>

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

char disconnect(USSRONLYC(USSREnv *env) unsigned char connector) {
  ussr_call_void_controller_method(env, "disconnect", "(I)V", (int)connector);
  return 0; /* NO_ERROR, TODO: return the value returned by the API */
}

char _connect(USSRONLYC(USSREnv *env) unsigned char connector) {
  ussr_call_void_controller_method(env, "connect", "(I)V", (int)connector);
  return 0; /* NO_ERROR, TODO: return the value returned by the API */
}

char isConnected(USSRONLYC(USSREnv *env) unsigned char connector) {
  return ussr_call_int_controller_method(env, "isConnected", "(I)Z", (int)connector);
}

char isDisconnected(USSRONLYC(USSREnv *env) unsigned char connector) {
  return ussr_call_int_controller_method(env, "isDisconnected", "(I)Z", (int)connector);
}

char isRotating(USSRONLY(USSREnv *env)) {
  return ussr_call_int_controller_method(env, "isRotating", "()Z");
}

char setNorthIOPort(USSRONLYC(USSREnv *env) unsigned char ledbits) {
  printf("[NIOP on %d = %d]", getRole(USSRONLY(env)),  ledbits); fflush(stdout);
  return 0; /* TODO: simulate LEDs */
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

void delay(USSRONLYC(USSREnv *env) int amount) {
  printf("<delay(%d)>", amount);
  ussr_call_void_controller_method(env, "delay_internal", "(I)V", amount);
}

void setup(USSREnv *env) {
  ussr_call_void_controller_method(env, "setup", "()V");
}

void home(USSREnv *env) {
  ussr_call_void_controller_method(env, "home", "()V");
}


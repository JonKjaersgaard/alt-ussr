#include <atron.h>
#include <ussr_internal.h>

#include <nativeController.h>

#include <stdlib.h>
#include <stdio.h>

void rotate(USSREnv *env, int direction) {
  printf("rotate(%d)\n", direction);
}

void rotateDegrees(USSREnv *env, int degrees) {
  printf("rotateDegrees(%d)\n", degrees);
}

void rotateToDegree(USSREnv *env, int degree) {
  printf("rotateToDegreeInDegrees(%d)\n", degree);
}

int get_angular_position(USSREnv *env, int degree) {
  printf("getAngularPositionDegrees\n");
}

void rotateContinuously(USSREnv *env, int direction) {
  printf("rotateContinuous(%d)\n", (float)direction);
}

void centerBrake(USSREnv *env) {
  printf("centerBrake()\n");
}

void centerStop(USSREnv *env) {
  printf("centerStop(%)\n");
}

int getRole(USSREnv *env) {
  printf("getRole\n");
}

unsigned char isOtherConnectorNearby(USSREnv *env, unsigned char connector) {
  printf("isOtherConnectorNearby(%d)\n", (int)connector);
}

unsigned char getJointPosition(USSREnv *env) {
  printf("getJointPosition()\n");
}

unsigned char isObjectNearby(USSREnv *env, unsigned char connector) {
  printf("isObjectNearby(%d)\n", (int)connector);
}

char disconnect(USSRONLYC(USSREnv *env) unsigned char connector) {
  printf("disconnect(%d)\n", (int)connector);
  return 0; /* NO_ERROR, TODO: return the value returned by the API */
}

char _connect(USSRONLYC(USSREnv *env) unsigned char connector) {
  printf("connect(%d)\n", (int)connector);
  return 0; /* NO_ERROR, TODO: return the value returned by the API */
}

char isConnected(USSRONLYC(USSREnv *env) unsigned char connector) {
  printf("isConnected(%d)\n", (int)connector);
}

char isDisconnected(USSRONLYC(USSREnv *env) unsigned char connector) {
  printf("isDisconnected(%d)\n", (int)connector);
}

char isRotating(USSRONLY(USSREnv *env)) {
  printf("isRotating()\n");
}

char setNorthIOPort(USSRONLYC(USSREnv *env) unsigned char ledbits) {
  printf("[NIOP on %d = %d]", getRole(USSRONLY(env)),  ledbits); fflush(stdout);
  return 0; /* TODO: simulate LEDs */
}

char sendMessage(USSRONLYC(USSREnv *env) unsigned char *message, unsigned char messageSize, unsigned char connector) {
  printf("sendMessage(%d,%d)\n", messageSize, connector);
  return result;
}

void EXTERNAL_handleMessage(USSRONLYC(USSREnv *env) unsigned char *message, unsigned char messageSize, unsigned char channel) {
  handleMessage(env, buffer, messageSize, channel);
}

void delay(USSRONLYC(USSREnv *env) int amount) {
  printf("<delay(%d)>", amount);
}

void setup(USSREnv *env) {
  printf("setup()\n");
}

void home(USSREnv *env) {
  printf("home()\n");
}


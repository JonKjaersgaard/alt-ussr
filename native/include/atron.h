#ifndef ATRON_H_
#define ATRON_H_

#include <ussr.h>

#define ATRON_MAX_MESSAGE_SIZE 128

#ifdef USSR

int getRole(USSRONLY(USSREnv *env));
void rotate(USSRONLYC(USSREnv *env) int direction);
void rotateContinuously(USSRONLYC(USSREnv *env) int direction);
void rotateDegrees(USSREnv *env, int degrees);
void rotateToDegree(USSREnv *env, int degree);
void handleMessage(USSRONLYC(USSREnv *env) unsigned char* message, unsigned char messageSize, unsigned char channel);
char sendMessage(USSRONLYC(USSREnv *env) unsigned char *message, unsigned char messageSize, unsigned char connector);
unsigned char isOtherConnectorNearby(USSRONLYC(USSREnv *env) unsigned char connector);
unsigned char getJointPosition(USSRONLY(USSREnv *env));
void centerBrake(USSRONLY(USSREnv *env));
void centerStop(USSRONLY(USSREnv *env));
unsigned char isObjectNearby(USSRONLYC(USSREnv *env) unsigned char connector);
void setup(USSRONLY(USSREnv *env));
void home(USSRONLY(USSREnv *env));
char disconnect(USSRONLYC(USSREnv *env) unsigned char connector);
char isConnected(USSRONLYC(USSREnv *env) unsigned char connector);
char isDisconnected(USSRONLYC(USSREnv *env) unsigned char connector);
char isRotating(USSRONLY(USSREnv *env));
char setNorthIOPort(USSRONLYC(USSREnv *env) unsigned char ledbits);
char _connect(USSRONLYC(USSREnv *env) unsigned char connector);
void delay(USSRONLYC(USSREnv *env) int amount);

#else

#include <api/API.h>

#endif


#endif /*ATRON_H_*/

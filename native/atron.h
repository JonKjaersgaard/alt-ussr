#ifndef ATRON_H_
#define ATRON_H_

#include "ussr.h"

#define MAX_MESSAGE_SIZE 128

int getRole(USSRONLY(USSREnv *env));
void rotate(USSRONLYC(USSREnv *env) int direction);
void rotateContinuous(USSRONLYC(USSREnv *env) int direction);
void handleMessage(USSRONLYC(USSREnv *env) unsigned char* message, unsigned char messageSize, unsigned char channel);
char sendMessage(USSRONLYC(USSREnv *env) unsigned char *message, unsigned char messageSize, unsigned char connector);

#endif /*ATRON_H_*/

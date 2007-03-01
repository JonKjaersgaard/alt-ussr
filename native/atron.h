#ifndef ATRON_H_
#define ATRON_H_

#include "ussr.h"

int getRole(USSRONLY(USSREnv *env));
void rotate(USSRONLYC(USSREnv *env) int direction);
void rotateContinuous(USSRONLYC(USSREnv *env) int direction);

#endif /*ATRON_H_*/

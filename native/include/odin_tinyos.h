#ifndef ODIN_TINYOS_H_
#define ODIN_TINYOS_H_

#include <ussr.h>

/*TODO: link it to TOSH_DATA_LENGTH*/
#define USSR_MAX_MESSAGE_SIZE 128

#ifdef USSR
void ussr_stub();
int getRandomNumber(USSREnv *env);
int moduleType(USSREnv *env);
void setActuatorSpeed(USSREnv* env, int8_t value);
int getActuatorPosition(USSREnv* env);
int32_t sendMessage(USSRONLYC(USSREnv *env) uint8_t *message, int32_t messageSize, int32_t connector);
#else

#ifdef EMPTY_ODIN_API

#include "odin.h"

#else

#include <api/API.h>

#endif

#endif


#endif /*ODIN_TINYOS_H_*/

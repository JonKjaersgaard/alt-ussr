#ifndef ATRON_TINYOS_H_
#define ATRON_TINYOS_H_

#include <ussr.h>

/*TODO: link it to TOSH_DATA_LENGTH*/
#define ATRON_MAX_MESSAGE_SIZE 128

#ifdef USSR
void ussr_stub();
void printf_to_system_out_print(USSREnv *env, char* buf);
int getRandomNumber(USSREnv *env);
int32_t sendMessage(USSRONLYC(USSREnv *env) uint8_t *message, int32_t messageSize, int32_t connector);
void setSpeedCentralJoint(USSREnv *env, int32_t speed);
int32_t getCentralJointEncoderValue(USSREnv *env);
void setPositionCentralJoint(USSREnv *env, int32_t position);//this does not really work ... !
int32_t isConnected(USSREnv *env, uint8_t connector);
int32_t isDisconnected(USSREnv *env, uint8_t connector);
void connect(USSREnv *env, uint8_t connector);
void disconnect(USSREnv *env, uint8_t connector);
int32_t readProximitySensor(USSREnv *env, uint8_t connector);
float getSouthRotationW(USSREnv *env);
float getSouthRotationX(USSREnv *env);
float getSouthRotationY(USSREnv *env);
float getSouthRotationZ(USSREnv *env);
float getSouthTranslationX(USSREnv *env);
float getSouthTranslationY(USSREnv *env);
float getSouthTranslationZ(USSREnv *env);
float getNorthRotationW(USSREnv *env);
float getNorthRotationX(USSREnv *env);
float getNorthRotationY(USSREnv *env);
float getNorthRotationZ(USSREnv *env);
float getNorthTranslationX(USSREnv *env);
float getNorthTranslationY(USSREnv *env);
float getNorthTranslationZ(USSREnv *env);
float getGlobalPosition(USSREnv *env, int conn, int comp);
#else

#ifdef EMPTY_ATRON_API

#include "atron.h"

#else

#include <api/API.h>

#endif

#endif


#endif /*ATRON_TINYOS_H_*/

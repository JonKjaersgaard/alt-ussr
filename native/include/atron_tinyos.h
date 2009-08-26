#ifndef ATRON_TINYOS_H_
#define ATRON_TINYOS_H_

#include <ussr.h>

/*TODO: link it to TOSH_DATA_LENGTH*/
#define ATRON_MAX_MESSAGE_SIZE 128

#ifdef USSR

void printf_to_system_out_print(USSREnv *env, char* buf);
int32_t sendMessage(USSRONLYC(USSREnv *env) uint8_t *message, int32_t messageSize, int32_t connector);

#else

#ifdef EMPTY_ATRON_API

#include "atron.h"

#else

#include <api/API.h>

#endif

#endif


#endif /*ATRON_TINYOS_H_*/

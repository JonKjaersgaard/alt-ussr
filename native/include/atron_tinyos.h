#ifndef ATRON_TINYOS_H_
#define ATRON_TINYOS_H_

#include <ussr.h>

#define ATRON_MAX_MESSAGE_SIZE 128

#ifdef USSR

/* the plain C version of the ATRON TinyOS API */

char sendMessage(USSRONLYC(USSREnv *env) unsigned char *message, unsigned char messageSize, unsigned char connector);

#else

#ifdef EMPTY_ATRON_API

#include "atron.h"

#else

#include <api/API.h>

#endif

#endif


#endif /*ATRON_TINYOS_H_*/

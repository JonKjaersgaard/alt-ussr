#ifndef ATRON_TINYOS_H_
#define ATRON_TINYOS_H_

#include <ussr.h>

//TODO: link it to TOSH_DATA_LENGTH
#define ATRON_MAX_MESSAGE_SIZE 128

char sendMessage(USSRONLYC(USSREnv *env) unsigned char *message, unsigned char messageSize, unsigned char connector);


#ifdef USSR

/* TinyOS includes, eventually customized for non-nesc compilation */


#else

#ifdef EMPTY_ATRON_API

#include "atron.h"

#else

#include <api/API.h>

#endif

#endif


#endif /*ATRON_TINYOS_H_*/

#ifndef ATRON_H_
#define ATRON_H_

#include <ussr.h>

#define ATRON_MAX_MESSAGE_SIZE 128

#ifdef USSR

// ?

#else

#ifdef EMPTY_ATRON_API

#include "atron.h"

#else

#include <api/API.h>

#endif

#endif


#endif /*ATRON_H_*/

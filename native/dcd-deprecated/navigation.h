#ifndef __DCD_NAVIGATION__
#define __DCD_NAVIGATION__
#include "config.h"

#include <ussr.h>
#include "dcdTypes.h"

extern uint8_t compute_receiver_coordinates(USSRONLYC(USSREnv *env) uint8_t virtual_channel, int8_t *x, int8_t *y, int8_t *z, uint8_t *r);
extern uint8_t virtual2physical(USSRONLYC(USSREnv *env) InterpreterContext *context, uint8_t virtual_channel);

#endif

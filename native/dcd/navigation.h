#include <ussr.h>
#include "dcdTypes.h"

extern unsigned char compute_receiver_coordinates(USSRONLYC(USSREnv *env) unsigned char virtual_channel, signed char *x, signed char *y, signed char *z, unsigned char *r);
extern unsigned char virtual2physical(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char virtual_channel);

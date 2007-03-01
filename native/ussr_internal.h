#ifndef _USSR_INTERNAL_H_
#define _USSR_INTERNAL_H_

#include "ussr.h"

extern void ussr_call_void_controller_method(USSREnv *env, char *name, char *signature, ...);
extern int ussr_call_int_controller_method(USSREnv *env, char *name, char *signature);

#endif /* _USSR_INTERNAL_H_ */

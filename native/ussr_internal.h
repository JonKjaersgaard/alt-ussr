#ifndef _USSR_INTERNAL_H_
#define _USSR_INTERNAL_H_

#include "ussr.h"
#include <jni.h>

extern void ussr_call_void_controller_method(USSREnv *env, char *name, char *signature, ...);
extern int ussr_call_int_controller_method(USSREnv *env, char *name, char *signature, ...);
extern int ussr_call_byte_controller_method(USSREnv *env, char *name, char *signature, ...);
extern jbyteArray ussr_charArray2byteArray(USSREnv *env, unsigned char *message, unsigned char messageSize);
extern void ussr_byteArray2charArray(USSREnv *env, jbyteArray message, unsigned char messageSize, unsigned char *buffer);
extern void ussr_releaseByteArray(USSREnv *env, jbyteArray array);

#endif /* _USSR_INTERNAL_H_ */

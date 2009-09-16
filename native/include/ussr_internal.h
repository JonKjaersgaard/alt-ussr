#ifndef _USSR_INTERNAL_H_
#define _USSR_INTERNAL_H_

#include <ussr.h>
#include <jni.h>
#include "stdint.h"

extern void ussr_call_void_controller_method(USSREnv *env, char *name, char *signature, ...);
extern int ussr_call_int_controller_method(USSREnv *env, char *name, char *signature, ...);
extern int ussr_call_byte_controller_method(USSREnv *env, char *name, char *signature, ...);
extern int64_t ussr_call_long_controller_method(USSREnv *env, char *name, char *signature, ...);
extern float ussr_call_float_controller_method(USSREnv *env, char *name, char *signature, ...);
extern double ussr_call_double_controller_method(USSREnv *env, char *name, char *signature, ...);
extern jbyteArray ussr_charArray2byteArray(USSREnv *env, unsigned char *message, unsigned char messageSize);
extern void ussr_byteArray2charArray(USSREnv *env, jbyteArray message, unsigned char messageSize, unsigned char *buffer);
extern void ussr_releaseByteArray(USSREnv *env, jbyteArray array);
extern jstring ussr_charArray2string(USSREnv *env, char *message);

#endif /* _USSR_INTERNAL_H_ */

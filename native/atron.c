#include "atron.h"
#include "ussr_internal.h"

void rotate(USSRONLYC(USSREnv *env) int direction) {
  ussr_call_void_controller_method(env, "rotate", "(I)V", direction);
}

void rotateContinuous(USSRONLYC(USSREnv *env) int direction) {
  ussr_call_void_controller_method(env, "rotateContinuous", "(F)V", (float)direction);
}

int getRole(USSREnv *env) {
  return ussr_call_int_controller_method(env, "getRole", "()I");
}

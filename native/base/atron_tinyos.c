#include <atron_tinyos.h>
#include <ussr_internal.h>

#include <nativeController.h>

#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>

void JNICALL Java_ussr_samples_atron_natives_ATRONNativeController_nativeHandleMessage(JNIEnv *jniENV, jobject self, jint initializationContext, jbyteArray message, jint messageSize, jint channel) {
  unsigned char buffer[ATRON_MAX_MESSAGE_SIZE];
  USSREnv env;
  env.jnienv = jniENV;
  env.controller = self;
  env.context = initializationContext;
  ussr_byteArray2charArray(&env, message, messageSize, buffer);
  printf("handleMessage(&env, buffer, messageSize, channel);\n");
}

//   _AlarmUssrImplP__VM_alarmStart
void VM_alarmStart(uint32_t moduleID, uint32_t dt) {
  printf("VM_alarmStart(%d,%d)\n", moduleID, dt);
}

extern void main();

void activate(USSRONLY(USSREnv *env)) {
  int moduleId = ussr_call_int_controller_method(env, "getRole", "()I");/* the old "role" */
  if (moduleId == 1)
    main_1(env);
}

int32_t initialize(USSRONLY(USSREnv *env)) { }

// temporary here to experiment
char sendMessage(USSREnv *env, unsigned char *message, unsigned char messageSize, unsigned char connector) {
  jbyteArray array = ussr_charArray2byteArray(env, message, messageSize);
  char result = ussr_call_byte_controller_method(env, "sendMessage", "([BBB)B", array, messageSize, connector);
  ussr_releaseByteArray(env, array);
  return result;
}

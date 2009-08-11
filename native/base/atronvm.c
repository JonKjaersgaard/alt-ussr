#include <atron.h>
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
  main_1();
}

int32_t initialize(USSRONLY(USSREnv *env)) { }


typedef uint8_t __nesc_atomic_t;

/* Saves current interrupt mask state and disables interrupts. */
inline __nesc_atomic_t 
__nesc_atomic_start(void) 
{
  //todo
  return 0;
}

/* Restores interrupt mask to original state. */
inline void 
__nesc_atomic_end(__nesc_atomic_t original_SREG) 
{
  //todo
}

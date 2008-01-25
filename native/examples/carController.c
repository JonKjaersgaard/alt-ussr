#include <ussr.h>
#include <atron.h>

#define ROLE_DRIVER 0
#define ROLE_WHEEL1 1
#define ROLE_WHEEL2 2
#define ROLE_WHEEL3 3
#define ROLE_WHEEL4 4

#ifdef USSR
int initialize(USSREnv *env) {return 0;}
#endif

void activate(USSRONLY(USSREnv *env)) {
  int role;
  while(1) {
    controllerIterationSimulatorHook(USSRONLY(env),0); /* Thread.yield(), if(paused) wait, kør Java kode vha. hook eller lignende */
    role = getRole(USSRONLY(env));
    if(role==ROLE_WHEEL1) rotateContinuously(USSRONLYC(env)1);
    if(role==ROLE_WHEEL2) rotateContinuously(USSRONLYC(env)-1);
    if(role==ROLE_WHEEL3) rotateContinuously(USSRONLYC(env)1);
    if(role==ROLE_WHEEL4) rotateContinuously(USSRONLYC(env)-1);
  }
}

void handleMessage(USSRONLYC(USSREnv *env) unsigned char* message, unsigned char messageSize, unsigned char channel) { ; }

#include "ussr.h"
#include "atron.h"

#define ROLE_WHEEL1 1
#define ROLE_WHEEL2 2
#define ROLE_WHEEL3 3
#define ROLE_WHEEL4 4

void activate(USSREnv *env) { 
  while(1) {
    controllerIterationSimulatorHook(USSRONLY(env)); // Thread.yield(), if(paused) wait, kør Java kode vha. hook eller lignende
    int role = getRole(USSRONLY(env));
    if(role==ROLE_WHEEL1) rotateContinuous(USSRONLYC(env)1);
    if(role==ROLE_WHEEL2) rotateContinuous(USSRONLYC(env)-1);
    if(role==ROLE_WHEEL3) rotateContinuous(USSRONLYC(env)1);
    if(role==ROLE_WHEEL4) rotateContinuous(USSRONLYC(env)-1);
  }
}

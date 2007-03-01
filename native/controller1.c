#include "ussr.h"
#include "atron.h"

#define ROLE_WHEEL1 1
#define ROLE_WHEEL2 1
#define ROLE_WHEEL3 1
#define ROLE_WHEEL4 1

void activate(USSREnv *env) { 
  while(1) {
    controllerIterationSimulatorHook(USSRONLY(env)); // Thread.yield(), if(paused) wait, kør Java kode vha. hook eller lignende
    int role = getRole(USSRONLY(env));
    if(role==ROLE_WHEEL1) rotate(USSRONLYC(env)1);
    if(role==ROLE_WHEEL2) rotate(USSRONLYC(env)-1);
    if(role==ROLE_WHEEL3) rotate(USSRONLYC(env)1);
    if(role==ROLE_WHEEL4) rotate(USSRONLYC(env)-1);
  }
}

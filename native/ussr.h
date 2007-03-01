#ifndef USSR_H_
#define USSR_H_

typedef struct {
	void *jnienv, *controller;
} USSREnv;

#ifdef USSR

#  define USSRONLY(x) x
#  define USSRONLYC(x) x,

/* Simulator-only API */

void controllerIterationSimulatorHook(USSREnv *env);  // Thread.yield(), if(paused) wait, kør Java kode vha. hook eller lignende

#  else /* USSR */

#    define USSRONLY(x)
#    define USSRONLYC(x)
#    define controllerIterationSimulationHook(x)

#  endif /* USSR */

/* Shared simulator and module API */

int getRole(USSRONLY(USSREnv *env));

/* Main controller function */

extern void activate(USSRONLY(USSREnv *env));

#endif /*USSR_H_*/

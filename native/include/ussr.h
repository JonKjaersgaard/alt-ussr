#ifndef USSR_H_
#define USSR_H_

#ifdef USSR

typedef struct {
  void *jnienv, *controller;
  int context;
} USSREnv;

#  define USSRONLY(x) x
#  define USSRONLYC(x) x,

/* Simulator-only API */

extern void disable_interrupts();
extern void enable_interrupts();

/* Should be called regularly by the controller, allowing the simulator to control scheduling, pausing etc. */
void controllerIterationSimulatorHook(USSREnv *env, unsigned char waitForEvent);

#  else /* USSR */

#    define USSRONLY(x)
#    define USSRONLYC(x)
#    define controllerIterationSimulatorHook(x)

extern uint8_t getRole();

#  endif /* USSR */

/* Main controller functions */

extern void activate(USSRONLY(USSREnv *env));
extern int32_t initialize(USSRONLY(USSREnv *env));

#endif /*USSR_H_*/

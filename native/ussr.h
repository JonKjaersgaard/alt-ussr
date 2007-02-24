#ifndef USSR_H_
#define USSR_H_

typedef struct {
	void *jnienv, *controller;
} USSREnv;

extern void activate(USSREnv *env);

/* Simulator API */

char simulatorIsPaused(USSREnv *env);
int getProperty(USSREnv *env, char *name, char *buffer, int count);
void threadYield(USSREnv *env);

#endif /*USSR_H_*/

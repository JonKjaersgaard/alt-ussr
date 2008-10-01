#include <ussr.h>
#include <atron.h>
#include <stdlib.h>
#include <stdio.h>
#include "Module.h"
#include "Component.h"


typedef struct _Global {
  unsigned char token[1];
  unsigned char moduleTranslator[7];
  unsigned char message[3];
} Global;

int counter=0;

#ifdef USSR
int initialize(USSREnv *env) {return (int)malloc(sizeof(Global));}
#define GLOBAL(env,name) (((Global*)(env)->context)->name)
#else
Global globals_static_alloc;
#define GLOBAL(env,name) globals_static_alloc.name
#endif

void activate(USSRONLY(USSREnv *env)) {
  delay(USSRONLY(env),10);
  setup(USSRONLY(env));
  home(USSRONLY(env));
  GLOBAL(env,token)[0]=0;
  while(1)
  {
	  controllerIterationSimulatorHook(USSRONLY(env),0);
	  //rotate(USSRONLYC(env) 1);
	  //printf("Module Running\n");
	  //delay(USSRONLY(env),1000);
	  //printf("Calling ASE\n");
	  //ASEmain();
	  //componentTest();
	  delay(USSRONLY(env),1000);
	  counter++;
	  printf("Counter = %i\n",counter);
	  GLOBAL(env,token)[0]++;
	  printf("Token = %i\n",GLOBAL(env,token)[0]);
  }
}



void handleMessage(USSRONLYC(USSREnv *env) unsigned char* incoming, unsigned char messageSize, unsigned char channel) {

	// if (incoming[2]==getRole(USSRONLY(env))) /* modified: getMyID */
	//sendMessage(USSRONLYC(env) incoming,messageSize,channel);
	printf("Modtog besked\n");
 }

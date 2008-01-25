#include <ussr.h>
#include <atron.h>

#include <stdio.h>

#ifdef USSR
int initialize(USSREnv *env) { return 0; }
#endif

void activate(USSRONLY(USSREnv *env)) {
  unsigned char send_buf[3];
  int i;
  send_buf[0] = 'A'; send_buf[1] = 'b'; send_buf[2] = 'e';
  while(1) {
    controllerIterationSimulatorHook(USSRONLY(env),0); // Thread.yield(), if(paused) wait, kør Java kode vha. hook eller lignende
    for(i=0; i<8; i++)
      sendMessage(USSRONLYC(env) send_buf, 3, i);
  }
}

void handleMessage(USSRONLYC(USSREnv *env) unsigned char* message, unsigned char messageSize, unsigned char channel) {
  int i;
  printf("Got a message on channel %d: ", channel);
  for(i=0; i<messageSize; i++)
    putchar(message[i]);
  printf("\n");
}

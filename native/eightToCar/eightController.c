#include <ussr.h>
#include <atron.h>
#include <stdlib.h>
#include <stdio.h>

typedef struct _Global {
  unsigned char token[1];
  unsigned char moduleTranslator[7];
  unsigned char message[3];
} Global;

#ifdef USSR
int initialize(USSREnv *env) { return (int)malloc(sizeof(Global)); }
#define GLOBAL(env,name) (((Global*)(env)->context)->name)
#else
Global globals_static_alloc;
#define GLOBAL(env,name) globals_static_alloc.name
#endif

void activate(USSRONLY(USSREnv *env)) {
  unsigned char i,k;
  // INSERTED:
  for(k=0;k<7;k++)
        GLOBAL(env,moduleTranslator)[k] = k;
for (i=0;i<1;i++)
	GLOBAL(env,token)[i]=255;
 delay(USSRONLY(env),10);
 setup(USSRONLY(env));
 home(USSRONLY(env));
 if(getRole(env)==0) GLOBAL(env,token)[0] = 0;
while(1)
{
    controllerIterationSimulatorHook(USSRONLY(env),0);
    if(GLOBAL(env,token)[0]!=255) {
      printf("Module #%d in state %d\n",getRole(env),GLOBAL(env,token)[0]);
      fflush(stdout);
    }
switch(GLOBAL(env,token)[0])
{
case 0:
	disconnect(USSRONLYC(env) 0);
	printf("I am Disconnecting...\n"); fflush(stdout);
	while (!isDisconnected(USSRONLYC(env) 0))controllerIterationSimulatorHook(USSRONLY(env),0);
	printf("Done...\n"); fflush(stdout);
	GLOBAL(env,token)[0]=1;
	break;
case 1:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=2;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[1];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,4);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 2:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=3;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[3];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,7);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 3:
	disconnect(USSRONLYC(env) 4);
	while (!isDisconnected(USSRONLYC(env) 4))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=4;
	break;
case 4:
	rotate(USSRONLYC(env) -1);
	//while (!isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	//while (isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=5;
	break;
case 5:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=6;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[5];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,0);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 6:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=7;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[6];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,5);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 7:
  //  rotate(USSRONLYC(env) -1);
  //  rotate(USSRONLYC(env) 1);
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=8;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[4];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,6);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 8:
	rotate(USSRONLYC(env) 1);
	//while (!isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	//while (isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=9;
	break;
case 9:
  printf("Waiting for connection possiblity...\n"); fflush(stdout);
  while(!isOtherConnectorNearby(USSRONLYC(env) 0))controllerIterationSimulatorHook(USSRONLY(env),0);
  printf("Got connection possiblity!\n"); fflush(stdout);
	_connect(USSRONLYC(env) 0);
	while (!isConnected(USSRONLYC(env) 0))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=10;
	break;
case 10:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=11;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[3];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,0);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 11:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=12;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[1];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,6);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 12:
	rotate(USSRONLYC(env) -1);
	//while (!isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	//while (isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=13;
	break;
case 13:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=14;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[3];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,7);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 14:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=15;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[5];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,0);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 15:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=16;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[6];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,5);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 16:
	disconnect(USSRONLYC(env) 2);
	while (!isDisconnected(USSRONLYC(env) 2))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=17;
	break;
case 17:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=18;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[4];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,6);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 18:
	rotate(USSRONLYC(env) 1);
	//while (!isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	//while (isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=19;
	break;
case 19:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=20;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[6];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,7);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 20:
	rotate(USSRONLYC(env) 1);
	//while (!isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	//while (isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=21;
	break;
case 21:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=22;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[4];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,6);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 22:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=23;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[3];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,0);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 23:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=24;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[1];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,6);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 24:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=25;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[0];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,3);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 25:
	_connect(USSRONLYC(env) 0);
	while (!isConnected(USSRONLYC(env) 0))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=26;
	break;
case 26:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=27;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[6];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,0);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 27:
	disconnect(USSRONLYC(env) 6);
	while (!isDisconnected(USSRONLYC(env) 6))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=28;
	break;
case 28:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=29;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[0];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,3);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 29:
	rotate(USSRONLYC(env) -1);
	//while (!isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	//while (isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=30;
	break;
case 30:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=31;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[1];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,4);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 31:
	rotate(USSRONLYC(env) 1);
	//while (!isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	//while (isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=32;
	break;
case 32:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=33;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[0];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,3);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 33:
	rotate(USSRONLYC(env) 1);
	//while (!isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	//while (isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=34;
	break;
case 34:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=35;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[1];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,4);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 35:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=36;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[3];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,7);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 36:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=37;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[5];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,0);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 37:
	_connect(USSRONLYC(env) 0);
	while (!isConnected(USSRONLYC(env) 0))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=38;
	break;
case 38:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=39;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[3];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,1);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 39:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=40;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[2];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,2);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 40:
	_connect(USSRONLYC(env) 4);
	while (!isConnected(USSRONLYC(env) 4))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=41;
	break;
case 41:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=42;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[3];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,5);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 42:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=43;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[1];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,6);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 43:
	_connect(USSRONLYC(env) 4);
	while (!isConnected(USSRONLYC(env) 4))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=44;
	break;
case 44:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=45;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[4];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,4);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 45:
	disconnect(USSRONLYC(env) 0);
	while (!isDisconnected(USSRONLYC(env) 0))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=46;
	break;
case 46:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=47;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[1];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,1);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 47:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=48;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[3];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,7);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 48:
	disconnect(USSRONLYC(env) 6);
	while (!isDisconnected(USSRONLYC(env) 6))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=49;
	break;
case 49:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=50;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[5];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,0);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 50:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=51;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[6];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,0);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 51:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=52;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[0];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,3);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 52:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=53;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[1];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,4);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 53:
	rotate(USSRONLYC(env) 1);
	//while (!isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	//while (isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=54;
	break;
case 54:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=55;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[0];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,3);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 55:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=56;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[6];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,0);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 56:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=57;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[5];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,5);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 57:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=58;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[3];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,1);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 58:
	rotate(USSRONLYC(env) 1);
	//while (!isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	//while (isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=59;
	break;
case 59:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=60;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[5];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,0);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 60:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=61;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[6];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,0);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 61:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=62;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[0];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,3);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 62:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=63;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[1];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,4);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 63:
	_connect(USSRONLYC(env) 6);
	while (!isConnected(USSRONLYC(env) 6))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=64;
	break;
case 64:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=65;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[3];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,6);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 65:
	disconnect(USSRONLYC(env) 0);
	while (!isDisconnected(USSRONLYC(env) 0))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=66;
	break;
case 66:
	disconnect(USSRONLYC(env) 2);
	while (!isDisconnected(USSRONLYC(env) 2))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=67;
	break;
case 67:
	GLOBAL(env,message)[0]=0;
	GLOBAL(env,message)[1]=68;
	GLOBAL(env,message)[2]=GLOBAL(env,moduleTranslator)[1];
	sendMessage(USSRONLYC(env) GLOBAL(env,message),3,5);
	GLOBAL(env,token)[0]=-1;
	setNorthIOPort(USSRONLYC(env) 0);
	break;
case 68:
	rotate(USSRONLYC(env) 1);
	//while (!isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	//while (isRotating(USSRONLY(env) ))controllerIterationSimulatorHook(USSRONLY(env),0);
	GLOBAL(env,token)[0]=69;
	break;
}
}

}

void handleMessage(USSRONLYC(USSREnv *env) unsigned char* incoming, unsigned char messageSize, unsigned char channel) {

  if (incoming[2]==getRole(USSRONLY(env))) /* modified: getMyID */
{
	GLOBAL(env,token)[incoming[0]]=incoming[1];
	setNorthIOPort(USSRONLYC(env) incoming[0]+0xF0);
}
else
{
	sendMessage(USSRONLYC(env) incoming,messageSize,channel);
}

 }

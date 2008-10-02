#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "AtronSocketApi.h"

char* host = "localhost";
int port=9900;

void parseCommandLineParameters(int argc, char *argv[]) {
	int i;
	for(i=1;i<argc;i++) {
		char * pch;
		pch = strtok (argv[i],"=");
		while (pch != NULL)
		{
			if(strcmp(pch,"-port")==0) {
				pch = strtok (NULL, "=");
				if(pch==NULL) {
					printf("-port set error\n");
					break;
				}
				else {
					port = atoi(pch);
					printf("Port set to %i\n",port);
				}
				break;
			}
			if(strcmp(pch,"-host")==0) {
				pch = strtok (NULL, "=");
				if(pch==NULL) {
					printf("-host set error\n");
					break;
				}
				else {
					host = pch;
					printf("Host set to %s\n",host);
				}
				break;
			}
			pch = strtok (NULL, "=");
		}
	}
}
int main(int argc, char *argv[])
{
	parseCommandLineParameters(argc, argv);
	printf("Testing AtronSocketApi.c now...\n");

	atronApi_setup(port, host);
	printf("> atronApi_setup() %i\n", atronApi_wasSend());

	atronApi_home();
	printf("> atronApi_home() %i\n", atronApi_wasSend());

	atronApi_rotate(1);
	printf("> atronApi_rotate(-1) %i\n", atronApi_wasSend());

	atronApi_rotateDegrees(90);
	printf("> atronApi_rotateDegrees(90) %i\n", atronApi_wasSend());

	atronApi_rotateToDegreeInDegrees(180);
	printf("> atronApi_rotateToDegreeInDegrees(180) %i\n", atronApi_wasSend());

	atronApi_rotateToDegree(3.14f/2);
	printf("> atronApi_rotateToDegree(3.14f/2) %i\n", atronApi_wasSend());

	atronApi_disconnectAll();
	printf("> atronApi_disconnectAll() %i\n", atronApi_wasSend());

	atronApi_connectAll();
	printf("> atronApi_connectAll() %i\n", atronApi_wasSend());

	atronApi_connect(0);
	printf("> atronApi_connect(0) %i\n", atronApi_wasSend());

	atronApi_disconnect(0);
	printf("> atronApi_disconnect(0) %i\n", atronApi_wasSend());

	atronApi_rotateContinuous(1);
	printf("> atronApi_rotateContinuous(1) %i\n", atronApi_wasSend());

	atronApi_centerBrake();
	printf("> atronApi_centerBrake() %i\n", atronApi_wasSend());

	atronApi_centerStop();
	printf("> atronApi_centerStop() %i\n", atronApi_wasSend());

	char* name = atronApi_getName();
	printf("> atronApi_getName() %i\n", atronApi_wasSend());
	printf(">>> return %s \n", name);

	float f = atronApi_getTime();
	printf("> atronApi_getTime() %i\n", atronApi_wasSend());
	printf(">>> return %f \n", f);

	f = atronApi_getAngularPosition();
	printf("> atronApi_getAngularPosition() %i\n", atronApi_wasSend());
	printf(">>> return %f \n", f);

	int d = atronApi_isRotating();
	printf("> atronApi_isRotating() %i\n", atronApi_wasSend());
	printf(">>> return %d \n", d);

	d = atronApi_getJointPosition();
	printf("> atronApi_getJointPosition() %i\n", atronApi_wasSend());
	printf(">>> return %d \n", d);

	d = atronApi_getAngularPositionDegrees();
	printf("> atronApi_getAngularPositionDegrees() %i\n", atronApi_wasSend());
	printf(">>> return %d \n", d);

	d = atronApi_canConnect(0);
	printf("> atronApi_canConnect(0) %i\n", atronApi_wasSend());
	printf(">>> return %d \n", d);

	d = atronApi_canDisconnect(0);
	printf("> atronApi_canDisconnect(0) %i\n", atronApi_wasSend());
	printf(">>> return %d \n", d);

	d = atronApi_isMale(0);
	printf("> atronApi_isMale(0) %i\n", atronApi_wasSend());
	printf(">>> return %d \n", d);

	d = atronApi_isConnected(0);
	printf("> atronApi_isConnected(0) %i\n", atronApi_wasSend());
	printf(">>> return %d \n", d);

	d = atronApi_isDisconnected(0);
	printf("> atronApi_isDisconnected(0) %i\n", atronApi_wasSend());
	printf(">>> return %d \n", d);

	d = atronApi_isConnected(0);
	printf("> atronApi_isConnected(0) %i\n", atronApi_wasSend());
	printf(">>> return %d \n", d);

	d = atronApi_isOtherConnectorNearby(0);
	printf("> atronApi_isOtherConnectorNearby(0) %i\n", atronApi_wasSend());
	printf(">>> return %d \n", d);

	d = atronApi_isObjectNearby(0);
	printf("> atronApi_isObjectNearby(0) %i\n", atronApi_wasSend());
	printf(">>> return %d \n", d);

	char msg[] = {'1','2','3','4'};
	d = atronApi_sendMessage(msg, 4, 0);
	printf("> atronApi_sendMessage(1234, 4, 0) %i\n", atronApi_wasSend());
	printf(">>> return %d \n", d);

	d = atronApi_getTiltX();
	printf("> atronApi_getTiltX() %i\n", atronApi_wasSend());
	printf(">>> return %d \n", d);

	d = atronApi_getTiltY();
	printf("> atronApi_getTiltY() %i\n", atronApi_wasSend());
	printf(">>> return %d \n", d);

	d = atronApi_getTiltZ();
	printf("> atronApi_getTiltZ() %i\n", atronApi_wasSend());
	printf(">>> return %d \n", d);

	printf("... done testing AtronSocketApi.c \n");
	return 0;
}

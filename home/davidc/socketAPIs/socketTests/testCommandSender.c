#include <stdio.h>
#include <string.h>
#include "CommandSender.h"

#define PORT 	9900
#define HOST 	"localhost"

char* command1 = "rotateContinuous 1";
char* command2 = "disconnectAll";
char* command3 = "foo";
char* command4 = "getAngularPosition";
char returnParam[50];

void testSendCommand(char* command) {
	if(cs_sendCommand(command,returnParam))  {
		printf("Command send success \n");
		printf("Return parameter: %s \n",returnParam);
	}
	else {
		printf("Command send error \n");
	}
}
int main(void)  {
	printf("Testing CommandSender.c now...\n");
	cs_init(PORT, HOST);
	testSendCommand(command1);
	testSendCommand(command2);
	testSendCommand(command3);
	testSendCommand(command4);
	printf("... done testing socket.c \n");
	return 0;
}

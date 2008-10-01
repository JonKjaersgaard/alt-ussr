#include <stdio.h>
#include <string.h>
#include "SimpleSocket.h"

#define PORT 	9900
#define HOST 	"localhost"

char* command1 = "30 rotateContinuous 1\n";
char* command2 = "31 disconnectAll\n";
char buffer[100];
int sockfd;
int main(void)  {
	printf("Testing socket.c now...\n");
	sockfd = ss_connect(HOST, PORT);
	ss_send(command1, strlen(command1), sockfd);
	ss_receive(buffer, sockfd);

	ss_send(command2, strlen(command2), sockfd);
	int nbytes = ss_receive(buffer, sockfd);
	printf("Received %i bytes: %s", nbytes, buffer);


	printf("... done testing socket.c \n");
	return 0;
}

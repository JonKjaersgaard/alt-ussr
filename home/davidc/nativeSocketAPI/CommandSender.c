#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "SimpleSocket.h"
#include "CommandSender.h"


char commandBuffer[50];
char receiveBuffer[50];
char returnBuffer[50];
int sockfd;
int commandCounter;
int commandBufferIndex;
int success;

void cs_init(int port, char* host) {
	sockfd = ss_connect(host, port);
	commandCounter=0;
	commandBufferIndex=0;
	success =1;
}

int parseReceiveBuffer(char* returnParam) {
	int tokenCounter = 0;
	char* token;
	for ( token = strtok(receiveBuffer," \n");token != NULL; token = strtok(NULL, " \n") ) {
		if(tokenCounter==0) {
			if(atoi(token)!=commandCounter) {
				//printf("Counter error in CommandSender: %s \n",receiveBuffer);
				return 0;
			}
		}
		if(tokenCounter==1) {
			if(strcmp(token,"ERROR")==0) {
				//printf("Ack error in CommandSender: %s \n",receiveBuffer);
				return 0;
			}
		}
		if(tokenCounter==2) {
			//printf("Return parameter: %s",token);
			memcpy(returnParam, token, strlen(token));
		}
		tokenCounter++;
	}
	return 1;
}
int cs_sendCommand(char* command, char* returnParam) {
	memset(returnParam, 0, 50);
	sprintf (commandBuffer, "%i %s\r\n", commandCounter, command);
	ss_send(commandBuffer, strlen(commandBuffer), sockfd);
	ss_receive(receiveBuffer, sockfd);
	//printf("Received: %s", receiveBuffer);
	int success = parseReceiveBuffer(returnParam);
	if(success) {
		commandCounter++;
		return 1;
	}
	else {
		return 0;
	}
}

void cs_sendCommand_void(char* command) {
	success = cs_sendCommand(command,returnBuffer);
	if(!success) printf("ERROR command: %s was not send or received correctly\n",command);
}
int cs_sendCommand_int(char* command) {
	int param;
	success = cs_sendCommand(command,returnBuffer);
	if(!success) printf("ERROR command: %s was not send or received correctly\n",command);
	sscanf(returnBuffer, "%d", &param);
	return param;
}
int cs_sendCommand_bool(char* command) {
	success = cs_sendCommand(command,returnBuffer);
	if(!success) printf("ERROR command: %s was not send or received correctly\n",command);
	if(strcmp(returnBuffer,"false")==0) {
		return 0;
	}
	else if(strcmp(returnBuffer,"true")==0) {
		return 1;
	}
	else {
		printf("ERROR: unable to parse return value (%s)",returnBuffer);
		return 0;
	}
}
float cs_sendCommand_float(char* command) {
	float param;
	success = cs_sendCommand(command,returnBuffer);
	if(!success) printf("ERROR command: %s was not send or received correctly\n",command);
	sscanf(returnBuffer, "%f", &param);
	return param;
}
char* cs_sendCommand_string(char* command) {
	success = cs_sendCommand(command,returnBuffer);
	if(!success) printf("ERROR command: %s was not send or received correctly\n",command);
	return returnBuffer;
}

int cs_wasSuccess() {
	return success;
}

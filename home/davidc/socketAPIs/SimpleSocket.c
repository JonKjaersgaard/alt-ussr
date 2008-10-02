#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <netdb.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <sys/socket.h>


#define MAXDATASIZE 100 // max number of bytes we can get at once
int ss_connect(char* host, int port) {
	int sockfd;
	struct hostent *he;
	struct sockaddr_in their_addr; // connector's address information

	if ((he=gethostbyname(host)) == NULL) {  // get the host info
	    perror("gethostbyname");
	    exit(1);
	}

	if ((sockfd = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
	    perror("socket");
	    exit(1);
	}
	their_addr.sin_family = AF_INET;    // host byte order
	their_addr.sin_port = htons(port);  // short, network byte order
	their_addr.sin_addr = *((struct in_addr *)he->h_addr);
	memset(their_addr.sin_zero, '\0', sizeof their_addr.sin_zero);
	if (connect(sockfd, (struct sockaddr *)&their_addr, sizeof their_addr) == -1) {
	    perror("connect");
	    exit(1);
	}
	return sockfd;
}

int ss_receive(char* buffer, int sockfd) {
	int numbytes;
	if ((numbytes=recv(sockfd, buffer, MAXDATASIZE-1, 0)) == -1) {
	    perror("recv");
	    exit(1);
	}
	buffer[numbytes] = '\0';
	return numbytes;
}

void ss_send(char* data, int length, int sockfd) {
	if (send(sockfd, data, length, 0) == -1) {
		perror("send");
		exit(1);
	}
}

void ss_close(int sockfd) {
	close(sockfd);
}

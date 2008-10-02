/*
 * SimpleSocket.h
 *
 *  Created on: Sep 10, 2008
 *  Author: David Johan Christensen
 */

#ifndef SIMPLESOCKET_H_
int ss_connect(char* host, int port);
int ss_receive(char* buffer, int sockfd);
void ss_send(char* data, int length, int sockfd);
void ss_close(int sockfd);
#define SIMPLESOCKET_H_


#endif /* SIMPLESOCKET_H_ */

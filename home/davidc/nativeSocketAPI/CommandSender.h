/*
 * CommandSender.h
 *
 *  Created on: Sep 10, 2008
 *  Author: David Johan Christensen
 */

#ifndef COMMANDSENDER_H_

void cs_init(int port, char* host);
int cs_sendCommand(char* command, char* returnParam);
int cs_wasSuccess();
char* cs_sendCommand_string(char* command);
void cs_sendCommand_void(char* command);
int cs_sendCommand_int(char* command);
int cs_sendCommand_bool(char* command);
float cs_sendCommand_float(char* command);

#define COMMANDSENDER_H_

#endif /* COMMANDSENDER_H_ */

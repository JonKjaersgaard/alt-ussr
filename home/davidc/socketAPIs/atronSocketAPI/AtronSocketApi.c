#include <stdio.h>
#include <string.h>
#include "AtronSocketApi.h"
#include "CommandSender.h"

void atronApi_setup(int port, char* host) {
	cs_init(port, host);
	cs_sendCommand_void("setup");
}
int atronApi_wasSend() {
	return cs_wasSuccess();
}

void atronApi_home() {
	cs_sendCommand_void("home");
}

//String
char* atronApi_getName() {
	return cs_sendCommand_string("getName");
}
//returns: Boolean
int atronApi_isRotating() {
	return cs_sendCommand_bool("isRotating");
}
int atronApi_getJointPosition() {
	char str[20]; sprintf(str, "getJointPosition");
	return cs_sendCommand_int(str);
}
void atronApi_rotate(int dir)  {
	char str[20]; sprintf(str, "rotate %d", dir);
	cs_sendCommand_void(str);
}
void atronApi_rotateDegrees(int degrees)  {
	char str[50]; sprintf(str, "rotateDegrees %d", degrees);
	cs_sendCommand_void(str);
}
void atronApi_rotateToDegreeInDegrees(int degrees)  {
	char str[50]; sprintf(str, "rotateToDegreeInDegrees %d", degrees);
	cs_sendCommand_void(str);
}
void atronApi_rotateToDegree(float rad) {
	char str[50]; sprintf(str, "rotateToDegree %f", rad);
	cs_sendCommand_void(str);
}
float atronApi_getTime() {
	char str[50]; sprintf(str, "getTime");
	return cs_sendCommand_float(str);
}
float atronApi_getAngularPosition() {
	char str[50]; sprintf(str, "getAngularPosition");
	return cs_sendCommand_float(str); 
}
int atronApi_getAngularPositionDegrees() {
	char str[50]; sprintf(str, "getAngularPositionDegrees");
	return cs_sendCommand_int(str);
}
void atronApi_disconnectAll() {
	cs_sendCommand_void("disconnectAll");
}
void atronApi_connectAll()  {
	cs_sendCommand_void("connectAll");
}
//returns int as boolean
int atronApi_canConnect(int connector) {
	char str[50]; sprintf(str, "canConnect %d", connector);
	return cs_sendCommand_bool(str);
}
//returns int as boolean
int atronApi_canDisconnect(int connector) {
	char str[50]; sprintf(str, "canDisconnect %d", connector);
	return cs_sendCommand_bool(str);
}
//returns int as boolean
int atronApi_isMale(int connector) {
	char str[50]; sprintf(str, "isMale %d", connector);
	return cs_sendCommand_bool(str);
}

void atronApi_connect(int connector)  {
	char str[50]; sprintf(str, "connect %d", connector);
	cs_sendCommand_void(str);
}

void atronApi_disconnect(int connector)  {
	char str[50]; sprintf(str, "disconnect %d", connector);
	cs_sendCommand_void(str);
}

//returns int as boolean
int atronApi_isConnected(int connector) {
	char str[50]; sprintf(str, "isConnected %d", connector);
	return cs_sendCommand_bool(str);
}

//returns int as boolean
int atronApi_isDisconnected(int connector) {
	char str[50]; sprintf(str, "isDisconnected %d", connector);
	return cs_sendCommand_bool(str);
}
void atronApi_rotateContinuous(float dir) {
	char str[50]; sprintf(str, "rotateContinuous %f", dir);
	cs_sendCommand_void(str);
}

void atronApi_centerBrake() {
	cs_sendCommand_void("centerBrake");
}
void atronApi_centerStop() {
	cs_sendCommand_void("centerStop");
}
//returns int as boolean
int atronApi_isOtherConnectorNearby(int connector) {
	char str[50]; sprintf(str, "isOtherConnectorNearby %d", connector);
	return cs_sendCommand_bool(str);
}

//returns int as boolean
int atronApi_isObjectNearby(int connector) {
	char str[50]; sprintf(str, "isObjectNearby %d", connector);
	return cs_sendCommand_bool(str);
}

//short used to be byte
short atronApi_getTiltX() {
	return cs_sendCommand_int("getTiltX");
}

//short used to be byte
short atronApi_getTiltY() {
	return cs_sendCommand_int("getTiltY");
}
//short used to be byte
short atronApi_getTiltZ() {
	return cs_sendCommand_int("getTiltZ");
}

//returns int as boolean
//TODO: fix potential problem where message contains "space" character
int atronApi_sendMessage(char* message, char messageSize, char connector) {
	char str[50]; sprintf(str, "sendMessage %s %d %d", "1234", messageSize, connector);
	return cs_sendCommand_int(str);
}

//TODO: how do we handle messages... separate socket for events EventListener.c vs CommandSender.c
//void atronApi_handleMessage(byte[] message, int messageSize, int channel)*;

/*
 * AtronSocketApi.h
 *
 *  Created on: Sep 12, 2008
 *  Author: David Johan Christensen
 */

#ifndef ATRONSOCKETAPI_H_
	void atronApi_setup(int port, char* host);
	int atronApi_wasSend();
	void atronApi_home();
	char* atronApi_getName();//String
	int atronApi_isRotating();//boolean
	int atronApi_getJointPosition();
	void atronApi_rotate(int dir);
	void atronApi_rotateDegrees(int degrees);
	void atronApi_rotateToDegreeInDegrees(int degrees);
	void atronApi_rotateToDegree(float rad);
	float atronApi_getTime();
	float atronApi_getAngularPosition();
	int atronApi_getAngularPositionDegrees();
	void atronApi_disconnectAll();
	void atronApi_connectAll();
	int atronApi_canConnect(int i); //boolean
	int atronApi_canDisconnect(int i); //boolean
	int atronApi_isMale(int i); //boolean
	void atronApi_connect(int i);
	void atronApi_disconnect(int i);
	int atronApi_isConnected(int i); //boolean
	int atronApi_isDisconnected(int i); //boolean
	void atronApi_rotateContinuous(float dir);
	void atronApi_centerBrake();
	void atronApi_centerStop();
	int atronApi_isOtherConnectorNearby(int connector); //boolean
	int atronApi_isObjectNearby(int connector); //boolean
	int atronApi_sendMessage(char* message, char messageSize, char connector); //byte as boolean
	short atronApi_getTiltX(); //byte
	short atronApi_getTiltY(); //byte
	short atronApi_getTiltZ(); //byte
	//void atronApi_handleMessage(byte[] message, int messageSize, int channel)*;


#define ATRONSOCKETAPI_H_

#endif /* ATRONSOCKETAPIR_H_ */

#include "../api/API.h"

unsigned char token[1];
unsigned char moduleTranslator[7]={0, 1, 2, 3, 4, 5, 6};
unsigned char message[3];

int main(void)
{
unsigned char i;
for (i=0;i<1;i++)
	token[i]=255;
setup();
home();
while(1)
{

switch(token[0])
{
case 0:
	disconnect(0);
	while (!isDisconnected(0));
	token[0]=1;
	break;
case 1:
	message[0]=0;
	message[1]=2;
	message[2]=moduleTranslator[1];
	sendMessage(message,3,4);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 2:
	message[0]=0;
	message[1]=3;
	message[2]=moduleTranslator[3];
	sendMessage(message,3,7);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 3:
	disconnect(4);
	while (!isDisconnected(4));
	token[0]=4;
	break;
case 4:
	rotate(-1);
	while (!isRotating());
	while (isRotating());
	token[0]=5;
	break;
case 5:
	message[0]=0;
	message[1]=6;
	message[2]=moduleTranslator[5];
	sendMessage(message,3,0);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 6:
	message[0]=0;
	message[1]=7;
	message[2]=moduleTranslator[6];
	sendMessage(message,3,5);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 7:
	message[0]=0;
	message[1]=8;
	message[2]=moduleTranslator[4];
	sendMessage(message,3,6);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 8:
	rotate(1);
	while (!isRotating());
	while (isRotating());
	token[0]=9;
	break;
case 9:
	connect(0);
	while (!isConnected(0));
	token[0]=10;
	break;
case 10:
	message[0]=0;
	message[1]=11;
	message[2]=moduleTranslator[3];
	sendMessage(message,3,0);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 11:
	message[0]=0;
	message[1]=12;
	message[2]=moduleTranslator[1];
	sendMessage(message,3,6);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 12:
	rotate(-1);
	while (!isRotating());
	while (isRotating());
	token[0]=13;
	break;
case 13:
	message[0]=0;
	message[1]=14;
	message[2]=moduleTranslator[3];
	sendMessage(message,3,7);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 14:
	message[0]=0;
	message[1]=15;
	message[2]=moduleTranslator[5];
	sendMessage(message,3,0);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 15:
	message[0]=0;
	message[1]=16;
	message[2]=moduleTranslator[6];
	sendMessage(message,3,5);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 16:
	disconnect(2);
	while (!isDisconnected(2));
	token[0]=17;
	break;
case 17:
	message[0]=0;
	message[1]=18;
	message[2]=moduleTranslator[4];
	sendMessage(message,3,6);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 18:
	rotate(1);
	while (!isRotating());
	while (isRotating());
	token[0]=19;
	break;
case 19:
	message[0]=0;
	message[1]=20;
	message[2]=moduleTranslator[6];
	sendMessage(message,3,7);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 20:
	rotate(1);
	while (!isRotating());
	while (isRotating());
	token[0]=21;
	break;
case 21:
	message[0]=0;
	message[1]=22;
	message[2]=moduleTranslator[4];
	sendMessage(message,3,6);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 22:
	message[0]=0;
	message[1]=23;
	message[2]=moduleTranslator[3];
	sendMessage(message,3,0);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 23:
	message[0]=0;
	message[1]=24;
	message[2]=moduleTranslator[1];
	sendMessage(message,3,6);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 24:
	message[0]=0;
	message[1]=25;
	message[2]=moduleTranslator[0];
	sendMessage(message,3,3);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 25:
	connect(0);
	while (!isConnected(0));
	token[0]=26;
	break;
case 26:
	message[0]=0;
	message[1]=27;
	message[2]=moduleTranslator[6];
	sendMessage(message,3,0);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 27:
	disconnect(6);
	while (!isDisconnected(6));
	token[0]=28;
	break;
case 28:
	message[0]=0;
	message[1]=29;
	message[2]=moduleTranslator[0];
	sendMessage(message,3,3);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 29:
	rotate(-1);
	while (!isRotating());
	while (isRotating());
	token[0]=30;
	break;
case 30:
	message[0]=0;
	message[1]=31;
	message[2]=moduleTranslator[1];
	sendMessage(message,3,4);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 31:
	rotate(1);
	while (!isRotating());
	while (isRotating());
	token[0]=32;
	break;
case 32:
	message[0]=0;
	message[1]=33;
	message[2]=moduleTranslator[0];
	sendMessage(message,3,3);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 33:
	rotate(1);
	while (!isRotating());
	while (isRotating());
	token[0]=34;
	break;
case 34:
	message[0]=0;
	message[1]=35;
	message[2]=moduleTranslator[1];
	sendMessage(message,3,4);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 35:
	message[0]=0;
	message[1]=36;
	message[2]=moduleTranslator[3];
	sendMessage(message,3,7);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 36:
	message[0]=0;
	message[1]=37;
	message[2]=moduleTranslator[5];
	sendMessage(message,3,0);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 37:
	connect(0);
	while (!isConnected(0));
	token[0]=38;
	break;
case 38:
	message[0]=0;
	message[1]=39;
	message[2]=moduleTranslator[3];
	sendMessage(message,3,1);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 39:
	message[0]=0;
	message[1]=40;
	message[2]=moduleTranslator[2];
	sendMessage(message,3,2);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 40:
	connect(4);
	while (!isConnected(4));
	token[0]=41;
	break;
case 41:
	message[0]=0;
	message[1]=42;
	message[2]=moduleTranslator[3];
	sendMessage(message,3,5);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 42:
	message[0]=0;
	message[1]=43;
	message[2]=moduleTranslator[1];
	sendMessage(message,3,6);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 43:
	connect(4);
	while (!isConnected(4));
	token[0]=44;
	break;
case 44:
	message[0]=0;
	message[1]=45;
	message[2]=moduleTranslator[4];
	sendMessage(message,3,4);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 45:
	disconnect(0);
	while (!isDisconnected(0));
	token[0]=46;
	break;
case 46:
	message[0]=0;
	message[1]=47;
	message[2]=moduleTranslator[1];
	sendMessage(message,3,1);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 47:
	message[0]=0;
	message[1]=48;
	message[2]=moduleTranslator[3];
	sendMessage(message,3,7);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 48:
	disconnect(6);
	while (!isDisconnected(6));
	token[0]=49;
	break;
case 49:
	message[0]=0;
	message[1]=50;
	message[2]=moduleTranslator[5];
	sendMessage(message,3,0);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 50:
	message[0]=0;
	message[1]=51;
	message[2]=moduleTranslator[6];
	sendMessage(message,3,0);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 51:
	message[0]=0;
	message[1]=52;
	message[2]=moduleTranslator[0];
	sendMessage(message,3,3);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 52:
	message[0]=0;
	message[1]=53;
	message[2]=moduleTranslator[1];
	sendMessage(message,3,4);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 53:
	rotate(1);
	while (!isRotating());
	while (isRotating());
	token[0]=54;
	break;
case 54:
	message[0]=0;
	message[1]=55;
	message[2]=moduleTranslator[0];
	sendMessage(message,3,3);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 55:
	message[0]=0;
	message[1]=56;
	message[2]=moduleTranslator[6];
	sendMessage(message,3,0);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 56:
	message[0]=0;
	message[1]=57;
	message[2]=moduleTranslator[5];
	sendMessage(message,3,5);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 57:
	message[0]=0;
	message[1]=58;
	message[2]=moduleTranslator[3];
	sendMessage(message,3,1);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 58:
	rotate(1);
	while (!isRotating());
	while (isRotating());
	token[0]=59;
	break;
case 59:
	message[0]=0;
	message[1]=60;
	message[2]=moduleTranslator[5];
	sendMessage(message,3,0);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 60:
	message[0]=0;
	message[1]=61;
	message[2]=moduleTranslator[6];
	sendMessage(message,3,0);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 61:
	message[0]=0;
	message[1]=62;
	message[2]=moduleTranslator[0];
	sendMessage(message,3,3);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 62:
	message[0]=0;
	message[1]=63;
	message[2]=moduleTranslator[1];
	sendMessage(message,3,4);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 63:
	connect(6);
	while (!isConnected(6));
	token[0]=64;
	break;
case 64:
	message[0]=0;
	message[1]=65;
	message[2]=moduleTranslator[3];
	sendMessage(message,3,6);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 65:
	disconnect(0);
	while (!isDisconnected(0));
	token[0]=66;
	break;
case 66:
	disconnect(2);
	while (!isDisconnected(2));
	token[0]=67;
	break;
case 67:
	message[0]=0;
	message[1]=68;
	message[2]=moduleTranslator[1];
	sendMessage(message,3,5);
	token[0]=-1;
	setNorthIOPort(0);
	break;
case 68:
	rotate(1);
	while (!isRotating());
	while (isRotating());
	token[0]=69;
	break;
}
}
}
void handleMessage(unsigned char* message, unsigned char messageSize, unsigned char channel)
{
if (message[2]==getMyID())
{
	token[message[0]]=message[1];
	setNorthIOPort(message[0]+0xF0);
}
else
{
	sendMessage(message,messageSize,channel);
}
}


#ifndef __DCD_CONTROLLER_H_
#define __DCD_CONTROLLER_H_

#include "ussr.h"
#include "dcdTypes.h"

extern void notifyRoleChange(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char physical_channel, unsigned char role);
extern void sendCommandMaybe(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char physical_channel, unsigned char role, unsigned char command, unsigned char argument, unsigned char use_local_packet_id, unsigned char packet_id_maybe);
extern void sendProgramMaybe(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char physical_channel, unsigned char *program, int programSize);
extern void activate(USSRONLY(USSREnv *env));
extern unsigned char store_program(USSRONLYC(USSREnv *env) ProgramPacket *packet, unsigned char messageSize, unsigned char channel);
extern Task *allocate_task(USSRONLY(USSREnv *env));
extern void enqueue_task(USSRONLYC(USSREnv *env) Task *task);
void installProgramMessage(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char* program, unsigned char programSize);

/* Global store */
#ifdef USSR
#define GLOBAL(env,name) (((Global*)(env)->context)->name)
#else
extern Global globals_static_alloc;
#define GLOBAL(env,name) globals_static_alloc.name
#endif

#endif

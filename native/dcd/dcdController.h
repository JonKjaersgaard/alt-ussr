#ifndef __DCD_CONTROLLER_H_
#define __DCD_CONTROLLER_H_
#include "config.h"

#include "ussr.h"
#include "dcdTypes.h"

extern void notifyRoleChange(USSRONLYC(USSREnv *env) InterpreterContext *context, uint8_t physical_channel, uint8_t role);
extern void sendCommandMaybe(USSRONLYC(USSREnv *env) InterpreterContext *context, uint8_t physical_channel, uint8_t role, uint8_t command, uint8_t argument, uint8_t use_local_packet_id, uint8_t packet_id_maybe);
extern void sendProgramMaybe(USSRONLYC(USSREnv *env) InterpreterContext *context, uint8_t physical_channel, uint8_t *program, uint8_t programSize);
extern void activate(USSRONLY(USSREnv *env));
extern uint8_t store_program(USSRONLYC(USSREnv *env) ProgramPacket *packet, uint8_t messageSize, uint8_t channel);
extern Task *allocate_task(USSRONLY(USSREnv *env));
extern void enqueue_task(USSRONLYC(USSREnv *env) Task *task);
void installProgramMessage(USSRONLYC(USSREnv *env) InterpreterContext *context, uint8_t* program, uint8_t programSize);
void activate_step(USSRONLYC(USSREnv *env) int32_t maxSteps);

/* Global store */
#ifdef USSR
#define GLOBAL(env,name) (((Global*)(env)->context)->name)
#else
extern Global globals_static_alloc;
#define GLOBAL(env,name) globals_static_alloc.name
#endif

#endif

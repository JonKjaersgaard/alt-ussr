#ifndef __DCD_ERROR_H_
#define __DCD_ERROR_H_

#include <ussr.h>

#define ERROR_PROGRAM_STORE_FULL 1
#define ERROR_TASK_QUEUE_FULL 2
#define ERROR_UNKNOWN_MESSAGE 3
#define ERROR_UNKNOWN_TASK_TYPE 4
#define ERROR_ILLEGAL_RECEIVER_ROTATION 5
#define ERROR_ILLEGAL_VIRTUAL_CHANNEL 6
#define INTERPRETER_STACK_OVERFLOW 7
#define INTERPRETER_STACK_UNDERFLOW 8
#define INTERPRETER_UNKNOWN_INSTRUCTION 9
#define INTERPRETER_PC_OVERFLOW 10
#define INTERPRETER_INSTRUCTION_COUNT_EXCEEDED 11
#define ERROR_ILLEGAL_CONNECTION_DIRECTION 12
#define ERROR_ILLEGAL_AXIS_DIRECTION 13
#define ERROR_ILLEGAL_JOINT_POSITION 14
#define ERROR_UNKNOWN_COMMAND 15
#define ERROR_TASK_QUEUE_BORKED 16
#define ERROR_ILLEGAL_PACKET_ID 17
#define ERROR_ILLEGAL_COMMAND_INSTALL 18
#define ERROR_ILLEGAL_FUNCTION 19
#define ERROR_ILLEGAL_CLOSURE 20
#define ERROR_ILLEGAL_ARGUMENT_INDEX 21

#define ERROR_NOT_SUPPORTED_YET 63
#define ERROR_SHOULD_NOT_HAPPEN 64

#define TRACE_TASKS             1
#define TRACE_COMMANDS          2
#define TRACE_NETWORK           4
#define TRACE_MIGRATION         8
#define TRACE_EVENTS           16
#define TRACE_COMPASS          32
#define TRACE_INTERPRET        64
#define TRACE_WARNINGS        128
#define TRACE_ACTUATION       256
#define TRACE_CACHE           512
#define TRACE_BEHAVIOR       1024

#define USSRDEBUG(flags,command) { if(vm_trace_flags&(flags)) command; fflush(stdout); }
#define USSRDEBUG2(local_trace_flags,flags,command) { if((vm_trace_flags|local_trace_flags)&(flags)) command; fflush(stdout); }

extern void report_error(USSRONLYC(USSREnv *env) char error_number, unsigned char argument);
#ifdef USSR
extern int vm_trace_flags;
extern void print_arg(unsigned char *program, unsigned char pc, int nl);
extern void print_program(unsigned char *program, unsigned char program_size);
#endif

#endif

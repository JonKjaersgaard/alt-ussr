#ifndef __DCD_CONSTANTS_H_
#define __DCD_CONSTANTS_H_

#define MT_PROGRAM 1
#define MT_COMMAND 2
#define MT_NOTIFY 3

#define CMD_ROTATE_CLOCKWISE 1
#define CMD_ROTATE_COUNTERCLOCKWISE 2
#define CMD_ROTATE_STOP 3
#define CMD_ROTATE_DEGREES 4
#define CMD_ROTATE_TO 5
#define CMD_MAX 64

/* Primitive functions */
#define PRIM_CENTERSTOP CMD_ROTATE_STOP
#define PRIM_TURNTO CMD_ROTATE_TO
#define PRIM_APPLY (CMD_MAX+0)

#define TASK_NONE 0
#define TASK_INTERPRET 1
#define TASK_COMMAND 2

#define TASK_FLAG_DISCARD 1

#define MAX_PROGRAM_SIZE 64
#define MAX_N_STORED_PROGRAMS 10
#define MAX_N_ENQUEUED_TASKS 10
#define MAX_N_EVENT_TYPES 8

#define MESSAGE_CACHE_MAX_SIZE 16
#define MESSAGE_CACHE_WINDOW_SIZE 32
#define MESSAGE_CACHE_FUTURE_SIZE 16

#define VECTOR_NONE 255
#define VECTOR_DISABLED_FLAG 128

#define MT_PACKET_HEADER_SIZE 7 /* 2 (packet header) + size of interpreter context - 1 (physical channel) */

#define MAX_ROLE_COMMANDS 5 /* max number of commands per role */
#define COMMAND_NONE 255
#define MAX_N_GENERIC_COMMANDS 128

#endif

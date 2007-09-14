#ifndef __DCD_TYPES_H_
#define __DCD_TYPES_H_

#include "dcdConstants.h"
#include "roles.h"

typedef struct _InterpreterContext {
  signed char mod_x,mod_y,mod_z;
  unsigned char mod_rotation;
  unsigned char incoming_virtual_channel;
  unsigned char incoming_physical_channel;
  unsigned char program_id;
} InterpreterContext;

typedef struct _StoredProgram {
  InterpreterContext context;
  unsigned char size;
  unsigned char program[MAX_PROGRAM_SIZE];
} StoredProgram;

typedef struct _Task {
  unsigned char type;
  unsigned char flags;
  unsigned char arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10;
} Task;

typedef struct _CommandTask {
  unsigned char type;
  unsigned char flags;
  unsigned char role, command, argument, physical_channel;
  signed char x,y,z;
  unsigned char r, virtual_channel, packet_id;
} CommandTask;

typedef struct _MessageCache {
  signed char x, y, z;
  unsigned char r;
  unsigned char min_id;
  unsigned int seen_id; /* 32-bit sliding window of seen IDs, higher causes window to slide, lower means we reject */
} MessageCache;

typedef struct _Global {
  StoredProgram global_program_store[MAX_N_STORED_PROGRAMS];
  int global_program_store_use_flags;
  Task task_queue[MAX_N_ENQUEUED_TASKS];
  int task_queue_start, task_queue_end;
  unsigned char role;
  unsigned char event_handler_vectors[MAX_N_EVENT_TYPES];
  unsigned char message_cache_size, message_cache_next;
  MessageCache message_cache[MESSAGE_CACHE_MAX_SIZE];
  unsigned char packet_id;
  unsigned char neighbor_role_cache[8];
  unsigned char command_table[MAX_N_ROLES][MAX_ROLE_COMMANDS];
  int neutral_joint_position;
} Global;

typedef struct _Packet {
  unsigned char header;
  unsigned char id;
  signed char x, y, z;
  unsigned char r;
  unsigned char virtual_channel;
} Packet;

typedef struct _ProgramPacket {
  unsigned char header;
  unsigned char id;
  signed char x, y, z;
  unsigned char r;
  unsigned char virtual_channel;
  unsigned char program[0];
} ProgramPacket;

typedef struct _CommandPacket {
  unsigned char header;
  unsigned char id;
  signed char x, y, z;
  unsigned char r;
  unsigned char virtual_channel;
  unsigned char role, command, argument;
} CommandPacket;

typedef struct _NotifyPacket {
  unsigned char header;
  unsigned char id;
  signed char x, y, z;
  unsigned char r;
  unsigned char virtual_channel;
  unsigned char role;
} NotifyPacket;

#endif

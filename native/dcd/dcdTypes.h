#ifndef __DCD_TYPES_H_
#define __DCD_TYPES_H_
#include "config.h"

#include "dcdConstants.h"
#include "roles.h"

typedef struct _InterpreterContext {
  int8_t mod_x,mod_y,mod_z;
  uint8_t mod_rotation;
  uint8_t incoming_virtual_channel;
  uint8_t incoming_physical_channel;
  uint8_t program_id;
} InterpreterContext;

typedef struct _StoredProgram {
  InterpreterContext context;
  uint8_t size;
  uint8_t program[MAX_PROGRAM_SIZE];
} StoredProgram;

typedef struct _Task {
  uint8_t type;
  uint8_t flags;
  uint8_t arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10;
} Task;

typedef struct _CommandTask {
  uint8_t type;
  uint8_t flags;
  uint8_t role, command, argument, physical_channel;
  int8_t x,y,z;
  uint8_t r, virtual_channel, packet_id;
} CommandTask;

typedef struct _MessageCache {
  int8_t x, y, z;
  uint8_t r;
  uint8_t min_id;
  uint32_t seen_id; /* 32-bit sliding window of seen IDs, higher causes window to slide, lower means we reject */
} MessageCache;

typedef struct _Global {
  StoredProgram global_program_store[MAX_N_STORED_PROGRAMS];
  uint32_t global_program_store_use_flags;
  Task task_queue[MAX_N_ENQUEUED_TASKS];
  int8_t task_queue_start, task_queue_end;
  uint8_t role;
  uint8_t event_handler_vectors[MAX_N_EVENT_TYPES];
  uint8_t message_cache_size, message_cache_next;
  MessageCache message_cache[MESSAGE_CACHE_MAX_SIZE];
  uint8_t packet_id;
  uint8_t neighbor_role_cache[8];
  uint8_t command_table[MAX_N_ROLES][MAX_ROLE_COMMANDS];
  int8_t neutral_joint_position;
} Global;

typedef struct _Packet {
  uint8_t header;
  uint8_t id;
  int8_t x, y, z;
  uint8_t r;
  uint8_t virtual_channel;
} Packet;

typedef struct _ProgramPacket {
  uint8_t header;
  uint8_t id;
  int8_t x, y, z;
  uint8_t r;
  uint8_t virtual_channel;
  uint8_t program[0];
} ProgramPacket;

typedef struct _CommandPacket {
  uint8_t header;
  uint8_t id;
  int8_t x, y, z;
  uint8_t r;
  uint8_t virtual_channel;
  uint8_t role, command, argument;
} CommandPacket;

typedef struct _NotifyPacket {
  uint8_t header;
  uint8_t id;
  int8_t x, y, z;
  uint8_t r;
  uint8_t virtual_channel;
  uint8_t role;
} NotifyPacket;

#endif

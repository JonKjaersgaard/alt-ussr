#include <ussr.h>
#include <atron.h>

#include "dcdError.h"
#include "dcdBytecode.h"
#include "dcdConstants.h"
#include "dcdTypes.h"

#include "dcdController.h"
#include "dcdInterpreter.h"

#include "navigation.h"

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include "roles.h"

/* Global store */
#ifndef USSR
Global globals_static_alloc;
#endif

/* Initialize controller: global store and scheduler */
int initialize(USSRONLY(USSREnv *env)) {
  Global *global;
  unsigned char vector, role, command, connector;
#ifdef USSR
  global = (Global*)malloc(sizeof(Global));
#else
  global = &globals_static_alloc;
#endif
  global->global_program_store_use_flags = 0;
  global->task_queue_start = 0;
  global->task_queue_end = 0;
  global->role = ROLE_NONE;
  for(vector=0; vector<MAX_N_EVENT_TYPES; vector++) global->event_handler_vectors[vector] = VECTOR_NONE;
  global->message_cache_size = 0;
  global->message_cache_next = 0;
  global->packet_id = 0;
  for(connector=0; connector<8; connector++) global->neighbor_role_cache[connector] = 0;
  for(role=0; role<MAX_N_ROLES; role++)
    for(command=0; command<MAX_ROLE_COMMANDS; command++)
      global->command_table[role][command] = COMMAND_NONE;
  return (int)global;
}

Task *allocate_task(USSRONLY(USSREnv *env)) {
  char start = GLOBAL(env,task_queue_start);
  int index = GLOBAL(env,task_queue_end);
  char task_queue_next = (index+1)%MAX_N_ENQUEUED_TASKS;
  if(task_queue_next==start) {
    report_error(USSRONLYC(env) ERROR_TASK_QUEUE_FULL,0);
    return 0;
  }
  return GLOBAL(env,task_queue)+index;
}
  
void enqueue_task(USSRONLYC(USSREnv *env) Task *task) {
  int task_index = task-GLOBAL(env,task_queue);
  char start = GLOBAL(env,task_queue_start);
  int index = GLOBAL(env,task_queue_end);
  if(task_index!=index) {
    report_error(USSRONLYC(env) ERROR_TASK_QUEUE_BORKED,0);
    return;
  }
  char task_queue_next = (index+1)%MAX_N_ENQUEUED_TASKS;
  if(task_queue_next==start) {
    report_error(USSRONLYC(env) ERROR_TASK_QUEUE_FULL,0);
    return;
  }
  GLOBAL(env,task_queue_end) = task_queue_next;
  USSRDEBUG(TRACE_TASKS,printf("<%d>(%d) enqueued task @%d %d(%d,%d,%d,%d): {%d,%d}\n", env->context, getRole(env), index, task->type, task->arg1, task->arg2, task->arg3, task->arg4, GLOBAL(env,task_queue_start), GLOBAL(env,task_queue_end)));
}

void notifyRoleChange(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char virtual_channel, unsigned char role) {
  NotifyPacket packet;
  signed char x = context->mod_x, y = context->mod_y, z = context->mod_z;
  unsigned char r = context->mod_rotation;
  unsigned char physical_channel = virtual2physical(USSRONLYC(env) context, virtual_channel);
  unsigned char receiver_virtual_channel = compute_receiver_coordinates(USSRONLYC(env) virtual_channel, &x, &y, &z, &r);
  if(!isOtherConnectorNearby(USSRONLYC(env) physical_channel)) return;
  packet.header = MT_NOTIFY;
  packet.id = GLOBAL(env,packet_id)++;
  packet.x = x;
  packet.y = y;
  packet.z = z;
  packet.r = r;
  packet.virtual_channel = receiver_virtual_channel;
  packet.role = role;
  sendMessage(USSRONLYC(env) (unsigned char*)&packet, sizeof(NotifyPacket), physical_channel);
}

void sendCommandMaybe(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char virtual_channel, unsigned char role, unsigned char command, unsigned char argument, unsigned char use_local_packet_id, unsigned char packet_id_maybe) {
  CommandPacket packet;
  signed char x = context->mod_x, y = context->mod_y, z = context->mod_z;
  unsigned char r = context->mod_rotation;
  unsigned char physical_channel = virtual2physical(USSRONLYC(env) context, virtual_channel);
  unsigned char receiver_virtual_channel = compute_receiver_coordinates(USSRONLYC(env) virtual_channel, &x, &y, &z, &r);
  if(!isOtherConnectorNearby(USSRONLYC(env) physical_channel)) return;
  USSRDEBUG(TRACE_COMMANDS|TRACE_NETWORK,printf("<%d>(%d) Sending command %d %d on %d (v=%d) (%d,%d,%d) -> (%d,%d,%d)\n", env->context, getRole(env), command, argument, physical_channel, receiver_virtual_channel, context->mod_x, context->mod_y, context->mod_z, x, y, z));
  USSRDEBUG(TRACE_COMMANDS|TRACE_NETWORK,printf("### SendC <%d> name=%d, pch=%d (vch=%d), MY{pch=%d,vch=%d, pos=(%d,%d,%d), r=%d} RC{vch=%d, pos={%d,%d,%d}, r=%d}\n", env->context, getRole(env), physical_channel, receiver_virtual_channel, context->incoming_physical_channel, context->incoming_virtual_channel, context->mod_x, context->mod_y, context->mod_z, context->mod_rotation, virtual_channel, x, y, z, r));
  packet.header = MT_COMMAND;
  packet.id = use_local_packet_id ? GLOBAL(env,packet_id)++ : packet_id_maybe;
  packet.x = x;
  packet.y = y;
  packet.z = z;
  packet.r = r;
  packet.virtual_channel = receiver_virtual_channel;
  packet.role = role;
  packet.command = command;
  packet.argument = argument;
  sendMessage(USSRONLYC(env) (unsigned char*)&packet, sizeof(CommandPacket), physical_channel);
}

void resendCommandMaybe(USSRONLYC(USSREnv *env) unsigned char virtual_channel, CommandTask *task) {
  InterpreterContext context;
  context.mod_x = task->x;
  context.mod_y = task->y;
  context.mod_z = task->z;
  context.mod_rotation = task->r;
  context.incoming_virtual_channel = task->virtual_channel;
  context.incoming_physical_channel = task->physical_channel;
  sendCommandMaybe(USSRONLYC(env) &context, virtual_channel, task->role, task->command, task->argument, 0, task->packet_id);
}

void createProgramPacket(unsigned char *buffer, InterpreterContext *context, unsigned char receiver_virtual_channel, unsigned char *program, int programSize) {
  ProgramPacket* packet = (ProgramPacket*)buffer;
  int i;
  packet->header = MT_PROGRAM;
  packet->id = context->program_id;
  packet->x = context->mod_x;
  packet->y = context->mod_y;
  packet->z = context->mod_z;
  packet->r = context->mod_rotation;
  packet->virtual_channel = receiver_virtual_channel;
  for(i=0; i<programSize; i++)
    packet->program[i] = program[i];
}

void sendProgramMaybe(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char virtual_channel, unsigned char *program, int programSize) {
  unsigned char buffer[MAX_PROGRAM_SIZE+MT_PACKET_HEADER_SIZE];
  signed char x = context->mod_x, y = context->mod_y, z = context->mod_z;
  unsigned char r = context->mod_rotation;
  unsigned char physical_channel = virtual2physical(USSRONLYC(env) context, virtual_channel);
  unsigned char receiver_virtual_channel = compute_receiver_coordinates(USSRONLYC(env) virtual_channel, &x, &y, &z, &r);
  if(!isOtherConnectorNearby(USSRONLYC(env) physical_channel)) return;
  USSRDEBUG(TRACE_MIGRATION|TRACE_NETWORK,printf("### SendP <%d> name=%d, pch=%d (vch=%d), MY{vch=%d, pos=(%d,%d,%d), r=%d} RC{vch=%d, pos={%d,%d,%d}, r=%d}\n", env->context, getRole(env), physical_channel, virtual_channel, context->incoming_virtual_channel, context->mod_x, context->mod_y, context->mod_z, context->mod_rotation, receiver_virtual_channel, x, y, z, r));
  createProgramPacket(buffer,context,receiver_virtual_channel,program,programSize);
#ifdef UNDEFINED
  {
    ProgramPacket* packet = (ProgramPacket*)&buffer;
    int i;
    packet->header = MT_PROGRAM;
    packet->id = context->program_id;
    packet->x = x;
    packet->y = y;
    packet->z = z;
    packet->r = r;
    packet->virtual_channel = receiver_virtual_channel;
    for(i=0; i<programSize; i++)
      packet->program[i] = program[i];
  }
#endif
  sendMessage(USSRONLYC(env) buffer, programSize+MT_PACKET_HEADER_SIZE, physical_channel);
}

unsigned char checkForEvents(USSRONLY(USSREnv *env)) {
  unsigned char vector, channel, activityFlag = 0;
  /* Check for proximity */
  for(vector=EVENT_PROXIMITY_0; vector<=EVENT_PROXIMITY_7; vector++) {
    unsigned char *hv = GLOBAL(env,event_handler_vectors);
    if(hv[vector]!=VECTOR_NONE && !(hv[vector]&VECTOR_DISABLED_FLAG)) {
      activityFlag = 1;
      channel = vector-EVENT_PROXIMITY_0;
      printf("<%d>",channel); fflush(stdout);
      if(isObjectNearby(USSRONLYC(env) channel)) {
	StoredProgram *program = GLOBAL(env,global_program_store)+hv[vector];
	USSRDEBUG(TRACE_EVENTS,printf("<%d>(%d) Handling vector %d\n", env->context, getRole(env), vector));
	interpret(USSRONLYC(env) &program->context, program->program, program->size, 0);
	break;
      }
    }
  }
  return activityFlag;
}

unsigned char schedulerAction(USSRONLY(USSREnv *env)) {
  unsigned char activityFlag = 0;
  activityFlag = checkForEvents(USSRONLY(env));
  /* Any tasks to execute? */
  if(GLOBAL(env,task_queue_end)!=GLOBAL(env,task_queue_start)) {
#ifdef VERBOSE_DEBUG
    USSRDEBUG(TRACE_TASKS,printf("<%d>(%d) {%d,%d} ", env->context, getRole(env), GLOBAL(env,task_queue_start), GLOBAL(env,task_queue_end)));
#endif
    /* Remove task from queue */
    int start = GLOBAL(env,task_queue_start);
    unsigned char task_type = GLOBAL(env,task_queue)[start].type;
    unsigned char task_flags = GLOBAL(env,task_queue)[start].flags;
    unsigned char task_arg_1 = GLOBAL(env,task_queue)[start].arg1;
    unsigned char task_arg_2 = GLOBAL(env,task_queue)[start].arg2;
    unsigned char task_arg_3 = GLOBAL(env,task_queue)[start].arg3;
    unsigned char task_arg_4 = GLOBAL(env,task_queue)[start].arg4;
    /* Process task */
    USSRDEBUG(TRACE_TASKS,printf("<%d>(%d) Found task in queue @%d: %d(%d,%d,%d,%d)\n", env->context, getRole(env), start, task_type, task_arg_1, task_arg_2, task_arg_3, task_arg_4));
    if(task_flags&TASK_FLAG_DISCARD) {
      USSRDEBUG(TRACE_TASKS,printf("<%d>(%d) Task @%d has discard flag, skipping\n", env->context, getRole(env), start));
    } else
      switch(task_type) {
      case TASK_INTERPRET:
	{
	  /* Execute program, free or reschedule afterwards */
	  USSRDEBUG(TRACE_TASKS|TRACE_MIGRATION,printf("<%d>(%d) Interpreting program @%d, mask=%d\n", env->context, getRole(env), task_arg_1, GLOBAL(env,global_program_store_use_flags)));
	  if(!interpret(USSRONLYC(env) &GLOBAL(env,global_program_store)[task_arg_1].context, GLOBAL(env,global_program_store)[task_arg_1].program, GLOBAL(env,global_program_store)[task_arg_1].size,task_arg_2))
	    GLOBAL(env,global_program_store_use_flags) ^= (1<<task_arg_1);
	  else { /* Reschedule */
	    Task *task;
	    disable_interrupts();
	    task = allocate_task(USSRONLY(env));
	    task->type = TASK_INTERPRET;
	    task->flags = 0;
	    task->arg1 = task_arg_1;
	    task->arg2 = task_arg_2;
	    enqueue_task(USSRONLYC(env) task);
	    enable_interrupts();
	  }
	  USSRDEBUG(TRACE_TASKS|TRACE_MIGRATION,printf("<%d>(%d) Done with program @%d, mask=%d\n", env->context, getRole(env), task_arg_1, GLOBAL(env,global_program_store_use_flags)));
	  break;
	}
      case TASK_COMMAND:
	{
	  unsigned char channel;
	  CommandTask* task = (CommandTask*)(GLOBAL(env,task_queue)+start);
	  for(channel=0; channel<8; channel++)
	    if(channel!=task->virtual_channel) resendCommandMaybe(USSRONLYC(env) channel, task);
	  if(check_role_instanceof(GLOBAL(env,role),task->role)) {
	    USSRDEBUG(TRACE_TASKS|TRACE_COMMANDS,printf("<%d>(%d) Evaluating command %d %d\n", env->context, getRole(env), task->command, task->argument));
	    execute_command(USSRONLYC(env) task->command, task->argument);
	  } else
	    USSRDEBUG(TRACE_TASKS|TRACE_COMMANDS,printf("<%d>(%d) Rejecting command %d %d\n", env->context, getRole(env), task->command, task->argument));
	  break;
	}
      default:
	report_error(USSRONLYC(env) ERROR_UNKNOWN_TASK_TYPE,task_type);
      }
    GLOBAL(env,task_queue_start) = (start+1)%MAX_N_ENQUEUED_TASKS;
    return 1;
  }
  return activityFlag;
}

signed char updateLED(signed char pos) {
  unsigned char bit;
  /* compute bit and next position */
  if(pos>=0) {
    bit = pos++;
    if(pos==9) pos = -8;
  } else
    bit = -(pos++);
#ifndef USSR
  setNorthIOPort(1<<bit);
#endif
  return pos;
}

unsigned char store_program(USSRONLYC(USSREnv *env) ProgramPacket *packet, unsigned char messageSize, unsigned char channel) {
  unsigned char index;
  for(index=0; index<MAX_N_STORED_PROGRAMS; index++) {
    if(((1<<index)&GLOBAL(env,global_program_store_use_flags))==0) {
      StoredProgram* program = GLOBAL(env,global_program_store)+index;
      GLOBAL(env,global_program_store_use_flags) |= (1<<index);
      program->context.mod_x = packet->x;
      program->context.mod_y = packet->y;
      program->context.mod_z = packet->z;
      program->context.mod_rotation = packet->r;
      program->context.incoming_virtual_channel = packet->virtual_channel;
      program->context.incoming_physical_channel = channel;
      program->context.program_id = packet->id;
      program->size = messageSize-MT_PACKET_HEADER_SIZE;
      memcpy(program->program, packet->program, program->size);
#ifdef VERBOSE_DEBUG
      printf("Stored program {x=%d,y=%d,z=%d,r=%d,vc=%d,pc=%d,sz=%d}\n",
	     program->context.mod_x,
	     program->context.mod_y,
	     program->context.mod_z,
	     program->context.mod_rotation,
	     program->context.incoming_virtual_channel,
	     program->context.incoming_physical_channel,
	     program->size);
#endif
      return index;
    }
  }
  report_error(USSRONLYC(env) ERROR_PROGRAM_STORE_FULL,0);
  return 0;
}

#define DCD_PERFECT_CACHE
#ifdef DCD_PERFECT_CACHE
#ifndef USSR
#error "Perfect cache configuration is only available for the simulator"
#endif
#include <ussr_internal.h>
unsigned char fresh_message(USSRONLYC(USSREnv *env) Packet *packet) {
  return ussr_call_int_controller_method(env, "dcd_perfect_cache", "(IIIII)I", (int)packet->id, (int)packet->x, (int)packet->y, (int)packet->z, (int)packet->r);
}
#else
static inline unsigned char inrange(unsigned char value, unsigned char min, unsigned char max) {
  if(min<max)
    return value>=min && value<max;
  else
    return value>=max || value<min;
}

unsigned char fresh_message(USSRONLYC(USSREnv *env) Packet *packet) {
  MessageCache *cache; int index;
  cache = GLOBAL(env,message_cache);
  int limit = GLOBAL(env,message_cache_size);
  if(packet->x==0 && packet->y==0 && packet->z==0) { USSRDEBUG(TRACE_CACHE,printf("<%d,X1:packet from this module>\n",env->context)); return 0; }; /* Packet was sent from this module */
  for(index=0; index<limit; index++)
    if(packet->x==cache[index].x && packet->y==cache[index].y && packet->z==cache[index].z && packet->r==cache[index].r) { /* hit */
      while(inrange(packet->id,cache[index].min_id+MESSAGE_CACHE_WINDOW_SIZE,cache[index].min_id+MESSAGE_CACHE_WINDOW_SIZE+MESSAGE_CACHE_FUTURE_SIZE)) {
	/* Message within accepted future range, we need to shift the cache */
	cache[index].seen_id >>= 1;
	cache[index].min_id += 1;
      }
      if(inrange(packet->id,cache[index].min_id,cache[index].min_id+MESSAGE_CACHE_WINDOW_SIZE)) {
	if((1<<packet->id) & cache[index].seen_id) { USSRDEBUG(TRACE_CACHE,printf("<%d@%d,X2:message seen before>\n",env->context,index)); return 0; };/* Message we've seen before */
	cache[index].seen_id |= 1<<packet->id;
	USSRDEBUG(TRACE_CACHE,printf("<%d@%d,X3:fresh message known origin>\n",env->context,index));
	return 1; /* Fresh message from known origin */
      } else {
	USSRDEBUG(TRACE_CACHE,printf("<%d@%d,X4:old message %d: (%d,%d,%d)@%d:min=%d,seen=%d>\n",env->context,index,packet->id,packet->x,packet->y,packet->z,packet->r,cache[index].min_id,cache[index].seen_id));
	return 0; /* Old message */
      }
    }
  /* Unknown origin, fresh message */
  if(index<MESSAGE_CACHE_MAX_SIZE)
    GLOBAL(env,message_cache_size)++;
  else
    index = (GLOBAL(env,message_cache_next)++)%MESSAGE_CACHE_MAX_SIZE;
  cache[index].x = packet->x;
  cache[index].y = packet->y;
  cache[index].z = packet->z;
  cache[index].r = packet->r;
  cache[index].seen_id = 1<<packet->id;
  USSRDEBUG(TRACE_CACHE,printf("<%d@%d,X5:fresh message unknown origin>\n",env->context,index));
  return 1;
}
#endif

static int checksum(unsigned char *bytes, int max) {
  int sum = 0, index = 0;
  for(index=0; index<max; index++) sum += bytes[index];
  return sum;
}

void handleMessage(USSRONLYC(USSREnv *env) unsigned char* message, unsigned char messageSize, unsigned char channel) {
  unsigned char id;
  Packet *p = (Packet*)message;
  USSRDEBUG(TRACE_NETWORK,printf("### Receive <%d> name=%d, type=%d, pch=%d, vch=%d, pos=(%d,%d,%d), r=%d\n", env->context, getRole(env), p->header, channel, p->virtual_channel, p->x, p->y, p->z, p->r));
  USSRDEBUG(TRACE_NETWORK,printf("<%d>(%d) Got a message on channel %d: id=%d,pos=(%d,%d,%d),r=%d,vc=%d {%d} ", env->context, getRole(env), channel, p->id, p->x, p->y, p->z, p->r, p->virtual_channel, checksum(message,messageSize)));
  if(!fresh_message(USSRONLYC(env) p)) {
    USSRDEBUG(TRACE_NETWORK,printf(" *** Rejecting message (type=%d), found in cache\n", p->header));
    return;
  }
  switch(p->header) {
  case 0:
    /* Ignore spurious messages with zero as the first byte on the real hardware */
    USSRDEBUG(TRACE_NETWORK,printf("Warning: zero-headed message (size=%d)\n ", messageSize));
    break;
  case MT_PROGRAM:
    {
      ProgramPacket *packet = (ProgramPacket*)p;
      Task *task;
      USSRDEBUG(TRACE_NETWORK|TRACE_MIGRATION,printf("Received program pos=(%d,%d,%d)@%d, vc=%d\n", packet->x, packet->y, packet->z, packet->r, packet->virtual_channel));
#ifdef VERBOSE_DEBUG
      print_program(packet->program, messageSize-MT_PACKET_HEADER_SIZE);
#endif
      id = store_program(USSRONLYC(env) packet, messageSize, channel);
      task = allocate_task(USSRONLY(env));
      task->type = TASK_INTERPRET;
      task->flags = 0;
      task->arg1 = id;
      task->arg2 = 0; /* Default argument is 0 */
      enqueue_task(USSRONLYC(env) task);
    }
    break;
  case MT_COMMAND:
    {
      CommandPacket *packet = (CommandPacket*)p;
      CommandTask *task;
      USSRDEBUG(TRACE_NETWORK|TRACE_COMMANDS,printf("<%d>(%d) Received remote command %d(%d) for role %d\n", env->context, getRole(env), packet->command, packet->argument, packet->role));
      task = (CommandTask*)allocate_task(USSRONLY(env));
      task->type = TASK_COMMAND;
      task->flags = 0;
      task->role = packet->role;
      task->command = packet->command;
      task->argument = packet->argument;
      task->physical_channel = channel;
      task->x = packet->x;
      task->y = packet->y;
      task->z = packet->z;
      task->r = packet->r;
      task->virtual_channel = packet->virtual_channel;
      task->packet_id = packet->id;
      enqueue_task(USSRONLYC(env) (Task*)task);
    }
    break;
  case MT_NOTIFY:
    {
      NotifyPacket *packet = (NotifyPacket*)p;
      GLOBAL(env,neighbor_role_cache)[p->virtual_channel] = packet->role;
      USSRDEBUG(TRACE_EVENTS,printf("<%d> Neighbor at virtual connector %d (physical %d) now has role %d\n", env->context, packet->virtual_channel, channel, packet->role));
    }
    break;
  default:
    USSRDEBUG(TRACE_NETWORK,printf("Unknown message (header=%d, size=%d)\n ", message[0], messageSize));
    report_error(USSRONLYC(env) ERROR_UNKNOWN_MESSAGE,message[0]);
  }
}

void installProgramMessage(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char* program, unsigned char programSize) {
  unsigned char buffer[MAX_PROGRAM_SIZE+MT_PACKET_HEADER_SIZE];
  createProgramPacket(buffer,context,0,program,programSize);
  handleMessage(USSRONLYC(env) buffer,programSize+MT_PACKET_HEADER_SIZE,0);
}

extern void dcd_activate(USSRONLYC(USSREnv *env) int role);

void activate(USSRONLY(USSREnv *env)) {
  int role; signed char bitpos = 0; unsigned int iteration = 0; unsigned char active;
  role = getRole(USSRONLY(env));
  dcd_activate(USSRONLYC(env) role);
  controllerIterationSimulatorHook(USSRONLY(env),1);
  while(1) {
    active = schedulerAction(USSRONLY(env));
    controllerIterationSimulatorHook(USSRONLY(env),active);
    if(!((iteration++)%50)) bitpos = updateLED(bitpos);
  }
}

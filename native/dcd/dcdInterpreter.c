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

#ifdef USSR
static int module_rotation_cache[1000];
static int cache_initialized = 0;
#endif

void execute_command(USSRONLYC(USSREnv *env) unsigned char command, unsigned char argument) {
  switch(command) {
  case CMD_ROTATE_CLOCKWISE:
    USSRDEBUG(TRACE_ACTUATION,printf("<%d>(%d) Actuate: ROTATE_CLOCKWISE(%d) = rotateContinuously(%d)\n", env->context, getRole(env), argument, argument));
    rotateContinuously(USSRONLYC(env) argument);
    break;
  case CMD_ROTATE_COUNTERCLOCKWISE:
    USSRDEBUG(TRACE_ACTUATION,printf("<%d>(%d) Actuate: ROTATE_COUNTERCLOCKWISE(%d) = rotateContinuously(%d)\n", env->context, getRole(env), argument, -argument));
    rotateContinuously(USSRONLYC(env) -argument);
    break;
  case CMD_ROTATE_STOP:
    USSRDEBUG(TRACE_ACTUATION,printf("<%d>(%d) Actuate: ROTATE_STOP(%d) = centerStop\n", env->context, getRole(env), argument));
    centerStop(USSRONLY(env));
    break;
  case CMD_ROTATE_DEGREES:
    USSRDEBUG(TRACE_ACTUATION,printf("<%d>(%d) Actuate: ROTATE_DEGREES(%d) = rotateDegrees(%d)\n", env->context, getRole(env), argument, 10*(signed char)argument));
    rotateDegrees(USSRONLYC(env) 10*(signed char)argument);
    break;
  case CMD_ROTATE_TO:
    USSRDEBUG(TRACE_ACTUATION,printf("<%d>(%d) Actuate: ROTATE_TO(%d) = rotateToDegree(%d)\n", env->context, getRole(env), argument, 10*(signed char)argument));
    rotateToDegree(USSRONLYC(env) 10*(signed char)argument);
    break;
  default:
    USSRDEBUG(TRACE_COMMANDS,printf("Got command %d (mx=%d), role=%d\n", command, MAX_N_GENERIC_COMMANDS,GLOBAL(env,role)));
    if(command>=MAX_N_GENERIC_COMMANDS) {
      unsigned char user_command = command-MAX_N_GENERIC_COMMANDS;
      if(user_command<MAX_ROLE_COMMANDS) {
	unsigned char index;
	index = GLOBAL(env,command_table)[GLOBAL(env,role)][user_command];
	if(index!=COMMAND_NONE) {
	  StoredProgram *program = GLOBAL(env,global_program_store)+index;
	  if(interpret(USSRONLYC(env) &program->context, program->program, program->size, argument)) {
	    Task *task;
	    disable_interrupts();
	    task = allocate_task(USSRONLY(env));
	    task->type = TASK_INTERPRET;
	    task->flags = 0;
	    task->arg1 = index;
	    task->arg2 = argument;
	    enqueue_task(USSRONLYC(env) task);
	    enable_interrupts();
	  }
	  return;
	}
      }
    }
    /*report_error(USSRONLYC(env) ERROR_UNKNOWN_COMMAND,command);*/
    printf("Warning: ignoring unknown command %d (my role=%d)\n",command,GLOBAL(env,role));
  }
}

static inline void stack_push(USSRONLYC(USSREnv *env) char *stack, unsigned char *sp, char value) {
  if(*sp==MAX_STACK_SIZE) {
    report_error(USSRONLYC(env) INTERPRETER_STACK_OVERFLOW,*sp);
    return;
  }
  stack[(*sp)++] = value;
}

static inline char stack_pop(USSRONLYC(USSREnv *env) char *stack, unsigned char *sp) {
  if(sp==0) {
    report_error(USSRONLYC(env) INTERPRETER_STACK_UNDERFLOW,*sp);
    return 0;
  }
  return stack[--(*sp)];
}

unsigned char count_bits(unsigned char value) {
  unsigned char total = 0;
  unsigned char count;
  for(count=0; count<8; count++) {
    if(value&1) total++;
    value>>=1;
  }
  return total;
}

static unsigned char compute_2_dir(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char dir, unsigned char dir1, unsigned char dir2) {
  unsigned char total = 0;
  unsigned char c0 = virtual2physical(USSRONLYC(env) context, dir1);
  unsigned char c4 = virtual2physical(USSRONLYC(env) context, dir2);
  unsigned char is0 = isOtherConnectorNearby(USSRONLYC(env) c0);
  unsigned char is4 = isOtherConnectorNearby(USSRONLYC(env) c4);
  if(is0) total++;
  if(is4) total++;
  USSRDEBUG(TRACE_COMPASS,printf("  <%6d> (Computing connected dir %d [%d->%d]: %d->%d=%d, %d->%d=%d, total=%d)\n", env->context, dir, context->incoming_virtual_channel, context->incoming_physical_channel, dir1, c0, is0, dir2, c4, is4, total));
  return total;
}

// need function mapping direction to virtual connectors!
// for each relevant virtual connector vc, return 1 if GLOBAL(env,neighbor_role_cache)[vc]==role
unsigned char check_neighbor_role(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char direction, unsigned char role) {
  unsigned char c;
  switch(context->mod_rotation) {
  case ARG_NORTH_SOUTH:
    switch(direction) {
    case ARG_DOWN:
      return check_role_instanceof(GLOBAL(env,neighbor_role_cache)[2],role) || check_role_instanceof(GLOBAL(env,neighbor_role_cache)[6],role);
    default:
      report_error(USSRONLYC(env) ERROR_ILLEGAL_CONNECTION_DIRECTION,direction);
    }
  case ARG_EAST_WEST:
    switch(direction) {
    case ARG_DOWN:
      return check_role_instanceof(GLOBAL(env,neighbor_role_cache)[2],role) || check_role_instanceof(GLOBAL(env,neighbor_role_cache)[6],role);
    default:
      report_error(USSRONLYC(env) ERROR_ILLEGAL_CONNECTION_DIRECTION,direction);
    }
  case ARG_UP_DOWN:
    switch(direction) {
    case ARG_DOWN:
      for(c=4; c<8; c++) if(check_role_instanceof(GLOBAL(env,neighbor_role_cache)[c],role)) return 1;
      return 0;
    default:
      report_error(USSRONLYC(env) ERROR_ILLEGAL_CONNECTION_DIRECTION,direction);
    }
  default:
    report_error(USSRONLYC(env) ERROR_ILLEGAL_AXIS_DIRECTION,context->mod_rotation);
    return 0;
  }      
}

unsigned char compute_connected_dir(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char dir) {
  unsigned char total = 0, index;
  switch(context->mod_rotation) {
  case ARG_NORTH_SOUTH:
    switch(dir) {
    case ARG_NORTH:
      for(index=0; index<4; index++) if(isOtherConnectorNearby(USSRONLYC(env) virtual2physical(USSRONLYC(env) context, index))) total++;
      return total;
    case ARG_DOWN:
      return compute_2_dir(USSRONLYC(env) context, dir, 2,6);
    default:
      report_error(USSRONLYC(env) ERROR_ILLEGAL_CONNECTION_DIRECTION,dir);
      return 0;
    }
  case ARG_EAST_WEST:
    switch(dir) {
    case ARG_UP:
      return compute_2_dir(USSRONLYC(env) context, dir, 0,4);
    case ARG_EAST:
      for(index=0; index<4; index++) if(isOtherConnectorNearby(USSRONLYC(env) virtual2physical(USSRONLYC(env) context, index))) total++;
      return total;
    case ARG_DOWN:
      return compute_2_dir(USSRONLYC(env) context, dir, 2,6);
    default:
      report_error(USSRONLYC(env) ERROR_ILLEGAL_CONNECTION_DIRECTION,dir);
      return 0;
    }
  default:
    report_error(USSRONLYC(env) ERROR_ILLEGAL_AXIS_DIRECTION,context->mod_rotation);
    return 0;
  }
}

unsigned char deprecated_compute_connected_dir(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char dir) {
  unsigned char total = 0, index;
  switch(context->mod_rotation) {
  case ARG_NORTH_SOUTH:
    switch(dir) {
    case ARG_NORTH:
      for(index=0; index<4; index++) if(isOtherConnectorNearby(USSRONLYC(env) virtual2physical(USSRONLYC(env) context, index))) total++;
      return total;
    case ARG_DOWN:
      return compute_2_dir(USSRONLYC(env) context, dir, 2,6);
    default:
      report_error(USSRONLYC(env) ERROR_ILLEGAL_CONNECTION_DIRECTION,dir);
      return 0;
    }
  case ARG_EAST_WEST:
    switch(dir) {
    case ARG_UP:
      return compute_2_dir(USSRONLYC(env) context, dir, 0,4);
    case ARG_EAST:
      for(index=0; index<4; index++) if(isOtherConnectorNearby(USSRONLYC(env) virtual2physical(USSRONLYC(env) context, index))) total++;
      return total;
    case ARG_WEST:
      for(index=4; index<8; index++) if(isOtherConnectorNearby(USSRONLYC(env) virtual2physical(USSRONLYC(env) context, index))) total++;
      return total;
    case ARG_DOWN:
      return compute_2_dir(USSRONLYC(env) context, dir, 2,6);
    default:
      report_error(USSRONLYC(env) ERROR_ILLEGAL_CONNECTION_DIRECTION,dir);
      return 0;
    }
  default:
    report_error(USSRONLYC(env) ERROR_ILLEGAL_AXIS_DIRECTION,context->mod_rotation);
    return 0;
  }
}

void migrate_continue(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char *program, unsigned char program_size) {
  unsigned char except = context->incoming_virtual_channel;
  unsigned char channel;
  for(channel=0; channel<8; channel++)
    if(channel!=except) {
      USSRDEBUG(TRACE_MIGRATION,printf("  <%6d> (Considering migrate continue on channel %d)\n", env->context, channel));
      sendProgramMaybe(USSRONLYC(env) context, channel, program, program_size);
    }
}

void set_role_notify(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char role) {
  unsigned char channel;
  GLOBAL(env,role)=role;
  USSRDEBUG(TRACE_EVENTS,printf("set role notify: module <%d>(%d) set to %d\n", env->context, getRole(env), role));
  for(channel=0; channel<8; channel++)
    notifyRoleChange(USSRONLYC(env) context, channel, role);
}

unsigned char save_program(USSRONLYC(USSREnv *env) InterpreterContext  *context, unsigned char *source_program, unsigned char adr, unsigned char size) {
  unsigned char index;
  StoredProgram* target_program;
  /* Find vacant program slot */
  disable_interrupts(USSRONLY(env));
  for(index=0; index<MAX_N_STORED_PROGRAMS; index++)
    if(((1<<index)&GLOBAL(env,global_program_store_use_flags))==0) {
      GLOBAL(env,global_program_store_use_flags) |= (1<<index);
      break;
    }
  enable_interrupts(USSRONLY(env));
  if(index==MAX_N_STORED_PROGRAMS) {
    report_error(USSRONLYC(env) ERROR_PROGRAM_STORE_FULL,1);
    return 255;
  }
  /* Store program in slot */
  target_program = GLOBAL(env,global_program_store)+index;
  target_program->context.mod_x = context->mod_x;
  target_program->context.mod_y = context->mod_y;
  target_program->context.mod_z = context->mod_z;
  target_program->context.mod_rotation = context->mod_rotation;
  target_program->context.incoming_virtual_channel = context->incoming_virtual_channel;
  target_program->context.incoming_physical_channel = context->incoming_physical_channel;
  target_program->size = size;
  memcpy(target_program->program, source_program+adr, size);
  /* Return slot number */
  return index;
}

void install_event_handler(USSRONLYC(USSREnv *env) InterpreterContext  *context, unsigned char *source_program, unsigned char vector, unsigned char adr, unsigned char size) {
  unsigned char index = save_program(USSRONLYC(env) context, source_program, adr, size);
  /* Set up vector */
  GLOBAL(env,event_handler_vectors[vector]) = index;
  USSRDEBUG(TRACE_EVENTS,printf("<%d>(%d) Stored event handler in slot %d, queue: {%d,%d}\n", env->context, getRole(env), index, GLOBAL(env,task_queue_start), GLOBAL(env,task_queue_end)));
}

void install_command(USSRONLYC(USSREnv *env) InterpreterContext  *context, unsigned char *source_program, unsigned char role, unsigned char raw_command, unsigned char adr, unsigned char size) {
  unsigned char index = save_program(USSRONLYC(env) context, source_program, adr, size);
  unsigned char command, r;
  if(raw_command<MAX_N_GENERIC_COMMANDS)
    report_error(USSRONLYC(env) ERROR_ILLEGAL_COMMAND_INSTALL,command);
  command = raw_command - MAX_N_GENERIC_COMMANDS;
  if((role>MAX_N_ROLES && role!=ROLE_ANY) || command>MAX_ROLE_COMMANDS)
    report_error(USSRONLYC(env) ERROR_ILLEGAL_COMMAND_INSTALL,(role<<4)+command);
  if(role==ROLE_ANY) {
      GLOBAL(env,command_table)[0][command] = index;
      USSRDEBUG(TRACE_EVENTS,printf("<%d>(%d) Installed command for ROLE_ANY,id=%d(%d) in slot %d\n", env->context, getRole(env), r, raw_command, command, index));
  }
  for(r=1; r<MAX_N_ROLES; r++)
    if(check_role_instanceof(r,role)) {
      GLOBAL(env,command_table)[r][command] = index;
      USSRDEBUG(TRACE_EVENTS,printf("<%d>(%d) Installed command role=%d,id=%d(%d) in slot %d\n", env->context, getRole(env), r, raw_command, command, index));
    }
}

void clear_event_handler(USSRONLYC(USSREnv *env) unsigned char vector) {
  int c;
  GLOBAL(env,event_handler_vectors[vector]) = VECTOR_NONE;
  USSRDEBUG(TRACE_EVENTS,printf("<%d>(%d) Cleared vector %d\n", env->context, getRole(env), vector));
#ifdef VERBOSE_DEBUG
  printf("; vector = ");
  for(c=0; c<MAX_N_EVENT_TYPES; c++) printf("%d ", GLOBAL(env,event_handler_vectors)[c]);
#endif
  GLOBAL(env,global_program_store_use_flags) ^= (1<<vector);
}

void disable_event_handler(USSRONLYC(USSREnv *env) unsigned char vector) {
  int c;
  if(GLOBAL(env,event_handler_vectors[vector])==VECTOR_NONE) return;
  GLOBAL(env,event_handler_vectors[vector]) |= VECTOR_DISABLED_FLAG;
  USSRDEBUG(TRACE_EVENTS,printf("<%d>(%d) Disabled vector %d\n", env->context, getRole(env), vector));
#ifdef VERBOSE_DEBUG
  printf("; vector = [ ");
  for(c=0; c<MAX_N_EVENT_TYPES; c++) printf("%d ", GLOBAL(env,event_handler_vectors)[c]);
  printf("]\n");
#endif
}

void enable_event_handler(USSRONLYC(USSREnv *env) unsigned char vector) {
  int c;
  if(GLOBAL(env,event_handler_vectors[vector])==VECTOR_NONE) return;
  GLOBAL(env,event_handler_vectors[vector]) ^= VECTOR_DISABLED_FLAG;
  USSRDEBUG(TRACE_EVENTS,printf("<%d>(%d) Enabled vector %d\n", env->context, getRole(env), vector));
#ifdef VERBOSE_DEBUG
  printf("; vector = [ ");
  for(c=0; c<MAX_N_EVENT_TYPES; c++) printf("%d ", GLOBAL(env,event_handler_vectors)[c]);
  printf("]\n");
#endif
}

void clear_command_queue(USSRONLYC(USSREnv *env) unsigned char command) {
  unsigned char index;
  index = GLOBAL(env, task_queue_start);
  while(index!=GLOBAL(env,task_queue_end)) {
    Task *task = GLOBAL(env,task_queue)+index;
    if(task->type==TASK_COMMAND && task->arg2==command) task->flags |= TASK_FLAG_DISCARD;
    index = (index+1)%MAX_N_ENQUEUED_TASKS;
  }
}

void sleep_rotations(USSRONLYC(USSREnv *env) unsigned char rotations) {
  unsigned char rotations_seen = 0, starting_joint_pos = getJointPosition(USSRONLY(env)), currently_at_start = 1;
  while(rotations_seen<rotations) {
    unsigned char current_pos = getJointPosition(USSRONLY(env));
    if(currently_at_start) {
      if(current_pos!=starting_joint_pos) currently_at_start = 0;
    } else {
      if(current_pos==starting_joint_pos) {
	currently_at_start=1;
	rotations_seen++;
      }
    }
    controllerIterationSimulatorHook(USSRONLY(env),1);
  }
}

void send_command(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char qual_role, unsigned char command, unsigned char argument) {
  unsigned char channel;
  for(channel=0; channel<8; channel++)
    sendCommandMaybe(USSRONLYC(env) context, channel, qual_role, command, argument, 1, 0);
  if(check_role_instanceof(GLOBAL(env,role),qual_role))
    execute_command(USSRONLYC(env) command, argument);
}

unsigned char function_apply(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char function, unsigned char argument) {
  if(function<CMD_MAX || function>=128) { /* Command, internal or user-defined */
    execute_command(USSRONLYC(env) function, argument);
  } else { /* Special primop */
    switch(function) {
    case PRIM_APPLY:
      /* if(argument<128) { // special case: command taking no argument (or rather: a module and the unit value = 0) */
	send_command(USSRONLYC(env) context, ROLE_ANY, argument, 0);
	/* } else { // closure
      report_error(USSRONLYC(env) ERROR_ILLEGAL_CLOSURE, argument);
      }*/
      break;
    default:
    report_error(USSRONLYC(env) ERROR_ILLEGAL_FUNCTION, function);
    }
  }
  return 0;
}

unsigned char do_interpret(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char *program, unsigned char program_size, unsigned char argument, unsigned char pc, char *stack, unsigned char sp) {
  int instruction_counter = 0;
  unsigned char role = getRole(USSRONLY(env));
  int interpreter_debug_flags = 0;
  printf("Interpreting program at address %d\n", program);
  while(pc<program_size) {
    USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("  <%6d,%2d> %2d(%2d) [%3d %3d %3d %3d %3d]: ", env->context, role, pc, sp, stack[0], stack[1], stack[2], stack[3], stack[4]));
    if(instruction_counter++>MAX_INSTRUCTION_COUNT) {
      report_error(USSRONLYC(env) INTERPRETER_INSTRUCTION_COUNT_EXCEEDED,pc);
      return 0;
    }
    if(pc>=program_size) {
      report_error(USSRONLYC(env) INTERPRETER_PC_OVERFLOW,pc);
      return 0;
    }
    switch(program[pc++]) {
    case INS_CENTER_POSITION_EW:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_CENTER_POSITION_EW\n"));
      stack_push(USSRONLYC(env) stack,&sp,context->mod_rotation==ARG_EAST_WEST);
      break;
    case INS_CENTER_POSITION_NS:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_CENTER_POSITION_NS\n")); 
      stack_push(USSRONLYC(env) stack,&sp,context->mod_rotation==ARG_NORTH_SOUTH);
      break;
    case INS_CENTER_POSITION_UD:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_CENTER_POSITION_UD\n")); 
      stack_push(USSRONLYC(env) stack,&sp,context->mod_rotation==ARG_UP_DOWN);
      break;
    case INS_CONNECTED:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_CONNECTED\n"));
      {
	signed char channel;
	unsigned char result = 0;
	for(channel=7; channel>=0; channel--) {
	  if(isOtherConnectorNearby(USSRONLYC(env) channel)) result |= 1;
	  result <<= 1;
	}
	stack_push(USSRONLYC(env) stack,&sp,result);
      }
      break;
    case INS_CONNECTED_SIZEOF:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_CONNECTED_SIZEOF\n"));
      {
	unsigned char channel, total = 0;
	for(channel=0; channel<8; channel++)
	  if(isOtherConnectorNearby(USSRONLYC(env) channel)) total++;
	stack_push(USSRONLYC(env) stack,&sp,total);
      }
      break;
    case INS_SIZEOF:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_SIZEOF\n"));
      {
	unsigned char tos = stack_pop(USSRONLYC(env) stack,&sp);
	unsigned char result = 0;
	char i;
	for(i=0; i<8; i++) {
	  if(tos&1) result++;
	  tos >>= 1;
	}
	stack_push(USSRONLYC(env) stack,&sp,result);
      }
      break;
    case INS_GREATER:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_GREATER\n"));
      {
	signed char tos = stack_pop(USSRONLYC(env) stack,&sp);
	signed char sos = stack_pop(USSRONLYC(env) stack,&sp);
	stack_push(USSRONLYC(env) stack,&sp,sos>tos);
      }
      break;
    case INS_EQUALS_0:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_EQUALS_0\n"));
      {
	char tos = stack_pop(USSRONLYC(env) stack,&sp);
	stack_push(USSRONLYC(env) stack,&sp,tos==0);
      }
      break;
    case INS_EQUALS_1:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_EQUALS_1\n"));
      {
	char tos = stack_pop(USSRONLYC(env) stack,&sp);
	stack_push(USSRONLYC(env) stack,&sp,tos==1);
      }
      break;
    case INS_CONNECTED_DIR_SIZEOF:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_CONNECTED_DIR_SIZEOF "));
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,print_arg(program,pc,1));
      {
	unsigned char dir = program[pc++];
	stack_push(USSRONLYC(env) stack,&sp,count_bits(deprecated_compute_connected_dir(USSRONLYC(env) context, dir)));
      }
      break;
    case INS_CONNECTED_DOWN_ROLE:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_CONNECTED_DOWN_ROLE %d\n", program[pc]));
      {
	unsigned char role = program[pc++];
#ifdef VERBOSE_DEBUGGING
	unsigned char c;
	USSRDEBUG2(interpreter_debug_flags,TRACE_EVENTS,printf("<%d> Looking for %d in [ ", env->context, role));
	for(c=0; c<8; c++) USSRDEBUG2(interpreter_debug_flags,TRACE_EVENTS,printf("%d ", GLOBAL(env,neighbor_role_cache[c])));
	USSRDEBUG2(interpreter_debug_flags,TRACE_EVENTS,printf("\n"));
#endif
	stack_push(USSRONLYC(env) stack, &sp, check_neighbor_role(USSRONLYC(env) context, ARG_DOWN, role));
      }
      break;
    case INS_HAS_ROLE:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_HAS_ROLE %d\n", program[pc]));
      stack_push(USSRONLYC(env) stack, &sp, check_role_instanceof(GLOBAL(env,role), program[pc++]));
      break;
    case INS_END_TERMINATE:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_END_TERMINATE\n"));
      return 0;
    case INS_END_REPEAT:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET|TRACE_BEHAVIOR,printf("INS_END_REPEAT\n"));
      return 1;
    case INS_IF_FALSE_GOTO:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_IF_FALSE_GOTO %d\n", program[pc]));
      if(!stack_pop(USSRONLYC(env) stack,&sp))
	pc = program[pc];
      else
	pc++;
      break;
    case INS_IF_TRUE_GOTO:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_IF_TRUE_GOTO %d\n", program[pc]));
      if(stack_pop(USSRONLYC(env) stack,&sp))
	pc = program[pc];
      else
	pc++;
      break;
    case INS_GOTO:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_GOTO %d\n", program[pc]));
      pc = program[pc];
      break;
    case INS_SET_ROLE_NOTIFY:
      USSRDEBUG2(interpreter_debug_flags,TRACE_EVENTS|TRACE_INTERPRET,printf("INS_SET_ROLE_NOTIFY %d\n", program[pc]));
      set_role_notify(USSRONLYC(env) context, program[pc++]);
      break;
    case INS_MIGRATE_CONTINUE:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_MIGRATE_CONTINUE\n"));
      migrate_continue(USSRONLYC(env) context, program, program_size);
      return 0;
    case INS_EVAL_COMMAND:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_EVAL_COMMAND %d %d\n", program[pc], program[pc+1]));
      execute_command(USSRONLYC(env) program[pc], program[pc+1]);
      pc+=2;
      break;
    case INS_NOP:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_NOP\n")); break;
    case INS_COORD_Y_GREATER:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_COORD_Y_GREATER %d\n", program[pc]));
      stack_push(USSRONLYC(env) stack, &sp, context->mod_y > stack_pop(USSRONLYC(env) stack, &sp));
      break;
    case INS_COORD_Y_LESSER:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_COORD_Y_LESSER %d\n", program[pc]));
      stack_push(USSRONLYC(env) stack, &sp, context->mod_y < stack_pop(USSRONLYC(env) stack, &sp));
      break;
    case INS_COORD_X_GREATER:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_COORD_X_GREATER %d\n", program[pc]));
      stack_push(USSRONLYC(env) stack, &sp, context->mod_x > stack_pop(USSRONLYC(env) stack, &sp));
      break;
    case INS_COORD_X_LESSER:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_COORD_X_LESSER %d\n", program[pc]));
      stack_push(USSRONLYC(env) stack, &sp, context->mod_x < stack_pop(USSRONLYC(env) stack, &sp));
      break;
    case INS_HANDLE_EVENT:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_HANDLE_EVENT %d %d %d\n", program[pc], program[pc+1], program[pc+2]));
      install_event_handler(USSRONLYC(env) context, program, program[pc], program[pc+1], program[pc+2]);
      pc+=3;
      break;
    case INS_INSTALL_COMMAND:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_INSTALL_COMMAND %d %d %d %d\n", program[pc], program[pc+1], program[pc+2], program[pc+3]));
      install_command(USSRONLYC(env) context, program, program[pc], program[pc+1], program[pc+2], program[pc+3]);
      pc+=4;
      break;
    case INS_CLEAR_EVENT:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_CLEAR_EVENT %d\n", program[pc]));
      clear_event_handler(USSRONLYC(env) program[pc++]);
      break;
    case INS_DISABLE_EVENT:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_DISABLE_EVENT %d\n", program[pc]));
      disable_event_handler(USSRONLYC(env) program[pc++]);
      break;
    case INS_ENABLE_EVENT:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_ENABLE_EVENT %d\n", program[pc]));
      enable_event_handler(USSRONLYC(env) program[pc++]);
      break;
    case INS_SEND_COMMAND:
      USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_SEND_COMMAND %d %d %d (pc=%d)\n", program[pc], program[pc+1], program[pc+2], pc));
      send_command(USSRONLYC(env) context, program[pc], program[pc+1], program[pc+2]);
      pc+=3;
      break;
    case INS_SLEEP:
      USSRDEBUG2(interpreter_debug_flags,TRACE_EVENTS|TRACE_INTERPRET,printf("INS_SLEEP\n"));
      delay(USSRONLYC(env) 1000);
      break;
    case INS_SLEEP_ROTATIONS:
      USSRDEBUG2(interpreter_debug_flags,TRACE_EVENTS|TRACE_INTERPRET,printf("INS_SLEEP_ROTATIONS %d\n", program[pc]));
      sleep_rotations(USSRONLYC(env) program[pc]);
      pc++;
      break;
    case INS_CLEAR_COMMAND_QUEUE:
      USSRDEBUG2(interpreter_debug_flags,TRACE_EVENTS|TRACE_TASKS,printf("INS_CLEAR_COMMAND_QUEUE %d\n", program[pc]));
      clear_command_queue(USSRONLYC(env) program[pc]);
      pc++;
      break;
    case INS_ACTIVATE_DEBUG:
      interpreter_debug_flags |= TRACE_INTERPRET;
      printf("### Activated debug: %d\n", interpreter_debug_flags);
      break;
    case INS_APPLY:
      {
	unsigned char function = stack_pop(USSRONLYC(env) stack, &sp);
	unsigned char arg = stack_pop(USSRONLYC(env) stack, &sp);
	unsigned char result = function_apply(USSRONLYC(env) context,function,arg);
	stack_push(USSRONLYC(env) stack, &sp, result);
	break;
      }
    case INS_PUSH_ARGUMENT:
      {
	unsigned char index = program[pc];
	if(index!=0) report_error(USSRONLYC(env) ERROR_ILLEGAL_ARGUMENT_INDEX, index);
	stack_push(USSRONLYC(env) stack, &sp, argument);
	pc++;
	break;
      }
    case INS_PUSHC:
      stack_push(USSRONLYC(env) stack, &sp, program[pc]);
      pc++;
      break;
    case INS_SET_LED:
      {
	USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_SET_LED %d (pc=%d)\n", program[pc], pc));
	unsigned char value = program[pc++];
	setNorthIOPort(USSRONLYC(env) value);
	break;
      }
    default:
      {
	unsigned char instruction = program[pc-1];
	if(instruction>127) {
	  USSRDEBUG2(interpreter_debug_flags,TRACE_INTERPRET,printf("INS_PUSHC %d\n", instruction^128));
	  stack_push(USSRONLYC(env) stack, &sp, instruction^128);
	} else {
	  USSRONLY(fprintf(stderr,"  Offending instruction: %d\n", instruction));
	  report_error(USSRONLYC(env) INTERPRETER_UNKNOWN_INSTRUCTION,pc-1);
	  return 0;
	}
      }
    }
    fflush(stdout);
  }
  USSRONLY(fprintf(stderr,"<%d>(%d) Warning: pc exceeded program size, pc=%d, max=%d\n", env->context, getRole(env), pc, program_size));
  return 0;
}
  
unsigned char interpret(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char *program, unsigned char size, unsigned char argument) {
  char stack[MAX_STACK_SIZE];
  int i;
  USSRDEBUG(TRACE_INTERPRET,printf("Executing program:\n"));
#ifdef VERBOSE_DEBUG
  print_program(program, size);
  printf("Trace:\n");
#endif
  for(i=0; i<MAX_STACK_SIZE; i++) stack[i] = 0;
  return do_interpret(USSRONLYC(env) context, program, size, argument, 0, stack, 0);
}


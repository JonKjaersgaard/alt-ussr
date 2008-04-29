#include "dcdController.h"
#include "dcdBytecode.h"
#include "roles.h"
#include "dcdError.h"

#include <stdio.h>

#ifdef USSR
int vm_trace_flags = /*TRACE_BEHAVIOR|*/ TRACE_EVENTS|TRACE_MIGRATION|TRACE_NETWORK; /*|TRACE_ACTUATION|TRACE_COMMANDS;*/
#endif

#define SIZE_program_findWheels 24
#define ROLE_program_findWheels ROLE_ANY
unsigned char program_findWheels[SIZE_program_findWheels] = {
  INS_CENTER_POSITION_EW,
  INS_IF_FALSE_GOTO, 23,
  INS_CONNECTED_SIZEOF,
  INS_EQUALS_1,
  INS_IF_FALSE_GOTO, 23,
  INS_CONNECTED_DIR_SIZEOF, ARG_UP,
  INS_EQUALS_1,
  INS_IF_FALSE_GOTO, 23,
  INS_CONNECTED_DIR_SIZEOF, ARG_EAST,
  INS_IF_FALSE_GOTO, 20,
  INS_SET_ROLE_NOTIFY, ROLE_RIGHT_WHEEL,
  INS_GOTO, 22,
  INS_SET_ROLE_NOTIFY, ROLE_LEFT_WHEEL,
  INS_END_TERMINATE,
  INS_MIGRATE_CONTINUE
};

#define SIZE_program_findWheels_START 30
#define ROLE_program_findWheels ROLE_ANY
unsigned char program_findWheels_START[SIZE_program_findWheels_START] = {
  INS_CENTER_POSITION_EW,
  INS_IF_FALSE_GOTO, 29,
  INS_CONNECTED_SIZEOF,
  INS_EQUALS_1,
  INS_IF_FALSE_GOTO, 29,
  INS_CONNECTED_DIR_SIZEOF, ARG_UP,
  INS_EQUALS_1,
  INS_IF_FALSE_GOTO, 29,
  INS_CONNECTED_DIR_SIZEOF, ARG_EAST,
  INS_IF_FALSE_GOTO, 23,
  INS_SET_ROLE_NOTIFY, ROLE_RIGHT_WHEEL,
  INS_EVAL_COMMAND, CMD_ROTATE_CLOCKWISE, 1,
  INS_GOTO, 28,
  INS_SET_ROLE_NOTIFY, ROLE_LEFT_WHEEL,  /* 20->23 */
  INS_EVAL_COMMAND, CMD_ROTATE_COUNTERCLOCKWISE, 1,
  INS_END_TERMINATE,
  INS_MIGRATE_CONTINUE
};

#define SIZE_program_installProximityEventHandler_right 21
#define ROLE_program_installProximityEventHandler_right ROLE_RIGHT_WHEEL
unsigned char program_installProximityEventHandler_right[SIZE_program_installProximityEventHandler_right] = {
  INS_HAS_ROLE, ROLE_RIGHT_WHEEL,
  INS_IF_FALSE_GOTO, 20,
  MK_INS_CONSTANT(0),
  INS_COORD_Y_LESSER,
  INS_IF_FALSE_GOTO, 12,
  INS_HANDLE_EVENT, EVENT_PROXIMITY_5, 13, 7,
  INS_END_TERMINATE,
  INS_DISABLE_EVENT, EVENT_PROXIMITY_5,
  INS_SEND_COMMAND, ROLE_WHEEL, 128, 0,
  INS_END_TERMINATE,
  INS_MIGRATE_CONTINUE
};

#define SIZE_program_installProximityEventHandler_left 21
#define ROLE_program_installProximityEventHandler_left ROLE_LEFT_WHEEL
unsigned char program_installProximityEventHandler_left[SIZE_program_installProximityEventHandler_left] = {
  INS_HAS_ROLE, ROLE_LEFT_WHEEL,
  INS_IF_FALSE_GOTO, 20,
  MK_INS_CONSTANT(0),
  INS_COORD_Y_LESSER,
  INS_IF_FALSE_GOTO, 12,
  INS_HANDLE_EVENT, EVENT_PROXIMITY_1, 13, 7,
  INS_END_TERMINATE,
  INS_DISABLE_EVENT, EVENT_PROXIMITY_1,
  INS_SEND_COMMAND, ROLE_WHEEL, 128, 0,
  INS_END_TERMINATE,
  INS_MIGRATE_CONTINUE
};

#define SIZE_program_findAxles 13
#define ROLE_program_findAxles ROLE_ANY
unsigned char program_findAxles[SIZE_program_findAxles] = {
  INS_CENTER_POSITION_UD,
  INS_IF_FALSE_GOTO, 12,
  INS_CONNECTED_DOWN_ROLE, ROLE_WHEEL,
  INS_EQUALS_0,
  INS_IF_FALSE_GOTO, 10,
  INS_GOTO, 12,
  INS_SET_ROLE_NOTIFY, ROLE_AXLE,
  INS_MIGRATE_CONTINUE
};

#define SIZE_program_installStopLeft 34
#define ROLE_program_installStopLeft ROLE_LEFT_WHEEL
unsigned char program_installStopLeft[SIZE_program_installStopLeft] = {
  INS_HAS_ROLE, ROLE_LEFT_WHEEL,
  INS_IF_FALSE_GOTO, 10,
  INS_INSTALL_COMMAND, ROLE_WHEEL, 128, 11, 23,
  INS_END_TERMINATE,
  INS_MIGRATE_CONTINUE,
  INS_EVAL_COMMAND, CMD_ROTATE_STOP, 0,
  INS_SET_ROLE_NOTIFY, ROLE_LEFT_WHEEL_REVERSE,
  INS_EVAL_COMMAND, CMD_ROTATE_CLOCKWISE, 1,
  INS_SLEEP_ROTATIONS, 3,
  INS_EVAL_COMMAND, CMD_ROTATE_STOP, 0,
  INS_SET_ROLE_NOTIFY, ROLE_RIGHT_WHEEL,
  INS_ENABLE_EVENT, EVENT_PROXIMITY_1,
  INS_EVAL_COMMAND, CMD_ROTATE_COUNTERCLOCKWISE, 1,
  INS_CLEAR_COMMAND_QUEUE, 128,
  INS_END_TERMINATE
};

#define SIZE_program_installStopRight 34
#define ROLE_program_installStopRight ROLE_RIGHT_WHEEL
unsigned char program_installStopRight[SIZE_program_installStopRight] = {
  INS_HAS_ROLE, ROLE_RIGHT_WHEEL,
  INS_IF_FALSE_GOTO, 10,
  INS_INSTALL_COMMAND, ROLE_WHEEL, 128, 11, 23,
  INS_END_TERMINATE,
  INS_MIGRATE_CONTINUE,
  INS_EVAL_COMMAND, CMD_ROTATE_STOP, 0,
  INS_SET_ROLE_NOTIFY, ROLE_RIGHT_WHEEL_REVERSE,
  INS_EVAL_COMMAND, CMD_ROTATE_COUNTERCLOCKWISE, 1,
  INS_SLEEP_ROTATIONS, 3,
  INS_EVAL_COMMAND, CMD_ROTATE_STOP, 0,
  INS_SET_ROLE_NOTIFY, ROLE_RIGHT_WHEEL,
  INS_ENABLE_EVENT, EVENT_PROXIMITY_5,
  INS_EVAL_COMMAND, CMD_ROTATE_CLOCKWISE, 1,
  INS_CLEAR_COMMAND_QUEUE, 128,
  INS_END_TERMINATE
};

#define SIZE_program_axleBehavior 53
#define ROLE_program_axleBehavior ROLE_AXLE
unsigned char program_axleBehavior[SIZE_program_axleBehavior] = {
  INS_HAS_ROLE, ROLE_AXLE,
  INS_IF_FALSE_GOTO, 12,
  INS_INSTALL_COMMAND, ROLE_AXLE, 128, 13, 40,
  INS_EVAL_COMMAND, 128, 0,
  INS_MIGRATE_CONTINUE,
  INS_CONNECTED_DOWN_ROLE, ROLE_REVERSE,  /* 0 */
  INS_IF_FALSE_GOTO, 36,                  /* 2 goto B */
  MK_INS_CONSTANT(0),                     /* 4 */
  INS_COORD_Y_LESSER,                     /* 5 */
  INS_IF_TRUE_GOTO, 24,                   /* 6 goto A */ 
  MK_INS_CONSTANT(1),                     /* 8 */
  INS_COORD_Y_GREATER,                    /* 9 */
  INS_IF_FALSE_GOTO, 36,                  /* 10 goto B */
  MK_INS_CONSTANT(0),                     /* 12 */
  INS_COORD_X_LESSER,                    /* 13 */
  INS_IF_TRUE_GOTO, 20,                   /* 14 */
  INS_EVAL_COMMAND, CMD_ROTATE_TO, 20,    /* 16 */
  INS_END_REPEAT,                         /* 19 */
  INS_EVAL_COMMAND, CMD_ROTATE_TO, 20,    /* 20 */
  INS_END_REPEAT,                         /* 23 */
  MK_INS_CONSTANT(0),                     /* 24 A */
  INS_COORD_X_LESSER,                    /* 25 */
  INS_IF_TRUE_GOTO, 32,                   /* 26 */
  INS_EVAL_COMMAND, CMD_ROTATE_TO, 16,    /* 28 */
  INS_END_REPEAT,                         /* 31 */
  INS_EVAL_COMMAND, CMD_ROTATE_TO, 16,    /* 32 C */
  INS_END_REPEAT,                         /* 35 */
  INS_EVAL_COMMAND, CMD_ROTATE_TO, 18,    /* 36 B */
  INS_END_REPEAT                          /* 39 */
};

#define SIZE_program_and_context_test 11
ProgramPacket program_and_context_test = {
  MT_PROGRAM,
  0,
  0, 0, 0, /* x y z */
  ARG_NORTH_SOUTH,
  2, /* vir-ch */
  { INS_EVAL_COMMAND, CMD_ROTATE_CLOCKWISE, 127, INS_END_TERMINATE }
};

#define SIZE_program_findArm 17
#define ROLE_program_findArm ROLE_ANY
unsigned char program_findArm[SIZE_program_findArm] = {
  INS_CENTER_POSITION_UD,
  INS_IF_FALSE_GOTO, 4,
  INS_MIGRATE_CONTINUE,
  INS_CONNECTED_SIZEOF,
  INS_EQUALS_1,
  INS_IF_FALSE_GOTO, 16,
  INS_CONNECTED_DIR_SIZEOF, ARG_DOWN,
  INS_EQUALS_1,
  INS_IF_FALSE_GOTO, 16,
  INS_SET_ROLE_NOTIFY, ROLE_FINGER,
  INS_END_TERMINATE,
  INS_MIGRATE_CONTINUE
};



static void test_action_1(USSRONLY(USSREnv *env)) {
  InterpreterContext context = { 0,0,0,ARG_NORTH_SOUTH, 0, 0, 0 }; /* assume physical channel = virtual channel */
  unsigned char channel;
  unsigned char id = 0;
  printf("################################\n");
  printf("##### TEST-1 DCD CONTROLLER# ###\n");
  printf("################################\n");
  for(channel=0; channel<8; channel++) {
    sendCommandMaybe(USSRONLYC(env) &context, channel, ROLE_ANY, CMD_ROTATE_STOP, 0, 0, id);
  }
}

static void test_action_2(USSRONLY(USSREnv *env)) {
  unsigned char pos;
  Task *task;
  printf("################################\n");
  printf("##### TEST-2 DCD CONTROLLER# ###\n");
  printf("################################\n");
  USSRONLY(printf("### Storing test program\n"));
  pos = store_program(USSRONLYC(env) &program_and_context_test, SIZE_program_and_context_test, 0);
  task = allocate_task(USSRONLY(env));
  task->type = TASK_INTERPRET;
  task->flags = 0;
  task->arg1 = pos;
  enqueue_task(USSRONLYC(env) task);
}

/* Currently assumes phy_ch==vir_ch for starting module */
static void arm_action(USSRONLY(USSREnv *env)) {
  char channel;
  InterpreterContext context = { 0,0,0,ARG_NORTH_SOUTH, 0, 0, 0 }; /* incoming_*_channel not used */
  printf("################################\n");
  printf("##ARM ACTION DCD CONTROLLER ###\n");
  printf("################################\n");
  delay(USSRONLYC(env) 100);
  USSRONLY(printf("*** Sending programs\n"));
  for(channel=0; channel<8; channel++) {
    sendProgramMaybe(USSRONLYC(env) &context, channel, program_findArm, SIZE_program_findArm);
  }
  USSRONLY(printf("*** Waiting\n"));
  delay(USSRONLYC(env) 500);
  USSRONLY(printf("*** Sending commands (would be better to schedule...)\n"));
  context.program_id++;
  for(channel=0; channel<8; channel++) {
    sendCommandMaybe(USSRONLYC(env) &context, channel, ROLE_FINGER, CMD_ROTATE_COUNTERCLOCKWISE, 0, 0, context.program_id);
  }
}

#define WAITTIME 1000

/* Currently assumes phy_ch==vir_ch for starting module */
static void car_action(USSRONLY(USSREnv *env)) {
  char channel;
  InterpreterContext context = { 0,0,0,ARG_NORTH_SOUTH, 0, 0, 0 }; /* incoming_*_channel not used */
  printf("################################\n");
  printf("## CAR ACTION DCD CONTROLLER ###\n");
  printf("################################\n");
  delay(USSRONLYC(env) WAITTIME);
  /* Discover wheels */
  USSRONLY(printf("*** Sending wheel discovery programs\n"));
  for(channel=0; channel<8; channel++) {
    sendProgramMaybe(USSRONLYC(env) &context, channel, program_findWheels, SIZE_program_findWheels);
  }
  USSRONLY(printf("*** Waiting\n"));
  delay(USSRONLYC(env) WAITTIME);
  /* Install left stop commands */
  USSRONLY(printf("*** Sending left stop command install programs\n"));
  context.program_id++;
  for(channel=0; channel<8; channel++) {
    sendProgramMaybe(USSRONLYC(env) &context, channel, program_installStopLeft, SIZE_program_installStopLeft);
  }
  USSRONLY(printf("*** Waiting\n"));
  delay(USSRONLYC(env) WAITTIME);
  /* Install right stop commands */
  USSRONLY(printf("*** Sending right stop command install programs\n"));
  context.program_id++;
  for(channel=0; channel<8; channel++) {
    sendProgramMaybe(USSRONLYC(env) &context, channel, program_installStopRight, SIZE_program_installStopRight);
  }
  USSRONLY(printf("*** Waiting\n"));
  delay(USSRONLYC(env) WAITTIME);
  /* Discover axles */
  USSRONLY(printf("*** Sending axle discovery programs\n"));
  context.program_id++;
  for(channel=0; channel<8; channel++) {
    sendProgramMaybe(USSRONLYC(env) &context, channel, program_findAxles, SIZE_program_findAxles);
  }
  USSRONLY(printf("*** Waiting\n"));
  delay(USSRONLYC(env) WAITTIME);
  /* Make axles behave nicely */
  USSRONLY(printf("*** Sending axle behaviors\n"));
  context.program_id++;
  for(channel=0; channel<8; channel++) {
    sendProgramMaybe(USSRONLYC(env) &context, channel, program_axleBehavior, SIZE_program_axleBehavior);
  }
  USSRONLY(printf("*** Waiting\n"));
  delay(USSRONLYC(env) WAITTIME);
  /* Make wheels turn */
  USSRONLY(printf("*** Sending drive commands (would be better to schedule...)\n"));
  context.program_id++;
  for(channel=0; channel<8; channel++) {
    sendCommandMaybe(USSRONLYC(env) &context, channel, ROLE_RIGHT_WHEEL, CMD_ROTATE_CLOCKWISE, 1, 0, context.program_id);
  }
  context.program_id++;
  for(channel=0; channel<8; channel++) {
    sendCommandMaybe(USSRONLYC(env) &context, channel, ROLE_LEFT_WHEEL, CMD_ROTATE_COUNTERCLOCKWISE, 1, 0, context.program_id);
    }
  /* Install event handlers */
  USSRONLY(printf("*** Waiting\n"));
  delay(USSRONLYC(env) WAITTIME);
  USSRONLY(printf("*** Sending event handlers\n"));
  context.program_id++;
  for(channel=0; channel<8; channel++) {
    sendProgramMaybe(USSRONLYC(env) &context, channel, program_installProximityEventHandler_left, SIZE_program_installProximityEventHandler_left);
  }
  context.program_id++;
  for(channel=0; channel<8; channel++) {
    sendProgramMaybe(USSRONLYC(env) &context, channel, program_installProximityEventHandler_right, SIZE_program_installProximityEventHandler_right);
  }
  USSRONLY(printf("*** Done\n"));
}

#define ROLE_Wheel ROLE_WHEEL
#define ROLE_RightWheel ROLE_RIGHT_WHEEL
#define ROLE_LeftWheel ROLE_LEFT_WHEEL
#define ROLE_Axle ROLE_AXLE
#define ROLE_Reverse ROLE_REVERSE
#include "/Users/ups/eclipse_workspace/ussr/home/ups/fapl_gen.c"

void dcd_activate(USSRONLYC(USSREnv *env) int role) {
  //if(role==0) arm_action(USSRONLY(env));
  //if(role==0) car_action(USSRONLY(env));
  if(role==0) dcd_action(USSRONLY(env));
  //if(role==0) test_action_2(USSRONLY(env));
}

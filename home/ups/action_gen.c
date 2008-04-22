/* Role selection code for RightWheel */
#define SIZE_RDCD_program_find_RightWheel 32
unsigned char RDCD_program_find_RightWheel[SIZE_RDCD_program_find_RightWheel+1]= {
/*[0]*/ /*: */INS_CENTER_POSITION_EW,
/*[1]*/ INS_IF_FALSE_GOTO, 30 /*cond_0_else*/,
/*[3]*/ INS_CONNECTED_SIZEOF,
/*[4]*/ /*: */INS_EQUALS_1,
/*[5]*/ INS_IF_FALSE_GOTO, 27 /*cond_1_else*/,
/*[7]*/ /*: */INS_CONNECTED_DIR_SIZEOF, ARG_UP,
/*[9]*/ /*: */INS_EQUALS_1,
/*[10]*/ INS_IF_FALSE_GOTO, 24 /*cond_2_else*/,
/*[12]*/ /*: */INS_CONNECTED_DIR_SIZEOF, ARG_EAST,
/*[14]*/ /*: */INS_EQUALS_1,
/*[15]*/ INS_IF_FALSE_GOTO, 21 /*cond_3_else*/,
/*[17]*/ INS_SET_ROLE_NOTIFY, ROLE_RightWheel,
/*[19]*/ INS_GOTO, 22 /*cond_3_end*/,
/*[21]*/ /* cond_3_else: */INS_MIGRATE_CONTINUE,
/*[22]*/ /* cond_3_end: */INS_GOTO, 25 /*cond_2_end*/,
/*[24]*/ /* cond_2_else: */INS_MIGRATE_CONTINUE,
/*[25]*/ /* cond_2_end: */INS_GOTO, 28 /*cond_1_end*/,
/*[27]*/ /* cond_1_else: */INS_MIGRATE_CONTINUE,
/*[28]*/ /* cond_1_end: */INS_GOTO, 31 /*cond_0_end*/,
/*[30]*/ /* cond_0_else: */INS_MIGRATE_CONTINUE,
/*[31]*/ /* cond_0_end: */INS_MIGRATE_CONTINUE,

0
};
/* Role selection code for LeftWheel */
#define SIZE_RDCD_program_find_LeftWheel 32
unsigned char RDCD_program_find_LeftWheel[SIZE_RDCD_program_find_LeftWheel+1]= {
/*[0]*/ /*: */INS_CENTER_POSITION_EW,
/*[1]*/ INS_IF_FALSE_GOTO, 30 /*cond_0_else*/,
/*[3]*/ INS_CONNECTED_SIZEOF,
/*[4]*/ /*: */INS_EQUALS_1,
/*[5]*/ INS_IF_FALSE_GOTO, 27 /*cond_1_else*/,
/*[7]*/ /*: */INS_CONNECTED_DIR_SIZEOF, ARG_UP,
/*[9]*/ /*: */INS_EQUALS_1,
/*[10]*/ INS_IF_FALSE_GOTO, 24 /*cond_2_else*/,
/*[12]*/ /*: */INS_CONNECTED_DIR_SIZEOF, ARG_WEST,
/*[14]*/ /*: */INS_EQUALS_1,
/*[15]*/ INS_IF_FALSE_GOTO, 21 /*cond_3_else*/,
/*[17]*/ INS_SET_ROLE_NOTIFY, ROLE_LeftWheel,
/*[19]*/ INS_GOTO, 22 /*cond_3_end*/,
/*[21]*/ /* cond_3_else: */INS_MIGRATE_CONTINUE,
/*[22]*/ /* cond_3_end: */INS_GOTO, 25 /*cond_2_end*/,
/*[24]*/ /* cond_2_else: */INS_MIGRATE_CONTINUE,
/*[25]*/ /* cond_2_end: */INS_GOTO, 28 /*cond_1_end*/,
/*[27]*/ /* cond_1_else: */INS_MIGRATE_CONTINUE,
/*[28]*/ /* cond_1_end: */INS_GOTO, 31 /*cond_0_end*/,
/*[30]*/ /* cond_0_else: */INS_MIGRATE_CONTINUE,
/*[31]*/ /* cond_0_end: */INS_MIGRATE_CONTINUE,

0
};
/* Role selection code for Axle */
#define SIZE_RDCD_program_find_Axle 13
unsigned char RDCD_program_find_Axle[SIZE_RDCD_program_find_Axle+1]= {
/*[0]*/ /*: */INS_CONNECTED_DOWN_ROLE, ROLE_Wheel,
/*[2]*/ INS_SIZEOF,
/*[3]*/ /*: */MK_INS_CONSTANT(0),
/*[4]*/ INS_GREATER,
/*[5]*/ INS_IF_FALSE_GOTO, 11 /*cond_0_else*/,
/*[7]*/ INS_SET_ROLE_NOTIFY, ROLE_Axle,
/*[9]*/ INS_GOTO, 12 /*cond_0_end*/,
/*[11]*/ /* cond_0_else: */INS_MIGRATE_CONTINUE,
/*[12]*/ /* cond_0_end: */INS_MIGRATE_CONTINUE,

0
};
/* Startup method for RightWheel */
#define SIZE_RDCD_program_startup_RightWheel 28
unsigned char RDCD_program_startup_RightWheel[SIZE_RDCD_program_startup_RightWheel+1]= {
/*[0]*/ INS_HAS_ROLE, ROLE_RightWheel,
/*[2]*/ INS_IF_FALSE_GOTO, 19 /*cond_0_else*/,
/*[4]*/ /*: */MK_INS_CONSTANT(0),
/*[5]*/ INS_COORD_Y_GREATER,
/*[6]*/ INS_IF_FALSE_GOTO, 14 /*cond_1_else*/,
/*[8]*/ INS_HANDLE_EVENT, EVENT_PROXIMITY_5, 20 /*_block_0*/, 7 /*27-20*/,
/*[12]*/ INS_GOTO, 14 /*cond_1_end*/,
/*[14]*/ /* cond_1_else cond_1_end: */INS_EVAL_COMMAND, CMD_ROTATE_CLOCKWISE, 1,
/*[17]*/ INS_GOTO, 19 /*cond_0_end*/,
/*[19]*/ /* cond_0_end cond_0_else: */INS_MIGRATE_CONTINUE,
/*[20(0)]*/ /* _block_0: */INS_DISABLE_EVENT, EVENT_PROXIMITY_5,
/*[22(2)]*/ INS_SEND_COMMAND, ROLE_Wheel, 128, 0,
/*[26(6)]*/ INS_END_TERMINATE,
/*[27(7)]*/ /* _block_0_END: */INS_NOP,

0
};
/* Startup method for LeftWheel */
#define SIZE_RDCD_program_startup_LeftWheel 28
unsigned char RDCD_program_startup_LeftWheel[SIZE_RDCD_program_startup_LeftWheel+1]= {
/*[0]*/ INS_HAS_ROLE, ROLE_LeftWheel,
/*[2]*/ INS_IF_FALSE_GOTO, 19 /*cond_0_else*/,
/*[4]*/ /*: */MK_INS_CONSTANT(0),
/*[5]*/ INS_COORD_Y_GREATER,
/*[6]*/ INS_IF_FALSE_GOTO, 14 /*cond_1_else*/,
/*[8]*/ INS_HANDLE_EVENT, EVENT_PROXIMITY_3, 20 /*_block_0*/, 7 /*27-20*/,
/*[12]*/ INS_GOTO, 14 /*cond_1_end*/,
/*[14]*/ /* cond_1_else cond_1_end: */INS_EVAL_COMMAND, CMD_ROTATE_COUNTERCLOCKWISE, 1,
/*[17]*/ INS_GOTO, 19 /*cond_0_end*/,
/*[19]*/ /* cond_0_end cond_0_else: */INS_MIGRATE_CONTINUE,
/*[20(0)]*/ /* _block_0: */INS_DISABLE_EVENT, EVENT_PROXIMITY_3,
/*[22(2)]*/ INS_SEND_COMMAND, ROLE_Wheel, 128, 0,
/*[26(6)]*/ INS_END_TERMINATE,
/*[27(7)]*/ /* _block_0_END: */INS_NOP,

0
};
/* Behavior method for Axle */
#define SIZE_RDCD_program_behavior_Axle 41
unsigned char RDCD_program_behavior_Axle[SIZE_RDCD_program_behavior_Axle+1]= {
/*[0]*/ INS_HAS_ROLE, ROLE_Axle,
/*[2]*/ INS_IF_FALSE_GOTO, 14 /*cond_0_else*/,
/*[4]*/ INS_INSTALL_COMMAND, ROLE_Axle, 128, 15 /*_block_0*/, 25 /*40-15*/,
/*[9]*/ INS_EVAL_COMMAND, 128, 0,
/*[12]*/ INS_GOTO, 14 /*cond_0_end*/,
/*[14]*/ /* cond_0_end cond_0_else: */INS_MIGRATE_CONTINUE,
/*[15(0)]*/ /* _block_0: */INS_CONNECTED_DOWN_ROLE, ROLE_Reverse,
/*[17(2)]*/ INS_SIZEOF,
/*[18(3)]*/ /*: */MK_INS_CONSTANT(0),
/*[19(4)]*/ INS_GREATER,
/*[20(5)]*/ INS_IF_FALSE_GOTO, 21 /*cond_1_else*/,
/*[22(7)]*/ /*: */MK_INS_CONSTANT(0),
/*[23(8)]*/ INS_COORD_Y_GREATER,
/*[24(9)]*/ INS_IF_FALSE_GOTO, 16 /*cond_2_else*/,
/*[26(11)]*/ INS_EVAL_COMMAND, CMD_ROTATE_TO, 20,
/*[29(14)]*/ INS_GOTO, 19 /*cond_2_end*/,
/*[31(16)]*/ /* cond_2_else: */INS_EVAL_COMMAND, CMD_ROTATE_TO, 16,
/*[34(19)]*/ /* cond_2_end: */INS_GOTO, 24 /*cond_1_end*/,
/*[36(21)]*/ /* cond_1_else: */INS_EVAL_COMMAND, CMD_ROTATE_TO, 18,
/*[39(24)]*/ /* cond_1_end: */INS_END_REPEAT,
/*[40(25)]*/ /* _block_0_END: */INS_NOP,

0
};
/* Command method for RightWheel */
#define SIZE_RDCD_program_command_RightWheel 28
unsigned char RDCD_program_command_RightWheel[SIZE_RDCD_program_command_RightWheel+1]= {
/*[0]*/ INS_HAS_ROLE, ROLE_RightWheel,
/*[2]*/ INS_IF_FALSE_GOTO, 11 /*cond_0_else*/,
/*[4]*/ INS_INSTALL_COMMAND, ROLE_RightWheel, 128, 12 /*_block_0*/, 15 /*27-12*/,
/*[9]*/ INS_GOTO, 11 /*cond_0_end*/,
/*[11]*/ /* cond_0_end cond_0_else: */INS_MIGRATE_CONTINUE,
/*[12(0)]*/ /* _block_0: */INS_SET_ROLE_NOTIFY, ROLE_Reverse,
/*[14(2)]*/ INS_EVAL_COMMAND, CMD_ROTATE_COUNTERCLOCKWISE, 1,
/*[17(5)]*/ INS_SLEEP_ROTATIONS, 3,
/*[19(7)]*/ INS_EVAL_COMMAND, CMD_ROTATE_CLOCKWISE, 1,
/*[22(10)]*/ INS_ENABLE_EVENT, EVENT_PROXIMITY_5,
/*[24(12)]*/ INS_SET_ROLE_NOTIFY, ROLE_RightWheel,
/*[26(14)]*/ INS_END_TERMINATE,
/*[27(15)]*/ /* _block_0_END: */INS_NOP,

0
};
/* Command method for LeftWheel */
#define SIZE_RDCD_program_command_LeftWheel 28
unsigned char RDCD_program_command_LeftWheel[SIZE_RDCD_program_command_LeftWheel+1]= {
/*[0]*/ INS_HAS_ROLE, ROLE_LeftWheel,
/*[2]*/ INS_IF_FALSE_GOTO, 11 /*cond_0_else*/,
/*[4]*/ INS_INSTALL_COMMAND, ROLE_LeftWheel, 128, 12 /*_block_0*/, 15 /*27-12*/,
/*[9]*/ INS_GOTO, 11 /*cond_0_end*/,
/*[11]*/ /* cond_0_end cond_0_else: */INS_MIGRATE_CONTINUE,
/*[12(0)]*/ /* _block_0: */INS_SET_ROLE_NOTIFY, ROLE_Reverse,
/*[14(2)]*/ INS_EVAL_COMMAND, CMD_ROTATE_CLOCKWISE, 1,
/*[17(5)]*/ INS_SLEEP_ROTATIONS, 3,
/*[19(7)]*/ INS_EVAL_COMMAND, CMD_ROTATE_COUNTERCLOCKWISE, 1,
/*[22(10)]*/ INS_ENABLE_EVENT, EVENT_PROXIMITY_3,
/*[24(12)]*/ INS_SET_ROLE_NOTIFY, ROLE_LeftWheel,
/*[26(14)]*/ INS_END_TERMINATE,
/*[27(15)]*/ /* _block_0_END: */INS_NOP,

0
};
static void dcd_action(USSRONLY(USSREnv *env)) {
  char channel;
  InterpreterContext context = { 0,0,0,ARG_NORTH_SOUTH, 0, 0, 0 };
  printf("################################\n");
  printf("## CAR ACTION DCD CONTROLLER ###\n");
  printf("################################\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("*** Sending program: RDCD_program_find_RightWheel \n");
  for(channel=0; channel<8; channel++) {
    sendProgramMaybe(USSRONLYC(env) &context, channel, RDCD_program_find_RightWheel, SIZE_RDCD_program_find_RightWheel);
  }
  context.program_id++;
  printf("*** Waiting\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("*** Sending program: RDCD_program_find_LeftWheel \n");
  for(channel=0; channel<8; channel++) {
    sendProgramMaybe(USSRONLYC(env) &context, channel, RDCD_program_find_LeftWheel, SIZE_RDCD_program_find_LeftWheel);
  }
  context.program_id++;
  printf("*** Waiting\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("*** Sending program: RDCD_program_find_Axle \n");
  for(channel=0; channel<8; channel++) {
    sendProgramMaybe(USSRONLYC(env) &context, channel, RDCD_program_find_Axle, SIZE_RDCD_program_find_Axle);
  }
  context.program_id++;
  printf("*** Waiting\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("*** Sending program: RDCD_program_command_RightWheel \n");
  for(channel=0; channel<8; channel++) {
    sendProgramMaybe(USSRONLYC(env) &context, channel, RDCD_program_command_RightWheel, SIZE_RDCD_program_command_RightWheel);
  }
  context.program_id++;
  printf("*** Waiting\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("*** Sending program: RDCD_program_command_LeftWheel \n");
  for(channel=0; channel<8; channel++) {
    sendProgramMaybe(USSRONLYC(env) &context, channel, RDCD_program_command_LeftWheel, SIZE_RDCD_program_command_LeftWheel);
  }
  context.program_id++;
  printf("*** Waiting\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("*** Sending program: RDCD_program_behavior_Axle \n");
  for(channel=0; channel<8; channel++) {
    sendProgramMaybe(USSRONLYC(env) &context, channel, RDCD_program_behavior_Axle, SIZE_RDCD_program_behavior_Axle);
  }
  context.program_id++;
  printf("*** Waiting\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("*** Sending program: RDCD_program_startup_RightWheel \n");
  for(channel=0; channel<8; channel++) {
    sendProgramMaybe(USSRONLYC(env) &context, channel, RDCD_program_startup_RightWheel, SIZE_RDCD_program_startup_RightWheel);
  }
  context.program_id++;
  printf("*** Waiting\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("*** Sending program: RDCD_program_startup_LeftWheel \n");
  for(channel=0; channel<8; channel++) {
    sendProgramMaybe(USSRONLYC(env) &context, channel, RDCD_program_startup_LeftWheel, SIZE_RDCD_program_startup_LeftWheel);
  }
  context.program_id++;
  printf("*** Waiting\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("Done!\n");
}

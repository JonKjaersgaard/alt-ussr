/* Invariants for role LeftWheel */
#define SIZE_FAPL_stop_all_1 32
unsigned char FAPL_stop_all_1[SIZE_FAPL_stop_all_1+1]= {
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
/*[31]*/ /* cond_0_end: */INS_END_TERMINATE,
0
};
/* Invariants for role RightWheel */
#define SIZE_FAPL_stop_all_2 32
unsigned char FAPL_stop_all_2[SIZE_FAPL_stop_all_2+1]= {
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
/*[31]*/ /* cond_0_end: */INS_END_TERMINATE,
0
};
/* Invariants for role FrontAxle */
#define SIZE_FAPL_stop_all_4 21
unsigned char FAPL_stop_all_4[SIZE_FAPL_stop_all_4+1]= {
/*[0]*/ /*: */INS_CONNECTED_DOWN_ROLE, ROLE_Wheel,
/*[2]*/ INS_SIZEOF,
/*[3]*/ /*: */MK_INS_CONSTANT(0),
/*[4]*/ INS_GREATER,
/*[5]*/ INS_IF_FALSE_GOTO, 19 /*cond_0_else*/,
/*[7]*/ /*: */INS_CONNECTED_DIR_SIZEOF, ARG_NORTH,
/*[9]*/ /*: */INS_EQUALS_0,
/*[10]*/ INS_IF_FALSE_GOTO, 16 /*cond_1_else*/,
/*[12]*/ INS_SET_ROLE_NOTIFY, ROLE_FrontAxle,
/*[14]*/ INS_GOTO, 17 /*cond_1_end*/,
/*[16]*/ /* cond_1_else: */INS_MIGRATE_CONTINUE,
/*[17]*/ /* cond_1_end: */INS_GOTO, 20 /*cond_0_end*/,
/*[19]*/ /* cond_0_else: */INS_MIGRATE_CONTINUE,
/*[20]*/ /* cond_0_end: */INS_END_TERMINATE,
0
};
/* Invariants for role RearAxle */
#define SIZE_FAPL_stop_all_5 21
unsigned char FAPL_stop_all_5[SIZE_FAPL_stop_all_5+1]= {
/*[0]*/ /*: */INS_CONNECTED_DOWN_ROLE, ROLE_Wheel,
/*[2]*/ INS_SIZEOF,
/*[3]*/ /*: */MK_INS_CONSTANT(0),
/*[4]*/ INS_GREATER,
/*[5]*/ INS_IF_FALSE_GOTO, 19 /*cond_0_else*/,
/*[7]*/ /*: */INS_CONNECTED_DIR_SIZEOF, ARG_SOUTH,
/*[9]*/ /*: */INS_EQUALS_0,
/*[10]*/ INS_IF_FALSE_GOTO, 16 /*cond_1_else*/,
/*[12]*/ INS_SET_ROLE_NOTIFY, ROLE_RearAxle,
/*[14]*/ INS_GOTO, 17 /*cond_1_end*/,
/*[16]*/ /* cond_1_else: */INS_MIGRATE_CONTINUE,
/*[17]*/ /* cond_1_end: */INS_GOTO, 20 /*cond_0_end*/,
/*[19]*/ /* cond_0_else: */INS_MIGRATE_CONTINUE,
/*[20]*/ /* cond_0_end: */INS_END_TERMINATE,
0
};
/* Function definition for stop(ANY) */
#define SIZE_FAPL_stop_all_6 18
unsigned char FAPL_stop_all_6[SIZE_FAPL_stop_all_6+1]= {
/*[0]*/ INS_HAS_ROLE, ROLE_ANY,
/*[2]*/ INS_IF_FALSE_GOTO, 11 /*cond_0_else*/,
/*[4]*/ INS_INSTALL_COMMAND, ROLE_ANY, 128, 12 /*_block_0*/, 4 /*16-12*/,
/*[9]*/ INS_GOTO, 11 /*cond_0_end*/,
/*[11]*/ /* cond_0_end cond_0_else: */INS_MIGRATE_CONTINUE,
/*[12(0)]*/ /* _block_0: */MK_INS_CONSTANT(PRIM_CENTERSTOP),
/*[13(1)]*/ /*: */MK_INS_CONSTANT(PRIM_APPLY),
/*[14(2)]*/ INS_APPLY,
/*[15(3)]*/ INS_END_TERMINATE,
/*[16(4)]*/ /* _block_0_END: */INS_NOP,
/*[17(5)]*/ INS_END_TERMINATE,
0
};
/* Function definition for turn(FrontAxle) */
#define SIZE_FAPL_stop_all_7 19
unsigned char FAPL_stop_all_7[SIZE_FAPL_stop_all_7+1]= {
/*[0]*/ INS_HAS_ROLE, ROLE_FrontAxle,
/*[2]*/ INS_IF_FALSE_GOTO, 11 /*cond_0_else*/,
/*[4]*/ INS_INSTALL_COMMAND, ROLE_FrontAxle, 129, 12 /*_block_0*/, 5 /*17-12*/,
/*[9]*/ INS_GOTO, 11 /*cond_0_end*/,
/*[11]*/ /* cond_0_end cond_0_else: */INS_MIGRATE_CONTINUE,
/*[12(0)]*/ /* _block_0: */INS_PUSH_ARGUMENT, 0,
/*[14(2)]*/ /*: */MK_INS_CONSTANT(PRIM_TURNTO),
/*[15(3)]*/ INS_APPLY,
/*[16(4)]*/ INS_END_TERMINATE,
/*[17(5)]*/ /* _block_0_END: */INS_NOP,
/*[18(6)]*/ INS_END_TERMINATE,
0
};
/* Function definition for turn(RearAxle) */
#define SIZE_FAPL_stop_all_8 20
unsigned char FAPL_stop_all_8[SIZE_FAPL_stop_all_8+1]= {
/*[0]*/ INS_HAS_ROLE, ROLE_RearAxle,
/*[2]*/ INS_IF_FALSE_GOTO, 11 /*cond_0_else*/,
/*[4]*/ INS_INSTALL_COMMAND, ROLE_RearAxle, 129, 12 /*_block_0*/, 6 /*18-12*/,
/*[9]*/ INS_GOTO, 11 /*cond_0_end*/,
/*[11]*/ /* cond_0_end cond_0_else: */INS_MIGRATE_CONTINUE,
/*[12(0)]*/ /* _block_0: */INS_PUSH_ARGUMENT, 0,
/*[14(2)]*/ INS_NEGATE,
/*[15(3)]*/ /*: */MK_INS_CONSTANT(PRIM_TURNTO),
/*[16(4)]*/ INS_APPLY,
/*[17(5)]*/ INS_END_TERMINATE,
/*[18(6)]*/ /* _block_0_END: */INS_NOP,
/*[19(7)]*/ INS_END_TERMINATE,
0
};
/* Function definition for _eval_0(ANY) */
#define SIZE_FAPL_stop_all_9 19
unsigned char FAPL_stop_all_9[SIZE_FAPL_stop_all_9+1]= {
/*[0]*/ INS_HAS_ROLE, ROLE_ANY,
/*[2]*/ INS_IF_FALSE_GOTO, 11 /*cond_0_else*/,
/*[4]*/ INS_INSTALL_COMMAND, ROLE_ANY, 130, 12 /*_block_0*/, 5 /*17-12*/,
/*[9]*/ INS_GOTO, 11 /*cond_0_end*/,
/*[11]*/ /* cond_0_end cond_0_else: */INS_MIGRATE_CONTINUE,
/*[12(0)]*/ /* _block_0: */MK_INS_CONSTANT(45),
/*[13(1)]*/ INS_PUSHC, 129,
/*[15(3)]*/ INS_APPLY,
/*[16(4)]*/ INS_END_TERMINATE,
/*[17(5)]*/ /* _block_0_END: */INS_NOP,
/*[18(6)]*/ INS_END_TERMINATE,
0
};
/* Anonymous evaluation block */
#define SIZE_FAPL_stop_all_10 5
unsigned char FAPL_stop_all_10[SIZE_FAPL_stop_all_10+1]= {
/*[0]*/ INS_PUSHC, 130,
/*[2]*/ /*: */MK_INS_CONSTANT(PRIM_APPLY),
/*[3]*/ INS_APPLY,
/*[4]*/ INS_END_TERMINATE,
0
};
static void dcd_action(USSRONLY(USSREnv *env)) {
  char channel;
  InterpreterContext context = { 0,0,0,ARG_NORTH_SOUTH, 0, 0, 0 };
  printf("################################\n");
  printf("## DCD CONTROLLER: stop_all");
  printf("################################\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("*** Sending program: FAPL_stop_all_1 \n");
  installProgramMessage(USSRONLYC(env) &context, FAPL_stop_all_1, SIZE_FAPL_stop_all_1);
  context.program_id++;
  activate_step(USSRONLYC(env) 100);
  printf("*** Waiting\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("*** Sending program: FAPL_stop_all_2 \n");
  installProgramMessage(USSRONLYC(env) &context, FAPL_stop_all_2, SIZE_FAPL_stop_all_2);
  context.program_id++;
  activate_step(USSRONLYC(env) 100);
  printf("*** Waiting\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("*** Sending program: FAPL_stop_all_4 \n");
  installProgramMessage(USSRONLYC(env) &context, FAPL_stop_all_4, SIZE_FAPL_stop_all_4);
  context.program_id++;
  activate_step(USSRONLYC(env) 100);
  printf("*** Waiting\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("*** Sending program: FAPL_stop_all_5 \n");
  installProgramMessage(USSRONLYC(env) &context, FAPL_stop_all_5, SIZE_FAPL_stop_all_5);
  context.program_id++;
  activate_step(USSRONLYC(env) 100);
  printf("*** Waiting\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("*** Sending program: FAPL_stop_all_6 \n");
  installProgramMessage(USSRONLYC(env) &context, FAPL_stop_all_6, SIZE_FAPL_stop_all_6);
  context.program_id++;
  activate_step(USSRONLYC(env) 100);
  printf("*** Waiting\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("*** Sending program: FAPL_stop_all_7 \n");
  installProgramMessage(USSRONLYC(env) &context, FAPL_stop_all_7, SIZE_FAPL_stop_all_7);
  context.program_id++;
  activate_step(USSRONLYC(env) 100);
  printf("*** Waiting\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("*** Sending program: FAPL_stop_all_8 \n");
  installProgramMessage(USSRONLYC(env) &context, FAPL_stop_all_8, SIZE_FAPL_stop_all_8);
  context.program_id++;
  activate_step(USSRONLYC(env) 100);
  printf("*** Waiting\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("*** Sending program: FAPL_stop_all_9 \n");
  installProgramMessage(USSRONLYC(env) &context, FAPL_stop_all_9, SIZE_FAPL_stop_all_9);
  context.program_id++;
  activate_step(USSRONLYC(env) 100);
  printf("*** Waiting\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("*** Sending program: FAPL_stop_all_10 \n");
  installProgramMessage(USSRONLYC(env) &context, FAPL_stop_all_10, SIZE_FAPL_stop_all_10);
  context.program_id++;
  activate_step(USSRONLYC(env) 100);
  printf("*** Waiting\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("Done!\n");
}

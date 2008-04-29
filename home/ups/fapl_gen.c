#define SIZE_FAPL_stop_all_0 4
unsigned char FAPL_stop_all_0[SIZE_FAPL_stop_all_0+1]= {
/*[0]*/ /*: */MK_INS_CONSTANT(PRIM_CENTERSTOP),
/*[1]*/ /*: */MK_INS_CONSTANT(PRIM_APPLY),
/*[2]*/ INS_APPLY,
/*[3]*/ INS_END_TERMINATE,
0
};
static void dcd_action(USSRONLY(USSREnv *env)) {
  char channel;
  InterpreterContext context = { 0,0,0,ARG_NORTH_SOUTH, 0, 0, 0 };
  printf("################################\n");
  printf("## DCD CONTROLLER: stop_all");
  printf("################################\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("*** Sending program: FAPL_stop_all_0 \n");
  installProgramMessage(USSRONLYC(env) &context, FAPL_stop_all_0, SIZE_FAPL_stop_all_0);
  context.program_id++;
  printf("*** Waiting\n");
  delay(USSRONLYC(env) WAITTIME);
  printf("Done!\n");
}

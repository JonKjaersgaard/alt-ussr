#include "dcdError.h"
#include "dcdBytecode.h"
#include <atron.h>
#include <stdio.h>

#define ENOUGH_TIME (24*3600)

void report_error(USSRONLYC(USSREnv *env) char error_number, unsigned char argument) {
#ifdef USSR
  fprintf(stderr, "<%d>(%d) Program error: %d.%d\n", env->context, getRole(env), error_number, argument);
  sleep(ENOUGH_TIME);
#else 
  while(1) {
    setNorthIOPort(error_number);
    delay(USSRONLYC(env) 250);
    setNorthIOPort(0);
    delay(USSRONLYC(env) 100);
    setNorthIOPort(error_number);
    delay(USSRONLYC(env) 250);
    setNorthIOPort(0);
    delay(USSRONLYC(env) 100);
    setNorthIOPort(argument);
    delay(USSRONLYC(env) 250);
    setNorthIOPort(0);
    delay(USSRONLYC(env) 100);
  }
#endif /* USSR */
}

#ifdef USSR
void print_arg(unsigned char *program, unsigned char pc, int nl) {
  switch(program[pc]) {
  case ARG_UP:
    printf("ARG_UP"); break;
  case ARG_DOWN:
    printf("ARG_DOWN"); break;
  case ARG_NORTH:
    printf("ARG_NORTH"); break;
  case ARG_SOUTH:
    printf("ARG_SOUTH"); break;
  case ARG_EAST:
    printf("ARG_EAST"); break;
  case ARG_WEST:
    printf("ARG_WEST"); break;
  default:
    printf("[unknown argument: %d]", program[pc]); break;
  }
  if(nl)
    printf("\n");
  else
    printf(" ");
}

void print_program(unsigned char *program, unsigned char program_size) {
  unsigned char pc = 0;
  while(pc<program_size) {
    printf("  %2d: ", pc);
    switch(program[pc++]) {
    case INS_CENTER_POSITION_EW:
      printf("INS_CENTER_POSITION_EW\n"); break;
    case INS_CENTER_POSITION_NS:
      printf("INS_CENTER_POSITION_NS\n"); break;
    case INS_CENTER_POSITION_UD:
      printf("INS_CENTER_POSITION_UD\n"); break;
    case INS_CONNECTED_SIZEOF:
      printf("INS_CONNECTED_SIZEOF\n"); break;
    case INS_EQUALS_1:
      printf("INS_EQUALS_1\n"); break;
    case INS_CONNECTED_DIR_SIZEOF:
      printf("INS_CONNECTED_DIR_SIZEOF ");
      print_arg(program,pc,1); pc++;
      break;
    case INS_END_TERMINATE:
      printf("INS_END_TERMINATE\n"); break;
    case INS_IF_FALSE_GOTO:
      printf("INS_IF_FALSE_GOTO %d\n", program[pc++]); break;
    case INS_GOTO:
      printf("INS_GOTO %d\n", program[pc++]); break;
    case INS_SET_ROLE_NOTIFY:
      printf("INS_SET_ROLE_NOTIFY %d\n", program[pc++]); break;
    case INS_MIGRATE_CONTINUE:
      printf("INS_MIGRATE_CONTINUE\n"); break;
    case INS_EVAL_COMMAND:
      printf("INS_EVAL_COMMAND %d %d\n", program[pc], program[pc+1]); pc+=2; break;
    case INS_NOP:
      printf("INS_NOP\n"); break;
    default:
      printf("Unknown instruction: %d\n", program[pc-1]);
      return;
    }
  }
  fflush(stdout);
}
#endif


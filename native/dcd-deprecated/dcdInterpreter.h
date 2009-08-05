#ifndef __DCD_INTERPRETER_H_
#define __DCD_INTERPRETER_H_
#include "config.h"

#define MAX_STACK_SIZE 5
#define MAX_INSTRUCTION_COUNT 100

extern void execute_command(USSRONLYC(USSREnv *env) uint8_t command, uint8_t argument);
extern uint8_t interpret(USSRONLYC(USSREnv *env) InterpreterContext *context, uint8_t *program, uint8_t size, uint8_t argument);

#endif

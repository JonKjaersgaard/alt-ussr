#ifndef __DCD_INTERPRETER_H_
#define __DCD_INTERPRETER_H_

#define MAX_STACK_SIZE 5
#define MAX_INSTRUCTION_COUNT 100

extern void execute_command(USSRONLYC(USSREnv *env) unsigned char command, unsigned char argument);
extern unsigned char interpret(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char *program, unsigned char size, unsigned char argument);

#endif

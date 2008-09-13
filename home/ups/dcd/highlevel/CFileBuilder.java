package dcd.highlevel;

import java.io.PrintWriter;

public class CFileBuilder implements OutputBuilder {
    private PrintWriter writer;
    
    public CFileBuilder(PrintWriter writer) {
        this.writer = writer;
        writer.println("#ifdef RDCD_INCLUDE_HEADER\n#include \"rdcd_header.h\"\n#endif");
    }
    
    public void addComment(String string) {
        writer.println("/* "+string+" */"); writer.flush();
    }

    public void finishFragment() {
        writer.println("0\n};");
    }

    public void startFragment(String programName, int size, String role_name) {
        writer.println("#define SIZE_"+programName+" "+size);
        writer.println("#define ROLE_"+programName+" ROLE_"+role_name);
        writer.println("unsigned char "+programName+"[SIZE_"+programName+"+1]= {");
    }

    public void scheduleFragmentSend(String fragmentName, boolean receiveLocally) {
        writer.println("  USSRONLY(printf(\"*** Sending program: "+fragmentName+" \\n\"));");
        if(receiveLocally) {
            writer.println("  installProgramMessage(USSRONLYC(env) &context, ROLE_"+fragmentName+", "+fragmentName+", SIZE_"+fragmentName+");");
        } else {
            writer.println("  for(channel=0; channel<8; channel++) {");
            writer.println("    sendProgramMaybe(USSRONLYC(env) &context, channel, ROLE_"+fragmentName+", "+fragmentName+", SIZE_"+fragmentName+");");
            writer.println("  }");
        }
        writer.println("  context.program_id++;");
        writer.println("  activate_step(USSRONLYC(env) 100);");
        writer.println("  USSRONLY(printf(\"*** Waiting\\n\"));");
        writer.println("  delay(USSRONLYC(env) WAITTIME);");
    }

    public void startFragmentScheduling(String name) {
        writer.println("static void dcd_action(USSRONLY(USSREnv *env)) {");
        writer.println("  char channel;");
        writer.println("  InterpreterContext context = { 0,0,0,ARG_NORTH_SOUTH, 0, 0, 0 };");
        writer.println("#ifdef USSR");
        writer.println("  printf(\"################################\\n\");");
        writer.println("  printf(\"## DCD CONTROLLER: "+name+"\");");
        writer.println("  printf(\"################################\\n\");");
        writer.println("#endif");
        writer.println("  delay(USSRONLYC(env) WAITTIME);");
    }

    public void finish() {
        writer.println("  USSRONLY(printf(\"Done!\\n\"));");
        writer.println("}");
        writer.flush();
    }

    public void addByteCode(ByteCode bc) {
        writer.println(bc);
    }

}

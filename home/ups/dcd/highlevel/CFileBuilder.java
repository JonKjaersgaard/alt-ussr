package dcd.highlevel;

import java.io.PrintWriter;

public class CFileBuilder implements OutputBuilder {
    private PrintWriter writer;
    
    public CFileBuilder(PrintWriter writer) {
        this.writer = writer;
    }
    
    public void addComment(String string) {
        writer.println("/* "+string+" */"); writer.flush();
    }

    public void finishFragment() {
        writer.println("0\n};");
    }

    public void startFragment(String programName, int size) {
        writer.println("#define SIZE_"+programName+" "+size);
        writer.println("unsigned char "+programName+"[SIZE_"+programName+"+1]= {");
    }

    public void scheduleFragmentSend(String fragmentName) {
        writer.println("  printf(\"*** Sending program: "+fragmentName+" \\n\");");
        writer.println("  for(channel=0; channel<8; channel++) {");
        writer.println("    sendProgramMaybe(USSRONLYC(env) &context, channel, "+fragmentName+", SIZE_"+fragmentName+");");
        writer.println("  }");
        writer.println("  context.program_id++;");
        writer.println("  printf(\"*** Waiting\\n\");");
        writer.println("  delay(USSRONLYC(env) WAITTIME);");
    }

    public void startFragmentScheduling(String name) {
        writer.println("static void dcd_action(USSRONLY(USSREnv *env)) {");
        writer.println("  char channel;");
        writer.println("  InterpreterContext context = { 0,0,0,ARG_NORTH_SOUTH, 0, 0, 0 };");
        writer.println("  printf(\"################################\\n\");");
        writer.println("  printf(\"## DCD CONTROLLER: "+name+"\");");
        writer.println("  printf(\"################################\\n\");");
        writer.println("  delay(USSRONLYC(env) WAITTIME);");
    }

    public void finish() {
        writer.println("  printf(\"Done!\\n\");");
        writer.println("}");
        writer.flush();
    }

    public void addByteCode(ByteCode bc) {
        writer.println(bc);
    }

}

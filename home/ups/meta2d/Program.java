package meta2d;

import meta2d.statements.Terminate;

public class Program {
    private Statement[] statements;
    public Program(Statement[] statements) {
        this.statements = statements;
    }
    public Statement[] getStatements() {
        return statements;
    }
    public Program reverse() {
        Statement[] in_program_stm = this.getStatements();
        Statement[] out_program_stm = new Statement[in_program_stm.length];
        int read_index = in_program_stm.length-1;
        int write_index = 0;
        // Is there a termination instruction at the end?
        if(in_program_stm[read_index].getClass()==Terminate.class) {
            out_program_stm[read_index] = new Terminate();
            read_index--;
        }
        while(read_index>=0) {
            out_program_stm[write_index++] = in_program_stm[read_index--].reverseStatement();
        }
        return new Program(out_program_stm);
    }
}

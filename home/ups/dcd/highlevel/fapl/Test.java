package dcd.highlevel.fapl;

public class Test {

    public static void main(String argv[]) {
        Program program = new Program(
                new Unit[] {
                   new Function("stop", new Parameter[0], new Apply(new PrimName("apply*"),new PrimName("@centerStop"))),
                   new Evaluate(new Apply(new FunName("stop"),new UnitValue()))
                });
        // Step 1: resolve values (constants) etc
        // Step 2, for functions, compute set of roles, with associated behaviors [skip now]
    }
    
}

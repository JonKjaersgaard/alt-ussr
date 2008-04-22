package dcd.highlevel.fapl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Program {
    private List<Unit> declarations;
    public Program(Unit[] declarations) {
        this.declarations = new ArrayList<Unit>(Arrays.asList(declarations)); 
    }
}

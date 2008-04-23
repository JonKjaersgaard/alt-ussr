package dcd.highlevel.fapl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dcd.highlevel.ast.Exp;

public class Function extends Unit {
    private String name;
    private List<Parameter> parameters;
    private Exp body;
    public Function(String name, Parameter[] parameters, Exp body) {
        this.name = name;
        this.parameters = new ArrayList<Parameter>(Arrays.asList(parameters));
        this.body = body;
    }
    public Exp getBody() {
        return body;
    }
}

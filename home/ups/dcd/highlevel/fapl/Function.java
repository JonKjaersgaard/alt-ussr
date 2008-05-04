package dcd.highlevel.fapl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dcd.highlevel.GlobalSource;
import dcd.highlevel.IName;
import dcd.highlevel.InvariantSource;
import dcd.highlevel.ast.Name;
import dcd.highlevel.MethodSpec;
import dcd.highlevel.ast.Block;
import dcd.highlevel.ast.ConstantDef;
import dcd.highlevel.ast.Exp;
import dcd.highlevel.ast.Modifier;
import dcd.highlevel.ast.Statement;
import dcd.highlevel.ast.program.SingleExp;

public class Function extends Unit implements MethodSpec {
    private String name, parameter, role;
    private Exp body;
    public Function(String name, String parameter, String role, Exp body) {
        this.name = name;
        this.parameter = parameter;
        this.role = role;
        this.body = body;
    }
    public IName getName() { return new Name(name); }
    public String getParameter() { return parameter; }
    public String getRole() { return role; }
    public Block getBody() {
        return new Block(new Statement[] { new SingleExp(body) });
    }
}

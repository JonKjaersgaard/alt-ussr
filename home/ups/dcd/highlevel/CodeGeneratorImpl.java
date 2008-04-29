package dcd.highlevel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import dcd.highlevel.ast.Block;
import dcd.highlevel.ast.Invariant;
import dcd.highlevel.ast.Method;
import dcd.highlevel.ast.Modifier;
import dcd.highlevel.ast.Statement;
import dcd.highlevel.ast.program.Conditional;
import dcd.highlevel.ast.program.Nop;
import dcd.highlevel.ast.program.Numeric;
import dcd.highlevel.ast.program.PrimOp;
import dcd.highlevel.ast.program.SelfFunction;

public abstract class CodeGeneratorImpl {
    protected Resolver resolver;
    
    public CodeGeneratorImpl(Resolver resolver) {
        this.resolver = resolver;
    }
    
    protected Block generateStartupBody(GlobalSource role, Method method) {
        return mobilize(role,method.getBody());
    }

    protected Block generateBehaviorBody(GlobalSource role, Method method) {
        return mobilize(role,installize(role,method,true));
    }
    
    protected Block generateCommandBody(GlobalSource role, Method method) {
        return mobilize(role,installize(role,method,false));
    }
    
    protected Block mobilize(GlobalSource role, Block body) {
        List<Statement> statements = new ArrayList<Statement>();
        statements.add(new Conditional(
                SelfFunction.HAS_ROLE(role.getName().getName()),
                body,
                new Nop()));
        statements.add(PrimOp.MIGRATE_CONTINUE);
        return new Block(statements);
    }
    
    protected Block mobilizeAnyRole(Block body) {
        List<Statement> statements = new ArrayList<Statement>(body.getStatements());
        statements.add(PrimOp.MIGRATE_CONTINUE);
        return new Block(statements);
    }
    
    protected Block installize(GlobalSource role, Method method, boolean isBehavior) {
        List<Statement> statements = new ArrayList<Statement>();
        int index = resolver.getMethodIndex(role.getName().getName(), method.getName().getName())+128;
        statements.add(PrimOp.INSTALL_COMMAND(role.getName(),index,method.getBody(),isBehavior));
        if(isBehavior) {
            statements.add(PrimOp.EVAL_COMMAND(index,new Numeric(0)));
        }
        return new Block(statements);
    }

    protected Block generateCheckInvariant(GlobalSource role) {
        Statement cascade = generateInvariantCascade(role,role.getInvariants().iterator());
        return annotize(role,new Block(new ArrayList<Statement>(Arrays.asList(new Statement[] { cascade, PrimOp.MIGRATE_CONTINUE }))));
    }
    
    protected Block annotize(GlobalSource role, Block body) {
        if(!role.hasModifier(Modifier.DEBUG)) return body;
        Block wrapper = new Block(new ArrayList<Statement>(Arrays.asList(new Statement[] { PrimOp.DEBUG, body })));
        return wrapper;
    }

    protected Statement generateInvariantCascade(GlobalSource role, Iterator<? extends InvariantSource> invariants) {
        if(invariants.hasNext()) {
            Invariant invariant = (Invariant)invariants.next();
            return new Conditional(
                    invariant.getCondition(),
                    generateInvariantCascade(role,invariants),
                    PrimOp.MIGRATE_CONTINUE
                    );
        } else {
            return PrimOp.SET_ROLE_NOTIFY(role.getName());
        }
    }
}

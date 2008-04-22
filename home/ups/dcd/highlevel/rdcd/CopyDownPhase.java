package dcd.highlevel.rdcd;

import java.util.HashSet;
import java.util.Set;

import dcd.highlevel.ast.ConstantDef;
import dcd.highlevel.ast.Member;
import dcd.highlevel.ast.Program;
import dcd.highlevel.ast.Role;

public class CopyDownPhase {
    private Program program;
    Set<Role> copied = new HashSet<Role>();
    
    public CopyDownPhase(Program program) {
        this.program = program;
    }
    
    public void copyDown() {
        for(Role role: program.getRoles())
            doCopyDown(role);
    }

    private void doCopyDown(Role role) {
        // Preliminaries
        if(copied.contains(role)) return;
        copied.add(role);
        if(role.getZuper().equals("Module")) return;
        Role zuper = program.getRole(role.getZuper());
        doCopyDown(zuper);
        // Now copy down non-abstract members
        for(Member member: zuper.getMembers())
            if(!member.isAbstract() && !role.hasMember(member)) role.addMember(member.duplicate());
    }
}

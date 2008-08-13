package dcd.highlevel.fapl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dcd.highlevel.InvariantSource;
import dcd.highlevel.ast.Invariant;

public class RoleInvariantDef extends Unit implements InvariantSource {

    private String roleName, requiredRole;
    private List<Invariant> invariants;
    boolean isPartial;

    public RoleInvariantDef(String roleName, String requiredRole, boolean partial, Invariant[] invariants) {
        this.roleName = roleName; this.requiredRole = requiredRole;
        this.isPartial = partial;
        this.invariants = new ArrayList<Invariant>(Arrays.asList(invariants));
    }

    public String getRoleName() {
        return roleName;
    }

    public String getRequiredRoleName() {
        return requiredRole;
    }

    public List<Invariant> getInvariants() {
        return invariants;
    }

    public boolean isPartial() {
        return isPartial;
    }
    
}

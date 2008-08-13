package dcd.highlevel.fapl;

import java.util.HashMap;
import java.util.Map;

public class PropagateInvariantsPhase {

    private Program program;
    Map<String,RoleInvariantDef> definitions = new HashMap<String,RoleInvariantDef>();
    
    public PropagateInvariantsPhase(Program program) {
        this.program = program;
    }

    public void propagate() {
        for(Unit unit: program.getUnits()) {
            if(unit instanceof RoleInvariantDef)
                handleRoleInvariantDef((RoleInvariantDef)unit);
        }
    }

    private void handleRoleInvariantDef(RoleInvariantDef unit) {
        if(definitions.get(unit.getRoleName())!=null) throw new Error("Overwriting of definitions not supported");
        definitions.put(unit.getRoleName(), unit);
        RoleInvariantDef zuper = definitions.get(unit.getRequiredRoleName());
        if(zuper!=null)
            unit.getInvariants().addAll(0, zuper.getInvariants());
    }

}

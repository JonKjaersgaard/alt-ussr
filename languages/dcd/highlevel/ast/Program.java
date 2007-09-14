package dcd.highlevel.ast;

import java.util.Arrays;
import java.util.List;

public class Program extends Node {
    private List<Role> roles;
    private List<Name> deployment;
    
    public Program(Role[] roles, Name[] deployment) {
        this.roles = Arrays.asList(roles);
        this.deployment = Arrays.asList(deployment);
    }
    
    public Role getRole(Name name) {
        for(Role role: roles) if(role.getName().equals(name)) return role;
        throw new Error("Role not found: "+name);
    }

    /**
     * @return the deployment
     */
    public List<Name> getDeployment() {
        return deployment;
    }

    /**
     * @param deployment the deployment to set
     */
    public void setDeployment(List<Name> deployment) {
        this.deployment = deployment;
    }

    /**
     * @return the roles
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}

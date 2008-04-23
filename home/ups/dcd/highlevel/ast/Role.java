package dcd.highlevel.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import dcd.highlevel.generic.GlobalSource;
import dcd.highlevel.generic.IName;
import dcd.highlevel.generic.InvariantSource;

public class Role extends Node implements GlobalSource {
    private Name name, zuper;
    private List<Modifier> modifiers;
    private List<ConstantDef> constants;
    private List<Invariant> invariants;
    private List<Method> methods;
    /**
     * @param name
     * @param zuper
     * @param isBehavior
     * @param isAbstract
     * @param constants
     * @param invariants
     * @param methods
     */
    public Role(Name name, Name zuper, Modifier[] modifiers, ConstantDef[] constants, Invariant[] invariants, Method[] methods) {
        this.name = name;
        this.zuper = zuper;
        setModifiers(new ArrayList<Modifier>(Arrays.asList(modifiers)));
        setConstants(new ArrayList<ConstantDef>(Arrays.asList(constants)));
        setInvariants(new ArrayList<Invariant>(Arrays.asList(invariants)));
        setMethods(new ArrayList<Method>(Arrays.asList(methods)));
    }
    /**
     * @return the constants
     */
    public List<ConstantDef> getConstants() {
        return Collections.unmodifiableList(constants);
    }
    /**
     * @param constants the constants to set
     */
    public void setConstants(List<ConstantDef> constants) {
        this.constants = constants;
    }
    
    /**
     * @return the invariants
     */
    public List<? extends InvariantSource> getInvariants() {
        return Collections.unmodifiableList(invariants);
    }
    /**
     * @param invariants the invariants to set
     */
    public void setInvariants(List<Invariant> invariants) {
        this.invariants = invariants;
    }
    /**
     * @return the isAbstract
     */
    public boolean isAbstract() {
        return modifiers.contains(Modifier.ABSTRACT) || modifiers.contains(Modifier.MIXIN);
    }
    /**
     * @param isAbstract the isAbstract to set
     */
    public void setModifiers(List<Modifier> modifiers) {
        this.modifiers = modifiers;
    }
    /**
     * @return the isBehavior
     */
    public boolean isBehavior() {
        return modifiers.contains(Modifier.BEHAVIOR);
    }

    /**
     * @return the methods
     */
    public List<Method> getMethods() {
        return Collections.unmodifiableList(methods); 
    }
    /**
     * @param methods the methods to set
     */
    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }
    /**
     * @return the name
     */
    public IName getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(Name name) {
        this.name = name;
    }
    /**
     * @return the zuper
     */
    public Name getZuper() {
        return zuper;
    }
    /**
     * @param zuper the zuper to set
     */
    public void setZuper(Name zuper) {
        this.zuper = zuper;
    }
    public List<Member> getMembers() {
        ArrayList<Member> members = new ArrayList<Member>();
        members.addAll(constants);
        members.addAll(invariants);
        members.addAll(methods);
        return members;
    }
    public boolean hasMember(Member member) {
        if(member instanceof ConstantDef) return constants.contains(member);
        if(member instanceof Invariant) return invariants.contains(member);
        if(member instanceof Method) return methods.contains(member);
        throw new Error("Type not supported: "+member);
    }
    public void addMember(Member member) {
        if(member instanceof ConstantDef) constants.add((ConstantDef)member);
        else if(member instanceof Invariant) invariants.add((Invariant)member);
        else if(member instanceof Method) methods.add((Method)member);
        else throw new Error("Type not supported: "+member);
    }

    public ConstantDef getConstant(IName name) {
        for(ConstantDef constant: this.getConstants())
            if(constant.getName().equals(name)) {
                if(constant.isAbstract()) throw new Error("Abstract constant reference "+this.getName()+"."+name);
                return constant;
            }
        throw new Error("Constant not found: "+name+" (role: "+this.getName()+")");
    }
    public boolean hasModifier(Modifier wanted) {
        for(Modifier modifier: modifiers)
            if(wanted==modifier) return true;
        return false;
    }

    
    
}

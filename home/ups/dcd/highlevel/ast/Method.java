package dcd.highlevel.ast;

import dcd.highlevel.BehaviorSource;
import dcd.highlevel.MethodSpec;

public class Method extends Member implements BehaviorSource, MethodSpec {
    private Modifier modifier;
    private Name name, parameter;
    private Block body;
    /**
     * @param modifier
     * @param name
     * @param parameter
     * @param body
     */
    public Method(Modifier modifier, Name name, Name parameter, Block body) {
        this.modifier = modifier;
        this.name = name;
        this.parameter = parameter;
        this.body = body;
    }
    /**
     * @return the body
     */
    public Block getBody() {
        return body;
    }
    /**
     * @param body the body to set
     */
    public void setBody(Block body) {
        this.body = body;
    }
    /**
     * @return the modifier
     */
    public Modifier getModifier() {
        return modifier;
    }
    /**
     * @param modifier the modifier to set
     */
    public void setModifier(Modifier modifier) {
        this.modifier = modifier;
    }
    /**
     * @return the name
     */
    public Name getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(Name name) {
        this.name = name;
    }
    /**
     * @return the parameter
     */
    public Name getParameter() {
        return parameter;
    }
    /**
     * @param parameter the parameter to set
     */
    public void setParameter(Name parameter) {
        this.parameter = parameter;
    }
    @Override
    public boolean isAbstract() {
        return modifier==Modifier.ABSTRACT;
    }
    @Override
    public Member duplicate() {
        return new Method(modifier,name,parameter,body.duplicate());
    }
    
}

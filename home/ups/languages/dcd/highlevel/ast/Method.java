package dcd.highlevel.ast;

public class Method extends Member {
    private Modifier modifier;
    private Name name;
    private Block body;
    /**
     * @param modifier
     * @param name
     * @param body
     */
    public Method(Modifier modifier, Name name, Block body) {
        this.modifier = modifier;
        this.name = name;
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
    @Override
    public boolean isAbstract() {
        return modifier==Modifier.ABSTRACT;
    }
    
}

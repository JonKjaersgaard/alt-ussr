package dcd.highlevel;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import dcd.highlevel.ByteCodeSequence.AddressSpec;
import dcd.highlevel.ast.program.Direction;

public class ByteCode {

    public static final String CENTER_POSITION = "CENTER_POSITION";
    public static final String PUSHC = "PUSHC";
    public static final String EQUALS = "EQUALS";
    public static final String EAST_WEST = "ARG_EAST_WEST";
    public static final String ANY = "*";
    public static final String SIZEOF = "SIZEOF";
    public static final String CONNECTED_ROLE = "CONNECTED_ROLE";
    public static final String INS_EQUALS = "EQUALS";
    public static final String INS_EQUALS_0 = "EQUALS_0";
    public static final String CONNECTED_DIR = "CONNECTED_DIR";
    public static final String INS_COORD_any = "INS_COORD_";
    public static final String INS_CONSTANT_any = "MK_INS_CONSTANT";
    public static final String INS_GREATER = "INS_GREATER";
    
    public static ByteCode INS_CENTER_POSITION(Direction direction) { return new ByteCode("INS_CENTER_POSITION",1,new String[] { direction.residualize() }); }
    public static ByteCode INS_IF_FALSE_GOTO(String label) { return new ByteCode("INS_IF_FALSE_GOTO",2,new String[] { label }, new int[] { 0 }); }
    public static ByteCode INS_GOTO(String label) { return new ByteCode("INS_GOTO",2,new String[] { label }, new int[] { 0 }); }
    
    private int address = -1, virtualAddress, target, size;
    private String memomic, arguments[];
    private Set<String> labels;
    private int targetArguments[];
    private int targetAddresses[];
    private boolean isBlockStart = false;

    public ByteCode(String memomic, int size, String[] arguments) {
        this.memomic = memomic;
        this.size = size;
        this.arguments = arguments;
    }

    public ByteCode(String memomic, int size, String[] arguments, int targetArguments[]) {
        this.memomic = memomic;
        this.size = size;
        this.arguments = arguments;
        this.targetArguments = targetArguments;
        this.targetAddresses = new int[targetArguments.length];
    }

    public boolean is(String memomic) {
        return this.memomic.startsWith(memomic) || (this.memomic.endsWith(memomic) && this.memomic.length()-4==memomic.length());
    }
    
    public String toString() {
        StringBuffer result = new StringBuffer();
        if(address>=0) { 
            result.append("/*["); result.append(address);
            if(address!=virtualAddress) {
                result.append('(');
                result.append(virtualAddress);
                result.append(')');
            }
            result.append("]*/ "); 
        }
        if(labels!=null) { 
            result.append("/*");
            for(String label: labels) {
                result.append(' ');
                result.append(label);
            }
            result.append(": */");
        }
        result.append(memomic);
        for(int i=0; i<arguments.length; i++) {
            result.append(", ");
            result.append(arguments[i]);
        }
        result.append(',');
        return result.toString();
    }
    
    /**
     * @return the address
     */
    public int getRealAddress() {
        return address;
    }
    
    public int getVirtualAddress() { return virtualAddress; }

    public void setAsBlockStart() { isBlockStart = true; }
    
    public boolean isBlockStart() { return isBlockStart; }
    
    public boolean isSubroutine() { return address!=virtualAddress; }
    
    /**
     * @param address the address to set
     */
    public void setAddress(int realAddress, int virtualAddress) {
        this.address = realAddress;
        this.virtualAddress = virtualAddress;
    }

    /**
     * @return the arguments
     */
    public String[] getArguments() {
        return arguments;
    }
    
    public int[] getTargetAddresses() {
        return targetAddresses;
    }

    /**
     * @return the memomic
     */
    public String getMemomic() {
        return memomic;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @return the target
     */
    public int getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(int target) {
        this.target = target;
    }

    public void setLabel(Set<String> pendingLabels) {
        this.labels = pendingLabels;
    }
    
    public Set<String> getLabels() {
        if(labels==null)
            return Collections.EMPTY_SET;
        else
            return labels;
    }
    
    public static ByteCode INS_EQUALS() {
        return new ByteCode("INS_EQUALS",1,new String[0]);
    }
    public static ByteCode INS_PUSHC(String constant) {
        return new ByteCode("INS_PUSHC",2,new String[] { constant });
    }
    public static ByteCode INS_TERMINATE() {
        return new ByteCode("INS_END_TERMINATE",1,new String[0]);
    }
    public static ByteCode INS_CENTER_POSITION_something(String what) {
        return new ByteCode("INS_CENTER_POSITION_"+what,1,new String[0]);
    }
    public void updatePositions(Map<String, AddressSpec> table) {
        int argcount = 0;
        for(int i=0; i<targetArguments.length; i++) {
            int index = targetArguments[i];
            AddressSpec spec = table.get(arguments[index]);
            Integer address = isSubroutine() ? spec.getVirtual() : spec.getReal();
            if(address==null) 
                throw new Error("Undefined label: "+arguments[index]);
            arguments[index] = Integer.toString(address)+" /*"+arguments[index]+"*/";
            targetAddresses[argcount++] = address;
        }
    }
    public boolean hasTarget() {
        return targetArguments!=null;
    }
    public static ByteCode INS_CONNECTED_something_ROLE(String direction, String role) {
        return new ByteCode("INS_CONNECTED_"+stripPrefix("ARG_",direction)+"_ROLE",2,new String[] { role });
    }
    public static ByteCode INS_EQUALS_something(String what) {
        return new ByteCode("INS_EQUALS_"+what,1,new String[0]);
    }
    public static ByteCode MK_INS_CONSTANT(String number) {
        return new ByteCode("MK_INS_CONSTANT("+number+")",1,new String[0]);
    }
    public static ByteCode MK_INS_CONNECTED_DIR_SIZEOF(String direction) {
        return new ByteCode("INS_CONNECTED_DIR_SIZEOF",2,new String[] { direction });
    }
    private static String stripPrefix(String prefix, String argument) {
        if(argument.startsWith(prefix)) return argument.substring(prefix.length());
        return argument;
    }
    public static ByteCode NOP() {
        return new ByteCode("INS_NOP",1,new String[0]);
    }
    public void patch(Map<String, AddressSpec> table) {
        ;
    }
    public static ByteCode MK_INS_COORD_any_any(String coord, String comp) {
        return new ByteCode("INS_COORD_"+coord+"_"+comp,1,new String[0]);
    }
    
}

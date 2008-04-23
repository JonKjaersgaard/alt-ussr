/**
 * 
 */
package dcd.highlevel;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ByteCodeSequence {

    public static class AddressSpec {
        private int real, virtual;
        public AddressSpec(int real, int virtual) {
            this.real = real; this.virtual = virtual;
        }
        public int getReal() { return real; }
        public int getVirtual() { return virtual; }
    }
    
    private List<ByteCode> bytecodes = new LinkedList<ByteCode>();
    private Set<String> pendingLabels = new HashSet<String>();
    private boolean blockStart = false;
    
    public int getSize() { 
        int result = 0;
        for(ByteCode code: bytecodes) result += code.getSize();
        return result;
    }
    public void resolveGoto() {
        if(!pendingLabels.isEmpty()) throw new Error("Not all labels flushed: "+pendingLabels);
        Map<String,AddressSpec> table = new HashMap<String,AddressSpec>();
        int realPosition = 0, virtualPosition = 0;
        for(ByteCode bytecode: bytecodes) {
            if(bytecode.isBlockStart()) virtualPosition = 0;
            if(!bytecode.getLabels().isEmpty()) {
                for(String label: bytecode.getLabels())
                    table.put(label,new AddressSpec(realPosition,virtualPosition));
            }
            bytecode.setAddress(realPosition,virtualPosition);
            realPosition += bytecode.getSize();
            virtualPosition += bytecode.getSize();
        }
        for(ByteCode bytecode: bytecodes)
            if(bytecode.hasTarget()) bytecode.updatePositions(table);
        for(ByteCode bytecode: bytecodes)
            bytecode.patch(table);
    }
    public void peepHoleOptimize() {
        int position = 0;
        loop: while(position<bytecodes.size()) {
            for(Pattern pattern: patterns)
                if(pattern.matchAndTransform(bytecodes, position)) {
                    position = 0;
                    continue loop;
                }
            position++;
        }
    }
    public void add(ByteCode bytecode) {
        if(blockStart) {
            bytecode.setAsBlockStart();
            blockStart = false;
        }
        if(!pendingLabels.isEmpty()) {
            bytecode.setLabel(pendingLabels);
            pendingLabels = new HashSet<String>();
        }
        bytecodes.add(bytecode); 
    } 
    public String toString() {
        StringBuffer result = new StringBuffer();
        for(ByteCode bc: bytecodes) {
            result.append(bc);
            result.append('\n');
        }
        return result.toString();
    }
    public void generate(OutputBuilder writer) {
        for(ByteCode bc: bytecodes)
            writer.addByteCode(bc);
    }
    public void addLabel(String label) {
        pendingLabels.add(label);
    }
    
    public static class Pattern {
        String fpat[];
        Rule rule;
        Pattern(String fpat[], Rule rule) {
            this.fpat = fpat; this.rule = rule;
        }
        boolean matchAndTransform(List<ByteCode> instructions, int position) {
            if(fpat.length+position>=instructions.size()) return false;
            ByteCode slice[] = new ByteCode[fpat.length];
            Set<String> labels = new HashSet<String>();
            boolean isBlockStart = instructions.get(position).isBlockStart();
            for(int i=0; i<fpat.length; i++) {
                if(!instructions.get(position+i).is(fpat[i])) return false;
                slice[i] = instructions.get(position+i);
                labels.addAll(slice[i].getLabels());
            }
            if(!rule.condition(slice)) return false;
            for(int i=0; i<fpat.length; i++) instructions.remove(position);
            ByteCode result[] = rule.apply(slice);
            result[0].setLabel(labels);
            if(isBlockStart) result[0].setAsBlockStart();
            for(int i=result.length-1; i>=0; i--) instructions.add(position, result[i]);
            return true;
        }
    }
    
    public abstract static class Rule {
        abstract ByteCode[] apply(ByteCode in[]);
        boolean condition(ByteCode in[]) { return true; }
    }
    
    public static final Pattern[] PEEP_HOLE_PATTERNS = new Pattern[] {
        new Pattern(
                new String[] { ByteCode.CENTER_POSITION,ByteCode.PUSHC,ByteCode.EQUALS },
                new Rule() { ByteCode[] apply(ByteCode in[]) {
                    if(in[1].getArguments()[0].equals(ByteCode.EAST_WEST)) return new ByteCode[] { ByteCode.INS_CENTER_POSITION_something("EW") };
                    throw new Error("Unknown direction: "+in[1]);
                }}),
        new Pattern(
                new String[] { ByteCode.CONNECTED_ROLE },
                new Rule() { ByteCode[] apply(ByteCode in[]) {
                    return new ByteCode[] { ByteCode.INS_CONNECTED_something_ROLE(in[0].getArguments()[0],in[0].getArguments()[1]) };
                }}),
        new Pattern(
                new String[] { ByteCode.PUSHC, ByteCode.INS_EQUALS },
                new Rule() {
                    boolean condition(ByteCode in[]) { return in[0].getArguments()[0].equals("0")||in[0].getArguments()[0].equals("1"); }
                    ByteCode[] apply(ByteCode in[]) {
                        return new ByteCode[] { ByteCode.INS_EQUALS_something(in[0].getArguments()[0]) };
        }}),
        new Pattern(
                new String[] { ByteCode.SIZEOF, ByteCode.INS_EQUALS_0 },
                new Rule() { ByteCode[] apply(ByteCode in[]) { return new ByteCode[] { ByteCode.INS_EQUALS_something("0") };
        }}),
        new Pattern(
                new String[] { ByteCode.PUSHC },
                new Rule() {
                    boolean condition(ByteCode in[]) { try { return (Integer.parseInt(in[0].getArguments()[0]))<128; } catch(NumberFormatException exn) { return false; } }
                    ByteCode[] apply(ByteCode in[]) {
                        return new ByteCode[] { ByteCode.MK_INS_CONSTANT(in[0].getArguments()[0]) };
                    }
                }),
        new Pattern(
                new String[] { ByteCode.CONNECTED_DIR, ByteCode.SIZEOF },
                new Rule() { ByteCode[] apply(ByteCode in[]) {
                    return new ByteCode[] { ByteCode.MK_INS_CONNECTED_DIR_SIZEOF(in[0].getArguments()[0]) };
                }}),
        new Pattern(
                new String[] { "INS_COORD_", "MK_INS_CONSTANT", "INS_GREATER" },
                new Rule() {
                    ByteCode[] apply(ByteCode[] in) {
                        ByteCode major;
                        if(in[0].getMemomic().equals("INS_COORD_Y")) major = ByteCode.MK_INS_COORD_any_any("Y","GREATER");
                        else throw new Error("Not supported: "+in[0].getMemomic());
                        return new ByteCode[] { in[1], major };
                    }
                })
    };
    private static final List<Pattern> patterns = Arrays.asList(PEEP_HOLE_PATTERNS);

    public void setBlockStart() {
        blockStart  = true;
    } 
}
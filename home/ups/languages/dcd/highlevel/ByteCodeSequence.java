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
    private List<ByteCode> bytecodes = new LinkedList<ByteCode>();
    private Set<String> pendingLabels = new HashSet<String>();
    public int getSize() { 
        int result = 0;
        for(ByteCode code: bytecodes) result += code.getSize();
        return result;
    }
    public void resolveGoto() {
        if(!pendingLabels.isEmpty()) throw new Error("Not all labels flushed");
        Map<String,Integer> table = new HashMap<String,Integer>();
        int position = 0;
        for(ByteCode bytecode: bytecodes) {
            if(!bytecode.getLabels().isEmpty()) {
                for(String label: bytecode.getLabels())
                    table.put(label,position);
            }
            bytecode.setAddress(position);
            position += bytecode.getSize();
        }
        for(ByteCode bytecode: bytecodes)
            if(bytecode.hasTarget()) bytecode.updatePositions(table);
    }
    public void peepHoleOptimize() {
        int position = 0;
        loop: while(position<bytecodes.size()) {
            for(Pattern pattern: patterns)
                if(pattern.matchAndTransform(bytecodes, position)) continue loop;
            position++;
        }
    }
    public void add(ByteCode bytecode) {
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
    public void generate(PrintWriter writer) {
        writer.println(this);
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
            for(int i=0; i<fpat.length; i++) {
                if(!instructions.get(position+i).is(fpat[i])) return false;
                slice[i] = instructions.get(position+i);
            }
            for(int i=0; i<fpat.length; i++) instructions.remove(position);
            ByteCode result[] = rule.apply(slice);
            for(int i=result.length-1; i>=0; i--) instructions.add(position, result[i]);
            return true;
        }
    }
    
    public abstract static class Rule {
        abstract ByteCode[] apply(ByteCode in[]);
    }
    
    public static final Pattern[] PEEP_HOLE_PATTERNS = new Pattern[] {
        new Pattern(
                new String[] { ByteCode.CENTER_POSITION,ByteCode.PUSHC,ByteCode.EQUALS },
                new Rule() { ByteCode[] apply(ByteCode in[]) {
                    if(in[1].getArguments()[0].equals(ByteCode.EAST_WEST)) return new ByteCode[] { ByteCode.INS_CENTER_POSITION_EW() };
                    throw new Error("Unknown direction: "+in[1]);
                }})
    };
    private static final List<Pattern> patterns = Arrays.asList(PEEP_HOLE_PATTERNS); 
}
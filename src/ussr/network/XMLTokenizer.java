/**
 * 
 */
package ussr.network;


class XMLTokenizer {
    private String string;
    private int index;
    public XMLTokenizer(String string) { this.string = string; }
    public void eat(String sequence) {
        if(!string.startsWith(sequence, index)) throw new XMLTokenizerMismatch("Expected "+sequence+" got "+string.substring(index));
        index+=sequence.length();
    }
    public boolean hasTag(String tag) {
        return string.startsWith("<"+tag+">", index);
    }
    public byte eatByte() {
        this.eat("<b>");
        int end = string.indexOf('<', index);
        byte result = Byte.parseByte(string.substring(index, end));
        index = end;
        this.eat("</b>");
        return result;
    }
}
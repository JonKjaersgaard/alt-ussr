package mpl;

import java.util.ArrayList;
import java.util.List;


public class GeneParser {

    private int regionSize;
    private Element[] traits;
    
    public GeneParser(int regionSize, Element[] traits) {
        this.regionSize = regionSize;
        this.traits = traits;
    }

    public List<Element> parse(String geneSpec) {
        ArrayList<Element> result = new ArrayList<Element>();
        for(int index = 0; index<geneSpec.length(); index+=regionSize) 
            result.add(parseRegion(geneSpec.substring(index,index+regionSize)));
        return result;
    }

    private Element parseRegion(String substring) {
        int value = 0;
        for(int index = substring.length()-1; index>=0; index--) {
            value<<=1;
            if(substring.charAt(index)=='1') value += 1;
        }
        if(value<traits.length)
            return traits[value];
        else
            throw new Error("Region value out of range: "+substring);
    }

}

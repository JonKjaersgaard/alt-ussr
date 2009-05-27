package mpl;

import java.util.ArrayList;
import java.util.List;

import mpl.MPLSimulation.ConveyorElement;

public class GeneParser {

    private int regionSize;
    private char[] traits;
    
    public GeneParser(int regionSize, char[] traits) {
        this.regionSize = regionSize;
        this.traits = traits;
    }

    public List<ConveyorElement> parse(String geneSpec) {
        ArrayList<ConveyorElement> result = new ArrayList<ConveyorElement>();
        for(int index = 0; index<geneSpec.length(); index+=regionSize) 
            result.add(parseRegion(geneSpec.substring(index,index+regionSize)));
        return result;
    }

    private ConveyorElement parseRegion(String substring) {
        int value = 0;
        for(int index = substring.length()-1; index>=0; index--) {
            value<<=1;
            if(substring.charAt(index)=='1') value += 1;
        }
        if(value<traits.length)
            return ConveyorElement.fromChar(traits[value]);
        else
            throw new Error("Region value out of range: "+substring);
    }

}

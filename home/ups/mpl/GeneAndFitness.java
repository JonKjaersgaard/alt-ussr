package mpl;

import ussr.description.geometry.VectorDescription;

public class GeneAndFitness {

    protected static final float FITNESS_SIMULATION_FAILED = 0;

    public static float computeFitness(VectorDescription initialBoxPosition, VectorDescription finalBoxPosition, VectorDescription targetPosition) {
        return (1/targetPosition.distance(finalBoxPosition))*10;
    }

    public static GeneParser createGeneParser() {
        return new GeneParser(1,new char[] { 'P', 'r'});
    }

}

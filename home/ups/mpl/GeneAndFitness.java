package mpl;

import ussr.description.geometry.VectorDescription;

public class GeneAndFitness {

    protected static final float FITNESS_SIMULATION_FAILED = 0;

    public static float computeFitness(VectorDescription initialBoxPosition, VectorDescription finalBoxPosition, VectorDescription targetPosition) {
        System.out.println("I=Initial pos="+initialBoxPosition);
        System.out.println("F=Final pos="+finalBoxPosition);
        System.out.println("T=Target pos="+targetPosition);
        float Df = finalBoxPosition.distance(targetPosition);
        System.out.println("D_f=Distance(F,T)="+Df);
        float Di = initialBoxPosition.distance(targetPosition);
        System.out.println("D_i=Distance(F,I)="+Di);
        float Dd = Di-Df;
        System.out.println("fitness=D_i-D_f="+Dd);
        return Dd;
    }

    public static GeneParser createGeneParser() {
        return new GeneParser(1,new char[] { 'P', 'r'});
    }

}

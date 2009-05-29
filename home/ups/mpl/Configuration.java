package mpl;

import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;

public class Configuration {

    // Fitness value indicating a failed simulation (ODE crash due to modules being destroyed)
    public static final float FITNESS_SIMULATION_FAILED = 0;
    
    // Use smooth ATRONs or plain ATRONs for passive modules?
    public static final boolean PASSIVE_MODULE_SMOOTH_ATRON = true;
    
    // Size and position of 2D ATRON plane
    public static final int PLANE_MAX_MODULES = 50;
    public static final int PLANE_MAX_X = 5;
    public static final int PLANE_MAX_Z = 10;
    public static final VectorDescription PLANE_POSITION = new VectorDescription(0,-0.54f,0);

    // Size and position of the box being transported
    public static final VectorDescription BOX_SIZE = new VectorDescription(0.2f,0.06f,0.2f);
    public static final float BOX_MASS = 10f;
    private static final VectorDescription BOX_INITIAL_POSITION = new VectorDescription(0.1f,-0.25f,0.4f);
    private static final RotationDescription BOX_INITIAL_ROTATION = new RotationDescription(0,0,0);
    
    // Fitness function
    public static float computeFitness(VectorDescription initialBoxPosition, VectorDescription finalBoxPosition, VectorDescription targetPosition) {
        System.out.println("I=Initial pos="+initialBoxPosition);
        System.out.println("F=Final pos="+finalBoxPosition);
        System.out.println("T=Target pos="+targetPosition);
        float Df = finalBoxPosition.distance(targetPosition);
        System.out.println("D_f=Distance(F,T)="+Df);
        float Di = initialBoxPosition.distance(targetPosition);
        System.out.println("D_i=Distance(F,I)="+Di);
        float Dd = Di-Df;
        float fitness = Dd+1;
        if(fitness<0) fitness = 0;
        System.out.println("fitness=pos(D_i-D_f+1)="+fitness);
        return fitness;
    }

    // How to parse gene string: size of each gene and numerical mapping of gene values to conveyor elements 
    public static GeneParser createGeneParser() {
        return new GeneParser(1,new Element[] { Element.PLAIN, Element.ROTATING_COUNTERCW });
    }

    public static VectorDescription boxInitialPosition() {
        return BOX_INITIAL_POSITION;
    }

    public static RotationDescription boxInitialRotation() {
        return BOX_INITIAL_ROTATION;
    }

}

/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.util;

/**
 * A genericly typed pair with constructor and methods for accessing first and second 
 * projects (elements)
 * 
 * @author ups
 */
public class Pair<T1, T2> {
    /**
     * First element in the pair
     */
    private T1 car;
    
    /**
     * Second element in the pair
     */
    private T2 cdr;
    
    /**
     * Construct a new pair from the two elements
     * @param car the first element in the pair
     * @param cdr the second element in the pair
     */
    public Pair(T1 car, T2 cdr) {
        this.car = car; this.cdr = cdr;
    }
    
    /**
     * Get the first element in the pair
     * @return the first pair element
     */
    public T1 fst() { return car; }
    
    /**
     * Get the second element in the pair
     * @return the second pair element
     */
    public T2 snd() { return cdr; }
}

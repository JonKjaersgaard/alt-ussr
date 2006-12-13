/**
 * 
 */
package ussr.util;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class Pair<T1, T2> {
    private T1 car;
    private T2 cdr;
    public Pair(T1 car, T2 cdr) {
        this.car = car; this.cdr = cdr;
    }
    public T1 fst() { return car; }
    public T2 snd() { return cdr; }
}

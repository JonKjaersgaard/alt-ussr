/**
 * 
 */
package mpl;

public enum Element {
    PLAIN, ROTATING_CLOCKWISE, ROTATING_COUNTERCW, BLOCKER, SPINNER, COUNTER_SPINNER;
    static Element fromChar(char c) {
        switch(c) {
        case 'P': return PLAIN;
        case 'R': return ROTATING_CLOCKWISE;
        case 'r': return ROTATING_COUNTERCW;
        case 'b': return BLOCKER;
        case 's': return SPINNER;
        case 'S': return COUNTER_SPINNER;
        default: throw new Error("Undefined conveyor element: "+c);
        }
    }
}
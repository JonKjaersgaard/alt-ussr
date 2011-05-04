/**
 * 
 */
package ussr.samples.atron.framework.distributed;

import ussr.samples.atron.framework.ATRONFramework;
import ussr.samples.atron.framework.ATRONFramework.Role;


public class Collaboration {

    public class ModuleSelector<R extends Role> {
        public R get(int id) { return null; }

        public <T> T all(Class<T> qualifier) { throw new Error(); }
        public <T> T some(Class<T> qualifier) { throw new Error(); }

        public Element<Boolean> isAlive(int i) {
            // TODO Auto-generated method stub
            // return null;
            throw new Error("Method not implemented");
        }

        public Collaboration getAny(int... i) {
            // TODO Auto-generated method stub
            // return null;
            throw new Error("Method not implemented");
        }

        public Collaboration getAll(int... i) {
            // TODO Auto-generated method stub
            // return null;
            throw new Error("Method not implemented");
        }

    }
    
    public Element<Void> sleep(float time) {
        throw new Error();
    }
    
    public Element<Void> par(Element<?>... elements) { throw new Error(); }
    public Element<Void> IF(Element<Boolean> condition, Element<?> thenCase) { throw new Error(); }
    public Element<Void> WHILE(Element<Boolean> condition, Element<?> body) {
        // TODO Auto-generated method stub
        // return null;
        throw new Error("Method not implemented");
    }
    public Script makeAtomicScript(Element<?>...elements) { throw new Error(); }
}
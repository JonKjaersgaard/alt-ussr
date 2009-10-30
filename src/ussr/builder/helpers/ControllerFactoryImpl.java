package ussr.builder.helpers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ussr.model.Controller;

public class ControllerFactoryImpl implements ControllerFactory {
    private Set<Class<? extends Controller>> controllerClasses = new HashSet<Class<? extends Controller>>(); 
    
    public ControllerFactoryImpl(List<String> controllerNames) {
        for(String name: controllerNames) {
            try {
                Class<?> potentialClass = Class.forName(name);
                if(!Controller.class.isAssignableFrom(potentialClass)) throw new Error("Illegal controller class "+name+": does not implement proper interface");
                controllerClasses.add((Class<? extends Controller>)potentialClass);
            } catch (ClassNotFoundException e) {
                throw new Error("Error: controller class "+name+" not found");
            }
        }
    }

    /* (non-Javadoc)
     * @see ussr.builder.helpers.ControllerFactory#has(java.lang.Class)
     */
    public boolean has(Class<? extends Controller> superClass) {
        return this.find(superClass)!=null;
    }

    /* (non-Javadoc)
     * @see ussr.builder.helpers.ControllerFactory#find(java.lang.Class)
     */
    public Class<? extends Controller> find(Class<? extends Controller> superClass) {
        for(Class<? extends Controller> c: controllerClasses)
            if(superClass.isAssignableFrom(c)) return c;
        return null;
    }

    /* (non-Javadoc)
     * @see ussr.builder.helpers.ControllerFactory#create(java.lang.Class)
     */
    public Controller create(Class<? extends Controller> superClass) {
        Class<? extends Controller> c = find(superClass);
        if(c==null) throw new Error("Controller class not found: "+superClass.getName());
        try {
            return c.newInstance();
        } catch (InstantiationException e) {
            throw new Error("Unable to instantiate controller "+c.getName()+": "+e);
        } catch (IllegalAccessException e) {
            throw new Error("Unable to instantiate controller: "+c.getName()+": "+e);
        }
    }

}

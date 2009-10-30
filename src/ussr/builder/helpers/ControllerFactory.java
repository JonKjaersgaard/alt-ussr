package ussr.builder.helpers;

import ussr.model.Controller;

public interface ControllerFactory {

    public boolean has(Class<? extends Controller> superClass);

    public Class<? extends Controller> find(
            Class<? extends Controller> superClass);

    public Controller create(Class<? extends Controller> superClass);

}
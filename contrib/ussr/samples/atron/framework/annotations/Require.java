package ussr.samples.atron.framework.annotations;

import java.lang.annotation.*;

/**
 * Annotation indicating a method that must evaluate to true for this role to activate; must
 * only be used on methods returning a boolean and taking no arguments.
 * @author ups
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Require {

}

package ussr.samples.atron.framework.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation indicating a Behavior Method, a method that is repeatedly activated as long as the role is active.
 * Must only be used on void methods that take no arguments.
 * @author ups
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Behavior {

}

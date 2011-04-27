package ussr.samples.atron.framework.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation indicating a Handler Method, a method that handles a specific kind of event.
 * The specific event to be handled is indicated by the properties of the annotation.  Must
 * only be used on void methods that take no arguments.
 * @author ups
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Handler {

    /**
     * Connectors on which a proximity event (with the current sensitivity) is triggered 
     */
    int[] proximity();


}

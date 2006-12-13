/**
 * 
 */
package ussr.physics.jme;

import com.jme.input.KeyInput;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class JMEKeyTranslator {

    public static int translate(String keyName) {
        if(keyName.equals("Z")) return KeyInput.KEY_Z;
        throw new Error("Key not supported: keyName");
    }

}

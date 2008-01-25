/**
 * 
 */
package ussr.physics.jme;

import com.jme.input.KeyInput;

/**
 * Translates string-based physics-engine-independent key names into JME equivalents 
 * 
 * @author Modular Robots @ MMMI
 */
public class JMEKeyTranslator {

    public static int translate(String keyName) {
        if(keyName.equals("Z")) return KeyInput.KEY_Z;
        throw new Error("Key not supported: "+ keyName);
    }

}

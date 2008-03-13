/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
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

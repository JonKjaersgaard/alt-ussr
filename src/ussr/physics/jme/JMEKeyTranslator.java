/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics.jme;

import java.util.HashMap;
import java.util.Map;

import com.jme.input.KeyInput;

/**
 * Translates string-based physics-engine-independent key names into JME equivalents 
 * 
 * @author Modular Robots @ MMMI
 */
public class JMEKeyTranslator {

    public static final Map<String,Integer> MAPPING = new HashMap<String,Integer>(); 

    static {
        MAPPING.put("ESCAPE",KeyInput.KEY_ESCAPE);
        MAPPING.put("1",KeyInput.KEY_1);
        MAPPING.put("2",KeyInput.KEY_2);
        MAPPING.put("3",KeyInput.KEY_3);
        MAPPING.put("4",KeyInput.KEY_4);
        MAPPING.put("5",KeyInput.KEY_5);
        MAPPING.put("6",KeyInput.KEY_6);
        MAPPING.put("7",KeyInput.KEY_7);
        MAPPING.put("8",KeyInput.KEY_8);
        MAPPING.put("9",KeyInput.KEY_9);
        MAPPING.put("0",KeyInput.KEY_0);
        MAPPING.put("MINUS",KeyInput.KEY_MINUS);
        MAPPING.put("EQUALS",KeyInput.KEY_EQUALS);
        MAPPING.put("BACK",KeyInput.KEY_BACK);
        MAPPING.put("TAB",KeyInput.KEY_TAB);
        MAPPING.put("Q",KeyInput.KEY_Q);
        MAPPING.put("W",KeyInput.KEY_W);
        MAPPING.put("E",KeyInput.KEY_E);
        MAPPING.put("R",KeyInput.KEY_R);
        MAPPING.put("T",KeyInput.KEY_T);
        MAPPING.put("Y",KeyInput.KEY_Y);
        MAPPING.put("U",KeyInput.KEY_U);
        MAPPING.put("I",KeyInput.KEY_I);
        MAPPING.put("O",KeyInput.KEY_O);
        MAPPING.put("P",KeyInput.KEY_P);
        MAPPING.put("LBRACKET",KeyInput.KEY_LBRACKET);
        MAPPING.put("RBRACKET",KeyInput.KEY_RBRACKET);
        MAPPING.put("RETURN",KeyInput.KEY_RETURN);
        MAPPING.put("LCONTROL",KeyInput.KEY_LCONTROL);
        MAPPING.put("A",KeyInput.KEY_A);
        MAPPING.put("S",KeyInput.KEY_S);
        MAPPING.put("D",KeyInput.KEY_D);
        MAPPING.put("F",KeyInput.KEY_F);
        MAPPING.put("G",KeyInput.KEY_G);
        MAPPING.put("H",KeyInput.KEY_H);
        MAPPING.put("J",KeyInput.KEY_J);
        MAPPING.put("K",KeyInput.KEY_K);
        MAPPING.put("L",KeyInput.KEY_L);
        MAPPING.put("SEMICOLON",KeyInput.KEY_SEMICOLON);
        MAPPING.put("APOSTROPHE",KeyInput.KEY_APOSTROPHE);
        MAPPING.put("GRAVE",KeyInput.KEY_GRAVE);
        MAPPING.put("LSHIFT",KeyInput.KEY_LSHIFT);
        MAPPING.put("BACKSLASH",KeyInput.KEY_BACKSLASH);
        MAPPING.put("Z",KeyInput.KEY_Z);
        MAPPING.put("X",KeyInput.KEY_X);
        MAPPING.put("C",KeyInput.KEY_C);
        MAPPING.put("V",KeyInput.KEY_V);
        MAPPING.put("B",KeyInput.KEY_B);
        MAPPING.put("N",KeyInput.KEY_N);
        MAPPING.put("M",KeyInput.KEY_M);
        MAPPING.put("COMMA",KeyInput.KEY_COMMA);
        MAPPING.put("PERIOD",KeyInput.KEY_PERIOD);
        MAPPING.put("SLASH",KeyInput.KEY_SLASH);
        MAPPING.put("RSHIFT",KeyInput.KEY_RSHIFT);
        MAPPING.put("MULTIPLY",KeyInput.KEY_MULTIPLY);
        MAPPING.put("LMENU",KeyInput.KEY_LMENU);
        MAPPING.put("SPACE",KeyInput.KEY_SPACE);
        MAPPING.put("CAPITAL",KeyInput.KEY_CAPITAL);
        MAPPING.put("F1",KeyInput.KEY_F1);
        MAPPING.put("F2",KeyInput.KEY_F2);
        MAPPING.put("F3",KeyInput.KEY_F3);
        MAPPING.put("F4",KeyInput.KEY_F4);
        MAPPING.put("F5",KeyInput.KEY_F5);
        MAPPING.put("F6",KeyInput.KEY_F6);
        MAPPING.put("F7",KeyInput.KEY_F7);
        MAPPING.put("F8",KeyInput.KEY_F8);
        MAPPING.put("F9",KeyInput.KEY_F9);
        MAPPING.put("F10",KeyInput.KEY_F10);
        MAPPING.put("NUMLOCK",KeyInput.KEY_NUMLOCK);
        MAPPING.put("SCROLL",KeyInput.KEY_SCROLL);
        MAPPING.put("NUMPAD7",KeyInput.KEY_NUMPAD7);
        MAPPING.put("NUMPAD8",KeyInput.KEY_NUMPAD8);
        MAPPING.put("NUMPAD9",KeyInput.KEY_NUMPAD9);
        MAPPING.put("SUBTRACT",KeyInput.KEY_SUBTRACT);
        MAPPING.put("NUMPAD4",KeyInput.KEY_NUMPAD4);
        MAPPING.put("NUMPAD5",KeyInput.KEY_NUMPAD5);
        MAPPING.put("NUMPAD6",KeyInput.KEY_NUMPAD6);
        MAPPING.put("ADD",KeyInput.KEY_ADD);
        MAPPING.put("NUMPAD1",KeyInput.KEY_NUMPAD1);
        MAPPING.put("NUMPAD2",KeyInput.KEY_NUMPAD2);
        MAPPING.put("NUMPAD3",KeyInput.KEY_NUMPAD3);
        MAPPING.put("NUMPAD0",KeyInput.KEY_NUMPAD0);
        MAPPING.put("DECIMAL",KeyInput.KEY_DECIMAL);
        MAPPING.put("F11",KeyInput.KEY_F11);
        MAPPING.put("F12",KeyInput.KEY_F12);
        MAPPING.put("F13",KeyInput.KEY_F13);
        MAPPING.put("F14",KeyInput.KEY_F14);
        MAPPING.put("F15",KeyInput.KEY_F15);
        MAPPING.put("KANA",KeyInput.KEY_KANA);
        MAPPING.put("CONVERT",KeyInput.KEY_CONVERT);
        MAPPING.put("NOCONVERT",KeyInput.KEY_NOCONVERT);
        MAPPING.put("YEN",KeyInput.KEY_YEN);
        MAPPING.put("NUMPADEQUALS",KeyInput.KEY_NUMPADEQUALS);
        MAPPING.put("CIRCUMFLEX",KeyInput.KEY_CIRCUMFLEX);
        MAPPING.put("AT",KeyInput.KEY_AT);
        MAPPING.put("COLON",KeyInput.KEY_COLON);
        MAPPING.put("UNDERLINE",KeyInput.KEY_UNDERLINE);
        MAPPING.put("KANJI",KeyInput.KEY_KANJI);
        MAPPING.put("STOP",KeyInput.KEY_STOP);
        MAPPING.put("AX",KeyInput.KEY_AX);
        MAPPING.put("UNLABELED",KeyInput.KEY_UNLABELED);
        MAPPING.put("NUMPADENTER",KeyInput.KEY_NUMPADENTER);
        MAPPING.put("RCONTROL",KeyInput.KEY_RCONTROL);
        MAPPING.put("NUMPADCOMMA",KeyInput.KEY_NUMPADCOMMA);
        MAPPING.put("DIVIDE",KeyInput.KEY_DIVIDE);
        MAPPING.put("SYSRQ",KeyInput.KEY_SYSRQ);
        MAPPING.put("RMENU",KeyInput.KEY_RMENU);
        MAPPING.put("PAUSE",KeyInput.KEY_PAUSE);
        MAPPING.put("HOME",KeyInput.KEY_HOME);
        MAPPING.put("UP",KeyInput.KEY_UP);
        MAPPING.put("PRIOR",KeyInput.KEY_PRIOR);
        MAPPING.put("PGUP",KeyInput.KEY_PGUP);
        MAPPING.put("LEFT",KeyInput.KEY_LEFT);
        MAPPING.put("RIGHT",KeyInput.KEY_RIGHT);
        MAPPING.put("END",KeyInput.KEY_END);
        MAPPING.put("DOWN",KeyInput.KEY_DOWN);
        MAPPING.put("NEXT",KeyInput.KEY_NEXT);
        MAPPING.put("PGDN",KeyInput.KEY_PGDN);
        MAPPING.put("INSERT",KeyInput.KEY_INSERT);
        MAPPING.put("DELETE",KeyInput.KEY_DELETE);
        MAPPING.put("LWIN",KeyInput.KEY_LWIN);
        MAPPING.put("RWIN",KeyInput.KEY_RWIN);
        MAPPING.put("APPS",KeyInput.KEY_APPS);
        MAPPING.put("POWER",KeyInput.KEY_POWER);
        MAPPING.put("SLEEP",KeyInput.KEY_SLEEP);
    }

    public static int translate(String keyName) {
        Integer code = MAPPING.get(keyName);
        if(code==null) throw new Error("Key not supported: "+ keyName);
        return code.intValue();
    }

}

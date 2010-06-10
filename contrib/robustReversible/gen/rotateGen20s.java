/* DynaRole Java code generated for parallelRotate */
/* DynaRole Java statemachine generated for parallelRotate */
package robustReversible.gen;

import robustReversible.*;

public class rotateGen20s extends StateMachine {

  private static final int CENTER_ERROR_TOLERANCE = 5;
  private int token = 255;
  private int myID=-1;
  private boolean isDone = false; 
  private static final boolean TRUE = true;
  private static final boolean FALSE = false;
  
  private boolean doneRotatingTo(int goal) { 
	
	if(api.isRotating()) {
		return false; 
	}

	return true;
  }

  private void rotateDirTo(int to, boolean direction) {
	api.rotateDirToInDegrees(to, direction);
}


  private void connect(int connector) {
	api.connect(connector);
  }

  private void disconnect(int connector) {
	api.disconnect(connector);
  }

  private boolean notDoneConnecting(int connector) {
	return !api.isConnected(connector);
} 
 
  private boolean notDoneDisconnecting(int connector) {
	return api.isConnected(connector);
  }

  protected void stateMachine() { 
    api.yield();
    if(token == 255) { /* try to see if there's a new state for me */
      token = stateManager.getMyNewState();
	  if(token!=255) {
		System.out.println(myID+": Now performing state "+token);
	  }
    }
	
	switch(token) {
	case 0: token = 1; /* fall-through */
    case 1: /* Module M__0 */
      stateManager.addPendingState(1);
      rotateDirTo(216,TRUE);
      token = 2;
      break;
    case 2:
      stateManager.sendState(4,1);
      token = 3; /* fall-through */
    case 3:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(1);
      token = 255;
      break;
    case 4: /* Module M__1 */
      stateManager.addPendingState(4);
      rotateDirTo(216,TRUE);
      token = 5;
      break;
    case 5:
      stateManager.sendState(7,2);
      token = 6; /* fall-through */
    case 6:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(4);
      token = 255;
      break;
    case 7: /* Module M__2 */
      stateManager.addPendingState(7);
      rotateDirTo(216,TRUE);
      token = 8;
      break;
    case 8:
      stateManager.sendState(10,3);
      token = 9; /* fall-through */
    case 9:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(7);
      token = 255;
      break;
    case 10: /* Module M__3 */
      stateManager.addPendingState(10);
      rotateDirTo(216,TRUE);
      token = 11;
      break;
    case 11:
      stateManager.sendState(13,4);
      token = 12; /* fall-through */
    case 12:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(10);
      token = 255;
      break;
    case 13: /* Module M__4 */
      stateManager.addPendingState(13);
      rotateDirTo(216,TRUE);
      token = 14;
      break;
    case 14:
      stateManager.sendState(16,5);
      token = 15; /* fall-through */
    case 15:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(13);
      token = 255;
      break;
    case 16: /* Module M__5 */
      stateManager.addPendingState(16);
      rotateDirTo(216,TRUE);
      token = 17;
      break;
    case 17:
      stateManager.sendState(19,6);
      token = 18; /* fall-through */
    case 18:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(16);
      token = 255;
      break;
    case 19: /* Module M__6 */
      stateManager.addPendingState(19);
      rotateDirTo(216,TRUE);
      token = 20;
      break;
    case 20:
      stateManager.sendState(22,7);
      token = 21; /* fall-through */
    case 21:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(19);
      token = 255;
      break;
    case 22: /* Module M__7 */
      stateManager.addPendingState(22);
      rotateDirTo(216,TRUE);
      token = 23;
      break;
    case 23:
      stateManager.sendState(25,8);
      token = 24; /* fall-through */
    case 24:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(22);
      token = 255;
      break;
    case 25: /* Module M__8 */
      stateManager.addPendingState(25);
      rotateDirTo(216,TRUE);
      token = 26;
      break;
    case 26:
      stateManager.sendState(28,9);
      token = 27; /* fall-through */
    case 27:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(25);
      token = 255;
      break;
    case 28: /* Module M__9 */
      stateManager.addPendingState(28);
      rotateDirTo(216,TRUE);
      token = 29;
      break;
    case 29:
      stateManager.sendState(31,10);
      token = 30; /* fall-through */
    case 30:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(28);
      token = 255;
      break;
    case 31: /* Module M_10 */
      stateManager.addPendingState(31);
      rotateDirTo(216,TRUE);
      token = 32;
      break;
    case 32:
      stateManager.sendState(34,11);
      token = 33; /* fall-through */
    case 33:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(31);
      token = 255;
      break;
    case 34: /* Module M_11 */
      stateManager.addPendingState(34);
      rotateDirTo(216,TRUE);
      token = 35;
      break;
    case 35:
      stateManager.sendState(37,12);
      token = 36; /* fall-through */
    case 36:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(34);
      token = 255;
      break;
    case 37: /* Module M_12 */
      stateManager.addPendingState(37);
      rotateDirTo(216,TRUE);
      token = 38;
      break;
    case 38:
      stateManager.sendState(40,13);
      token = 39; /* fall-through */
    case 39:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(37);
      token = 255;
      break;
    case 40: /* Module M_13 */
      stateManager.addPendingState(40);
      rotateDirTo(216,TRUE);
      token = 41;
      break;
    case 41:
      stateManager.sendState(43,14);
      token = 42; /* fall-through */
    case 42:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(40);
      token = 255;
      break;
    case 43: /* Module M_14 */
      stateManager.addPendingState(43);
      rotateDirTo(216,TRUE);
      token = 44;
      break;
    case 44:
      stateManager.sendState(46,15);
      token = 45; /* fall-through */
    case 45:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(43);
      token = 255;
      break;
    case 46: /* Module M_15 */
      stateManager.addPendingState(46);
      rotateDirTo(216,TRUE);
      token = 47;
      break;
    case 47:
      stateManager.sendState(49,16);
      token = 48; /* fall-through */
    case 48:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(46);
      token = 255;
      break;
    case 49: /* Module M_16 */
      stateManager.addPendingState(49);
      rotateDirTo(216,TRUE);
      token = 50;
      break;
    case 50:
      stateManager.sendState(52,17);
      token = 51; /* fall-through */
    case 51:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(49);
      token = 255;
      break;
    case 52: /* Module M_17 */
      stateManager.addPendingState(52);
      rotateDirTo(216,TRUE);
      token = 53;
      break;
    case 53:
      stateManager.sendState(55,18);
      token = 54; /* fall-through */
    case 54:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(52);
      token = 255;
      break;
    case 55: /* Module M_18 */
      stateManager.addPendingState(55);
      rotateDirTo(216,TRUE);
      token = 56;
      break;
    case 56:
      stateManager.sendState(58,19);
      token = 57; /* fall-through */
    case 57:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(55);
      token = 255;
      break;
    case 58: /* Module M_19 */
      rotateDirTo(216,TRUE);
      token = 59;
      break;
    case 59:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(216)) break;
      stateManager.sendState(60,0);
      token = 255;
      break;
    case 60: /* Module M__0 */
      stateManager.addPendingState(60);
      rotateDirTo(0,TRUE);
      token = 61;
      break;
    case 61:
      stateManager.sendState(63,1);
      token = 62; /* fall-through */
    case 62:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(60);
      token = 255;
      break;
    case 63: /* Module M__1 */
      stateManager.addPendingState(63);
      rotateDirTo(0,TRUE);
      token = 64;
      break;
    case 64:
      stateManager.sendState(66,2);
      token = 65; /* fall-through */
    case 65:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(63);
      token = 255;
      break;
    case 66: /* Module M__2 */
      stateManager.addPendingState(66);
      rotateDirTo(0,TRUE);
      token = 67;
      break;
    case 67:
      stateManager.sendState(69,3);
      token = 68; /* fall-through */
    case 68:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(66);
      token = 255;
      break;
    case 69: /* Module M__3 */
      stateManager.addPendingState(69);
      rotateDirTo(0,TRUE);
      token = 70;
      break;
    case 70:
      stateManager.sendState(72,4);
      token = 71; /* fall-through */
    case 71:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(69);
      token = 255;
      break;
    case 72: /* Module M__4 */
      stateManager.addPendingState(72);
      rotateDirTo(0,TRUE);
      token = 73;
      break;
    case 73:
      stateManager.sendState(75,5);
      token = 74; /* fall-through */
    case 74:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(72);
      token = 255;
      break;
    case 75: /* Module M__5 */
      stateManager.addPendingState(75);
      rotateDirTo(0,TRUE);
      token = 76;
      break;
    case 76:
      stateManager.sendState(78,6);
      token = 77; /* fall-through */
    case 77:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(75);
      token = 255;
      break;
    case 78: /* Module M__6 */
      stateManager.addPendingState(78);
      rotateDirTo(0,TRUE);
      token = 79;
      break;
    case 79:
      stateManager.sendState(81,7);
      token = 80; /* fall-through */
    case 80:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(78);
      token = 255;
      break;
    case 81: /* Module M__7 */
      stateManager.addPendingState(81);
      rotateDirTo(0,TRUE);
      token = 82;
      break;
    case 82:
      stateManager.sendState(84,8);
      token = 83; /* fall-through */
    case 83:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(81);
      token = 255;
      break;
    case 84: /* Module M__8 */
      stateManager.addPendingState(84);
      rotateDirTo(0,TRUE);
      token = 85;
      break;
    case 85:
      stateManager.sendState(87,9);
      token = 86; /* fall-through */
    case 86:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(84);
      token = 255;
      break;
    case 87: /* Module M__9 */
      stateManager.addPendingState(87);
      rotateDirTo(0,TRUE);
      token = 88;
      break;
    case 88:
      stateManager.sendState(90,10);
      token = 89; /* fall-through */
    case 89:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(87);
      token = 255;
      break;
    case 90: /* Module M_10 */
      stateManager.addPendingState(90);
      rotateDirTo(0,TRUE);
      token = 91;
      break;
    case 91:
      stateManager.sendState(93,11);
      token = 92; /* fall-through */
    case 92:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(90);
      token = 255;
      break;
    case 93: /* Module M_11 */
      stateManager.addPendingState(93);
      rotateDirTo(0,TRUE);
      token = 94;
      break;
    case 94:
      stateManager.sendState(96,12);
      token = 95; /* fall-through */
    case 95:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(93);
      token = 255;
      break;
    case 96: /* Module M_12 */
      stateManager.addPendingState(96);
      rotateDirTo(0,TRUE);
      token = 97;
      break;
    case 97:
      stateManager.sendState(99,13);
      token = 98; /* fall-through */
    case 98:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(96);
      token = 255;
      break;
    case 99: /* Module M_13 */
      stateManager.addPendingState(99);
      rotateDirTo(0,TRUE);
      token = 100;
      break;
    case 100:
      stateManager.sendState(102,14);
      token = 101; /* fall-through */
    case 101:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(99);
      token = 255;
      break;
    case 102: /* Module M_14 */
      stateManager.addPendingState(102);
      rotateDirTo(0,TRUE);
      token = 103;
      break;
    case 103:
      stateManager.sendState(105,15);
      token = 104; /* fall-through */
    case 104:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(102);
      token = 255;
      break;
    case 105: /* Module M_15 */
      stateManager.addPendingState(105);
      rotateDirTo(0,TRUE);
      token = 106;
      break;
    case 106:
      stateManager.sendState(108,16);
      token = 107; /* fall-through */
    case 107:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(105);
      token = 255;
      break;
    case 108: /* Module M_16 */
      stateManager.addPendingState(108);
      rotateDirTo(0,TRUE);
      token = 109;
      break;
    case 109:
      stateManager.sendState(111,17);
      token = 110; /* fall-through */
    case 110:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(108);
      token = 255;
      break;
    case 111: /* Module M_17 */
      stateManager.addPendingState(111);
      rotateDirTo(0,TRUE);
      token = 112;
      break;
    case 112:
      stateManager.sendState(114,18);
      token = 113; /* fall-through */
    case 113:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(111);
      token = 255;
      break;
    case 114: /* Module M_18 */
      stateManager.addPendingState(114);
      rotateDirTo(0,TRUE);
      token = 115;
      break;
    case 115:
      stateManager.sendState(117,19);
      token = 116; /* fall-through */
    case 116:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(114);
      token = 255;
      break;
    case 117: /* Module M_19 */
      rotateDirTo(0,TRUE);
      token = 118;
      break;
    case 118:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(119,0);
      token = 255;
      break;
    case 119: /* Module M__0 */
      stateManager.addPendingState(119);
      rotateDirTo(216,TRUE);
      token = 120;
      break;
    case 120:
      stateManager.sendState(122,1);
      token = 121; /* fall-through */
    case 121:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(119);
      token = 255;
      break;
    case 122: /* Module M__1 */
      stateManager.addPendingState(122);
      rotateDirTo(216,TRUE);
      token = 123;
      break;
    case 123:
      stateManager.sendState(125,2);
      token = 124; /* fall-through */
    case 124:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(122);
      token = 255;
      break;
    case 125: /* Module M__2 */
      stateManager.addPendingState(125);
      rotateDirTo(216,TRUE);
      token = 126;
      break;
    case 126:
      stateManager.sendState(128,3);
      token = 127; /* fall-through */
    case 127:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(125);
      token = 255;
      break;
    case 128: /* Module M__3 */
      stateManager.addPendingState(128);
      rotateDirTo(216,TRUE);
      token = 129;
      break;
    case 129:
      stateManager.sendState(131,4);
      token = 130; /* fall-through */
    case 130:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(128);
      token = 255;
      break;
    case 131: /* Module M__4 */
      stateManager.addPendingState(131);
      rotateDirTo(216,TRUE);
      token = 132;
      break;
    case 132:
      stateManager.sendState(134,5);
      token = 133; /* fall-through */
    case 133:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(131);
      token = 255;
      break;
    case 134: /* Module M__5 */
      stateManager.addPendingState(134);
      rotateDirTo(216,TRUE);
      token = 135;
      break;
    case 135:
      stateManager.sendState(137,6);
      token = 136; /* fall-through */
    case 136:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(134);
      token = 255;
      break;
    case 137: /* Module M__6 */
      stateManager.addPendingState(137);
      rotateDirTo(216,TRUE);
      token = 138;
      break;
    case 138:
      stateManager.sendState(140,7);
      token = 139; /* fall-through */
    case 139:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(137);
      token = 255;
      break;
    case 140: /* Module M__7 */
      stateManager.addPendingState(140);
      rotateDirTo(216,TRUE);
      token = 141;
      break;
    case 141:
      stateManager.sendState(143,8);
      token = 142; /* fall-through */
    case 142:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(140);
      token = 255;
      break;
    case 143: /* Module M__8 */
      stateManager.addPendingState(143);
      rotateDirTo(216,TRUE);
      token = 144;
      break;
    case 144:
      stateManager.sendState(146,9);
      token = 145; /* fall-through */
    case 145:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(143);
      token = 255;
      break;
    case 146: /* Module M__9 */
      stateManager.addPendingState(146);
      rotateDirTo(216,TRUE);
      token = 147;
      break;
    case 147:
      stateManager.sendState(149,10);
      token = 148; /* fall-through */
    case 148:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(146);
      token = 255;
      break;
    case 149: /* Module M_10 */
      stateManager.addPendingState(149);
      rotateDirTo(216,TRUE);
      token = 150;
      break;
    case 150:
      stateManager.sendState(152,11);
      token = 151; /* fall-through */
    case 151:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(149);
      token = 255;
      break;
    case 152: /* Module M_11 */
      stateManager.addPendingState(152);
      rotateDirTo(216,TRUE);
      token = 153;
      break;
    case 153:
      stateManager.sendState(155,12);
      token = 154; /* fall-through */
    case 154:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(152);
      token = 255;
      break;
    case 155: /* Module M_12 */
      stateManager.addPendingState(155);
      rotateDirTo(216,TRUE);
      token = 156;
      break;
    case 156:
      stateManager.sendState(158,13);
      token = 157; /* fall-through */
    case 157:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(155);
      token = 255;
      break;
    case 158: /* Module M_13 */
      stateManager.addPendingState(158);
      rotateDirTo(216,TRUE);
      token = 159;
      break;
    case 159:
      stateManager.sendState(161,14);
      token = 160; /* fall-through */
    case 160:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(158);
      token = 255;
      break;
    case 161: /* Module M_14 */
      stateManager.addPendingState(161);
      rotateDirTo(216,TRUE);
      token = 162;
      break;
    case 162:
      stateManager.sendState(164,15);
      token = 163; /* fall-through */
    case 163:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(161);
      token = 255;
      break;
    case 164: /* Module M_15 */
      stateManager.addPendingState(164);
      rotateDirTo(216,TRUE);
      token = 165;
      break;
    case 165:
      stateManager.sendState(167,16);
      token = 166; /* fall-through */
    case 166:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(164);
      token = 255;
      break;
    case 167: /* Module M_16 */
      stateManager.addPendingState(167);
      rotateDirTo(216,TRUE);
      token = 168;
      break;
    case 168:
      stateManager.sendState(170,17);
      token = 169; /* fall-through */
    case 169:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(167);
      token = 255;
      break;
    case 170: /* Module M_17 */
      stateManager.addPendingState(170);
      rotateDirTo(216,TRUE);
      token = 171;
      break;
    case 171:
      stateManager.sendState(173,18);
      token = 172; /* fall-through */
    case 172:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(170);
      token = 255;
      break;
    case 173: /* Module M_18 */
      stateManager.addPendingState(173);
      rotateDirTo(216,TRUE);
      token = 174;
      break;
    case 174:
      stateManager.sendState(176,19);
      token = 175; /* fall-through */
    case 175:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(173);
      token = 255;
      break;
    case 176: /* Module M_19 */
      rotateDirTo(216,TRUE);
      token = 177;
      break;
    case 177:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(216)) break;
      stateManager.sendState(178,0);
      token = 255;
      break;
    case 178: /* Module M__0 */
      stateManager.addPendingState(178);
      rotateDirTo(0,TRUE);
      token = 179;
      break;
    case 179:
      stateManager.sendState(181,1);
      token = 180; /* fall-through */
    case 180:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(178);
      token = 255;
      break;
    case 181: /* Module M__1 */
      stateManager.addPendingState(181);
      rotateDirTo(0,TRUE);
      token = 182;
      break;
    case 182:
      stateManager.sendState(184,2);
      token = 183; /* fall-through */
    case 183:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(181);
      token = 255;
      break;
    case 184: /* Module M__2 */
      stateManager.addPendingState(184);
      rotateDirTo(0,TRUE);
      token = 185;
      break;
    case 185:
      stateManager.sendState(187,3);
      token = 186; /* fall-through */
    case 186:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(184);
      token = 255;
      break;
    case 187: /* Module M__3 */
      stateManager.addPendingState(187);
      rotateDirTo(0,TRUE);
      token = 188;
      break;
    case 188:
      stateManager.sendState(190,4);
      token = 189; /* fall-through */
    case 189:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(187);
      token = 255;
      break;
    case 190: /* Module M__4 */
      stateManager.addPendingState(190);
      rotateDirTo(0,TRUE);
      token = 191;
      break;
    case 191:
      stateManager.sendState(193,5);
      token = 192; /* fall-through */
    case 192:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(190);
      token = 255;
      break;
    case 193: /* Module M__5 */
      stateManager.addPendingState(193);
      rotateDirTo(0,TRUE);
      token = 194;
      break;
    case 194:
      stateManager.sendState(196,6);
      token = 195; /* fall-through */
    case 195:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(193);
      token = 255;
      break;
    case 196: /* Module M__6 */
      stateManager.addPendingState(196);
      rotateDirTo(0,TRUE);
      token = 197;
      break;
    case 197:
      stateManager.sendState(199,7);
      token = 198; /* fall-through */
    case 198:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(196);
      token = 255;
      break;
    case 199: /* Module M__7 */
      stateManager.addPendingState(199);
      rotateDirTo(0,TRUE);
      token = 200;
      break;
    case 200:
      stateManager.sendState(202,8);
      token = 201; /* fall-through */
    case 201:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(199);
      token = 255;
      break;
    case 202: /* Module M__8 */
      stateManager.addPendingState(202);
      rotateDirTo(0,TRUE);
      token = 203;
      break;
    case 203:
      stateManager.sendState(205,9);
      token = 204; /* fall-through */
    case 204:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(202);
      token = 255;
      break;
    case 205: /* Module M__9 */
      stateManager.addPendingState(205);
      rotateDirTo(0,TRUE);
      token = 206;
      break;
    case 206:
      stateManager.sendState(208,10);
      token = 207; /* fall-through */
    case 207:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(205);
      token = 255;
      break;
    case 208: /* Module M_10 */
      stateManager.addPendingState(208);
      rotateDirTo(0,TRUE);
      token = 209;
      break;
    case 209:
      stateManager.sendState(211,11);
      token = 210; /* fall-through */
    case 210:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(208);
      token = 255;
      break;
    case 211: /* Module M_11 */
      stateManager.addPendingState(211);
      rotateDirTo(0,TRUE);
      token = 212;
      break;
    case 212:
      stateManager.sendState(214,12);
      token = 213; /* fall-through */
    case 213:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(211);
      token = 255;
      break;
    case 214: /* Module M_12 */
      stateManager.addPendingState(214);
      rotateDirTo(0,TRUE);
      token = 215;
      break;
    case 215:
      stateManager.sendState(217,13);
      token = 216; /* fall-through */
    case 216:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(214);
      token = 255;
      break;
    case 217: /* Module M_13 */
      stateManager.addPendingState(217);
      rotateDirTo(0,TRUE);
      token = 218;
      break;
    case 218:
      stateManager.sendState(220,14);
      token = 219; /* fall-through */
    case 219:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(217);
      token = 255;
      break;
    case 220: /* Module M_14 */
      stateManager.addPendingState(220);
      rotateDirTo(0,TRUE);
      token = 221;
      break;
    case 221:
      stateManager.sendState(223,15);
      token = 222; /* fall-through */
    case 222:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(220);
      token = 255;
      break;
    case 223: /* Module M_15 */
      stateManager.addPendingState(223);
      rotateDirTo(0,TRUE);
      token = 224;
      break;
    case 224:
      stateManager.sendState(226,16);
      token = 225; /* fall-through */
    case 225:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(223);
      token = 255;
      break;
    case 226: /* Module M_16 */
      stateManager.addPendingState(226);
      rotateDirTo(0,TRUE);
      token = 227;
      break;
    case 227:
      stateManager.sendState(229,17);
      token = 228; /* fall-through */
    case 228:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(226);
      token = 255;
      break;
    case 229: /* Module M_17 */
      stateManager.addPendingState(229);
      rotateDirTo(0,TRUE);
      token = 230;
      break;
    case 230:
      stateManager.sendState(232,18);
      token = 231; /* fall-through */
    case 231:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(229);
      token = 255;
      break;
    case 232: /* Module M_18 */
      stateManager.addPendingState(232);
      rotateDirTo(0,TRUE);
      token = 233;
      break;
    case 233:
      stateManager.sendState(235,19);
      token = 234; /* fall-through */
    case 234:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(232);
      token = 255;
      break;
    case 235: /* Module M_19 */
      rotateDirTo(0,TRUE);
      token = 236;
      break;
    case 236:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;

	
	stateManager.sendState(254,-1);
	token=255;
	break;
	
	case 255:
		if(stateManager.getGlobalState()==254) {
			if(!isDone) {
				System.out.println(myID+": i am done...");
				api.reportResult(true);
			}
			isDone = true;
		}
		break;
	}
}

  public void init(int self_id) {
    int address;
    if((self_id==0)) address = 0;
    else if((self_id==1)) address = 1;
    else if((self_id==2)) address = 2;
    else if((self_id==3)) address = 3;
    else if((self_id==4)) address = 4;
    else if((self_id==5)) address = 5;
    else if((self_id==6)) address = 6;
    else if((self_id==7)) address = 7;
    else if((self_id==8)) address = 8;
    else if((self_id==9)) address = 9;
    else if((self_id==10)) address = 10;
    else if((self_id==11)) address = 11;
    else if((self_id==12)) address = 12;
    else if((self_id==13)) address = 13;
    else if((self_id==14)) address = 14;
    else if((self_id==15)) address = 15;
    else if((self_id==16)) address = 16;
    else if((self_id==17)) address = 17;
    else if((self_id==18)) address = 18;
    else if((self_id==19)) address = 19;

    else address = 127;
	myID = address;
	token = 255;
	
    api.setLeds(myID);
	reset_state();
	stateManager.init(myID,0);
	isDone = false;
}

  public void reset_sequence() {
    stateManager.reset_sequence();
}

  public void reset_state() {
    stateManager.reset_state();
}

  public boolean checkPendingStateResponsibility(int address, int pendingState) {
    if(address==7) {
      if(pendingState==81) return true;
      if(pendingState==140) return true;
      if(pendingState==22) return true;
      if(pendingState==199) return true;
      return false;
    }
    if(address==8) {
      if(pendingState==84) return true;
      if(pendingState==143) return true;
      if(pendingState==202) return true;
      if(pendingState==25) return true;
      return false;
    }
    if(address==5) {
      if(pendingState==16) return true;
      if(pendingState==193) return true;
      if(pendingState==134) return true;
      if(pendingState==75) return true;
      return false;
    }
    if(address==6) {
      if(pendingState==137) return true;
      if(pendingState==19) return true;
      if(pendingState==196) return true;
      if(pendingState==78) return true;
      return false;
    }
    if(address==9) {
      if(pendingState==205) return true;
      if(pendingState==87) return true;
      if(pendingState==146) return true;
      if(pendingState==28) return true;
      return false;
    }
    if(address==0) {
      if(pendingState==119) return true;
      if(pendingState==1) return true;
      if(pendingState==178) return true;
      if(pendingState==60) return true;
      return false;
    }
    if(address==3) {
      if(pendingState==187) return true;
      if(pendingState==69) return true;
      if(pendingState==128) return true;
      if(pendingState==10) return true;
      return false;
    }
    if(address==4) {
      if(pendingState==190) return true;
      if(pendingState==131) return true;
      if(pendingState==72) return true;
      if(pendingState==13) return true;
      return false;
    }
    if(address==1) {
      if(pendingState==4) return true;
      if(pendingState==63) return true;
      if(pendingState==122) return true;
      if(pendingState==181) return true;
      return false;
    }
    if(address==2) {
      if(pendingState==184) return true;
      if(pendingState==66) return true;
      if(pendingState==7) return true;
      if(pendingState==125) return true;
      return false;
    }
    if(address==16) {
      if(pendingState==49) return true;
      if(pendingState==108) return true;
      if(pendingState==167) return true;
      if(pendingState==226) return true;
      return false;
    }
    if(address==15) {
      if(pendingState==223) return true;
      if(pendingState==46) return true;
      if(pendingState==164) return true;
      if(pendingState==105) return true;
      return false;
    }
    if(address==14) {
      if(pendingState==220) return true;
      if(pendingState==102) return true;
      if(pendingState==43) return true;
      if(pendingState==161) return true;
      return false;
    }
    if(address==13) {
      if(pendingState==217) return true;
      if(pendingState==99) return true;
      if(pendingState==158) return true;
      if(pendingState==40) return true;
      return false;
    }
    if(address==12) {
      if(pendingState==155) return true;
      if(pendingState==96) return true;
      if(pendingState==37) return true;
      if(pendingState==214) return true;
      return false;
    }
    if(address==11) {
      if(pendingState==152) return true;
      if(pendingState==34) return true;
      if(pendingState==93) return true;
      if(pendingState==211) return true;
      return false;
    }
    if(address==10) {
      if(pendingState==208) return true;
      if(pendingState==149) return true;
      if(pendingState==31) return true;
      if(pendingState==90) return true;
      return false;
    }
    if(address==18) {
      if(pendingState==55) return true;
      if(pendingState==114) return true;
      if(pendingState==173) return true;
      if(pendingState==232) return true;
      return false;
    }
    if(address==17) {
      if(pendingState==170) return true;
      if(pendingState==52) return true;
      if(pendingState==111) return true;
      if(pendingState==229) return true;
      return false;
    }
    return false;
  }

  int getLastState(int address) {
    if(address==0) return 178;
    if(address==1) return 181;
    if(address==2) return 184;
    if(address==3) return 187;
    if(address==4) return 190;
    if(address==5) return 193;
    if(address==6) return 196;
    if(address==7) return 199;
    if(address==8) return 202;
    if(address==9) return 205;
    if(address==10) return 208;
    if(address==11) return 211;
    if(address==12) return 214;
    if(address==13) return 217;
    if(address==14) return 220;
    if(address==15) return 223;
    if(address==17) return 229;
    if(address==16) return 226;
    if(address==19) return 235;
    if(address==18) return 232;
    return 255;
  }
  
  int getLastStateLowerBound(int address) {
    if(address==0) return 122;
    if(address==1) return 125;
    if(address==2) return 128;
    if(address==3) return 131;
    if(address==4) return 134;
    if(address==5) return 137;
    if(address==6) return 140;
    if(address==7) return 143;
    if(address==8) return 146;
    if(address==9) return 149;
    if(address==10) return 152;
    if(address==11) return 155;
    if(address==12) return 158;
    if(address==13) return 161;
    if(address==14) return 164;
    if(address==15) return 167;
    if(address==17) return 173;
    if(address==16) return 170;
    if(address==19) return 178;
    if(address==18) return 176;
    return 255;
  }


}
/* DynaRole Java code generated for parallelRotate */
/* DynaRole Java statemachine generated for parallelRotate */
package robustReversible.gen;

import robustReversible.*;

public class rotateGen20p extends StateMachine {

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
      stateManager.addPendingState(58);
      rotateDirTo(216,TRUE);
      token = 59;
      break;
    case 59:
      stateManager.sendState(61,0);
      token = 60; /* fall-through */
    case 60:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(58);
      token = 255;
      break;
    case 61: /* Module M__0 */
      stateManager.addPendingState(61);
      rotateDirTo(0,TRUE);
      token = 62;
      break;
    case 62:
      stateManager.sendState(64,1);
      token = 63; /* fall-through */
    case 63:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(61);
      token = 255;
      break;
    case 64: /* Module M__1 */
      stateManager.addPendingState(64);
      rotateDirTo(0,TRUE);
      token = 65;
      break;
    case 65:
      stateManager.sendState(67,2);
      token = 66; /* fall-through */
    case 66:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(64);
      token = 255;
      break;
    case 67: /* Module M__2 */
      stateManager.addPendingState(67);
      rotateDirTo(0,TRUE);
      token = 68;
      break;
    case 68:
      stateManager.sendState(70,3);
      token = 69; /* fall-through */
    case 69:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(67);
      token = 255;
      break;
    case 70: /* Module M__3 */
      stateManager.addPendingState(70);
      rotateDirTo(0,TRUE);
      token = 71;
      break;
    case 71:
      stateManager.sendState(73,4);
      token = 72; /* fall-through */
    case 72:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(70);
      token = 255;
      break;
    case 73: /* Module M__4 */
      stateManager.addPendingState(73);
      rotateDirTo(0,TRUE);
      token = 74;
      break;
    case 74:
      stateManager.sendState(76,5);
      token = 75; /* fall-through */
    case 75:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(73);
      token = 255;
      break;
    case 76: /* Module M__5 */
      stateManager.addPendingState(76);
      rotateDirTo(0,TRUE);
      token = 77;
      break;
    case 77:
      stateManager.sendState(79,6);
      token = 78; /* fall-through */
    case 78:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(76);
      token = 255;
      break;
    case 79: /* Module M__6 */
      stateManager.addPendingState(79);
      rotateDirTo(0,TRUE);
      token = 80;
      break;
    case 80:
      stateManager.sendState(82,7);
      token = 81; /* fall-through */
    case 81:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(79);
      token = 255;
      break;
    case 82: /* Module M__7 */
      stateManager.addPendingState(82);
      rotateDirTo(0,TRUE);
      token = 83;
      break;
    case 83:
      stateManager.sendState(85,8);
      token = 84; /* fall-through */
    case 84:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(82);
      token = 255;
      break;
    case 85: /* Module M__8 */
      stateManager.addPendingState(85);
      rotateDirTo(0,TRUE);
      token = 86;
      break;
    case 86:
      stateManager.sendState(88,9);
      token = 87; /* fall-through */
    case 87:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(85);
      token = 255;
      break;
    case 88: /* Module M__9 */
      stateManager.addPendingState(88);
      rotateDirTo(0,TRUE);
      token = 89;
      break;
    case 89:
      stateManager.sendState(91,10);
      token = 90; /* fall-through */
    case 90:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(88);
      token = 255;
      break;
    case 91: /* Module M_10 */
      stateManager.addPendingState(91);
      rotateDirTo(0,TRUE);
      token = 92;
      break;
    case 92:
      stateManager.sendState(94,11);
      token = 93; /* fall-through */
    case 93:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(91);
      token = 255;
      break;
    case 94: /* Module M_11 */
      stateManager.addPendingState(94);
      rotateDirTo(0,TRUE);
      token = 95;
      break;
    case 95:
      stateManager.sendState(97,12);
      token = 96; /* fall-through */
    case 96:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(94);
      token = 255;
      break;
    case 97: /* Module M_12 */
      stateManager.addPendingState(97);
      rotateDirTo(0,TRUE);
      token = 98;
      break;
    case 98:
      stateManager.sendState(100,13);
      token = 99; /* fall-through */
    case 99:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(97);
      token = 255;
      break;
    case 100: /* Module M_13 */
      stateManager.addPendingState(100);
      rotateDirTo(0,TRUE);
      token = 101;
      break;
    case 101:
      stateManager.sendState(103,14);
      token = 102; /* fall-through */
    case 102:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(100);
      token = 255;
      break;
    case 103: /* Module M_14 */
      stateManager.addPendingState(103);
      rotateDirTo(0,TRUE);
      token = 104;
      break;
    case 104:
      stateManager.sendState(106,15);
      token = 105; /* fall-through */
    case 105:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(103);
      token = 255;
      break;
    case 106: /* Module M_15 */
      stateManager.addPendingState(106);
      rotateDirTo(0,TRUE);
      token = 107;
      break;
    case 107:
      stateManager.sendState(109,16);
      token = 108; /* fall-through */
    case 108:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(106);
      token = 255;
      break;
    case 109: /* Module M_16 */
      stateManager.addPendingState(109);
      rotateDirTo(0,TRUE);
      token = 110;
      break;
    case 110:
      stateManager.sendState(112,17);
      token = 111; /* fall-through */
    case 111:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(109);
      token = 255;
      break;
    case 112: /* Module M_17 */
      stateManager.addPendingState(112);
      rotateDirTo(0,TRUE);
      token = 113;
      break;
    case 113:
      stateManager.sendState(115,18);
      token = 114; /* fall-through */
    case 114:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(112);
      token = 255;
      break;
    case 115: /* Module M_18 */
      stateManager.addPendingState(115);
      rotateDirTo(0,TRUE);
      token = 116;
      break;
    case 116:
      stateManager.sendState(118,19);
      token = 117; /* fall-through */
    case 117:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(115);
      token = 255;
      break;
    case 118: /* Module M_19 */
      rotateDirTo(0,TRUE);
      token = 119;
      break;
    case 119:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(120,0);
      token = 255;
      break;
    case 120: /* Module M__0 */
      stateManager.addPendingState(120);
      rotateDirTo(216,TRUE);
      token = 121;
      break;
    case 121:
      stateManager.sendState(123,1);
      token = 122; /* fall-through */
    case 122:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(120);
      token = 255;
      break;
    case 123: /* Module M__1 */
      stateManager.addPendingState(123);
      rotateDirTo(216,TRUE);
      token = 124;
      break;
    case 124:
      stateManager.sendState(126,2);
      token = 125; /* fall-through */
    case 125:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(123);
      token = 255;
      break;
    case 126: /* Module M__2 */
      stateManager.addPendingState(126);
      rotateDirTo(216,TRUE);
      token = 127;
      break;
    case 127:
      stateManager.sendState(129,3);
      token = 128; /* fall-through */
    case 128:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(126);
      token = 255;
      break;
    case 129: /* Module M__3 */
      stateManager.addPendingState(129);
      rotateDirTo(216,TRUE);
      token = 130;
      break;
    case 130:
      stateManager.sendState(132,4);
      token = 131; /* fall-through */
    case 131:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(129);
      token = 255;
      break;
    case 132: /* Module M__4 */
      stateManager.addPendingState(132);
      rotateDirTo(216,TRUE);
      token = 133;
      break;
    case 133:
      stateManager.sendState(135,5);
      token = 134; /* fall-through */
    case 134:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(132);
      token = 255;
      break;
    case 135: /* Module M__5 */
      stateManager.addPendingState(135);
      rotateDirTo(216,TRUE);
      token = 136;
      break;
    case 136:
      stateManager.sendState(138,6);
      token = 137; /* fall-through */
    case 137:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(135);
      token = 255;
      break;
    case 138: /* Module M__6 */
      stateManager.addPendingState(138);
      rotateDirTo(216,TRUE);
      token = 139;
      break;
    case 139:
      stateManager.sendState(141,7);
      token = 140; /* fall-through */
    case 140:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(138);
      token = 255;
      break;
    case 141: /* Module M__7 */
      stateManager.addPendingState(141);
      rotateDirTo(216,TRUE);
      token = 142;
      break;
    case 142:
      stateManager.sendState(144,8);
      token = 143; /* fall-through */
    case 143:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(141);
      token = 255;
      break;
    case 144: /* Module M__8 */
      stateManager.addPendingState(144);
      rotateDirTo(216,TRUE);
      token = 145;
      break;
    case 145:
      stateManager.sendState(147,9);
      token = 146; /* fall-through */
    case 146:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(144);
      token = 255;
      break;
    case 147: /* Module M__9 */
      stateManager.addPendingState(147);
      rotateDirTo(216,TRUE);
      token = 148;
      break;
    case 148:
      stateManager.sendState(150,10);
      token = 149; /* fall-through */
    case 149:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(147);
      token = 255;
      break;
    case 150: /* Module M_10 */
      stateManager.addPendingState(150);
      rotateDirTo(216,TRUE);
      token = 151;
      break;
    case 151:
      stateManager.sendState(153,11);
      token = 152; /* fall-through */
    case 152:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(150);
      token = 255;
      break;
    case 153: /* Module M_11 */
      stateManager.addPendingState(153);
      rotateDirTo(216,TRUE);
      token = 154;
      break;
    case 154:
      stateManager.sendState(156,12);
      token = 155; /* fall-through */
    case 155:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(153);
      token = 255;
      break;
    case 156: /* Module M_12 */
      stateManager.addPendingState(156);
      rotateDirTo(216,TRUE);
      token = 157;
      break;
    case 157:
      stateManager.sendState(159,13);
      token = 158; /* fall-through */
    case 158:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(156);
      token = 255;
      break;
    case 159: /* Module M_13 */
      stateManager.addPendingState(159);
      rotateDirTo(216,TRUE);
      token = 160;
      break;
    case 160:
      stateManager.sendState(162,14);
      token = 161; /* fall-through */
    case 161:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(159);
      token = 255;
      break;
    case 162: /* Module M_14 */
      stateManager.addPendingState(162);
      rotateDirTo(216,TRUE);
      token = 163;
      break;
    case 163:
      stateManager.sendState(165,15);
      token = 164; /* fall-through */
    case 164:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(162);
      token = 255;
      break;
    case 165: /* Module M_15 */
      stateManager.addPendingState(165);
      rotateDirTo(216,TRUE);
      token = 166;
      break;
    case 166:
      stateManager.sendState(168,16);
      token = 167; /* fall-through */
    case 167:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(165);
      token = 255;
      break;
    case 168: /* Module M_16 */
      stateManager.addPendingState(168);
      rotateDirTo(216,TRUE);
      token = 169;
      break;
    case 169:
      stateManager.sendState(171,17);
      token = 170; /* fall-through */
    case 170:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(168);
      token = 255;
      break;
    case 171: /* Module M_17 */
      stateManager.addPendingState(171);
      rotateDirTo(216,TRUE);
      token = 172;
      break;
    case 172:
      stateManager.sendState(174,18);
      token = 173; /* fall-through */
    case 173:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(171);
      token = 255;
      break;
    case 174: /* Module M_18 */
      stateManager.addPendingState(174);
      rotateDirTo(216,TRUE);
      token = 175;
      break;
    case 175:
      stateManager.sendState(177,19);
      token = 176; /* fall-through */
    case 176:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(174);
      token = 255;
      break;
    case 177: /* Module M_19 */
      stateManager.addPendingState(177);
      rotateDirTo(216,TRUE);
      token = 178;
      break;
    case 178:
      stateManager.sendState(180,0);
      token = 179; /* fall-through */
    case 179:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(177);
      token = 255;
      break;
    case 180: /* Module M__0 */
      stateManager.addPendingState(180);
      rotateDirTo(0,TRUE);
      token = 181;
      break;
    case 181:
      stateManager.sendState(183,1);
      token = 182; /* fall-through */
    case 182:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(180);
      token = 255;
      break;
    case 183: /* Module M__1 */
      stateManager.addPendingState(183);
      rotateDirTo(0,TRUE);
      token = 184;
      break;
    case 184:
      stateManager.sendState(186,2);
      token = 185; /* fall-through */
    case 185:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(183);
      token = 255;
      break;
    case 186: /* Module M__2 */
      stateManager.addPendingState(186);
      rotateDirTo(0,TRUE);
      token = 187;
      break;
    case 187:
      stateManager.sendState(189,3);
      token = 188; /* fall-through */
    case 188:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(186);
      token = 255;
      break;
    case 189: /* Module M__3 */
      stateManager.addPendingState(189);
      rotateDirTo(0,TRUE);
      token = 190;
      break;
    case 190:
      stateManager.sendState(192,4);
      token = 191; /* fall-through */
    case 191:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(189);
      token = 255;
      break;
    case 192: /* Module M__4 */
      stateManager.addPendingState(192);
      rotateDirTo(0,TRUE);
      token = 193;
      break;
    case 193:
      stateManager.sendState(195,5);
      token = 194; /* fall-through */
    case 194:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(192);
      token = 255;
      break;
    case 195: /* Module M__5 */
      stateManager.addPendingState(195);
      rotateDirTo(0,TRUE);
      token = 196;
      break;
    case 196:
      stateManager.sendState(198,6);
      token = 197; /* fall-through */
    case 197:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(195);
      token = 255;
      break;
    case 198: /* Module M__6 */
      stateManager.addPendingState(198);
      rotateDirTo(0,TRUE);
      token = 199;
      break;
    case 199:
      stateManager.sendState(201,7);
      token = 200; /* fall-through */
    case 200:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(198);
      token = 255;
      break;
    case 201: /* Module M__7 */
      stateManager.addPendingState(201);
      rotateDirTo(0,TRUE);
      token = 202;
      break;
    case 202:
      stateManager.sendState(204,8);
      token = 203; /* fall-through */
    case 203:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(201);
      token = 255;
      break;
    case 204: /* Module M__8 */
      stateManager.addPendingState(204);
      rotateDirTo(0,TRUE);
      token = 205;
      break;
    case 205:
      stateManager.sendState(207,9);
      token = 206; /* fall-through */
    case 206:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(204);
      token = 255;
      break;
    case 207: /* Module M__9 */
      stateManager.addPendingState(207);
      rotateDirTo(0,TRUE);
      token = 208;
      break;
    case 208:
      stateManager.sendState(210,10);
      token = 209; /* fall-through */
    case 209:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(207);
      token = 255;
      break;
    case 210: /* Module M_10 */
      stateManager.addPendingState(210);
      rotateDirTo(0,TRUE);
      token = 211;
      break;
    case 211:
      stateManager.sendState(213,11);
      token = 212; /* fall-through */
    case 212:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(210);
      token = 255;
      break;
    case 213: /* Module M_11 */
      stateManager.addPendingState(213);
      rotateDirTo(0,TRUE);
      token = 214;
      break;
    case 214:
      stateManager.sendState(216,12);
      token = 215; /* fall-through */
    case 215:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(213);
      token = 255;
      break;
    case 216: /* Module M_12 */
      stateManager.addPendingState(216);
      rotateDirTo(0,TRUE);
      token = 217;
      break;
    case 217:
      stateManager.sendState(219,13);
      token = 218; /* fall-through */
    case 218:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(216);
      token = 255;
      break;
    case 219: /* Module M_13 */
      stateManager.addPendingState(219);
      rotateDirTo(0,TRUE);
      token = 220;
      break;
    case 220:
      stateManager.sendState(222,14);
      token = 221; /* fall-through */
    case 221:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(219);
      token = 255;
      break;
    case 222: /* Module M_14 */
      stateManager.addPendingState(222);
      rotateDirTo(0,TRUE);
      token = 223;
      break;
    case 223:
      stateManager.sendState(225,15);
      token = 224; /* fall-through */
    case 224:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(222);
      token = 255;
      break;
    case 225: /* Module M_15 */
      stateManager.addPendingState(225);
      rotateDirTo(0,TRUE);
      token = 226;
      break;
    case 226:
      stateManager.sendState(228,16);
      token = 227; /* fall-through */
    case 227:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(225);
      token = 255;
      break;
    case 228: /* Module M_16 */
      stateManager.addPendingState(228);
      rotateDirTo(0,TRUE);
      token = 229;
      break;
    case 229:
      stateManager.sendState(231,17);
      token = 230; /* fall-through */
    case 230:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(228);
      token = 255;
      break;
    case 231: /* Module M_17 */
      stateManager.addPendingState(231);
      rotateDirTo(0,TRUE);
      token = 232;
      break;
    case 232:
      stateManager.sendState(234,18);
      token = 233; /* fall-through */
    case 233:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(231);
      token = 255;
      break;
    case 234: /* Module M_18 */
      stateManager.addPendingState(234);
      rotateDirTo(0,TRUE);
      token = 235;
      break;
    case 235:
      stateManager.sendState(237,19);
      token = 236; /* fall-through */
    case 236:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(234);
      token = 255;
      break;
    case 237: /* Module M_19 */
      rotateDirTo(0,TRUE);
      token = 238;
      break;
    case 238:
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
	stateManager.init(myID);
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
      if(pendingState==201) return true;
      if(pendingState==141) return true;
      if(pendingState==82) return true;
      if(pendingState==22) return true;
      return false;
    }
    if(address==8) {
      if(pendingState==85) return true;
      if(pendingState==204) return true;
      if(pendingState==144) return true;
      if(pendingState==25) return true;
      return false;
    }
    if(address==5) {
      if(pendingState==16) return true;
      if(pendingState==76) return true;
      if(pendingState==195) return true;
      if(pendingState==135) return true;
      return false;
    }
    if(address==6) {
      if(pendingState==19) return true;
      if(pendingState==138) return true;
      if(pendingState==198) return true;
      if(pendingState==79) return true;
      return false;
    }
    if(address==9) {
      if(pendingState==207) return true;
      if(pendingState==147) return true;
      if(pendingState==88) return true;
      if(pendingState==28) return true;
      return false;
    }
    if(address==0) {
      if(pendingState==1) return true;
      if(pendingState==180) return true;
      if(pendingState==61) return true;
      if(pendingState==120) return true;
      return false;
    }
    if(address==3) {
      if(pendingState==70) return true;
      if(pendingState==189) return true;
      if(pendingState==129) return true;
      if(pendingState==10) return true;
      return false;
    }
    if(address==4) {
      if(pendingState==192) return true;
      if(pendingState==132) return true;
      if(pendingState==73) return true;
      if(pendingState==13) return true;
      return false;
    }
    if(address==1) {
      if(pendingState==64) return true;
      if(pendingState==4) return true;
      if(pendingState==123) return true;
      if(pendingState==183) return true;
      return false;
    }
    if(address==2) {
      if(pendingState==186) return true;
      if(pendingState==67) return true;
      if(pendingState==7) return true;
      if(pendingState==126) return true;
      return false;
    }
    if(address==16) {
      if(pendingState==49) return true;
      if(pendingState==168) return true;
      if(pendingState==228) return true;
      if(pendingState==109) return true;
      return false;
    }
    if(address==15) {
      if(pendingState==106) return true;
      if(pendingState==46) return true;
      if(pendingState==225) return true;
      if(pendingState==165) return true;
      return false;
    }
    if(address==14) {
      if(pendingState==103) return true;
      if(pendingState==222) return true;
      if(pendingState==162) return true;
      if(pendingState==43) return true;
      return false;
    }
    if(address==13) {
      if(pendingState==100) return true;
      if(pendingState==219) return true;
      if(pendingState==159) return true;
      if(pendingState==40) return true;
      return false;
    }
    if(address==12) {
      if(pendingState==216) return true;
      if(pendingState==156) return true;
      if(pendingState==97) return true;
      if(pendingState==37) return true;
      return false;
    }
    if(address==11) {
      if(pendingState==34) return true;
      if(pendingState==153) return true;
      if(pendingState==213) return true;
      if(pendingState==94) return true;
      return false;
    }
    if(address==10) {
      if(pendingState==210) return true;
      if(pendingState==150) return true;
      if(pendingState==91) return true;
      if(pendingState==31) return true;
      return false;
    }
    if(address==19) {
      if(pendingState==58) return true;
      if(pendingState==177) return true;
      return false;
    }
    if(address==18) {
      if(pendingState==115) return true;
      if(pendingState==55) return true;
      if(pendingState==234) return true;
      if(pendingState==174) return true;
      return false;
    }
    if(address==17) {
      if(pendingState==171) return true;
      if(pendingState==112) return true;
      if(pendingState==52) return true;
      if(pendingState==231) return true;
      return false;
    }
    return false;
  }

  int getLastState(int address) {
    if(address==0) return 180;
    if(address==1) return 183;
    if(address==2) return 186;
    if(address==3) return 189;
    if(address==4) return 192;
    if(address==5) return 195;
    if(address==6) return 198;
    if(address==7) return 201;
    if(address==8) return 204;
    if(address==9) return 207;
    if(address==10) return 210;
    if(address==11) return 213;
    if(address==12) return 216;
    if(address==13) return 219;
    if(address==14) return 222;
    if(address==15) return 225;
    if(address==17) return 231;
    if(address==16) return 228;
    if(address==19) return 237;
    if(address==18) return 234;
    return 255;
  }
  
  int getLastStateLowerBound(int address) {
    if(address==0) return 123;
    if(address==1) return 126;
    if(address==2) return 129;
    if(address==3) return 132;
    if(address==4) return 135;
    if(address==5) return 138;
    if(address==6) return 141;
    if(address==7) return 144;
    if(address==8) return 147;
    if(address==9) return 150;
    if(address==10) return 153;
    if(address==11) return 156;
    if(address==12) return 159;
    if(address==13) return 162;
    if(address==14) return 165;
    if(address==15) return 168;
    if(address==17) return 174;
    if(address==16) return 171;
    if(address==19) return 180;
    if(address==18) return 177;
    return 255;
  }


}
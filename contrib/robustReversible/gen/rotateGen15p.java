/* DynaRole Java code generated for parallelRotate */
/* DynaRole Java statemachine generated for parallelRotate */
package robustReversible.gen;

import robustReversible.*;

public class rotateGen15p extends StateMachine {

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
      stateManager.sendState(46,0);
      token = 45; /* fall-through */
    case 45:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(43);
      token = 255;
      break;
    case 46: /* Module M__0 */
      stateManager.addPendingState(46);
      rotateDirTo(0,TRUE);
      token = 47;
      break;
    case 47:
      stateManager.sendState(49,1);
      token = 48; /* fall-through */
    case 48:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(46);
      token = 255;
      break;
    case 49: /* Module M__1 */
      stateManager.addPendingState(49);
      rotateDirTo(0,TRUE);
      token = 50;
      break;
    case 50:
      stateManager.sendState(52,2);
      token = 51; /* fall-through */
    case 51:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(49);
      token = 255;
      break;
    case 52: /* Module M__2 */
      stateManager.addPendingState(52);
      rotateDirTo(0,TRUE);
      token = 53;
      break;
    case 53:
      stateManager.sendState(55,3);
      token = 54; /* fall-through */
    case 54:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(52);
      token = 255;
      break;
    case 55: /* Module M__3 */
      stateManager.addPendingState(55);
      rotateDirTo(0,TRUE);
      token = 56;
      break;
    case 56:
      stateManager.sendState(58,4);
      token = 57; /* fall-through */
    case 57:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(55);
      token = 255;
      break;
    case 58: /* Module M__4 */
      stateManager.addPendingState(58);
      rotateDirTo(0,TRUE);
      token = 59;
      break;
    case 59:
      stateManager.sendState(61,5);
      token = 60; /* fall-through */
    case 60:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(58);
      token = 255;
      break;
    case 61: /* Module M__5 */
      stateManager.addPendingState(61);
      rotateDirTo(0,TRUE);
      token = 62;
      break;
    case 62:
      stateManager.sendState(64,6);
      token = 63; /* fall-through */
    case 63:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(61);
      token = 255;
      break;
    case 64: /* Module M__6 */
      stateManager.addPendingState(64);
      rotateDirTo(0,TRUE);
      token = 65;
      break;
    case 65:
      stateManager.sendState(67,7);
      token = 66; /* fall-through */
    case 66:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(64);
      token = 255;
      break;
    case 67: /* Module M__7 */
      stateManager.addPendingState(67);
      rotateDirTo(0,TRUE);
      token = 68;
      break;
    case 68:
      stateManager.sendState(70,8);
      token = 69; /* fall-through */
    case 69:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(67);
      token = 255;
      break;
    case 70: /* Module M__8 */
      stateManager.addPendingState(70);
      rotateDirTo(0,TRUE);
      token = 71;
      break;
    case 71:
      stateManager.sendState(73,9);
      token = 72; /* fall-through */
    case 72:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(70);
      token = 255;
      break;
    case 73: /* Module M__9 */
      stateManager.addPendingState(73);
      rotateDirTo(0,TRUE);
      token = 74;
      break;
    case 74:
      stateManager.sendState(76,10);
      token = 75; /* fall-through */
    case 75:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(73);
      token = 255;
      break;
    case 76: /* Module M_10 */
      stateManager.addPendingState(76);
      rotateDirTo(0,TRUE);
      token = 77;
      break;
    case 77:
      stateManager.sendState(79,11);
      token = 78; /* fall-through */
    case 78:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(76);
      token = 255;
      break;
    case 79: /* Module M_11 */
      stateManager.addPendingState(79);
      rotateDirTo(0,TRUE);
      token = 80;
      break;
    case 80:
      stateManager.sendState(82,12);
      token = 81; /* fall-through */
    case 81:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(79);
      token = 255;
      break;
    case 82: /* Module M_12 */
      stateManager.addPendingState(82);
      rotateDirTo(0,TRUE);
      token = 83;
      break;
    case 83:
      stateManager.sendState(85,13);
      token = 84; /* fall-through */
    case 84:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(82);
      token = 255;
      break;
    case 85: /* Module M_13 */
      stateManager.addPendingState(85);
      rotateDirTo(0,TRUE);
      token = 86;
      break;
    case 86:
      stateManager.sendState(88,14);
      token = 87; /* fall-through */
    case 87:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(85);
      token = 255;
      break;
    case 88: /* Module M_14 */
      rotateDirTo(0,TRUE);
      token = 89;
      break;
    case 89:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(90,0);
      token = 255;
      break;
    case 90: /* Module M__0 */
      stateManager.addPendingState(90);
      rotateDirTo(216,TRUE);
      token = 91;
      break;
    case 91:
      stateManager.sendState(93,1);
      token = 92; /* fall-through */
    case 92:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(90);
      token = 255;
      break;
    case 93: /* Module M__1 */
      stateManager.addPendingState(93);
      rotateDirTo(216,TRUE);
      token = 94;
      break;
    case 94:
      stateManager.sendState(96,2);
      token = 95; /* fall-through */
    case 95:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(93);
      token = 255;
      break;
    case 96: /* Module M__2 */
      stateManager.addPendingState(96);
      rotateDirTo(216,TRUE);
      token = 97;
      break;
    case 97:
      stateManager.sendState(99,3);
      token = 98; /* fall-through */
    case 98:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(96);
      token = 255;
      break;
    case 99: /* Module M__3 */
      stateManager.addPendingState(99);
      rotateDirTo(216,TRUE);
      token = 100;
      break;
    case 100:
      stateManager.sendState(102,4);
      token = 101; /* fall-through */
    case 101:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(99);
      token = 255;
      break;
    case 102: /* Module M__4 */
      stateManager.addPendingState(102);
      rotateDirTo(216,TRUE);
      token = 103;
      break;
    case 103:
      stateManager.sendState(105,5);
      token = 104; /* fall-through */
    case 104:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(102);
      token = 255;
      break;
    case 105: /* Module M__5 */
      stateManager.addPendingState(105);
      rotateDirTo(216,TRUE);
      token = 106;
      break;
    case 106:
      stateManager.sendState(108,6);
      token = 107; /* fall-through */
    case 107:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(105);
      token = 255;
      break;
    case 108: /* Module M__6 */
      stateManager.addPendingState(108);
      rotateDirTo(216,TRUE);
      token = 109;
      break;
    case 109:
      stateManager.sendState(111,7);
      token = 110; /* fall-through */
    case 110:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(108);
      token = 255;
      break;
    case 111: /* Module M__7 */
      stateManager.addPendingState(111);
      rotateDirTo(216,TRUE);
      token = 112;
      break;
    case 112:
      stateManager.sendState(114,8);
      token = 113; /* fall-through */
    case 113:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(111);
      token = 255;
      break;
    case 114: /* Module M__8 */
      stateManager.addPendingState(114);
      rotateDirTo(216,TRUE);
      token = 115;
      break;
    case 115:
      stateManager.sendState(117,9);
      token = 116; /* fall-through */
    case 116:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(114);
      token = 255;
      break;
    case 117: /* Module M__9 */
      stateManager.addPendingState(117);
      rotateDirTo(216,TRUE);
      token = 118;
      break;
    case 118:
      stateManager.sendState(120,10);
      token = 119; /* fall-through */
    case 119:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(117);
      token = 255;
      break;
    case 120: /* Module M_10 */
      stateManager.addPendingState(120);
      rotateDirTo(216,TRUE);
      token = 121;
      break;
    case 121:
      stateManager.sendState(123,11);
      token = 122; /* fall-through */
    case 122:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(120);
      token = 255;
      break;
    case 123: /* Module M_11 */
      stateManager.addPendingState(123);
      rotateDirTo(216,TRUE);
      token = 124;
      break;
    case 124:
      stateManager.sendState(126,12);
      token = 125; /* fall-through */
    case 125:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(123);
      token = 255;
      break;
    case 126: /* Module M_12 */
      stateManager.addPendingState(126);
      rotateDirTo(216,TRUE);
      token = 127;
      break;
    case 127:
      stateManager.sendState(129,13);
      token = 128; /* fall-through */
    case 128:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(126);
      token = 255;
      break;
    case 129: /* Module M_13 */
      stateManager.addPendingState(129);
      rotateDirTo(216,TRUE);
      token = 130;
      break;
    case 130:
      stateManager.sendState(132,14);
      token = 131; /* fall-through */
    case 131:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(129);
      token = 255;
      break;
    case 132: /* Module M_14 */
      stateManager.addPendingState(132);
      rotateDirTo(216,TRUE);
      token = 133;
      break;
    case 133:
      stateManager.sendState(135,0);
      token = 134; /* fall-through */
    case 134:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(132);
      token = 255;
      break;
    case 135: /* Module M__0 */
      stateManager.addPendingState(135);
      rotateDirTo(0,TRUE);
      token = 136;
      break;
    case 136:
      stateManager.sendState(138,1);
      token = 137; /* fall-through */
    case 137:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(135);
      token = 255;
      break;
    case 138: /* Module M__1 */
      stateManager.addPendingState(138);
      rotateDirTo(0,TRUE);
      token = 139;
      break;
    case 139:
      stateManager.sendState(141,2);
      token = 140; /* fall-through */
    case 140:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(138);
      token = 255;
      break;
    case 141: /* Module M__2 */
      stateManager.addPendingState(141);
      rotateDirTo(0,TRUE);
      token = 142;
      break;
    case 142:
      stateManager.sendState(144,3);
      token = 143; /* fall-through */
    case 143:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(141);
      token = 255;
      break;
    case 144: /* Module M__3 */
      stateManager.addPendingState(144);
      rotateDirTo(0,TRUE);
      token = 145;
      break;
    case 145:
      stateManager.sendState(147,4);
      token = 146; /* fall-through */
    case 146:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(144);
      token = 255;
      break;
    case 147: /* Module M__4 */
      stateManager.addPendingState(147);
      rotateDirTo(0,TRUE);
      token = 148;
      break;
    case 148:
      stateManager.sendState(150,5);
      token = 149; /* fall-through */
    case 149:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(147);
      token = 255;
      break;
    case 150: /* Module M__5 */
      stateManager.addPendingState(150);
      rotateDirTo(0,TRUE);
      token = 151;
      break;
    case 151:
      stateManager.sendState(153,6);
      token = 152; /* fall-through */
    case 152:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(150);
      token = 255;
      break;
    case 153: /* Module M__6 */
      stateManager.addPendingState(153);
      rotateDirTo(0,TRUE);
      token = 154;
      break;
    case 154:
      stateManager.sendState(156,7);
      token = 155; /* fall-through */
    case 155:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(153);
      token = 255;
      break;
    case 156: /* Module M__7 */
      stateManager.addPendingState(156);
      rotateDirTo(0,TRUE);
      token = 157;
      break;
    case 157:
      stateManager.sendState(159,8);
      token = 158; /* fall-through */
    case 158:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(156);
      token = 255;
      break;
    case 159: /* Module M__8 */
      stateManager.addPendingState(159);
      rotateDirTo(0,TRUE);
      token = 160;
      break;
    case 160:
      stateManager.sendState(162,9);
      token = 161; /* fall-through */
    case 161:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(159);
      token = 255;
      break;
    case 162: /* Module M__9 */
      stateManager.addPendingState(162);
      rotateDirTo(0,TRUE);
      token = 163;
      break;
    case 163:
      stateManager.sendState(165,10);
      token = 164; /* fall-through */
    case 164:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(162);
      token = 255;
      break;
    case 165: /* Module M_10 */
      stateManager.addPendingState(165);
      rotateDirTo(0,TRUE);
      token = 166;
      break;
    case 166:
      stateManager.sendState(168,11);
      token = 167; /* fall-through */
    case 167:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(165);
      token = 255;
      break;
    case 168: /* Module M_11 */
      stateManager.addPendingState(168);
      rotateDirTo(0,TRUE);
      token = 169;
      break;
    case 169:
      stateManager.sendState(171,12);
      token = 170; /* fall-through */
    case 170:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(168);
      token = 255;
      break;
    case 171: /* Module M_12 */
      stateManager.addPendingState(171);
      rotateDirTo(0,TRUE);
      token = 172;
      break;
    case 172:
      stateManager.sendState(174,13);
      token = 173; /* fall-through */
    case 173:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(171);
      token = 255;
      break;
    case 174: /* Module M_13 */
      stateManager.addPendingState(174);
      rotateDirTo(0,TRUE);
      token = 175;
      break;
    case 175:
      stateManager.sendState(177,14);
      token = 176; /* fall-through */
    case 176:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(174);
      token = 255;
      break;
    case 177: /* Module M_14 */
      rotateDirTo(0,TRUE);
      token = 178;
      break;
    case 178:
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
      if(pendingState==156) return true;
      if(pendingState==67) return true;
      if(pendingState==22) return true;
      if(pendingState==111) return true;
      return false;
    }
    if(address==8) {
      if(pendingState==70) return true;
      if(pendingState==114) return true;
      if(pendingState==159) return true;
      if(pendingState==25) return true;
      return false;
    }
    if(address==5) {
      if(pendingState==16) return true;
      if(pendingState==150) return true;
      if(pendingState==61) return true;
      if(pendingState==105) return true;
      return false;
    }
    if(address==6) {
      if(pendingState==153) return true;
      if(pendingState==19) return true;
      if(pendingState==64) return true;
      if(pendingState==108) return true;
      return false;
    }
    if(address==9) {
      if(pendingState==117) return true;
      if(pendingState==162) return true;
      if(pendingState==73) return true;
      if(pendingState==28) return true;
      return false;
    }
    if(address==0) {
      if(pendingState==1) return true;
      if(pendingState==46) return true;
      if(pendingState==135) return true;
      if(pendingState==90) return true;
      return false;
    }
    if(address==3) {
      if(pendingState==55) return true;
      if(pendingState==99) return true;
      if(pendingState==144) return true;
      if(pendingState==10) return true;
      return false;
    }
    if(address==4) {
      if(pendingState==102) return true;
      if(pendingState==58) return true;
      if(pendingState==147) return true;
      if(pendingState==13) return true;
      return false;
    }
    if(address==1) {
      if(pendingState==49) return true;
      if(pendingState==138) return true;
      if(pendingState==4) return true;
      if(pendingState==93) return true;
      return false;
    }
    if(address==2) {
      if(pendingState==141) return true;
      if(pendingState==96) return true;
      if(pendingState==52) return true;
      if(pendingState==7) return true;
      return false;
    }
    if(address==14) {
      if(pendingState==43) return true;
      if(pendingState==132) return true;
      return false;
    }
    if(address==13) {
      if(pendingState==85) return true;
      if(pendingState==174) return true;
      if(pendingState==129) return true;
      if(pendingState==40) return true;
      return false;
    }
    if(address==12) {
      if(pendingState==171) return true;
      if(pendingState==82) return true;
      if(pendingState==37) return true;
      if(pendingState==126) return true;
      return false;
    }
    if(address==11) {
      if(pendingState==34) return true;
      if(pendingState==168) return true;
      if(pendingState==79) return true;
      if(pendingState==123) return true;
      return false;
    }
    if(address==10) {
      if(pendingState==76) return true;
      if(pendingState==165) return true;
      if(pendingState==31) return true;
      if(pendingState==120) return true;
      return false;
    }
    return false;
  }

  int getLastState(int address) {
    if(address==0) return 135;
    if(address==1) return 138;
    if(address==2) return 141;
    if(address==3) return 144;
    if(address==4) return 147;
    if(address==5) return 150;
    if(address==6) return 153;
    if(address==7) return 156;
    if(address==8) return 159;
    if(address==9) return 162;
    if(address==10) return 165;
    if(address==11) return 168;
    if(address==12) return 171;
    if(address==13) return 174;
    if(address==14) return 177;
    return 255;
  }
  
  int getLastStateLowerBound(int address) {
    if(address==0) return 93;
    if(address==1) return 96;
    if(address==2) return 99;
    if(address==3) return 102;
    if(address==4) return 105;
    if(address==5) return 108;
    if(address==6) return 111;
    if(address==7) return 114;
    if(address==8) return 117;
    if(address==9) return 120;
    if(address==10) return 123;
    if(address==11) return 126;
    if(address==12) return 129;
    if(address==13) return 132;
    if(address==14) return 135;
    return 255;
  }


}
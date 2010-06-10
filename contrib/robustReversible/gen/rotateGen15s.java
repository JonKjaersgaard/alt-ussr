/* DynaRole Java code generated for parallelRotate */
/* DynaRole Java statemachine generated for parallelRotate */
package robustReversible.gen;

import robustReversible.*;

public class rotateGen15s extends StateMachine {

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
      rotateDirTo(216,TRUE);
      token = 44;
      break;
    case 44:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(216)) break;
      stateManager.sendState(45,0);
      token = 255;
      break;
    case 45: /* Module M__0 */
      stateManager.addPendingState(45);
      rotateDirTo(0,TRUE);
      token = 46;
      break;
    case 46:
      stateManager.sendState(48,1);
      token = 47; /* fall-through */
    case 47:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(45);
      token = 255;
      break;
    case 48: /* Module M__1 */
      stateManager.addPendingState(48);
      rotateDirTo(0,TRUE);
      token = 49;
      break;
    case 49:
      stateManager.sendState(51,2);
      token = 50; /* fall-through */
    case 50:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(48);
      token = 255;
      break;
    case 51: /* Module M__2 */
      stateManager.addPendingState(51);
      rotateDirTo(0,TRUE);
      token = 52;
      break;
    case 52:
      stateManager.sendState(54,3);
      token = 53; /* fall-through */
    case 53:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(51);
      token = 255;
      break;
    case 54: /* Module M__3 */
      stateManager.addPendingState(54);
      rotateDirTo(0,TRUE);
      token = 55;
      break;
    case 55:
      stateManager.sendState(57,4);
      token = 56; /* fall-through */
    case 56:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(54);
      token = 255;
      break;
    case 57: /* Module M__4 */
      stateManager.addPendingState(57);
      rotateDirTo(0,TRUE);
      token = 58;
      break;
    case 58:
      stateManager.sendState(60,5);
      token = 59; /* fall-through */
    case 59:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(57);
      token = 255;
      break;
    case 60: /* Module M__5 */
      stateManager.addPendingState(60);
      rotateDirTo(0,TRUE);
      token = 61;
      break;
    case 61:
      stateManager.sendState(63,6);
      token = 62; /* fall-through */
    case 62:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(60);
      token = 255;
      break;
    case 63: /* Module M__6 */
      stateManager.addPendingState(63);
      rotateDirTo(0,TRUE);
      token = 64;
      break;
    case 64:
      stateManager.sendState(66,7);
      token = 65; /* fall-through */
    case 65:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(63);
      token = 255;
      break;
    case 66: /* Module M__7 */
      stateManager.addPendingState(66);
      rotateDirTo(0,TRUE);
      token = 67;
      break;
    case 67:
      stateManager.sendState(69,8);
      token = 68; /* fall-through */
    case 68:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(66);
      token = 255;
      break;
    case 69: /* Module M__8 */
      stateManager.addPendingState(69);
      rotateDirTo(0,TRUE);
      token = 70;
      break;
    case 70:
      stateManager.sendState(72,9);
      token = 71; /* fall-through */
    case 71:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(69);
      token = 255;
      break;
    case 72: /* Module M__9 */
      stateManager.addPendingState(72);
      rotateDirTo(0,TRUE);
      token = 73;
      break;
    case 73:
      stateManager.sendState(75,10);
      token = 74; /* fall-through */
    case 74:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(72);
      token = 255;
      break;
    case 75: /* Module M_10 */
      stateManager.addPendingState(75);
      rotateDirTo(0,TRUE);
      token = 76;
      break;
    case 76:
      stateManager.sendState(78,11);
      token = 77; /* fall-through */
    case 77:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(75);
      token = 255;
      break;
    case 78: /* Module M_11 */
      stateManager.addPendingState(78);
      rotateDirTo(0,TRUE);
      token = 79;
      break;
    case 79:
      stateManager.sendState(81,12);
      token = 80; /* fall-through */
    case 80:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(78);
      token = 255;
      break;
    case 81: /* Module M_12 */
      stateManager.addPendingState(81);
      rotateDirTo(0,TRUE);
      token = 82;
      break;
    case 82:
      stateManager.sendState(84,13);
      token = 83; /* fall-through */
    case 83:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(81);
      token = 255;
      break;
    case 84: /* Module M_13 */
      stateManager.addPendingState(84);
      rotateDirTo(0,TRUE);
      token = 85;
      break;
    case 85:
      stateManager.sendState(87,14);
      token = 86; /* fall-through */
    case 86:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(84);
      token = 255;
      break;
    case 87: /* Module M_14 */
      rotateDirTo(0,TRUE);
      token = 88;
      break;
    case 88:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(89,0);
      token = 255;
      break;
    case 89: /* Module M__0 */
      stateManager.addPendingState(89);
      rotateDirTo(216,TRUE);
      token = 90;
      break;
    case 90:
      stateManager.sendState(92,1);
      token = 91; /* fall-through */
    case 91:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(89);
      token = 255;
      break;
    case 92: /* Module M__1 */
      stateManager.addPendingState(92);
      rotateDirTo(216,TRUE);
      token = 93;
      break;
    case 93:
      stateManager.sendState(95,2);
      token = 94; /* fall-through */
    case 94:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(92);
      token = 255;
      break;
    case 95: /* Module M__2 */
      stateManager.addPendingState(95);
      rotateDirTo(216,TRUE);
      token = 96;
      break;
    case 96:
      stateManager.sendState(98,3);
      token = 97; /* fall-through */
    case 97:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(95);
      token = 255;
      break;
    case 98: /* Module M__3 */
      stateManager.addPendingState(98);
      rotateDirTo(216,TRUE);
      token = 99;
      break;
    case 99:
      stateManager.sendState(101,4);
      token = 100; /* fall-through */
    case 100:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(98);
      token = 255;
      break;
    case 101: /* Module M__4 */
      stateManager.addPendingState(101);
      rotateDirTo(216,TRUE);
      token = 102;
      break;
    case 102:
      stateManager.sendState(104,5);
      token = 103; /* fall-through */
    case 103:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(101);
      token = 255;
      break;
    case 104: /* Module M__5 */
      stateManager.addPendingState(104);
      rotateDirTo(216,TRUE);
      token = 105;
      break;
    case 105:
      stateManager.sendState(107,6);
      token = 106; /* fall-through */
    case 106:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(104);
      token = 255;
      break;
    case 107: /* Module M__6 */
      stateManager.addPendingState(107);
      rotateDirTo(216,TRUE);
      token = 108;
      break;
    case 108:
      stateManager.sendState(110,7);
      token = 109; /* fall-through */
    case 109:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(107);
      token = 255;
      break;
    case 110: /* Module M__7 */
      stateManager.addPendingState(110);
      rotateDirTo(216,TRUE);
      token = 111;
      break;
    case 111:
      stateManager.sendState(113,8);
      token = 112; /* fall-through */
    case 112:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(110);
      token = 255;
      break;
    case 113: /* Module M__8 */
      stateManager.addPendingState(113);
      rotateDirTo(216,TRUE);
      token = 114;
      break;
    case 114:
      stateManager.sendState(116,9);
      token = 115; /* fall-through */
    case 115:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(113);
      token = 255;
      break;
    case 116: /* Module M__9 */
      stateManager.addPendingState(116);
      rotateDirTo(216,TRUE);
      token = 117;
      break;
    case 117:
      stateManager.sendState(119,10);
      token = 118; /* fall-through */
    case 118:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(116);
      token = 255;
      break;
    case 119: /* Module M_10 */
      stateManager.addPendingState(119);
      rotateDirTo(216,TRUE);
      token = 120;
      break;
    case 120:
      stateManager.sendState(122,11);
      token = 121; /* fall-through */
    case 121:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(119);
      token = 255;
      break;
    case 122: /* Module M_11 */
      stateManager.addPendingState(122);
      rotateDirTo(216,TRUE);
      token = 123;
      break;
    case 123:
      stateManager.sendState(125,12);
      token = 124; /* fall-through */
    case 124:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(122);
      token = 255;
      break;
    case 125: /* Module M_12 */
      stateManager.addPendingState(125);
      rotateDirTo(216,TRUE);
      token = 126;
      break;
    case 126:
      stateManager.sendState(128,13);
      token = 127; /* fall-through */
    case 127:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(125);
      token = 255;
      break;
    case 128: /* Module M_13 */
      stateManager.addPendingState(128);
      rotateDirTo(216,TRUE);
      token = 129;
      break;
    case 129:
      stateManager.sendState(131,14);
      token = 130; /* fall-through */
    case 130:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(128);
      token = 255;
      break;
    case 131: /* Module M_14 */
      rotateDirTo(216,TRUE);
      token = 132;
      break;
    case 132:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(216)) break;
      stateManager.sendState(133,0);
      token = 255;
      break;
    case 133: /* Module M__0 */
      stateManager.addPendingState(133);
      rotateDirTo(0,TRUE);
      token = 134;
      break;
    case 134:
      stateManager.sendState(136,1);
      token = 135; /* fall-through */
    case 135:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(133);
      token = 255;
      break;
    case 136: /* Module M__1 */
      stateManager.addPendingState(136);
      rotateDirTo(0,TRUE);
      token = 137;
      break;
    case 137:
      stateManager.sendState(139,2);
      token = 138; /* fall-through */
    case 138:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(136);
      token = 255;
      break;
    case 139: /* Module M__2 */
      stateManager.addPendingState(139);
      rotateDirTo(0,TRUE);
      token = 140;
      break;
    case 140:
      stateManager.sendState(142,3);
      token = 141; /* fall-through */
    case 141:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(139);
      token = 255;
      break;
    case 142: /* Module M__3 */
      stateManager.addPendingState(142);
      rotateDirTo(0,TRUE);
      token = 143;
      break;
    case 143:
      stateManager.sendState(145,4);
      token = 144; /* fall-through */
    case 144:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(142);
      token = 255;
      break;
    case 145: /* Module M__4 */
      stateManager.addPendingState(145);
      rotateDirTo(0,TRUE);
      token = 146;
      break;
    case 146:
      stateManager.sendState(148,5);
      token = 147; /* fall-through */
    case 147:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(145);
      token = 255;
      break;
    case 148: /* Module M__5 */
      stateManager.addPendingState(148);
      rotateDirTo(0,TRUE);
      token = 149;
      break;
    case 149:
      stateManager.sendState(151,6);
      token = 150; /* fall-through */
    case 150:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(148);
      token = 255;
      break;
    case 151: /* Module M__6 */
      stateManager.addPendingState(151);
      rotateDirTo(0,TRUE);
      token = 152;
      break;
    case 152:
      stateManager.sendState(154,7);
      token = 153; /* fall-through */
    case 153:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(151);
      token = 255;
      break;
    case 154: /* Module M__7 */
      stateManager.addPendingState(154);
      rotateDirTo(0,TRUE);
      token = 155;
      break;
    case 155:
      stateManager.sendState(157,8);
      token = 156; /* fall-through */
    case 156:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(154);
      token = 255;
      break;
    case 157: /* Module M__8 */
      stateManager.addPendingState(157);
      rotateDirTo(0,TRUE);
      token = 158;
      break;
    case 158:
      stateManager.sendState(160,9);
      token = 159; /* fall-through */
    case 159:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(157);
      token = 255;
      break;
    case 160: /* Module M__9 */
      stateManager.addPendingState(160);
      rotateDirTo(0,TRUE);
      token = 161;
      break;
    case 161:
      stateManager.sendState(163,10);
      token = 162; /* fall-through */
    case 162:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(160);
      token = 255;
      break;
    case 163: /* Module M_10 */
      stateManager.addPendingState(163);
      rotateDirTo(0,TRUE);
      token = 164;
      break;
    case 164:
      stateManager.sendState(166,11);
      token = 165; /* fall-through */
    case 165:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(163);
      token = 255;
      break;
    case 166: /* Module M_11 */
      stateManager.addPendingState(166);
      rotateDirTo(0,TRUE);
      token = 167;
      break;
    case 167:
      stateManager.sendState(169,12);
      token = 168; /* fall-through */
    case 168:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(166);
      token = 255;
      break;
    case 169: /* Module M_12 */
      stateManager.addPendingState(169);
      rotateDirTo(0,TRUE);
      token = 170;
      break;
    case 170:
      stateManager.sendState(172,13);
      token = 171; /* fall-through */
    case 171:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(169);
      token = 255;
      break;
    case 172: /* Module M_13 */
      stateManager.addPendingState(172);
      rotateDirTo(0,TRUE);
      token = 173;
      break;
    case 173:
      stateManager.sendState(175,14);
      token = 174; /* fall-through */
    case 174:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(172);
      token = 255;
      break;
    case 175: /* Module M_14 */
      rotateDirTo(0,TRUE);
      token = 176;
      break;
    case 176:
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
      if(pendingState==154) return true;
      if(pendingState==66) return true;
      if(pendingState==22) return true;
      if(pendingState==110) return true;
      return false;
    }
    if(address==8) {
      if(pendingState==69) return true;
      if(pendingState==157) return true;
      if(pendingState==113) return true;
      if(pendingState==25) return true;
      return false;
    }
    if(address==5) {
      if(pendingState==16) return true;
      if(pendingState==148) return true;
      if(pendingState==104) return true;
      if(pendingState==60) return true;
      return false;
    }
    if(address==6) {
      if(pendingState==19) return true;
      if(pendingState==63) return true;
      if(pendingState==107) return true;
      if(pendingState==151) return true;
      return false;
    }
    if(address==9) {
      if(pendingState==116) return true;
      if(pendingState==160) return true;
      if(pendingState==72) return true;
      if(pendingState==28) return true;
      return false;
    }
    if(address==0) {
      if(pendingState==1) return true;
      if(pendingState==133) return true;
      if(pendingState==89) return true;
      if(pendingState==45) return true;
      return false;
    }
    if(address==3) {
      if(pendingState==98) return true;
      if(pendingState==54) return true;
      if(pendingState==142) return true;
      if(pendingState==10) return true;
      return false;
    }
    if(address==4) {
      if(pendingState==101) return true;
      if(pendingState==145) return true;
      if(pendingState==57) return true;
      if(pendingState==13) return true;
      return false;
    }
    if(address==1) {
      if(pendingState==136) return true;
      if(pendingState==48) return true;
      if(pendingState==4) return true;
      if(pendingState==92) return true;
      return false;
    }
    if(address==2) {
      if(pendingState==51) return true;
      if(pendingState==139) return true;
      if(pendingState==7) return true;
      if(pendingState==95) return true;
      return false;
    }
    if(address==13) {
      if(pendingState==84) return true;
      if(pendingState==172) return true;
      if(pendingState==128) return true;
      if(pendingState==40) return true;
      return false;
    }
    if(address==12) {
      if(pendingState==169) return true;
      if(pendingState==81) return true;
      if(pendingState==37) return true;
      if(pendingState==125) return true;
      return false;
    }
    if(address==11) {
      if(pendingState==34) return true;
      if(pendingState==78) return true;
      if(pendingState==166) return true;
      if(pendingState==122) return true;
      return false;
    }
    if(address==10) {
      if(pendingState==119) return true;
      if(pendingState==163) return true;
      if(pendingState==31) return true;
      if(pendingState==75) return true;
      return false;
    }
    return false;
  }

  int getLastState(int address) {
    if(address==0) return 133;
    if(address==1) return 136;
    if(address==2) return 139;
    if(address==3) return 142;
    if(address==4) return 145;
    if(address==5) return 148;
    if(address==6) return 151;
    if(address==7) return 154;
    if(address==8) return 157;
    if(address==9) return 160;
    if(address==10) return 163;
    if(address==11) return 166;
    if(address==12) return 169;
    if(address==13) return 172;
    if(address==14) return 175;
    return 255;
  }
  
  int getLastStateLowerBound(int address) {
    if(address==0) return 92;
    if(address==1) return 95;
    if(address==2) return 98;
    if(address==3) return 101;
    if(address==4) return 104;
    if(address==5) return 107;
    if(address==6) return 110;
    if(address==7) return 113;
    if(address==8) return 116;
    if(address==9) return 119;
    if(address==10) return 122;
    if(address==11) return 125;
    if(address==12) return 128;
    if(address==13) return 131;
    if(address==14) return 133;
    return 255;
  }


}
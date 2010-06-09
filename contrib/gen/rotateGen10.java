/* DynaRole Java code generated for parallelRotate */
/* DynaRole Java statemachine generated for parallelRotate */
package gen;

import rar.*;

public class rotateGen10 extends StateMachine {

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
      rotateDirTo(108,TRUE);
      token = 2;
      break;
    case 2:
      stateManager.sendState(4,1);
      token = 3; /* fall-through */
    case 3:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(1);
      token = 255;
      break;
    case 4: /* Module M__1 */
      stateManager.addPendingState(4);
      rotateDirTo(108,TRUE);
      token = 5;
      break;
    case 5:
      stateManager.sendState(7,2);
      token = 6; /* fall-through */
    case 6:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(4);
      token = 255;
      break;
    case 7: /* Module M__2 */
      stateManager.addPendingState(7);
      rotateDirTo(108,TRUE);
      token = 8;
      break;
    case 8:
      stateManager.sendState(10,3);
      token = 9; /* fall-through */
    case 9:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(7);
      token = 255;
      break;
    case 10: /* Module M__3 */
      stateManager.addPendingState(10);
      rotateDirTo(108,TRUE);
      token = 11;
      break;
    case 11:
      stateManager.sendState(13,4);
      token = 12; /* fall-through */
    case 12:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(10);
      token = 255;
      break;
    case 13: /* Module M__4 */
      stateManager.addPendingState(13);
      rotateDirTo(108,TRUE);
      token = 14;
      break;
    case 14:
      stateManager.sendState(16,5);
      token = 15; /* fall-through */
    case 15:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(13);
      token = 255;
      break;
    case 16: /* Module M__5 */
      stateManager.addPendingState(16);
      rotateDirTo(108,TRUE);
      token = 17;
      break;
    case 17:
      stateManager.sendState(19,6);
      token = 18; /* fall-through */
    case 18:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(16);
      token = 255;
      break;
    case 19: /* Module M__6 */
      stateManager.addPendingState(19);
      rotateDirTo(108,TRUE);
      token = 20;
      break;
    case 20:
      stateManager.sendState(22,7);
      token = 21; /* fall-through */
    case 21:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(19);
      token = 255;
      break;
    case 22: /* Module M__7 */
      stateManager.addPendingState(22);
      rotateDirTo(108,TRUE);
      token = 23;
      break;
    case 23:
      stateManager.sendState(25,8);
      token = 24; /* fall-through */
    case 24:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(22);
      token = 255;
      break;
    case 25: /* Module M__8 */
      stateManager.addPendingState(25);
      rotateDirTo(108,TRUE);
      token = 26;
      break;
    case 26:
      stateManager.sendState(28,9);
      token = 27; /* fall-through */
    case 27:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(25);
      token = 255;
      break;
    case 28: /* Module M__9 */
      rotateDirTo(108,TRUE);
      token = 29;
      break;
    case 29:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(108)) break;
      stateManager.sendState(30,0);
      token = 255;
      break;
    case 30: /* Module M__0 */
      stateManager.addPendingState(30);
      rotateDirTo(0,TRUE);
      token = 31;
      break;
    case 31:
      stateManager.sendState(33,1);
      token = 32; /* fall-through */
    case 32:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(30);
      token = 255;
      break;
    case 33: /* Module M__1 */
      stateManager.addPendingState(33);
      rotateDirTo(0,TRUE);
      token = 34;
      break;
    case 34:
      stateManager.sendState(36,2);
      token = 35; /* fall-through */
    case 35:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(33);
      token = 255;
      break;
    case 36: /* Module M__2 */
      stateManager.addPendingState(36);
      rotateDirTo(0,TRUE);
      token = 37;
      break;
    case 37:
      stateManager.sendState(39,3);
      token = 38; /* fall-through */
    case 38:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(36);
      token = 255;
      break;
    case 39: /* Module M__3 */
      stateManager.addPendingState(39);
      rotateDirTo(0,TRUE);
      token = 40;
      break;
    case 40:
      stateManager.sendState(42,4);
      token = 41; /* fall-through */
    case 41:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(39);
      token = 255;
      break;
    case 42: /* Module M__4 */
      stateManager.addPendingState(42);
      rotateDirTo(0,TRUE);
      token = 43;
      break;
    case 43:
      stateManager.sendState(45,5);
      token = 44; /* fall-through */
    case 44:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(42);
      token = 255;
      break;
    case 45: /* Module M__5 */
      stateManager.addPendingState(45);
      rotateDirTo(0,TRUE);
      token = 46;
      break;
    case 46:
      stateManager.sendState(48,6);
      token = 47; /* fall-through */
    case 47:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(45);
      token = 255;
      break;
    case 48: /* Module M__6 */
      stateManager.addPendingState(48);
      rotateDirTo(0,TRUE);
      token = 49;
      break;
    case 49:
      stateManager.sendState(51,7);
      token = 50; /* fall-through */
    case 50:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(48);
      token = 255;
      break;
    case 51: /* Module M__7 */
      stateManager.addPendingState(51);
      rotateDirTo(0,TRUE);
      token = 52;
      break;
    case 52:
      stateManager.sendState(54,8);
      token = 53; /* fall-through */
    case 53:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(51);
      token = 255;
      break;
    case 54: /* Module M__8 */
      stateManager.addPendingState(54);
      rotateDirTo(0,TRUE);
      token = 55;
      break;
    case 55:
      stateManager.sendState(57,9);
      token = 56; /* fall-through */
    case 56:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(54);
      token = 255;
      break;
    case 57: /* Module M__9 */
      rotateDirTo(0,TRUE);
      token = 58;
      break;
    case 58:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(59,0);
      token = 255;
      break;
    case 59: /* Module M__0 */
      stateManager.addPendingState(59);
      rotateDirTo(108,TRUE);
      token = 60;
      break;
    case 60:
      stateManager.sendState(62,1);
      token = 61; /* fall-through */
    case 61:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(59);
      token = 255;
      break;
    case 62: /* Module M__1 */
      stateManager.addPendingState(62);
      rotateDirTo(108,TRUE);
      token = 63;
      break;
    case 63:
      stateManager.sendState(65,2);
      token = 64; /* fall-through */
    case 64:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(62);
      token = 255;
      break;
    case 65: /* Module M__2 */
      stateManager.addPendingState(65);
      rotateDirTo(108,TRUE);
      token = 66;
      break;
    case 66:
      stateManager.sendState(68,3);
      token = 67; /* fall-through */
    case 67:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(65);
      token = 255;
      break;
    case 68: /* Module M__3 */
      stateManager.addPendingState(68);
      rotateDirTo(108,TRUE);
      token = 69;
      break;
    case 69:
      stateManager.sendState(71,4);
      token = 70; /* fall-through */
    case 70:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(68);
      token = 255;
      break;
    case 71: /* Module M__4 */
      stateManager.addPendingState(71);
      rotateDirTo(108,TRUE);
      token = 72;
      break;
    case 72:
      stateManager.sendState(74,5);
      token = 73; /* fall-through */
    case 73:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(71);
      token = 255;
      break;
    case 74: /* Module M__5 */
      stateManager.addPendingState(74);
      rotateDirTo(108,TRUE);
      token = 75;
      break;
    case 75:
      stateManager.sendState(77,6);
      token = 76; /* fall-through */
    case 76:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(74);
      token = 255;
      break;
    case 77: /* Module M__6 */
      stateManager.addPendingState(77);
      rotateDirTo(108,TRUE);
      token = 78;
      break;
    case 78:
      stateManager.sendState(80,7);
      token = 79; /* fall-through */
    case 79:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(77);
      token = 255;
      break;
    case 80: /* Module M__7 */
      stateManager.addPendingState(80);
      rotateDirTo(108,TRUE);
      token = 81;
      break;
    case 81:
      stateManager.sendState(83,8);
      token = 82; /* fall-through */
    case 82:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(80);
      token = 255;
      break;
    case 83: /* Module M__8 */
      stateManager.addPendingState(83);
      rotateDirTo(108,TRUE);
      token = 84;
      break;
    case 84:
      stateManager.sendState(86,9);
      token = 85; /* fall-through */
    case 85:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(83);
      token = 255;
      break;
    case 86: /* Module M__9 */
      rotateDirTo(108,TRUE);
      token = 87;
      break;
    case 87:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(108)) break;
      stateManager.sendState(88,0);
      token = 255;
      break;
    case 88: /* Module M__0 */
      stateManager.addPendingState(88);
      rotateDirTo(0,TRUE);
      token = 89;
      break;
    case 89:
      stateManager.sendState(91,1);
      token = 90; /* fall-through */
    case 90:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(88);
      token = 255;
      break;
    case 91: /* Module M__1 */
      stateManager.addPendingState(91);
      rotateDirTo(0,TRUE);
      token = 92;
      break;
    case 92:
      stateManager.sendState(94,2);
      token = 93; /* fall-through */
    case 93:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(91);
      token = 255;
      break;
    case 94: /* Module M__2 */
      stateManager.addPendingState(94);
      rotateDirTo(0,TRUE);
      token = 95;
      break;
    case 95:
      stateManager.sendState(97,3);
      token = 96; /* fall-through */
    case 96:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(94);
      token = 255;
      break;
    case 97: /* Module M__3 */
      stateManager.addPendingState(97);
      rotateDirTo(0,TRUE);
      token = 98;
      break;
    case 98:
      stateManager.sendState(100,4);
      token = 99; /* fall-through */
    case 99:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(97);
      token = 255;
      break;
    case 100: /* Module M__4 */
      stateManager.addPendingState(100);
      rotateDirTo(0,TRUE);
      token = 101;
      break;
    case 101:
      stateManager.sendState(103,5);
      token = 102; /* fall-through */
    case 102:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(100);
      token = 255;
      break;
    case 103: /* Module M__5 */
      stateManager.addPendingState(103);
      rotateDirTo(0,TRUE);
      token = 104;
      break;
    case 104:
      stateManager.sendState(106,6);
      token = 105; /* fall-through */
    case 105:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(103);
      token = 255;
      break;
    case 106: /* Module M__6 */
      stateManager.addPendingState(106);
      rotateDirTo(0,TRUE);
      token = 107;
      break;
    case 107:
      stateManager.sendState(109,7);
      token = 108; /* fall-through */
    case 108:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(106);
      token = 255;
      break;
    case 109: /* Module M__7 */
      stateManager.addPendingState(109);
      rotateDirTo(0,TRUE);
      token = 110;
      break;
    case 110:
      stateManager.sendState(112,8);
      token = 111; /* fall-through */
    case 111:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(109);
      token = 255;
      break;
    case 112: /* Module M__8 */
      stateManager.addPendingState(112);
      rotateDirTo(0,TRUE);
      token = 113;
      break;
    case 113:
      stateManager.sendState(115,9);
      token = 114; /* fall-through */
    case 114:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(112);
      token = 255;
      break;
    case 115: /* Module M__9 */
      rotateDirTo(0,TRUE);
      token = 116;
      break;
    case 116:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(117,0);
      token = 255;
      break;
    case 117: /* Module M__0 */
      stateManager.addPendingState(117);
      rotateDirTo(108,TRUE);
      token = 118;
      break;
    case 118:
      stateManager.sendState(120,1);
      token = 119; /* fall-through */
    case 119:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(117);
      token = 255;
      break;
    case 120: /* Module M__1 */
      stateManager.addPendingState(120);
      rotateDirTo(108,TRUE);
      token = 121;
      break;
    case 121:
      stateManager.sendState(123,2);
      token = 122; /* fall-through */
    case 122:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(120);
      token = 255;
      break;
    case 123: /* Module M__2 */
      stateManager.addPendingState(123);
      rotateDirTo(108,TRUE);
      token = 124;
      break;
    case 124:
      stateManager.sendState(126,3);
      token = 125; /* fall-through */
    case 125:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(123);
      token = 255;
      break;
    case 126: /* Module M__3 */
      stateManager.addPendingState(126);
      rotateDirTo(108,TRUE);
      token = 127;
      break;
    case 127:
      stateManager.sendState(129,4);
      token = 128; /* fall-through */
    case 128:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(126);
      token = 255;
      break;
    case 129: /* Module M__4 */
      stateManager.addPendingState(129);
      rotateDirTo(108,TRUE);
      token = 130;
      break;
    case 130:
      stateManager.sendState(132,5);
      token = 131; /* fall-through */
    case 131:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(129);
      token = 255;
      break;
    case 132: /* Module M__5 */
      stateManager.addPendingState(132);
      rotateDirTo(108,TRUE);
      token = 133;
      break;
    case 133:
      stateManager.sendState(135,6);
      token = 134; /* fall-through */
    case 134:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(132);
      token = 255;
      break;
    case 135: /* Module M__6 */
      stateManager.addPendingState(135);
      rotateDirTo(108,TRUE);
      token = 136;
      break;
    case 136:
      stateManager.sendState(138,7);
      token = 137; /* fall-through */
    case 137:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(135);
      token = 255;
      break;
    case 138: /* Module M__7 */
      stateManager.addPendingState(138);
      rotateDirTo(108,TRUE);
      token = 139;
      break;
    case 139:
      stateManager.sendState(141,8);
      token = 140; /* fall-through */
    case 140:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(138);
      token = 255;
      break;
    case 141: /* Module M__8 */
      stateManager.addPendingState(141);
      rotateDirTo(108,TRUE);
      token = 142;
      break;
    case 142:
      stateManager.sendState(144,9);
      token = 143; /* fall-through */
    case 143:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(141);
      token = 255;
      break;
    case 144: /* Module M__9 */
      rotateDirTo(108,TRUE);
      token = 145;
      break;
    case 145:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(108)) break;
      stateManager.sendState(146,0);
      token = 255;
      break;
    case 146: /* Module M__0 */
      stateManager.addPendingState(146);
      rotateDirTo(0,TRUE);
      token = 147;
      break;
    case 147:
      stateManager.sendState(149,1);
      token = 148; /* fall-through */
    case 148:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(146);
      token = 255;
      break;
    case 149: /* Module M__1 */
      stateManager.addPendingState(149);
      rotateDirTo(0,TRUE);
      token = 150;
      break;
    case 150:
      stateManager.sendState(152,2);
      token = 151; /* fall-through */
    case 151:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(149);
      token = 255;
      break;
    case 152: /* Module M__2 */
      stateManager.addPendingState(152);
      rotateDirTo(0,TRUE);
      token = 153;
      break;
    case 153:
      stateManager.sendState(155,3);
      token = 154; /* fall-through */
    case 154:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(152);
      token = 255;
      break;
    case 155: /* Module M__3 */
      stateManager.addPendingState(155);
      rotateDirTo(0,TRUE);
      token = 156;
      break;
    case 156:
      stateManager.sendState(158,4);
      token = 157; /* fall-through */
    case 157:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(155);
      token = 255;
      break;
    case 158: /* Module M__4 */
      stateManager.addPendingState(158);
      rotateDirTo(0,TRUE);
      token = 159;
      break;
    case 159:
      stateManager.sendState(161,5);
      token = 160; /* fall-through */
    case 160:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(158);
      token = 255;
      break;
    case 161: /* Module M__5 */
      stateManager.addPendingState(161);
      rotateDirTo(0,TRUE);
      token = 162;
      break;
    case 162:
      stateManager.sendState(164,6);
      token = 163; /* fall-through */
    case 163:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(161);
      token = 255;
      break;
    case 164: /* Module M__6 */
      stateManager.addPendingState(164);
      rotateDirTo(0,TRUE);
      token = 165;
      break;
    case 165:
      stateManager.sendState(167,7);
      token = 166; /* fall-through */
    case 166:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(164);
      token = 255;
      break;
    case 167: /* Module M__7 */
      stateManager.addPendingState(167);
      rotateDirTo(0,TRUE);
      token = 168;
      break;
    case 168:
      stateManager.sendState(170,8);
      token = 169; /* fall-through */
    case 169:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(167);
      token = 255;
      break;
    case 170: /* Module M__8 */
      stateManager.addPendingState(170);
      rotateDirTo(0,TRUE);
      token = 171;
      break;
    case 171:
      stateManager.sendState(173,9);
      token = 172; /* fall-through */
    case 172:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(170);
      token = 255;
      break;
    case 173: /* Module M__9 */
      rotateDirTo(0,TRUE);
      token = 174;
      break;
    case 174:
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

    else address = 127;
	myID = address;
	token = 255;
	stateManager.init(myID);
	
    api.setLeds(myID);
	reset_state();
	isDone = false;
}

  public void reset_sequence() {
    if ( myID == 0 ) {
      token = 0; // Tolerate reset in the middle of reconfiguration
    }    
    stateManager.reset_sequence();
}

  public void reset_state() {
    if ( myID == 0 ) {
      token = 0; // Tolerate reset in the middle of reconfiguration
    }    
    stateManager.reset_state();
}

  public boolean checkPendingStateResponsibility(int address, int pendingState) {
    if(address==7) {
      if(pendingState==51) return true;
      if(pendingState==138) return true;
      if(pendingState==80) return true;
      if(pendingState==22) return true;
      if(pendingState==109) return true;
      if(pendingState==167) return true;
      return false;
    }
    if(address==8) {
      if(pendingState==170) return true;
      if(pendingState==141) return true;
      if(pendingState==54) return true;
      if(pendingState==83) return true;
      if(pendingState==112) return true;
      if(pendingState==25) return true;
      return false;
    }
    if(address==5) {
      if(pendingState==103) return true;
      if(pendingState==16) return true;
      if(pendingState==161) return true;
      if(pendingState==132) return true;
      if(pendingState==74) return true;
      if(pendingState==45) return true;
      return false;
    }
    if(address==6) {
      if(pendingState==19) return true;
      if(pendingState==48) return true;
      if(pendingState==77) return true;
      if(pendingState==106) return true;
      if(pendingState==135) return true;
      if(pendingState==164) return true;
      return false;
    }
    if(address==0) {
      if(pendingState==1) return true;
      if(pendingState==117) return true;
      if(pendingState==59) return true;
      if(pendingState==146) return true;
      if(pendingState==88) return true;
      if(pendingState==30) return true;
      return false;
    }
    if(address==3) {
      if(pendingState==68) return true;
      if(pendingState==155) return true;
      if(pendingState==39) return true;
      if(pendingState==97) return true;
      if(pendingState==126) return true;
      if(pendingState==10) return true;
      return false;
    }
    if(address==4) {
      if(pendingState==100) return true;
      if(pendingState==71) return true;
      if(pendingState==158) return true;
      if(pendingState==129) return true;
      if(pendingState==42) return true;
      if(pendingState==13) return true;
      return false;
    }
    if(address==1) {
      if(pendingState==33) return true;
      if(pendingState==4) return true;
      if(pendingState==149) return true;
      if(pendingState==62) return true;
      if(pendingState==91) return true;
      if(pendingState==120) return true;
      return false;
    }
    if(address==2) {
      if(pendingState==152) return true;
      if(pendingState==65) return true;
      if(pendingState==36) return true;
      if(pendingState==7) return true;
      if(pendingState==94) return true;
      if(pendingState==123) return true;
      return false;
    }
    return false;
  }

  int getLastState(int address) {
    if(address==0) return 146;
    if(address==1) return 149;
    if(address==2) return 152;
    if(address==3) return 155;
    if(address==4) return 158;
    if(address==5) return 161;
    if(address==6) return 164;
    if(address==7) return 167;
    if(address==8) return 170;
    if(address==9) return 173;
    return 255;
  }
  
  int getLastStateLowerBound(int address) {
    if(address==0) return 120;
    if(address==1) return 123;
    if(address==2) return 126;
    if(address==3) return 129;
    if(address==4) return 132;
    if(address==5) return 135;
    if(address==6) return 138;
    if(address==7) return 141;
    if(address==8) return 144;
    if(address==9) return 146;
    return 255;
  }


}
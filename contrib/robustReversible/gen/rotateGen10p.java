/* DynaRole Java code generated for parallelRotate */
/* DynaRole Java statemachine generated for parallelRotate */
package robustReversible.gen;

import robustReversible.*;

public class rotateGen10p extends StateMachine {

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
      stateManager.sendState(31,0);
      token = 30; /* fall-through */
    case 30:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(28);
      token = 255;
      break;
    case 31: /* Module M__0 */
      stateManager.addPendingState(31);
      rotateDirTo(0,TRUE);
      token = 32;
      break;
    case 32:
      stateManager.sendState(34,1);
      token = 33; /* fall-through */
    case 33:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(31);
      token = 255;
      break;
    case 34: /* Module M__1 */
      stateManager.addPendingState(34);
      rotateDirTo(0,TRUE);
      token = 35;
      break;
    case 35:
      stateManager.sendState(37,2);
      token = 36; /* fall-through */
    case 36:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(34);
      token = 255;
      break;
    case 37: /* Module M__2 */
      stateManager.addPendingState(37);
      rotateDirTo(0,TRUE);
      token = 38;
      break;
    case 38:
      stateManager.sendState(40,3);
      token = 39; /* fall-through */
    case 39:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(37);
      token = 255;
      break;
    case 40: /* Module M__3 */
      stateManager.addPendingState(40);
      rotateDirTo(0,TRUE);
      token = 41;
      break;
    case 41:
      stateManager.sendState(43,4);
      token = 42; /* fall-through */
    case 42:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(40);
      token = 255;
      break;
    case 43: /* Module M__4 */
      stateManager.addPendingState(43);
      rotateDirTo(0,TRUE);
      token = 44;
      break;
    case 44:
      stateManager.sendState(46,5);
      token = 45; /* fall-through */
    case 45:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(43);
      token = 255;
      break;
    case 46: /* Module M__5 */
      stateManager.addPendingState(46);
      rotateDirTo(0,TRUE);
      token = 47;
      break;
    case 47:
      stateManager.sendState(49,6);
      token = 48; /* fall-through */
    case 48:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(46);
      token = 255;
      break;
    case 49: /* Module M__6 */
      stateManager.addPendingState(49);
      rotateDirTo(0,TRUE);
      token = 50;
      break;
    case 50:
      stateManager.sendState(52,7);
      token = 51; /* fall-through */
    case 51:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(49);
      token = 255;
      break;
    case 52: /* Module M__7 */
      stateManager.addPendingState(52);
      rotateDirTo(0,TRUE);
      token = 53;
      break;
    case 53:
      stateManager.sendState(55,8);
      token = 54; /* fall-through */
    case 54:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(52);
      token = 255;
      break;
    case 55: /* Module M__8 */
      stateManager.addPendingState(55);
      rotateDirTo(0,TRUE);
      token = 56;
      break;
    case 56:
      stateManager.sendState(58,9);
      token = 57; /* fall-through */
    case 57:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(55);
      token = 255;
      break;
    case 58: /* Module M__9 */
      rotateDirTo(0,TRUE);
      token = 59;
      break;
    case 59:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(60,0);
      token = 255;
      break;
    case 60: /* Module M__0 */
      stateManager.addPendingState(60);
      rotateDirTo(216,TRUE);
      token = 61;
      break;
    case 61:
      stateManager.sendState(63,1);
      token = 62; /* fall-through */
    case 62:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(60);
      token = 255;
      break;
    case 63: /* Module M__1 */
      stateManager.addPendingState(63);
      rotateDirTo(216,TRUE);
      token = 64;
      break;
    case 64:
      stateManager.sendState(66,2);
      token = 65; /* fall-through */
    case 65:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(63);
      token = 255;
      break;
    case 66: /* Module M__2 */
      stateManager.addPendingState(66);
      rotateDirTo(216,TRUE);
      token = 67;
      break;
    case 67:
      stateManager.sendState(69,3);
      token = 68; /* fall-through */
    case 68:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(66);
      token = 255;
      break;
    case 69: /* Module M__3 */
      stateManager.addPendingState(69);
      rotateDirTo(216,TRUE);
      token = 70;
      break;
    case 70:
      stateManager.sendState(72,4);
      token = 71; /* fall-through */
    case 71:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(69);
      token = 255;
      break;
    case 72: /* Module M__4 */
      stateManager.addPendingState(72);
      rotateDirTo(216,TRUE);
      token = 73;
      break;
    case 73:
      stateManager.sendState(75,5);
      token = 74; /* fall-through */
    case 74:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(72);
      token = 255;
      break;
    case 75: /* Module M__5 */
      stateManager.addPendingState(75);
      rotateDirTo(216,TRUE);
      token = 76;
      break;
    case 76:
      stateManager.sendState(78,6);
      token = 77; /* fall-through */
    case 77:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(75);
      token = 255;
      break;
    case 78: /* Module M__6 */
      stateManager.addPendingState(78);
      rotateDirTo(216,TRUE);
      token = 79;
      break;
    case 79:
      stateManager.sendState(81,7);
      token = 80; /* fall-through */
    case 80:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(78);
      token = 255;
      break;
    case 81: /* Module M__7 */
      stateManager.addPendingState(81);
      rotateDirTo(216,TRUE);
      token = 82;
      break;
    case 82:
      stateManager.sendState(84,8);
      token = 83; /* fall-through */
    case 83:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(81);
      token = 255;
      break;
    case 84: /* Module M__8 */
      stateManager.addPendingState(84);
      rotateDirTo(216,TRUE);
      token = 85;
      break;
    case 85:
      stateManager.sendState(87,9);
      token = 86; /* fall-through */
    case 86:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(84);
      token = 255;
      break;
    case 87: /* Module M__9 */
      stateManager.addPendingState(87);
      rotateDirTo(216,TRUE);
      token = 88;
      break;
    case 88:
      stateManager.sendState(90,0);
      token = 89; /* fall-through */
    case 89:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(87);
      token = 255;
      break;
    case 90: /* Module M__0 */
      stateManager.addPendingState(90);
      rotateDirTo(0,TRUE);
      token = 91;
      break;
    case 91:
      stateManager.sendState(93,1);
      token = 92; /* fall-through */
    case 92:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(90);
      token = 255;
      break;
    case 93: /* Module M__1 */
      stateManager.addPendingState(93);
      rotateDirTo(0,TRUE);
      token = 94;
      break;
    case 94:
      stateManager.sendState(96,2);
      token = 95; /* fall-through */
    case 95:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(93);
      token = 255;
      break;
    case 96: /* Module M__2 */
      stateManager.addPendingState(96);
      rotateDirTo(0,TRUE);
      token = 97;
      break;
    case 97:
      stateManager.sendState(99,3);
      token = 98; /* fall-through */
    case 98:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(96);
      token = 255;
      break;
    case 99: /* Module M__3 */
      stateManager.addPendingState(99);
      rotateDirTo(0,TRUE);
      token = 100;
      break;
    case 100:
      stateManager.sendState(102,4);
      token = 101; /* fall-through */
    case 101:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(99);
      token = 255;
      break;
    case 102: /* Module M__4 */
      stateManager.addPendingState(102);
      rotateDirTo(0,TRUE);
      token = 103;
      break;
    case 103:
      stateManager.sendState(105,5);
      token = 104; /* fall-through */
    case 104:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(102);
      token = 255;
      break;
    case 105: /* Module M__5 */
      stateManager.addPendingState(105);
      rotateDirTo(0,TRUE);
      token = 106;
      break;
    case 106:
      stateManager.sendState(108,6);
      token = 107; /* fall-through */
    case 107:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(105);
      token = 255;
      break;
    case 108: /* Module M__6 */
      stateManager.addPendingState(108);
      rotateDirTo(0,TRUE);
      token = 109;
      break;
    case 109:
      stateManager.sendState(111,7);
      token = 110; /* fall-through */
    case 110:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(108);
      token = 255;
      break;
    case 111: /* Module M__7 */
      stateManager.addPendingState(111);
      rotateDirTo(0,TRUE);
      token = 112;
      break;
    case 112:
      stateManager.sendState(114,8);
      token = 113; /* fall-through */
    case 113:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(111);
      token = 255;
      break;
    case 114: /* Module M__8 */
      stateManager.addPendingState(114);
      rotateDirTo(0,TRUE);
      token = 115;
      break;
    case 115:
      stateManager.sendState(117,9);
      token = 116; /* fall-through */
    case 116:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(114);
      token = 255;
      break;
    case 117: /* Module M__9 */
      rotateDirTo(0,TRUE);
      token = 118;
      break;
    case 118:
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
      if(pendingState==81) return true;
      if(pendingState==52) return true;
      if(pendingState==22) return true;
      if(pendingState==111) return true;
      return false;
    }
    if(address==8) {
      if(pendingState==84) return true;
      if(pendingState==55) return true;
      if(pendingState==114) return true;
      if(pendingState==25) return true;
      return false;
    }
    if(address==5) {
      if(pendingState==16) return true;
      if(pendingState==46) return true;
      if(pendingState==105) return true;
      if(pendingState==75) return true;
      return false;
    }
    if(address==6) {
      if(pendingState==49) return true;
      if(pendingState==19) return true;
      if(pendingState==108) return true;
      if(pendingState==78) return true;
      return false;
    }
    if(address==9) {
      if(pendingState==87) return true;
      if(pendingState==28) return true;
      return false;
    }
    if(address==0) {
      if(pendingState==1) return true;
      if(pendingState==31) return true;
      if(pendingState==90) return true;
      if(pendingState==60) return true;
      return false;
    }
    if(address==3) {
      if(pendingState==69) return true;
      if(pendingState==99) return true;
      if(pendingState==40) return true;
      if(pendingState==10) return true;
      return false;
    }
    if(address==4) {
      if(pendingState==102) return true;
      if(pendingState==43) return true;
      if(pendingState==72) return true;
      if(pendingState==13) return true;
      return false;
    }
    if(address==1) {
      if(pendingState==34) return true;
      if(pendingState==4) return true;
      if(pendingState==93) return true;
      if(pendingState==63) return true;
      return false;
    }
    if(address==2) {
      if(pendingState==96) return true;
      if(pendingState==66) return true;
      if(pendingState==37) return true;
      if(pendingState==7) return true;
      return false;
    }
    return false;
  }

  int getLastState(int address) {
    if(address==0) return 90;
    if(address==1) return 93;
    if(address==2) return 96;
    if(address==3) return 99;
    if(address==4) return 102;
    if(address==5) return 105;
    if(address==6) return 108;
    if(address==7) return 111;
    if(address==8) return 114;
    if(address==9) return 117;
    return 255;
  }
  
  int getLastStateLowerBound(int address) {
    if(address==0) return 63;
    if(address==1) return 66;
    if(address==2) return 69;
    if(address==3) return 72;
    if(address==4) return 75;
    if(address==5) return 78;
    if(address==6) return 81;
    if(address==7) return 84;
    if(address==8) return 87;
    if(address==9) return 90;
    return 255;
  }


}
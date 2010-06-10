/* DynaRole Java code generated for parallelRotate */
/* DynaRole Java statemachine generated for parallelRotate */
package gen;

import rar.*;

public class rotateGen5s extends StateMachine {

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
      rotateDirTo(216,TRUE);
      token = 14;
      break;
    case 14:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(216)) break;
      stateManager.sendState(15,0);
      token = 255;
      break;
    case 15: /* Module M__0 */
      stateManager.addPendingState(15);
      rotateDirTo(0,TRUE);
      token = 16;
      break;
    case 16:
      stateManager.sendState(18,1);
      token = 17; /* fall-through */
    case 17:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(15);
      token = 255;
      break;
    case 18: /* Module M__1 */
      stateManager.addPendingState(18);
      rotateDirTo(0,TRUE);
      token = 19;
      break;
    case 19:
      stateManager.sendState(21,2);
      token = 20; /* fall-through */
    case 20:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(18);
      token = 255;
      break;
    case 21: /* Module M__2 */
      stateManager.addPendingState(21);
      rotateDirTo(0,TRUE);
      token = 22;
      break;
    case 22:
      stateManager.sendState(24,3);
      token = 23; /* fall-through */
    case 23:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(21);
      token = 255;
      break;
    case 24: /* Module M__3 */
      stateManager.addPendingState(24);
      rotateDirTo(0,TRUE);
      token = 25;
      break;
    case 25:
      stateManager.sendState(27,4);
      token = 26; /* fall-through */
    case 26:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(24);
      token = 255;
      break;
    case 27: /* Module M__4 */
      rotateDirTo(0,TRUE);
      token = 28;
      break;
    case 28:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(29,0);
      token = 255;
      break;
    case 29: /* Module M__0 */
      stateManager.addPendingState(29);
      rotateDirTo(216,TRUE);
      token = 30;
      break;
    case 30:
      stateManager.sendState(32,1);
      token = 31; /* fall-through */
    case 31:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(29);
      token = 255;
      break;
    case 32: /* Module M__1 */
      stateManager.addPendingState(32);
      rotateDirTo(216,TRUE);
      token = 33;
      break;
    case 33:
      stateManager.sendState(35,2);
      token = 34; /* fall-through */
    case 34:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(32);
      token = 255;
      break;
    case 35: /* Module M__2 */
      stateManager.addPendingState(35);
      rotateDirTo(216,TRUE);
      token = 36;
      break;
    case 36:
      stateManager.sendState(38,3);
      token = 37; /* fall-through */
    case 37:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(35);
      token = 255;
      break;
    case 38: /* Module M__3 */
      stateManager.addPendingState(38);
      rotateDirTo(216,TRUE);
      token = 39;
      break;
    case 39:
      stateManager.sendState(41,4);
      token = 40; /* fall-through */
    case 40:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(38);
      token = 255;
      break;
    case 41: /* Module M__4 */
      rotateDirTo(216,TRUE);
      token = 42;
      break;
    case 42:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(216)) break;
      stateManager.sendState(43,0);
      token = 255;
      break;
    case 43: /* Module M__0 */
      stateManager.addPendingState(43);
      rotateDirTo(0,TRUE);
      token = 44;
      break;
    case 44:
      stateManager.sendState(46,1);
      token = 45; /* fall-through */
    case 45:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(43);
      token = 255;
      break;
    case 46: /* Module M__1 */
      stateManager.addPendingState(46);
      rotateDirTo(0,TRUE);
      token = 47;
      break;
    case 47:
      stateManager.sendState(49,2);
      token = 48; /* fall-through */
    case 48:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(46);
      token = 255;
      break;
    case 49: /* Module M__2 */
      stateManager.addPendingState(49);
      rotateDirTo(0,TRUE);
      token = 50;
      break;
    case 50:
      stateManager.sendState(52,3);
      token = 51; /* fall-through */
    case 51:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(49);
      token = 255;
      break;
    case 52: /* Module M__3 */
      stateManager.addPendingState(52);
      rotateDirTo(0,TRUE);
      token = 53;
      break;
    case 53:
      stateManager.sendState(55,4);
      token = 54; /* fall-through */
    case 54:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(52);
      token = 255;
      break;
    case 55: /* Module M__4 */
      rotateDirTo(0,TRUE);
      token = 56;
      break;
    case 56:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(57,0);
      token = 255;
      break;
    case 57: /* Module M__0 */
      stateManager.addPendingState(57);
      rotateDirTo(216,TRUE);
      token = 58;
      break;
    case 58:
      stateManager.sendState(60,1);
      token = 59; /* fall-through */
    case 59:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(57);
      token = 255;
      break;
    case 60: /* Module M__1 */
      stateManager.addPendingState(60);
      rotateDirTo(216,TRUE);
      token = 61;
      break;
    case 61:
      stateManager.sendState(63,2);
      token = 62; /* fall-through */
    case 62:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(60);
      token = 255;
      break;
    case 63: /* Module M__2 */
      stateManager.addPendingState(63);
      rotateDirTo(216,TRUE);
      token = 64;
      break;
    case 64:
      stateManager.sendState(66,3);
      token = 65; /* fall-through */
    case 65:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(63);
      token = 255;
      break;
    case 66: /* Module M__3 */
      stateManager.addPendingState(66);
      rotateDirTo(216,TRUE);
      token = 67;
      break;
    case 67:
      stateManager.sendState(69,4);
      token = 68; /* fall-through */
    case 68:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(66);
      token = 255;
      break;
    case 69: /* Module M__4 */
      rotateDirTo(216,TRUE);
      token = 70;
      break;
    case 70:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(216)) break;
      stateManager.sendState(71,0);
      token = 255;
      break;
    case 71: /* Module M__0 */
      stateManager.addPendingState(71);
      rotateDirTo(0,TRUE);
      token = 72;
      break;
    case 72:
      stateManager.sendState(74,1);
      token = 73; /* fall-through */
    case 73:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(71);
      token = 255;
      break;
    case 74: /* Module M__1 */
      stateManager.addPendingState(74);
      rotateDirTo(0,TRUE);
      token = 75;
      break;
    case 75:
      stateManager.sendState(77,2);
      token = 76; /* fall-through */
    case 76:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(74);
      token = 255;
      break;
    case 77: /* Module M__2 */
      stateManager.addPendingState(77);
      rotateDirTo(0,TRUE);
      token = 78;
      break;
    case 78:
      stateManager.sendState(80,3);
      token = 79; /* fall-through */
    case 79:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(77);
      token = 255;
      break;
    case 80: /* Module M__3 */
      stateManager.addPendingState(80);
      rotateDirTo(0,TRUE);
      token = 81;
      break;
    case 81:
      stateManager.sendState(83,4);
      token = 82; /* fall-through */
    case 82:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(80);
      token = 255;
      break;
    case 83: /* Module M__4 */
      rotateDirTo(0,TRUE);
      token = 84;
      break;
    case 84:
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
    if(address==0) {
      if(pendingState==1) return true;
      if(pendingState==71) return true;
      if(pendingState==43) return true;
      if(pendingState==57) return true;
      if(pendingState==29) return true;
      if(pendingState==15) return true;
      return false;
    }
    if(address==3) {
      if(pendingState==38) return true;
      if(pendingState==80) return true;
      if(pendingState==66) return true;
      if(pendingState==52) return true;
      if(pendingState==24) return true;
      if(pendingState==10) return true;
      return false;
    }
    if(address==1) {
      if(pendingState==32) return true;
      if(pendingState==18) return true;
      if(pendingState==4) return true;
      if(pendingState==46) return true;
      if(pendingState==74) return true;
      if(pendingState==60) return true;
      return false;
    }
    if(address==2) {
      if(pendingState==35) return true;
      if(pendingState==49) return true;
      if(pendingState==21) return true;
      if(pendingState==7) return true;
      if(pendingState==77) return true;
      if(pendingState==63) return true;
      return false;
    }
    return false;
  }

  int getLastState(int address) {
    if(address==0) return 71;
    if(address==1) return 74;
    if(address==2) return 77;
    if(address==3) return 80;
    if(address==4) return 83;
    return 255;
  }
  
  int getLastStateLowerBound(int address) {
    if(address==0) return 60;
    if(address==1) return 63;
    if(address==2) return 66;
    if(address==3) return 69;
    if(address==4) return 71;
    return 255;
  }


}
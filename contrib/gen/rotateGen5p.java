/* DynaRole Java code generated for parallelRotate */
/* DynaRole Java statemachine generated for parallelRotate */
package gen;

import java.util.Date;

import rar.*;

public class rotateGen5p extends StateMachine {

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
      stateManager.sendState(16,0);
      token = 15; /* fall-through */
    case 15:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(13);
      token = 255;
      break;
    case 16: /* Module M__0 */
      stateManager.addPendingState(16);
      rotateDirTo(0,TRUE);
      token = 17;
      break;
    case 17:
      stateManager.sendState(19,1);
      token = 18; /* fall-through */
    case 18:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(16);
      token = 255;
      break;
    case 19: /* Module M__1 */
      stateManager.addPendingState(19);
      rotateDirTo(0,TRUE);
      token = 20;
      break;
    case 20:
      stateManager.sendState(22,2);
      token = 21; /* fall-through */
    case 21:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(19);
      token = 255;
      break;
    case 22: /* Module M__2 */
      stateManager.addPendingState(22);
      rotateDirTo(0,TRUE);
      token = 23;
      break;
    case 23:
      stateManager.sendState(25,3);
      token = 24; /* fall-through */
    case 24:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(22);
      token = 255;
      break;
    case 25: /* Module M__3 */
      stateManager.addPendingState(25);
      rotateDirTo(0,TRUE);
      token = 26;
      break;
    case 26:
      stateManager.sendState(28,4);
      token = 27; /* fall-through */
    case 27:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(25);
      token = 255;
      break;
    case 28: /* Module M__4 */
      rotateDirTo(0,TRUE);
      token = 29;
      break;
    case 29:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(30,0);
      token = 255;
      break;
    case 30: /* Module M__0 */
      stateManager.addPendingState(30);
      rotateDirTo(216,TRUE);
      token = 31;
      break;
    case 31:
      stateManager.sendState(33,1);
      token = 32; /* fall-through */
    case 32:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(30);
      token = 255;
      break;
    case 33: /* Module M__1 */
      stateManager.addPendingState(33);
      rotateDirTo(216,TRUE);
      token = 34;
      break;
    case 34:
      stateManager.sendState(36,2);
      token = 35; /* fall-through */
    case 35:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(33);
      token = 255;
      break;
    case 36: /* Module M__2 */
      stateManager.addPendingState(36);
      rotateDirTo(216,TRUE);
      token = 37;
      break;
    case 37:
      stateManager.sendState(39,3);
      token = 38; /* fall-through */
    case 38:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(36);
      token = 255;
      break;
    case 39: /* Module M__3 */
      stateManager.addPendingState(39);
      rotateDirTo(216,TRUE);
      token = 40;
      break;
    case 40:
      stateManager.sendState(42,4);
      token = 41; /* fall-through */
    case 41:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(39);
      token = 255;
      break;
    case 42: /* Module M__4 */
      stateManager.addPendingState(42);
      rotateDirTo(216,TRUE);
      token = 43;
      break;
    case 43:
      stateManager.sendState(45,0);
      token = 44; /* fall-through */
    case 44:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(42);
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
      rotateDirTo(216,TRUE);
      token = 60;
      break;
    case 60:
      stateManager.sendState(62,1);
      token = 61; /* fall-through */
    case 61:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(59);
      token = 255;
      break;
    case 62: /* Module M__1 */
      stateManager.addPendingState(62);
      rotateDirTo(216,TRUE);
      token = 63;
      break;
    case 63:
      stateManager.sendState(65,2);
      token = 64; /* fall-through */
    case 64:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(62);
      token = 255;
      break;
    case 65: /* Module M__2 */
      stateManager.addPendingState(65);
      rotateDirTo(216,TRUE);
      token = 66;
      break;
    case 66:
      stateManager.sendState(68,3);
      token = 67; /* fall-through */
    case 67:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(65);
      token = 255;
      break;
    case 68: /* Module M__3 */
      stateManager.addPendingState(68);
      rotateDirTo(216,TRUE);
      token = 69;
      break;
    case 69:
      stateManager.sendState(71,4);
      token = 70; /* fall-through */
    case 70:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(68);
      token = 255;
      break;
    case 71: /* Module M__4 */
      stateManager.addPendingState(71);
      rotateDirTo(216,TRUE);
      token = 72;
      break;
    case 72:
      stateManager.sendState(74,0);
      token = 73; /* fall-through */
    case 73:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(71);
      token = 255;
      break;
    case 74: /* Module M__0 */
      stateManager.addPendingState(74);
      rotateDirTo(0,TRUE);
      token = 75;
      break;
    case 75:
      stateManager.sendState(77,1);
      token = 76; /* fall-through */
    case 76:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(74);
      token = 255;
      break;
    case 77: /* Module M__1 */
      stateManager.addPendingState(77);
      rotateDirTo(0,TRUE);
      token = 78;
      break;
    case 78:
      stateManager.sendState(80,2);
      token = 79; /* fall-through */
    case 79:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(77);
      token = 255;
      break;
    case 80: /* Module M__2 */
      stateManager.addPendingState(80);
      rotateDirTo(0,TRUE);
      token = 81;
      break;
    case 81:
      stateManager.sendState(83,3);
      token = 82; /* fall-through */
    case 82:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(80);
      token = 255;
      break;
    case 83: /* Module M__3 */
      stateManager.addPendingState(83);
      rotateDirTo(0,TRUE);
      token = 84;
      break;
    case 84:
      stateManager.sendState(86,4);
      token = 85; /* fall-through */
    case 85:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(83);
      token = 255;
      break;
    case 86: /* Module M__4 */
      rotateDirTo(0,TRUE);
      token = 87;
      break;
    case 87:
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
      if(pendingState==16) return true;
      if(pendingState==1) return true;
      if(pendingState==59) return true;
      if(pendingState==74) return true;
      if(pendingState==45) return true;
      if(pendingState==30) return true;
      return false;
    }
    if(address==3) {
      if(pendingState==68) return true;
      if(pendingState==54) return true;
      if(pendingState==39) return true;
      if(pendingState==83) return true;
      if(pendingState==25) return true;
      if(pendingState==10) return true;
      return false;
    }
    if(address==4) {
      if(pendingState==71) return true;
      if(pendingState==42) return true;
      if(pendingState==13) return true;
      return false;
    }
    if(address==1) {
      if(pendingState==19) return true;
      if(pendingState==48) return true;
      if(pendingState==33) return true;
      if(pendingState==4) return true;
      if(pendingState==77) return true;
      if(pendingState==62) return true;
      return false;
    }
    if(address==2) {
      if(pendingState==51) return true;
      if(pendingState==80) return true;
      if(pendingState==65) return true;
      if(pendingState==36) return true;
      if(pendingState==22) return true;
      if(pendingState==7) return true;
      return false;
    }
    return false;
  }

  int getLastState(int address) {
    if(address==0) return 74;
    if(address==1) return 77;
    if(address==2) return 80;
    if(address==3) return 83;
    if(address==4) return 86;
    return 255;
  }
  
  int getLastStateLowerBound(int address) {
    if(address==0) return 62;
    if(address==1) return 65;
    if(address==2) return 68;
    if(address==3) return 71;
    if(address==4) return 74;
    return 255;
  }


}
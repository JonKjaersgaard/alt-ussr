/* DynaRole Java code generated for carsnakeSimple */
/* DynaRole Java statemachine generated for carsnakeSimple */
package robustReversible.gen;

import robustReversible.*;

public class snakeGen_par extends StateMachine {

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
    case 1: /* Module M__3 */
      /* { */
        disconnect(0);
        disconnect(6);
      /* } */
      token = 2;
      break;
    case 2:
        // local blocking handlers:
        if(notDoneDisconnecting(0)) break;
        if(notDoneDisconnecting(6)) break;
      stateManager.sendState(3,0);
      token = 255;
      break;
    case 3: /* Module M__0 */
      stateManager.addPendingState(3);
      rotateDirTo(216,TRUE);
      token = 4;
      break;
    case 4:
      stateManager.sendState(6,6);
      token = 5; /* fall-through */
    case 5:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(3);
      token = 255;
      break;
    case 6: /* Module M__6 */
      rotateDirTo(216,TRUE);
      token = 7;
      break;
    case 7:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(216)) break;
      token = 8; /* fall-through */
    case 8: /* Module M__6 */
      stateManager.addPendingState(8);
      rotateDirTo(0,FALSE);
      token = 9;
      break;
    case 9:
      stateManager.sendState(11,0);
      token = 10; /* fall-through */
    case 10:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(8);
      token = 255;
      break;
    case 11: /* Module M__0 */
      rotateDirTo(0,FALSE);
      token = 12;
      break;
    case 12:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(13,3);
      token = 255;
      break;
    case 13: /* Module M__3 */
      /* { */
        connect(6);
        connect(0);
      /* } */
      token = 14;
      break;
    case 14:
        // local blocking handlers:
        if(notDoneConnecting(6)) break;
        if(notDoneConnecting(0)) break;
      token = 15; /* fall-through */
    case 15: /* Module M__3 */
      /* { */
        disconnect(0);
        disconnect(6);
      /* } */
      token = 16;
      break;
    case 16:
        // local blocking handlers:
        if(notDoneDisconnecting(0)) break;
        if(notDoneDisconnecting(6)) break;
      stateManager.sendState(17,0);
      token = 255;
      break;
    case 17: /* Module M__0 */
      stateManager.addPendingState(17);
      rotateDirTo(216,TRUE);
      token = 18;
      break;
    case 18:
      stateManager.sendState(20,6);
      token = 19; /* fall-through */
    case 19:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(17);
      token = 255;
      break;
    case 20: /* Module M__6 */
      rotateDirTo(216,TRUE);
      token = 21;
      break;
    case 21:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(216)) break;
      token = 22; /* fall-through */
    case 22: /* Module M__6 */
      stateManager.addPendingState(22);
      rotateDirTo(0,FALSE);
      token = 23;
      break;
    case 23:
      stateManager.sendState(25,0);
      token = 24; /* fall-through */
    case 24:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(22);
      token = 255;
      break;
    case 25: /* Module M__0 */
      rotateDirTo(0,FALSE);
      token = 26;
      break;
    case 26:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(27,3);
      token = 255;
      break;
    case 27: /* Module M__3 */
      /* { */
        connect(6);
        connect(0);
      /* } */
      token = 28;
      break;
    case 28:
        // local blocking handlers:
        if(notDoneConnecting(6)) break;
        if(notDoneConnecting(0)) break;
      token = 29; /* fall-through */
    case 29: /* Module M__3 */
      /* { */
        disconnect(0);
        disconnect(6);
      /* } */
      token = 30;
      break;
    case 30:
        // local blocking handlers:
        if(notDoneDisconnecting(0)) break;
        if(notDoneDisconnecting(6)) break;
      stateManager.sendState(31,0);
      token = 255;
      break;
    case 31: /* Module M__0 */
      stateManager.addPendingState(31);
      rotateDirTo(216,TRUE);
      token = 32;
      break;
    case 32:
      stateManager.sendState(34,6);
      token = 33; /* fall-through */
    case 33:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(31);
      token = 255;
      break;
    case 34: /* Module M__6 */
      rotateDirTo(216,TRUE);
      token = 35;
      break;
    case 35:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(216)) break;
      token = 36; /* fall-through */
    case 36: /* Module M__6 */
      stateManager.addPendingState(36);
      rotateDirTo(0,FALSE);
      token = 37;
      break;
    case 37:
      stateManager.sendState(39,0);
      token = 38; /* fall-through */
    case 38:
      if(!doneRotatingTo(0)) break;
      stateManager.removePendingState(36);
      token = 255;
      break;
    case 39: /* Module M__0 */
      rotateDirTo(0,FALSE);
      token = 40;
      break;
    case 40:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(41,3);
      token = 255;
      break;
    case 41: /* Module M__3 */
      /* { */
        connect(6);
        connect(0);
      /* } */
      token = 42;
      break;
    case 42:
        // local blocking handlers:
        if(notDoneConnecting(6)) break;
        if(notDoneConnecting(0)) break;

	
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

    else address = 127;
	myID = address;
	token = 255;
	stateManager.init(myID);
	
    api.setLeds(myID);
	reset_state();
	isDone = false;
}

  public void reset_sequence() {
    if ( myID == 3 ) {
      token = 0; // Tolerate reset in the middle of reconfiguration
    }    
    stateManager.reset_sequence();
}

  public void reset_state() {
    if ( myID == 3 ) {
      token = 0; // Tolerate reset in the middle of reconfiguration
    }    
    stateManager.reset_state();
}

  public boolean checkPendingStateResponsibility(int address, int pendingState) {
    if(address==6) {
      if(pendingState==36) return true;
      if(pendingState==22) return true;
      if(pendingState==8) return true;
      return false;
    }
    if(address==0) {
      if(pendingState==17) return true;
      if(pendingState==3) return true;
      if(pendingState==31) return true;
      return false;
    }
    return false;
  }

  int getLastState(int address) {
    if(address==0) return 39;
    if(address==3) return 41;
    if(address==6) return 34;
    return 255;
  }
  
  int getLastStateLowerBound(int address) {
    if(address==0) return 27;
    if(address==3) return 34;
    if(address==6) return 27;
    return 255;
  }


}
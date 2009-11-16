/* DynaRole Java code generated for carsnakeSimple */
/* DynaRole Java statemachine generated for carsnakeSimple */
package gen;

import rar.*;

public class carsnakeSimpleGen_par extends StateMachine {

  private static final int CENTER_ERROR_TOLERANCE = 5;
  private int token = 255;
  private int myID=-1;
  private boolean isDone = false; 
  private static final boolean TRUE = true;
  private static final boolean FALSE = false;
  
  private ATRONStateMachineAPI api;

  public void setAPI(Object api) {
    this.api = (ATRONStateMachineAPI)api;
  }

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
      disconnect(0);
      token = 2;
      break;
    case 2:
      stateManager.sendState(4,3);
      token = 3; /* fall-through */
    case 3:
      if(notDoneDisconnecting(0)) break;
      stateManager.removePendingState(1);
      token = 255;
      break;
    case 4: /* Module M__3 */
      disconnect(4);
      token = 5;
      break;
    case 5:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneDisconnecting(4)) break;
      token = 6; /* fall-through */
    case 6: /* Module M__3 */
      rotateDirTo(324,FALSE);
      token = 7;
      break;
    case 7:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(324)) break;
      stateManager.sendState(8,4);
      token = 255;
      break;
    case 8: /* Module M__4 */
      rotateDirTo(108,TRUE);
      token = 9;
      break;
    case 9:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(108)) break;
      token = 10; /* fall-through */
    case 10: /* Module M__4 */
      stateManager.addPendingState(10);
      connect(0);
      token = 11;
      break;
    case 11:
      stateManager.sendState(13,1);
      token = 12; /* fall-through */
    case 12:
      if(notDoneConnecting(0)) break;
      stateManager.removePendingState(10);
      token = 255;
      break;
    case 13: /* Module M__1 */
      stateManager.addPendingState(13);
      rotateDirTo(324,FALSE);
      token = 14;
      break;
    case 14:
      stateManager.sendState(16,6);
      token = 15; /* fall-through */
    case 15:
      if(!doneRotatingTo(324)) break;
      stateManager.removePendingState(13);
      token = 255;
      break;
    case 16: /* Module M__6 */
      disconnect(2);
      token = 17;
      break;
    case 17:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneDisconnecting(2)) break;
      stateManager.sendState(18,4);
      token = 255;
      break;
    case 18: /* Module M__4 */
      stateManager.addPendingState(18);
      rotateDirTo(216,TRUE);
      token = 19;
      break;
    case 19:
      stateManager.sendState(21,6);
      token = 20; /* fall-through */
    case 20:
      if(!doneRotatingTo(216)) break;
      stateManager.removePendingState(18);
      token = 255;
      break;
    case 21: /* Module M__6 */
      rotateDirTo(108,TRUE);
      token = 22;
      break;
    case 22:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(108)) break;
      stateManager.sendState(23,0);
      token = 255;
      break;
    case 23: /* Module M__0 */
        System.out.println("Starting connect 0");
      connect(0);
      System.out.println("Connect 0 done");
      token = 24;
      break;
    case 24:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneConnecting(0)) break;
      stateManager.sendState(25,6);
      token = 255;
      break;
    case 25: /* Module M__6 */
      disconnect(6);
      token = 26;
      break;
    case 26:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneDisconnecting(6)) break;
      stateManager.sendState(27,0);
      token = 255;
      break;
    case 27: /* Module M__0 */
      stateManager.addPendingState(27);
      rotateDirTo(324,FALSE);
      token = 28;
      break;
    case 28:
      stateManager.sendState(30,1);
      token = 29; /* fall-through */
    case 29:
      if(!doneRotatingTo(324)) break;
      stateManager.removePendingState(27);
      token = 255;
      break;
    case 30: /* Module M__1 */
      rotateDirTo(0,TRUE);
      token = 31;
      break;
    case 31:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(32,0);
      token = 255;
      break;
    case 32: /* Module M__0 */
      rotateDirTo(0,TRUE);
      token = 33;
      break;
    case 33:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(34,5);
      token = 255;
      break;
    case 34: /* Module M__5 */
      stateManager.addPendingState(34);
      connect(0);
      token = 35;
      break;
    case 35:
      stateManager.sendState(37,2);
      token = 36; /* fall-through */
    case 36:
      if(notDoneConnecting(0)) break;
      stateManager.removePendingState(34);
      token = 255;
      break;
    case 37: /* Module M__2 */
      stateManager.addPendingState(37);
      connect(4);
      token = 38;
      break;
    case 38:
      stateManager.sendState(40,1);
      token = 39; /* fall-through */
    case 39:
      if(notDoneConnecting(4)) break;
      stateManager.removePendingState(37);
      token = 255;
      break;
    case 40: /* Module M__1 */
      connect(4);
      token = 41;
      break;
    case 41:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneConnecting(4)) break;
      stateManager.sendState(42,4);
      token = 255;
      break;
    case 42: /* Module M__4 */
      disconnect(0);
      token = 43;
      break;
    case 43:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneDisconnecting(0)) break;
      stateManager.sendState(44,3);
      token = 255;
      break;
    case 44: /* Module M__3 */
      disconnect(6);
      token = 45;
      break;
    case 45:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneDisconnecting(6)) break;
      stateManager.sendState(46,1);
      token = 255;
      break;
    case 46: /* Module M__1 */
      stateManager.addPendingState(46);
      rotateDirTo(108,TRUE);
      token = 47;
      break;
    case 47:
      stateManager.sendState(49,3);
      token = 48; /* fall-through */
    case 48:
      if(!doneRotatingTo(108)) break;
      stateManager.removePendingState(46);
      token = 255;
      break;
    case 49: /* Module M__3 */
      rotateDirTo(0,TRUE);
      token = 50;
      break;
    case 50:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(51,1);
      token = 255;
      break;
    case 51: /* Module M__1 */
      connect(6);
      token = 52;
      break;
    case 52:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneConnecting(6)) break;
      stateManager.sendState(53,3);
      token = 255;
      break;
    case 53: /* Module M__3 */
      /* { */
        disconnect(0);
        disconnect(2);
      /* } */
      token = 54;
      break;
    case 54:
        // local blocking handlers:
        if(notDoneDisconnecting(0)) break;
        if(notDoneDisconnecting(2)) break;
      stateManager.sendState(55,1);
      token = 255;
      break;
    case 55: /* Module M__1 */
      rotateDirTo(216,TRUE);
      token = 56;
      break;
    case 56:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(216)) break;

	
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


}
/* DynaRole Java code generated for carsnakeSimple */
/* DynaRole Java statemachine generated for carsnakeSimple */
package gen;

import rar.*;

public class carsnakeSimpleGen_seq extends StateMachine {

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
      disconnect(0);
      token = 2;
      break;
    case 2:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneDisconnecting(0)) break;
      stateManager.sendState(3,3);
      token = 255;
      break;
    case 3: /* Module M__3 */
      disconnect(4);
      token = 4;
      break;
    case 4:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneDisconnecting(4)) break;
      token = 5; /* fall-through */
    case 5: /* Module M__3 */
      rotateDirTo(324,FALSE);
      token = 6;
      break;
    case 6:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(324)) break;
      stateManager.sendState(7,4);
      token = 255;
      break;
    case 7: /* Module M__4 */
      rotateDirTo(108,TRUE);
      token = 8;
      break;
    case 8:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(108)) break;
      token = 9; /* fall-through */
    case 9: /* Module M__4 */
      connect(0);
      token = 10;
      break;
    case 10:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneConnecting(0)) break;
      stateManager.sendState(11,1);
      token = 255;
      break;
    case 11: /* Module M__1 */
      rotateDirTo(324,FALSE);
      token = 12;
      break;
    case 12:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(324)) break;
      stateManager.sendState(13,6);
      token = 255;
      break;
    case 13: /* Module M__6 */
      disconnect(2);
      token = 14;
      break;
    case 14:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneDisconnecting(2)) break;
      stateManager.sendState(15,4);
      token = 255;
      break;
    case 15: /* Module M__4 */
      rotateDirTo(216,TRUE);
      token = 16;
      break;
    case 16:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(216)) break;
      stateManager.sendState(17,6);
      token = 255;
      break;
    case 17: /* Module M__6 */
      rotateDirTo(108,TRUE);
      token = 18;
      break;
    case 18:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(108)) break;
      stateManager.sendState(19,0);
      token = 255;
      break;
    case 19: /* Module M__0 */
      connect(0);
      token = 20;
      break;
    case 20:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneConnecting(0)) break;
      stateManager.sendState(21,6);
      token = 255;
      break;
    case 21: /* Module M__6 */
      disconnect(6);
      token = 22;
      break;
    case 22:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneDisconnecting(6)) break;
      stateManager.sendState(23,0);
      token = 255;
      break;
    case 23: /* Module M__0 */
      rotateDirTo(324,FALSE);
      token = 24;
      break;
    case 24:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(324)) break;
      stateManager.sendState(25,1);
      token = 255;
      break;
    case 25: /* Module M__1 */
      rotateDirTo(0,TRUE);
      token = 26;
      break;
    case 26:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(27,0);
      token = 255;
      break;
    case 27: /* Module M__0 */
      rotateDirTo(0,TRUE);
      token = 28;
      break;
    case 28:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(29,5);
      token = 255;
      break;
    case 29: /* Module M__5 */
      connect(0);
      token = 30;
      break;
    case 30:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneConnecting(0)) break;
      stateManager.sendState(31,2);
      token = 255;
      break;
    case 31: /* Module M__2 */
      connect(4);
      token = 32;
      break;
    case 32:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneConnecting(4)) break;
      stateManager.sendState(33,1);
      token = 255;
      break;
    case 33: /* Module M__1 */
      connect(4);
      token = 34;
      break;
    case 34:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneConnecting(4)) break;
      stateManager.sendState(35,4);
      token = 255;
      break;
    case 35: /* Module M__4 */
      disconnect(0);
      token = 36;
      break;
    case 36:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneDisconnecting(0)) break;
      stateManager.sendState(37,3);
      token = 255;
      break;
    case 37: /* Module M__3 */
      disconnect(6);
      token = 38;
      break;
    case 38:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneDisconnecting(6)) break;
      stateManager.sendState(39,1);
      token = 255;
      break;
    case 39: /* Module M__1 */
      rotateDirTo(108,TRUE);
      token = 40;
      break;
    case 40:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(108)) break;
      stateManager.sendState(41,3);
      token = 255;
      break;
    case 41: /* Module M__3 */
      rotateDirTo(0,TRUE);
      token = 42;
      break;
    case 42:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(43,1);
      token = 255;
      break;
    case 43: /* Module M__1 */
      connect(6);
      token = 44;
      break;
    case 44:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneConnecting(6)) break;
      stateManager.sendState(45,3);
      token = 255;
      break;
    case 45: /* Module M__3 */
      disconnect(0);
      token = 46;
      break;
    case 46:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneDisconnecting(0)) break;
      token = 47; /* fall-through */
    case 47: /* Module M__3 */
      disconnect(2);
      token = 48;
      break;
    case 48:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneDisconnecting(2)) break;
      stateManager.sendState(49,1);
      token = 255;
      break;
    case 49: /* Module M__1 */
      rotateDirTo(216,TRUE);
      token = 50;
      break;
    case 50:
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
    if ( myID == 0) {
      token = 0; // Tolerate reset in the middle of reconfiguration
    }    
    stateManager.reset_state();
}


}
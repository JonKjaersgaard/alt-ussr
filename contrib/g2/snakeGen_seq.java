/* DynaRole Java code generated for carsnakeSimple */
/* DynaRole Java statemachine generated for carsnakeSimple */
package g2;

import rar.*;

public class snakeGen_seq extends StateMachine {

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
      disconnect(0);
      token = 2;
      break;
    case 2:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneDisconnecting(0)) break;
      token = 3; /* fall-through */
    case 3: /* Module M__3 */
      disconnect(6);
      token = 4;
      break;
    case 4:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneDisconnecting(6)) break;
      stateManager.sendState(5,0);
      token = 255;
      break;
    case 5: /* Module M__0 */
      rotateDirTo(216,TRUE);
      token = 6;
      break;
    case 6:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(216)) break;
      stateManager.sendState(7,6);
      token = 255;
      break;
    case 7: /* Module M__6 */
      rotateDirTo(216,TRUE);
      token = 8;
      break;
    case 8:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(216)) break;
      token = 9; /* fall-through */
    case 9: /* Module M__6 */
      rotateDirTo(0,FALSE);
      token = 10;
      break;
    case 10:
      if(stateManager.pendingStatesPresent()) break;
      if(!doneRotatingTo(0)) break;
      stateManager.sendState(11,0);
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
      connect(6);
      token = 14;
      break;
    case 14:
      if(stateManager.pendingStatesPresent()) break;
      if(notDoneConnecting(6)) break;
      token = 15; /* fall-through */
    case 15: /* Module M__3 */
      connect(0);
      token = 16;
      break;
    case 16:
      if(stateManager.pendingStatesPresent()) break;
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
    return false;
  }

  int getLastState(int address) {
    if(address==0) return 11;
    if(address==3) return 13;
    if(address==6) return 7;
    return 255;
  }
  
  int getLastStateLowerBound(int address) {
    if(address==0) return 7;
    if(address==3) return 7;
    if(address==6) return 5;
    return 255;
  }


}
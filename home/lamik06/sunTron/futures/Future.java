package sunTron.futures;

import sunTron.api.ISunTronAPI;



public abstract class Future extends Thread implements IFuture{
	Thread threadFuture;
	IFutureAction futureAction;
	long futureStartTime;
	/*
	 * Future timeout:
	 * Default = 30 sec. 
	 * Disable when set to 0 sec.
	 */
	long timeoutInMiliSec = 30000; 
	protected ISunTronAPI sunTronAPI;
	
	
	public void setTimeoutMiliSec(long timeInMiliSec){
		this.timeoutInMiliSec = timeInMiliSec;
	}
	public void block() {
//		sunTronAPI.addActiveFuturesTable(getKey(),(Future)this);
		while(!isCompleted()){
			yield();
		}
//		sunTronAPI.removeActiveFuturesTable(getKey());
	}
	
	public void waitFor(Future f) {
//		sunTronAPI.addActiveFuturesTable(getKey(),(Future)this);
		if (!f.isAlive()) f.onCompletion(new FutureAction(){public void execute(){}});
		while(f.isAlive() || !isCompleted()){
			yield();
		}
//		sunTronAPI.removeActiveFuturesTable(getKey());
	}
	
	
	public void onCompletion(FutureAction futureAction) {
		this.futureAction = futureAction;
		futureStartTime = System.currentTimeMillis();
//		sunTronAPI.addActiveFuturesTable(getKey(),(Future)this);
		if (!isAlive()) start();
	}
	public void onCompletion(FutureAction futureAction, long timeInMiliSec) {
		setTimeoutMiliSec(timeInMiliSec);
		onCompletion(futureAction);
	}
	public void  run() {
		while(true){
			long tmpTime = System.currentTimeMillis();
			if(isCompleted()){
				futureAction.execute();
				break;
			}
			if(tmpTime-futureStartTime > timeoutInMiliSec
					&& timeoutInMiliSec != 0){ // Disable timeOut
				futureAction.timeout();
				break;
			}
			yield();
		}
//		sunTronAPI.removeActiveFuturesTable(getKey());
		
	}
	/*
	 * Example on a waitForFutures() for two futures.
	 * onCompletion() have to be invoked before wait(f,f1)
	 */
	public static void waitForFutures(Future f,Future f1){
		if (!f.isAlive()) f.onCompletion(new FutureAction(){public void execute(){}});
		if (!f1.isAlive()) f.onCompletion(new FutureAction(){public void execute(){}});
		while (f.isAlive() || f1.isAlive()){
			yield();
		}
	}

}

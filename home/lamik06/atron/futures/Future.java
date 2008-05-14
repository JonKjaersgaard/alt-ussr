package atron.futures;

import atron.spot.ISunTronAPI;



public abstract class Future extends Thread implements IFutures{
	Thread threadFuture;
	IFutureActions futureAction;
	long futureStartTime;
	/*
	 * Future timeout:
	 * Default = 30 sec. 
	 * Disable when set to 0 sec.
	 */
	long timeOutInMiliSec = 30000; 
	protected ISunTronAPI atronAPI;
	
	
	public void setTimeOutMiliSec(long timeInMiliSec){
		this.timeOutInMiliSec = timeInMiliSec;
	}
	public void waitForCompletion() {
		while(!isCompleted()){
			yield();
		}
		atronAPI.removeActiveFuturesTable(getKey());
	}
	
	public void onCompletion(IFutureActions futureAction) {
		this.futureAction = futureAction;
		futureStartTime = System.currentTimeMillis();
		start();
	}
	public void  run() {
		while(true){
			long tmpTime = System.currentTimeMillis();
			if(isCompleted()){
				futureAction.execute();
				break;
			}
			if(tmpTime-futureStartTime > timeOutInMiliSec
					&& timeOutInMiliSec != 0){ // Disable timeOut
				futureAction.timeOutHandler();
				break;
			}
			yield();
		}
		atronAPI.removeActiveFuturesTable(getKey());
		
	}
	/*
	 * Example on a wait() for two futures.
	 * onCompletion() have to be invoked before wait(f,f1)
	 */
	public static void wait(Future f,Future f1){
		while (f.isAlive() || f1.isAlive()){
			yield();
		}
	}
}

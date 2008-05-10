package atron.futures;

import atron.spot.ISunTRONAPI;



public abstract class ATRONFutures extends Thread implements IATRONFutures{
	Thread threadFuture;
	IFutureActions futureAction;
	long futureStartTime;
	long timeOutInMiliSec = 5;//30000; //Default 30 sec. Disable 0 sec. 
	protected ISunTRONAPI atronAPI;
	
	
	public void setTimeOutMiliSec(long timeInMiliSec){
		this.timeOutInMiliSec = timeInMiliSec;
	}
	public void waitForCompletion() {
		while(!isCompleted()){
			yield();
		}
		atronAPI.removeActiveFuturesTable(getKey());
	}
	
	
	public abstract boolean isCompleted();
	
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
	 * Example on a wait() for multiple futures
	 * onCompletion() have to be invoked
	 */
	public static void wait(ATRONFutures f,ATRONFutures f1){
		while (f.isAlive() || f1.isAlive()){
			yield();
		}
	}
}

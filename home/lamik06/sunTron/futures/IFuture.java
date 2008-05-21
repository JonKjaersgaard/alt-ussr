package sunTron.futures;

public interface IFuture {

	public void block();
	public void waitFor(Future f);
	public boolean isCompleted();
	public void onCompletion(FutureAction command);
	public void onCompletion(FutureAction futureAction, long timeInMiliSec);
	public void setTimeoutMiliSec(long timeInMiliSec);
	public String getKey();
	
	
}
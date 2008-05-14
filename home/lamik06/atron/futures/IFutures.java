package atron.futures;

public interface IFutures {

	public void waitForCompletion();
	public boolean isCompleted();
	public void onCompletion(IFutureActions command);
	public void setTimeOutMiliSec(long timeInMiliSec);
	public String getKey();
	
}
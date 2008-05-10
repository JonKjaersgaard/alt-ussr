package atron.futures;

public interface IATRONFutures {

	public void waitForCompletion();
	public boolean isCompleted();
	public void onCompletion(IFutureActions command);
	public void setTimeOutMiliSec(long timeInMiliSec);
	public String getKey();
	
}
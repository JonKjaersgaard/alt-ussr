package atron.futures;

public interface IATRONFutures {

	public void waitForCompletion();
	public void onCompletion(ICommand command);
	public void setTimeOut(int timeInSec);
	public String getKey();
	
}
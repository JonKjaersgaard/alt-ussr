package atron.futures;

public abstract class ATRONFutures extends Thread{
	Thread threadFuture;
	
	abstract public boolean isAvalible();
	abstract public void waitForComplition();
	abstract public void onCompletion(ICommand command);
	abstract public void  run();

}

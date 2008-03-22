package atron.futures;

public abstract class ATRONFutures {
	abstract public boolean isAvalible();
	abstract public void waitForComplition();
	abstract public void onComplition(ICommand command);

}

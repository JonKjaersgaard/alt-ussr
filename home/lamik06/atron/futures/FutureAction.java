package atron.futures;

public abstract class FutureAction implements IFutureActions{
	public abstract void execute ( );
	public void timeOutHandler(){
		System.out.println("Debuginfo: Future time out");
	}
}

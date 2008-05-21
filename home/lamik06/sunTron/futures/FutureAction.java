package sunTron.futures;

public abstract class FutureAction implements IFutureAction{
	public abstract void execute ( );
	public void timeout(){
		System.out.println("Debuginfo: Future timeout");
	}
}

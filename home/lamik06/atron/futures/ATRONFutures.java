package atron.futures;

import ussr.samples.atron.IATRONAPI;


public abstract class ATRONFutures extends Thread{
	Thread threadFuture;
	ICommand command;
	protected IATRONAPI atronAPI;
	
	abstract public void waitForCompletion();
//	abstract public void onCompletion(ICommand command);
//	abstract public void  run();
	public void onCompletion(ICommand command) {
		this.command = command;
		start();
	}
	public void  run() {
		waitForCompletion();
		command.execute();
	}

}

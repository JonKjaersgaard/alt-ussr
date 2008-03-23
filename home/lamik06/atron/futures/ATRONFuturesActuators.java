package atron.futures;

public class ATRONFuturesActuators extends ATRONFutures{
	int connectorNo;
	public ATRONFuturesActuators(int i) {
		connectorNo = i;
	}

	@Override
	public boolean isAvalible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void waitForComplition() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onCompletion(ICommand command) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}

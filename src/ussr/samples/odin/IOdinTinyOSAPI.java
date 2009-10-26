package ussr.samples.odin;

/*
 * notes about C to Java type conversion:
 * error_t -> int
 * message_t -> byte[]
 * pointers -> long
 * for any other numerical type use int, unless it's a uint32_t ()
 */

public interface IOdinTinyOSAPI {

	/* error_t values (from TinyError.h) */
	static final int  SUCCESS        =  0;          
	static final int  FAIL           =  1;           // Generic condition: backwards compatible
	static final int  ESIZE          =  2;           // Parameter passed in was too big.
	static final int  ECANCEL        =  3;           // Operation canceled by a call.
	static final int  EOFF           =  4;           // Subsystem is not active
	static final int  EBUSY          =  5;           // The underlying system is busy; retry later
	static final int  EINVAL         =  6;           // An invalid parameter was passed
	static final int  ERETRY         =  7;           // A rare and transient failure: can retry
	static final int  ERESERVE       =  8;           // Reservation required before usage
	static final int  EALREADY       =  9;           // The device state you are requesting is already set
	static final int  ENOMEM         = 10;           // Memory required not available
	static final int  ENOACK         = 11;           // A packet was not acknowledged
	static final int  ELAST          = 11;           // Last enum value
	
	/**
	 * Send a message on a given connector
	 * @param message the buffer containing the bytes to send
	 * @param messageSize the number of bytes to send from the buffer
	 * @param connector the connector to send the message on
	 * @return error_t return code
	 */
	public int sendMessage(byte[] msg, int msgSize, int connector);
	
	/* event */
	public void sendDone(byte[] msg, int error, int connector);
	
	/* event */
	public void handleMessage(byte[] message, int messageSize, int connector);

}

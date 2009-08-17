/* Send commands */
error_t send(message_t* msg, uint8_t len) {
}

/**
 * Cancel a requested transmission. Returns SUCCESS if the 
 * transmission was cancelled properly (not sent in its
 * entirety). Note that the component may not know
 * if the send was successfully cancelled, if the radio is
 * handling much of the logic; in this case, a component
 * should be conservative and return an appropriate error code.
 *
 * @param   'message_t* ONE msg'    the packet whose transmission should be cancelled
 * @return         SUCCESS if the packet was successfully cancelled, FAIL
 *                 otherwise
 */
error_t cancel(message_t* msg) {
}

/**
 * Return the maximum payload length that this communication layer
 * can provide. This command behaves identically to
 * <tt>Packet.maxPayloadLength</tt> and is included in this
 * interface as a convenience.
 *
 * @return  the maximum payload length
 */


uint8_t maxPayloadLength() {
}


/**
 * Return a pointer to a protocol's payload region in a packet which
 * at least a certain length.  If the payload region is smaller than
 * the len parameter, then getPayload returns NULL. This command
 * behaves identicallt to <tt>Packet.getPayload</tt> and is
 * included in this interface as a convenience.
 *
 * @param   'message_t* ONE msg'    the packet
 * @return  'void* COUNT_NOK(len)'  a pointer to the packet's payload
 */
void* getPayload(message_t* msg, uint8_t len) {
}

/* Send events */
/** 
 * Signaled in response to an accepted send request. <tt>msg</tt>
 * is the sent buffer, and <tt>error</tt> indicates whether the
 * send was succesful, and if not, the cause of the failure.
 * 
 * @param 'message_t* ONE msg'   the message which was requested to send
 * @param error SUCCESS if it was transmitted successfully, FAIL if
 *              it was not, ECANCEL if it was cancelled via <tt>cancel</tt>
 */ 
void sendDone(message_t* msg, error_t error) {
}


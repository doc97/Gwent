package fi.riissanen.gwent.engine.net;

/**
 * Used to transfer the client ID on the server to the client.
 * @author Daniel
 */
public class ConnectionPacket extends Packet {

    private final int receiverID;
    
    /**
     * Creates a packet with a sender and receiver id.
     * @param senderID The ID of the sender
     * @param receiverID The ID of the receiver
     */
    public ConnectionPacket(int senderID, int receiverID) {
        super(senderID);
        this.receiverID = receiverID;
    }
    
    public int getReceiverID() {
        return receiverID;
    }
}

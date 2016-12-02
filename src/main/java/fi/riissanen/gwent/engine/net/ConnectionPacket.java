package fi.riissanen.gwent.engine.net;

/**
 * Used to transfer the client ID on the server to the client
 * @author Daniel
 */
public class ConnectionPacket extends Packet {

    private final int clientID;
    
    public ConnectionPacket(int senderID, int clientID) {
        super(senderID);
        this.clientID = clientID;
    }
    
    public int getClientID() {
        return clientID;
    }
}

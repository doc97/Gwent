package fi.riissanen.gwent.engine.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Functions as the server in a TCP/IP() network.
 * @author Daniel
 */
public class Server implements PacketListener {

    private static final int MAX_CLIENTS = 100;
    private final Map<Integer, ClientHandler> clients = new HashMap<>();
    private PacketListener listener;
    private ServerSocket socket;
    
    /**
     * Starts to listen for incoming connection on a port.
     * @param port The port on which to listen
     * @throws IOException If an error occurs while waiting for a connection
     */
    public void listen(int port) throws IOException {
        socket = new ServerSocket(port);
        while (true) {
            Socket client = socket.accept();
            int id = getFreeID();
            if (id == -1) {
                client.close();
                continue;
            }
            System.out.println("Client connected with id: " + id);
            ClientHandler handler = new ClientHandler(this, client, id);
            handler.start();
            handler.sendPacket(new ConnectionPacket(id, handler.getID()));
            clients.put(id, handler);
        }
    }
    
    /**
     * Closes the server socket.
     * @throws IOException If an error occurs while closing the socket
     */
    public void close() throws IOException {
        socket.close();
    }
    
    /**
     * Frees the client handler for a certain id.
     * @param id The id of the client (handler)
     */
    public void freeClient(int id) {
        System.out.println("Client with id " + id + " disconnected!");
        clients.remove(id);
    }
    
    /**
     * Sets the listener to which packets are forwarded.
     * @param listener Listener to forward to
     */
    public void setPacketListener(PacketListener listener) {
        if (!listener.equals(this)) {
            this.listener = listener;
        }
    }
    
    private int getFreeID() {
        int id = getID() + 1;
        do {
            if (!clients.containsKey(id)) {
                return id;
            } else {
                id++;
            }
        } while (id < MAX_CLIENTS);
        return -1;
    }
    
    /**
     * Gets the server's id, clients start from 1.
     * @return 0
     */
    public int getID() {
        return 0;
    }
    
    /**
     * Sends a packet to the client with a certain id.
     * @param targetClient The ID of the client
     * @param packet The packet to send
     * @return The success of adding the packet to the queue
     */
    public boolean sendPacket(int targetClient, Packet packet) {
        if (!clients.containsKey(targetClient)) {
            throw new IllegalArgumentException("Can't send packet to client with ID: " + targetClient);
        }
        if (packet.getSenderID() != getID()) {
            throw new IllegalArgumentException("Trying to send packet from server with different sender ID than the server's");
        }
        return clients.get(targetClient).sendPacket(packet);
    }
    
    /**
     * Gets a {@code ClientHandler} by id.
     * @param id The client id
     * @return The client handler
     */
    public ClientHandler getClient(int id) {
        return clients.get(id);
    }

    @Override
    public void receivedPacket(Packet packet) {
        if (listener != null) {
            listener.receivedPacket(packet);
        }
    }
}

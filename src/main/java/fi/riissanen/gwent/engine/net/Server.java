package fi.riissanen.gwent.engine.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Functions as the server in a TCP/IP() network
 * @author Daniel
 */
public class Server implements PacketListener {

    private static final int MAX_CLIENTS = 100;
    private final Map<Integer, ClientHandler> clients = new HashMap<>();
    private PacketListener listener;
    private ServerSocket socket;
    
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
    
    public void close() throws IOException {
        socket.close();
    }
    
    public void freeClient(int id) {
        System.out.println("Client with id " + id + " disconnected!");
        clients.remove(id);
    }
    
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
    
    public int getID() {
        return 0;
    }
    
    public boolean sendPacket(int targetClient, Packet packet) {
        if (!clients.containsKey(targetClient)) {
            throw new IllegalArgumentException("Can't send packet to client with ID: " + targetClient);
        }
        if (packet.getSenderID() != getID()) {
            throw new IllegalArgumentException("Trying to send packet from server with different sender ID than the server's");
        }
        return clients.get(targetClient).sendPacket(packet);
    }
    
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

package fi.riissanen.gwent.engine.net;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Represents a client in a TCP/IP() network.
 * @author Daniel
 */
public class Client implements PacketListener, PacketSender {

    private final Queue<Packet> empty;
    private final Queue<Packet> packets;
    private final List<Packet> falseIDPackets;
    private PacketListener listener;
    private Socket socket;
    private int id;
    private boolean waitForID = true;
    
    /**
     * Sets id to -1.
     */
    public Client() {
        id = -1;
        empty = new ConcurrentLinkedQueue<>();
        packets = new ConcurrentLinkedQueue<>();
        falseIDPackets = new ArrayList<>();
    }
    
    /**
     * Attempts to connect to a server.
     * 
     * <p>
     * The hostname can be a DNS name ("localhost") or an IP address
     * ("127.0.0.1"). Creates two threads, for handling the input and
     * output streams of the client {@code Socket}.
     * @param hostname The hostname of the server
     * @param port The port to connect to
     * @throws IOException If the connection attempts fails
     */
    public void connect(String hostname, int port) throws IOException {
        socket = new Socket(hostname, port);
        InputPacketAdapter input = new InputPacketAdapter(socket, this);
        OutputPacketAdapter output = new OutputPacketAdapter(socket, this);
        Thread inputThread = new Thread(input);
        Thread outputThread = new Thread(output);
        inputThread.start();
        outputThread.start();
    }
    
    /**
     * Closes the socket connection to the server.
     * @throws IOException If an I/O error occurs during closing of the socket
     */
    public void disconnect() throws IOException {
        socket.close();
        id = -1;
    }
    
    /**
     * Tries to queue a packet to be sent.
     * @param packet The packet to send
     * @return The success of adding the packet to the queue
     */
    public synchronized boolean sendPacket(Packet packet) {
        boolean added = packets.offer(packet);
        if (!hasID() && added) {
            falseIDPackets.add(packet);
        }
        return added;
    }
    
    /**
     * Sets a packet listener to which the client will redirect packets.
     * 
     * <p>
     * Note: The listener cannot be the client itself
     * @param listener The packet listener
     */
    public void setPacketListener(PacketListener listener) {
        if (!listener.equals(this)) {
            this.listener = listener;
        }
    }
    
    public String getHostName() {
        return socket.getInetAddress().getHostName();
    }
    
    public int getPort() {
        return socket.getPort();
    }
    
    public synchronized int getID() {
        return id;
    }
    
    /**
     * Returns id status.
     * @return True if client has gotten an id from the server
     */
    public synchronized boolean hasID() {
        return id != -1;
    }
    
    @Override
    public synchronized boolean isReady() {
        return hasID();
    }

    @Override
    public Queue<Packet> getQueuedPackets() {
        if (!hasID()) {
            return empty;
        }
        
        for (Packet packet : falseIDPackets) {
            packet.setSenderID(id);
        }
        return packets;
    }

    @Override
    public void receivedPacket(Packet packet) {
        if (waitForID) {
            if (packet instanceof ConnectionPacket) {
                id = ((ConnectionPacket) packet).getReceiverID();
                waitForID = false;
            }
        }
        if (listener != null) {
            listener.receivedPacket(packet);
        }
    }
}

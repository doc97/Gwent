package fi.riissanen.gwent.engine.net;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Represents a client in a TCP/IP() network
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
    
    public Client() {
        id = -1;
        empty = new ConcurrentLinkedQueue<>();
        packets = new ConcurrentLinkedQueue<>();
        falseIDPackets = new ArrayList<>();
    }
    
    public void connect(String hostname, int port) throws IOException {
        socket = new Socket(hostname, port);
        InputPacketAdapter input = new InputPacketAdapter(socket, this);
        OutputPacketAdapter output = new OutputPacketAdapter(socket, this);
        Thread inputThread = new Thread(input);
        Thread outputThread = new Thread(output);
        inputThread.start();
        outputThread.start();
    }
    
    public synchronized void disconnect() throws IOException {
        socket.close();
    }
    
    public synchronized boolean sendPacket(Packet packet) {
        boolean added = packets.offer(packet);
        if (!hasID() && added) {
            falseIDPackets.add(packet);
        }
        return added;
    }
    
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
                id = ((ConnectionPacket) packet).getClientID();
                waitForID = false;
            }
        }
        if (listener != null) {
            listener.receivedPacket(packet);
        }
    }
}

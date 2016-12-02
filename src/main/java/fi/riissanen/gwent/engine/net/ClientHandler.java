package fi.riissanen.gwent.engine.net;

import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A handler that handles one client connection on the server.
 * @author Daniel
 */
public class ClientHandler implements PacketSender {

    private final Queue<Packet> packets;
    private final Socket socket;
    private final Server server;
    private InputPacketAdapter input;
    private OutputPacketAdapter output;
    private final int id;
    
    /**
     * Prepares the handler.
     * @param server The server instance that owns this handler
     * @param socket The client socket connection that it handles
     * @param id The ID of the client
     */
    public ClientHandler(Server server, Socket socket, int id) {
        this.server = server;
        this.socket = socket;
        this.id = id;
        packets = new ConcurrentLinkedQueue<>();
    }
    
    /**
     * Starts servicing the client through two threads.
     */
    public void start() {
        if (!socket.isConnected() || socket.isClosed()) {
            System.out.println("Cannot handle a closed connection");
            return;
        }
        
        input = new InputPacketAdapter(socket, server);
        output = new OutputPacketAdapter(socket, this);
        Thread inputThread = new Thread(input);
        Thread outputThread = new Thread(output);
        inputThread.start();
        outputThread.start();
    }

    /**
     * Stops handling a client by closing the socket.
     * 
     * <p> It also removes itself from the server's list of handlers to
     * allow other handlers to be bound to that ID
     * @see Server#freeClient(int) 
     */
    public synchronized void stop() {
        try {
            socket.close();
        } catch (IOException ex) {
        } finally {
            server.freeClient(id);
        }
    }
    
    /**
     * Tries to queue a packet for sending.
     * @param packet The packet to send
     * @return The success of adding the packet to the queue
     */
    public synchronized boolean sendPacket(Packet packet) {
        return packets.offer(packet);
    }
    
    public int getID() {
        return id;
    }
    
    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public Queue<Packet> getQueuedPackets() {
        return packets;
    }
}

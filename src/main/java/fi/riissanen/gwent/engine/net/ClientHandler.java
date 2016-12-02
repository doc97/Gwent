package fi.riissanen.gwent.engine.net;

import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A handler that handles one client connection on the server
 * @author Daniel
 */
public class ClientHandler implements PacketSender {

    private final Queue<Packet> packets;
    private final Socket socket;
    private final Server server;
    private InputPacketAdapter input;
    private OutputPacketAdapter output;
    private final int id;
    
    public ClientHandler(Server server, Socket socket, int id) {
        this.server = server;
        this.socket = socket;
        this.id = id;
        packets = new ConcurrentLinkedQueue<>();
    }
    
    public void start() throws IOException {
        if (!socket.isConnected() || socket.isClosed()) {
            System.out.println("Stop");
            return;
        }
        
        input = new InputPacketAdapter(socket, server);
        output = new OutputPacketAdapter(socket, this);
        Thread inputThread = new Thread(input);
        Thread outputThread = new Thread(output);
        inputThread.start();
        outputThread.start();
    }

    public synchronized void stop() {
        try {
            socket.close();
        } catch (IOException ex) {
        } finally {
            server.freeClient(id);
        }
    }
    
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

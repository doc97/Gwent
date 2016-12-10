package fi.riissanen.gwent.game.net;

import fi.riissanen.gwent.engine.net.Client;
import fi.riissanen.gwent.engine.net.Packet;
import fi.riissanen.gwent.engine.net.PacketListener;
import java.io.IOException;
import java.util.Scanner;

 /**
 * Tests the Client of the engine.
 * @author Daniel
 */
public class ClientTest implements PacketListener {

    private final Client client;
    private final Scanner scanner;
    
    /**
     * Constructor.
     */
    public ClientTest() {
        client = new Client();
        client.setPacketListener(this);
        scanner = new Scanner(System.in);
    }
    
    /**
     * Connects to a server.
     * @param host The host address of the server.
     * @param port The port on which the host is listening on
     */
    public void connect(String host, int port) {
        try {
            System.out.println("Connecting to " + host + ":" + port);
            client.connect(host, port);
        } catch (IOException ex) {
            System.err.println("Failed to connect");
        }
    }
    
    public Client getClient() {
        return client;
    }
    
    @Override
    public void receivedPacket(Packet packet) {
        if (packet instanceof StringPacket) {
            System.out.println("Server: " + ((StringPacket) packet).getMessage());

            String message = scanner.nextLine();
            client.sendPacket(new StringPacket(client.getID(), message));
            System.out.println("Me:     " + message);
        }
    }
    
    /**
     * Main method.
     * @param args The command-line arguments 
     */
    public static void main(String[] args) {
        ClientTest test = new ClientTest();
        test.connect("localhost", 6666);
        test.getClient().sendPacket(new StringPacket(test.getClient().getID(), "Good morning."));
        System.out.println("Me:     Good morning.");
    }
}

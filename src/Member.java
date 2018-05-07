import java.io.IOException;
import java.net.*;

public class Member implements Runnable{
    private InetAddress global_address;
    private String my_address;

    private Thread t;
    private String threadName;
    MulticastSocket clientSocket;

    final private int PORT = 8888;
    Member() throws IOException {
        String INET_ADDR = "224.1.2.3";
        this.my_address = InetAddress.getLocalHost().getHostAddress();
        this.threadName = this.my_address.toString();
        this.global_address = InetAddress.getByName(INET_ADDR);
        this.clientSocket = new MulticastSocket(this.PORT);
        this.clientSocket.joinGroup(this.global_address);
    }

    @Override
    public void run(){
        byte[] buffer = new byte[256];

            while (true) {
                // Receive the information and print it.
                DatagramPacket msgPacket = new DatagramPacket(buffer, buffer.length);
                try {
                    this.clientSocket.receive(msgPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String msg = new String(buffer, 0, buffer.length);
                if(msg.trim().equals(my_address)) continue;

                System.out.println("new Address: " + msg);
            }

    }

    public void sendMyAddress(){
        try {
            DatagramSocket serverSocket = new DatagramSocket();
            DatagramPacket msgPacket = new DatagramPacket(this.my_address.getBytes(), this.my_address.getBytes().length, this.global_address, this.PORT);
            serverSocket.send(msgPacket);
            System.out.println("My Address: " + this.my_address);
            Thread.sleep(500);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start () {
        System.out.println("Starting " +  this.threadName );
        sendMyAddress();
        if (this.t == null) {
            this.t = new Thread(this, this.threadName);
            this.t.start();
        }
    }
}

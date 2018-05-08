import java.net.*;
import java.io.*;

public class Server implements Runnable{
    private Socket socket;
    private ServerSocket server;
    private DataInputStream in;
    final private int port = 8888;
    private Thread t;
    private String threadName;

    Server(String name){
        this.threadName = name;
        try {
            this.server = new ServerSocket(this.port);
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            socket = server.accept();

            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            String input = "";
            while(!input.equals("test")) {
                input = in.readUTF();
            }
            socket.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        if(t == null){
            t = new Thread(this, this.threadName);
            t.start();
        }
    }
}

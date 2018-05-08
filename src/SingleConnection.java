import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

class SingleConnection implements Runnable{

    private Thread t;
    private String threadName;
    private Socket socket;
    private InetAddress address;
    private DataInputStream input = null;
    private DataOutputStream out = null;
    final private int port = 8888;

    SingleConnection(String otherSide) throws UnknownHostException {
        this.threadName = otherSide;
        address = InetAddress.getByName(this.threadName);

        start();
    }


    @Override
    public void run(){
        try {
            this.socket = new Socket(this.address, this.port);
            input  = new DataInputStream(System.in);
            out    = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String in = "";
        while(!in.equals('\0'))
        {
            try
            {
                in = input.readUTF();
                out.writeUTF(in);
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }
    }

    public void start(){
        if (this.t == null) {
            this.t = new Thread(this, this.threadName);
            this.t.start();
        }
    }
}

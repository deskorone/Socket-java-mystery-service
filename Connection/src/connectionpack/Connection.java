package connectionpack;

import java.io.*;
import java.net.Socket;

public class Connection implements Closeable{
    private final Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;


    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    public Socket getSocket() {
        return socket;
    }

    public synchronized void sendMsg(Message message) throws IOException{
        synchronized (this.out){
            out.writeObject(message);
            out.flush();
        }
    }

    public Message getMsg() throws  IOException, ClassNotFoundException{
        synchronized (this.in){
            Message msg = (Message) in.readObject();
            return msg;
        }
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    @Override
    public String toString(){
        return "Port" + this.socket.getPort();
    }
}

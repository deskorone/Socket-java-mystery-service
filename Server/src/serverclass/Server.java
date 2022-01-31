package serverclass;

import connectionpack.Connection;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    Logger logger = Logger.getLogger(Server.class);
    private ServerSocket serverSocket;
    private boolean serverIsRun = false;

    public static void main(String[] args) {
        new Server(9020);
    }

    public boolean isServerIsRun() {
        return serverIsRun;
    }

    public void setServerIsRun(boolean serverIsRun) {
        this.serverIsRun = serverIsRun;
    }

    private Server(int port){
        try {
            runServer(port);
            while(true){
                if(isServerIsRun()){
                    getAcceptSocket();
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public  class ServerSocketRun extends Thread{
        private Socket socket;
        private Connection connection;
        public ServerSocketRun(Socket socket){this.socket = socket;}

        @Override
        public void run() {
            try {
                this.connection = new Connection(this.socket);
                System.out.println("User connected");
                System.out.println(connection.getSocket().getPort());
                Logica logica = new ServerLogicClass(connection);
                if (logica.register() ) {
                    logica.choise();
                    connection.close();
                } else {
                    connection.close();
                }

            } catch (IOException e) {
                logger.error("UNCORRECTED SOCKET CONNECTION");
                e.printStackTrace();
            }
        }

    }
    private void getAcceptSocket(){
        while(true){
            try {
                new ServerSocketRun(serverSocket.accept()).start();
            }catch (IOException e){
                logger.error("ERROR socket connection");
            }
        }
    }

    private void runServer(int PORT) throws  IOException{
        this.serverSocket = new ServerSocket(PORT);
        setServerIsRun(true);
        logger.info("Server running");
    }
}

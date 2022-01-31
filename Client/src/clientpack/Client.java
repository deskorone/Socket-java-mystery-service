package clientpack;

import connectionpack.Connection;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class Client {
    Logger logger = Logger.getLogger(Client.class);
    public Socket socket;

    public static void main(String[] args) {
        new Client();
    }

    private Client(){
        try {
            this.socket = new Socket("127.0.0.1", 9020);
        } catch (IOException e) {
            e.printStackTrace();
        }
                try (Connection connection = new Connection(Client.this.socket)){
                    System.out.println("Connected in server");
                    System.out.println(connection.getSocket().getPort());
                    ClientLogic clientLogic = new ClientLogicClass(connection);
                    if(clientLogic.registerClientPart() && clientLogic.choiseService()){
                        connection.close();
                    }
                }catch(IOException e){
                    logger.fatal("Sever cut off connection");
                }
            }


    }


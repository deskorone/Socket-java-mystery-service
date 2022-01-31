package clientpack;

import connectionpack.Connection;
import connectionpack.Message;
import connectionpack.TypeMessage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class ClientLogicClass implements ClientLogic{
    private final Logger logger = Logger.getLogger(ClientLogicClass.class);
    Connection connection = null;
    public ClientLogicClass(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean registerClientPart() {
        logger.info("Connected to server good " + connection.toString());
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        while(flag) {
            try{
                Message ansServer = connection.getMsg();
                if(ansServer.getTypeMessage()== TypeMessage.CHOISE_LOG_MSG || ansServer.getTypeMessage() == TypeMessage.NO_REGISTRATIONMESSAGE){
                    if(ansServer.getTypeMessage() == TypeMessage.NO_REGISTRATIONMESSAGE) {
                        System.out.println("Error user not exist or password uncorrected");
                    }
                    System.out.println("Login - 1\nRegistration - 2");
                    int tmp = InputNum.inputInteger();
                    if(tmp == 2){connection.sendMsg(new Message(TypeMessage.CHOISE_REGISTR));}
                    if(tmp == 1){connection.sendMsg(new Message(TypeMessage.CHOISE_LOGIN));}
                }
                if(ansServer.getTypeMessage() == TypeMessage.NAME_REQUEST){
                    System.out.println("Input your name ");
                    String name = sc.nextLine();
                    connection.sendMsg(new Message(name, TypeMessage.NAME_REQUEST));
                }
                if(ansServer.getTypeMessage() == TypeMessage.PASS_REQUEST){
                    System.out.println("Input your password");
                    connection.sendMsg(new Message(sc.nextLine(), TypeMessage.PASS_REQUEST));
                }
                if(ansServer.getTypeMessage() == TypeMessage.LOG_OR_REG_TRUE){
                    connection.sendMsg(new Message(TypeMessage.LOG_OR_REG_TRUE));
                    flag = false;
                }
                if(ansServer.getTypeMessage() == TypeMessage.NAME_REGISTRATION){
                    System.out.println("Register please");
                    System.out.println("Input your name ");
                    String name = sc.nextLine();
                    connection.sendMsg(new Message(name, TypeMessage.NAME_REGISTRATION));
                }
                if(ansServer.getTypeMessage() == TypeMessage.PASS_REGISTRATION){
                    System.out.println("Input your password");
                    connection.sendMsg(new Message(sc.nextLine(), TypeMessage.PASS_REGISTRATION));
                }
            }catch (IOException err){
                return false;
            }catch (ClassNotFoundException err){
                return false;
            }

        }
        logger.info("Server successfully logged into service");
        return true;
    }

    @Override
    public boolean choiseService() {
        logger.info("Server successfully logged into service");
        System.out.println("Service");
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        while(flag) {
            try {
                Message ansServer = connection.getMsg();
                if(ansServer.getTypeMessage() == TypeMessage.CHOISE_SERVICE){
                    connection.sendMsg(new Message(TypeMessage.CHOISE_LOG_MSG));
                    System.out.println("Get mystery- 1\nGet rating - 2\nGet online users - 3\nAdd mystery - 4\nExit - 5");
                    int choiser = InputNum.inputIntegerChoise();
                    if(choiser == 5){
                        connection.sendMsg(new Message(TypeMessage.EXIT_MSG));
                    }
                    if(choiser == 4){
                        connection.sendMsg(new Message(TypeMessage.ADD_MYSTERY));
                    }
                    if(choiser == 3){
                        connection.sendMsg(new Message(TypeMessage.GET_ONLINE));
                    }
                    if(choiser == 2){
                        connection.sendMsg(new Message(TypeMessage.GET_STATISTIC));
                    }
                    if(choiser == 1){
                        connection.sendMsg(new Message(TypeMessage.GET_MYSTERY));
                    }
                }
                if(ansServer.getTypeMessage() == TypeMessage.GET_ONLINE){
                    System.out.println(ansServer.getTextMessage());
                }
                if(ansServer.getTypeMessage() == TypeMessage.MYSTERY_MSG){
                    System.out.println("Mystery");
                    System.out.println(ansServer.getTextMessage());
                    System.out.print("Answer: ");
                    String ans = sc.nextLine();
                    connection.sendMsg(new Message(ans.toLowerCase(Locale.ROOT), TypeMessage.ANSWER_MSG));
                }
                if(ansServer.getTypeMessage() == TypeMessage.ANSWER_MYSTERY){
                    System.out.println(ansServer.getTextMessage());
                }
                if(ansServer.getTypeMessage() == TypeMessage.GET_STATISTIC){
                    System.out.println(ansServer.getTextMessage());
                }
                if(ansServer.getTypeMessage() == TypeMessage.ADD_MYSTERY1){
                    System.out.print("Text: ");
                    connection.sendMsg(new Message(sc.nextLine(), TypeMessage.ADD_MYSTERY1));
                }
                if(ansServer.getTypeMessage() == TypeMessage.ADD_MYSTERY2){
                    System.out.print("Answer: ");
                    connection.sendMsg(new Message(sc.nextLine().toLowerCase(Locale.ROOT), TypeMessage.ADD_MYSTERY2));
                }
                if(ansServer.getTypeMessage() == TypeMessage.ADD_TRUE){
                    System.out.println(ansServer.getTextMessage());
                }
                if(ansServer.getTypeMessage() == TypeMessage.EXIT_MSG){
                    logger.info("Exit from server");
                    System.out.println("Exit");
                    flag = false;
                }

            }catch (IOException e){
                return false;
            }catch (ClassNotFoundException e){
                return false;
            }
        }
        return true;
    }


}

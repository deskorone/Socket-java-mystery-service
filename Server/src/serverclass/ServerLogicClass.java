package serverclass;

import connectionpack.Connection;
import connectionpack.Message;
import connectionpack.TypeMessage;
import fileManager.FileManager;
import fileManager.FileWork;
import fileManager.User;
import mystery.Mystery;
import mystery.MysteryFileManager;
import org.apache.log4j.Logger;

import java.io.IOException;
//switch

public class ServerLogicClass implements Logica{
    Connection connection;
    User user = null;
    public ServerLogicClass(Connection connection) {
        this.connection = connection;
    }
    FileWork fileWork = new FileManager();
    String infoUser;
    private final Logger logger = Logger.getLogger(ServerLogicClass.class);

    @Override
    public boolean register() {
        logger.info("Connection find  " + connection.toString() + "  Entry in server");
        String name = null;
        String pass;
        boolean flag = true;
        boolean done = false;
        while(flag) {
            try{
                if(!done){connection.sendMsg(new Message(TypeMessage.CHOISE_LOG_MSG)); done = true;}
                Message ansFromClient = connection.getMsg();
                if(ansFromClient.getTypeMessage() == TypeMessage.CHOISE_LOGIN){
                    connection.sendMsg(new Message(TypeMessage.NAME_REQUEST));
                }
                if(ansFromClient.getTypeMessage() == TypeMessage.CHOISE_REGISTR){
                    connection.sendMsg(new Message(TypeMessage.NAME_REGISTRATION));
                }
                if(ansFromClient.getTypeMessage() == TypeMessage.NAME_REQUEST){
                    name = ansFromClient.getTextMessage();
                    connection.sendMsg(new Message(TypeMessage.PASS_REQUEST));
                }
                if(ansFromClient.getTypeMessage() == TypeMessage.PASS_REQUEST){
                    pass = ansFromClient.getTextMessage();
                    user = new User(name, pass);

                    if(fileWork.chekUserLog(user)){
                        if(fileWork.checkPass(user) && OnlineUsers.userIsOnline(user)) {
                            name = user.getName();
                            user = fileWork.getUser(name);
                            OnlineUsers.addUser(user, connection);
                            connection.sendMsg(new Message(TypeMessage.LOG_OR_REG_TRUE));
                        }else {
                            connection.sendMsg(new Message(TypeMessage.NO_REGISTRATIONMESSAGE));
                        }
                    }else{
                        connection.sendMsg(new Message(TypeMessage.NO_REGISTRATIONMESSAGE));
                    }

                }
                if(ansFromClient.getTypeMessage() == TypeMessage.LOG_OR_REG_TRUE){
                    flag = false;
                }
                if (ansFromClient.getTypeMessage() == TypeMessage.NAME_REGISTRATION){
                    name = ansFromClient.getTextMessage();
                    if(!fileWork.chekUserLog(new User(ansFromClient.getTextMessage()))) {
                        connection.sendMsg(new Message(TypeMessage.PASS_REGISTRATION));
                    }else {
                        connection.sendMsg(new Message(TypeMessage.CHOISE_LOG_MSG));
                    }
                }
                if(ansFromClient.getTypeMessage() == TypeMessage.PASS_REGISTRATION){
                    pass = ansFromClient.getTextMessage();
                    user = new User(name, pass , 0);
                    if(fileWork.registration(user)){
                        name = user.getName();
                        user = fileWork.getUser(name);
                        OnlineUsers.addUser(user, connection);
                        connection.sendMsg(new Message(TypeMessage.LOG_OR_REG_TRUE));
                    }else {
                        logger.info("User not connected because name register");
                    }
                }
            }catch (IOException err){
                logger.warn("This user cut off connection");
                OnlineUsers.delete(user);
                return false;
            }catch (ClassNotFoundException e){
                logger.error(e);
                return false;
            }
        }
        infoUser = user.getName() + " " + connection.toString();
        logger.info("User entered to server: Name" + infoUser);
        return true;
    }



    @Override
    public synchronized boolean choise() {
        logger.info("User work with service: " + user.getName() + " " + connection.toString());
        boolean flag = true;
        boolean done = false;
        MysteryFileManager manager = new MysteryFileManager();
        Mystery mystery = null;
        String text = null;
        String answer;
        while (flag) {
            try {
                if (!done) {
                    connection.sendMsg(new Message(TypeMessage.CHOISE_SERVICE));
                    done = true;
                }
                Message ansFromClient = connection.getMsg();
                if(ansFromClient.getTypeMessage() == TypeMessage.GET_ONLINE){
                    logger.info("User requested online list: " + infoUser);
                    connection.sendMsg(new Message(OnlineUsers.getOnlinne(), TypeMessage.GET_ONLINE));
                    done = false;
                }
                if(ansFromClient.getTypeMessage() == TypeMessage.GET_MYSTERY){
                    logger.info("User requested mystery: " + infoUser);
                    mystery = manager.getMystery(user.getPoints());
                    connection.sendMsg(new Message(mystery.getText(), TypeMessage.MYSTERY_MSG));
                }
                if(ansFromClient.getTypeMessage() == TypeMessage.ANSWER_MSG){
                    if(ansFromClient.getTextMessage().equals(mystery.getAnswer())){
                        fileWork.addPoints(user);
                        logger.info("User right answer: " + infoUser);
                        connection.sendMsg(new Message("Correct answer +1 point",TypeMessage.ANSWER_MYSTERY));
                        done = false;
                    }else {
                        logger.info("User wrong answer: " + infoUser);
                        connection.sendMsg(new Message("Uncorrect answer", TypeMessage.ANSWER_MYSTERY));
                        done = false;
                    }
                }
                if(ansFromClient.getTypeMessage() == TypeMessage.GET_STATISTIC){
                    logger.info("User requested statistics list: " + infoUser);
                    connection.sendMsg(new Message(fileWork.getStatistic(manager.getSize()), TypeMessage.GET_STATISTIC));
                    done = false;
                }
                if(ansFromClient.getTypeMessage() == TypeMessage.ADD_MYSTERY){
                    connection.sendMsg(new Message(TypeMessage.ADD_MYSTERY1));
                }
                if(ansFromClient.getTypeMessage() == TypeMessage.ADD_MYSTERY1){
                    text = ansFromClient.getTextMessage();
                    connection.sendMsg(new Message(TypeMessage.ADD_MYSTERY2));
                }
                if(ansFromClient.getTypeMessage() == TypeMessage.ADD_MYSTERY2){
                    answer = ansFromClient.getTextMessage();
                    if(manager.addMystery(new Mystery(text, answer))){
                        logger.info("User add mystery: " + infoUser);
                        connection.sendMsg(new Message("Add cool",TypeMessage.ADD_TRUE));
                        done = false;
                    }else{
                        logger.info("User NO add mystery: " + user.getName() + " " + connection.toString());
                        connection.sendMsg(new Message("No add", TypeMessage.ADD_TRUE));
                        done = false;
                    }
                }
                if(ansFromClient.getTypeMessage() == TypeMessage.EXIT_MSG){
                    logger.info("User exit: " + infoUser);
                    connection.sendMsg(new Message(TypeMessage.EXIT_MSG));
                    OnlineUsers.delete(user);
                    flag = false;
                }

            } catch (IOException e) {
                logger.warn("This user cut off connection: Name " + infoUser);
                OnlineUsers.delete(user);
                e.printStackTrace();
                return false;
            }catch (ClassNotFoundException e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }


}

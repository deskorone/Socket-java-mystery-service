package serverclass;

import connectionpack.Connection;
import fileManager.User;

import java.util.HashMap;
import java.util.Map;

public class OnlineUsers {
    static Map <User, Connection> mapUsers = new HashMap<>();

    public synchronized static void addUser(User user, Connection connection){mapUsers.put(user, connection);}

    public synchronized static String getOnlinne(){
        String str = "";
        for(Map.Entry<User, Connection> i: mapUsers.entrySet()){
            str += "Name: " + i.getKey().getName() + "    Port: " + i.getValue().getSocket().getPort() + "\n";
        }
        return str;
    }



    synchronized static boolean userIsOnline(User newuser){
        for(User i : mapUsers.keySet()){
            if(i.getName().equals(newuser.getName())){
                return false;
            }
        }
        return true;
    }

    synchronized static void delete(User user){
        mapUsers.remove(user);
    }
}

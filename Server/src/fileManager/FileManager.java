package fileManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class FileManager implements FileWork{
    private ArrayList<User> userList = new ArrayList<>();
    private File file;

    public  FileManager(){
        file = new File("your path");
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            while(reader.ready()){
                User user = new User(reader.readLine() , reader.readLine(), Integer.parseInt(reader.readLine()));
                userList.add(user);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }



    @Override
    public synchronized boolean chekUserLog(User user) {
        for(User i : userList){
            if(i.getName().equals(user.getName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized User getUser(String name) {
        for(User i : userList){
            if(i.getName().equals(name)){
                return i;
            }
        }
        return null;
    }


    @Override
    public synchronized boolean checkPass(User user) {
        for(User i : userList){
            if(user.getName().equals(i.getName())){
                if(user.getPassword().equals(i.getPassword())){
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public synchronized boolean registration(User user) {
        if(!chekUserLog(user)) {
            userList.add(user);
            try (FileWriter writer = new FileWriter(file, true)) {
                if (file.length() != 0) {
                    writer.write("\n" + user.getName() + "\n" + user.getPassword() + "\n" + user.getPoints());
                    return true;
                } else {
                    writer.write(user.getName() + "\n" + user.getPassword() + "\n" + user.getPoints());
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }





    @Override
    public synchronized void addPoints(User user){
        for(User i : userList){
            if(user.hashCode() == i.hashCode()){
                i.userPoint();
            }
        }
        try(FileWriter writer = new FileWriter(file, false)){
            for(User i : userList){
                writer.write(i.getName() + "\n" + i.getPassword() + "\n" + i.getPoints() + "\n");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public synchronized String getStatistic(int size) {
        String  str = "";
        userList.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o2.getPoints() - o1.getPoints();
            }
        });
        for(User i : userList){
            str += "Name: " + i.getName() + "   Solved: " + (((double)i.getPoints())/size)*100 + "%\n";
        }
        return str;
    }

}

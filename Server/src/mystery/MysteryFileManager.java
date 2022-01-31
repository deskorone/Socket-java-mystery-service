package mystery;


import java.io.*;
import java.util.ArrayList;


public class MysteryFileManager {
    private ArrayList <Mystery> mysteryList = new ArrayList<>(); // Не добавляет в онлайне потому что не статик
    private int size;
    private File file;
    public int getSize() {
        return size;
    }

    public MysteryFileManager() {
        file = new File("your path");
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            while (reader.ready()){
                Mystery mystery = new Mystery();
                mystery.setText(reader.readLine());
                mystery.setAnswer(reader.readLine());
                mysteryList.add(mystery);
            }
            this.size = mysteryList.size();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public synchronized boolean addMystery(Mystery mystery){
        for(Mystery i : mysteryList ){
            if(i.hashCode() == mystery.hashCode()){
                return false;
            }
        }
        try(FileWriter writer =  new FileWriter(file, true )){
            if(file.length() != 0){
                writer.write("\n" + mystery.getMystery() + "\n" + mystery.getAnswer());
                mysteryList.add(mystery);
                this.size = mysteryList.size();
                return true;
            }else {
                writer.write(mystery.getMystery() + "\n" + mystery.getAnswer());
                mysteryList.add(mystery);
                this.size = mysteryList.size();
                return true;
            }
        }catch (IOException e ){
            e.printStackTrace();
        }

        return true;
    }

    public synchronized Mystery getMystery(int i){
        if(i == size){
            return new Mystery("All mystery solved", "No answer" );
        }
        return mysteryList.get(i);
    }


}




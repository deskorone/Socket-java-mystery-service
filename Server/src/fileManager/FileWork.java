package fileManager;

public interface FileWork {
    boolean chekUserLog(User user);
    User getUser(String name);
    boolean checkPass(User user);
    boolean registration(User user);
    void addPoints(User user);
    String getStatistic(int size);
}

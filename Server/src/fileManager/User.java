package fileManager;

public class User {
    private String name;
    private String password;
    private int points = 0;

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String name) {
        this.name = name;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getPoints() {
        return points;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void userPoint(){
        this.points ++;
    }

    public User(String name, String password, int points) {
        this.name = name;
        this.password = password;
        this.points = points;
    }
}

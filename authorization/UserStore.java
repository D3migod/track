package classwork.authorization;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Булат on 12.10.2015.
 */
public class UserStore {
    private String fileName = "userData.ser";
    private Map<String, User> loginUserMap = new HashMap<>();

    public void UserStore() {
    }

    public boolean isUserExist(String login) {
        return this.loginUserMap.containsKey(login);
    }

    public void addUser(User user) {
        this.loginUserMap.put(user.getName(), user);
    }

    public User getUser(String name) {
        return this.loginUserMap.get(name);
    }

    public boolean compareLoginPass(String name, Integer pass) {
        return this.loginUserMap.get(name).getPass().equals(pass);
    }

    public void writeIntoFile() throws IOException {
        FileOutputStream fileOut =
                new FileOutputStream(this.fileName);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(loginUserMap);
        out.close();
        fileOut.close();
    }

    public void readFromFile() throws IOException {
        FileInputStream fileIn = new FileInputStream(this.fileName);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        try {
            loginUserMap = (HashMap<String, User>) in.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        in.close();
        fileIn.close();
    }

    public boolean isFileExist() {
        File f = new File(this.fileName);
        return (f.exists() && !f.isDirectory());
    }
}

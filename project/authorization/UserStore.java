package track.project.authorization;

import track.project.history.Store;
import track.project.session.User;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Булат on 12.10.2015.
 */
public class UserStore implements Store {
    private String fileName = "userData.ser";
    private String encoding = "utf-8";
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

    public void writeIntoFile() {
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), encoding));
            try {
                Set set = loginUserMap.entrySet();
                Iterator iterator = set.iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    writer.write(entry.getValue().toString());
                    writer.write(System.lineSeparator());
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Error writing userdata into file " + fileName + ": " + e.getMessage());
        }
    }

    public void readFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    fileName));
            try {
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    String[] userData = line.split(" ");
                    loginUserMap.put(userData[0], new User(userData[0], Integer.valueOf(userData[1]), userData[2]));
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.err.println("Error reading userdata from file " + fileName + ": " + e.getMessage());
        }
    }

    public boolean isFileExist() {
        File f = new File(this.fileName);
        return (f.exists() && !f.isDirectory());
    }
}

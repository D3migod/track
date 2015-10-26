package track.project.authorization;

import track.project.session.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Булат on 12.10.2015.
 */
public class FileUserStore implements UserStore {
    private static final String FILE_NAME = "userData.ser";
    private static final String ENCODING = "UTF-8";
    private Map<String, User> loginUserMap = new HashMap<>();

    public FileUserStore() {
        if (isFileExist()) {
            load();
        }
    }

    @Override
    public boolean isUserExist(String login) {
        return this.loginUserMap.containsKey(login);
    }

    @Override
    public void addUser(User user) {
        this.loginUserMap.put(user.getName(), user);
    }

    @Override
    public User getUser(String name) {
        return this.loginUserMap.get(name);
    }

    @Override
    public boolean compareLoginPass(String name, String pass) {
        return this.loginUserMap.get(name).getPass().checkPassword(pass);
    }

    @Override
    public void save() {
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(FILE_NAME), ENCODING));
            try {
                for (Map.Entry<String, User> entry : loginUserMap.entrySet()) {
                    writer.write(entry.getValue().toString() + System.lineSeparator());
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Error writing userdata into file " + FILE_NAME + ": " + e.getMessage());
        }
    }

    public void load() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    FILE_NAME));
            try {
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    String[] userData = line.split(" ");
                    Password pass = new Password(Base64.getDecoder().decode(userData[1]), Base64.getDecoder().decode(userData[2]));
                    loginUserMap.put(userData[0], new User(userData[0], pass, userData[3]));
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.err.println("Error reading userdata from file " + FILE_NAME + ": " + e.getMessage());
        }
    }

    public boolean isFileExist() {
        Path path = Paths.get(FILE_NAME);
        return ((Files.exists(path)) && (Files.isRegularFile(path)));
    }
}

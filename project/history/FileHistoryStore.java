package track.project.history;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Булат on 17.10.2015.
 */
public class FileHistoryStore implements HistoryStore {
    private static final String FILE_NAME = "historyData.ser";
    private static final String ENCODING = "utf-8";
    private Map<String, List<Message>> loginMessageMap = new HashMap<>();

    public FileHistoryStore() {
        if (isFileExist()) {
            load();
        }
    }

    public List<Message> getUserHistory(String login) {
        return loginMessageMap.get(login);
    }

    public void updateMessageList(String login, List<Message> currentHistory) {
        if (loginMessageMap.containsKey(login)) {
            loginMessageMap.get(login).addAll(currentHistory);
        } else {
            loginMessageMap.put(login, currentHistory);
        }
    }

    public void save() {
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(FILE_NAME), ENCODING));
            try {
                for (Map.Entry<String, List<Message>> entry : loginMessageMap.entrySet()) {
                    writer.write(entry.getKey() + System.lineSeparator() + String.valueOf(entry.getValue().size()) + System.lineSeparator());
                    for (Message msg : entry.getValue()) {
                        writer.write(new SimpleDateFormat(Message.FORMAT).format(msg.getTime()) + " " + msg.getMessage());
                        writer.write(System.lineSeparator());
                    }
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Error writig history into file: " + e.getMessage());
        }
    }

    public void load() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(FILE_NAME)), ENCODING));
            try {
                while (true) {
                    String login = reader.readLine();
                    if (login == null) {
                        break;
                    }
                    int size = Integer.parseInt(reader.readLine());
                    String[] messageString;
                    List<Message> messageHistory = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        messageString = reader.readLine().split(" ", 3);
                        DateFormat dateFormat = new SimpleDateFormat(Message.FORMAT);
                        try {
                            Date date = dateFormat.parse(messageString[0] + " " + messageString[1]);
                            messageHistory.add(new Message(date, messageString[2]));
                        } catch (ParseException e) {
                            System.err.println("Exception parsing date:" + e.getMessage());
                        }
                    }
                    loginMessageMap.put(login, messageHistory);
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.err.println("Error writig history into file: " + e.getMessage());
        }
    }

    public boolean isFileExist() {
        Path path = Paths.get(FILE_NAME);
        return ((Files.exists(path)) && (Files.isRegularFile(path)));
    }


}

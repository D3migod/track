package track.project.history;

import java.io.*;
import java.util.*;

/**
 * Created by Булат on 17.10.2015.
 */
public class HistoryStore implements Store {
    private String fileName = "historyData.ser";
    private String encoding = "utf-8";
    private Map<String, ArrayList<Message>> loginMessageMap = new HashMap<>();

    public ArrayList<Message> getUserHistory(String login) {
        return loginMessageMap.get(login);
    }

    @Override
    public void writeIntoFile() {
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), encoding));
            try {
                Set set = loginMessageMap.entrySet();
                Iterator iterator = set.iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    writer.write((String) entry.getKey());
                    writer.write(System.lineSeparator());
                    writer.write(String.valueOf(((ArrayList<Message>) entry.getValue()).size()));
                    writer.write(System.lineSeparator());
                    for (Message msg : (ArrayList<Message>) entry.getValue()) {
                        writer.write(msg.getTimeMessage());
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

    @Override
    public void readFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), encoding));
            try {
                while (true) {
                    String login = reader.readLine();
                    if (login == null) {
                        break;
                    }
                    int size = Integer.parseInt(reader.readLine());
                    String[] messageString;
                    ArrayList<Message> messageHistory = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        messageString = reader.readLine().split(" ", 3);
                        messageHistory.add(new Message(messageString[0] + " " + messageString[1], messageString[2]));
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

    @Override
    public boolean isFileExist() {
        File f = new File(this.fileName);
        return (f.exists() && !f.isDirectory());
    }

    public void updateMessageList(String login, ArrayList<Message> currentHistory) {
        if (loginMessageMap.containsKey(login)) {
            loginMessageMap.get(login).addAll(currentHistory);
        } else {
            loginMessageMap.put(login, currentHistory);
        }
    }
}

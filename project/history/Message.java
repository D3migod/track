package track.project.history;

/**
 * Created by Булат on 17.10.2015.
 */
public class Message {
    private String time;
    private String message;

    public Message(String time, String message) {
        this.time = time;
        this.message = message;
    }

    public String getTimeMessage() {
        return time + " " + message;
    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }
}

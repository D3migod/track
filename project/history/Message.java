package track.project.history;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Булат on 17.10.2015.
 */
public class Message {
    private Date time;
    private String message;
    static final String FORMAT = "MM.dd.yyyy HH:mm:ss"; //we need FORMAT if FileHistoryStore to safe message in this format

    public Message(Date time, String message) {
        this.time = time;
        this.message = message;
    }

    public String getTimeMessage() {
        return new SimpleDateFormat(FORMAT).format(time) + " " + message;
    }

    public Date getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }
}

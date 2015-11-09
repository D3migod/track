package track.project.message;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import track.project.commands.CommandType;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Булат on 05.11.2015.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class SendMessage extends Message {
    private Long chatId;
    private Long time;
    private String message;
    static final String FORMAT = "MM.dd.yyyy HH:mm:ss"; //we need FORMAT if FileHistoryStore to safe message in this format
    private static final int SECONDS_MULTIPLIER = 1000; //converting milliseconds to seconds

    public SendMessage() {
        setType(CommandType.MSG_SEND);
    }

    public SendMessage(Long chatId, String message) {
        this.chatId = chatId;
        this.time = (new Date()).getTime();
        this.message = message;
        setType(CommandType.MSG_SEND);
    }

    public SendMessage(Long chatId, Long time, String message) {
        this.chatId = chatId;
        this.time = time;
        this.message = message;
        setType(CommandType.MSG_SEND);
    }

    public String getTimeMessage() {
        return new SimpleDateFormat(FORMAT).format(new Date(time * SECONDS_MULTIPLIER)) + " " + message;
    }

    public Long getTime() {
        return time;
    }

    public Long setTime() {
        return time;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


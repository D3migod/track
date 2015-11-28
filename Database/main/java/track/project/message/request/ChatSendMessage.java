package track.project.message.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import track.project.commands.CommandType;
import track.project.message.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Булат on 09.11.2015.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class ChatSendMessage extends Message {
    private Long chatId;
    private Long time;
    private String message;
    static final String FORMAT = "MM.dd.yyyy HH:mm:ss"; //we need FORMAT if FileHistoryStore to safe message in this format

    public ChatSendMessage() {
        setType(CommandType.CHAT_SEND);
    }

    public ChatSendMessage(Long chatId, String message) {
        this.chatId = chatId;
        this.time = new Date().getTime();
        this.message = message;
        setType(CommandType.CHAT_SEND);
    }

    public ChatSendMessage(Long chatId, Long time, String message) {
        this.chatId = chatId;
        this.time = time;
        this.message = message;
        setType(CommandType.CHAT_SEND);
    }

    public ChatSendMessage(Long id, Long sender, Long chatId, Long time, String message) {
        setId(id);
        setSender(sender);
        this.chatId = chatId;
        this.time = time;
        this.message = message;
        setType(CommandType.CHAT_SEND);
    }

    @JsonIgnore
    public String getTimeString() {
        return new SimpleDateFormat(FORMAT).format(new Date(time));
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


    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ChatSendMessage)) {
            return false;
        }
        ChatSendMessage that = (ChatSendMessage) other;
        return equalsWithNulls(getSender(), that.getSender()) &&
                equalsWithNulls(getId(), that.getId()) &&
                equalsWithNulls(getType(), that.getType()) &&
                equalsWithNulls(chatId, that.chatId) &&
                equalsWithNulls(time, that.time) &&
                equalsWithNulls(message, that.message);
    }
}

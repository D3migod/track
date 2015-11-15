package track.project.message.request;

import track.project.commands.CommandType;
import track.project.message.Message;

/**
 * Created by Булат on 09.11.2015.
 */

// TODO: а чем отличается от SendMessage?
public class ChatSendMessage extends Message {
    private Long chatId;
    private String message;

    public ChatSendMessage() {
        setType(CommandType.CHAT_SEND);
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

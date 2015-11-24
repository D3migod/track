package track.project.message.request;

import track.project.commands.CommandType;
import track.project.message.Message;

/**
 * Created by Булат on 05.11.2015.
 */
public class ChatHistoryMessage extends Message {
    private Long chatId;
    private int messageNumber = -1;

    public ChatHistoryMessage() {
        setType(CommandType.CHAT_HISTORY);
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}

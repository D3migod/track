package track.project.message.request;

import track.project.commands.CommandType;
import track.project.message.Message;

/**
 * Created by Булат on 05.11.2015.
 */
public class ChatFindMessage extends Message {
    private Long chatId;
    private String searchWord;

    public ChatFindMessage() {
        setType(CommandType.CHAT_FIND);
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}

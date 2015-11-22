package track.project.commands.executor;

import track.project.commands.Command;
import track.project.message.Message;
import track.project.message.MessageStore;
import track.project.message.request.ChatFindMessage;
import track.project.message.request.ChatSendMessage;
import track.project.message.result.ChatFindResultMessage;
import track.project.message.result.additional.ResultStatus;
import track.project.session.Session;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ����� on 15.10.2015.
 */
public class ChatFindCommand implements Command {
    private final String description = "\\chat_find ChatId Word - find all occurences of the \"Word\" in the chat with \"ChatId\"";
    private MessageStore messageStore;

    public ChatFindCommand(MessageStore messageStore) {
        this.messageStore = messageStore;
    }

    @Override
    public void execute(Session session, Message message) {
        Message resultMessage;
        if (session.isLoggedIn()) {
            ChatFindMessage findMessage = (ChatFindMessage) message;
            List<Long> sendMessageIds = messageStore.getMessagesFromChat(findMessage.getChatId());
            if (sendMessageIds != null) {
                resultMessage = new ChatFindResultMessage(search(sendMessageIds, findMessage.getSearchWord()));
            } else {
                resultMessage = new ChatFindResultMessage("Word " + findMessage.getSearchWord() + " was not found", ResultStatus.FAILED);
            }
        } else {
            resultMessage = new ChatFindResultMessage(ResultStatus.NOT_LOGGED_IN);
        }
        session.getConnectionHandler().send(resultMessage);
    }

    private List<String> search(List<Long> sendMessageIds, String word) {
        List<String> response = new LinkedList<>();
        for (Long sendMessageId : sendMessageIds) {
            ChatSendMessage sendMessage = (ChatSendMessage) messageStore.getMessageById(sendMessageId);
            String messageString = sendMessage.getMessage();
            int index = messageString.indexOf(word);
            if (index >= 0) {
                response.add("Indexes: ");
            } else {
                continue;
            }
            while (index >= 0) {
                response.add(index + " ");
                index = messageString.indexOf(word, index + 1);
            }
            response.add("\nin the following message: ");
            response.add(sendMessage.getTimeMessage());
        }
        return response;
    }

    @Override
    public String getDescription() {
        return description;
    }
}

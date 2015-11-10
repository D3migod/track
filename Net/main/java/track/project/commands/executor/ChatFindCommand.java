package track.project.commands.executor;

import track.project.commands.Command;
import track.project.commands.result.CommandResult;
import track.project.commands.result.ResultStatus;
import track.project.message.Message;
import track.project.message.MessageStore;
import track.project.message.SendMessage;
import track.project.message.request.ChatFindMessage;
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
    public CommandResult execute(Session session, Message message) {
        if (session.isUserSet() == true) {
            ChatFindMessage findMessage = (ChatFindMessage) message;
            List<Long> sendMessageIds = messageStore.getMessagesFromChat(findMessage.getChatId());
            if (sendMessageIds != null) {
                return new CommandResult(search(sendMessageIds, findMessage.getSearchWord()));
            } else {
                return new CommandResult("Word " + findMessage.getSearchWord() + " was not found", ResultStatus.FAILED);
            }
        } else {
            return new CommandResult(ResultStatus.NOT_LOGGINED);
        }
    }

    private List<String> search(List<Long> sendMessageIds, String word) {
        List<String> response = new LinkedList<>();
        for (Long sendMessageId : sendMessageIds) {
            SendMessage sendMessage = (SendMessage) messageStore.getMessageById(sendMessageId);
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
            response.add("\nin the following sendMessage: ");
            response.add(sendMessage.getTimeMessage());
        }
        return response;
    }

    @Override
    public String getDescription() {
        return description;
    }
}

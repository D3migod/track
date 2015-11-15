package track.project.commands.executor;

import track.project.commands.Command;
import track.project.commands.result.CommandResult;
import track.project.commands.result.ResultStatus;
import track.project.message.Message;
import track.project.message.MessageStore;
import track.project.message.SendMessage;
import track.project.message.request.ChatHistoryMessage;
import track.project.session.Session;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ����� on 15.10.2015.
 */
public class ChatHistoryCommand implements Command {
    // TODO: private
    MessageStore messageStore;
    private final String description = "\\chat_history Id- show all message history in the \"Id\" chat.\n\\history N Id- show last N messages in the \"Id\" chat";

    public ChatHistoryCommand(MessageStore messageStore) {
        this.messageStore = messageStore;
    }

    @Override
    public CommandResult execute(Session session, Message message) {
        // TODO: результат уже boolean => if (session.isUserSet()) {...}
        if (session.isUserSet() == true) {
            ChatHistoryMessage chatHistoryMessage = (ChatHistoryMessage) message;
            List<Long> sendMessageIds = messageStore.getMessagesFromChat(chatHistoryMessage.getChatId());
            List<String> response = new LinkedList<>();
            if (chatHistoryMessage.getMessageNumber() != -1) {
                int size = chatHistoryMessage.getMessageNumber();
                int newSize = sendMessageIds.size();
                int curSize = (size > newSize) ? newSize : size;
                if (sendMessageIds != null) {
                    for (int i = curSize; i > 0; i--) {
                        Long sendMessageId = sendMessageIds.get(newSize - i);
                        SendMessage sendMessage = (SendMessage) messageStore.getMessageById(sendMessageId);
                        response.add(sendMessage.getTimeMessage());
                    }
                }
            } else {
                if (sendMessageIds != null) {
                    for (Long sendMessageId : sendMessageIds) {
                        response.add(((SendMessage) messageStore.getMessageById(sendMessageId)).getTimeMessage());
                    }
                }
            }
            return new CommandResult(response);
        } else {
            return new CommandResult(ResultStatus.NOT_LOGGINED);
        }

    }

    @Override
    public String getDescription() {
        return description;
    }
}

package track.project.commands.executor;

import track.project.commands.Command;
import track.project.message.Message;
import track.project.message.MessageStore;
import track.project.message.request.ChatHistoryMessage;
import track.project.message.request.ChatSendMessage;
import track.project.message.result.ChatHistoryResultMessage;
import track.project.message.result.additional.ResultStatus;
import track.project.session.Session;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ����� on 15.10.2015.
 */
public class ChatHistoryCommand implements Command {
    private MessageStore messageStore;
    private final String description = "\\chat_history Id- show all message history in the \"Id\" chat.\n\\history N Id- show last N messages in the \"Id\" chat";

    public ChatHistoryCommand(MessageStore messageStore) {
        this.messageStore = messageStore;
    }

    @Override
    public void execute(Session session, Message message) {
        Message resultMessage;
        if (session.isLoggedIn()) {
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
                        ChatSendMessage sendMessage = (ChatSendMessage) messageStore.getMessageById(sendMessageId);
                        response.add(sendMessage.getTimeMessage());
                    }
                }
            } else {
                if (sendMessageIds != null) {
                    for (Long sendMessageId : sendMessageIds) {
                        response.add(((ChatSendMessage) messageStore.getMessageById(sendMessageId)).getTimeMessage());
                    }
                }
            }
            resultMessage = new ChatHistoryResultMessage(response);
        } else {
            resultMessage = new ChatHistoryResultMessage(ResultStatus.NOT_LOGGED_IN);
        }
        session.getConnectionHandler().send(resultMessage);
    }

    @Override
    public String getDescription() {
        return description;
    }
}

package track.project.commands.executor;

import track.project.commands.Command;
import track.project.message.Message;
import track.project.message.MessageStore;
import track.project.message.request.ChatSendMessage;
import track.project.message.result.ChatSendResultMessage;
import track.project.message.result.base.ResultStatus;
import track.project.session.Session;

/**
 * Created by Булат on 09.11.2015.
 */
public class ChatSendCommand implements Command {
    private final String description = "\\chat_send Id Message - send \"Message\" to \"Id\" chat.";
    private MessageStore messageStore;

    public ChatSendCommand(MessageStore messageStore) {
        this.messageStore = messageStore;
    }

    @Override
    public void execute(Session session, Message message) {
        Message resultMessage;
        if (session.isLoggedIn()) {
            ChatSendMessage chatSendMessage = (ChatSendMessage) message;
            Long chatId = chatSendMessage.getChatId();
            if (messageStore.isChatExist(chatId)) {
                Message sendMessage = new ChatSendMessage(chatId, chatSendMessage.getMessage());
                sendMessage.setSender(session.getSessionUser().getId());
                messageStore.addMessage(chatId, sendMessage);
                resultMessage = ChatSendResultMessage.getResultOk();
            } else {
                resultMessage = new ChatSendResultMessage("Chat with id " + chatId.toString() + " does not exist", ResultStatus.FAILED);
            }
        } else {
            resultMessage = new ChatSendResultMessage(ResultStatus.NOT_LOGGED_IN);
        }
        session.getConnectionHandler().send(resultMessage);
    }

    @Override
    public String getDescription() {
        return description;
    }
}

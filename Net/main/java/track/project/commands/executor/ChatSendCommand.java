package track.project.commands.executor;

import track.project.commands.Command;
import track.project.commands.result.CommandResult;
import track.project.commands.result.ResultStatus;
import track.project.message.Message;
import track.project.message.MessageStore;
import track.project.message.SendMessage;
import track.project.message.request.ChatSendMessage;
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
    public CommandResult execute(Session session, Message message) {
        // FIXME: can be simplyfied to if (session.isUserSet()) {...}
        if (session.isUserSet() == true) {
            ChatSendMessage chatSendMessage = (ChatSendMessage) message;
            Long chatId = chatSendMessage.getChatId();
            if (messageStore.isChatExist(chatId)) {
                Message sendMessage = new SendMessage(chatId, chatSendMessage.getMessage());
                sendMessage.setSender(session.getSessionUser().getId());
                messageStore.addMessage(chatId, sendMessage);
                return new CommandResult();
            } else {
                // FIXME: сообщение потрется в CommandHandler (при статусе !=OK вы создаете там новое сообщение об ошибке)
                return new CommandResult("Chat with " + chatId.toString() + " id does not exist", ResultStatus.FAILED);
            }
        } else {
            return new CommandResult(ResultStatus.NOT_LOGGINED);
        }
    }

    @Override
    public String getDescription() {
        return description;
    }
}

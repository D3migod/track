package track.project.commands.executor;

import track.project.authorization.UserStore;
import track.project.commands.Command;
import track.project.message.Chat;
import track.project.message.Message;
import track.project.message.MessageStore;
import track.project.message.request.ChatCreateMessage;
import track.project.message.result.ChatCreateResultMessage;
import track.project.message.result.base.ResultStatus;
import track.project.session.Session;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Булат on 08.11.2015.
 */
public class ChatCreateCommand implements Command {
    private final String description = "\\chat_create id_list - create chat and invite all users with id in id_list";
    private MessageStore messageStore;
    private UserStore userStore;

    public ChatCreateCommand(UserStore userStore, MessageStore messageStore) {
        this.userStore = userStore;
        this.messageStore = messageStore;
    }

    @Override
    public void execute(Session session, Message message) {
        Message resultMessage;
        if (session.isLoggedIn()) {
            ChatCreateMessage chatCreateMessage = (ChatCreateMessage) message;
            List<Long> participantIds = chatCreateMessage.getParticipantsIds();
            List<Long> wrongParticipantsIds = new LinkedList<>();
            List<Long> rightParticipantsIds = new LinkedList<>();
            for (Long id : participantIds) {
                if (userStore.isUserExist(id)) {
                    rightParticipantsIds.add(id);
                } else {
                    wrongParticipantsIds.add(id);
                }
            }
            if (rightParticipantsIds.isEmpty()) {
                resultMessage = new ChatCreateResultMessage("All participant ids do not exist, chat was not created", ResultStatus.FAILED);
            } else {
                Long currentUserId = session.getSessionUser().getId();
                if (!rightParticipantsIds.contains(currentUserId)) {
                    rightParticipantsIds.add(session.getSessionUser().getId());
                }
                Chat chat = new Chat(rightParticipantsIds);
                messageStore.addChat(chat);
                resultMessage = new ChatCreateResultMessage(wrongParticipantsIds, chat.getId().toString());
            }
        } else {
            resultMessage = new ChatCreateResultMessage(ResultStatus.NOT_LOGGED_IN);
        }
        session.getConnectionHandler().send(resultMessage);
    }

    @Override
    public String getDescription() {
        return description;
    }
}

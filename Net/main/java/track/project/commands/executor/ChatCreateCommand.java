package track.project.commands.executor;

import track.project.authorization.UserStore;
import track.project.commands.Command;
import track.project.commands.result.CommandResult;
import track.project.commands.result.ResultStatus;
import track.project.message.Chat;
import track.project.message.Message;
import track.project.message.MessageStore;
import track.project.message.request.ChatCreateMessage;
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
    public CommandResult execute(Session session, Message message) {
        if (session.isUserSet() == true) {
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
                return new CommandResult("All participant ids do not exist, chat was not created", ResultStatus.FAILED);
            } else {
                rightParticipantsIds.add(session.getSessionUser().getId());
                messageStore.addChat(new Chat(rightParticipantsIds));
                return new CommandResult(wrongParticipantsIds);
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

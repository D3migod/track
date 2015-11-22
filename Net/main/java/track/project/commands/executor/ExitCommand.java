package track.project.commands.executor;

import track.project.authorization.UserStore;
import track.project.commands.Command;
import track.project.message.Message;
import track.project.message.MessageStore;
import track.project.message.result.ExitResultMessage;
import track.project.session.Session;

/**
 * Created by ����� on 18.10.2015.
 */
public class ExitCommand implements Command {
    private final String description = "\\exit - close the program";
    private MessageStore messageStore;
    private UserStore userStore;

    public ExitCommand(MessageStore messageStore, UserStore userStore) {
        this.messageStore = messageStore;
        this.userStore = userStore;
    }

    @Override
    public void execute(Session session, Message message) {
        if (session.isLoggedIn()) {
            messageStore.save();
            userStore.save();
        }
        Message resultMessage = ExitResultMessage.getResultOk();
        session.getConnectionHandler().send(resultMessage);
    }

    @Override
    public String getDescription() {
        return description;
    }
}

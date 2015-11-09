package track.project.commands;

import track.project.authorization.UserStore;
import track.project.commands.result.CommandResult;
import track.project.message.Message;
import track.project.message.MessageStore;
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
    public CommandResult execute(Session session, Message message) {
        if (session.isUserSet()) {
            messageStore.save();
            userStore.save();
            return new CommandResult("Changes are saved. Exit");
        }
        return new CommandResult("Exit");
    }

    @Override
    public String getDescription() {
        return description;
    }
}

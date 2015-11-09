package track.project.commands;

import track.project.commands.result.CommandResult;
import track.project.commands.result.ResultStatus;
import track.project.message.Message;
import track.project.message.request.UserMessage;
import track.project.session.Session;

/**
 * Created by ����� on 15.10.2015.
 */
public class UserCommand implements Command {
    private final String description = "\\user Nickname - changes your nick to \"Nickname\".";

    public UserCommand() {
    }

    @Override
    public CommandResult execute(Session session, Message message) {
        if (session.isUserSet() == true) {
            session.getSessionUser().setNick(((UserMessage) message).getNick());
        } else {
            return new CommandResult(ResultStatus.NOT_LOGGINED);
        }
        return new CommandResult();
    }

    @Override
    public String getDescription() {
        return description;
    }
}

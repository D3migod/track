package track.project.commands.executor;

import track.project.commands.Command;
import track.project.message.Message;
import track.project.message.request.UserMessage;
import track.project.message.result.UserResultMessage;
import track.project.message.result.additional.ResultStatus;
import track.project.session.Session;

/**
 * Created by ����� on 15.10.2015.
 */
public class UserCommand implements Command {
    private final String description = "\\user Nickname - changes your nick to \"Nickname\".";

    public UserCommand() {
    }

    @Override
    public void execute(Session session, Message message) {
        Message resultMessage;
        if (session.isLoggedIn()) {
            session.getSessionUser().setNick(((UserMessage) message).getNick());
            resultMessage = UserResultMessage.getResultOk();
        } else {
            resultMessage = new UserResultMessage(ResultStatus.NOT_LOGGED_IN);
        }
        session.getConnectionHandler().send(resultMessage);
    }

    @Override
    public String getDescription() {
        return description;
    }
}

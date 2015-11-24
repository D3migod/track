package track.project.commands.executor;

import track.project.authorization.UserStore;
import track.project.commands.Command;
import track.project.message.Message;
import track.project.message.request.UserMessage;
import track.project.message.result.UserResultMessage;
import track.project.message.result.additional.ResultStatus;
import track.project.session.Session;
import track.project.session.User;

/**
 * Created by ����� on 15.10.2015.
 */
public class UserCommand implements Command {
    private final String description = "\\user Nickname - changes your nick to \"Nickname\".";

    UserStore userStore;
    public UserCommand(UserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public void execute(Session session, Message message) {
        Message resultMessage;
        if (session.isLoggedIn()) {
            String nick = ((UserMessage) message).getNick();
            User user = session.getSessionUser();
            user.setNick(nick);
            userStore.changeNick(nick, user.getId());
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

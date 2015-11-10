package track.project.commands.executor;

import track.project.authorization.UserStore;
import track.project.commands.Command;
import track.project.commands.result.CommandResult;
import track.project.commands.result.ResultStatus;
import track.project.message.Message;
import track.project.message.request.LoginMessage;
import track.project.net.SessionManager;
import track.project.session.Session;
import track.project.session.User;

/**
 * Created by ����� on 17.10.2015.
 */
public class LoginCommand implements Command {
    private final String description = "\\login Login Password - sign in using \"Login\" \"Password\" combination.";
    private UserStore userStore;
    private SessionManager sessionManager;

    public LoginCommand(UserStore userStore, SessionManager sessionManager) {
        this.userStore = userStore;
        this.sessionManager = sessionManager;
    }

    @Override
    public CommandResult execute(Session session, Message message) {
        LoginMessage loginMessage = (LoginMessage) message;
        String inputLogin = loginMessage.getLogin();
        String inputPassword = loginMessage.getPass();
        User user = userStore.getUser(inputLogin);
        if (user != null) {
            if (user.getPass().checkPassword(inputPassword)) {
                session.setSessionUser(userStore.getUser(inputLogin));
                sessionManager.registerUser(user.getId(), session.getId());
                return new CommandResult("Successful authorization: " + inputLogin);
            } else {
                return new CommandResult("Wrong password: " + inputPassword, ResultStatus.FAILED);
            }
        } else {
            return new CommandResult("No such user: " + inputLogin, ResultStatus.FAILED);
        }
    }

    @Override
    public String getDescription() {
        return description;
    }
}

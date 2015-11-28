package track.project.commands.executor;

import track.project.authorization.UserStore;
import track.project.commands.Command;
import track.project.message.Message;
import track.project.message.request.LoginMessage;
import track.project.message.result.LoginResultMessage;
import track.project.message.result.base.ResultStatus;
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
    public void execute(Session session, Message message) {
        Message resultMessage;
        LoginMessage loginMessage = (LoginMessage) message;
        String inputLogin = loginMessage.getLogin();
        String inputPassword = loginMessage.getPass();
        User user = userStore.getUser(inputLogin);
        if (user != null) {
            if (user.getPass().checkPassword(inputPassword)) {
                session.setSessionUser(user);
                Long userId = user.getId();
                sessionManager.registerUser(userId, session.getId());
                resultMessage = new LoginResultMessage("Successful authorization: " + inputLogin + "\nYour id: " + userId.toString());
            } else {
                resultMessage = new LoginResultMessage("Wrong password: " + inputPassword, ResultStatus.FAILED);
            }
        } else {
            resultMessage = new LoginResultMessage("No such user: " + inputLogin, ResultStatus.FAILED);
        }
        session.getConnectionHandler().send(resultMessage);
    }

    @Override
    public String getDescription() {
        return description;
    }
}

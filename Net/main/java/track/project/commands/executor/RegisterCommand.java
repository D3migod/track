package track.project.commands.executor;

import track.project.authorization.Password;
import track.project.authorization.UserStore;
import track.project.commands.Command;
import track.project.message.Message;
import track.project.message.request.RegisterMessage;
import track.project.message.result.RegisterResultMessage;
import track.project.message.result.additional.ResultStatus;
import track.project.net.SessionManager;
import track.project.session.Session;
import track.project.session.User;

/**
 * Created by ����� on 18.10.2015.
 */
public class RegisterCommand implements Command {
    private final String description = "\\register Login Password - sign up using \"Login\" \"Password\" combination.";
    private UserStore userStore;
    private SessionManager sessionManager;

    public RegisterCommand(UserStore userStore, SessionManager sessionManager) {
        this.userStore = userStore;
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(Session session, Message message) {
        Message resultMessage;
        RegisterMessage registerMessage = (RegisterMessage) message;
        String inputLogin = registerMessage.getLogin();
        String inputPassword = registerMessage.getPass();
        if (userStore.isUserExist(inputLogin)) {
            resultMessage = new RegisterResultMessage("User already exists: " + inputLogin, ResultStatus.FAILED);
        } else {
            User currentUser = new User(inputLogin, new Password(inputPassword));
            userStore.addUser(currentUser);
            session.setSessionUser(currentUser);
            sessionManager.registerUser(currentUser.getId(), session.getId());
            resultMessage = new RegisterResultMessage("Successful registration: " + currentUser.getName());
        }
        session.getConnectionHandler().send(resultMessage);
    }

    @Override
    public String getDescription() {
        return description;
    }
}

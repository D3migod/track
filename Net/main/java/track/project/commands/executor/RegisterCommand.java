package track.project.commands.executor;

import track.project.authorization.Password;
import track.project.authorization.UserStore;
import track.project.commands.Command;
import track.project.commands.result.CommandResult;
import track.project.commands.result.ResultStatus;
import track.project.message.Message;
import track.project.message.request.RegisterMessage;
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
    public CommandResult execute(Session session, Message message) {
        RegisterMessage registerMessage = (RegisterMessage) message;
        String inputLogin = registerMessage.getLogin();
        String inputPassword = registerMessage.getPass();
        if (userStore.isUserExist(inputLogin)) {
            new CommandResult("User already exists: " + inputLogin, ResultStatus.FAILED);
        }
        User currentUser = new User(inputLogin, new Password(inputPassword));
        userStore.addUser(currentUser);
        session.setSessionUser(currentUser);
        sessionManager.registerUser(currentUser.getId(), session.getId());
        return new CommandResult("Successful registration: " + currentUser.getName());
    }

    @Override
    public String getDescription() {
        return description;
    }
}

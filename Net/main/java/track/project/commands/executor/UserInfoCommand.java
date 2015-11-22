package track.project.commands.executor;

import track.project.authorization.UserStore;
import track.project.commands.Command;
import track.project.message.Message;
import track.project.message.request.UserInfoMessage;
import track.project.message.result.UserInfoResultMessage;
import track.project.session.Session;
import track.project.session.User;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Булат on 08.11.2015.
 */
public class UserInfoCommand implements Command {
    private final String description = "\\user_info Id - show information about user with id \"Id\".\n\\user_info - show information about yourself.";
    UserStore userStore;

    public UserInfoCommand(UserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public void execute(Session session, Message message) {
        UserInfoMessage userInfoMessage = (UserInfoMessage) message;
        Long userId = userInfoMessage.getUserId();
        User user;
        if (userId == -1L) {
            user = session.getSessionUser();
        } else {
            user = userStore.getUser(userId);
        }
        List<String> response = new LinkedList<>();
        response.add("ID = " + user.getId().toString());
        response.add("Login = " + user.getName());
        response.add("Nick = " + user.getNick());
        Message resultMessage = new UserInfoResultMessage(response);
        session.getConnectionHandler().send(resultMessage);
    }

    @Override
    public String getDescription() {
        return description;
    }
}

package track.project.commands.executor;

import track.project.authorization.Password;
import track.project.commands.Command;
import track.project.commands.result.CommandResult;
import track.project.commands.result.ResultStatus;
import track.project.message.Message;
import track.project.message.request.UserPassMessage;
import track.project.session.Session;
import track.project.session.User;

/**
 * Created by Булат on 08.11.2015.
 */
public class UserPassCommand implements Command {
    private final String description = "\\user_pass Old_pass New_pass - change password from \"Old_pass\" to \"New_pass\".";

    @Override
    public CommandResult execute(Session session, Message message) {
        if (session.isUserSet() == true) {
            User user = session.getSessionUser();
            String oldPassword = ((UserPassMessage) message).getOldPassword();
            String newPassword = ((UserPassMessage) message).getNewPassword();
            if (user.getPass().checkPassword(oldPassword)) {
                user.setPass(new Password(newPassword));
            }
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

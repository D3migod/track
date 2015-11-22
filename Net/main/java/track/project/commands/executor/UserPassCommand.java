package track.project.commands.executor;

import track.project.authorization.Password;
import track.project.commands.Command;
import track.project.message.Message;
import track.project.message.request.UserPassMessage;
import track.project.message.result.UserPassResultMessage;
import track.project.message.result.additional.ResultStatus;
import track.project.session.Session;
import track.project.session.User;

/**
 * Created by Булат on 08.11.2015.
 */
public class UserPassCommand implements Command {
    private final String description = "\\user_pass Old_pass New_pass - change password from \"Old_pass\" to \"New_pass\".";

    @Override
    public void execute(Session session, Message message) {
        Message resultMessage;
        if (session.isLoggedIn()) {
            User user = session.getSessionUser();
            String oldPassword = ((UserPassMessage) message).getOldPassword();
            String newPassword = ((UserPassMessage) message).getNewPassword();
            if (user.getPass().checkPassword(oldPassword)) {
                user.setPass(new Password(newPassword));
                resultMessage = UserPassResultMessage.getResultOk();
            } else {
                resultMessage = new UserPassResultMessage("Wrong password", ResultStatus.FAILED);
            }
        } else {
            resultMessage = new UserPassResultMessage(ResultStatus.NOT_LOGGED_IN);
        }
        session.getConnectionHandler().send(resultMessage);
    }

    @Override
    public String getDescription() {
        return description;
    }
}

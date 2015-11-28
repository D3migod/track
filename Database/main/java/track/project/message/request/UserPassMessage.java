package track.project.message.request;

import track.project.commands.CommandType;
import track.project.message.Message;

/**
 * Created by Булат on 08.11.2015.
 */
public class UserPassMessage extends Message {
    String oldPassword;
    String newPassword;

    public UserPassMessage() {
        setType(CommandType.USER_PASS);
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

package track.project.message.request;

import track.project.commands.CommandType;
import track.project.message.Message;

/**
 * Created by Булат on 08.11.2015.
 */
public class UserInfoMessage extends Message {
    private Long userId = -1L;

    public UserInfoMessage() {
        setType(CommandType.USER_INFO);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

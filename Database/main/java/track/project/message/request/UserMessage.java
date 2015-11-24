package track.project.message.request;

import track.project.commands.CommandType;
import track.project.message.Message;

/**
 * Created by Булат on 05.11.2015.
 */
public class UserMessage extends Message {
    private String nick;

    public UserMessage() {
        setType(CommandType.USER_USER);
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}

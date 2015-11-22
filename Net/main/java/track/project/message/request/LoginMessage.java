package track.project.message.request;

import track.project.commands.CommandType;
import track.project.message.Message;

/**
 * Created by Булат on 04.11.2015.
 */
public class LoginMessage extends Message {
    private String login;
    private String pass;

    public LoginMessage() {
        setType(CommandType.USER_LOGIN);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}

package track.project.session;

import track.project.authorization.Password;

/**
 * Created by Булат on 12.10.2015.
 */
public class User {
    private String name;
    private Password pass;
    private String nick;

    public User(String name, Password pass) {
        this.name = name;
        this.pass = pass;
        this.nick = name;
    }

    public User(String name, Password pass, String nick) {
        this.name = name;
        this.pass = pass;
        this.nick = nick;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Password getPass() {
        return pass;
    }

    public void setPass(Password pass) {
        this.pass = pass;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String toString() {
        return name + " " + pass.toString() + " " + nick.toString();
    }

}

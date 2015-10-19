package track.project.session;

/**
 * Created by Булат on 12.10.2015.
 */
public class User {
    private String name;
    private Integer pass;
    private String nick;

    public User(String name, Integer pass) {
        this.name = name;
        this.pass = pass;
        this.nick = name;
    }

    public User(String name, Integer pass, String nick) {
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

    public Integer getPass() {
        return pass;
    }

    public void setPass(Integer pass) {
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

package track.project.authorization;

import track.project.session.User;

/**
 * Created by Булат on 25.10.2015.
 */
public interface UserStore {
    public boolean isUserExist(String login);

    public void addUser(User user);

    public User getUser(String name);

    public void save();

    public boolean compareLoginPass(String login, String pass);
}

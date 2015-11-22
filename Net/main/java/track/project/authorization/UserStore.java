package track.project.authorization;

import track.project.session.User;

/**
 * Created by ����� on 25.10.2015.
 */
public interface UserStore {
    public boolean isUserExist(String login);

    public boolean isUserExist(Long id);

    public void addUser(User user);

    public User getUser(String login);

    public User getUser(Long id);

    void save();
}

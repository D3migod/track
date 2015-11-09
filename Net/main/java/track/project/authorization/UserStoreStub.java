package track.project.authorization;

import track.project.session.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Булат on 07.11.2015.
 */
public class UserStoreStub implements UserStore {

    private static Map<Long, User> idUserMap = new HashMap<>();
    public static final AtomicLong userCounter = new AtomicLong(0);

    static {
        User u0 = new User("A", new Password("1"));
        User u1 = new User("B", new Password("1"));
        User u2 = new User("C", new Password("1"));
        User u3 = new User("D", new Password("2"));
        idUserMap.put(u0.getId(), u0);
        idUserMap.put(u1.getId(), u1);
        idUserMap.put(u2.getId(), u2);
        idUserMap.put(u3.getId(), u3);
    }

    @Override
    public boolean isUserExist(String login) {
        for (User user : idUserMap.values()) {
            if (user.getName().equals(login)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isUserExist(Long id) {
        return idUserMap.containsKey(id);
    }

    @Override
    public void addUser(User user) {
        user.setId(userCounter.getAndIncrement());
        idUserMap.put(user.getId(), user);
    }

    @Override
    public User getUser(String login) {
        for (User user : idUserMap.values()) {
            if (user.getName().equals(login)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUser(Long id) {
        return idUserMap.get(id);
    }

    @Override
    public void save() {
        //TODO: save
    }
}

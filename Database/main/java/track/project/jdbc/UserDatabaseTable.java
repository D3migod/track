package track.project.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.project.authorization.Password;
import track.project.authorization.UserStore;
import track.project.session.User;

import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Булат on 23.11.2015.
 */
public class UserDatabaseTable extends DatabaseTable implements UserStore {
    private static final String STRING_TYPE = "VARCHAR(20) NOT NULL";
    private static Logger log = LoggerFactory.getLogger(UserDatabaseTable.class);

    public UserDatabaseTable(QueryExecutor exec, Connection connection) {
        super(Arrays.asList("\"id\"", "\"username\"", "\"pass\"", "\"salt\"", "\"nick\""), "users", exec, connection);
        exec.execQuery(connection, createTableQuery(Arrays.asList("BIGSERIAL PRIMARY KEY", STRING_TYPE, "BYTEA", "BYTEA", STRING_TYPE)), (r) -> {
            return 0;
        });
    }

    @Override
    public boolean isUserExist(String login) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, login);
        return exec.execQuery(connection, selectAllWhereQuery(Arrays.asList(1)), prepared, (r) -> {
            return r.next();
        });
    }

    @Override
    public boolean isUserExist(Long id) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, id);
        return exec.execQuery(connection, selectAllWhereQuery(Arrays.asList(0)), prepared, (r) -> {
            return r.next();
        });
    }

    @Override
    public void addUser(User user) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, user.getName());
        prepared.put(2, user.getPass().getPass());
        prepared.put(3, user.getPass().getSalt());
        prepared.put(4, user.getNick());
        exec.execQuery(connection, insertQuery(), prepared, (r) -> {
            return 0;
        });
    }

    @Override
    public User getUser(String login) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, login);
        return exec.execQuery(connection, selectAllWhereQuery(Arrays.asList(1)), prepared, (r) -> {
            if (r.next()) {
                return new User(r.getLong(0), r.getString(1), new Password(r.getBytes(2), r.getBytes(3)), r.getString(4));
            }
            return null;
        });
    }

    @Override
    public User getUser(Long id) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, id);
        return exec.execQuery(connection, selectByIdQuery(), prepared, (r) -> {
            if (r.next()) {
                return new User(r.getLong(0), r.getString(1), new Password(r.getBytes(2), r.getBytes(3)), r.getString(4));
            }
            return null;
        });
    }

    @Override
    public void changePass(Password password, Long id) {
        Map<Integer, Object> prepared = new HashMap<>();
        byte[] pass = password.getPass();
        byte[] salt = password.getSalt();
        prepared.put(1, pass);
        prepared.put(2, salt);
        prepared.put(3, id);
        exec.execQuery(connection, updateQueryById(Arrays.asList(2, 3)), prepared, (r) -> {
            return 0;
        });
    }

    @Override
    public void changeNick(String nick, Long id) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, nick);
        prepared.put(2, id);
        exec.execQuery(connection, updateQueryById(Arrays.asList(4)), prepared, (r) -> {
            return 0;
        });
    }
}

package track.project.jdbc.table;

import track.project.authorization.Password;
import track.project.authorization.UserStore;
import track.project.jdbc.DatabaseTable;
import track.project.jdbc.QueryExecutor;
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

    public UserDatabaseTable(QueryExecutor exec, Connection connection) {
        super(Arrays.asList("id", "username", "pass", "salt", "nick"), "users", exec, connection);
        exec.execUpdate(connection, createTableQuery(Arrays.asList("BIGSERIAL PRIMARY KEY", STRING_TYPE, "BYTEA", "BYTEA", STRING_TYPE)));
    }

    @Override
    public boolean isUserExist(String login) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, "\'" + login + "\'");
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
        prepared.put(1, "\'" + user.getName() + "\'");
        prepared.put(2, user.getPass().getPass());
        prepared.put(3, user.getPass().getSalt());
        prepared.put(4, "\'" + user.getNick() + "\'");
        Long id = exec.execUpdate(connection, insertQuery(), prepared, "id");
        user.setId(id);
    }

    @Override
    public User getUser(String login) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, "\'" + login + "\'");
        return exec.execQuery(connection, selectAllWhereQuery(Arrays.asList(1)), prepared, (r) -> {
            if (r.next()) {
                return new User(r.getLong(1), r.getString(2), new Password(r.getBytes(3), r.getBytes(4)), r.getString(5));
            }
            return null;
        });
    }

    @Override
    public User getUser(Long id) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, id);
        return exec.execQuery(connection, selectAllWhereQuery(Arrays.asList(0)), prepared, (r) -> {
            if (r.next()) {
                return new User(r.getLong(1), r.getString(2), new Password(r.getBytes(3), r.getBytes(4)), r.getString(5));
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
        exec.execUpdate(connection, updateQueryById(Arrays.asList(2, 3)), prepared);
    }

    @Override
    public void changeNick(String nick, Long id) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, nick);
        prepared.put(2, id);
        exec.execUpdate(connection, updateQueryById(Arrays.asList(4)), prepared);
    }
}

package track.project.jdbc.table;

import track.project.jdbc.DatabaseTable;
import track.project.jdbc.QueryExecutor;

import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Булат on 24.11.2015.
 */
public class ChatDatabaseTable extends DatabaseTable {

    public ChatDatabaseTable(QueryExecutor exec, Connection connection) {
        super(Arrays.asList("id"), "chat", exec, connection);
        exec.execUpdate(connection, createTableQuery(Arrays.asList("BIGSERIAL PRIMARY KEY")));
    }

    public Long addChat() {
        return exec.execUpdate(connection, insertIdQuery(), "id");
    }

    public boolean isChatExist(Long id) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, id);
        return exec.execQuery(connection, selectAllWhereQuery(Arrays.asList(0)), prepared, (r) -> {
            return r.next();
        });
    }
}

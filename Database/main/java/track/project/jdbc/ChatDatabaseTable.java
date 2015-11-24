package track.project.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Булат on 24.11.2015.
 */
public class ChatDatabaseTable extends DatabaseTable {
    private static Logger log = LoggerFactory.getLogger(MessageStoreDatabaseTable.class);

    public ChatDatabaseTable(QueryExecutor exec, Connection connection) {
        super(Arrays.asList("id", "messageid"), "chat", exec, connection);
        exec.execQuery(connection, createTableQuery(Arrays.asList("BIGSERIAL", "BIGINT")), (r) -> {
            return 0;
        });
    }

    public Long addChat() {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, "id");
        return exec.execQuery(connection, insertQueryReturn(), prepared, (r) -> {
            return r.getLong(0);
        });
    }

    public boolean isChatExist(Long id) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, id);
        return exec.execQuery(connection, selectAllWhereQuery(Arrays.asList(0)), prepared, (r) -> {
            return r.next();
        });
    }
}

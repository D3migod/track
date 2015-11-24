package track.project.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.project.message.Chat;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Булат on 24.11.2015.
 */
public class ChatUserDatabaseTable extends DatabaseTable {
    private static Logger log = LoggerFactory.getLogger(MessageStoreDatabaseTable.class);

    public ChatUserDatabaseTable(QueryExecutor exec, Connection connection) {
        super(Arrays.asList("id", "userid"), "chatuser", exec, connection);
        exec.execQuery(connection, createTableQuery(Arrays.asList("BIGINT", "BIGINT")), (r) -> {
            return 0;
        });
    }

    public void addChat(Chat chat) {
        Map<Integer, Object> prepared = new HashMap<>();
        List<Long> participants = chat.getParticipantIds();
        for (Long participant : participants) {
            prepared.put(1, chat.getId());
            prepared.put(2, participant);
            exec.execQuery(connection, insertQuery(), prepared, (r) -> {
                return 0;
            });
        }

    }

    public List<Long> getChatsByUserId(Long userId) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, userId);
        return exec.execQuery(connection, selectAllWhereQuery(Arrays.asList(1)), prepared, (r) -> {
            List<Long> data = new ArrayList<>();
            while (r.next()) {
                data.add(r.getLong(0));
            }
            return data;
        });
    }

    public void addUserToChat(Long chatId, Long userId) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, chatId);
        prepared.put(2, userId);
        exec.execQuery(connection, insertQuery(), prepared, (r) -> {
            return 0;
        });
    }

    public List<Long> getUsersByChatId(Long chatId) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, chatId);
        return exec.execQuery(connection, selectAllWhereQuery(Arrays.asList(0)), prepared, (r) -> {
            List<Long> data = new ArrayList<>();
            while (r.next()) {
                data.add(r.getLong(1));
            }
            return data;
        });
    }
}

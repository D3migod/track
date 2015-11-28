package track.project.jdbc.table;

import track.project.jdbc.DatabaseTable;
import track.project.jdbc.QueryExecutor;
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

    public ChatUserDatabaseTable(QueryExecutor exec, Connection connection) {
        super(Arrays.asList("id", "userid"), "chatuser", exec, connection);
        exec.execUpdate(connection, createTableQuery(Arrays.asList("BIGINT", "BIGINT")));
    }

    public void addChat(Chat chat) {
        Map<Integer, Object> prepared = new HashMap<>();
        List<Long> participants = chat.getParticipantIds();
        for (Long participant : participants) {
            prepared.put(1, chat.getId());
            prepared.put(2, participant);
            exec.execUpdate(connection, insertWithIdQuery(), prepared);
        }

    }

    public List<Long> getChatsByUserId(Long userId) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, userId);
        return exec.execQuery(connection, selectAllWhereQuery(Arrays.asList(1)), prepared, (r) -> {
            List<Long> data = new ArrayList<>();
            while (r.next()) {
                data.add(r.getLong(1));
            }
            return data;
        });
    }

    public void addUserToChat(Long chatId, Long userId) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, chatId);
        prepared.put(2, userId);
        exec.execUpdate(connection, insertWithIdQuery(), prepared);
    }

    public List<Long> getUsersByChatId(Long chatId) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, chatId);
        return exec.execQuery(connection, selectAllWhereQuery(Arrays.asList(0)), prepared, (r) -> {
            List<Long> data = new ArrayList<>();
            while (r.next()) {
                data.add(r.getLong(2));
            }
            return data;
        });
    }
}

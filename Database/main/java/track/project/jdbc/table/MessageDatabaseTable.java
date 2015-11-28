package track.project.jdbc.table;

import track.project.jdbc.DatabaseTable;
import track.project.jdbc.QueryExecutor;
import track.project.message.Message;
import track.project.message.request.ChatSendMessage;

import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Булат on 24.11.2015.
 */
public class MessageDatabaseTable extends DatabaseTable {
    private static final String STRING_TYPE = "VARCHAR(20) NOT NULL";

    public MessageDatabaseTable(QueryExecutor exec, Connection connection) {
        super(Arrays.asList("id", "sender", "chatid", "messagetime", "message"), "messages", exec, connection);
        exec.execUpdate(connection, createTableQuery(Arrays.asList("BIGSERIAL PRIMARY KEY", "BIGINT", "BIGINT", "BIGINT", STRING_TYPE)));
    }

    public ChatSendMessage getMessageById(Long id) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, id);
        return exec.execQuery(connection, selectAllWhereQuery(Arrays.asList(0)), prepared, (r) -> {
            if (r.next()) {
                return new ChatSendMessage(r.getLong(1), r.getLong(2), r.getLong(3), r.getLong(4), r.getString(5));
            }
            return null;
        });
    }

    public void addMessage(Message message) {
        Map<Integer, Object> prepared = new HashMap<>();
        ChatSendMessage chatSendMessage = (ChatSendMessage) message;
        prepared.put(1, chatSendMessage.getSender());
        prepared.put(2, chatSendMessage.getChatId());
        prepared.put(3, chatSendMessage.getTime());
        prepared.put(4, "\'" + chatSendMessage.getMessage() + "\'");
        Long id = exec.execUpdate(connection, insertQuery(), prepared, "id");
        message.setId(id);
    }
}

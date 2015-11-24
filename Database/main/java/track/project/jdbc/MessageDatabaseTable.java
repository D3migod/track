package track.project.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger log = LoggerFactory.getLogger(MessageDatabaseTable.class);

    public MessageDatabaseTable(QueryExecutor exec, Connection connection) {
        super(Arrays.asList("id", "sender", "chatid", "time", "message"), "messages", exec, connection);
        exec.execQuery(connection, createTableQuery(Arrays.asList("BIGINT PRIMARY KEY", "BIGINT", "BIGINT", "BIGINT", STRING_TYPE)), (r) -> {
            return 0;
        });
    }

    public ChatSendMessage getMessageById(Long id) {
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, id);
        return exec.execQuery(connection, selectExistsQuery(Arrays.asList(0)), prepared, (r) -> {
            if (r.next()) {
                return new ChatSendMessage(r.getLong(0), r.getLong(1), r.getLong(2), r.getLong(3), r.getString(4));
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
        prepared.put(4, chatSendMessage.getMessage());
        exec.execQuery(connection, insertQuery(), prepared, (r) -> {
            return 0;
        });
    }
}

package track.project.jdbc.table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.project.jdbc.DatabaseTable;
import track.project.jdbc.QueryExecutor;
import track.project.message.Chat;
import track.project.message.Message;
import track.project.message.MessageStore;
import track.project.message.result.ChatSendResultMessage;
import track.project.net.SessionManager;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Булат on 24.11.2015.
 */
public class MessageStoreDatabaseTable extends DatabaseTable implements MessageStore {
    private static Logger log = LoggerFactory.getLogger(MessageStoreDatabaseTable.class);

    ChatUserDatabaseTable chatUserDatabaseTable;
    ChatDatabaseTable chatDatabaseTable;
    MessageDatabaseTable messageDatabaseTable;
    SessionManager sessionManager;

    public MessageStoreDatabaseTable(QueryExecutor exec, Connection connection, SessionManager sessionManager) {
        super(Arrays.asList("id", "messageid"), "chatmessage", exec, connection);
        exec.execUpdate(connection, createTableQuery(Arrays.asList("BIGINT", "BIGINT")));
        chatUserDatabaseTable = new ChatUserDatabaseTable(exec, connection);
        chatDatabaseTable = new ChatDatabaseTable(exec, connection);
        messageDatabaseTable = new MessageDatabaseTable(exec, connection);
        this.sessionManager = sessionManager;
    }

    @Override
    public List<Long> getChatsByUserId(Long userId) {
        return chatUserDatabaseTable.getChatsByUserId(userId);
    }

    @Override
    public Chat getChatById(Long chatId) {
        List<Long> messages = getMessagesFromChat(chatId);
        List<Long> participants = chatUserDatabaseTable.getUsersByChatId(chatId);
        return new Chat(chatId, messages, participants);
    }

    @Override
    public List<Long> getMessagesFromChat(Long chatId) {
        if (isChatExist(chatId)) {
            Map<Integer, Object> prepared = new HashMap<>();
            prepared.put(1, chatId);
            return exec.execQuery(connection, selectAllWhereQuery(Arrays.asList(0)), prepared, (r) -> {
                List<Long> messages = new ArrayList<>();
                while (r.next()) {
                    messages.add(r.getLong(2));
                }
                return messages;
            });
        } else {
            return null;
        }
    }

    @Override
    public Message getMessageById(Long messageId) {
        return messageDatabaseTable.getMessageById(messageId);
    }

    @Override
    public void addMessage(Long chatId, Message message) {
        messageDatabaseTable.addMessage(message);
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, chatId);
        prepared.put(2, message.getId());
        exec.execUpdate(connection, insertWithIdQuery(), prepared);
        List<Long> participantIds = chatUserDatabaseTable.getUsersByChatId(chatId);
        Message resultMessage = new ChatSendResultMessage(message);
        for (Long participant : participantIds) {
            sessionManager.getSessionByUser(participant).getConnectionHandler().send(resultMessage);
        }
    }

    @Override
    public void addUserToChat(Long userId, Long chatId) {
        chatUserDatabaseTable.addUserToChat(userId, chatId);
    }

    @Override
    public void addChat(Chat chat) {
        Long id = chatDatabaseTable.addChat();
        chat.setId(id);
        log.info("Chat id: {}", id);
        chatUserDatabaseTable.addChat(chat);
    }

    @Override
    public boolean isChatExist(Long chatId) {
        return chatDatabaseTable.isChatExist(chatId);
    }
}

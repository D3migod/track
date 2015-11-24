package track.project.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        exec.execQuery(connection, createTableQuery(Arrays.asList("BIGINT", "BIGSERIAL")), (r) -> {
            return 0;
        });
        chatUserDatabaseTable = new ChatUserDatabaseTable(exec, connection);
        chatDatabaseTable = new ChatDatabaseTable(exec, connection);
        sessionManager = this.sessionManager;
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
                    messages.add(r.getLong(1));
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
        if (isChatExist(chatId)) {
            messageDatabaseTable.addMessage(message);
            Map<Integer, Object> prepared = new HashMap<>();
            prepared.put(1, chatId);
            prepared.put(2, message.getId());
            exec.execQuery(connection, insertQuery(), prepared, (r) -> {
                return 0;
            });
            List<Long> participantIds = chatUserDatabaseTable.getUsersByChatId(chatId);
            Message resultMessage = new ChatSendResultMessage(message);
            for (Long participant : participantIds) {
                sessionManager.getSessionByUser(participant).getConnectionHandler().send(resultMessage);
            }
        } else {
            throw new IllegalArgumentException("Chat " + chatId + " does not exist");
        }
    }

    @Override
    public void addUserToChat(Long userId, Long chatId) {
        if (isChatExist(chatId)) {
            chatUserDatabaseTable.addUserToChat(userId, chatId);
        } else {
            throw new IllegalArgumentException("Chat " + chatId + " does not exist");
        }
    }

    @Override
    public void addChat(Chat chat) {
        Long id = chatDatabaseTable.addChat();
        chat.setId(id);
        chatUserDatabaseTable.addChat(chat);
    }

    @Override
    public boolean isChatExist(Long chatId) {
        return chatDatabaseTable.isChatExist(chatId);
    }
}

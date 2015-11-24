import org.junit.Before;
import org.junit.Test;
import track.project.authorization.UserStore;
import track.project.commands.executor.LoginCommand;
import track.project.jdbc.QueryExecutor;
import track.project.jdbc.UserDatabaseTable;
import track.project.message.Message;
import track.project.message.request.ChatCreateMessage;
import track.project.message.request.ChatSendMessage;
import track.project.message.request.LoginMessage;
import track.project.message.result.ChatSendResultMessage;
import track.project.message.result.additional.ResultStatus;
import track.project.net.ConnectionHandler;
import track.project.net.JsonProtocol;
import track.project.net.Protocol;
import track.project.net.SessionManager;
import track.project.net.SocketConnectionHandler;
import track.project.session.Session;

import java.net.Socket;

import static org.junit.Assert.assertEquals;

/**
 * Created by Булат on 10.11.2015.
 */
public class JsonProtocolTest {
    private Protocol protocol;
    private static final String TEST_STRING = "test";
    @Before
    public final void setUp() throws Exception {
        protocol = new JsonProtocol();
    }
    private void sendMessageEquals(ChatSendMessage chatSendMessage, ChatSendMessage chatSendDecodedMessage){
        assertEquals(chatSendMessage.getMessage(), chatSendDecodedMessage.getMessage());
        assertEquals(chatSendMessage.getChatId(), chatSendDecodedMessage.getChatId());
        assertEquals(chatSendMessage.getId(), chatSendDecodedMessage.getId());
        assertEquals(chatSendMessage.getSender(), chatSendDecodedMessage.getSender());
        assertEquals(chatSendMessage.getTime(), chatSendDecodedMessage.getTime());
    }
    @Test
    public void testChatSendMessage() throws Exception {
        Message message = new ChatSendMessage(0L, 1L, 2L, 3L, TEST_STRING);
        Message decodedMessage = protocol.decode(protocol.encode(message));
        sendMessageEquals((ChatSendMessage) message, (ChatSendMessage) decodedMessage);
    }
    @Test
    public void testChatSendResultMessageOK() throws Exception {
        Message sendMessage = new ChatSendMessage(0L, 1L, 2L, 3L, TEST_STRING);
        Message message = new ChatSendResultMessage(sendMessage);
        Message decodedMessage = protocol.decode(protocol.encode(message));
        ChatSendResultMessage chatSendResultMessage = (ChatSendResultMessage) message;
        ChatSendResultMessage chatSendResultDecodedMessage = (ChatSendResultMessage) decodedMessage;
        sendMessageEquals(chatSendResultMessage.getResponse(), chatSendResultDecodedMessage.getResponse());
        assertEquals(chatSendResultMessage.getStatus(), chatSendResultDecodedMessage.getStatus());
    }
    @Test
    public void testChatSendResultMessageFAIL() throws Exception {
        Message message = new ChatSendResultMessage(TEST_STRING, ResultStatus.FAILED);
        Message decodedMessage = protocol.decode(protocol.encode(message));
        ChatSendResultMessage chatSendResultMessage = (ChatSendResultMessage) message;
        ChatSendResultMessage chatSendResultDecodedMessage = (ChatSendResultMessage) decodedMessage;
        assertEquals(chatSendResultMessage.getStatusInfo(), chatSendResultDecodedMessage.getStatusInfo());
        assertEquals(chatSendResultMessage.getStatus(), chatSendResultDecodedMessage.getStatus());
    }
}

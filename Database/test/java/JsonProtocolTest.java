import org.junit.Before;
import org.junit.Test;
import track.project.message.Message;
import track.project.message.request.ChatSendMessage;
import track.project.message.result.ChatSendResultMessage;
import track.project.message.result.base.ResultStatus;
import track.project.net.JsonProtocol;
import track.project.net.Protocol;

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

    @Test
    public void testChatSendMessage() throws Exception {
        ChatSendMessage message = new ChatSendMessage(0L, 1L, 2L, 3L, TEST_STRING);
        ChatSendMessage decodedMessage = (ChatSendMessage) protocol.decode(protocol.encode(message));
        assertEquals(message, decodedMessage);
    }

    @Test
    public void testChatSendResultMessageOK() throws Exception {
        Message sendMessage = new ChatSendMessage(0L, 1L, 2L, 3L, TEST_STRING);
        ChatSendResultMessage message = new ChatSendResultMessage(sendMessage);
        ChatSendResultMessage decodedMessage = (ChatSendResultMessage) protocol.decode(protocol.encode(message));
        assertEquals(message, decodedMessage);
    }

    @Test
    public void testChatSendResultMessageFAIL() throws Exception {
        ChatSendResultMessage message = new ChatSendResultMessage(TEST_STRING, ResultStatus.FAILED);
        ChatSendResultMessage decodedMessage = (ChatSendResultMessage) protocol.decode(protocol.encode(message));
        assertEquals(message, decodedMessage);
    }
}

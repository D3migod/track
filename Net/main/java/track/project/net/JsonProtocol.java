package track.project.net;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.project.message.Message;

import java.io.IOException;

/**
 * Created by Булат on 04.11.2015.
 */
public class JsonProtocol implements Protocol {
    static Logger log = LoggerFactory.getLogger(JsonProtocol.class);

    @Override
    public Message decode(byte[] bytes) {
        ObjectMapper objectMapper = new ObjectMapper();
        Message message = new Message();
        try {
            message = objectMapper.readValue(bytes, Message.class);
        } catch (IOException e) {
            log.info("Exception decoding message {}: " + e.getMessage(), message.toString());
        }
        log.info("decoded:{}", message.toString());
        return message;
    }

    @Override
    public byte[] encode(Message message) {
        String json = "";
        try {
            json = new ObjectMapper().writeValueAsString(message);
        } catch (JsonProcessingException e) {
            log.info("Exception encoding message {}: " + e.getMessage(), message.toString());
        }
        log.info("encoded: {}", json);
        return json.getBytes();
    }

}

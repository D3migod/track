package track.project.net;

import track.project.message.Message;

/**
 * Created by ����� on 03.11.2015.
 */
public interface Protocol {
    Message decode(byte[] bytes);

    byte[] encode(Message msg);
}

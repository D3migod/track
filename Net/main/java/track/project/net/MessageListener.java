package track.project.net;

import track.project.message.Message;
import track.project.session.Session;

/**
 * Created by ����� on 03.11.2015.
 */
public interface MessageListener {
    void onMessage(Session session, Message message);
}

package track.project.net;

import track.project.message.Message;

/**
 * Created by ����� on 03.11.2015.
 */
public interface ConnectionHandler extends Runnable {

    void send(Message msg);

    void addListener(MessageListener listener);

    void stop();
}

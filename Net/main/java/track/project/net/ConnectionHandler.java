package track.project.net;

import track.project.message.Message;

import java.io.IOException;

/**
 * Created by Булат on 03.11.2015.
 */
public interface ConnectionHandler extends Runnable {

    void send(Message msg) throws IOException;

    void addListener(MessageListener listener);

    void stop();
}

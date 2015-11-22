package track.project.message.request;

import track.project.commands.CommandType;
import track.project.message.Message;

/**
 * Created by Булат on 05.11.2015.
 */
public class ExitMessage extends Message {
    public ExitMessage() {
        setType(CommandType.USER_EXIT);
    }
}

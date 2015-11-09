package track.project.commands;

import track.project.commands.result.CommandResult;
import track.project.message.Message;
import track.project.session.Session;

/**
 * Created by ����� on 13.10.2015.
 */
public interface Command {
    CommandResult execute(Session session, Message message);

    String getDescription();
}

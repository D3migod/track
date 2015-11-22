package track.project.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.project.message.Message;
import track.project.net.MessageListener;
import track.project.session.Session;

import java.util.Map;

/**
 * Created by Булат on 04.11.2015.
 */
public class CommandHandler implements MessageListener {
    static Logger log = LoggerFactory.getLogger(CommandHandler.class);

    Map<CommandType, Command> commands;

    public CommandHandler(Map<CommandType, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void onMessage(Session session, Message message) {
        Command cmd = commands.get(message.getType());
        log.info("onMessage: {} type {}", message, message.getType());
        cmd.execute(session, message);
    }
}

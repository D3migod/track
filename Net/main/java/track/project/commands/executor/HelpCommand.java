package track.project.commands.executor;

import track.project.commands.Command;
import track.project.commands.CommandType;
import track.project.message.Message;
import track.project.message.result.HelpResultMessage;
import track.project.session.Session;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by ����� on 15.10.2015.
 */
public class HelpCommand implements Command {
    private Map<CommandType, Command> commands;
    private final String description = "\\help - show all commands";

    public HelpCommand(Map<CommandType, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(Session session, Message message) {
        List<String> response = new LinkedList<>();
        for (Map.Entry<CommandType, Command> entry : commands.entrySet()) {
            response.add(entry.getValue().getDescription());
        }
        Message resultMessage = new HelpResultMessage(response);
        session.getConnectionHandler().send(resultMessage);
    }

    @Override
    public String getDescription() {
        return description;
    }

}

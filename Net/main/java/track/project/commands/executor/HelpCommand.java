package track.project.commands.executor;

import track.project.commands.Command;
import track.project.commands.CommandType;
import track.project.commands.result.CommandResult;
import track.project.message.Message;
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
    public CommandResult execute(Session session, Message message) {
        List<String> response = new LinkedList<>();
        for (Map.Entry<CommandType, Command> entry : commands.entrySet()) {
            response.add(entry.getValue().getDescription());
        }
        return new CommandResult(response);
    }

    @Override
    public String getDescription() {
        return description;
    }

}

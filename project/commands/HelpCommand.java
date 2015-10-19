package track.project.commands;

import track.project.session.Session;

import java.util.Map;

/**
 * Created by Булат on 15.10.2015.
 */
public class HelpCommand implements Command {
    private Map<String, Command> commands;
    int numberOfArguments = 1;
    String description = " - show all commands";

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(Session session, String[] args) {
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            System.out.println(entry.getKey() + entry.getValue().getDescription());
        }
    }

    @Override
    public boolean checkArgumentsValidity(String[] args) {
        return (this.numberOfArguments == args.length);
    }

    @Override
    public boolean checkUserValidity(boolean requiresUser) {
        return true;
    }

    @Override
    public String getDescription() {
        return description;
    }

}

package track.project.commands;

import track.project.session.Session;

import java.util.Map;

/**
 * Created by Булат on 15.10.2015.
 */
public class HelpCommand implements Command {
    private Map<String, Command> commands;
    private static final int NUMBER_OF_ARGUMENTS = 2;
    private final String description = " - show all commands";

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(Session session, String[] args) {
        if (NUMBER_OF_ARGUMENTS == args.length) {
            for (Map.Entry<String, Command> entry : commands.entrySet()) {
                System.out.println(entry.getKey() + entry.getValue().getDescription());
            }
        } else {
            System.out.println("Wrong number of arguments");
            System.out.println(args[0] + description);
        }
    }

    @Override
    public String getDescription() {
        return description;
    }

}

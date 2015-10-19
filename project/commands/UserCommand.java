package track.project.commands;

import track.project.session.Session;

/**
 * Created by Булат on 15.10.2015.
 */
public class UserCommand implements Command {
    int numberOfArguments = 2;
    String description = " Nickname - changes your nick to \"Nickname\".";

    public UserCommand() {
    }

    @Override
    public void execute(Session session, String[] args) {
        session.getSessionUser().setNick(args[1]);
    }

    @Override
    public boolean checkArgumentsValidity(String[] args) {
        return (this.numberOfArguments == args.length);
    }

    @Override
    public boolean checkUserValidity(boolean requiresUser) {
        return (true == requiresUser);
    }

    @Override
    public String getDescription() {
        return description;
    }
}

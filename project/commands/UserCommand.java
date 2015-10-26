package track.project.commands;

import track.project.session.Session;

/**
 * Created by Булат on 15.10.2015.
 */
public class UserCommand implements Command {
    private static final int NUMBER_OF_ARGUMENTS = 2;
    private final String description = " Nickname - changes your nick to \"Nickname\".";

    public UserCommand() {
    }

    @Override
    public void execute(Session session, String[] args) {
        if (NUMBER_OF_ARGUMENTS == args.length) {
            if (session.isUserSet() == true) {
                session.getSessionUser().setNick(args[1]);
            } else {
                System.out.println("Sign in to use " + args[0] + " command");
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

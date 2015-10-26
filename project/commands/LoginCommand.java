package track.project.commands;

import track.project.authorization.Authorization;
import track.project.history.HistoryStore;
import track.project.session.Session;

/**
 * Created by Булат on 17.10.2015.
 */
public class LoginCommand implements Command {
    private static final int NUMBER_OF_ARGUMENTS1 = 1;
    private static final int NUMBER_OF_ARGUMENTS2 = 3;
    private final String description = " Login Password - sign in using \"Login\" \"Password\" combination.";
    private Authorization authService;
    private HistoryStore historyStore;

    public LoginCommand(Authorization authService, HistoryStore historyStore) {
        this.authService = authService;
        this.historyStore = historyStore;
    }

    @Override
    public void execute(Session session, String[] args) {
        if (NUMBER_OF_ARGUMENTS1 == args.length) {
            if (session.isUserSet()) { //copypaste is better than checking if clause twice, I suppose
                historyStore.updateMessageList(session.getSessionUser().getName(), session.getCurrentHistory());
            }
            authService.signIn();
        } else if (NUMBER_OF_ARGUMENTS2 == args.length) {
            if (session.isUserSet()) {
                historyStore.updateMessageList(session.getSessionUser().getName(), session.getCurrentHistory());
            }
            authService.signIn(args[1], args[2]);
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

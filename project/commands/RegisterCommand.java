package track.project.commands;

import track.project.authorization.Authorization;
import track.project.history.HistoryStore;
import track.project.session.Session;

/**
 * Created by Булат on 18.10.2015.
 */
public class RegisterCommand implements Command {
    private static final int NUMBER_OF_ARGUMENTS1 = 1;
    private static final int NUMBER_OF_ARGUMENTS2 = 3;
    private final String description = " Login Password - sign up using \"Login\" \"Password\" combination.";
    private Authorization authService;
    private HistoryStore historyStore;

    public RegisterCommand(Authorization authService, HistoryStore historyStore) {
        this.authService = authService;
        this.historyStore = historyStore;
    }

    @Override
    public void execute(Session session, String[] args) {

        if (NUMBER_OF_ARGUMENTS1 == args.length) {
            if (session.isUserSet()) {
                historyStore.updateMessageList(session.getSessionUser().getName(), session.getCurrentHistory());
            }
            authService.signUp();
        } else if (NUMBER_OF_ARGUMENTS2 == args.length) {
            if (session.isUserSet()) {
                historyStore.updateMessageList(session.getSessionUser().getName(), session.getCurrentHistory());
            }
            authService.signUp(args[1], args[2]);
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

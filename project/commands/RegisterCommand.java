package track.project.commands;

import track.project.authorization.Authorization;
import track.project.history.HistoryStore;
import track.project.session.Session;

/**
 * Created by Булат on 18.10.2015.
 */
public class RegisterCommand implements Command {
    Authorization authService;
    HistoryStore historyStore;
    int numberOfArguments1 = 1;
    int numberOfArguments2 = 3;
    String description = " Login Password - sign up using \"Login\" \"Password\" combination.";

    public RegisterCommand(Authorization authService, HistoryStore historyStore) {
        this.authService = authService;
        this.historyStore = historyStore;
    }

    @Override
    public void execute(Session session, String[] args) {
        if (session.isUserSet()) {
            historyStore.updateMessageList(session.getSessionUser().getName(), session.getCurrentHistory());
        }
        if (this.numberOfArguments1 == args.length) {
            authService.signUp();
        } else {
            authService.signUp(args[1], args[2]);
        }
    }

    @Override
    public boolean checkArgumentsValidity(String[] args) {
        return ((this.numberOfArguments1 == args.length) || (this.numberOfArguments2 == args.length));
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

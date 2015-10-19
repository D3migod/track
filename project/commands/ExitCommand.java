package track.project.commands;

import track.project.authorization.UserStore;
import track.project.history.HistoryStore;
import track.project.session.Session;

/**
 * Created by Булат on 18.10.2015.
 */
public class ExitCommand implements Command {
    int numberOfArguments = 1;
    String description = " - close the program";
    HistoryStore historyStore;
    UserStore userStore;

    public ExitCommand(HistoryStore historyStore, UserStore userStore) {
        this.historyStore = historyStore;
        this.userStore = userStore;
    }

    @Override
    public void execute(Session session, String[] args) {
        if (session.isUserSet()) {
            historyStore.updateMessageList(session.getSessionUser().getName(), session.getCurrentHistory());
            historyStore.writeIntoFile();
            userStore.writeIntoFile();
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

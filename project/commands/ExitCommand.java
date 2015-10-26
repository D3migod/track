package track.project.commands;

import track.project.authorization.FileUserStore;
import track.project.authorization.UserStore;
import track.project.history.FileHistoryStore;
import track.project.history.HistoryStore;
import track.project.session.Session;

/**
 * Created by Булат on 18.10.2015.
 */
public class ExitCommand implements Command {
    private static final int NUMBER_OF_ARGUMENTS = 1;
    private final String description = " - close the program";
    private HistoryStore historyStore;
    private UserStore userStore;

    public ExitCommand(HistoryStore historyStore, UserStore userStore) {
        this.historyStore = historyStore;
        this.userStore = userStore;
    }

    @Override
    public void execute(Session session, String[] args) {
        if (NUMBER_OF_ARGUMENTS == args.length) {
            if (session.isUserSet()) {
                historyStore.updateMessageList(session.getSessionUser().getName(), session.getCurrentHistory());
                historyStore.save();
                userStore.save();
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

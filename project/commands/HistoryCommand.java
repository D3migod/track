package track.project.commands;

import track.project.history.HistoryStore;
import track.project.history.Message;
import track.project.session.Session;

import java.util.List;

/**
 * Created by Булат on 15.10.2015.
 */
public class HistoryCommand implements Command {
    HistoryStore historyStore;
    private static final int NUMBER_OF_ARGUMENTS1 = 1;
    private static final int NUMBER_OF_ARGUMENTS2 = 2;
    private final String description = " - show all message history.\n\\history N - show last N messages";

    public HistoryCommand(HistoryStore historyStore) {
        this.historyStore = historyStore;
    }

    @Override
    public void execute(Session session, String[] args) {
        if (NUMBER_OF_ARGUMENTS1 == args.length) {
            List<Message> oldMessages = historyStore.getUserHistory(session.getSessionUser().getName());
            List<Message> newMessages = session.getCurrentHistory();
            if (oldMessages != null) {
                for (Message message : oldMessages) {
                    System.out.println(message.getTimeMessage());
                }
            }
            for (Message message : newMessages) {
                System.out.println(message.getTimeMessage());
            }
        } else if (NUMBER_OF_ARGUMENTS2 == args.length) {
            if (args[1].matches("\\d+")) {
                List<Message> oldMessages = historyStore.getUserHistory(session.getSessionUser().getName());
                List<Message> newMessages = session.getCurrentHistory();
                int size = Integer.valueOf(args[1]);

                int newSize = newMessages.size();
                int curSize = (size > newSize) ? newSize : size;
                if (oldMessages != null) {
                    int oldSize = oldMessages.size();
                    for (int i = size - curSize; i > 0; i--) {
                        System.out.println(oldMessages.get(oldSize - i).getTimeMessage());
                    }
                }
                if (newMessages != null) {
                    for (int i = curSize; i > 0; i--) {
                        System.out.println(newMessages.get(newSize - i).getTimeMessage());
                    }
                }
            } else {
                System.out.println("Second argument must be digit");
                System.out.println(args[0] + description);
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

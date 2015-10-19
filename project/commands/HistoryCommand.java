package track.project.commands;

import track.project.history.HistoryStore;
import track.project.history.Message;
import track.project.session.Session;

import java.util.ArrayList;

/**
 * Created by Булат on 15.10.2015.
 */
public class HistoryCommand implements Command {
    HistoryStore historyStore;
    int numberOfArguments1 = 1;
    int numberOfArguments2 = 2;
    String description = " - show all message history.\n\\history N - show last N messages";

    public HistoryCommand(HistoryStore historyStore) {
        this.historyStore = historyStore;
    }

    @Override
    public void execute(Session session, String[] args) {
        ArrayList<Message> oldMessages = historyStore.getUserHistory(session.getSessionUser().getName());
        ArrayList<Message> newMessages = session.getCurrentHistory();
        if (args.length == numberOfArguments1) {
            if (oldMessages != null) {
                for (Message message : oldMessages) {
                    System.out.println(message.getTimeMessage());
                }
            }
            for (Message message : newMessages) {
                System.out.println(message.getTimeMessage());
            }
        } else {
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

        }
    }

    @Override
    public boolean checkArgumentsValidity(String args[]) {
        return ((this.numberOfArguments1 == args.length) || ((this.numberOfArguments2 == args.length) && args[1].matches("\\d+"))
        );
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

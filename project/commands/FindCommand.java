package track.project.commands;

import track.project.history.HistoryStore;
import track.project.history.Message;
import track.project.session.Session;

import java.util.List;

/**
 * Created by Булат on 15.10.2015.
 */
public class FindCommand implements Command {
    private static final int NUMBER_OF_ARGUMENTS = 2;
    private final String description = " Word - find all occurences of the \"Word\" in historyStore";
    private HistoryStore historyStore;

    public FindCommand(HistoryStore historyStore) {
        this.historyStore = historyStore;
    }

    @Override
    public void execute(Session session, String[] args) {
        if (NUMBER_OF_ARGUMENTS == args.length) {
            if (session.isUserSet() == true) {
                List<Message> oldMessages = historyStore.getUserHistory(session.getSessionUser().getName());
                List<Message> newMessages = session.getCurrentHistory();
                if (oldMessages != null) {
                    search(oldMessages, args[1]);
                }
                search(newMessages, args[1]);
            } else {
                System.out.println("Sign in to use " + args[0] + " command");
            }
        } else {
            System.out.println("Wrong number of arguments");
            System.out.println(args[0] + description);
        }
    }

    private void search(List<Message> messages, String word) {
        for (Message message : messages) {
            String messageString = message.getMessage();
            int index = messageString.indexOf(word);
            if (index >= 0) {
                System.out.println("Indexes: ");
            } else {
                continue;
            }
            while (index >= 0) {
                System.out.print(index + " ");
                index = messageString.indexOf(word, index + 1);
            }
            System.out.println("\nin the following message: ");
            System.out.println(message.getTimeMessage());
        }
    }

    @Override
    public String getDescription() {
        return description;
    }
}

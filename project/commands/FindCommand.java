package track.project.commands;

import track.project.history.HistoryStore;
import track.project.history.Message;
import track.project.session.Session;

import java.util.ArrayList;

/**
 * Created by Булат on 15.10.2015.
 */
public class FindCommand implements Command {
    int numberOfArguments = 2;
    HistoryStore historyStore;
    String description = " Word - find all occurences of the \"Word\" in historyStore";

    public FindCommand(HistoryStore historyStore) {
        this.historyStore = historyStore;
    }

    @Override
    public void execute(Session session, String[] args) {
        ArrayList<Message> oldMessages = historyStore.getUserHistory(session.getSessionUser().getName());
        ArrayList<Message> newMessages = session.getCurrentHistory();
        if (oldMessages != null) {
            search(oldMessages, args[1]);
        }
        search(newMessages, args[1]);
    }

    private void search(ArrayList<Message> messages, String word) {
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

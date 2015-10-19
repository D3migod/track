package track.project.session;

import track.project.history.Message;

import java.util.ArrayList;

/**
 * Created by Булат on 15.10.2015.
 */
public class Session {

    private User sessionUser;

    private ArrayList<Message> currentHistory = new ArrayList<>();

    public Session() {
    }

    public User getSessionUser() {
        return sessionUser;
    }

    public ArrayList<Message> getCurrentHistory() {
        return currentHistory;
    }

    public boolean isUserSet() {
        return sessionUser != null;
    }

    public void setSessionUser(User sessionUser) {
        this.sessionUser = sessionUser;
        this.currentHistory.clear();
    }

    public void updateCurrentHistory(Message newMessage) {
        currentHistory.add(newMessage);
    }
}
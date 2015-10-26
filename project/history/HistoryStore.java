package track.project.history;

import java.util.List;

/**
 * Created by Булат on 25.10.2015.
 */
public interface HistoryStore {
    public List<Message> getUserHistory(String login);

    public void updateMessageList(String login, List<Message> currentHistory);

    public void save();
}

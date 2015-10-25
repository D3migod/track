package track.project.commands;

import track.project.session.Session;

/**
 * Created by Булат on 13.10.2015.
 */
public interface Command {
    void execute(Session session, String[] args);

    String getDescription();
}

package track.project.session;

import track.project.net.ConnectionHandler;

/**
 * Created by ����� on 15.10.2015.
 */
public class Session {

    private User sessionUser;
    private Long id;
    private ConnectionHandler connectionHandler;

    public Session() {
    }

    public Session(Long id) {
        this.id = id;
    }

    public User getSessionUser() {
        return sessionUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public boolean isUserSet() {
        return sessionUser != null;
    }

    public void setSessionUser(User sessionUser) {
        this.sessionUser = sessionUser;
    }
}
package track.project;

import track.project.commands.Command;
import track.project.history.Message;
import track.project.session.Session;

import java.util.Date;
import java.util.Map;

/**
 * Created by Булат on 13.10.2015.
 */
public class Interpreter {
    private Session session;

    private Map<String, Command> commandMap;

    public Interpreter(Session session, Map<String, Command> commandMap) {
        this.session = session;
        this.commandMap = commandMap;
    }

    public void handle(String data) {
        if (data.startsWith("\\")) {
            String[] arg = data.split(" ");
            Command cmd = commandMap.get(arg[0]);
            if (cmd == null) {
                System.out.println(arg[0] + " is incorrect command");
            } else {
                cmd.execute(session, arg);
            }
        } else {
            if (session.isUserSet()) {
                System.out.println(session.getSessionUser().getNick() + "> " + data);
                Date timeStamp = new Date();
                session.updateCurrentHistory(new Message(timeStamp, data));
            } else {
                System.out.println("You must be signed in to write messages");
            }
        }
    }

}

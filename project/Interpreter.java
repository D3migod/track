package track.project;

import track.project.commands.Command;
import track.project.history.Message;
import track.project.session.Session;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Булат on 13.10.2015.
 */
public class Interpreter {
    private Session session;
    private String format = "MM/dd/yyyy HH:mm:ss";

    private Map<String, Command> commandMap;

    public Interpreter(Session session, Map<String, Command> commandMap) {
        this.session = session;
        this.commandMap = commandMap;
    }

    public void handle(String data) {
        if (data.startsWith("\\")) {
            StringTokenizer argtok = new StringTokenizer(data, " ",
                    false);
            String[] arg = new String[argtok.countTokens()];
            int i = 0;
            while (argtok.hasMoreTokens()) {
                arg[i++] = argtok.nextToken();
            }
            Command cmd = commandMap.get(arg[0]);
            if (cmd == null) {
                System.out.println(arg[0] + " is incorrect command");
            } else {
                try {
                    if (cmd.checkArgumentsValidity(arg)) {
                        if (cmd.checkUserValidity(session.isUserSet())) {
                            try {
                                cmd.execute(session, arg);
                            } catch (NullPointerException e) {
                                System.err.println("Nullpointer exception executing the " + arg[0] + " command");
                            }
                        } else {
                            System.out.println("Sign in to use " + arg[0] + " command");
                        }
                    } else {
                        System.out.println("Wrong arguments");
                        System.out.println(arg[0] + cmd.getDescription());
                    }
                } catch (NullPointerException e) {
                    System.err.println("Nullpointer exception checking validity of the " + arg[0] + " command");
                }
            }
        } else {
            if (session.isUserSet()) {
                System.out.println(session.getSessionUser().getNick() + "> " + data);
                String timeStamp = new SimpleDateFormat(format).format(new Date());
                session.updateCurrentHistory(new Message(timeStamp, data));
            } else {
                System.out.println("You must be signed in to write messages");
            }
        }
    }

}

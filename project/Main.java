package track.project;

import track.project.authorization.Authorization;
import track.project.authorization.FileUserStore;
import track.project.authorization.UserStore;
import track.project.commands.Command;
import track.project.commands.ExitCommand;
import track.project.commands.FindCommand;
import track.project.commands.HelpCommand;
import track.project.commands.HistoryCommand;
import track.project.commands.LoginCommand;
import track.project.commands.RegisterCommand;
import track.project.commands.UserCommand;
import track.project.history.FileHistoryStore;
import track.project.history.HistoryStore;
import track.project.session.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ����� on 06.10.2015.
 */

public class Main {

    public static void main(String[] args) {
        UserStore userStore = new FileUserStore();

        HistoryStore historyStore = new FileHistoryStore();

        Session session = new Session();
        Authorization authService = new Authorization(userStore, session);
        Map<String, Command> commands = new HashMap<>();
        Command exitCommand = new ExitCommand(historyStore, userStore);
        Command findCommand = new FindCommand(historyStore);
        Command helpCommand = new HelpCommand(commands);
        Command historyCommand = new HistoryCommand(historyStore);
        Command loginCommand = new LoginCommand(authService, historyStore);
        Command registerCommand = new RegisterCommand(authService, historyStore);
        Command userCommand = new UserCommand();

        commands.put("\\exit", exitCommand);
        commands.put("\\find", findCommand);
        commands.put("\\help", helpCommand);
        commands.put("\\history", historyCommand);
        commands.put("\\login", loginCommand);
        commands.put("\\register", registerCommand);
        commands.put("\\user", userCommand);

        Interpreter interpreter = new Interpreter(session, commands);

        BufferedReader in = new BufferedReader(new InputStreamReader(
                System.in));
        String input = "";
        while (true) {
            try {
                input = in.readLine();
            } catch (IOException e) {
                System.err.print("Error reading line: " + e.getMessage());
            }
            interpreter.handle(input);
            if (input.equals("\\exit")) {
                break;
            }
        }
    }
}

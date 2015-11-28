package track.project.commands.executor;

import track.project.commands.Command;
import track.project.message.Message;
import track.project.message.result.ExitResultMessage;
import track.project.session.Session;

/**
 * Created by ����� on 18.10.2015.
 */
public class ExitCommand implements Command {
    private final String description = "\\exit - close the program";

    @Override
    public void execute(Session session, Message message) {
        Message resultMessage = ExitResultMessage.getResultOk();
        session.getConnectionHandler().send(resultMessage);
    }

    @Override
    public String getDescription() {
        return description;
    }
}

package track.project.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.project.commands.result.CommandResult;
import track.project.commands.result.ResultMessage;
import track.project.commands.result.ResultStatus;
import track.project.message.Message;
import track.project.net.MessageListener;
import track.project.session.Session;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Булат on 04.11.2015.
 */
public class CommandHandler implements MessageListener {
    private static String COMMAND_EXECUTED_LOG = "{} executed: {}";
    private static String SEND_FAILED_LOG = "Exception sending ResultMessage {}: {}";
    static Logger log = LoggerFactory.getLogger(CommandHandler.class);

    Map<CommandType, Command> commands;

    public CommandHandler(Map<CommandType, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void onMessage(Session session, Message message) {
        Command cmd = commands.get(message.getType());
        log.info("onMessage: {} type {}", message, message.getType());
        CommandResult commandResult = cmd.execute(session, message);
        ResultStatus resultStatus = commandResult.getStatus();
        log.info(COMMAND_EXECUTED_LOG, message.getType(), commandResult.getStatus());
        Message resultMessage;

        // TODO: sendMessage можно вынести из блока case
        switch (resultStatus) {
            case OK:
                resultMessage = commandResult.getMessage();
                sendMessage(session, resultMessage);
                break;
            case FAILED:
                resultMessage = new ResultMessage(getCommandName(cmd) + " command failed due to system exception");
                sendMessage(session, resultMessage);
                break;
            case NOT_LOGGINED:
                resultMessage = new ResultMessage("Sign in to use " + getCommandName(cmd) + " command");
                sendMessage(session, resultMessage);
                break;
        }

    }

    private void sendMessage(Session session, Message resultMessage) {
        try {
            session.getConnectionHandler().send(resultMessage);
        } catch (IOException e) {
            log.info(SEND_FAILED_LOG, resultMessage, e.getMessage());
        }
    }

    private String getCommandName(Command cmd) {
        String descriptionFailed = cmd.getDescription();
        return descriptionFailed.substring(0, descriptionFailed.indexOf(' '));
    }
}

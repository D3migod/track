package track.project.commands.executor;

import track.project.commands.Command;
import track.project.commands.result.CommandResult;
import track.project.commands.result.ResultStatus;
import track.project.message.Message;
import track.project.message.request.UserMessage;
import track.project.session.Session;

/**
 * Created by ����� on 15.10.2015.
 */
public class UserCommand implements Command {
    private final String description = "\\user Nickname - changes your nick to \"Nickname\".";

    public UserCommand() {
    }

    @Override
    public CommandResult execute(Session session, Message message) {

        // FIXME: во всех местах поправьте, у вас IDEA не подсвечивает это место?
        if (session.isUserSet() == true) {
            session.getSessionUser().setNick(((UserMessage) message).getNick());
        } else {
            return new CommandResult(ResultStatus.NOT_LOGGINED);
        }

        // TODO: Для такого случая я бы в CommandResult создал статический инстанс
        // RESULT_OK = new CommandResult(); со статусом OK
        // а то слишком много объектов создается под капотом у new CommandResult()
        return new CommandResult();
    }

    @Override
    public String getDescription() {
        return description;
    }
}

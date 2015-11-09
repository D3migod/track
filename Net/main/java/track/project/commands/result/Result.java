package track.project.commands.result;

import track.project.message.Message;

/**
 * Created by Булат on 07.11.2015.
 */
public interface Result {
    ResultStatus getStatus();

    void setStatus(ResultStatus status);

    Message getMessage();

    void setMessage(Object object);
}

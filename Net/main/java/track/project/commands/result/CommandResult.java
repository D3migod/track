package track.project.commands.result;

import track.project.message.Message;

/**
 * Created by Булат on 07.11.2015.
 */
public class CommandResult implements Result {
    private ResultStatus status;
    private Message message;

    public CommandResult() {
        this.status = ResultStatus.OK;
        this.message = new ResultMessage();
    }

    public CommandResult(Object returnedObject) {
        this.status = ResultStatus.OK;
        this.message = new ResultMessage(returnedObject);
    }

    public CommandResult(Object returnedObject, ResultStatus status) {
        this.status = status;
        this.message = new ResultMessage(returnedObject);
    }

    @Override
    public ResultStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    @Override
    public Message getMessage() {
        return message;
    }

    @Override
    public void setMessage(Object object) {
        ((ResultMessage) message).setReturnedObject(object);
    }
}

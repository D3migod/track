package track.project.commands.result;

import track.project.commands.CommandType;
import track.project.message.Message;

/**
 * Created by Булат on 08.11.2015.
 */
public class ResultMessage extends Message {
    Object returnedObject;

    public ResultMessage() {
        setType(CommandType.MSG_RESULT);
    }

    public ResultMessage(Object returnedObject) {
        setType(CommandType.MSG_RESULT);
        this.returnedObject = returnedObject;
    }

    public Object getReturnedObject() {
        return returnedObject;
    }

    public void setReturnedObject(Object returnedObject) {
        this.returnedObject = returnedObject;
    }
}

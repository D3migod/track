package track.project.message.request;

import track.project.commands.CommandType;
import track.project.message.Message;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Булат on 09.11.2015.
 */
public class ChatCreateMessage extends Message {
    private List<Long> participantsIds = new LinkedList<>();

    public ChatCreateMessage() {
        setType(CommandType.CHAT_CREATE);
    }

    public List<Long> getParticipantsIds() {
        return participantsIds;
    }

    public void setParticipantsIds(List<Long> participantsIds) {
        this.participantsIds = participantsIds;
    }
}

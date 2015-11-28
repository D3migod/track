package track.project.message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Булат on 05.11.2015.
 */
public class Chat {

    private Long id;
    /**
     * Храним список идентификаторов
     */
    private List<Long> messageIds = new ArrayList<>();
    private List<Long> participantIds = new ArrayList<>();

    public Chat() {

    }

    public Chat(List<Long> participantIds) {
        this.participantIds = participantIds;
    }

    public Chat(Long id, List<Long> messageIds, List<Long> participantIds) {
        this.id = id;
        this.messageIds = messageIds;
        this.participantIds = participantIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(List<Long> messageIds) {
        this.messageIds = messageIds;
    }

    public List<Long> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
    }

    public void addParticipant(Long id) {
        participantIds.add(id);
    }

    public void addMessage(Long id) {
        messageIds.add(id);
    }
}
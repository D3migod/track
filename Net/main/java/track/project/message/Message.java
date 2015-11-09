package track.project.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import track.project.commands.CommandType;
import track.project.commands.result.ResultMessage;
import track.project.message.request.ChatCreateMessage;
import track.project.message.request.ChatFindMessage;
import track.project.message.request.ChatHistoryMessage;
import track.project.message.request.ChatSendMessage;
import track.project.message.request.ExitMessage;
import track.project.message.request.HelpMessage;
import track.project.message.request.LoginMessage;
import track.project.message.request.RegisterMessage;
import track.project.message.request.UserInfoMessage;
import track.project.message.request.UserMessage;
import track.project.message.request.UserPassMessage;

/**
 * Created by Булат on 04.11.2015.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({@JsonSubTypes.Type(value = ExitMessage.class, name = "exit"),
        @JsonSubTypes.Type(value = ChatCreateMessage.class, name = "chat_create"),
        @JsonSubTypes.Type(value = ChatFindMessage.class, name = "chat_find"),
        @JsonSubTypes.Type(value = HelpMessage.class, name = "help"),
        @JsonSubTypes.Type(value = ChatHistoryMessage.class, name = "chat_history"),
        @JsonSubTypes.Type(value = LoginMessage.class, name = "login"),
        @JsonSubTypes.Type(value = RegisterMessage.class, name = "register"),
        @JsonSubTypes.Type(value = ChatSendMessage.class, name = "chat_send"),
        @JsonSubTypes.Type(value = UserMessage.class, name = "user"),
        @JsonSubTypes.Type(value = UserInfoMessage.class, name = "user_info"),
        @JsonSubTypes.Type(value = UserPassMessage.class, name = "user_pass"),
        @JsonSubTypes.Type(value = ResultMessage.class, name = "result"),
        @JsonSubTypes.Type(value = SendMessage.class, name = "send")})
public class Message {
    private Long id;
    private Long sender;

    private CommandType type;

    public Message() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Message{" +
                ", sender=" + sender +
                ", type=" + type +
                '}';
    }
}

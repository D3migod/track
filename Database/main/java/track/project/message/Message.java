package track.project.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import track.project.commands.CommandType;
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
import track.project.message.result.ChatCreateResultMessage;
import track.project.message.result.ChatFindResultMessage;
import track.project.message.result.ChatHistoryResultMessage;
import track.project.message.result.ChatSendResultMessage;
import track.project.message.result.ExitResultMessage;
import track.project.message.result.HelpResultMessage;
import track.project.message.result.LoginResultMessage;
import track.project.message.result.RegisterResultMessage;
import track.project.message.result.UserInfoResultMessage;
import track.project.message.result.UserPassResultMessage;
import track.project.message.result.UserResultMessage;

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
        @JsonSubTypes.Type(value = ExitResultMessage.class, name = "exit_result"),
        @JsonSubTypes.Type(value = ChatCreateResultMessage.class, name = "chat_create_result"),
        @JsonSubTypes.Type(value = ChatFindResultMessage.class, name = "chat_find_result"),
        @JsonSubTypes.Type(value = HelpResultMessage.class, name = "help_result"),
        @JsonSubTypes.Type(value = ChatHistoryResultMessage.class, name = "chat_history_result"),
        @JsonSubTypes.Type(value = LoginResultMessage.class, name = "login_result"),
        @JsonSubTypes.Type(value = RegisterResultMessage.class, name = "register_result"),
        @JsonSubTypes.Type(value = ChatSendResultMessage.class, name = "chat_send_result"),
        @JsonSubTypes.Type(value = UserResultMessage.class, name = "user_result"),
        @JsonSubTypes.Type(value = UserInfoResultMessage.class, name = "user_info_result"),
        @JsonSubTypes.Type(value = UserPassResultMessage.class, name = "user_pass_result")})
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

    public boolean equalsWithNulls(Object a, Object b) {
        if (a == b) return true;
        if ((a == null) || (b == null)) return false;
        return a.equals(b);
    }
}

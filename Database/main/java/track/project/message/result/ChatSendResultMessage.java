package track.project.message.result;

import track.project.message.Message;
import track.project.message.request.ChatSendMessage;
import track.project.message.result.base.ResultMessage;
import track.project.message.result.base.ResultStatus;

/**
 * Created by Булат on 19.11.2015.
 */
public class ChatSendResultMessage extends Message implements ResultMessage {
    private ResultStatus status;
    private String statusInfo;
    private ChatSendMessage response;
    private static ChatSendResultMessage RESULT_OK = new ChatSendResultMessage();

    public ChatSendResultMessage() {
        status = ResultStatus.OK;
    }

    public ChatSendResultMessage(Message response) {
        this.response = (ChatSendMessage) response;
        status = ResultStatus.OK;
    }

    public ChatSendResultMessage(String statusInfo, ResultStatus status) {
        this.statusInfo = statusInfo;
        this.status = status;
    }

    public ChatSendResultMessage(ResultStatus status) {
        this.status = status;
    }

    @Override
    public void printMessage() {
        if (response != null) {
            System.out.printf("%s\n", response.getTimeString() + ": " + response.getMessage());
        }
    }

    public ChatSendMessage getResponse() {
        return response;
    }

    public void setResponse(ChatSendMessage response) {
        this.response = response;
    }

    public static ChatSendResultMessage getResultOk() {
        return RESULT_OK;
    }

    @Override
    public void printStatusInfo() {
        if (statusInfo != null) {
            System.out.printf("%s\n", statusInfo);
        }
    }

    @Override
    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    @Override
    public String getStatusInfo() {
        return statusInfo;
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
    public boolean equals(Object other) {

        if (!(other instanceof ChatSendResultMessage)) {
            return false;
        }
        ChatSendResultMessage that = (ChatSendResultMessage) other;
        return equalsWithNulls(getSender(), that.getSender()) &&
                equalsWithNulls(getId(), that.getId()) &&
                equalsWithNulls(getType(), that.getType()) &&
                equalsWithNulls(status, that.status) &&
                equalsWithNulls(statusInfo, that.statusInfo) &&
                equalsWithNulls(response, that.response);
    }
}

package track.project.message.result;

import track.project.message.Message;
import track.project.message.request.ChatSendMessage;
import track.project.message.result.additional.ResultMessage;
import track.project.message.result.additional.ResultStatus;

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
            System.out.printf("%s", response.getTimeMessage());
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
        System.out.printf(statusInfo);
    }

    @Override
    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
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
    public boolean messageIsNull() {
        return response == null;
    }
}

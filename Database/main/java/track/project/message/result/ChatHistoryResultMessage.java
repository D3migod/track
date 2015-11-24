package track.project.message.result;

import track.project.message.Message;
import track.project.message.result.additional.ResultMessage;
import track.project.message.result.additional.ResultStatus;

import java.util.List;

/**
 * Created by Булат on 19.11.2015.
 */
public class ChatHistoryResultMessage extends Message implements ResultMessage {
    private ResultStatus status;
    private String statusInfo;
    private List<String> response;

    public ChatHistoryResultMessage() {
        status = ResultStatus.OK;
    }

    public ChatHistoryResultMessage(List<String> response) {
        this.response = response;
        status = ResultStatus.OK;
    }

    public ChatHistoryResultMessage(String statusInfo, ResultStatus status) {
        this.statusInfo = statusInfo;
        this.status = status;
    }

    public ChatHistoryResultMessage(ResultStatus status) {
        this.status = status;
    }

    @Override
    public void printMessage() {
        if (!response.isEmpty()) {
            for (String responseString : response) {
                System.out.printf("%s", responseString + "\n");
            }
        }
    }

    public List<String> getResponse() {
        return response;
    }

    public void setResponse(List<String> response) {
        this.response = response;
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

package track.project.message.result;

import track.project.message.Message;
import track.project.message.result.additional.ResultMessage;
import track.project.message.result.additional.ResultStatus;

import java.util.List;

/**
 * Created by Булат on 19.11.2015.
 */
public class ChatCreateResultMessage extends Message implements ResultMessage {
    private ResultStatus status;
    private String statusInfo;
    private List<Long> response;
    private static ResultMessage RESULT_OK = new ChatCreateResultMessage();

    public ChatCreateResultMessage() {
        status = ResultStatus.OK;
    }

    public ChatCreateResultMessage(List<Long> response) {
        status = ResultStatus.OK;
        this.response = response;
    }

    public ChatCreateResultMessage(String statusInfo, ResultStatus status) {
        this.statusInfo = statusInfo;
        this.status = status;
    }

    public ChatCreateResultMessage(ResultStatus status) {
        this.status = status;
    }

    @Override
    public void printMessage() {
        if (!response.isEmpty()) {
            System.out.printf("The following participants do not exist:\n");
            for (Long responseLong : response) {
                System.out.printf("%s", responseLong.toString() + "\n");
            }
        }
    }

    public static ResultMessage getResultOk() {
        return RESULT_OK;
    }

    public List<Long> getResponse() {
        return response;
    }

    public void setResponse(List<Long> response) {
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

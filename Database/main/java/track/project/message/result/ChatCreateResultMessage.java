package track.project.message.result;

import track.project.message.Message;
import track.project.message.result.base.ResultMessage;
import track.project.message.result.base.ResultStatus;

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

    public ChatCreateResultMessage(List<Long> response, String statusInfo) {
        status = ResultStatus.OK;
        this.response = response;
        this.statusInfo = statusInfo;
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
        System.out.printf("Chat created with id: ");
        printStatusInfo();
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
}

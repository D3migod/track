package track.project.message.result;

import track.project.message.Message;
import track.project.message.result.base.ResultMessage;
import track.project.message.result.base.ResultStatus;

/**
 * Created by Булат on 19.11.2015.
 */
public class UserResultMessage extends Message implements ResultMessage {
    private ResultStatus status;
    private String statusInfo;
    private static UserResultMessage RESULT_OK = new UserResultMessage();
    String response;

    public UserResultMessage() {
        status = ResultStatus.OK;
    }

    public UserResultMessage(String response) {
        status = ResultStatus.OK;
        this.response = response;
    }

    public UserResultMessage(String statusInfo, ResultStatus status) {
        this.statusInfo = statusInfo;
        this.status = status;
    }

    public UserResultMessage(ResultStatus status) {
        this.status = status;
    }

    @Override
    public void printMessage() {
        System.out.printf(response + "\n");
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public static UserResultMessage getResultOk() {
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
}

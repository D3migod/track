package track.project.message.result;

import track.project.message.Message;
import track.project.message.result.additional.ResultMessage;
import track.project.message.result.additional.ResultStatus;

import java.util.List;

/**
 * Created by Булат on 19.11.2015.
 */
public class UserInfoResultMessage extends Message implements ResultMessage {
    private ResultStatus status;
    private String statusInfo;
    private List<String> response;

    public UserInfoResultMessage() {
        status = ResultStatus.OK;
    }

    public UserInfoResultMessage(List<String> response) {
        this.response = response;
        status = ResultStatus.OK;
    }

    public UserInfoResultMessage(String statusInfo, ResultStatus status) {
        this.statusInfo = statusInfo;
        this.status = status;
    }

    public UserInfoResultMessage(ResultStatus status) {
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

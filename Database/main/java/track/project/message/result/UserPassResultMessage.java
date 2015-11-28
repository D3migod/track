package track.project.message.result;

import track.project.message.Message;
import track.project.message.result.base.ResultMessage;
import track.project.message.result.base.ResultStatus;

import java.util.List;

/**
 * Created by Булат on 19.11.2015.
 */
public class UserPassResultMessage extends Message implements ResultMessage {
    private ResultStatus status;
    private String statusInfo;
    private List<String> response;
    private static UserPassResultMessage RESULT_OK = new UserPassResultMessage();

    public UserPassResultMessage() {
        status = ResultStatus.OK;
    }

    public UserPassResultMessage(List<String> response) {
        this.response = response;
        status = ResultStatus.OK;
    }

    public UserPassResultMessage(String statusInfo, ResultStatus status) {
        this.statusInfo = statusInfo;
        this.status = status;
    }

    public UserPassResultMessage(ResultStatus status) {
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

    public static UserPassResultMessage getResultOk() {
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

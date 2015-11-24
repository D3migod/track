package track.project.message.result;

import track.project.message.Message;
import track.project.message.result.additional.ResultMessage;
import track.project.message.result.additional.ResultStatus;

/**
 * Created by Булат on 19.11.2015.
 */
public class ExitResultMessage extends Message implements ResultMessage {
    private ResultStatus status;
    private String statusInfo;
    private static ExitResultMessage RESULT_OK = new ExitResultMessage();
    String response;

    public ExitResultMessage() {
        status = ResultStatus.OK;
    }

    public ExitResultMessage(String response) {
        status = ResultStatus.OK;
        this.response = response;
    }

    public ExitResultMessage(String statusInfo, ResultStatus status) {
        this.statusInfo = statusInfo;
        this.status = status;
    }

    public ExitResultMessage(ResultStatus status) {
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

    public static ExitResultMessage getResultOk() {
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

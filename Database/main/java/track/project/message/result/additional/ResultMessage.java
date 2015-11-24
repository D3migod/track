package track.project.message.result.additional;

/**
 * Created by Булат on 22.11.2015.
 */
public interface ResultMessage {
    void printMessage();

    void printStatusInfo();

    void setStatusInfo(String statusInfo);

    ResultStatus getStatus();

    void setStatus(ResultStatus status);

    boolean messageIsNull();
}

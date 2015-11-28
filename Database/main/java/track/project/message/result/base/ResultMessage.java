package track.project.message.result.base;

/**
 * Created by Булат on 22.11.2015.
 */
public interface ResultMessage {
    void printMessage();

    void printStatusInfo();

    String getStatusInfo();

    void setStatusInfo(String statusInfo);

    ResultStatus getStatus();

    void setStatus(ResultStatus status);
}

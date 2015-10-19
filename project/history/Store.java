package track.project.history;

/**
 * Created by Булат on 17.10.2015.
 */
public interface Store {

    public void writeIntoFile();

    public void readFromFile();

    public boolean isFileExist();

}

package track.project.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Булат on 23.11.2015.
 */
public interface ResultHandler<T> {
    T handle(ResultSet resultSet) throws SQLException;
}
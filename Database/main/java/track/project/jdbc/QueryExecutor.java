package track.project.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * Created by Булат on 23.11.2015.
 */
public class QueryExecutor {
    private static Logger log = LoggerFactory.getLogger(QueryExecutor.class);

    public <T> T execQuery(Connection connection, String query, ResultHandler<T> handler) {
        T value = null;
        try {
            if (connection == null) {
                log.info("connection == null");
            }
            Statement stmt = connection.createStatement();
            if (stmt == null) {
                log.info("stmt == null");
            }
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            if (result == null) {
                log.info("result == null");
            }
            value = handler.handle(result);
            if (result != null) {
                result.close();
            }
            stmt.close();
        } catch (SQLException e) {
            log.info("SQLException: {}", e);
        }
        return value;
    }


    // Подготовленный запрос
    public <T> T execQuery(Connection connection, String query, Map<Integer, Object> args, ResultHandler<T> handler) {
        T value = null;

        if (connection == null) {
            log.info("connection == null");
            return null;
        }
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(query);
        } catch (SQLException e) {
            log.info("SQLException in preparing statement: " + e.getMessage());
            return null;
        }
        if (stmt == null) {
            log.info("stmt == null");
            return null;
        }
        try {
            for (Map.Entry<Integer, Object> entry : args.entrySet()) {
                stmt.setObject(entry.getKey(), entry.getValue());
            }
        } catch (SQLException e) {
            log.info("SQLException in setting object: " + e.getMessage());
            return null;
        }
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            log.info("SQLException in executing query: " + e.getMessage());
            return null;
        }
        if (rs == null) {
            log.info("result == null");
        }
        try {
            value = handler.handle(rs);
        } catch (SQLException e) {
            log.info("SQLException in handling result: " + e.getMessage());
            return null;
        }
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            log.info("SQLException in closing rs: " + e.getMessage());
            return null;
        }
        try {
            stmt.close();
        } catch (SQLException e) {
            log.info("SQLException in closing stmt: " + e.getMessage());
            return null;
        }
        log.info("value: {}", value);
        return value;
    }
}

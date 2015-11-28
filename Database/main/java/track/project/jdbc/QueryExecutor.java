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
            Statement stmt = connection.createStatement();
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            value = handler.handle(result);
            if (result != null) {
                result.close();
            }
            stmt.close();
        } catch (SQLException e) {
            log.info("SQLException: " + e.getMessage());
        }
        return value;
    }


    // Подготовленный запрос
    public <T> T execQuery(Connection connection, String query, Map<Integer, Object> args, ResultHandler<T> handler) {
        T value = null;
        try {
            PreparedStatement stmt = null;
            stmt = connection.prepareStatement(query);
            for (Map.Entry<Integer, Object> entry : args.entrySet()) {
                stmt.setObject(entry.getKey(), entry.getValue());
            }
            ResultSet rs = stmt.executeQuery();
            if (rs == null) {
                log.info("result == null");
            }
            value = handler.handle(rs);
            if (rs != null) {
                rs.close();
            }

            stmt.close();
        } catch (SQLException e) {
            log.info("SQLException in exec query: " + e.getMessage());
            return null;
        }
        return value;
    }

    public Long execUpdate(Connection connection, String query, Map<Integer, Object> args, String columnName) {
        Long id = null;
        try {
            PreparedStatement stmt = connection.prepareStatement(query,
                    new String[]{columnName});
            for (Map.Entry<Integer, Object> entry : args.entrySet()) {
                stmt.setObject(entry.getKey(), entry.getValue());
            }
            if (stmt.executeUpdate() > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (null != generatedKeys && generatedKeys.next()) {
                    id = generatedKeys.getLong(1);
                }
                generatedKeys.close();
            }

            stmt.close();
        } catch (SQLException e) {
            log.info("SQLException in exec update: " + e.getMessage());
        }
        return id;
    }

    public void execUpdate(Connection connection, String query, Map<Integer, Object> args) {
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            for (Map.Entry<Integer, Object> entry : args.entrySet()) {
                stmt.setObject(entry.getKey(), entry.getValue());
            }
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException e) {
            log.info("SQLException in exec update: " + e.getMessage());
        }
    }

    public Long execUpdate(Connection connection, String query, String columnName) {
        Long id = null;
        try {
            PreparedStatement stmt = connection.prepareStatement(query,
                    new String[]{columnName});
            if (stmt.executeUpdate() > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (null != generatedKeys && generatedKeys.next()) {
                    id = generatedKeys.getLong(1);
                }
                generatedKeys.close();
            }
            stmt.close();
        } catch (SQLException e) {
            log.info("SQLException in exec update: " + e.getMessage());
        }
        return id;
    }

    public void execUpdate(Connection connection, String query) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException e) {
            log.info("SQLException in exec update: " + e.getMessage());
        }
    }
}

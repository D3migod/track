package track.project.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.project.jdbc.table.MessageStoreDatabaseTable;
import track.project.jdbc.table.UserDatabaseTable;
import track.project.net.SessionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Булат on 23.11.2015.
 */
public class DBConnector {
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_CONNECTION = "jdbc:postgresql://178.62.140.149:5432/D3migod";
    private static final String DB_USER = "senthil";
    private static final String DB_PASSWORD = "ubuntu";
    private static Logger log = LoggerFactory.getLogger(DBConnector.class);
    private Connection connection;
    private QueryExecutor executor;

    public DBConnector() {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            log.info("Class not found exception: {}", e.getMessage());
        }
        try {
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            if (connection == null) {
                log.info("Connection is null!");
            }
        } catch (SQLException e) {
            log.info("SQLException: {}", e.getMessage());
        }
        executor = new QueryExecutor();
    }

    public UserDatabaseTable createUserDatabaseTable() {
        return new UserDatabaseTable(executor, connection);
    }

    public MessageStoreDatabaseTable createMessageStoreDatabaseTable(SessionManager sessionManager) {
        return new MessageStoreDatabaseTable(executor, connection, sessionManager);
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.info("Failed to close connection: {}", e);
            }
        }
    }
}



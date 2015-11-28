package track.project.jdbc;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.project.jdbc.table.UserDatabaseTable;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Булат on 23.11.2015.
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({@JsonSubTypes.Type(value = UserDatabaseTable.class, name = "userdatabase")})
public abstract class DatabaseTable {
    private static Logger log = LoggerFactory.getLogger(DatabaseTable.class);
    protected List<String> columns;
    protected String tableName;
    protected QueryExecutor exec;
    protected Connection connection;


    public DatabaseTable() {

    }

    public DatabaseTable(List<String> columns, String tableName, QueryExecutor exec, Connection connection) {
        this.columns = columns;
        this.tableName = tableName;
        this.exec = exec;
        this.connection = connection;
    }


    protected String createTableQuery(List<String> columnTypes) {
        String query = "CREATE TABLE IF NOT EXISTS " + tableName + "(";
        int size = columns.size();
        String columnName;
        for (int i = 0; i < size - 1; i++) {
            columnName = columns.get(i);
            query += columnName + " " + columnTypes.get(i) + ", ";
        }
        columnName = columns.get(size - 1);
        query += columnName + " " + columnTypes.get(size - 1) + ")";
        log.info("query: " + query);
        return query;
    }

    protected String insertIdQuery() {
        String query = "INSERT INTO " + tableName + " ( id ) VALUES ( DEFAULT );";
        log.info("query: " + query);
        return query;
    }

    protected String insertQuery() {
        String query = "INSERT INTO " + tableName + " (";
        int size = columns.size();
        for (int i = 1; i < size - 1; i++) {
            query += columns.get(i) + ", ";
        }
        query += columns.get(size - 1);

        query += ") VALUES(";
        for (int i = 1; i < size - 1; i++) {
            query += "?,";
        }
        query += "?);";

        log.info("query: " + query);
        return query;
    }

    protected String insertWithIdQuery() {
        String query = "INSERT INTO " + tableName + " (";
        int size = columns.size();
        for (int i = 0; i < size - 1; i++) {
            query += columns.get(i) + ", ";
        }
        query += columns.get(size - 1);
        query += ") VALUES(";
        for (int i = 0; i < size - 1; i++) {
            query += "?,";
        }
        query += "?);";
        log.info("query: " + query);
        return query;
    }

    protected String selectAllQuery() {
        String query = "SELECT * FROM " + tableName;
        log.info("query: " + query);
        return query;
    }

    protected String selectQuery(List<Integer> selectedColumns) {
        String query = "SELECT ";
        int size = selectedColumns.size();
        for (int i = 0; i < size - 1; i++) {
            query += columns.get(selectedColumns.get(i)) + ", ";
        }
        query += columns.get(selectedColumns.get(size - 1));
        query += " FROM " + tableName;
        log.info("query: " + query);
        return query;
    }

    protected String selectByIdQuery() {
        String query = "SELECT * FROM " + tableName + " WHERE id = ?";
        log.info("query: " + query);
        return query;
    }

    protected String selectAllWhereQuery(List<Integer> selectedColumns) {
        String query = "SELECT * FROM " + tableName + " WHERE ";
        int size = selectedColumns.size();
        for (int i = 0; i < size - 1; i++) {
            query += columns.get(selectedColumns.get(i)) + " = ?, ";
        }
        query += columns.get(selectedColumns.get(size - 1)) + " = ?;";
        log.info("query: " + query);
        return query;
    }

    protected String deleteByIdQuery() {
        return "DELETE " + tableName + "WHERE id = ?;";
    }

    protected String updateQueryById(List<Integer> selectedColumns) {
        String query = "UPDATE " + tableName + " SET ";
        int size = selectedColumns.size();
        for (int i = 0; i < size - 1; i++) {
            query += columns.get(selectedColumns.get(i)) + " = ?, ";
        }
        query += columns.get(selectedColumns.get(size - 1)) + " = ? WHERE id = ?;";
        log.info("query: " + query);
        return query;
    }

    protected List<String> getColumns() {
        return columns;
    }

    protected void setColumns(List<String> columns) {
        this.columns = columns;
    }

    protected String getTableName() {
        return tableName;
    }

    protected void setTableName(String tableName) {
        this.tableName = tableName;
    }
}

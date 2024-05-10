package com.dbmslightweight.dbmslightweight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class User {
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Database {
    private final Map<String, String> data = new HashMap<>();

    public void createTable(String tableName) {
        data.put(tableName, "");
    }

    public void insert(String tableName, String values) {
        String currentData = data.get(tableName);
        currentData += values + "\n";
        data.put(tableName, currentData);
    }

    public void executeSQLQuery(String query) {
        System.out.println("Executing Query: " + query);
    }

    public String select(String tableName) {
        return data.get(tableName);
    }

    public void update(String tableName, String values) {
        data.put(tableName, values);
    }

    public void delete(String tableName) {
        data.remove(tableName);
    }
}

class TransactionManager {
    private final List<String> transactionLog = new ArrayList<>();
    private boolean inTransaction = false;
    private String activeUser;

    public void beginTransaction() {
        inTransaction = true;
    }

    public void endTransaction() {
        inTransaction = false;
    }

    public void setActiveUser(String username) {
        activeUser = username;
    }

    public void commitTransaction(Database db) {
        if (inTransaction) {
            for (String query : transactionLog) {
                // Apply the query to the database
                // For simplicity, we'll just print it here
                System.out.println("Applying: " + query);
            }
            transactionLog.clear();
            endTransaction();
        }
    }

    public void rollbackTransaction() {
        if (inTransaction) {
            transactionLog.clear();
            endTransaction();
        }
    }

    public void addQueryToTransaction(String query) {
        if (inTransaction) {
            transactionLog.add(query);
        }
    }

    public List<String> getTransactionLog() {
        return transactionLog;
    }
}


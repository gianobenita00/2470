package com.example.repo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AccountRepo {

    public boolean accountExists(int accountId)
            throws SQLException {

        String sql =
            "SELECT account_id FROM Accounts WHERE account_id = ?";

        try (
            Connection connection =
                DatabaseConnection.getConnection();

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(1, accountId);

            ResultSet resultSet =
                statement.executeQuery();

            return resultSet.next();
        }
    }


    public int createAccount(String pin)
            throws SQLException {

        String sql =
            "INSERT INTO Accounts (pin) " +
            "VALUES (?) RETURNING account_id";

        try (
            Connection connection =
                DatabaseConnection.getConnection();

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setString(1, pin);

            ResultSet resultSet =
                statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("account_id");
            }

            return -1;
        }
    }


    public boolean login(int accountId, String pin)
            throws SQLException {

        String sql =
            "SELECT account_id FROM Accounts " +
            "WHERE account_id = ? AND pin = ?";

        try (
            Connection connection =
                DatabaseConnection.getConnection();

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(1, accountId);
            statement.setString(2, pin);

            ResultSet resultSet =
                statement.executeQuery();

            return resultSet.next();
        }
    }

    public BigDecimal getBalance(int accountId)
            throws SQLException {

        String sql =
                "SELECT balance FROM Accounts WHERE account_id = ?";
        
        try (
            Connection connection = 
                DatabaseConnection.getConnection();

            PreparedStatement statement = 
                connection.prepareStatement(sql)
        ) {

            statement.setInt(1, accountId);

            ResultSet resultSet = 
                statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBigDecimal("Balance");
            }

            return null;
        }
    }

    public void deposit(int accountId, BigDecimal amount)
            throws SQLException {

        String updateBalanceSql =
            "UPDATE Accounts " +
            "SET balance = balance + ? " +
            "WHERE account_id = ?";

        String transactionSql =
            "INSERT INTO Transactions " +
            "(account_id, transaction_type, amount) " +
            "VALUES (?, 'DEPOSIT', ?)";

        try (Connection connection =
                DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try (
                PreparedStatement updateStatement =
                    connection.prepareStatement(updateBalanceSql);

                PreparedStatement transactionStatement =
                    connection.prepareStatement(transactionSql)
            ) {

                updateStatement.setBigDecimal(1, amount);
                updateStatement.setInt(2, accountId);

                updateStatement.executeUpdate();

                transactionStatement.setInt(1, accountId);
                transactionStatement.setBigDecimal(2, amount);

                transactionStatement.executeUpdate();

                connection.commit();

            } catch (SQLException e) {

                connection.rollback();

                throw e;
            }
        }   
    }

    public void withdraw(int accountId, BigDecimal amount)
            throws SQLException {

        String updateBalanceSql =
            "UPDATE Accounts " +
            "SET balance = balance - ? " +
            "WHERE account_id = ?";

        String transactionSql =
            "INSERT INTO Transactions " +
            "(account_id, transaction_type, amount) " +
            "VALUES (?, 'WITHDRAW', ?)";

        try (Connection connection =
                DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try (
                PreparedStatement updateStatement =
                    connection.prepareStatement(updateBalanceSql);

                PreparedStatement transactionStatement =
                    connection.prepareStatement(transactionSql)
            ) {

                updateStatement.setBigDecimal(1, amount);
                updateStatement.setInt(2, accountId);

                updateStatement.executeUpdate();

                transactionStatement.setInt(1, accountId);
                transactionStatement.setBigDecimal(2, amount);

                transactionStatement.executeUpdate();

                connection.commit();

            } catch (SQLException e) {

                connection.rollback();

                throw e;
            }
        }
    }

    public void transfer(int fromAccountId, int toAccountId, BigDecimal amount)
            throws SQLException {

        String withdrawSql =
            "UPDATE Accounts " +
            "SET balance = balance - ? " +
            "WHERE account_id = ?";

        String depositSql =
            "UPDATE Accounts " +
            "SET balance = balance + ? " +
            "WHERE account_id = ?";

        String transactionSql =
            "INSERT INTO Transactions " +
            "(account_id, transaction_type, amount, related_account_id) " +
            "VALUES (?, ?, ?, ?)";

        try (Connection connection =
                DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try (
                PreparedStatement withdrawStatement =
                    connection.prepareStatement(withdrawSql);

                PreparedStatement depositStatement =
                    connection.prepareStatement(depositSql);

                PreparedStatement transactionStatement =
                    connection.prepareStatement(transactionSql)
            ) {

                withdrawStatement.setBigDecimal(1, amount);
                withdrawStatement.setInt(2, fromAccountId);

                withdrawStatement.executeUpdate();

                depositStatement.setBigDecimal(1, amount);
                depositStatement.setInt(2, toAccountId);

                depositStatement.executeUpdate();

                transactionStatement.setInt(1, fromAccountId);
                transactionStatement.setString(2, "TRANSFER_OUT");
                transactionStatement.setBigDecimal(3, amount);
                transactionStatement.setInt(4, toAccountId);

                transactionStatement.executeUpdate();

                transactionStatement.setInt(1, toAccountId);
                transactionStatement.setString(2, "TRANSFER_IN");
                transactionStatement.setBigDecimal(3, amount);
                transactionStatement.setInt(4, fromAccountId);

                transactionStatement.executeUpdate();

                connection.commit();

            } catch (SQLException e) {

                connection.rollback();

                throw e;
            }
        }
    }

    public List<String> getTransactionHistory(int accountId)
            throws SQLException {

        String sql =
            "SELECT transaction_type, amount, related_account_id, created_at " +
            "FROM Transactions " +
            "WHERE account_id = ? " +
            "ORDER BY created_at DESC " +
            "LIMIT 10";

        List<String> transactions = new ArrayList<>();

        try (
            Connection connection =
                DatabaseConnection.getConnection();

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(1, accountId);

            ResultSet resultSet =
                statement.executeQuery();

            while (resultSet.next()) {

                String type =
                    resultSet.getString("transaction_type");

                BigDecimal amount =
                    resultSet.getBigDecimal("amount");

                int relatedAccountId =
                    resultSet.getInt("related_account_id");

                String transaction =
                    type + " | $" + amount;

                if (!resultSet.wasNull()) {

                    transaction +=
                        " | Related Account: " +
                        relatedAccountId;
                }

                transaction +=
                    " | " +
                    resultSet.getTimestamp("created_at");

                transactions.add(transaction);
            }
        }

        return transactions;
    }

}
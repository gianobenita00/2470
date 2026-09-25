package com.example.business;

import com.example.repo.AccountRepo;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;


public class AccountServices {

    private AccountRepo accountRepo;

    public AccountServices() {

        accountRepo = new AccountRepo();

    }

    public int registerAccount(String pin) throws SQLException {

        if (pin == null || !pin.matches("[0-9]{4}")) {

            throw new IllegalArgumentException(
                "PIN must be 4 digits."
            );

        }

        return accountRepo.createAccount(pin);
    }

    public boolean loginAccount(int accountId, String pin)
            throws SQLException {
        
        if (accountId <= 0) {
            throw new IllegalArgumentException("Invalid Account ID.");
        }

        if (pin == null || !pin.matches("[0-9]{4}")) {
            throw new IllegalArgumentException(
                "PIN must be exactly 4 digits."
            );
        }

        return accountRepo.login(accountId, pin);
    }

    public BigDecimal getBalance(int accountId)
            throws SQLException {

        if (accountId <= 0) {
            throw new IllegalArgumentException(
                "Invalid Account ID."
            );
        }

        BigDecimal balance = 
                accountRepo.getBalance(accountId);

        if (balance == null) {
            throw new IllegalArgumentException(
                "Account does not exist."
            );
        }

        return balance; 
    }

    public void deposit(int accountId, BigDecimal amount)
        throws SQLException {

        if (accountId <= 0) {
            throw new IllegalArgumentException(
                "Invalid Account ID."
            );
        }

        if (amount == null ||
            amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                "Deposit amount must be greater than zero."
            );
        }

        if (!accountRepo.accountExists(accountId)) {

            throw new IllegalArgumentException(
                "Account does not exist."
            );
        }

        accountRepo.deposit(accountId, amount);
    }

    public void withdraw(int accountId, BigDecimal amount)
        throws SQLException {

        if (accountId <= 0) {
            throw new IllegalArgumentException(
                "Invalid Account ID."
            );
        }

        if (amount == null ||
            amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                "Withdrawal amount must be greater than zero."
            );
        }

        BigDecimal balance =
            accountRepo.getBalance(accountId);

        if (balance == null) {
            throw new IllegalArgumentException(
                "Account does not exist."
            );
        }

        if (amount.compareTo(balance) > 0) {
            throw new IllegalArgumentException(
                "Insufficient funds."
            );
        }

        accountRepo.withdraw(accountId, amount);
    }

    public void transfer(int fromAccountId, int toAccountId, BigDecimal amount)
            throws SQLException {

        if (fromAccountId <= 0 || toAccountId <= 0) {

            throw new IllegalArgumentException(
                "Invalid Account ID."
            );
        }

        if (fromAccountId == toAccountId) {

            throw new IllegalArgumentException(
                "You cannot transfer money to the same account."
            );
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                "Transfer amount must be greater than zero."
            );
        }

        if (!accountRepo.accountExists(fromAccountId)) {

            throw new IllegalArgumentException(
                "Sender account does not exist."
            );
        }

        if (!accountRepo.accountExists(toAccountId)) {

            throw new IllegalArgumentException(
                "Receiving account does not exist."
            );
        }

        BigDecimal senderBalance =
            accountRepo.getBalance(fromAccountId);

        if (amount.compareTo(senderBalance) > 0) {

            throw new IllegalArgumentException(
                "Insufficient funds."
            );
        }

        accountRepo.transfer(
            fromAccountId,
            toAccountId,
            amount
        );
    }

    public List<String> getTransactionHistory(int accountId)
            throws SQLException {

        if (accountId <= 0) {

            throw new IllegalArgumentException(
                "Invalid Account ID."
            );
       }

        if (!accountRepo.accountExists(accountId)) {

           throw new IllegalArgumentException(
                "Account does not exist."
            );
        }

        return accountRepo.getTransactionHistory(accountId);
    }    

}

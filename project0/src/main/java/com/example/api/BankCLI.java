package com.example.api;

import com.example.business.AccountServices;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BankCLI {

    private static final Logger logger =
        LogManager.getLogger(BankCLI.class);

    private final AccountServices accountServices;
    private final Scanner scanner;

    public BankCLI() {

        accountServices = new AccountServices();
        scanner = new Scanner(System.in);
    }

    public void start() {

        boolean running = true;

        while (running) {

            System.out.println("\n===== BANK OF CLI =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            try {

                switch (choice) {

                    case "1":
                        register();
                        break;

                    case "2":
                        login();
                        break;

                    case "3":
                        running = false;
                        System.out.println("Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid option.");
                }

            } catch (SQLException e) {

                logger.error(
                    "Database error occurred.",
                    e
                );

                System.out.println(
                    "Service unavailable. Please try again later."
                );
            }
        }

        scanner.close();
    }


    private void register() throws SQLException {

        System.out.print("Create a 4-digit PIN: ");

        String pin = scanner.nextLine();

        try {

            int accountId =
                accountServices.registerAccount(pin);

            logger.info(
                "Account {} successfully registered.",
                accountId
            );

            System.out.println(
                "Account created successfully!"
            );

            System.out.println(
                "Your Account ID is: " + accountId
            );

        } catch (IllegalArgumentException e) {

            logger.error(
                "Account registration failed: {}",
                e.getMessage()
            );

            System.out.println(
                e.getMessage()
            );
        }
    }


    private void login() throws SQLException {

        try {

            System.out.print("Account ID: ");

            int accountId =
                Integer.parseInt(
                    scanner.nextLine()
                );

            System.out.print("PIN: ");

            String pin =
                scanner.nextLine();

            boolean loggedIn =
                accountServices.loginAccount(
                    accountId,
                    pin
                );

            if (loggedIn) {

                logger.info(
                    "Account {} successfully logged in.",
                    accountId
                );

                System.out.println(
                    "Login successful!"
                );

                accountMenu(accountId);

            } else {

                logger.error(
                    "Failed login attempt for account {}.",
                    accountId
                );

                System.out.println(
                    "Invalid Account ID or PIN."
                );
            }

        } catch (NumberFormatException e) {

            logger.error(
                "Login failed because Account ID was not a number."
            );

            System.out.println(
                "Account ID must be a number."
            );

        } catch (IllegalArgumentException e) {

            logger.error(
                "Login failed: {}",
                e.getMessage()
            );

            System.out.println(
                e.getMessage()
            );
        }
    }


    private void accountMenu(int accountId)
            throws SQLException {

        boolean loggedIn = true;

        while (loggedIn) {

            System.out.println(
                "\n===== ACCOUNT MENU ====="
            );

            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Transaction History");
            System.out.println("6. Logout");

            System.out.print("Choose an option: ");

            String choice =
                scanner.nextLine();

            try {

                switch (choice) {

                    case "1":
                        checkBalance(accountId);
                        break;

                    case "2":
                        deposit(accountId);
                        break;

                    case "3":
                        withdraw(accountId);
                        break;

                    case "4":
                        transfer(accountId);
                        break;

                    case "5":
                        showTransactionHistory(accountId);
                        break;

                    case "6":

                        logger.info(
                            "Account {} logged out.",
                            accountId
                        );

                        loggedIn = false;

                        System.out.println(
                            "Logged out."
                        );

                        break;

                    default:
                        System.out.println(
                            "Invalid option."
                        );
                }

            } catch (NumberFormatException e) {

                logger.error(
                    "Invalid numeric input from account {}.",
                    accountId
                );

                System.out.println(
                    "Please enter a valid number."
                );

            } catch (IllegalArgumentException e) {

                logger.error(
                    "Action failed for account {}: {}",
                    accountId,
                    e.getMessage()
                );

                System.out.println(
                    e.getMessage()
                );
            }
        }
    }


    private void checkBalance(int accountId)
            throws SQLException {

        BigDecimal balance =
            accountServices.getBalance(accountId);

        logger.info(
            "Account {} checked balance.",
            accountId
        );

        System.out.println(
            "Current balance: $" + balance
        );
    }


    private void deposit(int accountId)
            throws SQLException {

        System.out.print(
            "Deposit amount: $"
        );

        BigDecimal amount =
            new BigDecimal(
                scanner.nextLine()
            );

        accountServices.deposit(
            accountId,
            amount
        );

        logger.info(
            "Account {} deposited ${}.",
            accountId,
            amount
        );

        System.out.println(
            "Deposit successful!"
        );

        System.out.println(
            "Current balance: $" +
            accountServices.getBalance(accountId)
        );
    }


    private void withdraw(int accountId)
            throws SQLException {

        System.out.print(
            "Withdrawal amount: $"
        );

        BigDecimal amount =
            new BigDecimal(
                scanner.nextLine()
            );

        accountServices.withdraw(
            accountId,
            amount
        );

        logger.info(
            "Account {} withdrew ${}.",
            accountId,
            amount
        );

        System.out.println(
            "Withdrawal successful!"
        );

        System.out.println(
            "Current balance: $" +
            accountServices.getBalance(accountId)
        );
    }


    private void transfer(int accountId)
            throws SQLException {

        System.out.print(
            "Transfer to Account ID: "
        );

        int receivingAccountId =
            Integer.parseInt(
                scanner.nextLine()
            );

        System.out.print(
            "Transfer amount: $"
        );

        BigDecimal amount =
            new BigDecimal(
                scanner.nextLine()
            );

        accountServices.transfer(
            accountId,
            receivingAccountId,
            amount
        );

        logger.info(
            "Account {} transferred ${} to account {}.",
            accountId,
            amount,
            receivingAccountId
        );

        System.out.println(
            "Transfer successful!"
        );

        System.out.println(
            "Current balance: $" +
            accountServices.getBalance(accountId)
        );
    }


    private void showTransactionHistory(
            int accountId)
            throws SQLException {

        List<String> transactions =
            accountServices.getTransactionHistory(
                accountId
            );

        logger.info(
            "Account {} viewed transaction history.",
            accountId
        );

        System.out.println(
            "\n===== RECENT TRANSACTIONS ====="
        );

        if (transactions.isEmpty()) {

            System.out.println(
                "No transactions found."
            );

            return;
        }

        for (String transaction : transactions) {

            System.out.println(transaction);
        }
    }
}
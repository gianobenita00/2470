package com.example.repo;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class AccountRepoTest {

    @Test
    void accountExistsShouldReturnTrueForExistingAccount()
            throws SQLException {

        AccountRepo accountRepo = new AccountRepo();

        boolean result = accountRepo.accountExists(1);

        assertTrue(result);
    }

    @Test
    void accountExistsShouldReturnFalseForMissingAccount()
            throws SQLException {

        AccountRepo accountRepo = new AccountRepo();

        boolean result = accountRepo.accountExists(999);

        assertFalse(result);
    }

    @Test 
    void loginShouldReturnTrueForCorrectCredentials()
        throws SQLException {

            AccountRepo accountRepo = new AccountRepo();

            boolean result = accountRepo.login(1, "1234");

            assertTrue(result);
        }

    @Test 
    void loginShouldReturnFalseForWrongPin()
        throws SQLException {

            AccountRepo accountRepo = new AccountRepo();

            boolean result = accountRepo.login(1, "9999");

            assertFalse(result);
        }

    @Test 
    void getbalanceShouldReturnBalanceForExistingAcount()
            throws SQLException {
                
        AccountRepo accountRepo = 
            new AccountRepo();

        BigDecimal balance = 
                accountRepo.getBalance(1);

        assertNotNull(balance);
    }      

    @Test 
    void getBalanceShouldReturnNullForMissingAccount()
            throws SQLException {

        AccountRepo accountRepo = 
                new AccountRepo();

        BigDecimal balance =
                accountRepo.getBalance(999);

        assertNull(balance);
    }


}
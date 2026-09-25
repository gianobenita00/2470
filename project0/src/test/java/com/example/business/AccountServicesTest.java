package com.example.business;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServicesTest {
    
    @Test 
    void registerAccountShouldReturnAccountIdForValidPin()
        throws SQLException {
            
            AccountServices accountServices = new AccountServices();

            int accountId = accountServices.registerAccount("3456");

            assertTrue(accountId > 0);
        }

    @Test 
    void registerAccountShouldRejectInvalidPin() {

        AccountServices accountServices = new AccountServices();

        assertThrows(
            IllegalArgumentException.class, ()
            -> accountServices.registerAccount("12")
        );
    }

    @Test 
    void loginAccountShouldReturnTrueForCorrectCredentials()
            throws SQLException{

        AccountServices accountServices = 
                new AccountServices();

        boolean result = 
                accountServices.loginAccount(1,"1234");

        assertTrue(result);
    }


    @Test 
    void loginAccountShouldRejectInvalidPin() {

        AccountServices accountServices =
            new AccountServices();

        assertThrows(
            IllegalArgumentException.class,
            () -> accountServices.loginAccount(1,"12") 
        );
    }

    @Test
    void getbalanceShouldReturnBalanceForExistingAcount()
            throws SQLException {

        AccountServices accountServices =
                new AccountServices();

        BigDecimal balance = 
                accountServices.getBalance(1);

        assertNotNull(balance);
    }

    @Test 
    void getBalanceShouldRejectMissingAccount() {

        AccountServices accountServices = 
                new AccountServices();

        assertThrows(
            IllegalArgumentException.class,
            () -> accountServices.getBalance(999)
        );
    }

}

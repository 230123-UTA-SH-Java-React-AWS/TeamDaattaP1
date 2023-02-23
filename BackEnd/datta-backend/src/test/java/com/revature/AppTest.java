package com.revature;

import org.junit.jupiter.api.Test;

import com.revature.service.AccountService;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
class AppTest {
    /**
     * Rigorous Test.
     */
    @Test
    void testApp() {
        AccountService service = new AccountService();

        String json = "{'email' : 'garbage'}";
        service.registerUser(json);
        assertEquals(1, 1);
    }
}

package bank.impl;

import akka.Done;
import akka.NotUsed;
import bank.api.Account;
import bank.api.AccountService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import org.junit.After;
import org.junit.Before;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
/*

public class AccountServiceImplTest {

    static class AccountStub implements AccountService {
        @Override
        public ServiceCall<Integer, Done> updateBalance(String id) {
            Map<String, String> response = new HashMap<>();
            response.put("success","true")
            return null;
        }

        @Override
        public ServiceCall<Account, Done> createAccount() {
            return null;
        }

        @Override
        public ServiceCall<NotUsed, Integer> getAccountBalance(String id) {
            return null;
        }
    }
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

}*/

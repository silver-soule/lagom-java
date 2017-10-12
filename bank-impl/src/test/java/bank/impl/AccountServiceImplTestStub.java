package bank.impl;

import akka.Done;
import akka.NotUsed;
import bank.api.Account;
import bank.api.AccountCreatedEvt;
import bank.api.AccountService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.broker.Topic;
import com.lightbend.lagom.javadsl.testkit.ServiceTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static com.lightbend.lagom.javadsl.testkit.ServiceTest.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;

public class AccountServiceImplTestStub {

    private static ServiceTest.TestServer server;
    private static AccountService accountService;
    private static Setup setup = defaultSetup().withCassandra(false).withPersistence(true)
            .withConfigureBuilder(b -> b.overrides(bind(AccountService.class).to(AccountStub.class)));

    @Before
    public void setUp() throws Exception {
        server = startServer(setup);
        accountService = server.client(AccountService.class);
    }

    @After
    public void tearDown() throws Exception {
        if(server!=null) {
            server.stop();
            server = null;
        }
    }

    static class AccountStub implements AccountService {

        @Override
        public ServiceCall<NotUsed, Optional<String>> getUserByCountry(String country) {
            return null;
        }

        @Override
        public ServiceCall<Integer, Done> updateBalance(String id) {
            Map<String, String> response = new HashMap<>();
            response.put("success", "Done");
            return request -> CompletableFuture.completedFuture(Done.getInstance());
        }

        @Override
        public ServiceCall<Account, Done> createAccount() {
            Map<String, String> response = new HashMap<>();
            response.put("success", "created user");
            return returnrequest -> CompletableFuture.completedFuture(Done.getInstance());
        }

        @Override
        public ServiceCall<NotUsed, Integer> getAccountBalance(String id) {
            Map<String, String> response = new HashMap<>();
            response.put("success", "100");
            return returnrequest -> CompletableFuture.completedFuture(Integer.valueOf(22));
        }

        @Override
        public Topic<AccountCreatedEvt> accountCreatedTopic() {
            throw new UnsupportedOperationException();
        }
    }

    @Test
    public void updateBalanceTest(){
        try{
            int balance = accountService.getAccountBalance("ab").invoke().toCompletableFuture().get(12, TimeUnit.SECONDS);
            assertEquals(balance,22);
        }
        catch (ExecutionException ex){
            System.out.print("a");
        }
        catch (TimeoutException ex){
            System.out.print("B");
        }
        catch (InterruptedException ex){
            System.out.print("c");
        }
    }

/*    @Test
    public void createAccountTest(){
        assertEquals(accountService.createAccount().invoke(),Done.getInstance());
    }

    @Test
    public void accountBalanceTest(){
        assertEquals(accountService.getAccountBalance("neelaksh").invoke(),22);
    }*/


}
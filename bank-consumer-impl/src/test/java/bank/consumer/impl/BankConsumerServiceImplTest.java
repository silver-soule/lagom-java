package bank.consumer.impl;

import akka.Done;
import akka.NotUsed;
import bank.api.Account;
import bank.api.AccountCreatedEvt;
import bank.api.AccountService;
import bank.consumer.api.BankConsumerService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.broker.Topic;
import com.lightbend.lagom.javadsl.testkit.ProducerStubFactory;
import com.lightbend.lagom.javadsl.testkit.ServiceTest;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.inject.Inject;

import java.util.Optional;

import static bank.api.AccountService.ACCOUNT_CREATED_TOPIC;
import static com.lightbend.lagom.javadsl.testkit.ServiceTest.*;
import static org.junit.Assert.*;

public class BankConsumerServiceImplTest {

    private static ServiceTest.TestServer server;
    @BeforeClass
    public static void setUp(){
        server = startServer(defaultSetup().withCassandra(true));

    }

    @AfterClass
    public static void tearDown(){
        if(server!=null){
            server.stop();
            server = null;
        }
    }

    @Test
    public void demoUrl(){
        BankConsumerService service = server.client(BankConsumerService.class);
        assertEquals(service.tokenCall().invoke(), Done.getInstance());
    }
    static class AccountServiceStub implements AccountService {
        @Inject
        AccountServiceStub(ProducerStubFactory producerFactory){
            throw new UnsupportedOperationException();
        }

        @Override
        public ServiceCall<NotUsed, Optional<String>> getUserByCountry(String country) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Topic<AccountCreatedEvt> accountCreatedTopic() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ServiceCall<NotUsed, Integer> getAccountBalance(String id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ServiceCall<Integer, Done> updateBalance(String id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ServiceCall<Account, Done> createAccount() {
            throw new UnsupportedOperationException();
        }
    }


}
package bank.consumer.impl;

import akka.Done;
import akka.NotUsed;
import bank.api.Account;
import bank.api.AccountCreatedEvt;
import bank.api.AccountService;
import bank.consumer.api.BankConsumerService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.broker.Topic;
import com.lightbend.lagom.javadsl.testkit.ProducerStub;
import com.lightbend.lagom.javadsl.testkit.ProducerStubFactory;
import com.lightbend.lagom.javadsl.testkit.ServiceTest;
import org.junit.Test;

import javax.inject.Inject;

import static com.lightbend.lagom.javadsl.testkit.ServiceTest.*;
/*

public class BankConsumerTest {
    private ServiceTest.Setup setup = defaultSetup().configureBuilder(b ->
    b.overrides(
            bind(BankConsumerService.class).to(BankConsumerServiceImpl.class),
            bind(AccountService.class).to(AccountServiceStub.class)
    ));

    private static ProducerStub<AccountCreatedEvt> accountDataProducer;

    @Test
    public void shouldReceiveMsg(){
        withServer(defaultSetup(),server ->{
            AccountCreatedEvt accountCreatedEvt = new AccountCreatedEvt("neelaksh",9999);
            BankConsumerService client = server.client(BankConsumerService.class);
            accountDataProducer.send(accountCreatedEvt);
        });
    }

    static class AccountServiceStub implements AccountService{
        @Inject
        AccountServiceStub(ProducerStubFactory producerFactory){
            accountDataProducer = producerFactory.producer(ACCOUNT_CREATED_TOPIC);
        }

        @Override
        public Topic<AccountCreatedEvt> accountCreatedTopic() {
            return accountDataProducer.topic();
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
*/

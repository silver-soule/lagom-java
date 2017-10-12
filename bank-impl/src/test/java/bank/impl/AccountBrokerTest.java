package bank.impl;
import akka.stream.javadsl.Source;
import akka.stream.testkit.TestSubscriber;
import akka.stream.testkit.javadsl.TestSink;
import bank.api.Account;
import bank.api.AccountCreatedEvt;
import bank.api.AccountService;
import org.junit.Test;
import play.Logger;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.*;
import static com.lightbend.lagom.javadsl.testkit.ServiceTest.*;

public class AccountBrokerTest {

    @Test
    public void shouldEmitAccountCreatedMsg(){
        withServer(defaultSetup().withCassandra(true),server -> {
            AccountService accountService = server.client(AccountService.class);
            Source<AccountCreatedEvt,?> source =
                    accountService.accountCreatedTopic().subscribe().atMostOnceSource();
            TestSubscriber.Probe<AccountCreatedEvt> probe =
                    source.runWith(
                            TestSink.probe(server.system()),server.materializer()
                    );
            accountService.createAccount().invoke(new Account("neelaksh",122,"1234",9999,"India"))
                .toCompletableFuture().get(10, SECONDS);

            FiniteDuration finiteDuration = new FiniteDuration(35, SECONDS);
            Logger.info("FINE\n\n\n");
            AccountCreatedEvt actual = probe.request(1).expectNext(finiteDuration);
            AccountCreatedEvt expected = new AccountCreatedEvt("neelaksh",9999);
            Logger.info(actual.toString());
            assertEquals(expected.getName(),actual.getName());
        });
    }

}

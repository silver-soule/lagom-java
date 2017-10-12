package bank.consumer.impl;

import akka.Done;
import akka.NotUsed;
import akka.stream.javadsl.Flow;
import bank.api.AccountCreatedEvt;
import bank.api.AccountService;
import bank.consumer.api.BankConsumerService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import play.Logger;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

public class BankConsumerServiceImpl implements BankConsumerService{


    private final AccountService accountService;

    @Inject
    public BankConsumerServiceImpl(AccountService accountService) {
        this.accountService = accountService;
        accountService.accountCreatedTopic()
                .subscribe()
                .atLeastOnce(Flow.fromFunction((AccountCreatedEvt message) -> {
                    Logger.info("\n\n\n" + message.toString().toUpperCase() + "\n\n\n");
                    return Done.getInstance();
                }));
    }



    @Override
    public ServiceCall<NotUsed, Done> tokenCall() {
        return (notUsed) -> CompletableFuture.completedFuture(Done.getInstance());
    }


}

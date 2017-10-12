package bank.consumer.impl;

import akka.Done;
import akka.NotUsed;
import akka.stream.javadsl.Flow;
import bank.api.AccountCreatedEvt;
import bank.api.AccountService;
import bank.consumer.api.BankConsumerService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import external.service.api.ExternalService;
import external.service.api.User;
import play.Logger;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class BankConsumerServiceImpl implements BankConsumerService {


    private final AccountService accountService;
    private final ExternalService externalService;

    @Inject
    public BankConsumerServiceImpl(AccountService accountService, ExternalService externalService) {
        this.accountService = accountService;
        this.externalService = externalService;
        accountService.accountCreatedTopic()
                .subscribe()
                .atLeastOnce(Flow.fromFunction((AccountCreatedEvt message) -> {
                    Logger.info("\n\n\n" + message.toString().toUpperCase() + "\n\n\n");
                    return Done.getInstance();
                }));
    }


    @Override
    public ServiceCall<NotUsed, User> getUser(String userName) {
        return request -> {
            CompletionStage<User> user = externalService.getUser(userName).invoke();
            return user;
        };
    }

    @Override
    public ServiceCall<NotUsed, Done> tokenCall() {
        return (notUsed) -> CompletableFuture.completedFuture(Done.getInstance());
    }


}

package bank.consumer.impl;

import bank.api.AccountService;
import bank.consumer.api.BankConsumerService;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class BankConsumerModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindService(BankConsumerService.class, BankConsumerServiceImpl.class);
        bindClient(AccountService.class);
    }
}

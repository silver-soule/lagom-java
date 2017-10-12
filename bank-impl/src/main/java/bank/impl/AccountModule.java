package bank.impl;

import bank.api.AccountService;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class AccountModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindService(AccountService.class, AccountServiceImpl.class);
    }
}

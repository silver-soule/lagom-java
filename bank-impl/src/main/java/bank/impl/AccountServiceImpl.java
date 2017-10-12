package bank.impl;

import akka.Done;
import akka.NotUsed;
import akka.japi.Pair;
import bank.api.Account;
import bank.api.AccountCreatedEvt;
import bank.api.AccountService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.broker.Topic;
import com.lightbend.lagom.javadsl.broker.TopicProducer;
import com.lightbend.lagom.javadsl.persistence.*;
import com.lightbend.lagom.javadsl.persistence.ReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import play.Logger;

import javax.inject.Inject;

import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    final PersistentEntityRegistry persistentEntityRegistry;
    final CassandraSession cassandraSession;

    @Inject
    public AccountServiceImpl(final PersistentEntityRegistry persistentEntityRegistry,
                              ReadSide readSide,
                              CassandraSession cassandraSession
                              ) {
        this.persistentEntityRegistry = persistentEntityRegistry;
        persistentEntityRegistry.register(AccountEntity.class);
        this.cassandraSession = cassandraSession;
        readSide.register(AccountCreatedEventProcessor.class);
    }

    @Override
    public ServiceCall<NotUsed, Integer> getAccountBalance(String id) {
        return request -> {
            Logger.info("REACHED\n\n\n");
            PersistentEntityRef<AccountCommand> ref = persistentEntityRegistry.refFor(AccountEntity.class, id);
            return ref.ask(new AccountCommand.Balance(id));
        };
    }

    @Override
    public ServiceCall<Account, Done> createAccount() {
        return request -> {
            PersistentEntityRef<AccountCommand> ref = persistentEntityRegistry.refFor(AccountEntity.class, request.accountNumber);
            return ref.ask(new AccountCommand.CreateAccount(request.accountNumber, request.pin, request.mobileNum, request.name, request.country));
        };
    }

    @Override
    public ServiceCall<Integer, Done> updateBalance(String id) {
        return request -> {
            PersistentEntityRef<AccountCommand> ref = persistentEntityRegistry.refFor(AccountEntity.class, id);
            return ref.ask(new AccountCommand.Deposit(id, request.intValue()));
        };
    }

    @Override
    public Topic<AccountCreatedEvt> accountCreatedTopic() {
        return TopicProducer.singleStreamWithOffset(offset ->
                persistentEntityRegistry.eventStream(AccountEvent.AccountCreated.ACCOUNT_CREATED_TAG, offset)
                        .map(this::convertEvent)
        );
    }

    public Pair<AccountCreatedEvt, Offset> convertEvent(Pair<AccountEvent.AccountCreated, Offset> event) {
        AccountEvent.AccountCreated evt = event.first();
        Logger.info("creating pair\n\n\n\n");
        return new Pair(new AccountCreatedEvt(evt.getUserName(), evt.getMobileNum()), event.second());
    }

    @Override
    public ServiceCall<NotUsed, Optional<String>> getUserByCountry(String country) {
        return request ->
            cassandraSession.selectAll("select * from userinfo where country = ?", country)
                    .thenApply(rows -> rows.stream()
                            .map(row -> row.getString("name"))
                            .findFirst()
                    );
    }
}

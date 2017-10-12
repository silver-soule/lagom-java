package bank.impl;

import akka.Done;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.ReadSideProcessor;

import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import org.pcollections.TreePVector;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;

public class AccountCreatedEventProcessor extends ReadSideProcessor<AccountEvent.AccountCreated> {
    private final CassandraSession session;
    private final CassandraReadSide readSide;
    private PreparedStatement writeData = null;

    @Inject
    public AccountCreatedEventProcessor(CassandraSession session, CassandraReadSide readSide) {
        this.session = session;
        this.readSide = readSide;
    }

    @Override
    public ReadSideProcessor.ReadSideHandler<AccountEvent.AccountCreated> buildHandler() {
        return readSide
                .<AccountEvent.AccountCreated>builder("accountinfo")
                .setGlobalPrepare(this::createTable)
                .setPrepare(tag -> prepareAccountCreate())
                .setEventHandler(AccountEvent.AccountCreated.class, this::processAccountAdded)
                .build();
    }

    private CompletionStage<List<BoundStatement>> processAccountAdded(AccountEvent.AccountCreated event) {
        BoundStatement bindWriteTitle = writeData.bind();
        bindWriteTitle.setString("accountnumber", String.valueOf(event.getMobileNum()));
        bindWriteTitle.setString("country", event.getCoutnry());
        bindWriteTitle.setString("name", event.getUserName());
        return CassandraReadSide.completedStatements(Arrays.asList(bindWriteTitle));
    }


    private CompletionStage<Done> prepareAccountCreate() {
        return session.prepare("INSERT INTO userinfo (accountnumber, country, name) VALUES (?, ?, ?);")
                .thenApply(ps -> {
                    this.writeData = ps;
                    return Done.getInstance();
                });
    }

    @Override
    public TreePVector<AggregateEventTag<AccountEvent.AccountCreated>> aggregateTags() {
        return TreePVector.singleton(AccountEvent.AccountCreated.ACCOUNT_CREATED_TAG);
    }

    private CompletionStage<Done> createTable() {
        return session.executeCreateTable("Create table if not exists userinfo" +
                "(accountnumber Text, country TEXT, name TEXT, PRIMARY KEY (country,accountnumber))");
    }
}

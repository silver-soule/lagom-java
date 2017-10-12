package bank.api;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.*;
import com.lightbend.lagom.javadsl.api.broker.Topic;
import com.lightbend.lagom.javadsl.api.transport.Method;

import java.util.List;
import java.util.Optional;

import static com.lightbend.lagom.javadsl.api.Service.*;
import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

public interface AccountService extends Service {

    ServiceCall<NotUsed, Integer> getAccountBalance(String id);

    ServiceCall<Integer, Done> updateBalance(String id);

    ServiceCall<Account, Done> createAccount();

    ServiceCall<NotUsed,Optional<String>> getUserByCountry(String country);

    Topic<AccountCreatedEvt> accountCreatedTopic();

    String ACCOUNT_CREATED_TOPIC = "created";

    @Override
    default Descriptor descriptor() {
        return named("account").withCalls(
                pathCall("/account/balance/:accountnum", this::getAccountBalance),
                restCall(Method.PUT, "/account/deposit/:accountNum", this::updateBalance),
                pathCall("/register", this::createAccount),
                pathCall("/users/:country",this::getUserByCountry)
        ).withTopics(topic(ACCOUNT_CREATED_TOPIC, this::accountCreatedTopic)).withAutoAcl(true);
    }
}



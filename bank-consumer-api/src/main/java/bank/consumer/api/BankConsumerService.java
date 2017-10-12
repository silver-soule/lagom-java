package bank.consumer.api;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import external.service.api.User;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

public interface BankConsumerService extends Service {

    ServiceCall<NotUsed, Done> tokenCall();

    ServiceCall<NotUsed,User> getUser(String userName);
    @Override
    default Descriptor descriptor(){
        return named("bankconsumer")
                .withCalls(pathCall("/token",this::tokenCall),
                        pathCall("/user/:username",this::getUser))
                .withAutoAcl(true);
    }
}

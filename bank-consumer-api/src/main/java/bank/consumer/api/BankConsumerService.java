package bank.consumer.api;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

public interface BankConsumerService extends Service {

    ServiceCall<NotUsed, Done> tokenCall();

    @Override
    default Descriptor descriptor(){
        return named("bankconsumer")
                .withCalls(pathCall("/token",this::tokenCall))
                .withAutoAcl(true);
    }
}

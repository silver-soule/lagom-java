package external.service.api;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;
import static com.lightbend.lagom.javadsl.api.Service.named;

public interface ExternalService extends Service {

    public ServiceCall<NotUsed,User> getUser(String userName);

    @Override
    default Descriptor descriptor() {
        return named("github-external")
                .withCalls(pathCall("/users/:username",this::getUser))
                .withAutoAcl(true);
    }
}

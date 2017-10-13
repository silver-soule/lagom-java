package external.service.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.concurrent.Immutable;

@Immutable
public class User {
    private String userName;
    private String name;
    private int followers;
    private int publicRepos;

    public User(){}

    @JsonProperty("login")
    public String getUserName() {
        return userName;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("followers")
    public int getFollowers() {
        return followers;
    }

    @JsonProperty("public_repos")
    public int getPublicRepos() {
        return publicRepos;
    }


}

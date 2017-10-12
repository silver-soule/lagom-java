package bank.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.serialization.Jsonable;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Nullable;
public class AccountState implements Jsonable {
    public final String userName;
    public final int balance;
    public final int encryptedPin;
    public final long mobileNum;
    public final String country;

    @JsonCreator
    public AccountState(@JsonProperty("userName") String userName, @JsonProperty("balance") int balance,
                        @JsonProperty("encryptedPin") int encryptedPin,@JsonProperty("mobileNum") long mobileNum,
                        @JsonProperty("country") String country) {
        this.userName = Preconditions.checkNotNull(userName, "userName");
        this.balance = balance;
        this.encryptedPin = encryptedPin;
        this.mobileNum = mobileNum;
        this.country = Preconditions.checkNotNull(country, "country");
    }

    @Override
    public int hashCode() {
        int h = 31;
        h = 17 * h + userName.hashCode();
        h = 17 * h + Integer.hashCode(balance);
        h = 17 * h + Long.hashCode(mobileNum);
        h = h * 17 + country.hashCode();
        return h;
    }

    @Override
    public boolean equals(@Nullable Object another) {
        if (this == another)
            return true;
        return another instanceof AccountState && equalTo((AccountState) another);
    }

    public boolean equalTo(AccountState another) {
        return this.userName == another.userName && this.balance == another.balance && this.mobileNum == another.mobileNum && country == another.country;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass().getName()).
                add("userName", userName).add("balance", balance).add("mobile number", mobileNum).toString();
    }
}

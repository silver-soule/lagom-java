package bank.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import play.Logger;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
@JsonDeserialize
public final class Account {

    @JsonProperty("name")
    public final String name;
    @JsonProperty("pin")
    public final int pin;
    @JsonProperty("accountnumber")
    public final String accountNumber;
    @JsonProperty("mobile-number")
    public final long mobileNum;
    @JsonProperty("country")
    public final String country;

    @JsonCreator
    public Account(@JsonProperty("name") String name, @JsonProperty("pin") int pin,
                   @JsonProperty("accountnumber") String accountNum,
                   @JsonProperty("mobile-number") long mobileNum,
                   @JsonProperty("country") String country) {
        Logger.info(String.format("creating account\n\n\n\n name - %s\n accountNum - %s \n pin - %d", name, accountNum, pin));
        this.name = Preconditions.checkNotNull(name, "invalid name");
        Logger.info("name created\n\n\n");

        this.accountNumber = Preconditions.checkNotNull(accountNum, "account number is null");
        Logger.info("accountnumber created\n\n\n");

        this.pin = pin;
        Logger.info("pin created\n\n\n");

        this.mobileNum = mobileNum;
        Logger.info("mobileNum created\n\n\n");

        this.country = country;
    }

    @Override
    public boolean equals(@Nullable Object another) {
        if (this == another)
            return true;
        return another instanceof Account && equalTo((Account) another);
    }

    private boolean equalTo(Account another) {
        return name.equals(another.name) && accountNumber.equals(another.accountNumber)
                && pin == another.pin && mobileNum == another.mobileNum && country == another.country;
    }

    @Override
    public int hashCode() {
        int h = 31;
        h = h * 17 + name.hashCode();
        h = h * 17 + Integer.hashCode(pin);
        h = h * 17 + accountNumber.hashCode();
        h = h * 17 + Long.hashCode(mobileNum);
        h = h * 17 + country.hashCode();
        return h;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("Account").add("name", name).add("country",country).toString();
    }
}

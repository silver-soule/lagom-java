package bank.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.serialization.Jsonable;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

public interface AccountEvent extends Jsonable {

    @SuppressWarnings("serializable")
    @Immutable
    @JsonDeserialize
    public class DepositedMoney implements AccountEvent {
        private int amount;
        private String country;

        public DepositedMoney(int amount,String country) {
            this.amount = Preconditions.checkNotNull(amount, "amount");
            this.country = Preconditions.checkNotNull(country,"country");
        }

        public DepositedMoney(){}

        public int getBalance(){
            return amount;
        }

        public String getCountry(){
            return country;
        }

        @Override
        public boolean equals(@Nullable Object another) {
            if (this == another)
                return true;
            return another instanceof DepositedMoney && equalTo((DepositedMoney) another);
        }

        private boolean equalTo(DepositedMoney another) {
            return amount == another.amount && country == another.country;
        }

        @Override
        public int hashCode() {
            int h = 31;
            h = h * 17 + Integer.hashCode(amount);
            h = h * 17 + country.hashCode();
            return h;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper("GreetingMessageChanged").add("amount", amount).toString();
        }
    }

    @SuppressWarnings("serializable")
    @Immutable
    @JsonDeserialize
    public class AccountCreated implements AccountEvent,AggregateEvent<AccountCreated> {
        private String userName;
        private int pin;
        private long mobileNum;
        private String coutnry;
        private String accountNumber;
        public static final AggregateEventTag<AccountCreated> ACCOUNT_CREATED_TAG = AggregateEventTag.of(AccountCreated.class);

        @Override
        public AggregateEventTag<AccountCreated> aggregateTag() {
            return ACCOUNT_CREATED_TAG;
        }


        public AccountCreated( int pin, long mobileNum, String userName,String coutnry,String accountNumber) {
            this.pin = Preconditions.checkNotNull(pin, "pin");
            this.mobileNum = Preconditions.checkNotNull(mobileNum, "mobilenumber");
            this.userName = Preconditions.checkNotNull(userName,"userName");
            this.coutnry = Preconditions.checkNotNull(coutnry,"country");
            this.accountNumber = Preconditions.checkNotNull(accountNumber,"accountnumber");
        }

        public AccountCreated(){}

        public int getPin(){
            return pin;
        }

        public long getMobileNum(){
            return mobileNum;
        }

        public String getUserName(){ return userName;}

        public String getCoutnry() {
            return coutnry;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        @Override
        public boolean equals(@Nullable Object another) {
            if (this == another)
                return true;
            return another instanceof AccountCreated && equalTo((AccountCreated) another);
        }

        private boolean equalTo(AccountCreated another) {
            return mobileNum == another.mobileNum && userName == another.userName;
        }

        @Override
        public int hashCode() {
            int h = 31;
            h = h * 17 + Long.hashCode(mobileNum);
            h = h * 17 + userName.hashCode();
            return h;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper("GreetingMessageChanged").add("mobile number",mobileNum).add("userName",userName).toString();
        }
    }

}

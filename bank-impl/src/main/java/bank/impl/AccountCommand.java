package bank.impl;

import akka.Done;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

public interface AccountCommand extends Jsonable {

    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    public final class Balance implements AccountCommand, CompressedJsonable, PersistentEntity.ReplyType<Integer> {
        private String accountNumber;

        public Balance(){}
        public Balance(String accountNumber) {
            this.accountNumber = Preconditions.checkNotNull(accountNumber, "message");
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        @Override
        public boolean equals(@Nullable Object another) {
            if (this == another)
                return true;
            return another instanceof Balance && equalTo((Balance) another);
        }

        private boolean equalTo(Balance another) {
            return accountNumber.equals(another.accountNumber);
        }

        @Override
        public int hashCode() {
            int h = 31;
            h = h * 17 + accountNumber.hashCode();
            return h;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper("Balance").add("accountNumber", accountNumber).toString();
        }
    }

    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    public final class Deposit implements AccountCommand, CompressedJsonable, PersistentEntity.ReplyType<Done> {
        private String accountNumber;
        private int amount;

        public Deposit(){}

        public Deposit(String accountNumber, int amount) {
            this.accountNumber = Preconditions.checkNotNull(accountNumber, "accountNumber");
            this.amount = Preconditions.checkNotNull(amount, "message");
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public int getAmount() {
            return amount;
        }

        @Override
        public boolean equals(@Nullable Object another) {
            if (this == another)
                return true;
            return another instanceof Deposit && equalTo((Deposit) another);
        }

        private boolean equalTo(Deposit another) {
            return accountNumber.equals(another.accountNumber) && (amount == another.amount);
        }

        @Override
        public int hashCode() {
            int h = 31;
            h = h * 17 + accountNumber.hashCode();
            h = h * 17 + amount;
            return h;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper("Deposit").add("accountNumber", accountNumber).add("amount", amount).toString();
        }
    }

    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    public final class CreateAccount implements AccountCommand, CompressedJsonable, PersistentEntity.ReplyType<Done> {
        private String accountNumber;
        private int pin;
        private long mobileNum;
        private String name;
        private String country;

        public CreateAccount(String accountNumber, int pin,long mobileNum,String name,String country) {
            this.accountNumber = Preconditions.checkNotNull(accountNumber, "accountNumber");
            this.pin = Preconditions.checkNotNull(pin, "pin");
            this.mobileNum = Preconditions.checkNotNull(mobileNum, "pin");
            this.name = name;
            this.country = country;
        }

        public CreateAccount(){}

        public int getPin() {
            return pin;
        }

        public String getAccountNumber(){ return accountNumber;}

        public String getName() {
            return name;
        }

        public long getMobileNum(){
            return mobileNum;
        }

        public String getCountry(){return country;}

        @Override
        public boolean equals(@Nullable Object another) {
            if (this == another)
                return true;
            return another instanceof CreateAccount && equalTo((CreateAccount) another);
        }

        private boolean equalTo(CreateAccount another) {
            return accountNumber.equals(another.accountNumber) && name.equals(another.name);
        }

        @Override
        public int hashCode() {
            int h = 31;
            h = h * 17 + accountNumber.hashCode();
            h = h * 17 + name.hashCode();
            return h;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper("CreateAccount").add("accountNumber", accountNumber).toString();
        }
    }
}

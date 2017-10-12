package bank.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import javax.annotation.concurrent.Immutable;

@Immutable
public class AccountCreatedEvt {
    private String name;
    private long mobileNumber;

    public AccountCreatedEvt( String name, long mobileNumber){
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    public AccountCreatedEvt(){}

    public String getName(){
        return name;
    }

    public long getMobileNumber(){
        return mobileNumber;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("AccountCreatedEvt").add("name",name).add("mobile number",mobileNumber).toString();
    }
}

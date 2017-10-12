package bank.impl;

import akka.actor.ActorSystem;
import akka.testkit.JavaTestKit;
import bank.api.AccountService;
import bank.impl.util.Encrypter;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.javadsl.testkit.PersistentEntityTestDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.lightbend.lagom.javadsl.testkit.PersistentEntityTestDriver.Outcome;
import static com.lightbend.lagom.javadsl.testkit.ServiceTest.*;
import static org.junit.Assert.*;

public class AccountEntityTest {
    static ActorSystem system;
    @Before
    public void setUp() throws Exception {
        system = ActorSystem.create();
    }

    @After
    public void tearDown() throws Exception {
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testCreateUser(){
        PersistentEntityTestDriver<AccountCommand,AccountEvent,AccountState> freshDriver =
                new PersistentEntityTestDriver<>(system,new AccountEntity(),"1234567");
        int pin = 322;
        int encryptedPin = Encrypter.encrypt(pin);
        AccountState actualState = new AccountState("neelaksh",0, encryptedPin,12345678,"India");

        AccountEvent actualEvent = new AccountEvent.AccountCreated(encryptedPin,12345678,"neelaksh","India","1234567");
        Outcome<AccountEvent,AccountState> outCome = freshDriver.run(new AccountCommand
                .CreateAccount("1234567",322,12345678,"neelaksh","India"));

        assertEquals(outCome.state(), actualState);

        assertEquals(outCome.events().get(0),actualEvent);

    }

    @Test
    public void invalidUserData(){
        PersistentEntityTestDriver<AccountCommand,AccountEvent,AccountState> driver =
                new PersistentEntityTestDriver<>(system,new AccountEntity(),"123456");
        driver.run(new AccountCommand.CreateAccount("123456",322,9999999,"sunny","India"));
        Outcome<AccountEvent,AccountState> outCome = driver.run(new AccountCommand.CreateAccount("123456",322,9999999,"sunny","India"));
        assertEquals(outCome.getReplies().get(0).getClass(), PersistentEntity.InvalidCommandException.class);
    }

    @Test
    public void depositMoney(){
        PersistentEntityTestDriver<AccountCommand,AccountEvent,AccountState> driver =
                new PersistentEntityTestDriver<>(system,new AccountEntity(),"123456");
        driver.run(new AccountCommand.CreateAccount("123456",322,9999999,"sunny","India"));
        String accountNum = "123456";
        int depositAmount = 10000;
        int pin = 322;
        int encryptedPin = Encrypter.encrypt(pin);
        AccountState actualState = new AccountState("sunny",10000, encryptedPin,9999999,"India");
        Outcome<AccountEvent,AccountState> outCome = driver.run(new AccountCommand.Deposit(accountNum,10000));
        assertEquals(outCome.state(),actualState);
    }

    @Test
    public void depositMoneyInvalid(){
        PersistentEntityTestDriver<AccountCommand,AccountEvent,AccountState> driver =
                new PersistentEntityTestDriver<>(system,new AccountEntity(),"123456");
        Outcome<AccountEvent,AccountState> outcome = driver.run(new AccountCommand.Deposit("123456",1000));
        assertEquals(outcome.getReplies().get(0).getClass(), PersistentEntity.InvalidCommandException.class);
    }

    @Test
    public void getBalance(){
        PersistentEntityTestDriver<AccountCommand,AccountEvent,AccountState> driver =
                new PersistentEntityTestDriver<>(system,new AccountEntity(),"123456");
        driver.run(new AccountCommand.CreateAccount("123456",322,9999999,"sunny","India"));
        Outcome<AccountEvent,AccountState> outcome = driver.run(new AccountCommand.Balance("123456"));
        assertEquals(outcome.getReplies().get(0),0);
    }
}
package bank.impl;

import akka.Done;
import bank.impl.util.Encrypter;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import java.util.Optional;

public class AccountEntity extends PersistentEntity<AccountCommand, AccountEvent, AccountState> {

    @Override
    public Behavior initialBehavior(Optional<AccountState> snapshotState) {

        BehaviorBuilder b = newBehaviorBuilder(snapshotState.orElse(null));

        b.setCommandHandler(AccountCommand.Deposit.class, (cmd, ctx) -> {
                    if (this.state() != null) {
                        return ctx.thenPersist(new AccountEvent.DepositedMoney(cmd.getAmount(),this.state().country),
                                evt -> ctx.reply(Done.getInstance()));
                    } else {
                        ctx.invalidCommand("user not found");
                        return ctx.done();
                    }
                }
        );

        b.setEventHandler(AccountEvent.DepositedMoney.class, evt ->
                new AccountState(this.state().userName, this.state().balance + evt.getBalance(), this.state().encryptedPin, this.state().mobileNum,this.state().country)
        );

        b.setCommandHandler(AccountCommand.CreateAccount.class, (cmd, ctx) -> {
                    if (this.state() == null)
                        return ctx.thenPersist(new AccountEvent.AccountCreated(Encrypter.encrypt(cmd.getPin()), cmd.getMobileNum(), cmd.getName(),cmd.getCountry(),cmd.getAccountNumber()),
                                evt -> ctx.reply(Done.getInstance()));
                    else {
                        ctx.invalidCommand("user already exists");
                        return ctx.done();
                    }
                }
        );

        b.setEventHandler(AccountEvent.AccountCreated.class, evt ->
                new AccountState(evt.getUserName(), 0, evt.getPin(), evt.getMobileNum(),evt.getCoutnry())
        );

        b.setReadOnlyCommandHandler(AccountCommand.Balance.class, (evt, ctx) -> {
                    if (state() != null)
                        ctx.reply(this.state().balance);
                    else {
                        ctx.invalidCommand("user not found");
                    }
                }
        );
        return b.build();

    }
}

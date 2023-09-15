package live.nerotv.api.paper.coins.vaultSupport;

import live.nerotv.api.paper.Nero;

import java.util.List;
import java.util.UUID;

public class Ecosystem implements Economy {

    @Override
    public boolean createAccount(UUID paramUUID) {
        return Nero.getCoinsystem().setCoins(paramUUID,0);
    }

    @Override
    public boolean hasAccount(UUID paramUUID) {
        return Nero.getCoinsystem().hasCoins(paramUUID);
    }

    @Override
    public boolean delete(UUID paramUUID) {
        return Nero.getCoinsystem().setCoins(paramUUID,0);
    }

    @Override
    public boolean withdraw(UUID paramUUID, double paramDouble) {
        return Nero.getCoinsystem().removeCoins(paramUUID,(int)paramDouble);
    }

    @Override
    public boolean deposit(UUID paramUUID, double paramDouble) {
        return Nero.getCoinsystem().addCoins(paramUUID,(int)paramDouble);
    }

    @Override
    public boolean set(UUID paramUUID, double paramDouble) {
        return Nero.getCoinsystem().setCoins(paramUUID,(int)paramDouble);
    }

    @Override
    public boolean has(UUID paramUUID, double paramDouble) {
        return (getBalance(paramUUID).getBalance() >= paramDouble);
    }

    @Override
    public UserBalance getBalance(UUID paramUUID) {
        return new UserBalance(paramUUID, Nero.getCoinsystem().getCoins(paramUUID));
    }

    @Override
    public List<UserBalance> getPlayers() { return null; }
}
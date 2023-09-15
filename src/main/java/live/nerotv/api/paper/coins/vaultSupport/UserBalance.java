package live.nerotv.api.paper.coins.vaultSupport;

import java.util.UUID;

public class UserBalance {

    private UUID uuid;
    private double balance;

    public UserBalance(UUID uuid, double balance) {
        this.uuid = uuid;
        this.balance = balance;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
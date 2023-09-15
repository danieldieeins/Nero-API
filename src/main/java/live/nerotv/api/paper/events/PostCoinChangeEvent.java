package live.nerotv.api.paper.events;

import live.nerotv.api.paper.Nero;
import live.nerotv.api.paper.coins.Coinsystem;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class PostCoinChangeEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final UUID userid;
    private final int amount;
    private final Coinsystem.ChangeType changeType;

    public PostCoinChangeEvent(UUID userid, Coinsystem.ChangeType changeType) {
        this.userid = userid;
        this.amount = Nero.getCoinsystem().getCoins(userid);
        this.changeType = changeType;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public int getAmount() {
        return amount;
    }

    public Coinsystem.ChangeType getChangeType() {
        return changeType;
    }

    public UUID getUUID() {
        return userid;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
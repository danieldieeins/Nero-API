package live.nerotv.api.paper.events;

import live.nerotv.api.paper.coins.Coinsystem;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class CoinChangeEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();
    private final UUID userid;
    private final int oldAmount;
    private int newAmount;
    private boolean cancelled;
    private final Coinsystem.ChangeType changeType;

    public CoinChangeEvent(UUID userid, int oldAmount, int newAmount, Coinsystem.ChangeType changeType) {
        this.userid = userid;
        this.oldAmount = oldAmount;
        this.newAmount = newAmount;
        this.changeType = changeType;
        this.cancelled = false;
    }

    public UUID getUUID() {
        return userid;
    }

    public int getOldAmount() {
        return oldAmount;
    }

    public int getNewAmount() {
        return newAmount;
    }

    public Coinsystem.ChangeType getChangeType() {
        return changeType;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public void setNewAmount(int newAmount) {
        this.newAmount = newAmount;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
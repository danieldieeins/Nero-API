package live.nerotv.api.paper.coins.vaultSupport;

import live.nerotv.api.paper.Nero;
import org.bukkit.plugin.ServicePriority;

import static org.bukkit.Bukkit.getServer;

public class VaultCoins {

    private static Economy eco = new Ecosystem();
    public static Economy getEco() { return eco; }
    private static boolean isEnabled = false;

    public static void setupEconomy() {
        if(Nero.getNeroServer().getConfig().getCFG().getBoolean("Coinsystem.vaultSupport")) {
            if (!isEnabled) {
                boolean setupEconomy;
                eco = (Economy) new Ecosystem();
                VaultEco vaultImpl = new VaultEco();
                if (getServer().getPluginManager().getPlugin("Vault") == null) {
                    setupEconomy = false;
                } else {
                    getServer().getServicesManager().register(net.milkbowl.vault.economy.Economy.class, vaultImpl, Nero.getInstance(), ServicePriority.Normal);
                    setupEconomy = true;
                }
                if (!setupEconomy) {
                    Nero.getNeroServer().sendMessage("§cEconomy konnte nicht gestartet werden.§8!");
                } else {
                    isEnabled = true;
                }
                Nero.getNeroServer().sendMessage(" §0 ");
            }
        }
    }

    public static boolean isEnabled() {
        return isEnabled;
    }
}
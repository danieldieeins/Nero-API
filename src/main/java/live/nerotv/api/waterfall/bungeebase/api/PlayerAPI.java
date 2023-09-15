package live.nerotv.api.waterfall.bungeebase.api;

import live.nerotv.api.waterfall.utils.communication.Communicate;
import live.nerotv.api.waterfall.utils.user.Sound;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerAPI {

    public static void playNewSound(ProxiedPlayer player, Sound sound) {
        Communicate.sendSound(player, sound.toString());
    }
}
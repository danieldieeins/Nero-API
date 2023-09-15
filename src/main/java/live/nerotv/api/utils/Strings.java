package live.nerotv.api.utils;

public class Strings {

    public static final String api_version = "1.17-1.20.1-RELEASE";

    private static String prefixWord = "§aa§8.§anerotv§8.§alive";

    public static String prefix(String prefixWord) {
        return "§9"+prefixWord+"§8 » §7";
    }

    public static String prefix() {
        return "§9"+prefixWord+" §8» §7";
    }

    public static String playerNotFound(String playerName) {
        return "§cDer/die Spieler/in §4"+playerName+"§c wurde nicht gefunden§8!";
    }

    public static String playerNotFound() {
        return "§cDer/die Spieler/in wurde nicht gefunden§8!";
    }

    public static String noPerms() {
        return "§cDas darfst du nicht§8!";
    }

    public static String notEnoughPoints() {
        return "§cDazu hast du nicht genug Punkte§8!";
    }

    public static String notEnoughMoney() {
        return "§cDazu hast du nicht genug Geld§8!";
    }

    public static String needPlayer() {
        return "§cDazu §4musst§c du ein Spieler sein§8!";
    }

    public static void setPrefixWord(String word) {
        prefixWord = word;
    }

    public static String backToLobbyItem() {
        return "§cZurück zur Lobby";
    }

    public static String commandNotFound() { return "§cDieser Command ist dem System nicht bekannt§8!"; }

    public static String getServerDomain() {
        return "a.nerotv.live";
    }
}
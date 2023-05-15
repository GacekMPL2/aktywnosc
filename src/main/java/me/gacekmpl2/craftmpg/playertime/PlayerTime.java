package me.gacekmpl2.craftmpg.playertime;
import java.util.HashMap;
import org.bukkit.entity.Player;

public class PlayerTime {
    private static final HashMap<Player, Long> player_time = new HashMap<>();

    public static Long getPlayerTime(Player player) {
        return player_time.get(player);
    }

    public static void addPlayer(Player player) {
        player_time.put(player, System.currentTimeMillis());
    }

    public static void removePlayer(Player player) {
        player_time.remove(player);
    }

    public static boolean constainsPlayer(Player player) {
        return player_time.containsKey(player);
    }
}

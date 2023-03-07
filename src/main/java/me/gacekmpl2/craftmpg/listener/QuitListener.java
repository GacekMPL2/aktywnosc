package me.gacekmpl2.craftmpg.listener;

import java.sql.SQLException;

import me.gacekmpl2.craftmpg.GlobalVariables;
import me.gacekmpl2.craftmpg.playertime.PlayerTime;
import me.gacekmpl2.craftmpg.utils.Debug;
import me.gacekmpl2.craftmpg.utils.TimeCountUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event) throws SQLException {
        Player player = event.getPlayer();
        if (player.hasPermission(GlobalVariables.getTime_count_perm())) {
            Debug.log("Czas: " + TimeCountUtils.getSecondsOnServer(event.getPlayer()));
            TimeCountUtils.savePlayer(player, TimeCountUtils.getSecondsOnServer(player));
            PlayerTime.removePlayer(player);
        }
    }
}

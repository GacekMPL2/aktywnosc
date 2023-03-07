package me.gacekmpl2.craftmpg.listener;

import me.gacekmpl2.craftmpg.GlobalVariables;
import me.gacekmpl2.craftmpg.playertime.PlayerTime;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission(GlobalVariables.getTime_count_perm()))
            PlayerTime.addPlayer(player);
    }
}


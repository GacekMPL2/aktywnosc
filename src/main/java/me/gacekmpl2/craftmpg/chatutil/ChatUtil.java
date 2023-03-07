package me.gacekmpl2.craftmpg.chatutil;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtil {
    public static void sendMessage(Player player, String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        player.sendMessage(message);
    }
}
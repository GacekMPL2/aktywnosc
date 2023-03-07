package me.gacekmpl2.craftmpg.commands;

import me.gacekmpl2.craftmpg.chatutil.ChatUtil;
import me.gacekmpl2.craftmpg.utils.Debug;
import me.gacekmpl2.craftmpg.utils.LogsUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class LogsCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Debug.log("Komenda tylko dla graczy");
            return true;
        }
        Player player = (Player)sender;
        if (!LogsUtils.isAllowed(player.getName())) {
            ChatUtil.sendMessage(player, "&cNie masz dostepu do tej komendy!");
            return true;
        }
        if (args.length == 1) {
            if (!args[0].equalsIgnoreCase("wyczysc")) {
                String nickname = args[0];
                try {
                    LogsUtils.showLogs(player, nickname);
                } catch (SQLException throwables) {
                    ChatUtil.sendMessage(player, "&cNie ma nick'u w bazie");
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("wyczysc")) {
                LogsUtils.clearLogs();
                ChatUtil.sendMessage(player, "&cUsunieto wszystkie logi");
                return true;
            }
        }
        ChatUtil.sendMessage(player, "&7/" + label + " <nick>/<wyczysc>");
        return true;
    }
}

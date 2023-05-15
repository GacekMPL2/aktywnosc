package me.gacekmpl2.craftmpg.utils;

import me.gacekmpl2.craftmpg.DatabaseManager;
import me.gacekmpl2.craftmpg.Main;
import me.gacekmpl2.craftmpg.chatutil.ChatUtil;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LogsUtils {
    private static final ArrayList<String> allowedNicknames = new ArrayList<>();

    private static final String serverName = Main.getInstance().getServer().getMotd();

    public static void loadSettings() {
        allowedNicknames.add("Pijok_");
        allowedNicknames.add("GacekMPL2");
        allowedNicknames.add("Mysiula");
        allowedNicknames.add("_mkko120");
        Debug.log("[Aktywnosc] Laduje ustawienia");
        Debug.log("Server: " + serverName);
    }

    public static boolean isAllowed(String nickname) {
        return allowedNicknames.contains(nickname);
    }

    public static void showLogs(Player player, String nickname) throws SQLException {
        DatabaseManager databaseManager = Main.getInstance().getDatabaseManager();
        String sql = "SELECT * FROM aktywnosc_logs WHERE nickname = ? ORDER BY id ASC";
        try (ResultSet results = databaseManager.query(sql, nickname)) {
            ChatUtil.sendMessage(player, "&7Wyniki dla &e&l" + nickname);
            ChatUtil.sendMessage(player, "&7==================");
            while (results.next()) {
                long time = results.getLong("time");
                String date = results.getString("date");
                String serverName = results.getString("server");
                int hours = (int) (time / 3600L);
                int minutes = (int) (time % 3600L / 60L);
                ChatUtil.sendMessage(player, "&7" + serverName + " | &e" + date + " &7| &7" + hours + " h " + minutes + " m");
            }
        }
        ChatUtil.sendMessage(player, "&7==================");
    }

    public static void clearLogs() {
        String sql = "TRUNCATE TABLE aktywnosc_logs;";
        DatabaseManager databaseManager = Main.getInstance().getDatabaseManager();
        databaseManager.update(sql);
    }

    public static String getServerName() {
        return serverName;
    }
}


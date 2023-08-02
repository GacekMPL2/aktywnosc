package me.gacekmpl2.craftmpg.utils;

import me.gacekmpl2.craftmpg.DatabaseManager;
import me.gacekmpl2.craftmpg.chatutil.ChatUtil;
import me.gacekmpl2.craftmpg.playertime.PlayerTime;
import org.bukkit.entity.Player;
import me.gacekmpl2.craftmpg.Main;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class TimeCountUtils {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");

    public static Long getPlayerTimeInDataBase(Player player) throws SQLException {
        long time;
        long nope = -1L;
        DatabaseManager databaseManager = Main.getInstance().getDatabaseManager();
        String sql = "SELECT * FROM aktywnosc_time_count WHERE nickname = ?;";
        try (ResultSet results = databaseManager.query(sql, player.getName())) {
            if (!results.next()) return nope;
            System.out.println("Success");
            time = results.getLong("time");
        }
        return time;
    }

    public static void savePlayer(Player player, Long seconds) throws SQLException {
        long seconds_in_base = getPlayerTimeInDataBase(player);
        long seconds_to_save = seconds_in_base + seconds;
        DatabaseManager databaseManager = Main.getInstance().getDatabaseManager();
        String sql;
        if (seconds_in_base == -1L) {
            databaseManager.update("INSERT INTO aktywnosc_time_count(nickname, time) VALUES (?, ?);", player.getName(), seconds_to_save);
        } else {
            Debug.log("Jest");
            databaseManager.update("UPDATE aktywnosc_time_count SET time = ? WHERE nickname = ?;", seconds_to_save, player.getName());
        }
        Date date = new Date(System.currentTimeMillis());
        String date_text = formatter.format(date);
        sql = "INSERT INTO aktywnosc_logs (nickname, time, date, server) VALUES (?, ?, ?, ?);";
        databaseManager.update(sql, player.getName(), seconds, date_text, LogsUtils.getServerName());
    }

    public static Long getSecondsOnServer(Player player) {
        return System.currentTimeMillis() / 1000L - PlayerTime.getPlayerTime(player) / 1000L;
    }

    public static void resetPlayerTime(String name) throws SQLException {
        String sql = "DELETE FROM aktywnosc_time_count WHERE nickname = ?;";
        DatabaseManager databaseManager = Main.getInstance().getDatabaseManager();
        databaseManager.update(sql, name);
    }

    public static void resetAllPlayers() throws SQLException {
        String sql = "TRUNCATE TABLE aktywnosc_time_count;";
        DatabaseManager databaseManager = Main.getInstance().getDatabaseManager();
        databaseManager.update(sql);
    }

    public static void showTop(Player player) throws SQLException {
        DatabaseManager databaseManager = Main.getInstance().getDatabaseManager();
        String sql = "SELECT * FROM aktywnosc_time_count ORDER BY `time` DESC LIMIT 10";
        try (ResultSet results = databaseManager.query(sql)) {
            ChatUtil.sendMessage(player, "&3&lTop 10 Aktywnosci");
            int i = 1;
            while (results.next()) {
                String nickname = results.getString("nickname");
                long time = results.getLong("time");
                int hours = (int) (time / 3600L);
                int minutes = (int) (time % 3600L / 60L);
                ChatUtil.sendMessage(player, "&3&l" + i + ". &b" + nickname + ": &7" + hours + " h " + minutes + " m");
                i++;
            }
        }
    }

    public static void showAllResults(Player player) throws SQLException {
        DatabaseManager databaseManager = Main.getInstance().getDatabaseManager();
        String sql = "SELECT * FROM aktywnosc_time_count";
        try (ResultSet results = databaseManager.query(sql)) {
            while (results.next()) {
                String nickname = results.getString("nickname");
                long time = results.getLong("time");
                int hours = (int) (time / 3600L);
                int minutes = (int) (time % 3600L / 60L);
                ChatUtil.sendMessage(player, "&e" + nickname + ": &7" + hours + " h " + minutes + " m");
            }
        }
    }

    public static void backupAllPlayers() throws SQLException {
        Date date = new Date(System.currentTimeMillis());
        String tableName = "aktywnosc_time_archive";
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "nickname VARCHAR(32) NOT NULL," +
                "time BIGINT NOT NULL," +
                "date DATE NOT NULL)";
        String sqlInsertData = "INSERT INTO " + tableName + " (nickname, time, date) " +
                "SELECT nickname, time, ? FROM aktywnosc_time_count " +
                "ON DUPLICATE KEY UPDATE time = VALUES(time)";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        DatabaseManager databaseManager = Main.getInstance().getDatabaseManager();
        databaseManager.update(sqlCreateTable);
        databaseManager.update(sqlInsertData, dateFormat.format(date));
    }

    public static void setPlayerTime(Player player, long seconds) throws SQLException {
        DatabaseManager databaseManager = Main.getInstance().getDatabaseManager();
        String sql = "UPDATE aktywnosc_time_count SET time = ? WHERE nickname = ?;";
        databaseManager.update(sql, seconds, player.getName());

        Date date = new Date(System.currentTimeMillis());
        String date_text = formatter.format(date);
        String sqlLogs = "INSERT INTO aktywnosc_logs (nickname, time, date, server) VALUES (?, ?, ?, ?);";
        databaseManager.update(sqlLogs, player.getName(), seconds, date_text, LogsUtils.getServerName());
    }
}

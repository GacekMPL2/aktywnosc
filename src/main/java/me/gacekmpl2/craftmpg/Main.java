package me.gacekmpl2.craftmpg;

import me.gacekmpl2.craftmpg.commands.LogsCommand;
import me.gacekmpl2.craftmpg.utils.LogsUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import me.gacekmpl2.craftmpg.commands.TimeCommand;
import me.gacekmpl2.craftmpg.listener.JoinListener;
import me.gacekmpl2.craftmpg.listener.QuitListener;

public class Main extends JavaPlugin {
    private static Main instance;

    private DatabaseManager databaseManager;

    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new QuitListener(), this);
        getServer().getName();
        getCommand("aktywnosc").setExecutor(new TimeCommand());
        getCommand("timelog").setExecutor(new LogsCommand());
        this.databaseManager = new DatabaseManager(
                "localhost",
                "3306",
                "luckperms",
                "luckperms",
                ""
        );
        prepareTables();
        LogsUtils.loadSettings();
    }

    private void prepareTables() {
        String query = "CREATE TABLE IF NOT EXISTS aktywnosc_time_count (" +
                "nickname VARCHAR(32) NOT NULL PRIMARY KEY," +
                "time BIGINT NOT NULL)";
        String query_2 = "CREATE TABLE IF NOT EXISTS aktywnosc_logs (" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "nickname VARCHAR(32) NOT NULL," +
                "time BIGINT NOT NULL," +
                "date VARCHAR(64) NOT NULL," +
                "server VARCHAR(32) NOT NULL)";
        String query_3 = "CREATE TABLE IF NOT EXISTS aktywnosc_time_archive (" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "nickname VARCHAR(32) NOT NULL," +
                "time BIGINT NOT NULL," +
                "date DATE NOT NULL)";
        this.databaseManager.update(query);
        this.databaseManager.update(query_2);
        this.databaseManager.update(query_3);
    }

    public static Main getInstance() {
        return instance;
    }

    public DatabaseManager getDatabaseManager() {
        return this.databaseManager;
    }
}

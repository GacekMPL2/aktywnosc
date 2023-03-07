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

    final String username = "root";

    final String password = "";

    final String host = "localhost";

    final String port = "3306";

    final String database = "aktywnosc";

    final String url = "jdbc:mysql://localhost:3306/aktywnosc";

    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents((Listener)new JoinListener(), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new QuitListener(), (Plugin)this);
        getServer().getName();
        getCommand("aktywnosc").setExecutor((CommandExecutor)new TimeCommand());
        getCommand("timelog").setExecutor((CommandExecutor)new LogsCommand());
        this.databaseManager = new DatabaseManager("localhost", "3306", "aktywnosc", "root", "");
        prepareTables();
        LogsUtils.loadSettings();
    }

    private void prepareTables() {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS time_count (");
        query.append("nickname VARCHAR(32) NOT NULL PRIMARY KEY,");
        query.append("time BIGINT NOT NULL)");
        StringBuilder query_2 = new StringBuilder();
        query_2.append("CREATE TABLE IF NOT EXISTS logs (");
        query_2.append("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,");
        query_2.append("nickname VARCHAR(32) NOT NULL,");
        query_2.append("time BIGINT NOT NULL,");
        query_2.append("date VARCHAR(64) NOT NULL,");
        query_2.append("server VARCHAR(32) NOT NULL)");
        this.databaseManager.update(query.toString());
        this.databaseManager.update(query_2.toString());
    }

    public static Main getInstance() {
        return instance;
    }

    public DatabaseManager getDatabaseManager() {
        return this.databaseManager;
    }
}

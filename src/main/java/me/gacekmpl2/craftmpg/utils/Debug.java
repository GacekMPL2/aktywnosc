package me.gacekmpl2.craftmpg.utils;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class Debug {
    private static final ConsoleCommandSender console = Bukkit.getConsoleSender();

    public static void log(String a) {
        console.sendMessage(a.replace("&", "§"));
    }

    public static void log(Object a) {
        console.sendMessage(String.valueOf(a).replace("&", "§"));
    }
}
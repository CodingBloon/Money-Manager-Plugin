package de.bloon.moneysystem;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MoneySystem extends JavaPlugin {

    public static String PREFIX = "§6Money§aManager §7 >> ";

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§6System wird gestartet...");
        Bukkit.getPluginCommand("eco").setExecutor(new Command());
        Bukkit.getPluginCommand("money").setExecutor(new MoneyCmd());
        MySQLManager.setStandartMySQL();
        MySQLManager.readMySQL();
        MySQLManager.ConnecttoDataBase();
        MySQLManager.createTable();
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§6System gestartet!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

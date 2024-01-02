package de.bloon.moneysystem;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MoneySystem extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§6System wird gestartet...");
        Bukkit.getPluginCommand("eco").setExecutor(new Command());
        Bukkit.getPluginCommand("money").setExecutor(new MoneyCmd());
        MySQLManager.setStandartMySQL();
        MySQLManager.readMySQL();
        MySQLManager.ConnecttoDataBase();
        MySQLManager.createTable();
        Bukkit.getConsoleSender().sendMessage("§6System gestartet!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

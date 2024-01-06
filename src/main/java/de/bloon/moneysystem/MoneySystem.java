package de.bloon.moneysystem;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import javax.activation.CommandMap;

public final class MoneySystem extends JavaPlugin {

    public static String PREFIX = "§6Money§aManager §7 >> ";
    public static SecondCurrency secondCurrency;

    @Override
    public void onEnable() {
        SecondCurrency.setDefault();
        secondCurrency = new SecondCurrency(SecondCurrency.getNameFromFile());
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§6Starting up...");
        Bukkit.getPluginCommand("eco").setExecutor(new Command());
        Bukkit.getPluginCommand("money").setExecutor(new MoneyCmd());
        Bukkit.getPluginCommand("setsql").setExecutor(new SetSQL());
        Bukkit.getPluginCommand("pay").setExecutor(new PayCommand());
        Bukkit.getPluginCommand("setname").setExecutor(new SetCurrencyName());
        MySQLManager.setStandartMySQL();
        MySQLManager.readMySQL();
        if(MySQLManager.useSQL()) {
            MySQLManager.ConnecttoDataBase();
            MySQLManager.createTable();
            Bukkit.getConsoleSender().sendMessage(PREFIX + "§aThis plugin is using a mysql database!\n§6If you want to change this please use '/setsql false'");
        } else {
            Bukkit.getConsoleSender().sendMessage(PREFIX + "§cNOTE: You are not using a mysql database!\nIf you want to use a mysql database please use '/setsql true'");
        }
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§aReady!");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§6Shutting down...");
        if(MySQLManager.useSQL()) {
            Bukkit.getConsoleSender().sendMessage(PREFIX + "§6Disconnecting from database...");
            if(MySQLManager.disconnect())
                Bukkit.getConsoleSender().sendMessage(PREFIX + "§aDisconnected from database!");
            else Bukkit.getConsoleSender().sendMessage(PREFIX + "§cAn error occurred while closing the connection! There was probably no connection to the database.");
        }

        Bukkit.getConsoleSender().sendMessage(PREFIX + "§6Unregistering events...");
        HandlerList.unregisterAll();
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§aEvents unregistered!");
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§aPlugin disabled!");
        // Plugin shutdown logic
    }
}

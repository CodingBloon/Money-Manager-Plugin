package de.bloon.moneysystem;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import javax.activation.CommandMap;
import java.util.HashMap;

public final class MoneySystem extends JavaPlugin {

    public static String PREFIX = "§6Money§aManager §7 >> ";
    public static PayOrderManager payOrderManager;
    public static SecondCurrency secondCurrency;
    public static HashMap<Player, Long> cooldown;
    public static MoneySystem plugin;

    @Override
    public void onEnable() {
        plugin = this;
        cooldown = new HashMap<>();
        payOrderManager = new PayOrderManager();
        SecondCurrency.setDefault();
        secondCurrency = new SecondCurrency(SecondCurrency.getNameFromFile());
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§6Starting up...");
        Bukkit.getPluginCommand("eco").setExecutor(new Command());
        Bukkit.getPluginCommand("money").setExecutor(new MoneyCmd());
        Bukkit.getPluginCommand("setsql").setExecutor(new SetSQL());
        Bukkit.getPluginCommand("pay").setExecutor(new PayCommand());
        Bukkit.getPluginCommand("setname").setExecutor(new SetCurrencyName());
        Bukkit.getPluginCommand("pluginrestart").setExecutor(new RestartCommand());
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

    public void restart(CommandSender sender) {
        sender.sendMessage(PREFIX + "§6Shutting down...");
        if(MySQLManager.useSQL()) {
            sender.sendMessage(PREFIX + "§6Disconnecting from database...");
            if(MySQLManager.disconnect())
                sender.sendMessage(PREFIX + "§aDisconnected from database!");
            else sender.sendMessage(PREFIX + "§cAn error occurred while closing the connection! There was probably no connection to the database.");
        }
        sender.sendMessage(PREFIX + "§6Starting up...");
        SecondCurrency.setDefault();
        secondCurrency = new SecondCurrency(SecondCurrency.getNameFromFile());
        MySQLManager.setStandartMySQL();
        MySQLManager.readMySQL();
        if(MySQLManager.useSQL()) {
            MySQLManager.ConnecttoDataBase();
            MySQLManager.createTable();
            sender.sendMessage(PREFIX + "§aThis plugin is using a mysql database!\n§6If you want to change this please use '/setsql false'");
        } else {
            sender.sendMessage(PREFIX + "§cNOTE: You are not using a mysql database!\nIf you want to use a mysql database please use '/setsql true'");
        }
        sender.sendMessage(PREFIX + "§aReady!");

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

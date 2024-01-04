package de.bloon.moneysystem;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.omg.CORBA.PERSIST_STORE;

public final class MoneySystem extends JavaPlugin {

    public static String PREFIX = "§6Money§aManager §7 >> ";

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§6Starting up...");
        Bukkit.getPluginCommand("eco").setExecutor(new Command());
        Bukkit.getPluginCommand("money").setExecutor(new MoneyCmd());
        Bukkit.getPluginCommand("setsql").setExecutor(new SetSQL());
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
        // Plugin shutdown logic
    }
}

package de.bloon.moneysystem;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MoneyManager {

    public MoneyManager() {
    }

    private static File getFile() {
        return new File("plugins\\MoneySystem\\", "players.yml");
    }

    public static void setMoney(UUID uuid, double amount) {
        if(MySQLManager.useSQL()) {
            if (exists(uuid)) {
                MySQLManager.update("UPDATE MONEY SET UUID='" + uuid.toString() + "', Geld='" + amount + "' WHERE UUID='" + uuid.toString() + "'");
            } else {
                MySQLManager.update("INSERT INTO MONEY (UUID, Geld) VALUES ('" + uuid.toString() + "', '" + amount + "')");
            }
        } else {
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(new File("plugins\\MoneySystem\\", "players.yml"));
            cfg.set("money." + uuid.toString(), amount);
            try {
                cfg.save(getFile());
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(MoneySystem.PREFIX + "§cSomething went wrong!");
            }
        }
    }

    public static void addMoney(UUID uuid, double amount) {
        double nbalance = getMoney(uuid) + amount;
        setMoney(uuid, nbalance);
    }

    public static void removeMoney(UUID uuid, double amount) {
        double nbalance = getMoney(uuid) - amount;
        setMoney(uuid, nbalance);
    }

    public static double getMoney(UUID uuid) {
        if(MySQLManager.useSQL()) {
            double money = 0.0;
            ResultSet rs = MySQLManager.getResult("SELECT * FROM MONEY WHERE UUID='" + uuid.toString() + "'");

            try {
                while(rs.next()) {
                    money = rs.getDouble("Geld");
                }

                return money;
            } catch (SQLException var5) {
                //Bukkit.getConsoleSender().sendMessage("§cBeim Abrufen der Daten kam es zu einem Fehler!");
                return 0.0;
            }
        } else {
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(getFile());
            return cfg.getDouble("money." + uuid.toString());
            //throw new NotImplementedException("File support is not implemented yet!");
        }
    }

    public static boolean exists(UUID uuid) {
        if(!MySQLManager.useSQL())
            return false;
        ResultSet rs = MySQLManager.getResult("SELECT * FROM MONEY WHERE UUID='" + uuid.toString() + "'");

        try {
            if (rs.next()) {
                return rs != null;
            }
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        return false;
    }

}

package de.bloon.moneysystem;

import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MoneyManager {

    public MoneyManager() {
    }

    public static void setMoney(UUID uuid, double amount) {
        if (exists(uuid)) {
            MySQLManager.update("UPDATE MONEY SET UUID='" + uuid.toString() + "', Geld='" + amount + "' WHERE UUID='" + uuid.toString() + "'");
        } else {
            MySQLManager.update("INSERT INTO MONEY (UUID, Geld) VALUES ('" + uuid.toString() + "', '" + amount + "')");
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
        double money = 0.0;
        ResultSet rs = MySQLManager.getResult("SELECT * FROM MONEY WHERE UUID='" + uuid.toString() + "'");

        try {
            while(rs.next()) {
                money = rs.getDouble("Geld");
            }

            return money;
        } catch (SQLException var5) {
            Bukkit.getConsoleSender().sendMessage("§cBeim Abrufen der Daten kam es zu einem Fehler!");
            return 0.0;
        }
    }

    public static boolean exists(UUID uuid) {
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

package de.bloon.moneysystem;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLManager {

    public static String username;
    public static String password;
    public static String database;
    public static String host;
    public static String port;
    public static Connection con;
    public static String currency;

    public MySQLManager() {
    }

    public static void ConnecttoDataBase() {
        if (!isconnect()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            } catch (SQLException var1) {
                Bukkit.getConsoleSender().sendMessage("�5MoneySystem �7>> �cBeim Herstellen einer Verbindung mit der MySQL Datenbank gab einen Fehler: " + var1.getErrorCode());
            }
        }
    }

    public static void disconnect() {
        if (isconnect()) {
            try {
                con.close();
                Bukkit.getConsoleSender().sendMessage("�5MoneySystem �7>> �aMySQL Verbindung geschlossen!");
            } catch (SQLException var1) {
                Bukkit.getConsoleSender().sendMessage("�5MoneyManager �7>> �cBeim Schlie�en der Verbindung kam es zu einem Fehler!");
            }
        } else {;
            Bukkit.getConsoleSender().sendMessage("�5MoneySystem �7>> �cDie Verbindung  zur MySQL Datenbank konnte nich beendet werden, da keine Verbing vorhanden war!");
        }

    }

    public static void setStandartMySQL() {
        FileConfiguration cfg = getMySQLgFileConfiguration();
        cfg.options().copyDefaults(true);
        cfg.addDefault("username", "root");
        cfg.addDefault("password", "");
        cfg.addDefault("database", "localhost");
        cfg.addDefault("host", "localhost");
        cfg.addDefault("port", "3306");
        cfg.addDefault("use.sql", true);

        try {
            cfg.save(getMySQLFile());
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public static void setUseMySQL(Boolean b) {
        FileConfiguration cfg = getMySQLgFileConfiguration();
        cfg.set("use.sql", b);

        try {
            cfg.save(getMySQLFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readMySQL() {
        FileConfiguration cfg = getMySQLgFileConfiguration();
        username = cfg.getString("username");
        password = cfg.getString("password");
        database = cfg.getString("database");
        host = cfg.getString("host");
        port = cfg.getString("port");
    }

    public static boolean useSQL() {
        FileConfiguration cfg = getMySQLgFileConfiguration();
        return cfg.getBoolean("use.sql");
    }

    public static void getDefaultcurrency() {
        FileConfiguration cfg = getMySQLgFileConfiguration();
        currency = cfg.getString("currency");
    }

    public static void setDefaultcurrency() {
        FileConfiguration cfg = getMySQLgFileConfiguration();
        cfg.options().copyDefaults(true);
        cfg.addDefault("currency", "$");
    }

    public static boolean isconnect() {
        return con != null;
    }

    public static File getMySQLFile() {
        return new File("plugins\\MoneySystem\\", "mysql.yml");
    }

    public static FileConfiguration getMySQLgFileConfiguration() {
        return YamlConfiguration.loadConfiguration(getMySQLFile());
    }

    public static Connection getconnection() {
        return con;
    }

    public static void createTable() {
        try {
            con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS MONEY (UUID varchar(100), Geld varchar(100))");
        } catch (SQLException var1) {
            var1.printStackTrace();
        }

    }

    public static void update(String qry) {
        if (isconnect()) {
            try {
                con.createStatement().executeUpdate(qry);
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        } else {
            //Bukkit.getConsoleSender().sendMessage("�5MoneySystem �7>> �cDie Methode 'update' konnte nicht ausgef�hrt werden, da keine Verbindung besteht.");
        }

    }

    public static ResultSet getResult(String qry) {
        if (isconnect()) {
            try {
                return con.createStatement().executeQuery(qry);
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        }

        return null;
    }

}

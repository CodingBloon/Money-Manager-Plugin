package de.bloon.moneysystem;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SecondCurrency {
    private final String name;
    public SecondCurrency(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBalance(UUID uuid, double amount) {
        if(MySQLManager.useSQL()) {
            if (exists(uuid)) {
                MySQLManager.update("UPDATE SecondCurrency SET UUID='" + uuid.toString() + "', Geld='" + amount + "' WHERE UUID='" + uuid.toString() + "'");
            } else {
                MySQLManager.update("INSERT INTO SecondCurrency (UUID, Geld) VALUES ('" + uuid.toString() + "', '" + amount + "')");
            }
        } else {
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(new File("plugins\\MoneySystem\\", "players.yml"));
            cfg.set("secondCurrency." + uuid.toString(), amount);
            try {
                cfg.save(getFile());
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(MoneySystem.PREFIX + "§cSomething went wrong!");
            }
        }
    }
    public void addBalance(UUID uuid, double amount) {
        double nbalance = getBalance(uuid) + amount;
    }
    public void removeBalance(UUID uuid, double amount) {
        double nbalance = getBalance(uuid) - amount;
    }

    public static double getBalance(UUID uuid) {
        if(MySQLManager.useSQL()) {
            double money = 0.0;
            ResultSet rs = MySQLManager.getResult("SELECT * FROM SecondCurrency WHERE UUID='" + uuid.toString() + "'");

            try {
                while(rs.next()) {
                    money = rs.getDouble("Geld");
                }

                return money;
            } catch (SQLException var5) {
                return 0.0;
            }
        } else {
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(getFile());
            return cfg.getDouble("secondCurrency." + uuid.toString());
        }
    }

    public static boolean exists(UUID uuid) {
        if(!MySQLManager.useSQL())
            return false;
        ResultSet rs = MySQLManager.getResult("SELECT * FROM SecondCurrency WHERE UUID='" + uuid.toString() + "'");

        try {
            if (rs.next()) {
                return rs != null;
            }
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        return false;
    }

    private static File getFile() {
        return new File("plugins\\MoneySystem\\", "players.yml");
    }

    //to use commands
    public boolean dispatch(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            OfflinePlayer op;
            double amount;
            if (args[1].equalsIgnoreCase("set")) {
                try {
                    amount = Double.parseDouble(args[2]);
                    op = Bukkit.getOfflinePlayer(args[0]);
                    MoneySystem.secondCurrency.setBalance(op.getUniqueId(), amount);
                    sender.sendMessage(MoneySystem.PREFIX + "§aBalance of " + args[0] + " set to §6" + amount + " " + getName());
                } catch (Exception var11) {
                    sender.sendMessage(MoneySystem.PREFIX + "§4" + args[2] + " is not a number!");
                }
            }

            if (args[1].equalsIgnoreCase("add")) {
                try {
                    amount = Double.parseDouble(args[2]);
                    op = Bukkit.getOfflinePlayer(args[0]);
                    MoneySystem.secondCurrency.addBalance(op.getUniqueId(), amount);
                    sender.sendMessage(MoneySystem.PREFIX + "Increased the balance of " + args[0] + " by §6" + args[2] + " " + getName());
                } catch (Exception var10) {
                    sender.sendMessage(MoneySystem.PREFIX + "§4" + args[2] + " is not a number!");
                }
            }

            if (args[1].equalsIgnoreCase("remove")) {
                try {
                    amount = Double.parseDouble(args[2]);
                    op = Bukkit.getOfflinePlayer(args[0]);
                    MoneySystem.secondCurrency.removeBalance(op.getUniqueId(), amount);
                    sender.sendMessage(MoneySystem.PREFIX + "§aDecreased the balance of " + args[0] + " by §6" + args[2] + " " + getName());
                } catch (Exception var9) {
                    sender.sendMessage(MoneySystem.PREFIX + "§4" + args[2] + " is not a number!");
                }
            }

            return false;
        }

        Player p = (Player) sender;
        if (!p.hasPermission("admin.money")) {
            p.sendMessage(MoneySystem.PREFIX + "§cIt is better that you can't do this!");
            return false;
        }

        double amount;
        OfflinePlayer op;
        if (args[1].equalsIgnoreCase("set")) {
            try {
                amount = Double.parseDouble(args[2]);
                op = Bukkit.getOfflinePlayer(args[0]);
                MoneySystem.secondCurrency.setBalance(op.getUniqueId(), amount);
                p.sendMessage(MoneySystem.PREFIX + "§aBalance of " + args[0] + " set to §6" + amount + " " + getName());
            } catch (Exception var14) {
                sender.sendMessage(MoneySystem.PREFIX + "§4" + args[2] + " is not a number!");
            }
        }

        if (args[1].equalsIgnoreCase("add")) {
            try {
                amount = Double.parseDouble(args[2]);
                op = Bukkit.getOfflinePlayer(args[0]);
                MoneySystem.secondCurrency.addBalance(op.getUniqueId(), amount);
                p.sendMessage(MoneySystem.PREFIX + "Increased the balance of " + args[0] + " by §6" + args[2] + " " + getName());
            } catch (Exception var13) {
                sender.sendMessage(MoneySystem.PREFIX + "§4" + args[2] + " is not a number!");
            }
        }

        if (args[1].equalsIgnoreCase("remove")) {
            try {
                amount = Double.parseDouble(args[2]);
                op = Bukkit.getOfflinePlayer(args[0]);
                MoneySystem.secondCurrency.removeBalance(op.getUniqueId(), amount);
                p.sendMessage(MoneySystem.PREFIX + "§aDecreased the balance of " + args[0] + " by §6" + args[2] + " " + getName());
            } catch (Exception var12) {
                sender.sendMessage(MoneySystem.PREFIX + "§4" + args[2] + " is not a number!");
            }
        }

        return false;
    }
}

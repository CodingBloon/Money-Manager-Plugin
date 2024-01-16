package de.bloon.moneysystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSQL implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            if(args.length != 1) {
                sender.sendMessage(MoneySystem.PREFIX + "§bPlease use /setsql <true/false>");
                return false;
            }

            String parse = args[0];
            try {
                if(!parse.equalsIgnoreCase("true") && !parse.equalsIgnoreCase("false")) {
                    sender.sendMessage(MoneySystem.PREFIX + "§4Please enter 'TRUE' or 'FALSE'");
                    return false;
                }
                Boolean result = Boolean.parseBoolean(parse);
                MySQLManager.setUseMySQL(result);
                if(result) {
                    sender.sendMessage(MoneySystem.PREFIX + "§bMoneyManager will now use a mysql database\n§6You have to restart the plugin to apply this change.");
                    MySQLManager.ConnecttoDataBase();
                    MySQLManager.createTable();
                } else {
                    sender.sendMessage(MoneySystem.PREFIX + "§bMoneyManager will no longer use a mysql database\n§6You have to restart the plugin to apply this change.");
                }
            } catch (Exception e) {
                sender.sendMessage(MoneySystem.PREFIX + "§4Please enter 'FALSE' OR 'TRUE'!");
            }

            return false;
        }

        Player p = (Player) sender;

        if(!p.hasPermission("admin.money.set.sql")) {
            p.sendMessage(MoneySystem.PREFIX + "§cSorry, this command is only for admins!");
            return false;
        }

        if(args.length != 1) {
            sender.sendMessage(MoneySystem.PREFIX + "§bPlease use /setsql <true/false>");
            return false;
        }

        String parse = args[0];
        try {
            if(!parse.equalsIgnoreCase("true") && !parse.equalsIgnoreCase("false")) {
                sender.sendMessage(MoneySystem.PREFIX + "§4Please enter 'TRUE' or 'FALSE'");
                return false;
            }
            Boolean result = Boolean.parseBoolean(parse);
            MySQLManager.setUseMySQL(result);
            if(result) {
                sender.sendMessage(MoneySystem.PREFIX + "§bMoneyManager will now use a mysql database.\n§6You have to restart the plugin to apply this change.");
                MySQLManager.ConnecttoDataBase();
                MySQLManager.createTable();
            } else {
                sender.sendMessage(MoneySystem.PREFIX + "§bMoneyManager will no longer use a mysql database\n§6You have to restart the plugin to apply this change.");
            }
        } catch (Exception e) {
            sender.sendMessage(MoneySystem.PREFIX + "§4Please enter 'FALSE' OR 'TRUE'!");
        }

        return false;
    }
}

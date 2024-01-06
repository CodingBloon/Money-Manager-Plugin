package de.bloon.moneysystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCurrencyName implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            if(args.length != 1) {
                sender.sendMessage("§bBitte nutze /setname <Name>");
            }

            SecondCurrency.setCurrencyName(args[0]);
            sender.sendMessage(MoneySystem.PREFIX + "§aCurrency name set to '"+args[0]+"'");
            return false;
        }

        Player p = (Player) sender;

        if(!p.hasPermission("admin.money.name")) {
            p.sendMessage(MoneySystem.PREFIX + "§cIt is not your task to name a currency!");
            return false;
        }

        if(args.length != 1) {
            sender.sendMessage("§bBitte nutze /setname <Name>");
            return false;
        }

        SecondCurrency.setCurrencyName(args[0]);
        sender.sendMessage(MoneySystem.PREFIX + "§aCurrency name set to '"+args[0]+"'");
        return false;
    }
}

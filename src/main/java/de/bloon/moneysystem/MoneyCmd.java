package de.bloon.moneysystem;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyCmd implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MoneySystem.PREFIX + "§4You have to be a player to execute this command!");
            return false;
        }

        if(args.length == 2) {
            Player p = (Player)sender;
            if (args.length == 0) {
                double money = MoneySystem.secondCurrency.getBalance(p.getUniqueId());
                //String msg = ;
                p.sendMessage(MoneySystem.PREFIX + "§aYour balance: §6" + money + " " + MoneySystem.secondCurrency.getName());
                return false;
            } else if (!p.hasPermission("admin.money.other")) {
                p.sendMessage(MoneySystem.PREFIX + "§aI think you should not know this!");
                return false;
            } else {
                OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                double money = MoneySystem.secondCurrency.getBalance(op.getUniqueId());
                p.sendMessage("§aBalance of " + args[0] + ": §6" + money + " " + MoneySystem.secondCurrency.getName());
                return false;
            }
        }

        if (args.length != 1 && args.length != 0) {
            sender.sendMessage(MoneySystem.PREFIX + "§aPlease use §6/money (<Player>)");
            return false;
        }
        Player p = (Player)sender;
        if (args.length == 0) {
            double money = MoneyManager.getMoney(p.getUniqueId());
            //String msg = ;
            p.sendMessage(MoneySystem.PREFIX + "§aYour balance: §6" + money + "$");
            return false;
        }

        if(args[0].equalsIgnoreCase("-s")) {
            double money = MoneySystem.secondCurrency.getBalance(p.getUniqueId());
            p.sendMessage("§aYour balance: §6" + money + " " + MoneySystem.secondCurrency.getName());
            return false;
        }

        if (!p.hasPermission("admin.money.other")) {
            p.sendMessage(MoneySystem.PREFIX + "§aI think you should not know this!");
            return false;
        }
        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
        double money = MoneyManager.getMoney(op.getUniqueId());
        p.sendMessage("§aBalance of " + args[0] + ": §6" + money + "$");
        return false;
    }

}

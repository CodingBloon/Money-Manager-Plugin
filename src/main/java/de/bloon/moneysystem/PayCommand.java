package de.bloon.moneysystem;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(MoneySystem.PREFIX + "§4You have to be a player!");
            return false;
        }

        Player p = (Player) sender;

        if(args.length != 2) {
            p.sendMessage(MoneySystem.PREFIX + "§6Please use /pay <Player> <Amount>");
            return false;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if(!target.isOnline()) {
            p.sendMessage(MoneySystem.PREFIX + "§4Player not found!");
            return false;
        }

        try {
            double amount = Double.parseDouble(args[1]);
            if(MoneyManager.getMoney(p.getUniqueId()) < amount) {
                p.sendMessage(MoneySystem.PREFIX + "§cYou don't have enough money!");
                return false;
            }
            MoneyManager.removeMoney(p.getUniqueId(), amount);
            MoneyManager.addMoney(target.getUniqueId(), amount);
            p.sendMessage(MoneySystem.PREFIX + "§aYou have payed §c" + args[0] + " §6" + args[1] + "$");
            Player t = (Player) target;
            t.sendMessage(MoneySystem.PREFIX + "§c" + p.getName() + " §apayed you §6" + args[1] + "$");
        } catch (Exception var10) {
            sender.sendMessage(MoneySystem.PREFIX + "§4" + args[1] + " is not a number!");
        }

        return false;
    }
}

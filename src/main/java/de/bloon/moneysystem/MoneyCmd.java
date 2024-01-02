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
            sender.sendMessage("§4Du kannst diesen Befehl nur als Spieler ausführen!");
            return false;
        }

        if(!sender.hasPermission("")) {
            sender.sendMessage("§cIch glaube, dass solltest du nicht tun!");
            return false;
        }

        if (args.length != 1 && args.length != 0) {
            sender.sendMessage("§aBitte nutze §6/money (<Spieler>)");
            return false;
        } else {
            Player p = (Player)sender;
            if (args.length == 0) {
                double money = MoneyManager.getMoney(p.getUniqueId());
                p.sendMessage("§aDein Kontostand beträgt: §6" + money + "$");
                return false;
            } else if (!p.hasPermission("admin.money.other")) {
                p.sendMessage("Ich glaube, dass solltest du nicht wissen!");
                return false;
            } else {
                OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                double money = MoneyManager.getMoney(op.getUniqueId());
                p.sendMessage("§aDer Kontostand von " + args[0] + " beträgt: §6" + money + "§");
                return false;
            }
        }
    }

}

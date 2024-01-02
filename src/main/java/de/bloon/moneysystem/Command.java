package de.bloon.moneysystem;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length != 3) {
                sender.sendMessage("§aBitte nutze:\n§6/eco <User> set <Betrag>\n/eco <User> add <Betrag>\n/eco <User> remove <Betrag>");
                return false;
            } else {
                OfflinePlayer op;
                double amount;
                if (args[1].equalsIgnoreCase("set")) {
                    try {
                        amount = Double.parseDouble(args[2]);
                        op = Bukkit.getOfflinePlayer(args[0]);
                        MoneyManager.setMoney(op.getUniqueId(), amount);
                        sender.sendMessage("Du hast den Kontostand von " + op.getName() + " auf " + args[2] + "$ gesetzt");
                    } catch (Exception var11) {
                        sender.sendMessage(args[2] + " ist keine Zahl!");
                    }
                }

                if (args[1].equalsIgnoreCase("add")) {
                    try {
                        amount = Double.parseDouble(args[2]);
                        op = Bukkit.getOfflinePlayer(args[0]);
                        MoneyManager.addMoney(op.getUniqueId(), amount);
                        sender.sendMessage("Du hast den Kontostand von " + op.getName() + " um " + args[2] + "$ erhöht");
                    } catch (Exception var10) {
                        sender.sendMessage(args[2] + " ist keine Zahl!");
                    }
                }

                if (args[1].equalsIgnoreCase("remove")) {
                    try {
                        amount = Double.parseDouble(args[2]);
                        op = Bukkit.getOfflinePlayer(args[0]);
                        MoneyManager.removeMoney(op.getUniqueId(), amount);
                        sender.sendMessage("Du hast den Kontostand von " + op.getName() + " um " + args[2] + "$ herabgesetzt");
                    } catch (Exception var9) {
                        sender.sendMessage(args[2] + " ist keine Zahl!");
                    }
                }

                return false;
            }
        } else {
            Player p = (Player)sender;
            if (!p.hasPermission("admin.money")) {
                p.sendMessage("§cEs ist besser, dass du dies nicht tun kannst");
                return false;
            } else if (args.length != 3) {
                p.sendMessage("§aBitte nutze:\n§6/eco <User> set <Betrag>\n/eco <User> add <Betrag>\n/eco <User> remove <Betrag>");
                return false;
            } else if (!p.hasPermission("admin.money")) {
                p.sendMessage("§cDas war wohl nichts!");
                return false;
            } else {
                double amount;
                OfflinePlayer op;
                if (args[1].equalsIgnoreCase("set")) {
                    try {
                        amount = Double.parseDouble(args[2]);
                        op = Bukkit.getOfflinePlayer(args[0]);
                        MoneyManager.setMoney(op.getUniqueId(), amount);
                        p.sendMessage("Du hast den Kontostand von " + op.getName() + " auf " + args[2] + "$ gesetzt");
                    } catch (Exception var14) {
                        p.sendMessage(args[2] + " ist keine Zahl!");
                    }
                }

                if (args[1].equalsIgnoreCase("add")) {
                    try {
                        amount = Double.parseDouble(args[2]);
                        op = Bukkit.getOfflinePlayer(args[0]);
                        MoneyManager.addMoney(op.getUniqueId(), amount);
                        p.sendMessage("Du hast den Kontostand von " + op.getName() + " um " + args[2] + "$ erhöht");
                    } catch (Exception var13) {
                        p.sendMessage(args[2] + " ist keine Zahl!");
                    }
                }

                if (args[1].equalsIgnoreCase("remove")) {
                    try {
                        amount = Double.parseDouble(args[2]);
                        op = Bukkit.getOfflinePlayer(args[0]);
                        MoneyManager.removeMoney(op.getUniqueId(), amount);
                        p.sendMessage("Du hast den Kontostand von " + op.getName() + " um " + args[2] + "$ herabgesetzt");
                    } catch (Exception var12) {
                        p.sendMessage(args[2] + " ist keine Zahl!");
                    }
                }

                return false;
            }
        }
    }

}

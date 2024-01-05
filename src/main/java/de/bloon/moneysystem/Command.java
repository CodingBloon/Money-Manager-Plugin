package de.bloon.moneysystem;

import javafx.scene.input.Mnemonic;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {

            if(args.length == 4) {
                return MoneySystem.secondCurrency.dispatch(sender, args);
            }

            if (args.length != 3) {
                sender.sendMessage(MoneySystem.PREFIX + "§aPlease use: §5\n/eco <User> set <Amount>\n/eco <User> add <Amount>\n/eco <User> remove <Amount>");
                return false;
            }

                OfflinePlayer op;
                double amount;
                if (args[1].equalsIgnoreCase("set")) {
                    try {
                        amount = Double.parseDouble(args[2]);
                        op = Bukkit.getOfflinePlayer(args[0]);
                        MoneyManager.setMoney(op.getUniqueId(), amount);
                        sender.sendMessage(MoneySystem.PREFIX + "§aBalance of " + args[0] + " set to §6" + amount + "$");
                    } catch (Exception var11) {
                        sender.sendMessage(MoneySystem.PREFIX + "§4" + args[2] + " is not a number!");
                    }
                }

                if (args[1].equalsIgnoreCase("add")) {
                    try {
                        amount = Double.parseDouble(args[2]);
                        op = Bukkit.getOfflinePlayer(args[0]);
                        MoneyManager.addMoney(op.getUniqueId(), amount);
                        sender.sendMessage(MoneySystem.PREFIX + "Increased the balance of " + args[0] + " by §6" + args[2] + "$");
                    } catch (Exception var10) {
                        sender.sendMessage(MoneySystem.PREFIX + "§4" + args[2] + " is not a number!");
                    }
                }

                if (args[1].equalsIgnoreCase("remove")) {
                    try {
                        amount = Double.parseDouble(args[2]);
                        op = Bukkit.getOfflinePlayer(args[0]);
                        MoneyManager.removeMoney(op.getUniqueId(), amount);
                        sender.sendMessage(MoneySystem.PREFIX + "§aDecreased the balance of " + args[0] + " by §6" + args[2] + "$");
                    } catch (Exception var9) {
                        sender.sendMessage(MoneySystem.PREFIX + "§4" + args[2] + " is not a number!");
                    }
                }

                return false;
        }

            Player p = (Player)sender;
            if (!p.hasPermission("admin.money")) {
                p.sendMessage(MoneySystem.PREFIX + "§cIt is better that you can't do this!");
                return false;
            }


            if(args.length == 4) {
                return MoneySystem.secondCurrency.dispatch(sender, args);
            }

            if (args.length != 3) {
                p.sendMessage(MoneySystem.PREFIX + "§aPlease use: §5\n/eco <User> set <Amount>\n/eco <User> add <Amount>\n/eco <User> remove <Amount>");
                return false;
            }
                double amount;
                OfflinePlayer op;
                if (args[1].equalsIgnoreCase("set")) {
                    try {
                        amount = Double.parseDouble(args[2]);
                        op = Bukkit.getOfflinePlayer(args[0]);
                        MoneyManager.setMoney(op.getUniqueId(), amount);
                        p.sendMessage(MoneySystem.PREFIX + "§aBalance of " + args[0] + " set to §6" + amount + "$");
                    } catch (Exception var14) {
                        sender.sendMessage(MoneySystem.PREFIX + "§4" + args[2] + " is not a number!");
                    }
                }

                if (args[1].equalsIgnoreCase("add")) {
                    try {
                        amount = Double.parseDouble(args[2]);
                        op = Bukkit.getOfflinePlayer(args[0]);
                        MoneyManager.addMoney(op.getUniqueId(), amount);
                        p.sendMessage(MoneySystem.PREFIX + "Increased the balance of " + args[0] + " by §6" + args[2] + "$");
                    } catch (Exception var13) {
                        sender.sendMessage(MoneySystem.PREFIX + "§4" + args[2] + " is not a number!");
                    }
                }

                if (args[1].equalsIgnoreCase("remove")) {
                    try {
                        amount = Double.parseDouble(args[2]);
                        op = Bukkit.getOfflinePlayer(args[0]);
                        MoneyManager.removeMoney(op.getUniqueId(), amount);
                        p.sendMessage(MoneySystem.PREFIX + "§aDecreased the balance of " + args[0] + " by §6" + args[2] + "$");
                    } catch (Exception var12) {
                        sender.sendMessage(MoneySystem.PREFIX + "§4" + args[2] + " is not a number!");
                    }
                }

                return false;
    }

}

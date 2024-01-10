package de.bloon.moneysystem;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class PayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(MoneySystem.PREFIX + "§4You have to be a player!");
            return false;
        }

        Player p = (Player) sender;

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("confirm")) {
                if(!MoneySystem.payOrderManager.hasPlayerOrders(p)) {
                    p.sendMessage(MoneySystem.PREFIX + "§cYou don't have open payment confirmations!");
                    return false;
                }

                MoneySystem.payOrderManager.confirmOrder(p);
                p.sendMessage(MoneySystem.PREFIX + "§aPayment confirmed!");
                return false;
            }

            if(args[0].equalsIgnoreCase("cancel")) {
                if(!MoneySystem.payOrderManager.hasPlayerOrders(p)) {
                    p.sendMessage(MoneySystem.PREFIX + "§cYou don't have open payment confirmations!");
                    return false;
                }

                MoneySystem.payOrderManager.cancelOrder(p);
                p.sendMessage(MoneySystem.PREFIX + "§aPayment canceled!");
                return false;
            }
        }

        if(args.length != 2) {
            p.sendMessage(MoneySystem.PREFIX + "§6Please use /pay <Player> <Amount>");
            return false;
        }

        if(args[0].equalsIgnoreCase("*")) {
            if(!p.hasPermission("admin.money.pay.all")) {
                p.sendMessage(MoneySystem.PREFIX + "§cSorry, you can't do this!");
            }

            Collection<? extends Player> playerlist = Bukkit.getOnlinePlayers();

            int players = playerlist.size() - 1;

            try {
                double amount = Double.parseDouble(args[1]);
                double required = amount * players;
                if(MoneyManager.getMoney(p.getUniqueId()) < required) {
                    p.sendMessage(MoneySystem.PREFIX + "§cYou don't have enough money!");
                    return false;
                }

                for (Player target : playerlist) {
                    if(!target.equals(p)) {
                        MoneyManager.removeMoney(p.getUniqueId(), amount);
                        MoneyManager.addMoney(target.getUniqueId(), amount);
                        p.sendMessage(MoneySystem.PREFIX + "§aYou have payed §c" + target.getName() + " §6" + args[1] + "$");
                        target.sendMessage(MoneySystem.PREFIX + "§c" + p.getName() + " §apayed you §6" + args[1] + "$");
                    }
                }

                p.sendMessage(MoneySystem.PREFIX + "§cYou payed " + players + " players " + args[1] + "$ (Total: "+required+")");

            } catch (Exception var10) {
                sender.sendMessage(MoneySystem.PREFIX + "§4" + args[1] + " is not a number!");
            }

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

            if(amount >= 250000D) {
                MoneySystem.payOrderManager.createOrder(p, Bukkit.getOfflinePlayer(target.getUniqueId()), amount);
                StringBuilder information = new StringBuilder();
                double a = amount / 1000;
                if(amount > 1000000)
                    a = amount / 1000000;
                information.append("§6-------------------- §cPayment Confirmation §6--------------------")
                        .append("§6Receiver: " + target.getName()).append("\n");

                if(amount > 1000000)
                    information.append("§6Amount: " + a + "M $").append("\n");
                else information.append("Amount: " + a + "K $").append("\n");

                information.append("§6-------------------- §cPayment Confirmation §6--------------------").append("\n")
                                .append("§6Use §a/pay confirm to §2confirm §apayment").append("\n")
                                .append("§6Use §c/pay cancel to §4cancel §cpayment").append("\n")
                                .append("§6-------------------- §cPayment Confirmation §6--------------------");

                p.sendMessage(information.toString());
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

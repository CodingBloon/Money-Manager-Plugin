package de.bloon.moneysystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RestartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            MoneySystem.plugin.restart(sender);
            return false;
        }

        if(!sender.hasPermission("admin.money.restart")) {
            sender.sendMessage(MoneySystem.PREFIX + "§cThat was a nice try but no!");
            return false;
        }

        MoneySystem.plugin.restart(sender);

        return false;
    }
}

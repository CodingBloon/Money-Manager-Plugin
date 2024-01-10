package de.bloon.moneysystem;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PayOrderManager {

    private final ArrayList<PayOrder> orders;

    public PayOrderManager() {
        this.orders = new ArrayList<>();
    }

    public void addOrder(PayOrder order) {
        this.orders.add(order);
    }

    public PayOrder getOrder(Player creator) {
        for (PayOrder order : orders) {
            if(order.getCreator().equals(creator))
                return order;
        }

        return null;
    }

    public boolean hasPlayerOrders(Player p) {
        for (PayOrder order : orders) {
            if(order.getCreator().equals(p))
                return true;
        }

        return false;
    }

    public void confirmOrder(Player creator) {
        PayOrder order = getOrder(creator);
        OfflinePlayer target = order.getTarget();
        MoneyManager.removeMoney(creator.getUniqueId(), order.getAmount());
        MoneyManager.addMoney(target.getUniqueId(), order.getAmount());
        creator.sendMessage(MoneySystem.PREFIX + "§aYou have payed §c" + target.getName() + " §6" + order.getAmount() + "$");
        if(target.isOnline()) {
            target.getPlayer().sendMessage(MoneySystem.PREFIX + "§c" + creator.getName() + " §apayed you §6" + order.getAmount() + "$");
        }
        orders.remove(order);
    }

    public void cancelOrder(Player creator) {
        PayOrder order = getOrder(creator);
        orders.remove(order);
    }

    public void createOrder(Player creator, OfflinePlayer target, double amount) {
        addOrder(new PayOrder(creator, target.getPlayer(), amount));
    }
}

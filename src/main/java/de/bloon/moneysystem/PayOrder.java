package de.bloon.moneysystem;

import org.bukkit.entity.Player;

public class PayOrder {

    private final Player creator;
    private final Player target;
    private final double amount;

    public PayOrder(Player creator, Player target, double amount) {
        this.creator = creator;
        this.target = target;
        this.amount = amount;
    }

    public Player getCreator() {
        return creator;
    }

    public Player getTarget() {
        return target;
    }

    public double getAmount() {
        return amount;
    }

}

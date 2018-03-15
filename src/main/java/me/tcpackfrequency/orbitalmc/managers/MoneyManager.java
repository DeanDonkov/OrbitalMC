package me.tcpackfrequency.orbitalmc.managers;

import me.tcpackfrequency.orbitalmc.OrbitalMC;

import java.util.UUID;

public class MoneyManager {

    private OrbitalMC pl;

    public MoneyManager(OrbitalMC pl){
        this.pl = pl;
    }

    public void addMoney(double amount, UUID u) {
        pl.getPm().getOrCreateProfile(u).setMoney(pl.getPm().getOrCreateProfile(u).getMoney() + amount);
    }

    public void removeMoney(double amount, UUID u) {
        pl.getPm().getOrCreateProfile(u).setMoney(pl.getPm().getOrCreateProfile(u).getMoney() - amount);
    }

}

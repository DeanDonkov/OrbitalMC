package me.tcpackfrequency.orbitalmc.profile;

import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Profile {

    private UUID u;

    public Profile(UUID u){
        this.u = u;
        //this.p = Bukkit.getPlayer(u);
    }

    private Map<UUID, PermissionAttachment> perms = new HashMap<>();


    private double money;
    private String nickname;
    private String nametag;

    private int xp;

    private int level;

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getMoney() {
        return this.money;
    }

    public String getNickname() {
        return nickname;
    }

    public String getNametag() {
        return nametag;
    }

    public void setMoney(double money) {
        this.money = money;
    }


    public Map<UUID, PermissionAttachment> getPerms() {
        return perms;
    }



}

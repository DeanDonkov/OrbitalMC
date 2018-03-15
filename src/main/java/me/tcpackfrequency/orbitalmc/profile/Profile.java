package me.tcpackfrequency.orbitalmc.profile;

import java.util.UUID;

public class Profile {

    public Profile(UUID u){
        this.u = u;
    }

    private UUID u;


    private double money;
    private String nickname;
    private String nametag;


    public double getMoney() {
        return this.money;
    }

    public String getNickname() {
        return nickname;
    }

    public String getNametag() {
        return nametag;
    }

    public void setMoney(double mony) {
        this.money = mony;
    }



}

package me.tcpackfrequency.orbitalmc.messages;

import me.tcpackfrequency.orbitalmc.OrbitalMC;
import org.bukkit.ChatColor;

public enum Messages {

    NO_PERMISSION("Messages.NO_PERMISSION"),
    CURRENT_LEVEL("Messages.CURRENT_LEVEL");


    private String message;
    private OrbitalMC pl;

    void foo(OrbitalMC pl){
        this.pl = pl;
    }

    Messages(String message) {
        this.foo(pl);
        this.message = this.colorize(this.pl.getConfig().getString(message));
    }


    public String getMessage(){
        return message;
    }


    private String colorize(String a){
        return ChatColor.translateAlternateColorCodes('&', a);
    }
}

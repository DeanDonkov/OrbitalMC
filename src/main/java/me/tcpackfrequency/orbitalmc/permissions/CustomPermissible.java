package me.tcpackfrequency.orbitalmc.permissions;

import me.tcpackfrequency.orbitalmc.OrbitalMC;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.ServerOperator;

public class CustomPermissible extends PermissibleBase {


    private OrbitalMC pl;

    public CustomPermissible(ServerOperator op, OrbitalMC pl) {
        super(op);
        this.pl = pl;
    }


    public boolean hasPermission(String s){
        // implement later.
        return super.hasPermission(s);
    }

    public boolean hasPermission(Permission p){
        // implement later.
        return super.hasPermission(p);
    }
}

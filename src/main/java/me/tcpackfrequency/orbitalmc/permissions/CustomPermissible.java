package me.tcpackfrequency.orbitalmc.permissions;

import me.tcpackfrequency.orbitalmc.OrbitalMC;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.ServerOperator;

import java.util.List;

public class CustomPermissible extends PermissibleBase {


    private OrbitalMC pl;

    private ServerOperator op;

    public CustomPermissible(ServerOperator op, OrbitalMC pl) {
        super(op);
        this.op = op;
        this.pl = pl;
    }


    public boolean hasPermission(String s){
        if(!(op instanceof ConsoleCommandSender)) {
            Player p = (Player) op;
            List<PermissionAttachment> perms = pl.getPermissions().listPerms(p);
            // do later.
        }
        // ServerOperator is the player!
        return super.hasPermission(s);
    }

    public boolean hasPermission(Permission p){
        // implement later.
        // ServerOperator is the player!
        return super.hasPermission(p);
    }
}

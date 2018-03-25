package me.tcpackfrequency.orbitalmc.permissions;

import me.tcpackfrequency.orbitalmc.OrbitalMC;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class Permissions {

    private OrbitalMC pl;

    public Permissions(OrbitalMC pl){
        this.pl = pl;
    }

    public Permissions Attach(OrbitalMC pl, Player p) {
        PermissionAttachment attach = p.addAttachment(pl);
        pl.getPm().getOrCreateProfile(p.getUniqueId()).getPerms().put(p.getUniqueId(), attach);
        return this;
    }

    public Permissions addPermission(String permission, Player p){
        PermissionAttachment perms = pl.getPm().getOrCreateProfile(p.getUniqueId()).getPerms().get(p.getUniqueId());
        perms.setPermission(permission, true);
        return this;
    }

    public Permissions removePermission(String permission, Player p){
        PermissionAttachment perms = pl.getPm().getOrCreateProfile(p.getUniqueId()).getPerms().get(p.getUniqueId());
        perms.unsetPermission(permission);
        return this;
    }

    public List<PermissionAttachment> listPerms(Player p){
        return this.pl.getPm().getOrCreateProfile(p.getUniqueId()).getPerms().entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public String[] getPerms(UUID u) {
        String Perms = pl.getDb().getCurrentDatabaseHandler().getPermissions(u);
        return Perms.split("\\|");
    }


}

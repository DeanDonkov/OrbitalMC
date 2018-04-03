package me.tcpackfrequency.orbitalmc.permissions;

import me.tcpackfrequency.orbitalmc.OrbitalMC;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
        pl.getDb().getCurrentDatabaseHandler().addPermission(permission, p.getUniqueId());
        PermissionAttachment perms = pl.getPm().getOrCreateProfile(p.getUniqueId()).getPerms().get(p.getUniqueId());
        perms.setPermission(permission, true);
        return this;
    }

    public Permissions addPermissions(Player p, String... permissions){
        HashSet<String> Permissions = new HashSet<>(Arrays.asList(permissions));
        PermissionAttachment perms = pl.getPm().getOrCreateProfile(p.getUniqueId()).getPerms().get(p.getUniqueId());
        Permissions.forEach(s -> perms.setPermission(s, true));
        return this;
    }

    public Permissions removePermission(String permission, Player p){
        PermissionAttachment perms = pl.getPm().getOrCreateProfile(p.getUniqueId()).getPerms().get(p.getUniqueId());
        perms.unsetPermission(permission);
        return this;
    }

    public Permissions removePermissions(Player p, String... permissions){
        HashSet<String> Permissions = new HashSet<>(Arrays.asList(permissions));
        PermissionAttachment perms = pl.getPm().getOrCreateProfile(p.getUniqueId()).getPerms().get(p.getUniqueId());
        Permissions.forEach(s -> perms.setPermission(s, false));
        return this;
    }

    public List<PermissionAttachment> listPerms(Player p){
        return this.pl.getPm().getOrCreateProfile(p.getUniqueId()).getPerms().entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
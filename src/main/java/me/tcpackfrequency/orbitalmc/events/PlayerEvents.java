package me.tcpackfrequency.orbitalmc.events;

import me.tcpackfrequency.orbitalmc.OrbitalMC;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEvents implements Listener {

    private OrbitalMC pl;

    public PlayerEvents(OrbitalMC pl) {
        this.pl = pl;
    }

    @EventHandler
    public void Leave(PlayerQuitEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(pl, () -> this.pl.getDb().getCurrentDatabaseHandler().saveStats(e.getPlayer().getUniqueId()));
        this.pl.getPm().RemoveProfile(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void Join(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(pl, () -> pl.getPm().getOrCreateProfile(e.getPlayer().getUniqueId()).setId(pl.getDb().getCurrentDatabaseHandler().getUserId(e.getPlayer().getUniqueId())));
        pl.getPermissions().Attach(pl, e.getPlayer()).addPermission("yooo", e.getPlayer());
        pl.getPermissions().Attach(pl, e.getPlayer()).addPermission("hi", e.getPlayer());
        pl.getPermissions().Attach(pl, e.getPlayer()).addPermissions(e.getPlayer(), "here you keep on adding", "like this.", "yes");

    }
}

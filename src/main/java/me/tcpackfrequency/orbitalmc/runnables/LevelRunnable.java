package me.tcpackfrequency.orbitalmc.runnables;

import me.tcpackfrequency.orbitalmc.OrbitalMC;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class LevelRunnable extends BukkitRunnable {

    private OrbitalMC pl;

    public LevelRunnable(OrbitalMC pl){
        this.pl = pl;
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> pl.getPm().getOrCreateProfile(player.getUniqueId()).setXp(pl.getPm().getOrCreateProfile(player.getUniqueId()).getXp() + 20));
    }
}

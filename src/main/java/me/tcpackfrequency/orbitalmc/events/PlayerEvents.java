package me.tcpackfrequency.orbitalmc.events;

import me.tcpackfrequency.orbitalmc.OrbitalMC;
import me.tcpackfrequency.orbitalmc.permissions.CustomPermissible;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftHumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.ServerOperator;

import java.lang.reflect.Field;

public class PlayerEvents implements Listener {

    private OrbitalMC pl;

    public PlayerEvents(OrbitalMC pl) {
        this.pl = pl;
    }

    @EventHandler
    public void Leave(PlayerQuitEvent e){
        this.pl.getDb().getCurrentDatabaseHandler().saveStats(e.getPlayer().getUniqueId());
        this.pl.getPm().RemoveProfile(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void Join(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        CraftHumanEntity craftHumanEntity = (CraftHumanEntity) p;
        ServerOperator so = new ServerOperator() {
            @Override
            public boolean isOp() {
                return p.isOp();
            }

            @Override
            public void setOp(boolean value) {
                p.setOp(value);
            }
        };
        try {
            Class c = Class.forName("org.bukkit.craftbukkit.v1_8_R1.entity.CraftHumanEntity");

            Field field = c.getDeclaredField("perm");
            field.setAccessible(true);
            FieldUtils.removeFinalModifier(field, true);
            field.set(craftHumanEntity, new CustomPermissible(so, pl));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

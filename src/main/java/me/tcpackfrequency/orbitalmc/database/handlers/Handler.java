package me.tcpackfrequency.orbitalmc.database.handlers;

import org.bukkit.configuration.ConfigurationSection;

import java.util.UUID;

public interface Handler {

    void init();
    void connect(ConfigurationSection cs);
    void saveStats(UUID u);
    void stopDB();
    void setPermisions(String[] permission, UUID u);
    String getPermissions(UUID u);
}

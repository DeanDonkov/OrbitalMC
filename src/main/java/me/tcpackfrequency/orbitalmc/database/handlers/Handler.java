package me.tcpackfrequency.orbitalmc.database.handlers;

import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;
import java.util.UUID;

public interface Handler {

    void init();
    void connect(ConfigurationSection cs);
    void saveStats(UUID u);
    void stopDB();
    int getUserId(UUID u);
    HashSet<String> getPermissions(UUID u);
    void addPermission(String permission, UUID u);
    void addPermission(HashSet<String> permissions, UUID u);
}

package me.tcpackfrequency.orbitalmc.database.handlers;

import org.bukkit.configuration.ConfigurationSection;

public interface Handler {

    void init(ConfigurationSection cs);
    void connect();
}

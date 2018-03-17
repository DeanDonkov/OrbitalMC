package me.tcpackfrequency.orbitalmc.database.handlers;

import org.bukkit.configuration.ConfigurationSection;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.UUID;

public class RedisHandler implements Handler {

    RedissonClient redisson;

    @Override
    public void init() {

    }

    @Override
    public void connect(ConfigurationSection cs) {
        // Will update it in a bit.
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
               //.setPassword(cs.getString("Password"))
                .setConnectionPoolSize(50);
         redisson = Redisson.create(config);

    }

    @Override
    public void saveStats(UUID u) {

    }

    @Override
    public void stopDB() {

    }
}

package me.tcpackfrequency.orbitalmc.database.type;

import me.tcpackfrequency.orbitalmc.database.handlers.Handler;
import me.tcpackfrequency.orbitalmc.database.handlers.RedisHandler;

public class Redis implements Type {

    @Override
    public Handler getHandler() {
        return new RedisHandler();
    }

    @Override
    public String getName() {
        return "Redis";
    }
}

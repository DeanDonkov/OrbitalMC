package me.tcpackfrequency.orbitalmc.database.type;

import me.tcpackfrequency.orbitalmc.database.handlers.Handler;
import me.tcpackfrequency.orbitalmc.database.handlers.MySQLHandler;

public class MySQL implements Type {

    @Override
    public Handler getHandler() {
        return new MySQLHandler();
    }

    @Override
    public String getName() {
        return "MySQL";
    }
}

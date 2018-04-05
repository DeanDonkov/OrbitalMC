package me.tcpackfrequency.orbitalmc.database.type;

import me.tcpackfrequency.orbitalmc.database.handlers.Handler;

public interface Type {
    Handler getHandler();
    String getName();


}

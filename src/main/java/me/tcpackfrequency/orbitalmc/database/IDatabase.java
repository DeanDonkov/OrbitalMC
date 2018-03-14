package me.tcpackfrequency.orbitalmc.database;

import me.tcpackfrequency.orbitalmc.database.handlers.Handler;
import me.tcpackfrequency.orbitalmc.database.type.Type;

public interface IDatabase {

    void setCurrentDatabase(String db);
    Type getCurrentDatabase();

    void setCurrentDatabaseHandler();
    Handler getCurrentDatabaseHandler();

    Type getDatabase(String db);
    Handler getDatabaseHandler(String dbHandler);

}

package me.tcpackfrequency.orbitalmc.database;

import me.tcpackfrequency.orbitalmc.database.handlers.Handler;
import me.tcpackfrequency.orbitalmc.database.handlers.RedisHandler;
import me.tcpackfrequency.orbitalmc.database.type.Redis;
import me.tcpackfrequency.orbitalmc.database.type.Type;

public class Database implements IDatabase {

    private Type currentDatabase;
    private Handler currentHandler;

    @Override
    public void setCurrentDatabase(String db) {
        if(db.equalsIgnoreCase("redis")) this.currentDatabase = this.getDatabase("redis");
    }

    @Override
    public Type getCurrentDatabase() {
        if (this.currentDatabase == null) {
            throw new NullPointerException("The current database is null, set it first before accessing it!");
        }
        return this.currentDatabase;
    }

    @Override
    public void setCurrentDatabaseHandler() {
        if(this.getCurrentDatabase().getName().equalsIgnoreCase("Redis")) {
            this.currentHandler = this.getDatabaseHandler("Redis");
        }
    }

    @Override
        public Type getDatabase (String db){
            if (db.equalsIgnoreCase("Redis")) return new Redis();
            return null;
        }

    @Override
    public Handler getCurrentDatabaseHandler() {
        if(this.currentHandler == null) {
            throw new NullPointerException("The current database handler is null, set it first before accessing it!");
        }
        return this.currentHandler;
    }

    @Override
    public Handler getDatabaseHandler(String dbHandler) {
        if(dbHandler.equalsIgnoreCase("Redis")) {
            return new RedisHandler();
        }
        return null;
    }
}


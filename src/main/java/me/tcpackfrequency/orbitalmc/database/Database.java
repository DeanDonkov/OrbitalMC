package me.tcpackfrequency.orbitalmc.database;

import me.tcpackfrequency.orbitalmc.OrbitalMC;
import me.tcpackfrequency.orbitalmc.database.handlers.Handler;
import me.tcpackfrequency.orbitalmc.database.handlers.MySQLHandler;
import me.tcpackfrequency.orbitalmc.database.type.MySQL;
import me.tcpackfrequency.orbitalmc.database.type.Type;

public class Database implements IDatabase {

    private Type currentDatabase;
    private Handler currentHandler;

    private OrbitalMC pl;

    public Database(OrbitalMC pl){
        this.pl = pl;
    }

    @Override
    public void setCurrentDatabase(String db) {
        if(db.equalsIgnoreCase("MySQL")) this.currentDatabase = this.getDatabase("MySQL");
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
        if(this.getCurrentDatabase().getName().equalsIgnoreCase("MySQL")) {
            this.currentHandler = this.getDatabaseHandler("MySQL");
        }

    }

    @Override
        public Type getDatabase (String db){
            if (db.equalsIgnoreCase("MySQL")) return new MySQL(pl);
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
        if(dbHandler.equalsIgnoreCase("MySQL")) {
            return new MySQLHandler(pl);
        }
        return null;
    }
}


package me.tcpackfrequency.orbitalmc.database.type;

import me.tcpackfrequency.orbitalmc.OrbitalMC;
import me.tcpackfrequency.orbitalmc.database.handlers.Handler;
import me.tcpackfrequency.orbitalmc.database.handlers.MySQLHandler;

public class MySQL implements Type {

    private OrbitalMC pl;

    public MySQL(OrbitalMC pl){
        this.pl = pl;
    }

    @Override
    public Handler getHandler() {
        return new MySQLHandler(pl);
    }

    @Override
    public String getName() {
        return "MySQL";
    }
}

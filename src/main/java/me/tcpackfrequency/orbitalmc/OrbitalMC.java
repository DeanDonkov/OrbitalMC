package me.tcpackfrequency.orbitalmc;

import me.tcpackfrequency.orbitalmc.database.Database;
import me.tcpackfrequency.orbitalmc.managers.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class OrbitalMC extends JavaPlugin {

    private Database db;
    private FileManager fm;

    @Override
    public void onEnable() {
        this.setupManagers();
        this.setupDatabase();
    }

    @Override
    public void onDisable(){
        db.getCurrentDatabaseHandler().stopDB();
    }

    private void setupDatabase(){
        this.db = new Database();
        db.setCurrentDatabase("MySQL");
        db.setCurrentDatabaseHandler();
        db.getCurrentDatabaseHandler().connect(this.getConfig().getConfigurationSection("MySQL"));
        db.getCurrentDatabaseHandler().init();
    }

    private void setupManagers(){
        this.fm = new FileManager(this);
        this.fm.loadFile("config.yml");
    }
}

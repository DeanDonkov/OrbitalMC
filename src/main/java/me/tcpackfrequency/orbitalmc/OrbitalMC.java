package me.tcpackfrequency.orbitalmc;

import me.tcpackfrequency.orbitalmc.commands.BalanceCommand;
import me.tcpackfrequency.orbitalmc.database.Database;
import me.tcpackfrequency.orbitalmc.database.cache.Redis;
import me.tcpackfrequency.orbitalmc.events.PlayerEvents;
import me.tcpackfrequency.orbitalmc.managers.FileManager;
import me.tcpackfrequency.orbitalmc.managers.MoneyManager;
import me.tcpackfrequency.orbitalmc.managers.ProfileManager;
import me.tcpackfrequency.orbitalmc.runnables.LevelRunnable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class OrbitalMC extends JavaPlugin {

    private Database db;
    private FileManager fm;

    private LevelRunnable lr;

    private MoneyManager mm;

    private ProfileManager pm;

    private Redis redis;

    @Override
    public void onEnable() {
        this.setupManagers();
        this.setupDatabase();
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerEvents(this), this);
        this.setupCommands();
        this.setupRunnables();
        this.setupRedis();

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
        this.mm = new MoneyManager(this);
        this.pm = new ProfileManager();
    }

    private void setupCommands(){
        getCommand("Balance").setExecutor(new BalanceCommand(this));
    }

    private void setupRedis(){
        redis = new Redis();
        redis.connect(this.getConfig().getString("Redis.Host"), this.getConfig().getInt("Redis.Port"), this.getConfig().getString("Redis.Password"));
    }

    private void setupRunnables(){
        this.lr = new LevelRunnable(this);
        lr.runTaskTimer(this, 0, 12000);
    }

    public Database getDb() {
        return db;
    }

    public MoneyManager getMm() {
        return mm;
    }

    public ProfileManager getPm() {
        return pm;
    }


}

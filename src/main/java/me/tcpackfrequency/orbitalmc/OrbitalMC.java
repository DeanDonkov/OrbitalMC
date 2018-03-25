package me.tcpackfrequency.orbitalmc;

import me.tcpackfrequency.orbitalmc.commands.BalanceCommand;
import me.tcpackfrequency.orbitalmc.commands.LevelCommand;
import me.tcpackfrequency.orbitalmc.commands.PermissionsCommand;
import me.tcpackfrequency.orbitalmc.database.Database;
import me.tcpackfrequency.orbitalmc.events.PlayerEvents;
import me.tcpackfrequency.orbitalmc.managers.FileManager;
import me.tcpackfrequency.orbitalmc.managers.MoneyManager;
import me.tcpackfrequency.orbitalmc.managers.ProfileManager;
import me.tcpackfrequency.orbitalmc.permissions.Permissions;
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

    private Permissions permissions;

    @Override
    public void onEnable() {
        this.setupManagers();
        this.setupDatabase();
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerEvents(this), this);
        this.setupCommands();
        this.setupRunnables();

        this.permissions = new Permissions(this);

    }

    @Override
    public void onDisable(){
        db.getCurrentDatabaseHandler().stopDB();
    }

    private void setupDatabase(){
        this.db = new Database(this);
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
        getCommand("Level").setExecutor(new LevelCommand(this));
        getCommand("Permissions").setExecutor(new PermissionsCommand(this));
    }


    private void setupRunnables(){
        this.lr = new LevelRunnable(this);
        lr.runTaskTimer(this, 0, 12000);
    }

    public Permissions getPermissions() {
        return permissions;
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

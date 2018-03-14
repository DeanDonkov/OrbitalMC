package me.tcpackfrequency.orbitalmc.managers;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private JavaPlugin m;

    public FileManager(JavaPlugin m) {
        this.m = m;
    }

    private void createFile(String fileName) {
        File file = new File(m.getDataFolder(), fileName);
        if (!file.exists()) {
            m.saveResource(fileName, false);
        }
    }

    public void saveFile(FileConfiguration fileConfig, String fileName) {
        File file = new File(m.getDataFolder(), fileName);
        try {
            fileConfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration loadFile(String fileName) {
        createFile(fileName);
        File file = new File(m.getDataFolder(), fileName);
        FileConfiguration fileConfig = new YamlConfiguration();
        try {
            fileConfig.load(file);
            return fileConfig;
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

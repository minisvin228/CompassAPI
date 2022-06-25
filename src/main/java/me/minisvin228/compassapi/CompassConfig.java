package me.minisvin228.compassapi;

import org.bukkit.configuration.file.FileConfiguration;

public class CompassConfig {

    private CompassAPI plugin;
    private FileConfiguration mainConfig;

    public CompassConfig(CompassAPI plugin) {
        this.plugin = plugin;
        this.mainConfig = plugin.getConfig();
        initConfig();
    }

    private void initConfig() {
        mainConfig.addDefault("compass.tick", 20L);

        mainConfig.options().copyDefaults(true);
        plugin.saveConfig();
    }

    public long getCompassTick() {
        return mainConfig.getLong("compass.tick");
    }

    public CompassAPI getPlugin() {
        return plugin;
    }

    public FileConfiguration getMainConfig() {
        return mainConfig;
    }

}

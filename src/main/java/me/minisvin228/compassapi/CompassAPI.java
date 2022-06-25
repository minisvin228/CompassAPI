package me.minisvin228.compassapi;

import me.minisvin228.compassapi.CompassThread;
import org.bukkit.plugin.java.JavaPlugin;

public final class CompassAPI extends JavaPlugin {

    private CompassThread compassThread;
    private CompassConfig compassConfig;

    @Override
    public void onEnable() {
        compassConfig = new CompassConfig(this);

        compassThread = new CompassThread(this);

        CompassCommand compassCommand = new CompassCommand(this);
        getCommand("compass").setExecutor(compassCommand);
        getCommand("compass").setTabCompleter(compassCommand);
    }

    @Override
    public void onDisable() {

    }

    public CompassThread getCompassThread() {
        return compassThread;
    }

    public CompassConfig getCompassConfig() {
        return compassConfig;
    }
}

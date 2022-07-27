package me.minisvin228.compassapi;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class CompassAPI extends JavaPlugin {

    private CompassConfig compassConfig;
    private CompassStorage compassStorage;
    private CompassThread compassThread;

    @Override
    public void onEnable() {
        compassConfig = new CompassConfig(this);

        compassStorage = new CompassStorage(this);

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

    public CompassStorage getCompassStorage() {
        return compassStorage;
    }

    public static NamespacedKey getCompassTargetKey() {
        return new NamespacedKey(
                CompassAPI.getPlugin(CompassAPI.class), "target"
        );
    }
}

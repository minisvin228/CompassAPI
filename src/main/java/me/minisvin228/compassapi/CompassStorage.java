package me.minisvin228.compassapi;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CompassStorage implements Listener {

    private final CompassAPI plugin;
    private ConcurrentHashMap<UUID, ConcurrentHashMap<UUID, Location>> storage;

    public CompassStorage(CompassAPI plugin) {
        this.plugin = plugin;
        storage = new ConcurrentHashMap<>();
    }

    public boolean registerPlayer(Player player) {
        return storage.putIfAbsent(player.getUniqueId(), new ConcurrentHashMap<>()) == null;
    }

    public boolean registerPlayerLocation(Player player) {
        UUID playerUUID = player.getUniqueId();

        World world = player.getWorld();
        UUID worldUUID = world.getUID();

        Location location = player.getLocation();

        ConcurrentHashMap<UUID, Location> locationStorage = storage.get(playerUUID);
        if (locationStorage == null) return false;

        locationStorage.put(worldUUID, location);
        return true;
    }

    public Location getLastPlayerLocation(Player player, World world) {
        UUID playerUUID = player.getUniqueId();

        UUID worldUUID = world.getUID();

        ConcurrentHashMap<UUID, Location> locationStorage = storage.get(playerUUID);
        if (locationStorage == null) return null;

        return locationStorage.get(worldUUID);
    }


    public ConcurrentHashMap<UUID, ConcurrentHashMap<UUID, Location>> getStorage() {
        return storage;
    }
}

package me.minisvin228.compassapi;

import me.minisvin228.compassapi.CompassAPI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Objects;

public class CompassThread extends BukkitRunnable {

    private final CompassAPI plugin;

    public CompassThread (CompassAPI plugin) {
        this.plugin = plugin;
        CompassConfig compassConfig = plugin.getCompassConfig();
        runTaskTimer(plugin, 0L, compassConfig.getCompassTick());
    }

    @Override
    public void run() {
        CompassStorage storage = plugin.getCompassStorage();
        Bukkit.getOnlinePlayers().forEach(target -> {
            storage.registerPlayer(target);
            storage.registerPlayerLocation(target);
        });

        Bukkit.getOnlinePlayers().forEach(this::updatePlayerCompasses);
    }

    private void updatePlayerCompasses(Player player) {
        World world = player.getWorld();
        Arrays.stream(player.getInventory().getContents())
                .filter(Objects::nonNull)
                .filter(item -> item.getType() == Material.COMPASS)
                .filter(compass -> {
                    CompassMeta meta = (CompassMeta) compass.getItemMeta();
                    PersistentDataContainer container = meta.getPersistentDataContainer();
                    return container.has(
                            CompassAPI.getCompassTargetKey(), PersistentDataType.STRING
                    );
                }).forEach(compass -> updateCompass(compass, world));
    }

    private void updateCompass(ItemStack compass, World world) {
        CompassMeta meta = (CompassMeta) compass.getItemMeta();

        PersistentDataContainer container = meta.getPersistentDataContainer();
        String targetName = container.get(
                CompassAPI.getCompassTargetKey(), PersistentDataType.STRING
        );

        Player target = Bukkit.getPlayer(targetName);
        if (target == null) return;

        CompassStorage storage = plugin.getCompassStorage();
        Location location = storage.getLastPlayerLocation(target, world);

        meta.setLodestone(location);
    }

}

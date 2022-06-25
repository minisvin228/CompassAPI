package me.minisvin228.compassapi;

import me.minisvin228.compassapi.CompassAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class CompassThread extends BukkitRunnable {

    private CompassAPI plugin;

    public CompassThread (CompassAPI plugin) {
        this.plugin = plugin;
        CompassConfig compassConfig = plugin.getCompassConfig();
        runTaskTimer(plugin, 0L, compassConfig.getCompassTick());
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(this::track);
    }

    private void track(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;
            if (item.getType() != Material.COMPASS) continue;
            modifyCompass(item, player.getWorld());
        }
    }

    private void modifyCompass(ItemStack compass, World world) {
        CompassMeta meta = (CompassMeta) compass.getItemMeta();
        NamespacedKey key = new NamespacedKey(CompassAPI.getPlugin(CompassAPI.class), "target");
        if (!meta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) return;
        Player target = Bukkit.getPlayerExact(meta.getPersistentDataContainer().get(key, PersistentDataType.STRING));
        if (target == null) return;
        if (!target.getWorld().equals(world)) return;
        meta.setLodestone(target.getLocation());
        compass.setItemMeta(meta);
    }

}

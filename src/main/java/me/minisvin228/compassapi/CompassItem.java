package me.minisvin228.compassapi;

import me.minisvin228.compassapi.CompassAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class CompassItem extends ItemStack {

    public CompassItem (String playerName) {
        super(Material.COMPASS);
        CompassMeta meta = (CompassMeta) getItemMeta();
        meta.setLodestoneTracked(false);
        NamespacedKey key = CompassAPI.getCompassTargetKey();
        meta.setDisplayName(ChatColor.YELLOW + "Tracker");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, playerName);
        setItemMeta(meta);
    }
}

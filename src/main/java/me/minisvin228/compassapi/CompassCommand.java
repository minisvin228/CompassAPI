package me.minisvin228.compassapi;

import me.minisvin228.compassapi.CompassItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompassCommand implements CommandExecutor, TabExecutor {

    private final CompassAPI plugin;

    public CompassCommand(CompassAPI plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You cannot execute this command from console!");
            return true;
        }
        if (!sender.hasPermission("compassapi.compass")) {
            sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to execute this command!");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatColor.DARK_RED + "Please, specify a player.");
            return true;
        }

        String targetName = args[0];
        Player target = Bukkit.getPlayerExact(targetName);

        if (target == null) {
            sender.sendMessage(ChatColor.DARK_RED + "Player not found.");
            return true;
        }

        Player hunter = (Player) sender;
        if (hunter.getInventory().firstEmpty() == -1) {
            sender.sendMessage(ChatColor.DARK_RED + "Sorry, you do not have enough inventory space.");
            return true;
        }

        hunter.getInventory().addItem(new CompassItem(targetName));
        hunter.sendMessage(ChatColor.GRAY + "You have been given a compass.");

        target.sendMessage(ChatColor.WHITE + hunter.getName() + ChatColor.GRAY + " is hunting you.");

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> result = Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        if (args.length < 1) return new ArrayList<>();
        String current = args[0];
        result = result.stream().filter(name -> name.startsWith(current)).collect(Collectors.toList());
        return result;
    }
}

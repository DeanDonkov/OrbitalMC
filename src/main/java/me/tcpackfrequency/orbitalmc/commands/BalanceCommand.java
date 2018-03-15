package me.tcpackfrequency.orbitalmc.commands;

import me.tcpackfrequency.orbitalmc.OrbitalMC;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand extends CommandBase<OrbitalMC>{

    /**
     * Creates a new CommandBase for the given plugin.
     *
     * @param plugin The plugin that owns this command.
     */
    public BalanceCommand(OrbitalMC plugin) {
        super(plugin);
    }

    @Override
    public boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            System.out.println("Ingame pls.");
            return true;
        }
        Player p = (Player) sender;
        if(args.length == 0) {
            p.sendMessage(ChatColor.GREEN + "Balance: " + ChatColor.YELLOW + getPlugin().getPm().getOrCreateProfile(p.getUniqueId()).getMoney());
            return true;
        }
        return true;
    }
}

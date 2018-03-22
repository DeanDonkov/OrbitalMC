package me.tcpackfrequency.orbitalmc.commands;

import me.tcpackfrequency.orbitalmc.OrbitalMC;
import me.tcpackfrequency.orbitalmc.messages.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelCommand extends CommandBase<OrbitalMC> {
    /**
     * Creates a new CommandBase for the given plugin.
     *
     * @param plugin The plugin that owns this command.
     */

    private OrbitalMC pl;

    public LevelCommand(OrbitalMC plugin) {
        super(plugin);
        this.pl = plugin;
    }

    @Override
    public boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            System.out.println("Cannot be executed from Console.");
            return true;
        }
        Player p = (Player) sender;
        if(args.length == 0) {
            if(!p.hasPermission("Orbital.level")) {
                p.sendMessage(Messages.NO_PERMISSION.getMessage().replace("{permission}", "Orbital.level"));
                return true;
               }
                p.sendMessage(Messages.CURRENT_LEVEL.getMessage()
                    .replace("{level}", String.valueOf(pl.getPm().getOrCreateProfile(p.getUniqueId()).getLevel()))
                    .replace("{name}", p.getName()));
                if(!p.hasPermission("Orbital.level.admin")) {
                    p.sendMessage(Messages.NO_PERMISSION.getMessage().replace("{permission}", "Orbital.level.admin"));
                    return true;
            }
            pl.getConfig().getStringList("LevelCommand.AdminIndex").forEach(s -> p.sendMessage(this.colorize(s)));
            return true;
        }
        return true;
    }



    private String colorize(String a){
        return ChatColor.translateAlternateColorCodes('&', a);
    }
}

package me.tcpackfrequency.orbitalmc.commands;

import me.tcpackfrequency.orbitalmc.OrbitalMC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermissionsCommand extends CommandBase<OrbitalMC> {
    /**
     * Creates a new CommandBase for the given plugin.
     *
     * @param plugin The plugin that owns this command.
     */

    OrbitalMC pl;

    public PermissionsCommand(OrbitalMC plugin) {
        super(plugin);
        this.pl = plugin;
    }

    @Override
    public boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;

        if(args.length == 0) {
            p.sendMessage("Your permissions are:");
            pl.getPermissions().listPerms(p).forEach(s -> s.getPermissions().keySet().forEach(p::sendMessage));
        }
        return true;
    }
}

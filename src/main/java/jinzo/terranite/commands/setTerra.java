package jinzo.terranite.commands;

import jinzo.terranite.Terranite;
import jinzo.terranite.utils.CommandHelper;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class setTerra {
    public static boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        if (!(sender instanceof Player player)) {
            CommandHelper.sendError(sender, "Only players can use this.");
            return true;
        }

        if (args.length < 2) {
            CommandHelper.sendError(player, "Usage: /s set <block>");
            return true;
        }

        Material material = Material.matchMaterial(args[1]);
        if (material == null || !material.isBlock()) {
            CommandHelper.sendError(player, "Invalid block type: " + args[1]);
            return true;
        }

        var config = Terranite.getInstance().getConfiguration();

        if (config.blockedMaterials.contains(material)) {
            CommandHelper.sendError(player, "This block is forbidden to use");
            return true;
        }

        int changed = CommandHelper.modifySelection(player, material, block -> true);

        if (changed == -1) {
            CommandHelper.sendError(player, "You must set both Position 1 and Position 2 first.");
        } else if (changed == -2) {
            CommandHelper.sendError(player, "Selection too large!");
        } else if (changed == -3) {
            return true;
        } else {
            CommandHelper.sendSuccess(player, "Set " + changed + (changed == 1 ? " block" : " blocks") + " to " + material.name().toLowerCase() + ".");
        }

        return true;
    }
}

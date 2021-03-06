
package io.snw.tutorial.commands;

import io.snw.tutorial.ServerTutorial;
import io.snw.tutorial.data.Getters;
import io.snw.tutorial.enums.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TutorialUse implements CommandExecutor {
	
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (Permissions.USE.hasPerm(sender)){
            if (Getters.getGetters().getAllTutorials().contains(args[0].toLowerCase())) {
                ServerTutorial.getInstance().startTutorial(args[0], player);
                return true;
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThere is no Tutorial by that Name!"));
                return true;
            }
        } else if (Permissions.TUTORIAL.hasTutorialPerm(player, args[0].toLowerCase())) {
            if (Getters.getGetters().getAllTutorials().contains(args[0].toLowerCase())){
                ServerTutorial.getInstance().startTutorial(args[0], player);
                return true;
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThere is no Tutorial by that Name!"));
                return true;
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission for this!!"));
            return true;
        }
    }
}

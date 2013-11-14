package io.snw.tutorial;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TutorialCommands implements CommandExecutor {

    ServerTutorial plugin;

    public TutorialCommands(ServerTutorial plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (cmd.getName().equalsIgnoreCase("tutorial")) {
            if (args.length == 0) {
                if (sender.hasPermission("tutorial.use")) {
                    this.plugin.startTutorial((Player) sender);
                }
            }
            if (args.length == 1) {
                sender.sendMessage(ChatColor.RED + "You must supply a message!");
                return true;
            }
            if (args.length > 1) {
                if (args[0].equalsIgnoreCase("create")) {
                    if (sender.hasPermission("tutorial.create")) {
                        String message = "";
                        boolean skip = true;
                        for (String part : args) {
                            if (skip) {
                                skip = false;
                            } else {
                                if (message != "")
                                    message += " ";
                                message += part;
                            }
                        }
                        int viewID = 1;
                        while (this.plugin.getConfig().get("views." + viewID) != null) {
                            viewID++;
                        }
                        Location loc = ((Player) sender).getLocation();
                        this.plugin.getTutorialUtils().saveLoc(viewID, loc);
                        this.plugin.getConfig().set("views." + viewID + ".message", message);
                        this.plugin.getConfig().set("views." + viewID + ".type", "META");
                        this.plugin.saveConfig();
                        this.plugin.incrementTotalViews();
                        TutorialView view = new TutorialView(viewID, message, loc, MessageType.META);
                        plugin.addTutorialView(viewID, view);

                        sender.sendMessage(ChatColor.DARK_BLUE + "[Tutorial] " + ChatColor.LIGHT_PURPLE + "View " + viewID + " was successfully saved.");
                    }
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Try /tutorial");
        }
        return true;
    }
}
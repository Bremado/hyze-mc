package com.toddydev.lobby.commands;

import com.toddydev.hyze.bukkit.commands.CommandBase;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.api.properties.PropertiesCache;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.lobby.Lobby;
import com.toddydev.lobby.invite.Invite;
import com.toddydev.lobby.menus.InviteMenu;
import com.toddydev.lobby.platform.Platform;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Properties;

public class InviteCommand extends CommandBase {

    public InviteCommand() {
        super(
                "duel"
        );
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        HyzePlayer hyzePlayer = Core.getPlayerController().getHyzePlayer(player.getUniqueId());
        Properties properties = PropertiesCache.getProperties(hyzePlayer.getLanguage().getCode() + ".properties");
        if (args.length == 0) {
            player.sendMessage(properties.getProperty("command-duel-usage"));
            return true;
        }

        if (args.length > 0) {
            String s = args[0];
            if (s.equalsIgnoreCase("aceitar")) {
                if (args.length > 1) {
                    Player received = Lobby.getInstance().getServer().getPlayer(args[1]);
                    if (received == null) {
                        player.sendMessage(properties.getProperty("command-duel-not-found"));
                        return true;
                    }

                    if (Platform.getInviteController().getInvite(received.getName()) == null) {
                        player.sendMessage(properties.getProperty("command-duel-invite-not-found").replace("{player}", received.getName()));
                        return true;
                    }

                    Invite invite = Platform.getInviteController().getInvite(received.getName());
                    Platform.getInviteController().acceptInvite(invite);
                    return true;
                }
            }
            Player received = Lobby.getInstance().getServer().getPlayer(args[0]);
            if (received == null) {
                player.sendMessage(properties.getProperty("command-duel-not-found"));
                return true;
            }

            InviteMenu.open(player);
        }
        return false;
    }
}

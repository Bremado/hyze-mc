package com.toddydev.auth.commands.login;

import com.toddydev.auth.data.info.AuthInfo;
import com.toddydev.auth.encrypt.Serializer;
import com.toddydev.auth.events.GamerLoggedEvent;
import com.toddydev.auth.platform.Platform;
import com.toddydev.auth.player.Gamer;
import com.toddydev.auth.player.state.AuthState;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoginCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String s2, String[] args) {
        if (!(s instanceof Player)) {
            return true;
        }

        Player player = (Player) s;
        Gamer gamer = Platform.getGamerManager().getGamer(player.getUniqueId());
        if (cmd.getLabel().equalsIgnoreCase("login")) {
            if (args.length == 0) {
                if (gamer.getState() != AuthState.LOGIN) {
                    player.sendMessage("§cVocê não está registrado, crie uma conta utilizando '/registrar [senha]'.");
                    return true;
                } else {
                    player.sendMessage("§cSintaxe incorreta, utilize '/logar [senha]'.");
                    return true;
                }
            } else if (args.length > 0) {
                if (gamer.getState() != AuthState.LOGIN) {
                    player.sendMessage("§cVocê não está registrado, crie uma conta utilizando '/registrar [senha]'.");
                    return true;
                }
                AuthInfo authInfo = Platform.getDataAuth().getAuthInfo(player.getUniqueId());
                String password = args[0];
                String dPassword = Serializer.decrypt(authInfo.getPassword(), password);
                if (dPassword == null) {
                    player.sendMessage("§cA sua senha está incorreta.");
                    return true;
                }

                if (dPassword.equals(password)) {
                    player.sendMessage("§aVocê autenticou com sucesso!");
                    Bukkit.getPluginManager().callEvent(new GamerLoggedEvent(player, AuthState.LOGGED));
                    return true;
                }
            }
        }
        return false;
    }
}

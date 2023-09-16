package com.toddydev.duels.commands;

import com.toddydev.duels.gamer.GamePlayer;
import com.toddydev.duels.platform.Platform;
import com.toddydev.duels.utils.GameUtils;
import com.toddydev.hyze.bukkit.BukkitCore;
import com.toddydev.hyze.bukkit.commands.CommandBase;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.games.GameInfo;
import com.toddydev.hyze.core.games.room.RoomType;
import com.toddydev.hyze.core.games.type.GameType;
import com.toddydev.hyze.core.packets.Packets;
import com.toddydev.hyze.core.player.connect.PlayerConnect;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.toddydev.hyze.core.games.state.GameState.ESPERANDO;

public class PlayagainCommand extends CommandBase {

    public PlayagainCommand() {
        super(
                "playagain"
        );
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        GamePlayer gamePlayer = Platform.getGameController().getGamerPlayer(player.getUniqueId());
        if (args.length == 0) {
            if (gamePlayer.getArena() == null) {
                player.sendMessage("§cVocê não está em uma arena.");
                return true;
            }
            GameInfo gameInfo = GameUtils.getGame(gamePlayer.getArena().getName());

            if (gameInfo == null) {
                player.sendMessage("§cNão foi possível identificar o seu servidor.");
                return true;
            }

            for (GameInfo serverInfo : Core.getGameController().getServerInfos()) {
                if (serverInfo.getType().equals(gameInfo.getType())) {
                    if (serverInfo.getRoom().equals(gameInfo.getRoom())) {
                        if (!serverInfo.getName().equals(gameInfo.getName())) {
                            if (serverInfo.getState() == ESPERANDO) {
                                PlayerConnect playerConnect = new PlayerConnect(player.getUniqueId(), serverInfo.getDisplay());
                                Packets.Game.Connect.publish(playerConnect);
                                BukkitCore.getInstance().connectServer(player, Core.getServerController().getServerInfo(serverInfo.getName()));
                                return true;
                            }
                        }
                    }
                }
            }
            player.sendMessage("§cNão existem partidas disponíveis no momento.");
            return true;
        }
        return false;
    }
}

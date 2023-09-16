package com.toddydev.lobby.controllers;

import com.toddydev.hyze.bukkit.BukkitCore;
import com.toddydev.hyze.core.Core;
import com.toddydev.hyze.core.api.properties.PropertiesCache;
import com.toddydev.hyze.core.games.GameInfo;
import com.toddydev.hyze.core.games.room.RoomType;
import com.toddydev.hyze.core.games.type.GameType;
import com.toddydev.hyze.core.packets.Packets;
import com.toddydev.hyze.core.player.HyzePlayer;
import com.toddydev.hyze.core.player.connect.PlayerConnect;
import com.toddydev.lobby.Lobby;
import com.toddydev.lobby.invite.Invite;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

import static com.toddydev.hyze.core.games.state.GameState.ESPERANDO;

public class InviteController {

    private HashMap<String, Invite> invites = new HashMap<>();

    public void load(Invite invite) {
        invites.put(invite.getSender().getName(), invite);
    }

    public void sendInvite(Invite invite) {
        invites.put(invite.getSender().getName(), invite);

        HyzePlayer hyzePlayer = Core.getPlayerController().getHyzePlayer(invite.getSender().getUniqueId());
        Properties langHyzePlayer = PropertiesCache.getProperties(hyzePlayer.getLanguage().getCode() + ".properties");
        HyzePlayer receveidPlayer = Core.getPlayerController().getHyzePlayer(invite.getReceived().getUniqueId());
        Properties langReceivedPlayer = PropertiesCache.getProperties(receveidPlayer.getLanguage().getCode() + ".properties");
        invite.getSender().sendMessage(langHyzePlayer.getProperty("command-duel-invite-sent").replace("{player}", invite.getReceived().getName()));

        TextComponent p1 = new TextComponent(langReceivedPlayer.getProperty("command-duel-invite-received-click"));
        TextComponent message = new TextComponent(langReceivedPlayer.getProperty("command-duel-invite-received").replace("{player}", invite.getSender().getName()));
        p1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel aceitar " + invite.getSender().getName()));

        invite.getReceived().sendMessage(message);
        invite.getReceived().sendMessage(p1);
    }

    public void acceptInvite(Invite invite) {
        HyzePlayer sender = Core.getPlayerController().getHyzePlayer(invite.getSender().getUniqueId());
        HyzePlayer received = Core.getPlayerController().getHyzePlayer(invite.getReceived().getUniqueId());

        Properties senderProperties = PropertiesCache.getProperties(sender.getLanguage().getCode() + ".properties");
        Properties receivedProperties = PropertiesCache.getProperties(received.getLanguage().getCode() + ".properties");

        invite.getSender().sendMessage(senderProperties.getProperty("command-duel-invite-accepted-sender").replace("{player}", invite.getReceived().getName()));
        invite.getReceived().sendMessage(receivedProperties.getProperty("command-duel-invite-accepted-received").replace("{player}", invite.getSender().getName()));

        for (GameInfo gameInfo : Core.getGameController().getServerInfos()) {
            if (gameInfo.getType().equals(GameType.DUELS)) {
                if (gameInfo.getRoom().equals(invite.getRoomType())) {
                    if (gameInfo.getState() == ESPERANDO) {
                        PlayerConnect playerConnect = new PlayerConnect(invite.getSender().getUniqueId(), gameInfo.getDisplay());
                        PlayerConnect playerConnect2 = new PlayerConnect(invite.getReceived().getUniqueId(), gameInfo.getDisplay());
                        Packets.Game.Connect.publish(playerConnect);
                        Packets.Game.Connect.publish(playerConnect2);

                        Lobby.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Lobby.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                BukkitCore.getInstance().connectServer(invite.getSender(), Core.getServerController().getServerInfo(gameInfo.getName()));
                                BukkitCore.getInstance().connectServer(invite.getReceived(), Core.getServerController().getServerInfo(gameInfo.getName()));
                                invites.remove(invite.getReceived().getName());
                                return;
                            }
                        }, 10L);
                    }
                }
            }
            invite.getSender().sendMessage(senderProperties.getProperty("servers-unavailable"));
            invite.getReceived().sendMessage(receivedProperties.getProperty("servers-unavailable"));
            return;
        }
        // CONNECT
    }

    public Invite getInvite(String name) {
        return invites.get(name);
    }
}

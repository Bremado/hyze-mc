package com.toddydev.lobby.invite;

import com.toddydev.hyze.core.games.room.RoomType;
import com.toddydev.lobby.invite.type.InviteType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter @Setter
public class Invite {

    private Player sender;
    private Player received;

    private RoomType roomType;

    public Invite(Player sender, Player received) {
        this.sender = sender;
        this.received = received;
    }
}

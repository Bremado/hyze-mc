package com.toddydev.auth;

import com.toddydev.auth.commands.SetarCommand;
import com.toddydev.auth.commands.login.LoginCommand;
import com.toddydev.auth.commands.register.RegisterCommand;
import com.toddydev.auth.data.impl.DataAuthImpl;
import com.toddydev.auth.listener.GamerListeners;
import com.toddydev.auth.listener.others.OthersListeners;
import com.toddydev.auth.platform.Platform;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AuthPlugin extends JavaPlugin {

    @Getter @Setter
    private static AuthPlugin instance;

    @Override
    public void onLoad() {
        setInstance(this);
        saveDefaultConfig();
        Platform.setDataAuth(new DataAuthImpl());
    }

    @Override
    public void onEnable() {
        getCommand("setar").setExecutor(new SetarCommand());
        getCommand("login").setExecutor(new LoginCommand());
        getCommand("register").setExecutor(new RegisterCommand());
        Bukkit.getPluginManager().registerEvents(new GamerListeners(), this);
        Bukkit.getPluginManager().registerEvents(new OthersListeners(), this);
    }

    @Override
    public void onDisable() {

    }
}

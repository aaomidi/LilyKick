package com.aaomidi.lilykick;


import lilypad.client.connect.api.Connect;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public class LilyKick extends JavaPlugin {
    private final LilyKick _plugin = this;
    @Getter
    private String SERVER_NAME;
    @Getter
    private Connect connect;
    @Getter
    private LilyConnection lilyConnection;
    @Getter
    private String hubName;

    @Override
    public void onLoad() {
        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        }
    }

    @Override
    public void onEnable() {
        hubName = _plugin.getConfig().getString("Hub-Name");
        _plugin.setupLily();
        lilyConnection = new LilyConnection(this);
    }

    @Override
    public void onDisable() {
        Player[] players = _plugin.getServer().getOnlinePlayers();
        if (players != null) {
            for (Player player : players) {
                _plugin.getLilyConnection().teleportRequest(player.getName());
            }
        }
    }

    private void setupLily() {
        Plugin plugin = _plugin.getServer().getPluginManager().getPlugin("LilyPad-Connect");
        if (plugin == null) {
            _plugin.setEnabled(false);
            _plugin.getLogger().log(Level.SEVERE, "CowCore was shut down since LilyPad-Connect was not found!");
        } else {
            connect = (Connect) _plugin.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
            SERVER_NAME = connect.getSettings().getUsername();
        }
    }

}

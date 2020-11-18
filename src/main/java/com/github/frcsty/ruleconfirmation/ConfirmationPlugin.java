package com.github.frcsty.ruleconfirmation;

import com.github.frcsty.ruleconfirmation.listener.PlayerCommandListener;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class ConfirmationPlugin extends JavaPlugin {

    public static NamespacedKey CONFIRMATION_KEY;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        CONFIRMATION_KEY = new NamespacedKey(this, "rule-confirmation-status");

        getServer().getPluginManager().registerEvents(
                new PlayerCommandListener(this),
                this
        );
    }

    @Override
    public void onDisable() {
        reloadConfig();
    }

}

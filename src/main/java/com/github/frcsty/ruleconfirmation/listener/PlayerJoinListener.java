package com.github.frcsty.ruleconfirmation.listener;

import com.github.frcsty.ruleconfirmation.ConfirmationPlugin;
import com.github.frcsty.ruleconfirmation.util.Color;
import me.clip.placeholderapi.libs.JSONMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.concurrent.CompletableFuture;

public final class PlayerJoinListener implements Listener {

    private final ConfirmationPlugin plugin;

    public PlayerJoinListener(final ConfirmationPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        CompletableFuture.supplyAsync(() -> {
            final FileConfiguration config = plugin.getConfig();
            final Player player = event.getPlayer();

            if (plugin.getConfirmationRequest().hasConfirmedRules(player)) {
                return null;
            }

            player.sendMessage(Color.translate(config.getString("messages.rules")));

            final JSONMessage jsonMessage = JSONMessage.create(Color.translate(config.getString("messages.confirm-rules")));
            jsonMessage.tooltip(Color.translate(config.getString("messages.hover")));
            jsonMessage.runCommand("/rules confirm");

            jsonMessage.send(player);
            return null;
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

}

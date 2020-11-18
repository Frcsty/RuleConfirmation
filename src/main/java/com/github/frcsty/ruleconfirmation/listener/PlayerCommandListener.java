package com.github.frcsty.ruleconfirmation.listener;

import com.github.frcsty.ruleconfirmation.ConfirmationPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public final class PlayerCommandListener implements Listener {

    private final Set<String> WHITELISTED_COMMANDS = new HashSet<>(Arrays.asList(
            "login",
            "register",
            "rules"
    ));

    private final ConfirmationPlugin plugin;

    public PlayerCommandListener(final ConfirmationPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommand(final PlayerCommandPreprocessEvent event) {
        CompletableFuture.supplyAsync(() -> {
            final Player player = event.getPlayer();
            final String command = event.getMessage().split(" ")[0];

            if (plugin.getConfirmationRequest().hasConfirmedRules(player)) {
                return null;
            }

            if (!WHITELISTED_COMMANDS.contains(command.toLowerCase())) {
                event.setCancelled(true);
            }
            return null;
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

}

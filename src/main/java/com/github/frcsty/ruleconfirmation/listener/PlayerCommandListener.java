package com.github.frcsty.ruleconfirmation.listener;

import com.github.frcsty.ruleconfirmation.ConfirmationPlugin;
import com.github.frcsty.ruleconfirmation.request.ConfirmationRequest;
import com.github.frcsty.ruleconfirmation.util.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class PlayerCommandListener implements Listener {

    private static final Set<String> WHITELISTED_COMMANDS = new HashSet<>(Arrays.asList(
            "rules",
            "login",
            "register"
    ));

    private final ConfirmationPlugin plugin;

    public PlayerCommandListener(final ConfirmationPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommandPreprocess(final PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final String commandMessage = event.getMessage().split(" ")[0];

        if (ConfirmationRequest.hasConfirmedRules(player)) {
            return;
        }

        if (!WHITELISTED_COMMANDS.contains(commandMessage.toLowerCase())) {
            event.setCancelled(true);

            player.sendMessage(Color.translate(
                    plugin.getConfig().getString("messages.confirm-rules")
            ));
        }
    }

}

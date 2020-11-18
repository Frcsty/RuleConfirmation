package com.github.frcsty.ruleconfirmation.command;

import com.github.frcsty.ruleconfirmation.ConfirmationPlugin;
import com.github.frcsty.ruleconfirmation.util.Color;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

@Command("rules")
public final class RulesConfirmationCommand extends CommandBase {

    private final ConfirmationPlugin plugin;

    public RulesConfirmationCommand(final ConfirmationPlugin plugin) {
        this.plugin = plugin;
    }

    @SubCommand("confirm")
    public void onCommand(final Player player) {
        CompletableFuture.supplyAsync(() -> {
            if (plugin.getConfirmationRequest().hasConfirmedRules(player)) {
                return null;
            }

            plugin.getConfirmationRequest().setConfirmationStatus(player);
            player.sendMessage(Color.translate(
                    plugin.getConfig().getString("messages.confirmed-rules")
            ));
            return null;
        }).exceptionally(ex -> {
           ex.printStackTrace();
           return null;
        });
    }

}

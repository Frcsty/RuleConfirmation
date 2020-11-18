package com.github.frcsty.ruleconfirmation;

import com.github.frcsty.ruleconfirmation.command.RulesConfirmationCommand;
import com.github.frcsty.ruleconfirmation.listener.PlayerCommandListener;
import com.github.frcsty.ruleconfirmation.listener.PlayerJoinListener;
import com.github.frcsty.ruleconfirmation.request.ConfirmationRequest;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

public final class ConfirmationPlugin extends JavaPlugin {

    private final ConfirmationRequest confirmationRequest = new ConfirmationRequest(this);

    @Override
    public void onEnable() {
        saveDefaultConfig();

        registerListeners(
                new PlayerJoinListener(this),
                new PlayerCommandListener(this)
        );

        final CommandManager commandManager = new CommandManager(this);
        commandManager.register(
                new RulesConfirmationCommand(this)
        );
    }

    @Override
    public void onDisable() {
        reloadConfig();
    }

    public void saveResources(final String... resources) {
        Arrays.stream(resources).forEach(resource -> {
            if (!new File(getDataFolder(), resource).exists()) saveResource(resource, false);
        });
    }

    private void registerListeners(final Listener... listeners) {
        Arrays.stream(listeners).forEach(listener ->
                getServer().getPluginManager().registerEvents(listener, this)
        );
    }

    public ConfirmationRequest getConfirmationRequest() {
        return this.confirmationRequest;
    }

}

package com.github.frcsty.ruleconfirmation;

import com.github.frcsty.ruleconfirmation.command.RulesConfirmationCommand;
import com.github.frcsty.ruleconfirmation.listener.PlayerJoinListener;
import com.github.frcsty.ruleconfirmation.request.ConfirmationRequest;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

public final class ConfirmationPlugin extends JavaPlugin {

    private final ConfirmationRequest confirmationRequest = new ConfirmationRequest(this);

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(
                new PlayerJoinListener(this),
                this
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

    /**
     * Saves resources, without outputting and "already exists" messages into console
     *
     * @param resources Desired resources path's to be saved
     */
    public void saveResources(final String... resources) {
        Arrays.stream(resources).forEach(resource -> {
            if (!new File(getDataFolder(), resource).exists()) saveResource(resource, false);
        });
    }

    public ConfirmationRequest getConfirmationRequest() {
        return this.confirmationRequest;
    }

}

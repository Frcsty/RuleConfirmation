package com.github.frcsty.ruleconfirmation.request;

import com.github.frcsty.ruleconfirmation.ConfirmationPlugin;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public final class ConfirmationRequest {

    public static boolean hasConfirmedRules(final Player player) {
        final PersistentDataContainer container = player.getPersistentDataContainer();
        final String confirmationStatus = container.get(ConfirmationPlugin.CONFIRMATION_KEY, PersistentDataType.STRING);

        if (confirmationStatus == null) {
            container.set(ConfirmationPlugin.CONFIRMATION_KEY, PersistentDataType.STRING, "false");
            return false;
        }

        return confirmationStatus.equalsIgnoreCase("true");
    }

    public static void setConfirmedRules(final Player player) {
        final PersistentDataContainer container = player.getPersistentDataContainer();

        container.set(ConfirmationPlugin.CONFIRMATION_KEY, PersistentDataType.STRING, "true");
    }

}

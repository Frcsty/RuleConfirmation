package com.github.frcsty.ruleconfirmation.request;

import com.github.frcsty.ruleconfirmation.ConfirmationPlugin;
import com.github.frcsty.ruleconfirmation.request.database.ConnectionProvider;
import com.github.frcsty.ruleconfirmation.request.database.Statement;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ConfirmationRequest {

    private final ConnectionProvider connectionProvider;

    public ConfirmationRequest(final ConfirmationPlugin plugin) {
        plugin.saveResources(
                "hikari.properties"
        );
        this.connectionProvider = new ConnectionProvider(plugin);

        this.connectionProvider.setupDatabase();
    }

    public boolean hasConfirmedRules(final Player player) {
        final Connection connection = this.connectionProvider.getConnection();

        if (connection == null) return false;

        try {
            final PreparedStatement statement = connection.prepareStatement(
                    Statement.SELECT_USER
            );

            if (statement == null) {
                return false;
            }

            statement.setString(0, this.connectionProvider.getDatabaseName());
            statement.setString(1, Statement.USER_TABLE);
            statement.setString(2, player.getUniqueId().toString());

            final ResultSet resultSet = statement.executeQuery();

            statement.close();
            connection.close();

            final boolean result = resultSet.getBoolean("confirmationStatus");
            resultSet.close();

            return result;
        } catch (final SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public void setConfirmationStatus(final Player player) {
        final Connection connection = this.connectionProvider.getConnection();

        if (connection == null) return;

        try {
            final PreparedStatement statement = connection.prepareStatement(
                    Statement.UPDATE_USER
            );

            statement.setString(0, this.connectionProvider.getDatabaseName());
            statement.setString(1, Statement.USER_TABLE);

            statement.setString(2, player.getUniqueId().toString());
            statement.setBoolean(3, true);

            statement.setBoolean(4, true);

            statement.close();
            connection.close();
        } catch (final SQLException ex) {
            ex.printStackTrace();
        }
    }

}

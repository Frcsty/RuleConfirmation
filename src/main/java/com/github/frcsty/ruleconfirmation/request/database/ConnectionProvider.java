package com.github.frcsty.ruleconfirmation.request.database;

import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

public final class ConnectionProvider extends DatabaseFactory {

    private final HikariDataSource dataSource;
    private final JavaPlugin plugin;

    /**
     * Set's up our variables
     *
     * @param plugin Our {@link JavaPlugin} instance
     */
    public ConnectionProvider(final JavaPlugin plugin) {
        this.dataSource = configureDataSource(plugin);
        this.plugin = plugin;
    }

    /**
     * Returns our initialized database connection, or disables the plugin if it
     * could not be retrieved
     *
     * @return Our Database {@link Connection}
     */
    @Override
    public java.sql.Connection getConnection() {
        java.sql.Connection connection;

        try {
            connection = dataSource.getConnection();
        } catch (final SQLException ex) {
            plugin.getLogger().log(Level.WARNING, "Failed to initialize database connection!");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return null;
        }

        return connection;
    }

    /**
     * Get's our database name from hikari.properties
     *
     * @return Our database name
     */
    @Override
    public String getDatabaseName() {
        final Properties properties = readPropertiesFile(plugin.getDataFolder() + "/hikari.properties");
        return properties.getProperty("dataSource.databaseName");
    }

    /**
     * Set's up our database to ensure the required tables have been created
     */
    public void setupDatabase() {
        final Connection connection = getConnection();

        if (connection == null) {
            plugin.getLogger().log(Level.WARNING, "Database connection was null, failed to setup database!");
            return;
        }

        try {
            final PreparedStatement databaseSetupStatement = connection.prepareStatement(
                    Statement.SETUP_DATABASE
            );

            databaseSetupStatement.setString(0, getDatabaseName());
            databaseSetupStatement.executeUpdate();

            final PreparedStatement usersTableSetupStatement = connection.prepareStatement(
                    Statement.SETUP_USERS_TABLE
            );

            usersTableSetupStatement.setString(0, getDatabaseName());
            usersTableSetupStatement.setString(1, Statement.USER_TABLE);
            usersTableSetupStatement.executeUpdate();

            connection.close();
        } catch (final SQLException ex) {
            plugin.getLogger().log(Level.WARNING, "An exception has occurred while setting up the database!", ex);
        }
    }
}

package com.github.frcsty.ruleconfirmation.request.database;

public final class Statement {

    public static final String USER_TABLE = "users";

    static final String SETUP_DATABASE = "CREATE DATABASE IF NOT EXISTS `?`;";
    static final String SETUP_USERS_TABLE = "CREATE TABLE IF NOT EXISTS `?`.`?` (`uuid` CHAR(36) NOT NULL, `confirmationStatus` BOOLEAN NOT NULL, PRIMARY KEY (`uuid`));";

    public static final String SELECT_USER = "SELECT * FROM `?`.`?` WHERE uuid='?'";
    public static final String UPDATE_USER = "INSERT INTO `?`.`?` (uuid, confirmationStatus) VALUES (`?`, ?) ON DUPLICATE KEY UPDATE confirmationStatus=?";

}

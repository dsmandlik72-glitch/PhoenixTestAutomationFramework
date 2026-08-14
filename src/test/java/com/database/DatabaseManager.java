package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtil;
import com.api.utils.VaultDBConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {

//	private static final String DB_URL = ConfigManager.getProperty("DB_URL");
//	private static final String DB_USERNAME = ConfigManager.getProperty("DB_USERNAME");
//	private static final String DB_PASSWORD = ConfigManager.getProperty("DB_PASSWORD");

//	private static final String DB_URL = EnvUtil.getValue("DB_URL");
//	private static final String DB_USERNAME = EnvUtil.getValue("DB_USERNAME");
//	private static final String DB_PASSWORD = EnvUtil.getValue("DB_PASSWORD");

//	private static final String DB_URL = VaultDBConfig.getSecret("DB_URL");
//	private static final String DB_USERNAME = VaultDBConfig.getSecret("DB_USERNAME");
//	private static final String DB_PASSWORD = VaultDBConfig.getSecret("DB_PASSWORD");

	private static final int MAX_POOL_SIZE = Integer.parseInt(ConfigManager.getProperty("MAXIMUM_POOL_SIZE"));
	private static final int MINIUM_IDLE_COUNT = Integer.parseInt(ConfigManager.getProperty("MINIUM_IDLE_COUNT"));
	private static final int CONNECTION_TIMEOUT_IN_SECS = Integer
			.parseInt(ConfigManager.getProperty("CONNECTION_TIMEOUT_IN_SECS"));
	private static final int IDLE_TIMEOUT_SECS = Integer.parseInt(ConfigManager.getProperty("IDLE_TIMEOUT_SECS"));
	private static final int MAX_LIFETIME_IN_MINS = Integer.parseInt(ConfigManager.getProperty("MAX_LIFETIME_IN_MINS"));
	private static final String HIKARI_CP_POOL_NAME = ConfigManager.getProperty("HIKARI_CP_POOL_NAME");
	private static HikariConfig hikariConfig;
	private volatile static HikariDataSource hikariDataSource = null;// We want to make sure hikariDataSource is only
																		// one,here
																		// only one Connection Pool is created. The
																		// purpose of
																		// volatile keyword is that All Threads will be
																		// aware of
																		// any update happens in hikariDataSource
	// private static Connection connection;

	private static boolean isVaultUp = true;
	private static final String DB_URL = loadSecret("DB_URL");
	private static final String DB_USERNAME = loadSecret("DB_USERNAME");
	private static final String DB_PASSWORD = loadSecret("DB_PASSWORD");

	public static String loadSecret(String key) {
		String value = null;
		// Value will get its value from either Vault or Env

		if (isVaultUp) {
			value = VaultDBConfig.getSecret(key);

			if (value == null) {// When something is wrong with Vault!
				System.err.println("Vault is Down!! or some issue with Vault");
				isVaultUp = false;

			} else {
				System.out.println("READING VALUE FROM VAULT......");
				return value;// Coming from Vault!!
			}
		}
		// We need to pick up data from Env!!
		System.out.println("READING VALUE FROM ENV.....");
		value = EnvUtil.getValue(key);
		return value;
	}

	private DatabaseManager() {

	}

	private static void initializePool() {
		if (hikariDataSource == null) {// First check which all the parallel threads will enter
			synchronized (DatabaseManager.class) {
				if (hikariDataSource == null) {// ONLY and only for the first Connection Request
					HikariConfig hikariConfig = new HikariConfig();
					hikariConfig.setJdbcUrl(DB_URL);
					hikariConfig.setUsername(DB_USERNAME);
					hikariConfig.setPassword(DB_PASSWORD);

					hikariConfig.setMaximumPoolSize(MAX_POOL_SIZE);// "10" String is converted to Int
					hikariConfig.setMinimumIdle(MINIUM_IDLE_COUNT);
					hikariConfig.setConnectionTimeout(CONNECTION_TIMEOUT_IN_SECS * 1000);// 10 secs
					hikariConfig.setIdleTimeout(IDLE_TIMEOUT_SECS * 1000);
					hikariConfig.setMaxLifetime(MAX_LIFETIME_IN_MINS * 60 * 1000);// 30 mins
					hikariConfig.setPoolName(HIKARI_CP_POOL_NAME);

					hikariDataSource = new HikariDataSource(hikariConfig);

				}
			}
		}

	}

	public static Connection getConnection() throws SQLException {
		Connection connection = null;
		if (hikariDataSource == null) {
			initializePool();// Automatic Intialization of HikariDataSource
		} else if (hikariDataSource.isClosed()) {
			throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
		}

		connection = hikariDataSource.getConnection();

		return connection;
	}

}

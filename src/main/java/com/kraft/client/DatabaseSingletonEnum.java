package com.kraft.client;

import java.sql.*;

public enum DatabaseSingletonEnum {

	// Enum Singleton Design Pattern in avantajları
	// 1. Thread safe
	// 2. Serialization problemi yok
	// 3. Double checked locking problemi yok
	// 4. Enum singletonlar lazy initialization yaparlar
	// 5. Enum singletonlar enum classlar gibi final ve immutabledır
	// 6. Enum singletonlar garbage collector tarafından yok edilemezler
	// 7. Enum singletonlar her zaman bir instance dönerler

	INSTANCE;

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	private DatabaseSingletonEnum() {
		try {
			String myDriver = "org.postgresql.Driver";
			String dbPath = "jdbc:postgresql://localhost:5433/Kraft";
			Class.forName(myDriver);
			this.connection = DriverManager.getConnection(dbPath, "postgres", "1234");
			this.statement = connection.createStatement();
			System.out.println("Bağlantı Kuruldu!");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Hata: DatabaseSingletonEnum() " + e.getMessage());
		}
	}

	
	public Connection getConnection() {
		return this.connection;
	}
	
	
	public Statement getStatement() {
		return this.statement;
	}
	
	
	public ResultSet getResultSet() {
		return this.resultSet;
	}

	
	public void closeConnection() {
		try {
			if (resultSet != null) {
                resultSet.close();
            }
			this.connection.close();
			this.statement.close();
			System.out.println("Bağlantı kapatıldı");
		} catch (SQLException e) {
			System.out.println("Hata: closeConnetion() " + e.getMessage());
		}
	}

	

}

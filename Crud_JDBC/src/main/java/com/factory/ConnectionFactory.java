package com.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConnectionFactory {

	// Nome do usuário do mysql
	private static final String USERNAME = "postgres";
	// Senha do banco
	private static final String PASSWORD = "admin";
	// Caminho do banco de dados, porta, nome do banco de dados
	private static final String DATABASE_URL = "jdbc:postgresql://localhost:5433/agenda?autoReconnect=true";

	// Conexão com o banco de dados
	public static Connection createConnectionToMySQL() throws SQLException, ClassNotFoundException {
		//Class.forName("com.postgresql.jdbc.Driver");
		Connection connection = null;
		//Cria a conexão com o banco de dados
		connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
		return connection;
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Connection conn = createConnectionToMySQL();

		if (conn != null) {
			JOptionPane.showMessageDialog(null, "Conexão feita");
			conn.close();
		}
	}

}

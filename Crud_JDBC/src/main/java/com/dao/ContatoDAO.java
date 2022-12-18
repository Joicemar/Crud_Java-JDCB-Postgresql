package com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.factory.ConnectionFactory;
import com.model.Contato;

public class ContatoDAO {

	/*
	 * O prepare statment esta passando tudo para minusculo; No banco de dados o
	 * nome das colunas tem que ser todo em minusculo.
	 */
	public void save(Contato contato) {

		String sql = "INSERT INTO contatos(nome, idade, datacadastro) VALUES(?, ?, ?)";

		Connection conn = null;
		PreparedStatement preparedStatement = null;

		try {
			// Cria uma conexão com o banco de dados
			conn = ConnectionFactory.createConnectionToMySQL();
			// O prepareStatement é para executar a query
			preparedStatement = conn.prepareStatement(sql);
			// Passa a posição nos ? e o valor. Valores esperados pela query. 1,2 e 3 é a
			// coluna no banco
			preparedStatement.setString(1, contato.getNome());
			preparedStatement.setInt(2, contato.getIdade());
			preparedStatement.setDate(3, new Date(contato.getDataCadastro().getTime()));

			// Executar a query
			preparedStatement.execute();
			JOptionPane.showMessageDialog(null, "Contato salvo com sucesso!");
			System.out.println("Contato salvo com sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro de SQLException");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro de ClassNotFoundException");
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Erro ao fechar conexão ou o prepareStatement");
			}
		}
	}

	public void update(Contato contato) {

		String sql = "UPDATE contatos SET nome = ?, idade = ?, datacadastro = ? " + "WHERE id = ?";

		Connection conn = null;
		PreparedStatement preparedStatement = null;

		try {
			// Criar conexão com o banco
			conn = ConnectionFactory.createConnectionToMySQL();
			// Criar a classe para executar a query
			preparedStatement = conn.prepareStatement(sql);
			// adiciona os valores para atualizar
			preparedStatement.setString(1, contato.getNome());
			preparedStatement.setInt(2, contato.getIdade());
			preparedStatement.setDate(3, new Date(contato.getDataCadastro().getTime()));
			// Qual o id do registro que deseja atualizar?
			preparedStatement.setInt(4, contato.getId());
			// Executar a query
			preparedStatement.execute();
			JOptionPane.showMessageDialog(null, "Contato atualizado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Erro ao fechar conexão ou o prepareStatement");
			}
		}

	}


	public void deleteById(int id) {
		String sql = "DELETE FROM contatos WHERE id = ?";

		Connection conn = null;
		PreparedStatement preparedStatement = null;

		if (verificaId(id)) {
			try {
				conn = ConnectionFactory.createConnectionToMySQL();
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setInt(1, id);

				ContatoDAO contatoDAO = new ContatoDAO();
				preparedStatement.execute();
				JOptionPane.showMessageDialog(null, "Contato deletado com sucesso!!");

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (preparedStatement != null) {
						preparedStatement.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Contato inexistente!");
		}
	}

	/* recuperar os dados do banco */
	public List<Contato> getContatos() {

		String sql = "SELECT * FROM contatos";

		List<Contato> contatos = new ArrayList<Contato>();

		Connection conn = null;
		PreparedStatement preparedStatement = null;
		// Classe que vai recuperar os dados do banco de dados.
		ResultSet resultSet = null;

		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			preparedStatement = conn.prepareStatement(sql);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Contato contato = new Contato();

				contato.setId(resultSet.getInt("id"));
				contato.setNome(resultSet.getString("nome"));
				contato.setIdade(resultSet.getInt("idade"));
				contato.setDataCadastro(resultSet.getDate("datacadastro"));

				contatos.add(contato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return contatos;

	}

	/*Recebe a lista de todos os contatos e verifica se existe a id passada*/
	public boolean verificaId(int id) {
		ContatoDAO contatoDAO = new ContatoDAO();
		for (Contato contato : contatoDAO.getContatos()) {
			if (contato.getId() == id) {
				return true;
			}
		}
		return false;
	}

}

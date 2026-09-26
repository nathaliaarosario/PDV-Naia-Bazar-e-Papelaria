package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;
import model.Clientes;

public class ClienteController {

	// Instanciar o banco de dados
	private Database database;

	
	
	// Construtor
	public ClienteController() {

		// Reutilizar o database no CRUD
		database = new Database();
	}

	// =====================================
	// Adicionar Cliente (CRUD)
	// =====================================

	public void adicionar(Clientes cliente) throws SQLException {
		String sql = """
				insert into clientes (nome, fone, email, cpf)
				values (?,?,?,?)
				""";

		// abrir a conexão com o banco (passo 2)
		Connection con = database.conectar();

		// executar o comando sql (passo 3)
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, cliente.getNome());
		stmt.setString(2, cliente.getFone());
		stmt.setString(3, cliente.getEmail());
		stmt.setString(4, cliente.getCPF());
		stmt.executeUpdate();

		// fechar a conexão
		stmt.close();
		con.close();
	}

	// =====================================
	// Buscar o Cliente - CRUD
	// =====================================
	public Clientes buscar(String nome) {
		try {
			// Como se eu estivesse escrevendo no banco de dados
			String sql = """
					select idClientes, nome, fone, email, cpf
					from clientes
					where nome like ?
					""";
			// Iniciar um objeto fornecedor como nulo
			Clientes cliente = null;

			// JDBC (Connection e PreparedStatement)
			Connection con = database.conectar();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, "%" + nome + "%");

			// JDBC (ResultSet) = "Trazer os dados do banco"
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				// setar o model
				cliente = new Clientes();
				cliente.setIDClientes(rs.getInt("IdClientes"));
				cliente.setNome(rs.getString("nome"));
				cliente.setFone(rs.getString("fone"));
				cliente.setEmail(rs.getString("email"));
				cliente.setCPF(rs.getString("CPF"));
			}

			// Fechar as conexões
			rs.close();
			stmt.close();
			con.close();

			return cliente;
			// =====================================

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	// =====================================

	// =====================================
	// CRUD Update - Excluir o cliente
	// =====================================
	public void Apagar(int idClientes) {
		try {
			String sql = """
					delete from clientes
					where idClientes = ?
					""";
			// Abrir conexão com o banco
			Connection con = database.conectar();

			// Executar a query (instrução sql)
			PreparedStatement stmt = con.prepareStatement(sql);

			// setar o id do fornecedor (model)
			stmt.setInt(1, idClientes);

			// Executar o delete
			stmt.executeUpdate();

			// Fechar as conexões
			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);

		}
	}// =========================================

	// =====================================
	// CRUD - Editar os dados dos clientes
	// =====================================
	public void editarCliente(Clientes cliente) {
		try {
			String sql = """
					update clientes
					set nome = ?, fone = ?, email = ?, cpf = ?
					where idClientes=?
					""";

			// Estabelecer a conexão com o banco
			Connection con = database.conectar();

			// Executar a instrução sql
			PreparedStatement stmt = con.prepareStatement(sql);

			// Obter os dados do contato (Model)
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getFone());
			stmt.setString(3, cliente.getEmail());
			stmt.setString(4, cliente.getCPF());
			stmt.setInt(5, cliente.getIDClientes());


			// Executa a atualização no banco
			stmt.executeUpdate();

			// Fechar as conexões
			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	// =====================================

}

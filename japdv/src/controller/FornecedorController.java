package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

//Importação de database
import database.Database;
//importação do modelo de dados 
import model.Fornecedor;

public class FornecedorController {

	// Instanciar o banco de dados
	private Database database;

	// Construtor
	public FornecedorController() {

		// Reutilizar o database no CRUD
		database = new Database();
	}

	// Métodos(função) CROUD

	// =====================================
	// Adicionar fonrcedor (CRUD CREATE)
	// =====================================

	public void adicionar(Fornecedor fornecedor) throws SQLException {

		// Comando sql (passo 1)
		String sql = """
				insert into fornecedores (nome, fone, email, site)
				values (?,?,?,?)
				""";

		// abrir a conexão com o banco (passo 2)
		Connection con = database.conectar();

		// executar o comando sql (passo 3)
		PreparedStatement stmt = con.prepareStatement(sql);
		// 1,2,3 = (?,?,?)
		stmt.setString(1, fornecedor.getNome());
		stmt.setString(2, fornecedor.getFone());
		stmt.setString(3, fornecedor.getEmail());
		stmt.setString(4, fornecedor.getSite());
		stmt.executeUpdate();

		// fechar a conexão
		stmt.close();
		con.close();
	}

	// Fim CRUD Create =====================

	// =====================================
	// Buscar o fornecedor (CRUD Read)
	// =====================================
	public Fornecedor buscar(String nome) {
		try {
			// Como se eu estivesse escrevendo no banco de dados
			String sql = """
					select idFornecedor, nome, fone, email, site
					from fornecedores
					where nome like ?
					""";
			// Iniciar um objeto fornecedor como nulo
			Fornecedor fornecedor = null;

			// JDBC (Connection e PreparedStatement)
			Connection con = database.conectar();
			PreparedStatement stmt = con.prepareStatement(sql);

			// Setar a consulta (% Coringa)
			stmt.setString(1, "%" + nome + "%");

			// JDBC (ResultSet) = "Trazer os dados do banco"
			ResultSet rs = stmt.executeQuery();

			// Se existir o fornecedor com o nome pesquisado
			if (rs.next()) {
				// setar o model
				fornecedor = new Fornecedor();
				fornecedor.setIdFornecedor(rs.getInt("idFornecedor"));
				fornecedor.setNome(rs.getString("nome"));
				fornecedor.setFone(rs.getString("fone"));
				fornecedor.setEmail(rs.getString("email"));
				fornecedor.setSite(rs.getString("site"));
			}

			// Fechar as conexões
			rs.close();
			stmt.close();
			con.close();

			return fornecedor;
			// =====================================

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	// =====================================

	// =====================================
	// CRUD Update - Editar os dados
	// =====================================
	public void editarFornecedor(Fornecedor fornecedor) {
		try {
			String sql = """
					update fornecedores
					set nome = ?, fone = ?, email = ?, site = ?
					where idFornecedor=?
					""";

			// Estabelecer a conexão com o banco
			Connection con = database.conectar();

			// Executar a instrução sql
			PreparedStatement stmt = con.prepareStatement(sql);

			// Obter os dados do contato (Model)
			stmt.setString(1, fornecedor.getNome());
			stmt.setString(2, fornecedor.getFone());
			stmt.setString(3, fornecedor.getEmail());
			stmt.setInt(5, fornecedor.getIdFornecedor());
			stmt.setString(4, fornecedor.getSite());

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

	// =====================================
	// CRUD Update - Excluir o fornecedor
	// =====================================
	public void excluir(int idfornecedor) {
		try {
			String sql = """
					delete from fornecedores
					where idFornecedor = ?
					""";
			// Abrir conexão com o banco
			Connection con = database.conectar();

			// Executar a query (instrução sql)
			PreparedStatement stmt = con.prepareStatement(sql);

			// setar o id do fornecedor (model)
			stmt.setInt(1, idfornecedor);

			// Executar o delete
			stmt.executeUpdate();

			// Fechar as conexões
			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);

		}
	}// =========================================

	// =========================================
	// Gerar relatório de de fornecedores (PDF)
	// =========================================
	public void gerarRelatorioFornecedores() {
		try {
			// comando sql
			String sql = """
					select nome, fone, email, site
					from fornecedores order by nome;
					""";
			// abrir conexão com banco
			Connection con = database.conectar();

			// Preparar o comando SQL
			PreparedStatement stmt = con.prepareStatement(sql);

			// Executar consulta
			ResultSet rs = stmt.executeQuery();

			// Criar um objeto documento(pdf)
			// Atenção importar da bibliotexca com.lowagie.text
			Document documento = new Document(); // falta importar

			// Nome do arquivo pdf
			String caminho = "relatorio_fornecedores.pdf";

			// Criar o arquivo pdf
			PdfWriter.getInstance(documento, new FileOutputStream(caminho)); // falta importar

			// Abrir o documento
			documento.open();

			// Título ====================================
			Font fonteTitulo = new Font(Font.HELVETICA, 18, Font.BOLD);

			Paragraph titulo = new Paragraph("RELATÓRIO DE FORNECEDORES", fonteTitulo

			);

			titulo.setAlignment(Element.ALIGN_CENTER);

			documento.add(titulo);

			// ===========================================

			// Data e Hora
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

			String dataHora = LocalDateTime.now().format(formato);

			Paragraph data = new Paragraph("Data de Emissão: " + dataHora);

			data.setAlignment(Element.ALIGN_CENTER);

			documento.add(data);

			// espaço
			documento.add(new Paragraph(" "));

			// Tabela --------------------------------------

			// Criar a tabela com 4 colunas
			PdfPTable tabela = new PdfPTable(4);

			// Definir largura das colunas
			tabela.setWidths(new float[] { 2.5f, 2.0f, 3.0f, 4.0f });

			// Ocupar toda a largura disponível
			tabela.setWidthPercentage(100);

			// Cabeçalho da tabela
			tabela.addCell("Nome");
			tabela.addCell("Fone");
			tabela.addCell("Email");
			tabela.addCell("Site");

			// Dados do fornecedor
			int quantidade = 0; // variável de apoio

			// Enquanto existir fornecedore, adicionar a tabela
			while (rs.next()) {
				tabela.addCell(rs.getString("nome"));
				tabela.addCell(rs.getString("fone"));
				tabela.addCell(rs.getString("email"));
				tabela.addCell(rs.getString("site"));
				// somar a quantidade, atribuindo a variavel
				quantidade++;
			}

			// Adicionar a tabela ao documento
			documento.add(tabela);

			// Fim tabela --------------------------------------

			// ================================
			documento.add(new Paragraph(" ")); // espaço

			// Total de Fornecedores ===========================
			Font fonteTotal = new Font(Font.HELVETICA, 18, Font.BOLD

			);

			Paragraph total = new Paragraph("Total de Fornecedores: " + quantidade, fonteTotal);

			total.setAlignment(Element.ALIGN_RIGHT);

			documento.add(total);
			// =================================================

			// Fechar o documento
			documento.close();

			// Fechar os recursso do banco de dados
			rs.close();
			con.close();
			stmt.close();

			// Abrir o pdf automaticamente no leitor(navegador)
			File arquivo = new File(caminho);
			Desktop.getDesktop().open(arquivo);

		} catch (Exception e) {
			System.out.println(e);
		}
	}// ========================================

	// =========================================
	// Listar fornecedores -> produtos
	// =========================================

	// Para criar uma lista é necessário instanciar um Array no método e também
	// criar um objeto que será usado no array
	public ArrayList<Fornecedor> listarFornecedores() {
		// objeto lista (usando a classe modelo ArrayList)
		ArrayList<Fornecedor> lista = new ArrayList<>();

		try {
			// buscar o id e o nome dos fornecedores
			String sql = """
					select idFornecedor, nome
					from fornecedores
					order by nome
					""";

			// Abrir a conexão com o banco
			Connection con = database.conectar();

			// Preparar o comando sql
			PreparedStatement stmt = con.prepareStatement(sql);

			// Obter os dados do banco
			ResultSet rs = stmt.executeQuery();

			// enquanto existir fornecedores cadastrados
			while (rs.next()) {
				// criar o objeto fornecedor
				Fornecedor fornecedor = new Fornecedor();

				// Armazenar no objeto os Ids e Nomes
				fornecedor.setIdFornecedor(rs.getInt("idFornecedor"));
				fornecedor.setNome(rs.getString("nome"));

				// Adicionar os fornecedores a lista (array)
				lista.add(fornecedor);
			}

			// Encerrar os recursos JDBC
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		// apoio a lógica e depuração (debug) "observação extra"
		System.out.println(lista);

		// Retornar a lista de fornecedores(array)
		return lista;

	}

	// =========================================
}

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

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import database.Database;
import model.Produto;

public class ProdutoController {

	// Instanciar o banco de dados
	private Database database;

	// Construtor
	public ProdutoController() {

		// Reutilizar o database no CRUD
		database = new Database();

	}
	// =====================================
	// Adicionar Produtos (CRUD)
	// =====================================

	public boolean adicionar(Produto produto) throws SQLException {
		try {
			String sql = """
					insert into produtos (codigoBarras, descricao, categoria, precoCusto, precoVenda, quantidade, estoqueMinimo, idFornecedor)
					values (?,?,?,?,?,?,?,?)
					""";

			// abrir a conexão com o banco
			Connection con = database.conectar();

			// executar o comando sql
			PreparedStatement stmt = con.prepareStatement(sql);

			// stmt.setString(1, produtos.getCodigoBarras()); //BUG

			// Correção de BUG se existir um produto ou mais produtos sem código de barras
			// para evitar a dublicidade (Unique no banco), converter o campo de texto não
			// preenchido em null

			if (produto.getCodigoBarras().isBlank()) {
				stmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
				stmt.setString(1, produto.getCodigoBarras());
			}

			stmt.setString(2, produto.getDescricao());
			stmt.setString(3, produto.getCategoria());
			stmt.setDouble(4, produto.getPrecoCusto());
			stmt.setDouble(5, produto.getPrecoVenda());
			stmt.setInt(6, produto.getQuantidade());
			stmt.setInt(7, produto.getEstoqueMinimo());
			stmt.setInt(8, produto.getIdFornecedor());

			stmt.executeUpdate();

			// fechar a conexão
			stmt.close();
			con.close();

			return true;

		} catch (Exception e) {
			System.out.println(e);
			return false;

		}
	}// ==============================================

	// ================================================
	// Buscar o Produto pelo código de Barras ========
	// ================================================
	public Produto buscarCodigoBarras(String codigoBarras) {
		try {
			String sql = """
					select idProdutos, descricao, categoria, precoCusto, precoVenda, quantidade, estoqueMinimo, idFornecedor
					from produtos
					where codigoBarras = ?
					""";

			// Iniciar um objeto como nullo
			Produto produto = null;

			// Abrir conexão com o banco
			Connection con = database.conectar();

			// Preparar a execução do comando sql
			PreparedStatement stmt = con.prepareStatement(sql);

			// setar o código de barras(?)
			stmt.setString(1, codigoBarras);

			// JDBC (ResultSet) = "Trazer os dados do banco"
			ResultSet rs = stmt.executeQuery();

			// Se existir um produto cadastrado
			if (rs.next()) {
				// setar o model
				produto = new Produto();
				produto.setIdProduto(rs.getInt("idProdutos"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setCategoria(rs.getString("categoria"));
				produto.setPrecoCusto(rs.getDouble("precoCusto"));
				produto.setPrecoVenda(rs.getDouble("precoVenda"));
				produto.setQuantidade(rs.getInt("quantidade"));
				produto.setEstoqueMinimo(rs.getInt("estoqueMinimo"));
				produto.setIdFornecedor(rs.getInt("idFornecedor"));
			}

			stmt.close();
			con.close();

			// retornar o objeto (contem atributos)
			return produto;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	} // ===============================================

	// =====================================
	// CRUD Update - Editar os dados
	// =====================================
	public void editarProduto(Produto produto) {
		try {
			String sql = """
					update produtos
					set codigoBarras = ?, descricao =?, categoria = ?, precoCusto = ?, precoVenda = ?, quantidade = ?, estoqueMinimo = ?, idFornecedor = ?
					where idProdutos = ?
					""";

			// Estabelecer a conexão com o banco
			Connection con = database.conectar();

			// Executar a instrução sql
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setString(1, produto.getCodigoBarras());
			stmt.setString(2, produto.getDescricao());
			stmt.setString(3, produto.getCategoria());
			stmt.setDouble(4, produto.getPrecoCusto());
			stmt.setDouble(5, produto.getPrecoVenda());
			stmt.setInt(6, produto.getQuantidade());
			stmt.setInt(7, produto.getEstoqueMinimo());
			stmt.setInt(8, produto.getIdFornecedor());

			stmt.setInt(9, produto.getIdProduto());

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

	// ========================================
	// Gerar relatório de produtos (PDF)
	// =======================================
	public void gerarRelatorioproduto() {
		try {

			// consulta sql com inner join (relatório personalizado)
			String sql = """
					select p.codigoBarras, p.descricao, p.categoria, f.nome as fornecedor,
					p.precoCusto, p.precoVenda, p.quantidade, p.estoqueMinimo
					from produtos p
					 inner join fornecedores f on p.idFornecedor = f.idFornecedor
					 order by descricao;
					""";

			// abrir a conexão com banco de dados
			Connection con = database.conectar();

			// preparar o comando sql
			PreparedStatement stmt = con.prepareStatement(sql);

			// Executar a consulta e obter os dados
			ResultSet rs = stmt.executeQuery();

			// Criar o objeto documento (pdf)
			// .rotate() - a folha em modo deitada
			Document documento = new Document(PageSize.A4.rotate());

			// Caminho e nome do arquivo
			String caminho = "Relatório_Produtos.pdf";

			// Criar arquivo pdf
			PdfWriter.getInstance(documento, new FileOutputStream(caminho));

			// abrir o documento (formatar o documento pdf)
			documento.open();

			// Título
			Font fonteTitulo = new Font(Font.HELVETICA, 16, Font.BOLD);
			Paragraph titulo = new Paragraph("RELATÓRIO DE CONTROLE DE ESTOQUE", fonteTitulo);
			titulo.setAlignment(Element.ALIGN_CENTER);
			documento.add(titulo);

			// Data e hora ====================================
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

			String dataHora = LocalDateTime.now().format(formato);
			Paragraph data = new Paragraph("Data de Emissão: " + dataHora);
			data.setAlignment(Element.ALIGN_CENTER);
			documento.add(data);

			// espaço
			documento.add(new Paragraph(" "));

			// ================================================
			// Tabela ========================================

			// Criar a tabela com 4 colunas
			PdfPTable tabela = new PdfPTable(8);

			// Definir largura das colunas
			tabela.setWidths(new float[] { 2.2f, 2.0f, 2.0f, 2.0f, 1.8f, 1.8f, 2.0f, 1.8f });

			// Ocupar toda a largura disponível
			tabela.setWidthPercentage(100);

			// Cabeçalho personalizado da tabela
			Font fonteCabecalho = new Font(Font.HELVETICA, 9, Font.BOLD, java.awt.Color.white);
			String[] colunas = { "Código de Barras", "Descrição", "Categoria", "Fornecedor", "Custo (R$)", "Venda (R$)",
					"Qtd. Estoque", "Estoque Min." };
			for (String nomeColuna : colunas) {
				PdfPCell celulaCabecalho = new PdfPCell(new Paragraph(nomeColuna, fonteCabecalho));
				celulaCabecalho.setBackgroundColor(java.awt.Color.DARK_GRAY);
				celulaCabecalho.setHorizontalAlignment(Element.ALIGN_CENTER);
				celulaCabecalho.setVerticalAlignment(Element.ALIGN_MIDDLE);
				celulaCabecalho.setPadding(5f); // espaçamento interno
				tabela.addCell(celulaCabecalho);
			}

			// Cores de backgroud para identificação rápida
			java.awt.Color corZerado = new java.awt.Color(220, 53, 69);
			java.awt.Color corRepor = new java.awt.Color(255, 193, 7);

			// estilo de fonte para os dados e alertas
			Font fonteNormal = new Font(Font.HELVETICA, 9, Font.NORMAL);
			Font fonteAlertaBranca = new Font(Font.HELVETICA, 9, Font.BOLD, java.awt.Color.WHITE);
			Font fonteAlertaEscura = new Font(Font.HELVETICA, 9, Font.BOLD, java.awt.Color.WHITE);

			// Dados da tabela
			while (rs.next()) {
				// Capturar dados para pesonalizar alertas
				int quantidade = rs.getInt("quantidade");
				int estoqueMinimo = rs.getInt("estoqueMinimo");

				tabela.addCell(new Paragraph(rs.getString("codigoBarras"), fonteNormal));
				tabela.addCell(new Paragraph(rs.getString("descricao"), fonteNormal));
				tabela.addCell(new Paragraph(rs.getString("categoria"), fonteNormal));
				tabela.addCell(new Paragraph(rs.getString("fornecedor"), fonteNormal));
				// String.format ("%.2f") converte para String e formata 2 casas
				tabela.addCell(new Paragraph(String.format("%.2f", rs.getDouble("precoCusto"), fonteNormal)));
				tabela.addCell(new Paragraph(String.format("%.2f", rs.getDouble("precoVenda"), fonteNormal)));

				// Lógica para mudar a formatação da célula se estoque zerado ou menor que o
				// minímo
				PdfPCell cellQtde;

				if (quantidade == 0) {
					cellQtde = new PdfPCell(new Paragraph(quantidade + " ( Zerado ) ", fonteAlertaBranca));
					cellQtde.setBackgroundColor(corZerado);
				} else if (quantidade <= estoqueMinimo) {
					cellQtde = new PdfPCell(new Paragraph(quantidade + " ( Repor ) ", fonteAlertaEscura));
					cellQtde.setBackgroundColor(corRepor);
				} else {
					cellQtde = new PdfPCell(new Paragraph(String.valueOf(quantidade), fonteNormal));

				}

				cellQtde.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tabela.addCell(cellQtde);

				// tabela.addCell(new Paragraph(String.valueOf(rs.getInt("quantidade"))));
				tabela.addCell(new Paragraph(String.valueOf(rs.getInt("estoqueMinimo")), fonteNormal));
			}

			// Adicionar a tabela ao documento
			documento.add(tabela);

			// Fechar o documento (fim da formatação)
			documento.close();

			// fechar os recursos do banco de dados
			rs.close();
			stmt.close();
			con.close();

			// Abrir o documento (pdf) no leitor padrão
			File arquivo = new File(caminho);
			Desktop.getDesktop().open(arquivo);

		} catch (Exception e) {
			System.out.println(e);
		}
	}// ==============================================

	// =========================================
	// Dashboard
	// =========================================

	// Card Quantidade total de produtos =======
	public int contarProdutos() {
		try {
			// comando sql
			String sql = """

					select count(*) as total from produtos
					""";
			// Abrir a conexão com o banco
			Connection con = database.conectar();

			// Preparar a conexão
			PreparedStatement stmt = con.prepareStatement(sql);

			// executar o comando e obter o resultado do banco
			ResultSet rs = stmt.executeQuery();

			int total = 0;
			if (rs.next()) {
				total = rs.getInt("total");
			}

			// Encerrar os recursos JDBC
			rs.close();
			stmt.close();
			con.close();

			// retornar o total
			return total;

		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}
	// =========================================

	// =========================================
	// Dashboard
	// =========================================

	// Card Produtos com estoque baixo =======
	public int contarEstoqueBaixo() {
		try {
			// comando sql
			String sql = """
					select count(*) as total
					from produtos
					where quantidade <= estoqueMinimo and quantidade != 0;
						""";
			// Abrir a conexão com o banco
			Connection con = database.conectar();

			// Preparar a conexão
			PreparedStatement stmt = con.prepareStatement(sql);

			// executar o comando e obter o resultado do banco
			ResultSet rs = stmt.executeQuery();

			int total = 0;
			if (rs.next()) {
				total = rs.getInt("total");
			}

			// Encerrar os recursos JDBC
			rs.close();
			stmt.close();
			con.close();

			// retornar o total
			return total;

		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}
	// =========================================

	// Card Produtos sem estoque =======
	public int SemEstoque() {
		try {
			// comando sql
			String sql = """
					select count(*) as total
					from produtos
					where quantidade <= 0;
						""";
			// Abrir a conexão com o banco
			Connection con = database.conectar();

			// Preparar a conexão
			PreparedStatement stmt = con.prepareStatement(sql);

			// executar o comando e obter o resultado do banco
			ResultSet rs = stmt.executeQuery();

			int total = 0;
			if (rs.next()) {
				total = rs.getInt("total");
			}

			// Encerrar os recursos JDBC
			rs.close();
			stmt.close();
			con.close();

			// retornar o total
			return total;

		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}
	// =========================================
	

}

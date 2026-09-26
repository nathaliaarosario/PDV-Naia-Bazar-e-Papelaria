package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//importar o FornecedorController (combo box)
import controller.FornecedorController;
//importar o ProdutoController (CRUD produtos)
import controller.ProdutoController;
//importar os modelos de dados
import model.Fornecedor;
import model.Produto;
import utils.Validador;

public class frmProduto extends JDialog {

	// Criar os objetos controller (fornecedores e produtos)
	FornecedorController controllerFornecedor = new FornecedorController();
	ProdutoController controllerProduto = new ProdutoController();

	// Criar os objetos fornecedor e produto
	Fornecedor fornecedor = new Fornecedor();
	Produto produto = new Produto();

	private static final long serialVersionUID = 1L;
	private JTextField txtIDProduto;
	private JTextField txtBarcode;
	private JTextField txtDescricao;
	private JTextField txtIDFornecedor;
	private JTextField txtPrecoCusto;
	private JTextField txtPrecoVenda;
	private JTextField txtQuantidade;
	private JLabel lblQuantidade;
	private JTextField txtEstoque;
	private JTextField txtCategoria;
	private JComboBox<String> cboFornecedor;
	private JButton btnAdicionarProduto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmProduto dialog = new frmProduto();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public frmProduto() {
		getContentPane().setForeground(SystemColor.controlShadow);
		getContentPane().setBackground(SystemColor.menu);
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(frmProduto.class.getResource("/img/material-escolar (1).png")));
		setModal(true);
		setResizable(false);
		setTitle("Produtos");
		setBounds(100, 100, 711, 592);
		getContentPane().setLayout(null);

		JLabel lblID = new JLabel("ID");
		lblID.setForeground(SystemColor.desktop);
		lblID.setBounds(73, 48, 22, 14);
		getContentPane().add(lblID);

		txtIDProduto = new JTextField();
		txtIDProduto.setEnabled(false);
		txtIDProduto.setBounds(73, 62, 142, 20);
		getContentPane().add(txtIDProduto);
		txtIDProduto.setColumns(10);

		JLabel lblCodigo = new JLabel("");
		lblCodigo.setIcon(new ImageIcon(frmProduto.class.getResource("/img/barcode.png")));
		lblCodigo.setBounds(579, 48, 64, 45);
		getContentPane().add(lblCodigo);

		txtBarcode = new JTextField();

		// ==========================================
		// Evento relacionado ao código de barras
		// CRUD READ
		// ==========================================
		txtBarcode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// se a tecla enter for pressionada
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					// Capturar o código de basrras
					String barcode = txtBarcode.getText();

					// Instanciar o produto executando a busca atráves do controller
					Produto produto = controllerProduto.buscarCodigoBarras(barcode);

					// Se existir um produto cadastrado
					if (produto != null) {
						// setar os campos do formulário
						txtIDProduto.setText(String.valueOf(produto.getIdProduto()));
						txtDescricao.setText(produto.getDescricao());
						txtCategoria.setText(produto.getCategoria());
						txtPrecoCusto.setText(String.valueOf(produto.getPrecoCusto()));
						txtPrecoVenda.setText(String.valueOf(produto.getPrecoVenda()));
						txtQuantidade.setText(String.valueOf(produto.getQuantidade()));
						txtEstoque.setText(String.valueOf(produto.getEstoqueMinimo()));

						txtIDFornecedor.setText(String.valueOf(produto.getIdFornecedor()));

						// Localizar e selecionar o fornecedor a partir do ID do fornecedor e setar o
						// jcombox
						for (int i = 0; i < cboFornecedor.getItemCount(); i++) {
							String item = (String) cboFornecedor.getItemAt(i);
							if (item.startsWith(produto.getIdFornecedor() + " - ")) {
								cboFornecedor.setSelectedIndex(i);

							}

						}

						// Desabilitar o botão adicionar
						btnAdicionarProduto.setEnabled(false);

					} else {
						int resposta = JOptionPane.showConfirmDialog(null,
								"Produto não cadastrado.\nDeseja cadastrar esse produto?", "Aviso!",
								JOptionPane.YES_NO_OPTION);
						if (resposta == JOptionPane.NO_OPTION) {
							limparCampos();
						} else {
							txtDescricao.requestFocus();
						}

					}

				}

			}
		});// ==========================================
		txtBarcode.setColumns(10);
		txtBarcode.setBounds(252, 62, 321, 20);
		getContentPane().add(txtBarcode);
		// Validador
		txtBarcode.setDocument(new Validador(20, "inteiro"));

		JLabel lblProduto = new JLabel("Produto");
		lblProduto.setForeground(SystemColor.desktop);
		lblProduto.setBounds(73, 112, 46, 14);
		getContentPane().add(lblProduto);

		txtDescricao = new JTextField();
		txtDescricao.setBounds(73, 137, 520, 20);
		getContentPane().add(txtDescricao);
		txtDescricao.setColumns(10);

		JLabel lblFornecedor = new JLabel("Fornecedor");
		lblFornecedor.setForeground(SystemColor.desktop);
		lblFornecedor.setBounds(76, 259, 88, 14);
		getContentPane().add(lblFornecedor);

		JLabel lblIdFornecedor = new JLabel("ID Fornecedor");
		lblIdFornecedor.setForeground(SystemColor.desktop);
		lblIdFornecedor.setBounds(423, 259, 88, 14);
		getContentPane().add(lblIdFornecedor);

		txtIDFornecedor = new JTextField();
		txtIDFornecedor.setEnabled(false);
		txtIDFornecedor.setColumns(10);
		txtIDFornecedor.setBounds(423, 281, 220, 20);
		getContentPane().add(txtIDFornecedor);

		JLabel lblPrecoCusto = new JLabel("Preço de Custo");
		lblPrecoCusto.setForeground(SystemColor.desktop);
		lblPrecoCusto.setBounds(76, 341, 88, 14);
		getContentPane().add(lblPrecoCusto);

		txtPrecoCusto = new JTextField();
		txtPrecoCusto.setBounds(78, 356, 305, 20);
		getContentPane().add(txtPrecoCusto);
		txtPrecoCusto.setColumns(10);
		// Validador
		txtPrecoCusto.setDocument(new Validador(10, "decimal"));

		JLabel lblPrecoVenda = new JLabel("Preço de Venda");
		lblPrecoVenda.setForeground(SystemColor.desktop);
		lblPrecoVenda.setBounds(423, 341, 98, 14);
		getContentPane().add(lblPrecoVenda);

		txtPrecoVenda = new JTextField();
		txtPrecoVenda.setColumns(10);
		txtPrecoVenda.setBounds(423, 356, 220, 20);
		getContentPane().add(txtPrecoVenda);
		// Validador
		txtPrecoVenda.setDocument(new Validador(10, "decimal"));

		lblQuantidade = new JLabel("Quantidade");
		lblQuantidade.setForeground(SystemColor.desktop);
		lblQuantidade.setBounds(76, 404, 88, 14);
		getContentPane().add(lblQuantidade);

		txtQuantidade = new JTextField();
		txtQuantidade.setColumns(10);
		txtQuantidade.setBounds(78, 422, 305, 20);
		getContentPane().add(txtQuantidade);
		// Validador
		txtQuantidade.setDocument(new Validador(5, "inteiro"));

		JLabel lblEstoqueMnimo = new JLabel("Estoque Mínimo");
		lblEstoqueMnimo.setForeground(SystemColor.desktop);
		lblEstoqueMnimo.setBounds(423, 404, 98, 14);
		getContentPane().add(lblEstoqueMnimo);

		txtEstoque = new JTextField();
		txtEstoque.setColumns(10);
		txtEstoque.setBounds(423, 422, 220, 20);
		getContentPane().add(txtEstoque);
		// Validador
		txtEstoque.setDocument(new Validador(5, "inteiro"));

		btnAdicionarProduto = new JButton("Adicionar");

		// ==================================================================
		// CRUD - Botão adicionar ==========================================
		// ==================================================================
		btnAdicionarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtCategoria.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Preencha o Nome do Produto");
				} else if (txtDescricao.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Preencha a categoria do Produto");
				} else if (txtPrecoCusto.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Preencha o preço de custo do Produto");
				} else if (txtPrecoVenda.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Preencha o preço de venda do Produto");
				} else if (txtQuantidade.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Preencha a quantidade mínima do Produto");
				} else if (txtEstoque.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Preencha a quantidade de estoque do Produto");
				} else {
					try {
						// Transferir os dados da tela para o objeto
						produto.setCodigoBarras(txtBarcode.getText());
						produto.setDescricao(txtDescricao.getText());
						produto.setCategoria(txtCategoria.getText());
						produto.setPrecoCusto(Double.parseDouble(txtPrecoCusto.getText().replace(",", ".")));
						produto.setPrecoVenda(Double.parseDouble(txtPrecoVenda.getText().replace(",", ".")));
						produto.setQuantidade(Integer.parseInt(txtQuantidade.getText().replace(",", ".")));
						produto.setEstoqueMinimo(Integer.parseInt(txtEstoque.getText().replace(",", ".")));
						produto.setIdFornecedor(Integer.parseInt(txtIDFornecedor.getText()));

						// enviar o objeto para o controller (com confirmação)
						boolean sucesso = controllerProduto.adicionar(produto);
						if (sucesso == true) {
							JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso.");

							limparCampos();

						} else {
							JOptionPane.showMessageDialog(null,
									"Não foi possível adicionar o produto.\n Código de barras já cadastrado.");
							txtBarcode.setText(null);
							txtBarcode.requestFocus();
						}

					} catch (Exception e2) {
						System.out.println(e2);
					}

				}

			}
		}); // ========================================================
		btnAdicionarProduto.setBounds(105, 476, 89, 23);
		getContentPane().add(btnAdicionarProduto);

		JButton btnEditarProduto = new JButton("Editar");

		// ===========================================================
		// CRUD Update - editar produtos
		// ===========================================================
		btnEditarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Validação de campos obrigatórios
				if (txtDescricao.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Preencha o nome do produto");
					txtBarcode.requestFocus();
				} else if (txtBarcode.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Preencha o código de barras do produto");
				} else {
					// Lógica principal se os campos obrigatórios estiverem preenchidos
					// Tranferir os dados da tela para o Model
					produto.setCodigoBarras(txtBarcode.getText());
					produto.setDescricao(txtDescricao.getText());
					produto.setCategoria(txtCategoria.getText());
					produto.setPrecoCusto(Double.parseDouble(txtPrecoCusto.getText().replace(",", ".")));
					produto.setPrecoVenda(Double.parseDouble(txtPrecoVenda.getText().replace(",", ".")));
					produto.setQuantidade(Integer.parseInt(txtQuantidade.getText().replace(",", ".")));
					produto.setEstoqueMinimo(Integer.parseInt(txtEstoque.getText().replace(",", ".")));
					produto.setIdFornecedor(Integer.parseInt(txtIDFornecedor.getText()));

					produto.setIdProduto(Integer.parseInt(txtIDProduto.getText()));

					// enviar o objeto para o controller
					controllerProduto.editarProduto(produto);

					// Mensagem para o usuario
					JOptionPane.showMessageDialog(null, "Dados do produto alterados com sucesso");

					// limpar os campos
					limparCampos();

				}
			}
		});// ============================================================

		btnEditarProduto.setBounds(245, 476, 89, 23);
		getContentPane().add(btnEditarProduto);

		JButton btnExcluirProduto = new JButton("Excluir");
		btnExcluirProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int idProdutos;

				// Validação
				if (txtBarcode.getText().isBlank()) {

					JOptionPane.showMessageDialog(null, "Digite o código de barras");

					txtBarcode.requestFocus();

					return; // importante: para a execução aqui

				} else {

					// Capturar o ID do produto
					idProdutos = Integer.parseInt(txtIDProduto.getText());
				}

				// Confirmação de exclusão
				int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente apagar\neste Produto?", "Atenção!",
						JOptionPane.YES_NO_OPTION);

				if (resposta == JOptionPane.YES_OPTION) {

					// Excluir através do controller
					controllerProduto.excluir(idProdutos);

					// Limpar os campos
					limparCampos();

					// Mensagem para o usuário
					JOptionPane.showMessageDialog(null, "Produto apagado com sucesso.");
				}
			}
		});
		btnExcluirProduto.setBounds(387, 476, 89, 23);
		getContentPane().add(btnExcluirProduto);

		JButton btnRelatorioProduto = new JButton("Relatório");

		// ===================================================
		// Relatório personalizado de produtos ==============
		// ===================================================
		btnRelatorioProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controllerProduto.gerarRelatorioproduto();
			}
		}); // =================================================

		btnRelatorioProduto.setBounds(530, 476, 89, 23);
		getContentPane().add(btnRelatorioProduto);

		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setForeground(SystemColor.desktop);
		lblCategoria.setBounds(76, 179, 57, 20);
		getContentPane().add(lblCategoria);

		txtCategoria = new JTextField();
		txtCategoria.setColumns(10);
		txtCategoria.setBounds(76, 203, 567, 20);
		getContentPane().add(txtCategoria);

		cboFornecedor = new JComboBox<>();

		cboFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Evento que seleciona um ítem da lista (Id do fornecedor) =======
				cboFornecedor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Obter o ítem selecionado do comobobox
						String selecionado = (String) cboFornecedor.getSelectedItem();

						// se foi selecionado o ítem da lista
						if (selecionado != null && !selecionado.equals("Selecione")) {
							// separar o id do nome (índice [0] do vetor)
							String id = selecionado.split(" - ")[0];
							// setar (preencher) o id do fornecedor
							txtIDFornecedor.setText(id);
						} else {
							// nenhum fornecedor selecionado
							txtIDFornecedor.setText("");
						}
					}
				});
				// ================================================================

			}
		});
		cboFornecedor.setBounds(76, 280, 307, 22);
		getContentPane().add(cboFornecedor);

		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(new Color(89, 13, 20));
		panel.setBounds(0, 0, 63, 553);
		getContentPane().add(panel);

		JLabel lblProduto_1 = new JLabel("Produto");
		lblProduto_1.setForeground(SystemColor.desktop);
		lblProduto_1.setBounds(252, 48, 46, 14);
		getContentPane().add(lblProduto_1);

		JButton btnNewButton = new JButton("");

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setIcon(new ImageIcon(frmProduto.class.getResource("/img/lupa-arredondada.png")));
		btnNewButton.setBounds(596, 137, 38, 23);
		getContentPane().add(btnNewButton);

		// Centralizar
		setLocationRelativeTo(null);

		// Executar o método para Carregar o ID e nome dos fornecedores
		carregarFornecedores();

	}// fim do construtor

	// ==================================================
	// Preencher o combo box com a lista de fornecedores
	// ==================================================
	private void carregarFornecedores() {
		// limpar o combobox
		cboFornecedor.removeAllItems();
		// Opção padrão
		cboFornecedor.addItem("Selecione");
		// Executar o método para buscar a lista de fornecedores(array)
		ArrayList<Fornecedor> lista = controllerFornecedor.listarFornecedores();
		// Percorre o vetor e adicionar os Fornecedores ao combobox
		// Uso do laço foreach (simplificação do laço for)
		for (Fornecedor fornecedor : lista) {
			// Exibir o ID e o nome do fornecedor no combobox
			cboFornecedor.addItem(fornecedor.getIdFornecedor() + " - " + fornecedor.getNome());
		}

	}

	void limparCampos() {
		txtIDProduto.setText(null);
		txtDescricao.setText(null);
		txtBarcode.setText(null);
		txtBarcode.requestFocus(null);
		txtCategoria.setText(null);
		txtPrecoCusto.setText(null);
		txtPrecoVenda.setText(null);
		txtQuantidade.setText(null);
		txtEstoque.setText(null);
		txtIDFornecedor.setText(null);
		btnAdicionarProduto.setEnabled(true);
	}
}

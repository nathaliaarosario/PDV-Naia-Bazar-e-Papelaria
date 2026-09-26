package view;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

//importar o FornecedorController
import controller.FornecedorController;
//importar o modelo de dados
import model.Fornecedor;
import utils.Validador;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.SystemColor;

public class frmFornecedor extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField txtNome;
	private JTextField txtFone;
	private JTextField txtEmail;
	private JTextField txtID;
	private JTextField txtSiteFornecedor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmFornecedor dialog = new frmFornecedor();
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
	public frmFornecedor() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmFornecedor.class.getResource("/img/fornecedor.png")));
		setBackground(new Color(255, 255, 255));
		getContentPane().setBackground(SystemColor.menu);
		setResizable(false);
		setTitle("Fornecedores");
		setBounds(100, 100, 640, 480);

		// criar objeto controller

		// versão moderna de criar um objeto (sintaxe moderna)
		// controller = new FornecedorController();

		// versão tradicional
		FornecedorController controller = new FornecedorController();

		// Criar o objeto fornecedor
		Fornecedor fornecedor = new Fornecedor();

		// iniciar centralizado
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setForeground(new Color(0, 0, 0));
		lblNome.setBounds(52, 93, 46, 14);
		getContentPane().add(lblNome);

		JLabel lblEmail = new JLabel("Fone");
		lblEmail.setForeground(new Color(0, 0, 0));
		lblEmail.setBounds(52, 150, 46, 14);
		getContentPane().add(lblEmail);

		JLabel lblNewLabel_2 = new JLabel("E-mail");
		lblNewLabel_2.setForeground(new Color(0, 0, 0));
		lblNewLabel_2.setBounds(52, 204, 46, 14);
		getContentPane().add(lblNewLabel_2);

		txtNome = new JTextField();
		txtNome.setBounds(108, 90, 363, 22);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		// Validação do número máximo de caracteres
		txtNome.setDocument(new Validador(50));

		txtFone = new JTextField();
		txtFone.setBounds(108, 147, 208, 23);
		getContentPane().add(txtFone);
		txtFone.setColumns(10);
		// Validação do número máximo de caracteres
		txtFone.setDocument(new Validador(20));

		txtEmail = new JTextField();
		txtEmail.setBounds(108, 201, 459, 22);
		getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		// Validação do número máximo de caracteres
		txtEmail.setDocument(new Validador(50));

		JButton btnAdicionar = new JButton("Adicionar");

		// =======================================================
		// CRUD CREATE - Cadastrar Fornecedor ====================
		// =======================================================
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Validação de campos obrigatórios
				if (txtNome.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Preencha o nome do fornecedor");
					txtNome.requestFocus();
				} else if (txtFone.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Preencha o telefone do fornecedor");
				} else {
					// Lógica principal se os campos obrigatórios estiverem preenchidos
					// Inserir as informações no banco de dados
					try {
						// Transferir os dados da tela para o objeto
						fornecedor.setNome(txtNome.getText());
						fornecedor.setFone(txtFone.getText());
						fornecedor.setEmail(txtEmail.getText());
						fornecedor.setSite(txtSiteFornecedor.getText());
						// enviar o objeto para o controller
						controller.adicionar(fornecedor);
						// Mensagem de confirmação
						JOptionPane.showMessageDialog(null, "fornecedor adicionado com sucesso.");
						// limpar os campos
						limparCampos();

					} catch (Exception e2) {
						System.out.println(e2);
					}

				}
			}

		});
		// Fim - CRUD CREATE ======================================

		btnAdicionar.setBounds(69, 342, 89, 23);
		getContentPane().add(btnAdicionar);

		JButton btnEditar = new JButton("Editar");

		// ===========================================================
		// CRUD Update - editar fornecedor
		// ===========================================================
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Validação de campos obrigatórios
				if (txtNome.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Preencha o nome do fornecedor");
					txtNome.requestFocus();
				} else if (txtFone.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Preencha o telefone do fornecedor");
				} else {
					// Lógica principal se os campos obrigatórios estiverem preenchidos
					// Tranferir os dados da tela para o Model
					fornecedor.setIdFornecedor(Integer.parseInt(txtID.getText()));
					fornecedor.setNome(txtNome.getText());
					fornecedor.setFone(txtFone.getText());
					fornecedor.setEmail(txtEmail.getText());
					fornecedor.setSite(txtSiteFornecedor.getText());

					// Enviar o objeto para o controller
					controller.editarFornecedor(fornecedor);

					// Mensagem para o usuario
					JOptionPane.showMessageDialog(null, "Dados do fornecedor alterados com sucesso");

					// limpar os campos
					limparCampos();
				}
			}
		});//================================================
		btnEditar.setBounds(209, 342, 89, 23);
		getContentPane().add(btnEditar);

		JButton btnExcluir = new JButton("Excluir");

		// ===========================================================
		// CRUD Delete - Excluir Fornecedor
		// ===========================================================
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Validação
				if (txtNome.getText().isBlank()) {
					// Mensagem para o usuario
					JOptionPane.showMessageDialog(null, "Digite o nome do fornecedor");
					txtNome.requestFocus();
				} else {
					// capturar o id do fornecedor
					int idFornecedor = Integer.parseInt(txtID.getText());

					// Confirmação de exclusão
					int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir\n este fornecedor?",
							"Atenção!", JOptionPane.YES_OPTION);
					if (resposta == JOptionPane.YES_OPTION) {
						// exluir através do controller
						controller.excluir(idFornecedor);

						// limpar os campos
						limparCampos();

						// Mensagem para o usuario
						JOptionPane.showMessageDialog(null, "Fornecedor exluido com sucesso.");

					}
				}
			}
		});
		// ===========================================================
		btnExcluir.setBounds(334, 342, 89, 23);
		getContentPane().add(btnExcluir);

		JButton btnRelatorio = new JButton("Relatório");

		// ========================================================
		//Gerar relatório de fornecedores 
		// =========================================================
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.gerarRelatorioFornecedores();
			}
		});// FIM relatório de fornecedores ========

		btnRelatorio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorio.setBounds(463, 342, 89, 23);
		getContentPane().add(btnRelatorio);

		JLabel lblID = new JLabel("ID");
		lblID.setForeground(new Color(0, 0, 0));
		lblID.setBounds(52, 46, 46, 14);
		getContentPane().add(lblID);

		txtID = new JTextField();
		txtID.setEnabled(false);
		txtID.setBounds(108, 43, 86, 22);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		JButton btnBuscar = new JButton("Buscar");

		// ===========================================================
		// CRUD Read - Buscar Fornecedor
		// ===========================================================
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Validação
				if (txtNome.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Informe o nome do fornecedor");
					txtNome.requestFocus();
				} else {
					// Lógica principal

					// Capturar por nome para busca
					String nome = txtNome.getText();

					// Instanciar (criar) o forncedor execultando a busca através do controller
					Fornecedor fornecedor = controller.buscar(nome);

					// se existir um fornecedor cadastrado
					if (fornecedor != null) {
						// setar os campos do formulário
						txtID.setText(String.valueOf(fornecedor.getIdFornecedor()));
						txtNome.setText(fornecedor.getNome());
						txtFone.setText(fornecedor.getFone());
						txtEmail.setText(fornecedor.getEmail());
						txtSiteFornecedor.setText(fornecedor.getSite());
					}

					else {
						JOptionPane.showMessageDialog(null, "Fornecedor não cadastrado");
						limparCampos();
					}

				}
			}
		});
		// Fim CRUD Read

		btnBuscar.setBounds(478, 89, 89, 23);
		getContentPane().add(btnBuscar);

		JLabel lblSite = new JLabel("Site");
		lblSite.setForeground(new Color(0, 0, 0));
		lblSite.setBounds(52, 253, 46, 14);
		getContentPane().add(lblSite);

		txtSiteFornecedor = new JTextField();
		txtSiteFornecedor.setBounds(108, 250, 363, 20);
		getContentPane().add(txtSiteFornecedor);
		txtSiteFornecedor.setColumns(10);

		JButton btnAcessar = new JButton("Acessar");

		// Acessar link externo =========================
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String site = txtSiteFornecedor.getText();
				// Executar o método passando o site
				link(site);

			}
		});
		// ==============================================

		btnAcessar.setBounds(478, 249, 89, 23);
		getContentPane().add(btnAcessar);

	} // fim do construtor

	// ===========================================================
	// Limpar campos
	// ===========================================================
	void limparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtFone.setText(null);
		txtEmail.setText(null);
		txtSiteFornecedor.setText(null);
		txtNome.requestFocus(); // posicionar o cursor no nome
	}

	// ==================================================
	// Abrir um link no navegador =======================
	// ==================================================
	private void link(String url) {
		// a linha abaixo obtem o desktop do cliente
		Desktop desktop = Desktop.getDesktop();
		// uso do try catch(tratamento de exceções)
		try {
			// objeto uri para acessar os métodos necessários para estabelecer uma conexão
			// com a url (link)
			URI uri = new URI(url);
			// abrir o link no navegador padrão do cliente
			desktop.browse(uri);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}

package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;

import controller.ProdutoController;
//importar a classe Database do pacote database
import database.Database;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblData;

	// Criação de um objeto para lidar com a conexão
	Database db = new Database();

	// Criação de um objeto para acessar o controller e atualizar o dashboard
	ProdutoController controllerProduto = new ProdutoController();

	private JLabel lblStatus;
	private JLabel lblMysql;
	private JLabel lblDashQuantidade;
	private JLabel lblDashRepor;
	private JLabel lblDashSemEstoque;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		// Uso da biblioteca flatlaf (swing moderno)
		FlatLightLaf.setup();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/img/Logo.png")));
		setResizable(false);
		setTitle("Naia Bazar e Papelaria");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panelLateral = new JPanel();
		panelLateral.setBackground(new Color(89, 13, 20));
		panelLateral.setBounds(0, 0, 197, 527);
		contentPane.add(panelLateral);
		panelLateral.setLayout(null);

		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(10, 11, 64, 64);
		panelLateral.add(lblLogo);
		lblLogo.setIcon(new ImageIcon(Main.class.getResource("/img/Logo.png")));

		JLabel lblJAPDV = new JLabel("Naia Papelaria");
		lblJAPDV.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblJAPDV.setForeground(new Color(255, 255, 255));
		lblJAPDV.setBounds(84, 28, 97, 23);
		panelLateral.add(lblJAPDV);

		JLabel lblSistema = new JLabel("Sistema de PDV");
		lblSistema.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSistema.setForeground(new Color(255, 255, 255));
		lblSistema.setBounds(86, 52, 91, 14);
		panelLateral.add(lblSistema);

		JButton btnFornecedores = new JButton("Fornecedores");
		btnFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmFornecedor fornecedor = new frmFornecedor();
				fornecedor.setVisible(true);
			}
		});
		btnFornecedores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFornecedores.setBorderPainted(false);
		btnFornecedores.setForeground(new Color(255, 255, 255));
		btnFornecedores.setBackground(new Color(104, 15, 23));
		btnFornecedores.setIconTextGap(12);
		btnFornecedores.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnFornecedores.setHorizontalAlignment(SwingConstants.LEFT);
		btnFornecedores.setIcon(new ImageIcon(Main.class.getResource("/img/fornecedor.png")));
		btnFornecedores.setBounds(10, 110, 177, 44);
		panelLateral.add(btnFornecedores);

		JButton btnProdutos = new JButton("Produtos");
		btnProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmProduto produto = new frmProduto();
				produto.setVisible(true);
				// Atualizar o dashboard depois que a janela produtos for fechada(JDialog da
				// janela de produtos (frmProduto) quando
				// ativamos o modal (modal true) o Java "espera" o fechamento da janela para
				// encerrar o processamento)
				atualizarDashboard();
			}
		});
		btnProdutos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnProdutos.setBorderPainted(false);
		btnProdutos.setIconTextGap(12);
		btnProdutos.setIcon(new ImageIcon(Main.class.getResource("/img/material-escolar (1).png")));
		btnProdutos.setHorizontalAlignment(SwingConstants.LEFT);
		btnProdutos.setForeground(Color.WHITE);
		btnProdutos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnProdutos.setBackground(new Color(104, 15, 23));
		btnProdutos.setBounds(10, 165, 177, 44);
		panelLateral.add(btnProdutos);

		JButton btnPDV = new JButton("PDV");
		btnPDV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPDV.setBorderPainted(false);
		btnPDV.setIconTextGap(12);
		btnPDV.setIcon(new ImageIcon(Main.class.getResource("/img/bolsa-de-compras.png")));
		btnPDV.setHorizontalAlignment(SwingConstants.LEFT);
		btnPDV.setForeground(Color.WHITE);
		btnPDV.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPDV.setBackground(new Color(104, 15, 23));
		btnPDV.setBounds(10, 277, 177, 44);
		panelLateral.add(btnPDV);

		JButton btnVendas = new JButton("Vendas");
		btnVendas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVendas.setBorderPainted(false);
		btnVendas.setIconTextGap(12);
		btnVendas.setIcon(new ImageIcon(Main.class.getResource("/img/pos.png")));
		btnVendas.setHorizontalAlignment(SwingConstants.LEFT);
		btnVendas.setForeground(Color.WHITE);
		btnVendas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnVendas.setBackground(new Color(104, 15, 23));
		btnVendas.setBounds(10, 332, 177, 44);
		panelLateral.add(btnVendas);

		JButton btnSobre = new JButton("Sobre");
		btnSobre.addActionListener(new ActionListener() {
			// Ativar a tela sobre
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.setBorderPainted(false);
		btnSobre.setIconTextGap(12);
		btnSobre.setIcon(new ImageIcon(Main.class.getResource("/img/informacoes.png")));
		btnSobre.setHorizontalAlignment(SwingConstants.LEFT);
		btnSobre.setForeground(Color.WHITE);
		btnSobre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSobre.setBackground(new Color(104, 15, 23));
		btnSobre.setBounds(10, 467, 177, 39);
		panelLateral.add(btnSobre);

		JLabel lblSeparador = new JLabel("------------------------");
		lblSeparador.setBackground(UIManager.getColor("Button.disabledForeground"));
		lblSeparador.setForeground(SystemColor.textInactiveText);
		lblSeparador.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSeparador.setBounds(10, 442, 177, 14);
		panelLateral.add(lblSeparador);

		JButton btnClientes = new JButton("Clientes");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmCliente cliente = new frmCliente();
				cliente.setVisible(true);
			}
		});
		btnClientes.setIconTextGap(12);
		btnClientes.setIcon(new ImageIcon(Main.class.getResource("/img/clientes.png")));
		btnClientes.setHorizontalAlignment(SwingConstants.LEFT);
		btnClientes.setForeground(Color.WHITE);
		btnClientes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setBorderPainted(false);
		btnClientes.setBackground(new Color(104, 15, 23));
		btnClientes.setBounds(10, 220, 177, 46);
		panelLateral.add(btnClientes);

		JButton btnSair = new JButton("Sair");
		btnSair.setBounds(10, 385, 177, 46);
		panelLateral.add(btnSair);
		btnSair.addActionListener(new ActionListener() {
			// Quando clicar no botão
			public void actionPerformed(ActionEvent e) {
				int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente sair do sistema?",
						"Confirmar saída", JOptionPane.YES_NO_OPTION);
				// apoio ao entendimento da lógica
				System.out.println(resposta);
				if (resposta == 0) {
					System.exit(0); // encerra o sistema
				}
			}
		});
		btnSair.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSair.setBorderPainted(false);
		btnSair.setIconTextGap(12);
		btnSair.setIcon(new ImageIcon(Main.class.getResource("/img/sair.png")));
		btnSair.setHorizontalAlignment(SwingConstants.LEFT);
		btnSair.setForeground(Color.WHITE);
		btnSair.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSair.setBackground(new Color(104, 15, 23));

		JPanel panelTabela = new JPanel();
		panelTabela.setBackground(SystemColor.window);
		panelTabela.setBounds(207, 314, 567, 201);
		contentPane.add(panelTabela);
		panelTabela.setLayout(null);

		JPanel panelCard1 = new JPanel();
		panelCard1.setBounds(207, 54, 180, 115);
		contentPane.add(panelCard1);
		panelCard1.setLayout(null);
		panelCard1.setBackground(SystemColor.window);

		lblDashQuantidade = new JLabel("0");
		lblDashQuantidade.setForeground(new Color(0, 128, 255));
		lblDashQuantidade.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDashQuantidade.setBounds(45, 47, 46, 14);
		panelCard1.add(lblDashQuantidade);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Main.class.getResource("/img/sacolas-de-compras.png")));
		lblNewLabel.setBounds(10, 11, 32, 32);
		panelCard1.add(lblNewLabel);

		JLabel lblProdutos = new JLabel("Produtos");
		lblProdutos.setForeground(new Color(0, 128, 255));
		lblProdutos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblProdutos.setBounds(52, 17, 113, 19);
		panelCard1.add(lblProdutos);

		JLabel lblTotalDeProdutos = new JLabel("Total de produtos ");
		lblTotalDeProdutos.setForeground(new Color(0, 0, 0));
		lblTotalDeProdutos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTotalDeProdutos.setBounds(25, 72, 130, 19);
		panelCard1.add(lblTotalDeProdutos);

		JLabel lblNewLabel_1 = new JLabel("cadastrados");
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(26, 90, 80, 14);
		panelCard1.add(lblNewLabel_1);

		JLabel lblVelocimetro = new JLabel("");
		lblVelocimetro.setIcon(new ImageIcon(Main.class.getResource("/img/dash.png")));
		lblVelocimetro.setBounds(222, 11, 32, 32);
		contentPane.add(lblVelocimetro);

		JLabel lblDashboard = new JLabel("Dashboard");
		lblDashboard.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDashboard.setBounds(266, 11, 109, 32);
		contentPane.add(lblDashboard);

		JPanel panelCard2 = new JPanel();
		panelCard2.setLayout(null);
		panelCard2.setBackground(SystemColor.window);
		panelCard2.setBounds(401, 54, 180, 115);
		contentPane.add(panelCard2);

		JLabel lblEstoqueBaixo = new JLabel("Estoque Baixo");
		lblEstoqueBaixo.setForeground(new Color(255, 128, 0));
		lblEstoqueBaixo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEstoqueBaixo.setBounds(57, 18, 113, 19);
		panelCard2.add(lblEstoqueBaixo);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Main.class.getResource("/img/alerta.png")));
		lblNewLabel_2.setBounds(10, 11, 32, 32);
		panelCard2.add(lblNewLabel_2);

		lblDashRepor = new JLabel("0");
		lblDashRepor.setForeground(new Color(255, 128, 0));
		lblDashRepor.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDashRepor.setBounds(42, 48, 46, 14);
		panelCard2.add(lblDashRepor);

		JLabel lblTotalDeProdutos_1 = new JLabel("Produtos com estoque ");
		lblTotalDeProdutos_1.setForeground(new Color(0, 0, 0));
		lblTotalDeProdutos_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTotalDeProdutos_1.setBounds(28, 73, 129, 19);
		panelCard2.add(lblTotalDeProdutos_1);

		JLabel lblNewLabel_1_1 = new JLabel("abaixo do minímo");
		lblNewLabel_1_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(30, 90, 113, 14);
		panelCard2.add(lblNewLabel_1_1);

		JPanel panelCard3 = new JPanel();
		panelCard3.setLayout(null);
		panelCard3.setBackground(SystemColor.window);
		panelCard3.setBounds(594, 54, 180, 115);
		contentPane.add(panelCard3);

		JLabel lblNewLabel_2_1 = new JLabel("");
		lblNewLabel_2_1.setIcon(new ImageIcon(Main.class.getResource("/img/sem_estoque.png")));
		lblNewLabel_2_1.setBounds(10, 11, 32, 32);
		panelCard3.add(lblNewLabel_2_1);

		JLabel lblEstoqueBaixo_1 = new JLabel("Estoque Baixo");
		lblEstoqueBaixo_1.setForeground(new Color(213, 0, 0));
		lblEstoqueBaixo_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEstoqueBaixo_1.setBounds(57, 18, 113, 19);
		panelCard3.add(lblEstoqueBaixo_1);

		lblDashSemEstoque = new JLabel("9");
		lblDashSemEstoque.setForeground(new Color(213, 0, 0));
		lblDashSemEstoque.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDashSemEstoque.setBounds(42, 48, 46, 14);
		panelCard3.add(lblDashSemEstoque);

		JLabel lblTotalDeProdutos_1_1 = new JLabel("Produtos sem estoque ");
		lblTotalDeProdutos_1_1.setForeground(Color.BLACK);
		lblTotalDeProdutos_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTotalDeProdutos_1_1.setBounds(28, 85, 129, 19);
		panelCard3.add(lblTotalDeProdutos_1_1);

		JPanel panelCard4 = new JPanel();
		panelCard4.setLayout(null);
		panelCard4.setBackground(SystemColor.window);
		panelCard4.setBounds(207, 185, 180, 115);
		contentPane.add(panelCard4);

		JPanel panelCard5 = new JPanel();
		panelCard5.setLayout(null);
		panelCard5.setBackground(SystemColor.window);
		panelCard5.setBounds(401, 185, 180, 115);
		contentPane.add(panelCard5);

		JPanel panelCard6 = new JPanel();
		panelCard6.setLayout(null);
		panelCard6.setBackground(SystemColor.window);
		panelCard6.setBounds(594, 185, 180, 115);
		contentPane.add(panelCard6);

		JLabel lblCalendario = new JLabel("");
		lblCalendario.setIcon(new ImageIcon(Main.class.getResource("/img/calendario.png")));
		lblCalendario.setBounds(644, 15, 24, 24);
		contentPane.add(lblCalendario);

		lblData = new JLabel("");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblData.setBounds(678, 19, 90, 19);
		contentPane.add(lblData);

		JSeparator separatorInferior = new JSeparator();
		separatorInferior.setBounds(0, 526, 800, 2);
		contentPane.add(separatorInferior);

		JSeparator separatorSuperior = new JSeparator();
		separatorSuperior.setBounds(-16, -1, 800, 2);
		contentPane.add(separatorSuperior);

		JLabel lblDatabase = new JLabel("");
		lblDatabase.setBounds(10, 536, 16, 16);
		contentPane.add(lblDatabase);
		lblDatabase.setIcon(new ImageIcon(Main.class.getResource("/img/banco.png")));

		lblMysql = new JLabel("");
		lblMysql.setForeground(SystemColor.controlDkShadow);
		lblMysql.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMysql.setBounds(36, 537, 109, 14);
		contentPane.add(lblMysql);

		lblStatus = new JLabel("●");
		lblStatus.setForeground(Color.RED);
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStatus.setBounds(144, 535, 16, 16);
		contentPane.add(lblStatus);

		// iniciar centralizado
		setLocationRelativeTo(null);

		// atualizar a data
		atualizarData();

		// status do banco (mudar texto e cor da bolinha)
		if (db.testarConexao() == true) {
			System.out.println("Banco conectado");
			lblMysql.setText("MySQL Conectado");
			lblStatus.setForeground(Color.GREEN);
		} else {
			System.out.println("Erro na conexão");
			lblMysql.setText("MySQL Desconectado");
			lblStatus.setForeground(Color.RED);
		}

		// Atualizar o dashboard
		atualizarDashboard();

	}// FIM do construtor

	// Função (método) para atualizar a data do sistema
	private void atualizarData() {
		// obter a data do sistema operacional
		LocalDate hoje = LocalDate.now();
		// formatar a data dia/mês/ano(4 dígitos)
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		// alterar o texto de lblData
		lblData.setText(hoje.format(formato));
	}

	// Método(função) para atualizar o dashboard
	private void atualizarDashboard() {
		int totalProdutos = controllerProduto.contarProdutos();
		lblDashQuantidade.setText(String.valueOf(totalProdutos));

		// card produtos com estoque baixo
		int estoqueBaixo = controllerProduto.contarEstoqueBaixo();
		lblDashRepor.setText(String.valueOf(estoqueBaixo));

		// card produtos com estoque baixo
		int estoqueZerado = controllerProduto.SemEstoque();
		lblDashSemEstoque.setText(String.valueOf(estoqueZerado));

	}

}// FIM da classe Main (principal)

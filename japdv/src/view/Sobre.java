package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Sobre extends JDialog {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
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
	public Sobre() {
		setResizable(false);
		setModal(true);
		setTitle("Sobre");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblJAPDV = new JLabel("Naia Papelaria");
		lblJAPDV.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblJAPDV.setBounds(26, 21, 152, 26);
		getContentPane().add(lblJAPDV);
		
		JLabel lblDescricao = new JLabel("Sistema para gestão de estoque e PDV");
		lblDescricao.setBounds(26, 58, 279, 27);
		getContentPane().add(lblDescricao);
		
		JLabel lblAutor = new JLabel("Autor: Nathalia Alves Rosário");
		lblAutor.setBounds(26, 80, 188, 25);
		getContentPane().add(lblAutor);
		
		JLabel lblVersao = new JLabel("Versão 1.0");
		lblVersao.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblVersao.setBounds(26, 119, 97, 14);
		getContentPane().add(lblVersao);
		
		JLabel lblMit = new JLabel("");
		lblMit.setIcon(new ImageIcon(Sobre.class.getResource("/img/mit.png")));
		lblMit.setBounds(340, 21, 64, 64);
		getContentPane().add(lblMit);
		
		JLabel lblGithub = new JLabel("");
		lblGithub.setIcon(new ImageIcon(Sobre.class.getResource("/img/github.png")));
		lblGithub.setBounds(26, 180, 32, 32);
		getContentPane().add(lblGithub);
		
		JLabel lblRepositorio = new JLabel("https://github.com/nathaliaarosario");
		lblRepositorio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				link("https://github.com/nathaliaarosario");
			}
		});
		lblRepositorio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblRepositorio.setForeground(new Color(0, 0, 204));
		lblRepositorio.setBounds(70, 188, 208, 14);
		getContentPane().add(lblRepositorio);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//fechar apenas o sobre
				dispose();
			}
		});
		btnOk.setBounds(314, 205, 89, 23);
		getContentPane().add(btnOk);
		
		// centralizar
		setLocationRelativeTo(null);

	} // fim do construtor
	
	// Função(método) para abrir um link no navegador padrão
	private void link(String url) {
		// a linha abaixo obtem o desktop do cliente
		Desktop desktop = Desktop.getDesktop();
		//uso do try catch(tratamento de exceções)
		try {
			// objeto uri para acessar os métodos necessários para estabelecer uma conexão com a url (link)			
			URI uri = new URI(url);
			// abrir o link no navegador padrão do cliente
			desktop.browse(uri);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}

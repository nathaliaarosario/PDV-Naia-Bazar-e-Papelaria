package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
	// informações do banco de dados
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/NaiaPapelaria";
	private String user = "root";
	private String password = "";
	
	// método("função") para conectar o banco
	public Connection conectar() {
		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, user, password); //abre a conexão com o banco
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	//método para testar a conexão (status de conexão)
	public boolean testarConexao() {
		// alinha abaixo cria o objeto con que é responsável por estabelecer a conexão com o banco
		Connection con = conectar();
		
		//se não conseguir estabelecer a conexão
		if (con == null) {
			return false;
		}
		
		// na tentativa de fechar a conexão
		try {
			con.close(); //encerra a conexão após o teste
			return true; //retorno verdadeiro para Main
		} catch (Exception e) {
			// se não conseguir fechar a conexão
			System.out.println(e);
			return false;
		}
	}

}

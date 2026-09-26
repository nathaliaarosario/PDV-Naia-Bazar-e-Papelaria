package model;

public class Clientes {

	// modelo de dados da tabela fornecedor
	private int idClientes;
	private String nome;
	private String fone;
	private String email;
	private String CPF;

	// gerar getters e setters de forma automática

	public int getIDClientes() {
		return idClientes;
	}

	public void setIDClientes(int idClientes) {
		this.idClientes = idClientes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String CPF) {
		this.CPF = CPF;
	}

}

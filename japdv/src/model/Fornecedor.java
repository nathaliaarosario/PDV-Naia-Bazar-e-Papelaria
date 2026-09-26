package model;

public class Fornecedor {
	// modelo de dados da tabela fornecedor
	private int idFornecedor;
	private String nome;
	private String fone;
	private String email;
	private String site;

	// gerar getters e setters de forma automática
	// botão direito do mouse -> Souse -> generate getters e setters

	public int getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(int idFornecedor) {
		this.idFornecedor = idFornecedor;
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

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

}

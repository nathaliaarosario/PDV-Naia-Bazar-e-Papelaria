package utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class Validador extends PlainDocument {

	private static final long serialVersionUID = 1L;

	private int limite;
	private String tipo;

	// Construtor original
	// Aceita qualquer caractere, respeitando apenas o limite
	public Validador(int limite) {
		super();
		this.limite = limite;
		this.tipo = "texto";
	}

	// Novo construtor
	// Permite definir o tipo de conteúdo
	public Validador(int limite, String tipo) {
		super();
		this.limite = limite;
		this.tipo = tipo;
	}

	@Override
	public void insertString(int ofs, String str, AttributeSet a) throws BadLocationException {

		// Verificar se o texto é nulo
		if (str == null) {
			return;
		}

		// Verificar limite de caracteres
		if ((getLength() + str.length()) > limite) {
			return;
		}

		// Tipo texto
		if (tipo.equals("texto")) {

			super.insertString(ofs, str, a);
		}

		// Tipo inteiro
		else if (tipo.equals("inteiro")) {

			// Aceita somente números de 0 a 9
			if (str.matches("[0-9]+")) {
				super.insertString(ofs, str, a);
			}
		}

		// Tipo decimal
		else if (tipo.equals("decimal")) {

			// Texto atual do campo
			String textoAtual = getText(0, getLength());

			// Texto que será inserido
			String novoTexto = textoAtual.substring(0, ofs) + str + textoAtual.substring(ofs);

			// Aceita números
			// Permite uma vírgula ou um ponto decimal
			if (novoTexto.matches("[0-9]*([,.][0-9]*)?")) {
				super.insertString(ofs, str, a);
			}
		}
	}

}
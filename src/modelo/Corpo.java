package modelo;

import java.util.ArrayList;
import java.util.List;

import helpers.Constantes;

public class Corpo {
	private String nomeTabela;
	private TipoScriptEnum tipo;
	private List<String> colunas;
	private List<List<String>> registros;
	private boolean utilizaIdentity;
	private List<String> chavePrimaria;

	public Corpo(String nomeTabela, TipoScriptEnum tipo, boolean utilizaIdentity) {
		this.nomeTabela = nomeTabela;
		this.tipo = tipo;
		this.registros = new ArrayList<>();
		this.colunas = new ArrayList<>();
		this.utilizaIdentity = utilizaIdentity;
		this.chavePrimaria = new ArrayList<>();
	}

	public void adicionarRegistros(List<List<String>> registros) {
		this.registros.addAll(registros);
	}

	public void adicionarColunas(List<String> colunas) {
		this.colunas.addAll(colunas);
	}
	
	public void adicionarChavePrimaria(List<String> chavePrimaria) {
		this.chavePrimaria.addAll(chavePrimaria);
	}

	/**
	 * Método responsável por construir a clausula "WHERE" do script, removendo as colunas ignoradas.
	 * @param registro
	 * @param chavePrimaria
	 * @return String contendo a clausula WHERE.
	 */
	private String constuirClausulaWhere(List<String> registro, List<String> chavePrimaria) {
		StringBuilder clausulaWhere = new StringBuilder("WHERE" + " ");

		for (int i = 0; i < this.colunas.size(); i++) {
			if (i > 0) {
				clausulaWhere.append(" AND ");
			}

			clausulaWhere.append(colunas.get(i) + obterOperadorPorValor(registro.get(i)) + tratarValorColuna(registro.get(i)));
		}

		return clausulaWhere.toString();
	}
	
	/**
	 * Método responsável por retornar o tipo de operador dependendo do valor da coluna
	 * @param valor
	 * @return String contendo operador de comparação
	 */
	private String obterOperadorPorValor(String valor) {
		return "null".equalsIgnoreCase(valor) ? " IS " : " = ";
	}

	/**
	 * Método responsável por construir a clausula "IF (NOT) EXISTS" do script.
	 * Essa clausula SQL evita inserir valores já existentes ou deletar valores não existentes.
	 * @param clausulaWhere
	 * @param negar
	 * @return String contendo a clausula.
	 */
	private String construirChecagemDeRegistro(String clausulaWhere, boolean negar) {
		String checagemDeRegistro = "IF";

		checagemDeRegistro = checagemDeRegistro + (negar ? " NOT EXISTS" : " EXISTS ");
		checagemDeRegistro = checagemDeRegistro + "(SELECT 1 FROM " + this.nomeTabela + " " + clausulaWhere + ")";

		return checagemDeRegistro;
	}

	/**
	 * Método responsável por construir um Insert Statement.
	 * @param registro
	 * @return String contendo o Insert Statement.
	 */
	private String construirInsert(List<String> registro) {
		StringBuilder insert = new StringBuilder("INSERT INTO " + this.nomeTabela + " ");
		
		insert.append("(");
		
		for (int i = 0; i < this.colunas.size(); i++) {
			if (i > 0) {
				insert.append(",");
			}
			insert.append(colunas.get(i));
		}
		
		insert.append(")" + Constantes.QUEBRA_DE_LINHA);
		insert.append(Constantes.TAB + Constantes.TAB + "VALUES ");
		insert.append("(");

		for (int i = 0; i < registro.size(); i++) {
			if (i > 0) {
				insert.append(",");
			}
			
			insert.append(tratarValorColuna(registro.get(i)));
		}

		insert.append(");");

		return insert.toString();
	}

	private void tratarInsertComIdentity() {
		if (this.tipo == TipoScriptEnum.INSERT && this.utilizaIdentity) {
			chavePrimaria.forEach(chave -> {
				int indiceChavePrimaria = this.colunas.indexOf(chave);
				this.colunas.remove(indiceChavePrimaria);
				
				this.registros.forEach(registro -> {
					registro.remove(indiceChavePrimaria);
				});
			});
		}
	}

	/**
	 * Método responsável por construir um Delete Statement.
	 * @param clausulaWhere
	 * @return Stringo contendo o Delete Statement.
	 */
	private String construirDelete(String clausulaWhere) {
		StringBuilder delete = new StringBuilder("DELETE FROM " + this.nomeTabela + " " + Constantes.QUEBRA_DE_LINHA);
		delete.append(Constantes.TAB + Constantes.TAB + clausulaWhere);
		
		return delete.toString();
	}
	
	private String tratarValorColuna(String valor) {
		String valorTratado = "";

		if ("null".equalsIgnoreCase(valor)) {
			valorTratado = valor;
		} else if (valor != null && valor.indexOf("()") != -1) {
			valorTratado = valor;
		} else {
			valorTratado = "'"+valor+"'";
		}

		return valorTratado;
	}

	@Override
	public String toString() {
		StringBuilder corpo = new StringBuilder();
		
		this.tratarInsertComIdentity();
		
		for (List<String> registro : this.registros) {
			String clausulaWhere = this.constuirClausulaWhere(registro, this.chavePrimaria);
			String checagemDeRegistro = this.construirChecagemDeRegistro(clausulaWhere, this.tipo == TipoScriptEnum.INSERT ? true : false);
			
			corpo.append(checagemDeRegistro + Constantes.QUEBRA_DE_LINHA
						 + Constantes.TAB + "BEGIN" + Constantes.QUEBRA_DE_LINHA); 
			
			switch (this.tipo) {
			case INSERT:
				corpo.append(Constantes.TAB + Constantes.TAB + this.construirInsert(registro) + Constantes.QUEBRA_DE_LINHA);
				break;
			case DELETE:
				corpo.append(Constantes.TAB + Constantes.TAB + this.construirDelete(clausulaWhere) + Constantes.QUEBRA_DE_LINHA);
				break;
			default:
				break;
			}
			
			corpo.append(Constantes.TAB + Constantes.TAB + "SET @L_CONTADOR = @L_CONTADOR + 1;" + Constantes.QUEBRA_DE_LINHA);
			corpo.append(Constantes.TAB + "END");
			corpo.append(Constantes.QUEBRA_DE_LINHA);
		}
		
		return corpo.toString();
	}

}

package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import helpers.Constantes;

public class Corpo {
	private String nomeTabela;
	private TipoScriptEnum tipo;
	private List<String> colunas;
	private List<String> registros;
	private List<String> colunasIgnoradas;

	public Corpo(String nomeTabela, TipoScriptEnum tipo) {
		this.nomeTabela = nomeTabela;
		this.tipo = tipo;
		this.registros = new ArrayList<>();
		this.colunas = new ArrayList<>();
		this.colunasIgnoradas = new ArrayList<>();
	}

	public void adicionarRegistros(List<String> registros) {
		this.registros.addAll(registros);
	}

	public void adicionarColunas(List<String> colunas) {
		this.colunas.addAll(colunas);
	}
	
	public void adicionarColunasIgnoradas(List<String> colunasIgnoradas) {
		this.colunasIgnoradas.addAll(colunasIgnoradas);
	}

	/**
	 * Método responsável por construir a clausula "WHERE" do script, removendo as colunas ignoradas.
	 * @param registro
	 * @param colunasIgnoradas
	 * @return String contendo a clausula WHERE.
	 */
	private String constuirClausulaWhere(String registro, List<String> colunasIgnoradas) {
		StringBuilder clausulaWhere = new StringBuilder("WHERE");
		List<String> listaValoresRegistro = Arrays.asList(registro.split(";"));
		
		for (int i = 0; i< this.colunas.size(); i++) {
			if(colunasIgnoradas.contains(colunas.get(i))){
				continue;
			}
			
			if(i>0) {
				clausulaWhere.append(" AND");
			}
			
			clausulaWhere.append(" " + colunas.get(i) + " = " + tratarValorColuna(listaValoresRegistro.get(i)));
		}
		
		return clausulaWhere.toString();
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
	private String construirInsert(String registro) {
		StringBuilder insert = new StringBuilder("INSERT INTO " + this.nomeTabela + " ");
		String[] valoresRegistro = registro.split(";");
		
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

		for (int i = 0; i < valoresRegistro.length; i++) {
			if (i > 0) {
				insert.append(",");
			}
			
			insert.append(tratarValorColuna(valoresRegistro[i]));
		}

		insert.append(");");

		return insert.toString();
	}
	
	/**
	 * Método responsável por construir um Delete Statement.
	 * @param registro
	 * @param clausulaWhere
	 * @return Stringo contendo o Delete Statement.
	 */
	private String construirDelete(String registro, String clausulaWhere) {
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
		
		for (String registro : this.registros) {
			String clausulaWhere = this.constuirClausulaWhere(registro, this.colunasIgnoradas);
			String checagemDeRegistro = this.construirChecagemDeRegistro(clausulaWhere, this.tipo == TipoScriptEnum.INSERT ? true : false);
			
			corpo.append(checagemDeRegistro + Constantes.QUEBRA_DE_LINHA
						 + Constantes.TAB + "BEGIN" + Constantes.QUEBRA_DE_LINHA); 
			
			switch (this.tipo) {
			case INSERT:
				corpo.append(Constantes.TAB + Constantes.TAB + this.construirInsert(registro) + Constantes.QUEBRA_DE_LINHA);
				break;
			case DELETE:
				corpo.append(Constantes.TAB + Constantes.TAB + this.construirDelete(registro, clausulaWhere) + Constantes.QUEBRA_DE_LINHA);
				break;
			default:
				break;
			}
			
			corpo.append(Constantes.TAB + Constantes.TAB + "SET @L_CONTADOR = @L_CONTADOR + 1;" + Constantes.QUEBRA_DE_LINHA);
			corpo.append(Constantes.TAB + "END");
			corpo.append(Constantes.QUEBRA_DE_LINHA + Constantes.QUEBRA_DE_LINHA);
		}
		
		return corpo.toString();
	}

}

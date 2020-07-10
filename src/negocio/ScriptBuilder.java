package negocio;

import java.util.Arrays;
import java.util.List;

import modelo.Cabecalho;
import modelo.Corpo;
import modelo.Rodape;
import modelo.Script;
import modelo.TipoScriptEnum;

public class ScriptBuilder {
	private List<String> colunas;
	private List<String> registros;
	private List<String> colunasIgnoradas;
	private String baseDeDados;
	private String nomeTabela;
	private TipoScriptEnum tipoScript;
	private int numeroRegistros;

	public ScriptBuilder paraBaseDeDados(String baseDeDados) {
		this.baseDeDados = baseDeDados;

		return this;
	}

	public ScriptBuilder paraTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;

		return this;
	}
	
	public ScriptBuilder doTipo(String tipoScript) {
		if("INSERT".equals(tipoScript)) {
			this.tipoScript = TipoScriptEnum.INSERT;
		} else if("DELETE".equals(tipoScript)) {
			this.tipoScript = TipoScriptEnum.DELETE;
		} else {
			this.tipoScript = TipoScriptEnum.UPDATE;
		}
		return this;
	}

	public ScriptBuilder comColunas(List<String> registros) {
		this.colunas = Arrays.asList(registros.get(0).split(";"));
		return this;
	}

	public ScriptBuilder comOsRegistros(List<String> registros) {
		this.registros = registros.subList(1, registros.size());

		return this;
	}

	public ScriptBuilder IgnorandoAsColunas(String colunasIgnoradas) {
		this.colunasIgnoradas = Arrays.asList(colunasIgnoradas.split(","));
		
		return this;
	}

	public ScriptBuilder eQuantidade(int numeroRegistros) {
		this.numeroRegistros = numeroRegistros;
		
		return this;
		
	}
	
	public Script criarScript() {
		Cabecalho cabecalho = new Cabecalho(this.baseDeDados);
		Corpo corpo = new Corpo(this.nomeTabela, this.tipoScript);
		corpo.adicionarColunas(colunas);
		corpo.adicionarRegistros(registros);
		corpo.adicionarColunasIgnoradas(colunasIgnoradas);
		
		Rodape rodape = new Rodape(this.numeroRegistros);
		
		return new Script(cabecalho, corpo, rodape);
	}



}

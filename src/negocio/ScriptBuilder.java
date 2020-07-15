package negocio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import modelo.Cabecalho;
import modelo.Corpo;
import modelo.Rodape;
import modelo.Script;
import modelo.TipoScriptEnum;

public class ScriptBuilder {
	private List<String> colunas;
	private List<List<String>> registros;
	private boolean utilizaIdentity;
	private List<String> chavePrimaria;
	private String baseDeDados;
	private String nomeTabela;
	private TipoScriptEnum tipoScript;
	
	public ScriptBuilder() {
		this.registros = new ArrayList<>();
	}

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
		List<String> linhasRegistro = registros.subList(1, registros.size());
		for (String registro : linhasRegistro) {
			this.registros.add(new ArrayList<String>(Arrays.asList(registro.split(";"))));
		}

		return this;
	}
	
	public ScriptBuilder utilizandoIdentity(boolean utilizaIdentity) {
		this.utilizaIdentity = utilizaIdentity;
		
		return this;
	}

	public ScriptBuilder comChavePrimaria(String chavePrimaria) {
		this.chavePrimaria = Arrays.asList(chavePrimaria.split(","));
		
		return this;
	}

	public Script criarScript() {
		Cabecalho cabecalho = new Cabecalho(this.baseDeDados);
		Corpo corpo = new Corpo(this.nomeTabela, this.tipoScript, this.utilizaIdentity);
		corpo.adicionarColunas(colunas);
		corpo.adicionarChavePrimaria(chavePrimaria);
		corpo.adicionarRegistros(registros);
		
		Rodape rodape = new Rodape(this.registros.size());
		
		return new Script(cabecalho, corpo, rodape);
	}



}

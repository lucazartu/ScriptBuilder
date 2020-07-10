package negocio;

import java.util.List;

import helpers.ArquivosHelper;
import modelo.Script;

public class GerenciadorScript {
	
	/**
	 * Método responsável por gerar o arquivo de script utilizando os parametros preenchidos.
	 * @param baseDeDados
	 * @param nomeTabela
	 * @param colunasIgnoradas
	 * @param tipoScript
	 */
	public static void gerarScript(String baseDeDados, String nomeTabela, String colunasIgnoradas, String tipoScript) {
		List<String> arquivoScript = ArquivosHelper.lerArquivoEmLista("script.csv");
		ScriptBuilder builder = new ScriptBuilder();
		
		builder.paraBaseDeDados(baseDeDados)
		.paraTabela(nomeTabela)
		.doTipo(tipoScript)
		.comColunas(arquivoScript)
		.comOsRegistros(arquivoScript)
		.IgnorandoAsColunas(colunasIgnoradas)
		.eQuantidade(arquivoScript.size() - 1);
		
		Script script = builder.criarScript();
		
		ArquivosHelper.criarEscreverArquivo("script.sql", script.toString());
	}
}

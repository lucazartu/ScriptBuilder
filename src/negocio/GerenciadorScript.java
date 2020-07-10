package negocio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import helpers.ArquivosHelper;
import modelo.Script;

public class GerenciadorScript {

	/**
	 * M�todo respons�vel por gerar o arquivo de script utilizando os parametros
	 * preenchidos.
	 * 
	 * @param baseDeDados
	 * @param nomeTabela
	 * @param colunasIgnoradas
	 * @param tipoScript
	 * @throws FileNotFoundException
	 */
	public static void gerarScript(String baseDeDados, String nomeTabela, String colunasIgnoradas, String tipoScript)
			throws FileNotFoundException {
		List<String> arquivoScript = ArquivosHelper.lerArquivoEmLista("script.csv");
		ScriptBuilder builder = new ScriptBuilder();

		builder.paraBaseDeDados(baseDeDados).
		paraTabela(nomeTabela)
		.doTipo(tipoScript)
		.comColunas(arquivoScript)
		.comOsRegistros(arquivoScript)
		.IgnorandoAsColunas(colunasIgnoradas)
		.eQuantidade(arquivoScript.size() - 1);

		Script script = builder.criarScript();

		ArquivosHelper.criarEscreverArquivo("script.sql", script.toString());

		System.out.println("Arquivo CSV N�o encontrado.");
	}
}

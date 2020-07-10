package negocio;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import helpers.ArquivosHelper;
import modelo.Script;

public class GerenciadorScript {

	/**
	 * Método responsável por gerar o arquivo de script utilizando os parametros
	 * preenchidos.
	 * 
	 * @param baseDeDados
	 * @param nomeTabela
	 * @param colunasIgnoradas
	 * @param tipoScript
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException 
	 */
	public static void gerarScript(String baseDeDados, String nomeTabela, String colunasIgnoradas, String tipoScript,
			String arquivoDeScript, String diretorioDeSaida) throws FileNotFoundException, UnsupportedEncodingException {
		List<String> arquivoScript = ArquivosHelper.lerArquivoEmLista(arquivoDeScript);
		ScriptBuilder builder = new ScriptBuilder();

		builder.paraBaseDeDados(baseDeDados)
		.paraTabela(nomeTabela)
		.doTipo(tipoScript)
		.comColunas(arquivoScript)
		.comOsRegistros(arquivoScript)
		.IgnorandoAsColunas(colunasIgnoradas)
		.eQuantidade(arquivoScript.size() - 1);

		Script script = builder.criarScript();

		ArquivosHelper.criarEscreverArquivo(diretorioDeSaida+"\\script.sql", script.toString());
	}
}

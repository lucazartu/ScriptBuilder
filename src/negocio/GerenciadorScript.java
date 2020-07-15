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
	 * @param utilizaIdentity 
	 * @param chavePrimaria
	 * @param tipoScript
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException 
	 */
	public static void gerarScript(String baseDeDados, String nomeTabela, boolean utilizaIdentity, String chavePrimaria, String tipoScript,
			String arquivoDeScript, String diretorioDeSaida) throws FileNotFoundException, UnsupportedEncodingException {
		List<String> arquivoScript = ArquivosHelper.lerArquivoEmLista(arquivoDeScript);
		ScriptBuilder builder = new ScriptBuilder();

		builder.paraBaseDeDados(baseDeDados)
		.paraTabela(nomeTabela)
		.doTipo(tipoScript)
		.comColunas(arquivoScript)
		.comOsRegistros(arquivoScript)
		.utilizandoIdentity(utilizaIdentity)
		.comChavePrimaria(chavePrimaria);

		Script script = builder.criarScript();

		ArquivosHelper.criarEscreverArquivo(diretorioDeSaida+"\\script.sql", script.toString());
	}
}

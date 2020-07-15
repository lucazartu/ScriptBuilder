package negocio;

import exceptions.BaseNaoInformadaException;
import exceptions.ChavePrimariaObrigatoriaException;
import exceptions.NomeTabelaNaoInformadaException;

public class ValidadorParametros {
	public static void validarParametros(String baseDeDados, String nomeTabela, String chavePrimaria,
			boolean utilizaIdentity)
			throws BaseNaoInformadaException, NomeTabelaNaoInformadaException, ChavePrimariaObrigatoriaException {
		if (baseDeDados.isEmpty()) {
			throw new BaseNaoInformadaException("É necessário informar a base de dados!");
		} else if (utilizaIdentity && chavePrimaria.isEmpty()) {
			throw new ChavePrimariaObrigatoriaException(
					"Necessário informar a chave primária quando a tabela possui Identity");
		} else if (nomeTabela.isEmpty()) {
			throw new NomeTabelaNaoInformadaException("É necessário informar o nome da tabela!");
		}
	}
}

package negocio;

import exceptions.BaseNaoInformadaException;
import exceptions.ChavePrimariaObrigatoriaException;
import exceptions.NomeTabelaNaoInformadaException;

public class ValidadorParametros {
	public static void validarParametros(String baseDeDados, String nomeTabela, String chavePrimaria,
			boolean utilizaIdentity)
			throws BaseNaoInformadaException, NomeTabelaNaoInformadaException, ChavePrimariaObrigatoriaException {
		if (baseDeDados.isEmpty()) {
			throw new BaseNaoInformadaException("� necess�rio informar a base de dados!");
		} else if (utilizaIdentity && chavePrimaria.isEmpty()) {
			throw new ChavePrimariaObrigatoriaException(
					"Necess�rio informar a chave prim�ria quando a tabela possui Identity");
		} else if (nomeTabela.isEmpty()) {
			throw new NomeTabelaNaoInformadaException("� necess�rio informar o nome da tabela!");
		}
	}
}

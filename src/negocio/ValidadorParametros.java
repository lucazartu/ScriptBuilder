package negocio;

import exceptions.BaseNaoInformadaException;
import exceptions.NomeTabelaNaoInformadaException;

public class ValidadorParametros {
	public static void validarParametros(String baseDeDados, String nomeTabela)
			throws BaseNaoInformadaException, NomeTabelaNaoInformadaException {
		if (baseDeDados.isEmpty()) {
			throw new BaseNaoInformadaException("� necess�rio informar a base de dados!");
		} else if (nomeTabela.isEmpty()) {
			throw new NomeTabelaNaoInformadaException("� necess�rio iformar o nome da tabela!");
		}
	}
}

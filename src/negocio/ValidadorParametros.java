package negocio;

import exceptions.BaseNaoInformadaException;
import exceptions.NomeTabelaNaoInformadaException;

public class ValidadorParametros {
	public static void validarParametros(String baseDeDados, String nomeTabela)
			throws BaseNaoInformadaException, NomeTabelaNaoInformadaException {
		if (baseDeDados.isEmpty()) {
			throw new BaseNaoInformadaException("É necessário informar a base de dados!");
		} else if (nomeTabela.isEmpty()) {
			throw new NomeTabelaNaoInformadaException("É necessário iformar o nome da tabela!");
		}
	}
}

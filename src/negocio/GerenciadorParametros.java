package negocio;

import java.util.Collection;
import java.util.List;

import exceptions.BaseNaoInformadaException;
import exceptions.IndentificadorNaoInformadoException;
import exceptions.NomeTabelaNaoInformadaException;
import helpers.ArquivosHelper;

public class GerenciadorParametros {
	public static Collection<String> popularBaseDados() {
		List<String> listaBases = ArquivosHelper.lerArquivoEmLista("lista-base-dados.txt");

		return listaBases;
	}

	public static void validarParametros(String base, String nomeTabela, String identificadoresUnicos)
			throws BaseNaoInformadaException, IndentificadorNaoInformadoException, NomeTabelaNaoInformadaException {
		if (base == null || base.isEmpty()) {
			throw new BaseNaoInformadaException("A Base de dados deve ser informada!");
		} else if (nomeTabela == null || nomeTabela.isEmpty()) {
			throw new NomeTabelaNaoInformadaException("O nome da tabela deve ser informado!");
		} else if (identificadoresUnicos == null || identificadoresUnicos.isEmpty()) {
			throw new IndentificadorNaoInformadoException("Pelo menos um identificador deve ser informado!");
		}
	}

}

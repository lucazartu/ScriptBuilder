package negocio;

import java.util.Arrays;
import java.util.List;

import exceptions.IdentificadorInvalidoException;
import helpers.ArquivosHelper;

public class GerenciadorScript {
	
	final static String INSERT_UPDATE = "INSERT/UPDATE";

	public static void gerarScript(String baseDeDados, String nomeTabela, String identificadores, String tipoScript)
			throws IdentificadorInvalidoException {
		StringBuilder scriptBase = ArquivosHelper.lerArquivoEmTexto("templates\\templateScript.txt");

		List<String> registros = ArquivosHelper.lerArquivoEmLista("script.csv");
		StringBuilder corpo = new StringBuilder();

		int indiceBaseDeDados = scriptBase.indexOf("{0}");
		scriptBase.replace(indiceBaseDeDados, indiceBaseDeDados + 3, baseDeDados);
		
		int indiceContador = scriptBase.indexOf("{1}");
		scriptBase.replace(indiceContador, indiceContador+3, String.format("%s", registros.size() - 1));

		if (INSERT_UPDATE.equals(tipoScript)) {
			corpo = gerarInsert(nomeTabela, identificadores, registros);
		} else {
			corpo = gerarDelete(nomeTabela, identificadores, registros);
		}

		int indiceCorpo = scriptBase.indexOf("{2}");
		scriptBase.replace(indiceCorpo, indiceCorpo + 3, corpo.toString());
		
		ArquivosHelper.criarEscreverArquivo("script.sql", scriptBase.toString());
	}

	private static StringBuilder gerarDelete(String nomeTabela, String identificadores, List<String> registros)
			throws IdentificadorInvalidoException {
		StringBuilder corpo = new StringBuilder();
		StringBuilder templateDelete = ArquivosHelper.lerArquivoEmTexto("templates\\templateDelete.txt");

		for (int i = 1; i < registros.size(); i++) {
			StringBuilder registro = new StringBuilder(templateDelete);

			StringBuilder clausulaWhere = gerarClausulaWhere(registros, Arrays.asList(registros.get(i).split(";")),
					Arrays.asList(identificadores.split(",")));

			int indiceNomeTabela = registro.indexOf("{0}");
			registro.replace(indiceNomeTabela, indiceNomeTabela + 3, nomeTabela);
			indiceNomeTabela = registro.indexOf("{0}");
			registro.replace(indiceNomeTabela, indiceNomeTabela + 3, nomeTabela);
			
			int indiceWhere = registro.indexOf("{1}");
			registro.replace(indiceWhere, indiceWhere + 3, clausulaWhere.toString());
			indiceWhere = registro.indexOf("{1}");
			registro.replace(indiceWhere, indiceWhere + 3, clausulaWhere.toString());

			corpo.append(registro);
		}
		return corpo;
	}

	private static StringBuilder gerarInsert(String nomeTabela, String identificadores, List<String> registros)
			throws IdentificadorInvalidoException {
		StringBuilder corpo = new StringBuilder();

		StringBuilder templateInsertUpdate = ArquivosHelper.lerArquivoEmTexto("templates\\templateInsertUpdate.txt");

		for (int i = 1; i < registros.size(); i++) {
			StringBuilder registro = new StringBuilder(templateInsertUpdate);

			StringBuilder clausulaWhere = gerarClausulaWhere(registros, Arrays.asList(registros.get(i).split(";")),
					Arrays.asList(identificadores.split(",")));

			int indiceNomeTabela = registro.indexOf("{0}");
			registro.replace(indiceNomeTabela, indiceNomeTabela + 3, nomeTabela);

			indiceNomeTabela = registro.indexOf("{0}");
			registro.replace(indiceNomeTabela, indiceNomeTabela + 3, nomeTabela);

			int indiceWhere = registro.indexOf("{1}");
			registro.replace(indiceWhere, indiceWhere + 3, clausulaWhere.toString());

			int indiceNomeColunas = registro.indexOf("{2}");
			registro.replace(indiceNomeColunas, indiceNomeColunas + 3, registros.get(0).replace(";", ","));

			int indiceValores = registro.indexOf("{3}");
			registro.replace(indiceValores, indiceValores + 3, "'" + registros.get(i).replace(";", "','").replace("\\w+", "\'") + "'");

			corpo.append(registro);
		}

		return corpo;
	}

	private static StringBuilder gerarClausulaWhere(List<String> registros, List<String> linha,
			List<String> identificadores) throws IdentificadorInvalidoException {
		StringBuilder clausulaWhere = new StringBuilder();
		List<String> nomesColunas = Arrays.asList(registros.get(0).split(";"));

		for (int i = 0; i < identificadores.size(); i++) {
			if (i > 0) {
				clausulaWhere.append(" AND ");
			}
			int indiceColuna = nomesColunas.indexOf(identificadores.get(i));

			if (indiceColuna == -1) {
				throw new IdentificadorInvalidoException("Identificador " + identificadores.get(i) + " não inválido!");
			}

			clausulaWhere.append(String.format("%s=%s", identificadores.get(i), linha.get(indiceColuna)));
		}

		return clausulaWhere;
	}
}

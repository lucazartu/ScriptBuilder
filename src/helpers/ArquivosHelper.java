package helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArquivosHelper {
	public static List<String> lerArquivoEmLista(String nomeArquivo) {
		File arquivo = new File(nomeArquivo);
		List<String> conteudo = new ArrayList<>();

		try {
			InputStream is = new FileInputStream(arquivo);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			for (String linha : br.lines().collect(Collectors.toList())) {
				conteudo.add(linha);
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return conteudo;
	}

	public static StringBuilder lerArquivoEmTexto(String nomeArquivo) {
		File arquivo = new File(nomeArquivo);
		StringBuilder conteudo = new StringBuilder();

		try {
			InputStream is = new FileInputStream(arquivo);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			for (String linha : br.lines().collect(Collectors.toList())) {
				conteudo.append(linha + "\n");
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return conteudo;
	}

	private static boolean criarArquivo(File arquivo) {
			
			boolean criado = false;
			
			try {
				criado = arquivo.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return criado;
	}

	private static void escreverEmArquivo(String nomeArquivo, String conteudo) {
		FileWriter arquivo;
		try {
			arquivo = new FileWriter(nomeArquivo);
			arquivo.write(conteudo);
			arquivo.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void criarEscreverArquivo(String nomeArquivo, String conteudo) {
		File arquivo = new File(nomeArquivo);
		
		if(!arquivo.exists()) {
			criarArquivo(arquivo);
		}
		
		escreverEmArquivo(nomeArquivo, conteudo);
	}
}

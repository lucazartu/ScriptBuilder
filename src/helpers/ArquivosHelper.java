package helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.input.BOMInputStream;

public class ArquivosHelper {
	public static List<String> lerArquivoEmLista(String nomeArquivo) throws FileNotFoundException, UnsupportedEncodingException {
		File arquivo = new File(nomeArquivo);
		List<String> conteudo = new ArrayList<>();

		InputStream is = new FileInputStream(arquivo);
		BufferedReader br = new BufferedReader(new InputStreamReader(new BOMInputStream(is)));

		for (String linha : br.lines().collect(Collectors.toList())) {
			if(linha.startsWith("?")) {
				linha = linha.substring(1);
			}
			conteudo.add(linha);
		}

		try {
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

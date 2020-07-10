package modelo;

import helpers.Constantes;

public class Script {
	private Cabecalho cabecalho;
	private Corpo corpo;
	private Rodape rodape;

	public Script(Cabecalho cabecalho, Corpo corpo, Rodape rodape) {
		this.cabecalho = cabecalho;
		this.corpo = corpo;
		this.rodape = rodape;
	}
	
	@Override
	public String toString() {
		return this.cabecalho.toString() + Constantes.QUEBRA_DE_LINHA 
				+ this.corpo.toString() + Constantes.QUEBRA_DE_LINHA 
				+ this.rodape.toString();
	}

}

package modelo;

import helpers.Constantes;

public class Rodape {
	private int contador;

	public Rodape(int contador) {
		this.contador = contador;
	}

	@Override
	public String toString() {
		return "IF @L_CONTADOR = " + this.contador + Constantes.QUEBRA_DE_LINHA
				+ "COMMIT TRAN" + Constantes.QUEBRA_DE_LINHA
				+ "ELSE" + Constantes.QUEBRA_DE_LINHA
				+ "ROLLBACK TRAN" + Constantes.QUEBRA_DE_LINHA
				+ "GO" + Constantes.QUEBRA_DE_LINHA
				+ "ROLLBACK TRAN" + Constantes.QUEBRA_DE_LINHA 
				+ "SELECT @L_CONTADOR as 'Registros Afetados'" + Constantes.QUEBRA_DE_LINHA 
				+ "GO" + Constantes.QUEBRA_DE_LINHA;
	}
}

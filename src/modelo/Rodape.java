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
				+ "IF @@TRANCOUNT = 0" + Constantes.QUEBRA_DE_LINHA
				+ "BEGIN" + Constantes.QUEBRA_DE_LINHA
				+ Constantes.TAB + "DECLARE @MENSAGEM VARCHAR(100)" + Constantes.QUEBRA_DE_LINHA
				+ Constantes.TAB + "SET @MENSAGEM = 'ERRO DE TRANSAÇÕES - EXISTE(M) ' + CAST(@@TRANCOUNT AS VARCHAR(9))+ ' BEGIN(S) A MAIS NO SCRIPT'" + Constantes.QUEBRA_DE_LINHA
				+ Constantes.TAB + "ROLLBACK TRAN" + Constantes.QUEBRA_DE_LINHA
				+ Constantes.TAB + "RAISERROR( @MENSAGEM, 16, 1)"+ Constantes.QUEBRA_DE_LINHA
				+ "END" + Constantes.QUEBRA_DE_LINHA
				+ "ELSE" + Constantes.QUEBRA_DE_LINHA
				+ "SELECT GETDATE(), DB_NAME(), USER_NAME(), SUSER_NAME(), @@SERVERNAME";
	}
}

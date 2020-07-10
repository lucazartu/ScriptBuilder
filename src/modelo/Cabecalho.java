package modelo;

import helpers.Constantes;

public class Cabecalho {
	private String baseDeDados;

	public Cabecalho(String baseDeDados) {
		this.baseDeDados = baseDeDados;
	}
	
	@Override
	public String toString() {
		return "USE " + this.baseDeDados + Constantes.QUEBRA_DE_LINHA 
				+ "BEGIN TRAN" + Constantes.QUEBRA_DE_LINHA 
				+ "GO" + Constantes.QUEBRA_DE_LINHA 
				+ "SET NUMERIC_ROUNDABORT OFF" + Constantes.QUEBRA_DE_LINHA 
				+ "GO" + Constantes.QUEBRA_DE_LINHA 
				+ "SET ANSI_PADDING, ANSI_WARNINGS, CONCAT_NULL_YIELDS_NULL, ARITHABORT, QUOTED_IDENTIFIER, ANSI_NULLS ON" + Constantes.QUEBRA_DE_LINHA 
				+ "GO" + Constantes.QUEBRA_DE_LINHA 
				+ "SET XACT_ABORT OFF" + Constantes.QUEBRA_DE_LINHA 
				+ "GO" + Constantes.QUEBRA_DE_LINHA 
				+ "SELECT GETDATE(), DB_NAME(), USER_NAME(), SUSER_NAME(), @@SERVERNAME" + Constantes.QUEBRA_DE_LINHA 
				+ "GO" + Constantes.QUEBRA_DE_LINHA 
				+ "" + Constantes.QUEBRA_DE_LINHA 
				+ "declare @L_CONTADOR int = 0" + Constantes.QUEBRA_DE_LINHA;
	}
}

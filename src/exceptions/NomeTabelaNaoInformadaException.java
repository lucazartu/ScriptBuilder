package exceptions;

@SuppressWarnings("serial")
public class NomeTabelaNaoInformadaException extends Exception {
	
	public NomeTabelaNaoInformadaException(String mensagem) {
		super(mensagem);
	}
}

package application;

import java.net.URL;
import java.util.ResourceBundle;

import exceptions.BaseNaoInformadaException;
import exceptions.NomeTabelaNaoInformadaException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import negocio.GerenciadorScript;
import negocio.ValidadorParametros;

public class ScriptBuilderController implements Initializable {
	@FXML
	private TextField txtBaseDados;

	@FXML
	private TextField txtNomeTabela;

	@FXML
	private TextArea txtIdentificadoresUnicos;

	@FXML
	private ToggleGroup tglGrpTipoScript;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	/**
	 * Método responsável por resetar todos os campos do software e recarregar todas
	 * as informações.
	 * Atrelado ao botão "Recarregar".
	 * 
	 * @param event
	 */
	public void regarregar(ActionEvent event) {
		this.txtBaseDados.deleteText(0, txtBaseDados.getLength());
		this.txtNomeTabela.deleteText(0, this.txtNomeTabela.getLength());
		this.txtIdentificadoresUnicos.deleteText(0, txtIdentificadoresUnicos.getLength());
		this.tglGrpTipoScript.getToggles().get(0).setSelected(true);
	}

	@FXML
	/**
	 * Método responsável por gerar o script baseado nos parametros de entrada do
	 * programa.
	 * Atrelado ao botão "Gerar Script"
	 * 
	 * @param event
	 */
	public void gerarScript(ActionEvent event) {
		Alert alertaValidacao = new Alert(AlertType.WARNING);
		String baseDeDados = this.txtBaseDados.getText();
		String nomeTabela = this.txtNomeTabela.getText();
		String identificadores = this.txtIdentificadoresUnicos.getText().replaceAll("\\s", "").toUpperCase();
		String tipoScript = ((RadioButton) this.tglGrpTipoScript.getSelectedToggle()).getText();

		try {
			ValidadorParametros.validarParametros(baseDeDados, nomeTabela);
		} catch (BaseNaoInformadaException e) {
			alertaValidacao.setTitle("Base de Dados");
			alertaValidacao.setContentText(e.getMessage());
			alertaValidacao.show();
		} catch (NomeTabelaNaoInformadaException e) {
			alertaValidacao.setTitle("Nome da Tabela");
			alertaValidacao.setContentText(e.getMessage());
			alertaValidacao.show();
		}

		try {
			GerenciadorScript.gerarScript(baseDeDados, nomeTabela, identificadores, tipoScript);
		} catch (Exception e) {
			alertaValidacao.setAlertType(AlertType.ERROR);
			alertaValidacao.setTitle("ERRO Não Tratado");
			alertaValidacao.setContentText("Erro não identificado, favor, verifique os parametros e o arquivo CSV");
			alertaValidacao.show();
		}
	}

}
